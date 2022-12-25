package com.one.tomato.thirdpart.emotion;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.p096sj.emoji.EmojiBean;
import com.p096sj.emoji.EmojiDisplay;
import java.util.ArrayList;
import java.util.Map;
import sj.keyboard.adpater.EmoticonsAdapter;
import sj.keyboard.adpater.PageSetAdapter;
import sj.keyboard.data.EmoticonPageEntity;
import sj.keyboard.data.EmoticonPageSetEntity;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.interfaces.EmoticonDisplayListener;
import sj.keyboard.interfaces.PageViewInstantiateListener;
import sj.keyboard.utils.EmoticonsKeyboardUtils;
import sj.keyboard.widget.EmoticonPageView;

/* loaded from: classes3.dex */
public class EmotionUtil {
    public static int EMOTICON_CLICK_TEXT = 1;
    public static PageSetAdapter sCommonPageSetAdapter;

    public static PageSetAdapter getCommonAdapter(Context context, EmoticonClickListener emoticonClickListener) {
        PageSetAdapter pageSetAdapter = sCommonPageSetAdapter;
        if (pageSetAdapter != null) {
            return pageSetAdapter;
        }
        PageSetAdapter pageSetAdapter2 = new PageSetAdapter();
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, Integer> entry : EmotionArray.pageMap1.entrySet()) {
            arrayList.add(new EmojiBean(entry.getValue().intValue(), entry.getKey()));
        }
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry<String, Integer> entry2 : EmotionArray.pageMap2.entrySet()) {
            arrayList2.add(new EmojiBean(entry2.getValue().intValue(), entry2.getKey()));
        }
        ArrayList arrayList3 = new ArrayList();
        for (Map.Entry<String, Integer> entry3 : EmotionArray.pageMap3.entrySet()) {
            arrayList3.add(new EmojiBean(entry3.getValue().intValue(), entry3.getKey()));
        }
        ArrayList arrayList4 = new ArrayList();
        for (Map.Entry<String, Integer> entry4 : EmotionArray.pageMap4.entrySet()) {
            arrayList4.add(new EmojiBean(entry4.getValue().intValue(), entry4.getKey()));
        }
        addPage(pageSetAdapter2, arrayList, emoticonClickListener, R.drawable.a_1);
        addPage(pageSetAdapter2, arrayList2, emoticonClickListener, R.drawable.b_1);
        addPage(pageSetAdapter2, arrayList3, emoticonClickListener, R.drawable.c_1);
        addPage(pageSetAdapter2, arrayList4, emoticonClickListener, R.drawable.d_1);
        return pageSetAdapter2;
    }

    public static void addPage(PageSetAdapter pageSetAdapter, ArrayList<EmojiBean> arrayList, final EmoticonClickListener emoticonClickListener, int i) {
        pageSetAdapter.add(new EmoticonPageSetEntity.Builder().setLine(3).setRow(7).setEmoticonList(arrayList).setIPageViewInstantiateItem(getDefaultEmoticonPageViewInstantiateItem(new EmoticonDisplayListener<Object>() { // from class: com.one.tomato.thirdpart.emotion.EmotionUtil.1
            @Override // sj.keyboard.interfaces.EmoticonDisplayListener
            public void onBindView(int i2, ViewGroup viewGroup, EmoticonsAdapter.ViewHolder viewHolder, Object obj, final boolean z) {
                final EmojiBean emojiBean = (EmojiBean) obj;
                if (emojiBean != null || z) {
                    viewHolder.ly_root.setBackgroundResource(R.drawable.bg_emoticon);
                    if (z) {
                        viewHolder.iv_emoticon.setImageResource(R.drawable.icon_deletes);
                    } else {
                        viewHolder.iv_emoticon.setImageResource(emojiBean.icon);
                    }
                    viewHolder.rootView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.thirdpart.emotion.EmotionUtil.1.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            EmoticonClickListener emoticonClickListener2 = EmoticonClickListener.this;
                            if (emoticonClickListener2 != null) {
                                emoticonClickListener2.onEmoticonClick(emojiBean, EmotionUtil.EMOTICON_CLICK_TEXT, z);
                            }
                        }
                    });
                }
            }
        })).setShowDelBtn(EmoticonPageEntity.DelBtnStatus.LAST).mo6884setIconUri(i).mo6883build());
    }

    public static Object newInstance(Class cls, Object... objArr) throws Exception {
        return newInstance(cls, 0, objArr);
    }

    public static Object newInstance(Class cls, int i, Object... objArr) throws Exception {
        return cls.getConstructors()[i].newInstance(objArr);
    }

    public static PageViewInstantiateListener<EmoticonPageEntity> getDefaultEmoticonPageViewInstantiateItem(EmoticonDisplayListener<Object> emoticonDisplayListener) {
        return getEmoticonPageViewInstantiateItem(EmoticonsAdapter.class, null, emoticonDisplayListener);
    }

    public static PageViewInstantiateListener<EmoticonPageEntity> getEmoticonPageViewInstantiateItem(Class cls, EmoticonClickListener emoticonClickListener) {
        return getEmoticonPageViewInstantiateItem(cls, emoticonClickListener, null);
    }

    public static PageViewInstantiateListener<EmoticonPageEntity> getEmoticonPageViewInstantiateItem(final Class cls, final EmoticonClickListener emoticonClickListener, final EmoticonDisplayListener<Object> emoticonDisplayListener) {
        return new PageViewInstantiateListener<EmoticonPageEntity>() { // from class: com.one.tomato.thirdpart.emotion.EmotionUtil.2
            @Override // sj.keyboard.interfaces.PageViewInstantiateListener
            public View instantiateItem(ViewGroup viewGroup, int i, EmoticonPageEntity emoticonPageEntity) {
                if (emoticonPageEntity.getRootView() == null) {
                    EmoticonPageView emoticonPageView = new EmoticonPageView(viewGroup.getContext());
                    emoticonPageView.setNumColumns(emoticonPageEntity.getRow());
                    emoticonPageEntity.setRootView(emoticonPageView);
                    try {
                        EmoticonsAdapter emoticonsAdapter = (EmoticonsAdapter) EmotionUtil.newInstance(cls, viewGroup.getContext(), emoticonPageEntity, emoticonClickListener);
                        if (emoticonDisplayListener != null) {
                            emoticonsAdapter.setOnDisPlayListener(emoticonDisplayListener);
                        }
                        emoticonPageView.getEmoticonsGridView().setAdapter((ListAdapter) emoticonsAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return emoticonPageEntity.getRootView();
            }
        };
    }

    public static void delClick(EditText editText) {
        editText.onKeyDown(67, new KeyEvent(0, 67));
    }

    public static void spannableEmoticonFilter(TextView textView, String str) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        EmojiDisplay.spannableFilter(textView.getContext(), spannableStringBuilder, str, EmoticonsKeyboardUtils.getFontHeight(textView));
        textView.setText(EmotionEditFilter.spannableFilter(textView.getContext(), spannableStringBuilder, str, EmoticonsKeyboardUtils.getFontHeight(textView), null));
    }
}
