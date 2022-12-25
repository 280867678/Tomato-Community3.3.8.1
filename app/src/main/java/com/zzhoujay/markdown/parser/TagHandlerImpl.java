package com.zzhoujay.markdown.parser;

import android.text.SpannableStringBuilder;
import android.util.Pair;
import android.util.SparseArray;
import com.tomatolive.library.utils.ConstantUtils;
import com.zzhoujay.markdown.parser.QueueConsumer;
import com.zzhoujay.markdown.style.CodeSpan;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class TagHandlerImpl implements TagHandler {
    private HashMap<String, Pair<String, String>> idImageUrl = new HashMap<>();
    private HashMap<String, Pair<String, String>> idLinkLinks = new HashMap<>();
    private QueueConsumer.QueueProvider queueProvider;
    private StyleBuilder styleBuilder;
    private static final Matcher matcherH1_2 = Pattern.compile("^\\s*=+\\s*$").matcher("");
    private static final Matcher matcherH2_2 = Pattern.compile("^\\s*-+\\s*$").matcher("");
    private static final Matcher matcherH = Pattern.compile("^\\s*#{1,6}\\s*([^#]*)(\\s*#)?").matcher("");
    private static final Matcher matcherH1 = Pattern.compile("^\\s*#\\s*([^#]*)(\\s*#)?").matcher("");
    private static final Matcher matcherH2 = Pattern.compile("^\\s*#{2}\\s*([^#]*)(\\s*#)?").matcher("");
    private static final Matcher matcherH3 = Pattern.compile("^\\s*#{3}\\s*([^#]*)(\\s*#)?").matcher("");
    private static final Matcher matcherH4 = Pattern.compile("^\\s*#{4}\\s*([^#]*)(\\s*#)?").matcher("");
    private static final Matcher matcherH5 = Pattern.compile("^\\s*#{5}\\s*([^#]*)(\\s*#)?").matcher("");
    private static final Matcher matcherH6 = Pattern.compile("^\\s*#{6}\\s*([^#]*)(\\s*#)?").matcher("");
    private static final Matcher matcherQuota = Pattern.compile("^\\s{0,3}>\\s(.*)").matcher("");
    private static final Matcher matcherUl = Pattern.compile("^\\s*[*+-]\\s+(.*)").matcher("");
    private static final Matcher matcherOl = Pattern.compile("^\\s*\\d+\\.\\s+(.*)").matcher("");
    private static final Matcher matcherItalic = Pattern.compile("[^*_]*(([*_])([^*_].*?)\\2)").matcher("");
    private static final Matcher matcherEm = Pattern.compile("[^*_]*(([*_])\\2([^*_].*?)\\2\\2)").matcher("");
    private static final Matcher matcherEmItalic = Pattern.compile("[^*_]*(([*_])\\2\\2([^*_].*?)\\2\\2\\2)").matcher("");
    private static final Matcher matcherDelete = Pattern.compile("[^~]*((~{2,4})([^~].*?)\\2)").matcher("");
    private static final Matcher matcherCode = Pattern.compile("[^`]*((`+)([^`].*?)\\2)").matcher("");
    private static final Matcher matcherLink = Pattern.compile(".*?(\\[\\s*(.*?)\\s*]\\(\\s*(\\S*?)(\\s+(['\"])(.*?)\\5)?\\s*?\\))").matcher("");
    private static final Matcher matcherImage = Pattern.compile(".*?(!\\[\\s*(.*?)\\s*]\\(\\s*(\\S*?)(\\s+(['\"])(.*?)\\5)?\\s*?\\))").matcher("");
    private static final Matcher matcherLink2 = Pattern.compile(".*?(\\[\\s*(.*?)\\s*]\\s*\\[\\s*(.*?)\\s*])").matcher("");
    private static final Matcher matcherLinkId = Pattern.compile("^\\s*\\[\\s*(.*?)\\s*]:\\s*(\\S+?)(\\s+(['\"])(.*?)\\4)?\\s*$").matcher("");
    private static final Matcher matcherImage2 = Pattern.compile(".*?(!\\[\\s*(.*?)\\s*]\\s*\\[\\s*(.*?)\\s*])").matcher("");
    private static final Matcher matcherImageId = Pattern.compile("^\\s*!\\[\\s*(.*?)\\s*]:\\s*(\\S+?)(\\s+(['\"])(.*?)\\4)?\\s*$").matcher("");
    private static final Matcher matcherEmail = Pattern.compile(".*?(<(\\S+@\\S+\\.\\S+)>).*?").matcher("");
    private static final Matcher matcherAutoLink = Pattern.compile("((https|http|ftp|rtsp|mms)?://)[^\\s]+").matcher("");
    private static final Matcher matcherEndSpace = Pattern.compile("(.*?) {2} *$").matcher("");
    private static final Matcher matcherCodeBlock = Pattern.compile("^( {4}|\\t)(.*)").matcher("");
    private static final Matcher matcherCodeBlock2 = Pattern.compile("^\\s*```").matcher("");
    private static final Matcher matcherBlankLine = Pattern.compile("^\\s*$").matcher("");
    private static final Matcher matcherGap = Pattern.compile("^\\s*([-*]\\s*){3,}$").matcher("");
    private static final SparseArray<Matcher> matchers = new SparseArray<>();

    static {
        Pattern.compile("\\S*(\\s+)\\S+").matcher("");
        matchers.put(1, matcherCodeBlock);
        matchers.put(2, matcherCodeBlock2);
        matchers.put(3, matcherH1);
        matchers.put(4, matcherH2);
        matchers.put(24, matcherH3);
        matchers.put(5, matcherH4);
        matchers.put(6, matcherH5);
        matchers.put(7, matcherH6);
        matchers.put(23, matcherH);
        matchers.put(8, matcherQuota);
        matchers.put(9, matcherUl);
        matchers.put(10, matcherOl);
        matchers.put(11, matcherEm);
        matchers.put(12, matcherItalic);
        matchers.put(13, matcherEmItalic);
        matchers.put(14, matcherEmail);
        matchers.put(15, matcherAutoLink);
        matchers.put(16, matcherDelete);
        matchers.put(17, matcherLink);
        matchers.put(18, matcherLink2);
        matchers.put(19, matcherLinkId);
        matchers.put(20, matcherImage);
        matchers.put(21, matcherImage2);
        matchers.put(22, matcherImageId);
        matchers.put(25, matcherBlankLine);
        matchers.put(26, matcherEndSpace);
        matchers.put(27, matcherGap);
        matchers.put(28, matcherH1_2);
        matchers.put(29, matcherH2_2);
        matchers.put(30, matcherCode);
    }

    public TagHandlerImpl(StyleBuilder styleBuilder) {
        this.styleBuilder = styleBuilder;
    }

    @Override // com.zzhoujay.markdown.parser.TagHandler
    /* renamed from: h */
    public boolean mo133h(Line line) {
        return m127h6(line) || m128h5(line) || m129h4(line) || m130h3(line) || m131h2(line) || m132h1(line);
    }

    /* renamed from: h1 */
    public boolean m132h1(Line line) {
        Matcher obtain = obtain(3, line.getSource());
        if (obtain == null || !obtain.find()) {
            return false;
        }
        line.setType(4);
        line.setStyle(SpannableStringBuilder.valueOf(obtain.group(1)));
        inline(line);
        line.setStyle(this.styleBuilder.mo142h1(line.getStyle()));
        return true;
    }

    /* renamed from: h2 */
    public boolean m131h2(Line line) {
        Matcher obtain = obtain(4, line.getSource());
        if (obtain.find()) {
            line.setType(5);
            line.setStyle(SpannableStringBuilder.valueOf(obtain.group(1)));
            inline(line);
            line.setStyle(this.styleBuilder.mo141h2(line.getStyle()));
            return true;
        }
        return false;
    }

    /* renamed from: h3 */
    public boolean m130h3(Line line) {
        Matcher obtain = obtain(24, line.getSource());
        if (obtain.find()) {
            line.setType(6);
            line.setStyle(SpannableStringBuilder.valueOf(obtain.group(1)));
            inline(line);
            line.setStyle(this.styleBuilder.mo140h3(line.getStyle()));
            return true;
        }
        return false;
    }

    /* renamed from: h4 */
    public boolean m129h4(Line line) {
        Matcher obtain = obtain(5, line.getSource());
        if (obtain.find()) {
            line.setType(7);
            line.setStyle(SpannableStringBuilder.valueOf(obtain.group(1)));
            inline(line);
            line.setStyle(this.styleBuilder.mo139h4(line.getStyle()));
            return true;
        }
        return false;
    }

    /* renamed from: h5 */
    public boolean m128h5(Line line) {
        Matcher obtain = obtain(6, line.getSource());
        if (obtain.find()) {
            line.setType(8);
            line.setStyle(SpannableStringBuilder.valueOf(obtain.group(1)));
            inline(line);
            line.setStyle(this.styleBuilder.mo138h5(line.getStyle()));
            return true;
        }
        return false;
    }

    /* renamed from: h6 */
    public boolean m127h6(Line line) {
        Matcher obtain = obtain(7, line.getSource());
        if (obtain.find()) {
            line.setType(9);
            line.setStyle(SpannableStringBuilder.valueOf(obtain.group(1)));
            inline(line);
            line.setStyle(this.styleBuilder.mo137h6(line.getStyle()));
            return true;
        }
        return false;
    }

    @Override // com.zzhoujay.markdown.parser.TagHandler
    public boolean quota(Line line) {
        LineQueue queue = this.queueProvider.getQueue();
        line.get();
        Matcher obtain = obtain(8, line.getSource());
        if (obtain.find()) {
            line.setType(1);
            Line createChild = line.createChild(obtain.group(1));
            line.attachChildToNext();
            line.attachChildToPrev();
            Line prevLine = queue.prevLine();
            if (line.parentLine() == null && prevLine != null && prevLine.getType() == 1) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(ConstantUtils.PLACEHOLDER_STR_ONE);
                this.styleBuilder.quota(spannableStringBuilder);
                while (prevLine.childLine() != null && prevLine.childLine().getType() == 1) {
                    prevLine = prevLine.childLine();
                    this.styleBuilder.quota(spannableStringBuilder);
                }
                prevLine.copyToNext();
                queue.prevLine().setStyle(spannableStringBuilder);
            }
            if (quota(createChild) || mo124ul(createChild) || mo126ol(createChild) || mo133h(createChild)) {
                if (createChild.getHandle() == 1) {
                    if (line.parentLine() == null) {
                        if (createChild.getData() == 2) {
                            line.setStyle(this.styleBuilder.ul2(createChild.getStyle(), findCount(8, line, 1) - 1, createChild.getAttr()));
                        } else {
                            line.setStyle(this.styleBuilder.ol2(createChild.getStyle(), findCount(8, line, 1) - 1, createChild.getAttr(), createChild.getCount()));
                        }
                    } else {
                        line.setData(createChild.getData());
                        line.setStyle(createChild.getStyle());
                        line.setAttr(createChild.getAttr());
                        line.setCount(createChild.getCount());
                        line.setHandle(1);
                    }
                    return true;
                }
            } else {
                createChild.setStyle(SpannableStringBuilder.valueOf(createChild.getSource()));
                inline(createChild);
            }
            line.setStyle(this.styleBuilder.quota(createChild.getStyle()));
            return true;
        }
        return false;
    }

    @Override // com.zzhoujay.markdown.parser.TagHandler
    /* renamed from: ul */
    public boolean mo124ul(Line line) {
        return m123ul(line, 0);
    }

    /* renamed from: ul */
    private boolean m123ul(Line line, int i) {
        CharSequence source;
        SpannableStringBuilder spannableStringBuilder;
        Matcher obtain = obtain(9, line.getSource());
        boolean z = false;
        if (obtain.find()) {
            line.setType(2);
            Line createChild = line.createChild(obtain.group(1));
            line.setAttr(0);
            Line parentLine = line.parentLine();
            LineQueue queue = this.queueProvider.getQueue();
            Line prevLine = line.prevLine();
            if (queue.currLine().getType() == 1) {
                z = true;
            }
            if (z) {
                line.setHandle(1);
                line.setData(2);
            }
            if (prevLine != null && (prevLine.getType() == 3 || prevLine.getType() == 2)) {
                if (i > 0) {
                    line.setAttr(i);
                } else {
                    String replaceAll = line.getSource().substring(obtain.start(), obtain.start(1) - 2).replaceAll("\\t", "    ");
                    if (replaceAll.length() > (prevLine.getAttr() * 2) + 1) {
                        line.setAttr(prevLine.getAttr() + 1);
                    } else {
                        line.setAttr(replaceAll.length() / 2);
                    }
                }
            }
            if (z) {
                line.setStyle(ConstantUtils.PLACEHOLDER_STR_ONE);
            } else {
                line.setStyle(this.styleBuilder.mo135ul(ConstantUtils.PLACEHOLDER_STR_ONE, line.getAttr()));
            }
            if (find(9, createChild)) {
                int attr = line.getAttr() + 1;
                createChild.unAttachFromParent();
                if (parentLine != null) {
                    Line copyToNext = parentLine.copyToNext();
                    copyToNext.addChild(createChild);
                    queue.next();
                    m123ul(createChild, attr);
                    if (z) {
                        while (copyToNext.parentLine() != null) {
                            copyToNext = copyToNext.parentLine();
                        }
                        copyToNext.setStyle(this.styleBuilder.ul2(createChild.getStyle(), findCount(8, copyToNext, 1) - 1, createChild.getAttr()));
                    } else {
                        while (copyToNext != null && copyToNext.getType() == 1) {
                            copyToNext.setStyle(this.styleBuilder.quota(createChild.getStyle()));
                            copyToNext = copyToNext.parentLine();
                        }
                    }
                } else {
                    line.addNext(createChild);
                    queue.next();
                    m123ul(queue.currLine(), attr);
                }
                return true;
            } else if (find(10, createChild)) {
                int attr2 = line.getAttr() + 1;
                createChild.unAttachFromParent();
                if (parentLine != null) {
                    Line copyToNext2 = parentLine.copyToNext();
                    copyToNext2.addChild(createChild);
                    queue.next();
                    m125ol(createChild, attr2);
                    if (z) {
                        while (copyToNext2.parentLine() != null) {
                            copyToNext2 = copyToNext2.parentLine();
                        }
                        copyToNext2.setStyle(this.styleBuilder.ol2(createChild.getStyle(), findCount(8, copyToNext2, 1) - 1, createChild.getAttr(), createChild.getCount()));
                    } else {
                        while (copyToNext2 != null && copyToNext2.getType() == 1) {
                            copyToNext2.setStyle(this.styleBuilder.quota(createChild.getStyle()));
                            copyToNext2 = copyToNext2.parentLine();
                        }
                    }
                } else {
                    line.addNext(createChild);
                    queue.next();
                    m125ol(queue.currLine(), attr2);
                }
                return true;
            } else {
                if (mo133h(createChild)) {
                    source = createChild.getStyle();
                } else {
                    source = createChild.getSource();
                }
                if (source instanceof SpannableStringBuilder) {
                    spannableStringBuilder = (SpannableStringBuilder) source;
                } else {
                    spannableStringBuilder = new SpannableStringBuilder(source);
                }
                line.setStyle(spannableStringBuilder);
                inline(line);
                if (!z) {
                    line.setStyle(this.styleBuilder.mo135ul(line.getStyle(), line.getAttr()));
                }
                return true;
            }
        }
        return false;
    }

    @Override // com.zzhoujay.markdown.parser.TagHandler
    /* renamed from: ol */
    public boolean mo126ol(Line line) {
        return m125ol(line, 0);
    }

    /* renamed from: ol */
    private boolean m125ol(Line line, int i) {
        CharSequence source;
        SpannableStringBuilder spannableStringBuilder;
        Matcher obtain = obtain(10, line.getSource());
        boolean z = false;
        if (obtain.find()) {
            line.setType(3);
            Line line2 = new Line(obtain.group(1));
            line.setAttr(0);
            Line parentLine = line.parentLine();
            LineQueue queue = this.queueProvider.getQueue();
            Line prevLine = line.prevLine();
            if (queue.currLine().getType() == 1) {
                z = true;
            }
            if (z) {
                line.setHandle(1);
                line.setData(3);
            }
            if (prevLine != null && (prevLine.getType() == 3 || prevLine.getType() == 2)) {
                if (i > 0) {
                    line.setAttr(i);
                } else {
                    String replaceAll = line.getSource().substring(obtain.start(), obtain.start(1) - 2).replaceAll("\\t", "    ");
                    if (replaceAll.length() > (prevLine.getAttr() * 2) + 1) {
                        line.setAttr(prevLine.getAttr() + 1);
                    } else {
                        line.setAttr(replaceAll.length() / 2);
                    }
                }
            }
            if (prevLine != null && prevLine.getType() == 3 && prevLine.getAttr() == line.getAttr()) {
                line.setCount(prevLine.getCount() + 1);
            } else {
                line.setCount(1);
            }
            if (z) {
                line.setStyle(ConstantUtils.PLACEHOLDER_STR_ONE);
            } else {
                line.setStyle(this.styleBuilder.mo136ol(ConstantUtils.PLACEHOLDER_STR_ONE, line.getAttr(), line.getCount()));
            }
            if (find(9, line2)) {
                int attr = line.getAttr() + 1;
                line2.unAttachFromParent();
                if (parentLine != null) {
                    Line copyToNext = parentLine.copyToNext();
                    copyToNext.addChild(line2);
                    queue.next();
                    m123ul(line2, attr);
                    if (z) {
                        while (copyToNext.parentLine() != null) {
                            copyToNext = copyToNext.parentLine();
                        }
                        copyToNext.setStyle(this.styleBuilder.ul2(line2.getStyle(), findCount(8, copyToNext, 1) - 1, line2.getAttr()));
                    } else {
                        while (copyToNext != null && copyToNext.getType() == 1) {
                            copyToNext.setStyle(this.styleBuilder.quota(line2.getStyle()));
                            copyToNext = copyToNext.parentLine();
                        }
                    }
                } else {
                    line.addNext(line2);
                    queue.next();
                    m123ul(queue.currLine(), attr);
                }
                return true;
            } else if (find(10, line2)) {
                int attr2 = line.getAttr() + 1;
                line2.unAttachFromParent();
                if (parentLine != null) {
                    Line copyToNext2 = parentLine.copyToNext();
                    copyToNext2.addChild(line2);
                    queue.next();
                    m125ol(line2, attr2);
                    if (z) {
                        while (copyToNext2.parentLine() != null) {
                            copyToNext2 = copyToNext2.parentLine();
                        }
                        copyToNext2.setStyle(this.styleBuilder.ol2(line2.getStyle(), findCount(8, copyToNext2, 1) - 1, line2.getAttr(), line2.getCount()));
                    } else {
                        while (copyToNext2 != null && copyToNext2.getType() == 1) {
                            copyToNext2.setStyle(this.styleBuilder.quota(line2.getStyle()));
                            copyToNext2 = copyToNext2.parentLine();
                        }
                    }
                } else {
                    line.addNext(line2);
                    queue.next();
                    m125ol(queue.currLine(), attr2);
                }
                return true;
            } else {
                if (mo133h(line2)) {
                    source = line2.getStyle();
                } else {
                    source = line2.getSource();
                }
                if (source instanceof SpannableStringBuilder) {
                    spannableStringBuilder = (SpannableStringBuilder) source;
                } else {
                    spannableStringBuilder = new SpannableStringBuilder(source);
                }
                line.setStyle(spannableStringBuilder);
                inline(line);
                if (!z) {
                    line.setStyle(this.styleBuilder.mo136ol(line.getStyle(), line.getAttr(), line.getCount()));
                }
                return true;
            }
        }
        return false;
    }

    @Override // com.zzhoujay.markdown.parser.TagHandler
    public boolean gap(Line line) {
        line.get();
        if (obtain(27, line.getSource()).matches()) {
            line.setType(12);
            line.setStyle(this.styleBuilder.gap());
            return true;
        }
        return false;
    }

    /* renamed from: em */
    public boolean m134em(Line line) {
        line.get();
        SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) line.getStyle();
        Matcher obtain = obtain(11, spannableStringBuilder);
        while (obtain.find()) {
            if (!checkInCode(spannableStringBuilder, obtain.start(1), obtain.end(1))) {
                spannableStringBuilder.delete(obtain.start(1), obtain.end(1));
                spannableStringBuilder.insert(obtain.start(1), this.styleBuilder.mo144em((SpannableStringBuilder) spannableStringBuilder.subSequence(obtain.start(3), obtain.end(3))));
                m134em(line);
                return true;
            }
        }
        return false;
    }

    public boolean italic(Line line) {
        line.get();
        SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) line.getStyle();
        Matcher obtain = obtain(12, spannableStringBuilder);
        while (obtain.find()) {
            if (!checkInCode(spannableStringBuilder, obtain.start(1), obtain.end(1))) {
                spannableStringBuilder.delete(obtain.start(1), obtain.end(1));
                spannableStringBuilder.insert(obtain.start(1), this.styleBuilder.italic((SpannableStringBuilder) spannableStringBuilder.subSequence(obtain.start(3), obtain.end(3))));
                italic(line);
                return true;
            }
        }
        return false;
    }

    public boolean emItalic(Line line) {
        line.get();
        SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) line.getStyle();
        Matcher obtain = obtain(13, spannableStringBuilder);
        while (obtain.find()) {
            if (!checkInCode(spannableStringBuilder, obtain.start(1), obtain.end(1))) {
                spannableStringBuilder.delete(obtain.start(1), obtain.end(1));
                spannableStringBuilder.insert(obtain.start(1), this.styleBuilder.emItalic((SpannableStringBuilder) spannableStringBuilder.subSequence(obtain.start(3), obtain.end(3))));
                emItalic(line);
                return true;
            }
        }
        return false;
    }

    public boolean code(Line line) {
        line.get();
        SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) line.getStyle();
        Matcher obtain = obtain(30, spannableStringBuilder);
        if (obtain.find()) {
            String group = obtain.group(3);
            spannableStringBuilder.delete(obtain.start(1), obtain.end(1));
            spannableStringBuilder.insert(obtain.start(1), (CharSequence) this.styleBuilder.code(group));
            code(line);
            return true;
        }
        return false;
    }

    public boolean email(Line line) {
        line.get();
        SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) line.getStyle();
        Matcher obtain = obtain(14, spannableStringBuilder);
        if (obtain.find()) {
            spannableStringBuilder.delete(obtain.start(1), obtain.end(1));
            spannableStringBuilder.insert(obtain.start(1), (CharSequence) this.styleBuilder.email((SpannableStringBuilder) spannableStringBuilder.subSequence(obtain.start(2), obtain.end(2))));
            email(line);
            return true;
        }
        return false;
    }

    public boolean delete(Line line) {
        line.get();
        SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) line.getStyle();
        Matcher obtain = obtain(16, spannableStringBuilder);
        while (obtain.find()) {
            if (!checkInCode(spannableStringBuilder, obtain.start(1), obtain.end(1))) {
                spannableStringBuilder.delete(obtain.start(1), obtain.end(1));
                spannableStringBuilder.insert(obtain.start(1), this.styleBuilder.delete((SpannableStringBuilder) spannableStringBuilder.subSequence(obtain.start(3), obtain.end(3))));
                delete(line);
                return true;
            }
        }
        return false;
    }

    public boolean autoLink(Line line) {
        line.get();
        SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) line.getStyle();
        Matcher obtain = obtain(15, spannableStringBuilder);
        boolean z = false;
        while (obtain.find()) {
            String group = obtain.group();
            spannableStringBuilder.delete(obtain.start(), obtain.end());
            spannableStringBuilder.insert(obtain.start(), (CharSequence) this.styleBuilder.link(group, group, ""));
            z = true;
        }
        return z;
    }

    public boolean link(Line line) {
        line.get();
        SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) line.getStyle();
        Matcher obtain = obtain(17, spannableStringBuilder);
        if (obtain.find()) {
            String group = obtain.group(2);
            String group2 = obtain.group(3);
            String group3 = obtain.group(6);
            spannableStringBuilder.delete(obtain.start(1), obtain.end(1));
            spannableStringBuilder.insert(obtain.start(1), (CharSequence) this.styleBuilder.link(group, group2, group3));
            link(line);
            return true;
        }
        return false;
    }

    public boolean link2(Line line) {
        line.get();
        SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) line.getStyle();
        Matcher obtain = obtain(18, spannableStringBuilder);
        if (obtain.find()) {
            String group = obtain.group(2);
            Pair<String, String> pair = this.idLinkLinks.get(obtain.group(3));
            if (pair == null) {
                return false;
            }
            spannableStringBuilder.delete(obtain.start(1), obtain.end(1));
            spannableStringBuilder.insert(obtain.start(1), (CharSequence) this.styleBuilder.link(group, (String) pair.first, (String) pair.second));
            link2(line);
            return true;
        }
        return false;
    }

    @Override // com.zzhoujay.markdown.parser.TagHandler
    public boolean linkId(String str) {
        Matcher obtain = obtain(19, str);
        if (obtain.find()) {
            this.idLinkLinks.put(obtain.group(1), new Pair<>(obtain.group(2), obtain.group(5)));
            return true;
        }
        return false;
    }

    public boolean image(Line line) {
        line.get();
        SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) line.getStyle();
        Matcher obtain = obtain(20, spannableStringBuilder);
        if (obtain.find()) {
            String group = obtain.group(2);
            String group2 = obtain.group(3);
            String group3 = obtain.group(6);
            spannableStringBuilder.delete(obtain.start(1), obtain.end(1));
            spannableStringBuilder.insert(obtain.start(1), (CharSequence) this.styleBuilder.image(group, group2, group3));
            image(line);
            return true;
        }
        return false;
    }

    public boolean image2(Line line) {
        line.get();
        SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) line.getStyle();
        Matcher obtain = obtain(21, spannableStringBuilder);
        if (obtain.find()) {
            String group = obtain.group(2);
            Pair<String, String> pair = this.idImageUrl.get(obtain.group(3));
            if (pair == null) {
                return false;
            }
            spannableStringBuilder.delete(obtain.start(1), obtain.end(1));
            spannableStringBuilder.insert(obtain.start(1), (CharSequence) this.styleBuilder.image(group, (String) pair.first, (String) pair.second));
            image2(line);
            return true;
        }
        return false;
    }

    @Override // com.zzhoujay.markdown.parser.TagHandler
    public boolean imageId(String str) {
        Matcher obtain = obtain(22, str);
        if (obtain.find()) {
            this.idImageUrl.put(obtain.group(1), new Pair<>(obtain.group(2), obtain.group(5)));
            return true;
        }
        return false;
    }

    @Override // com.zzhoujay.markdown.parser.TagHandler
    public boolean codeBlock1(Line line) {
        Matcher obtain = obtain(1, line.getSource());
        if (obtain.find()) {
            String group = obtain.group(2);
            LineQueue queue = this.queueProvider.getQueue();
            StringBuilder sb = new StringBuilder(group);
            StringBuilder sb2 = new StringBuilder();
            for (Line nextLine = queue.nextLine(); nextLine != null; nextLine = queue.nextLine()) {
                CharSequence charSequence = get(1, nextLine, 2);
                if (charSequence == null) {
                    if (!find(25, nextLine)) {
                        break;
                    }
                    sb2.append(' ');
                    sb2.append('\n');
                } else if (sb2.length() != 0) {
                    sb.append((CharSequence) sb2);
                    sb.append(charSequence);
                    sb2.delete(0, sb.length());
                } else {
                    sb.append('\n');
                    sb.append(charSequence);
                }
                queue.removeNextLine();
            }
            line.setType(11);
            line.setStyle(this.styleBuilder.codeBlock(sb.toString()));
            return true;
        }
        return false;
    }

    @Override // com.zzhoujay.markdown.parser.TagHandler
    public boolean codeBlock2(Line line) {
        boolean z;
        if (find(2, line)) {
            LineQueue queue = this.queueProvider.getQueue();
            LineQueue copy = queue.copy();
            while (true) {
                if (copy.nextLine() == null) {
                    z = false;
                    break;
                } else if (find(2, copy.nextLine())) {
                    copy.next();
                    removePrevBlankLine(copy);
                    removeNextBlankLine(queue);
                    z = true;
                    break;
                } else {
                    copy.next();
                }
            }
            if (z) {
                StringBuilder sb = new StringBuilder();
                queue.next();
                queue.removePrevLine();
                while (queue.currLine() != copy.currLine()) {
                    sb.append(queue.currLine().getSource());
                    sb.append('\n');
                    queue.next();
                    queue.removePrevLine();
                }
                removeNextBlankLine(copy);
                copy.currLine().setType(10);
                copy.currLine().setStyle(this.styleBuilder.codeBlock(sb.toString()));
                return true;
            }
        }
        return false;
    }

    @Override // com.zzhoujay.markdown.parser.TagHandler
    public boolean inline(Line line) {
        return autoLink(line) || (link2(line) || (link(line) || (image2(line) || (image(line) || (email(line) || (delete(line) || (italic(line) || (m134em(line) || (emItalic(line) || code(line))))))))));
    }

    @Override // com.zzhoujay.markdown.parser.TagFinder
    public boolean find(int i, Line line) {
        return line != null && find(i, line.getSource());
    }

    @Override // com.zzhoujay.markdown.parser.TagFinder
    public boolean find(int i, String str) {
        Matcher obtain;
        return (str == null || (obtain = obtain(i, str)) == null || !obtain.find()) ? false : true;
    }

    @Override // com.zzhoujay.markdown.parser.TagFinder
    public int findCount(int i, Line line, int i2) {
        if (line == null) {
            return 0;
        }
        return findCount(i, line.getSource(), i2);
    }

    public int findCount(int i, String str, int i2) {
        Matcher obtain;
        if (str == null || (obtain = obtain(i, str)) == null || !obtain.find()) {
            return 0;
        }
        return findCount(i, obtain.group(i2), i2) + 1;
    }

    private boolean checkInCode(SpannableStringBuilder spannableStringBuilder, int i, int i2) {
        CodeSpan[] codeSpanArr;
        for (CodeSpan codeSpan : (CodeSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), CodeSpan.class)) {
            int spanStart = spannableStringBuilder.getSpanStart(codeSpan);
            int spanEnd = spannableStringBuilder.getSpanEnd(codeSpan);
            if (spanStart < i2 && spanEnd > i) {
                return true;
            }
        }
        return false;
    }

    private Matcher obtain(int i, CharSequence charSequence) {
        Matcher matcher = matchers.get(i, null);
        if (matcher != null) {
            matcher.reset(charSequence);
        }
        return matcher;
    }

    @Override // com.zzhoujay.markdown.parser.QueueConsumer
    public void setQueueProvider(QueueConsumer.QueueProvider queueProvider) {
        this.queueProvider = queueProvider;
    }

    public CharSequence get(int i, Line line, int i2) {
        return get(i, line.getSource(), i2);
    }

    public CharSequence get(int i, CharSequence charSequence, int i2) {
        Matcher obtain = obtain(i, charSequence);
        if (obtain.find()) {
            return obtain.group(i2);
        }
        return null;
    }

    private void removeNextBlankLine(LineQueue lineQueue) {
        while (lineQueue.nextLine() != null && find(25, lineQueue.nextLine())) {
            lineQueue.removeNextLine();
        }
    }

    private void removePrevBlankLine(LineQueue lineQueue) {
        while (lineQueue.prevLine() != null && find(25, lineQueue.prevLine())) {
            lineQueue.removePrevLine();
        }
    }
}
