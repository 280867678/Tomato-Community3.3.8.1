package com.mcxtzhang.indexlib.IndexBar.helper;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;
import java.util.List;

/* loaded from: classes3.dex */
public interface IIndexBarDataHelper {
    IIndexBarDataHelper convert(List<? extends BaseIndexPinyinBean> list);

    IIndexBarDataHelper fillInexTag(List<? extends BaseIndexPinyinBean> list);

    IIndexBarDataHelper getSortedIndexDatas(List<? extends BaseIndexPinyinBean> list, List<String> list2);

    IIndexBarDataHelper sortSourceDatas(List<? extends BaseIndexPinyinBean> list);
}
