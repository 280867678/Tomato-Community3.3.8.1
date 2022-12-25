package org.jsoup.select;

import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.TokenQueue;
import org.jsoup.select.CombiningEvaluator;
import org.jsoup.select.Evaluator;
import org.jsoup.select.Selector;
import org.jsoup.select.StructuralEvaluator;
import org.slf4j.Marker;

/* loaded from: classes4.dex */
public class QueryParser {
    private List<Evaluator> evals = new ArrayList();
    private String query;

    /* renamed from: tq */
    private TokenQueue f6055tq;
    private static final String[] combinators = {",", SimpleComparison.GREATER_THAN_OPERATION, Marker.ANY_NON_NULL_MARKER, "~", ConstantUtils.PLACEHOLDER_STR_ONE};
    private static final String[] AttributeEvals = {SimpleComparison.EQUAL_TO_OPERATION, "!=", "^=", "$=", "*=", "~="};
    private static final Pattern NTH_AB = Pattern.compile("(([+-])?(\\d+)?)n(\\s*([+-])?\\s*\\d+)?", 2);
    private static final Pattern NTH_B = Pattern.compile("([+-])?(\\d+)");

    private QueryParser(String str) {
        Validate.notEmpty(str);
        String trim = str.trim();
        this.query = trim;
        this.f6055tq = new TokenQueue(trim);
    }

    public static Evaluator parse(String str) {
        try {
            return new QueryParser(str).parse();
        } catch (IllegalArgumentException e) {
            throw new Selector.SelectorParseException(e.getMessage(), new Object[0]);
        }
    }

    Evaluator parse() {
        this.f6055tq.consumeWhitespace();
        if (this.f6055tq.matchesAny(combinators)) {
            this.evals.add(new StructuralEvaluator.Root());
            combinator(this.f6055tq.consume());
        } else {
            findElements();
        }
        while (!this.f6055tq.isEmpty()) {
            boolean consumeWhitespace = this.f6055tq.consumeWhitespace();
            if (this.f6055tq.matchesAny(combinators)) {
                combinator(this.f6055tq.consume());
            } else if (consumeWhitespace) {
                combinator(' ');
            } else {
                findElements();
            }
        }
        if (this.evals.size() == 1) {
            return this.evals.get(0);
        }
        return new CombiningEvaluator.And(this.evals);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0046  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void combinator(char c) {
        Evaluator and;
        Evaluator evaluator;
        boolean z;
        CombiningEvaluator.And and2;
        this.f6055tq.consumeWhitespace();
        Evaluator parse = parse(consumeSubQuery());
        if (this.evals.size() == 1) {
            and = this.evals.get(0);
            if ((and instanceof CombiningEvaluator.C5428Or) && c != ',') {
                z = true;
                evaluator = and;
                and = ((CombiningEvaluator.C5428Or) and).rightMostEvaluator();
                this.evals.clear();
                if (c != '>') {
                    and2 = new CombiningEvaluator.And(parse, new StructuralEvaluator.ImmediateParent(and));
                } else if (c == ' ') {
                    and2 = new CombiningEvaluator.And(parse, new StructuralEvaluator.Parent(and));
                } else if (c == '+') {
                    and2 = new CombiningEvaluator.And(parse, new StructuralEvaluator.ImmediatePreviousSibling(and));
                } else if (c == '~') {
                    and2 = new CombiningEvaluator.And(parse, new StructuralEvaluator.PreviousSibling(and));
                } else if (c == ',') {
                    if (and instanceof CombiningEvaluator.C5428Or) {
                        CombiningEvaluator.C5428Or c5428Or = (CombiningEvaluator.C5428Or) and;
                        c5428Or.add(parse);
                        and2 = c5428Or;
                    } else {
                        CombiningEvaluator.C5428Or c5428Or2 = new CombiningEvaluator.C5428Or();
                        c5428Or2.add(and);
                        c5428Or2.add(parse);
                        and2 = c5428Or2;
                    }
                } else {
                    throw new Selector.SelectorParseException("Unknown combinator: " + c, new Object[0]);
                }
                if (z) {
                    ((CombiningEvaluator.C5428Or) evaluator).replaceRightMostEvaluator(and2);
                    and2 = evaluator;
                }
                this.evals.add(and2);
            }
        } else {
            and = new CombiningEvaluator.And(this.evals);
        }
        evaluator = and;
        z = false;
        this.evals.clear();
        if (c != '>') {
        }
        if (z) {
        }
        this.evals.add(and2);
    }

    private String consumeSubQuery() {
        StringBuilder borrowBuilder = StringUtil.borrowBuilder();
        while (!this.f6055tq.isEmpty()) {
            if (this.f6055tq.matches("(")) {
                borrowBuilder.append("(");
                borrowBuilder.append(this.f6055tq.chompBalanced('(', ')'));
                borrowBuilder.append(")");
            } else if (this.f6055tq.matches("[")) {
                borrowBuilder.append("[");
                borrowBuilder.append(this.f6055tq.chompBalanced('[', ']'));
                borrowBuilder.append("]");
            } else if (this.f6055tq.matchesAny(combinators)) {
                break;
            } else {
                borrowBuilder.append(this.f6055tq.consume());
            }
        }
        return StringUtil.releaseBuilder(borrowBuilder);
    }

    private void findElements() {
        if (this.f6055tq.matchChomp("#")) {
            byId();
        } else if (this.f6055tq.matchChomp(".")) {
            byClass();
        } else if (this.f6055tq.matchesWord() || this.f6055tq.matches("*|")) {
            byTag();
        } else if (this.f6055tq.matches("[")) {
            byAttribute();
        } else if (this.f6055tq.matchChomp(Marker.ANY_MARKER)) {
            allElements();
        } else if (this.f6055tq.matchChomp(":lt(")) {
            indexLessThan();
        } else if (this.f6055tq.matchChomp(":gt(")) {
            indexGreaterThan();
        } else if (this.f6055tq.matchChomp(":eq(")) {
            indexEquals();
        } else if (this.f6055tq.matches(":has(")) {
            has();
        } else if (this.f6055tq.matches(":contains(")) {
            contains(false);
        } else if (this.f6055tq.matches(":containsOwn(")) {
            contains(true);
        } else if (this.f6055tq.matches(":containsData(")) {
            containsData();
        } else if (this.f6055tq.matches(":matches(")) {
            matches(false);
        } else if (this.f6055tq.matches(":matchesOwn(")) {
            matches(true);
        } else if (this.f6055tq.matches(":not(")) {
            not();
        } else if (this.f6055tq.matchChomp(":nth-child(")) {
            cssNthChild(false, false);
        } else if (this.f6055tq.matchChomp(":nth-last-child(")) {
            cssNthChild(true, false);
        } else if (this.f6055tq.matchChomp(":nth-of-type(")) {
            cssNthChild(false, true);
        } else if (this.f6055tq.matchChomp(":nth-last-of-type(")) {
            cssNthChild(true, true);
        } else if (this.f6055tq.matchChomp(":first-child")) {
            this.evals.add(new Evaluator.IsFirstChild());
        } else if (this.f6055tq.matchChomp(":last-child")) {
            this.evals.add(new Evaluator.IsLastChild());
        } else if (this.f6055tq.matchChomp(":first-of-type")) {
            this.evals.add(new Evaluator.IsFirstOfType());
        } else if (this.f6055tq.matchChomp(":last-of-type")) {
            this.evals.add(new Evaluator.IsLastOfType());
        } else if (this.f6055tq.matchChomp(":only-child")) {
            this.evals.add(new Evaluator.IsOnlyChild());
        } else if (this.f6055tq.matchChomp(":only-of-type")) {
            this.evals.add(new Evaluator.IsOnlyOfType());
        } else if (this.f6055tq.matchChomp(":empty")) {
            this.evals.add(new Evaluator.IsEmpty());
        } else if (this.f6055tq.matchChomp(":root")) {
            this.evals.add(new Evaluator.IsRoot());
        } else if (this.f6055tq.matchChomp(":matchText")) {
            this.evals.add(new Evaluator.MatchText());
        } else {
            throw new Selector.SelectorParseException("Could not parse query '%s': unexpected token at '%s'", this.query, this.f6055tq.remainder());
        }
    }

    private void byId() {
        String consumeCssIdentifier = this.f6055tq.consumeCssIdentifier();
        Validate.notEmpty(consumeCssIdentifier);
        this.evals.add(new Evaluator.C5429Id(consumeCssIdentifier));
    }

    private void byClass() {
        String consumeCssIdentifier = this.f6055tq.consumeCssIdentifier();
        Validate.notEmpty(consumeCssIdentifier);
        this.evals.add(new Evaluator.Class(consumeCssIdentifier.trim()));
    }

    private void byTag() {
        String normalize = Normalizer.normalize(this.f6055tq.consumeElementSelector());
        Validate.notEmpty(normalize);
        if (normalize.startsWith("*|")) {
            this.evals.add(new CombiningEvaluator.C5428Or(new Evaluator.Tag(normalize), new Evaluator.TagEndsWith(normalize.replace("*|", ":"))));
            return;
        }
        if (normalize.contains("|")) {
            normalize = normalize.replace("|", ":");
        }
        this.evals.add(new Evaluator.Tag(normalize));
    }

    private void byAttribute() {
        TokenQueue tokenQueue = new TokenQueue(this.f6055tq.chompBalanced('[', ']'));
        String consumeToAny = tokenQueue.consumeToAny(AttributeEvals);
        Validate.notEmpty(consumeToAny);
        tokenQueue.consumeWhitespace();
        if (tokenQueue.isEmpty()) {
            if (consumeToAny.startsWith("^")) {
                this.evals.add(new Evaluator.AttributeStarting(consumeToAny.substring(1)));
            } else {
                this.evals.add(new Evaluator.Attribute(consumeToAny));
            }
        } else if (tokenQueue.matchChomp(SimpleComparison.EQUAL_TO_OPERATION)) {
            this.evals.add(new Evaluator.AttributeWithValue(consumeToAny, tokenQueue.remainder()));
        } else if (tokenQueue.matchChomp("!=")) {
            this.evals.add(new Evaluator.AttributeWithValueNot(consumeToAny, tokenQueue.remainder()));
        } else if (tokenQueue.matchChomp("^=")) {
            this.evals.add(new Evaluator.AttributeWithValueStarting(consumeToAny, tokenQueue.remainder()));
        } else if (tokenQueue.matchChomp("$=")) {
            this.evals.add(new Evaluator.AttributeWithValueEnding(consumeToAny, tokenQueue.remainder()));
        } else if (tokenQueue.matchChomp("*=")) {
            this.evals.add(new Evaluator.AttributeWithValueContaining(consumeToAny, tokenQueue.remainder()));
        } else if (tokenQueue.matchChomp("~=")) {
            this.evals.add(new Evaluator.AttributeWithValueMatching(consumeToAny, Pattern.compile(tokenQueue.remainder())));
        } else {
            throw new Selector.SelectorParseException("Could not parse attribute query '%s': unexpected token at '%s'", this.query, tokenQueue.remainder());
        }
    }

    private void allElements() {
        this.evals.add(new Evaluator.AllElements());
    }

    private void indexLessThan() {
        this.evals.add(new Evaluator.IndexLessThan(consumeIndex()));
    }

    private void indexGreaterThan() {
        this.evals.add(new Evaluator.IndexGreaterThan(consumeIndex()));
    }

    private void indexEquals() {
        this.evals.add(new Evaluator.IndexEquals(consumeIndex()));
    }

    private void cssNthChild(boolean z, boolean z2) {
        String normalize = Normalizer.normalize(this.f6055tq.chompTo(")"));
        Matcher matcher = NTH_AB.matcher(normalize);
        Matcher matcher2 = NTH_B.matcher(normalize);
        int i = 2;
        int i2 = 0;
        if ("odd".equals(normalize)) {
            i2 = 1;
        } else if (!"even".equals(normalize)) {
            if (matcher.matches()) {
                int parseInt = matcher.group(3) != null ? Integer.parseInt(matcher.group(1).replaceFirst("^\\+", "")) : 1;
                if (matcher.group(4) != null) {
                    i2 = Integer.parseInt(matcher.group(4).replaceFirst("^\\+", ""));
                }
                i = parseInt;
            } else if (matcher2.matches()) {
                i2 = Integer.parseInt(matcher2.group().replaceFirst("^\\+", ""));
                i = 0;
            } else {
                throw new Selector.SelectorParseException("Could not parse nth-index '%s': unexpected format", normalize);
            }
        }
        if (z2) {
            if (z) {
                this.evals.add(new Evaluator.IsNthLastOfType(i, i2));
            } else {
                this.evals.add(new Evaluator.IsNthOfType(i, i2));
            }
        } else if (z) {
            this.evals.add(new Evaluator.IsNthLastChild(i, i2));
        } else {
            this.evals.add(new Evaluator.IsNthChild(i, i2));
        }
    }

    private int consumeIndex() {
        String trim = this.f6055tq.chompTo(")").trim();
        Validate.isTrue(StringUtil.isNumeric(trim), "Index must be numeric");
        return Integer.parseInt(trim);
    }

    private void has() {
        this.f6055tq.consume(":has");
        String chompBalanced = this.f6055tq.chompBalanced('(', ')');
        Validate.notEmpty(chompBalanced, ":has(el) subselect must not be empty");
        this.evals.add(new StructuralEvaluator.Has(parse(chompBalanced)));
    }

    private void contains(boolean z) {
        this.f6055tq.consume(z ? ":containsOwn" : ":contains");
        String unescape = TokenQueue.unescape(this.f6055tq.chompBalanced('(', ')'));
        Validate.notEmpty(unescape, ":contains(text) query must not be empty");
        if (z) {
            this.evals.add(new Evaluator.ContainsOwnText(unescape));
        } else {
            this.evals.add(new Evaluator.ContainsText(unescape));
        }
    }

    private void containsData() {
        this.f6055tq.consume(":containsData");
        String unescape = TokenQueue.unescape(this.f6055tq.chompBalanced('(', ')'));
        Validate.notEmpty(unescape, ":containsData(text) query must not be empty");
        this.evals.add(new Evaluator.ContainsData(unescape));
    }

    private void matches(boolean z) {
        this.f6055tq.consume(z ? ":matchesOwn" : ":matches");
        String chompBalanced = this.f6055tq.chompBalanced('(', ')');
        Validate.notEmpty(chompBalanced, ":matches(regex) query must not be empty");
        if (z) {
            this.evals.add(new Evaluator.MatchesOwn(Pattern.compile(chompBalanced)));
        } else {
            this.evals.add(new Evaluator.Matches(Pattern.compile(chompBalanced)));
        }
    }

    private void not() {
        this.f6055tq.consume(":not");
        String chompBalanced = this.f6055tq.chompBalanced('(', ')');
        Validate.notEmpty(chompBalanced, ":not(selector) subselect must not be empty");
        this.evals.add(new StructuralEvaluator.Not(parse(chompBalanced)));
    }
}
