package org.jsoup.select;

import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;

/* loaded from: classes4.dex */
abstract class CombiningEvaluator extends Evaluator {
    final ArrayList<Evaluator> evaluators;
    int num;

    CombiningEvaluator() {
        this.num = 0;
        this.evaluators = new ArrayList<>();
    }

    CombiningEvaluator(Collection<Evaluator> collection) {
        this();
        this.evaluators.addAll(collection);
        updateNumEvaluators();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Evaluator rightMostEvaluator() {
        int i = this.num;
        if (i > 0) {
            return this.evaluators.get(i - 1);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void replaceRightMostEvaluator(Evaluator evaluator) {
        this.evaluators.set(this.num - 1, evaluator);
    }

    void updateNumEvaluators() {
        this.num = this.evaluators.size();
    }

    /* loaded from: classes4.dex */
    static final class And extends CombiningEvaluator {
        /* JADX INFO: Access modifiers changed from: package-private */
        public And(Collection<Evaluator> collection) {
            super(collection);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public And(Evaluator... evaluatorArr) {
            this(Arrays.asList(evaluatorArr));
        }

        @Override // org.jsoup.select.Evaluator
        public boolean matches(Element element, Element element2) {
            for (int i = 0; i < this.num; i++) {
                if (!this.evaluators.get(i).matches(element, element2)) {
                    return false;
                }
            }
            return true;
        }

        public String toString() {
            return StringUtil.join(this.evaluators, ConstantUtils.PLACEHOLDER_STR_ONE);
        }
    }

    /* renamed from: org.jsoup.select.CombiningEvaluator$Or */
    /* loaded from: classes4.dex */
    static final class C5428Or extends CombiningEvaluator {
        C5428Or(Collection<Evaluator> collection) {
            if (this.num > 1) {
                this.evaluators.add(new And(collection));
            } else {
                this.evaluators.addAll(collection);
            }
            updateNumEvaluators();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public C5428Or(Evaluator... evaluatorArr) {
            this(Arrays.asList(evaluatorArr));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public C5428Or() {
        }

        public void add(Evaluator evaluator) {
            this.evaluators.add(evaluator);
            updateNumEvaluators();
        }

        @Override // org.jsoup.select.Evaluator
        public boolean matches(Element element, Element element2) {
            for (int i = 0; i < this.num; i++) {
                if (this.evaluators.get(i).matches(element, element2)) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return StringUtil.join(this.evaluators, ", ");
        }
    }
}
