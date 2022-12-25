package com.mcxtzhang.indexlib.IndexBar.helper;

import com.github.promeg.pinyinhelper.Pinyin;
import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes3.dex */
public class IndexBarDataHelperImpl implements IIndexBarDataHelper {
    @Override // com.mcxtzhang.indexlib.IndexBar.helper.IIndexBarDataHelper
    public IIndexBarDataHelper convert(List<? extends BaseIndexPinyinBean> list) {
        if (list != null && !list.isEmpty()) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                BaseIndexPinyinBean baseIndexPinyinBean = list.get(i);
                StringBuilder sb = new StringBuilder();
                if (baseIndexPinyinBean.isNeedToPinyin()) {
                    String target = baseIndexPinyinBean.getTarget();
                    for (int i2 = 0; i2 < target.length(); i2++) {
                        sb.append(Pinyin.toPinyin(target.charAt(i2)).toUpperCase());
                    }
                    baseIndexPinyinBean.setBaseIndexPinyin(sb.toString());
                }
            }
        }
        return this;
    }

    @Override // com.mcxtzhang.indexlib.IndexBar.helper.IIndexBarDataHelper
    public IIndexBarDataHelper fillInexTag(List<? extends BaseIndexPinyinBean> list) {
        if (list != null && !list.isEmpty()) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                BaseIndexPinyinBean baseIndexPinyinBean = list.get(i);
                if (baseIndexPinyinBean.isNeedToPinyin()) {
                    String substring = baseIndexPinyinBean.getBaseIndexPinyin().toString().substring(0, 1);
                    if (substring.matches("[A-Z]")) {
                        baseIndexPinyinBean.setBaseIndexTag(substring);
                    } else {
                        baseIndexPinyinBean.setBaseIndexTag("#");
                    }
                }
            }
        }
        return this;
    }

    @Override // com.mcxtzhang.indexlib.IndexBar.helper.IIndexBarDataHelper
    public IIndexBarDataHelper sortSourceDatas(List<? extends BaseIndexPinyinBean> list) {
        if (list != null && !list.isEmpty()) {
            convert(list);
            fillInexTag(list);
            Collections.sort(list, new Comparator<BaseIndexPinyinBean>(this) { // from class: com.mcxtzhang.indexlib.IndexBar.helper.IndexBarDataHelperImpl.1
                @Override // java.util.Comparator
                public int compare(BaseIndexPinyinBean baseIndexPinyinBean, BaseIndexPinyinBean baseIndexPinyinBean2) {
                    if (baseIndexPinyinBean.isNeedToPinyin() && baseIndexPinyinBean2.isNeedToPinyin()) {
                        if (baseIndexPinyinBean.getBaseIndexTag().equals("#")) {
                            return 1;
                        }
                        if (!baseIndexPinyinBean2.getBaseIndexTag().equals("#")) {
                            return baseIndexPinyinBean.getBaseIndexPinyin().compareTo(baseIndexPinyinBean2.getBaseIndexPinyin());
                        }
                        return -1;
                    }
                    return 0;
                }
            });
        }
        return this;
    }

    @Override // com.mcxtzhang.indexlib.IndexBar.helper.IIndexBarDataHelper
    public IIndexBarDataHelper getSortedIndexDatas(List<? extends BaseIndexPinyinBean> list, List<String> list2) {
        if (list != null && !list.isEmpty()) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                String baseIndexTag = list.get(i).getBaseIndexTag();
                if (!list2.contains(baseIndexTag)) {
                    list2.add(baseIndexTag);
                }
            }
        }
        return this;
    }
}
