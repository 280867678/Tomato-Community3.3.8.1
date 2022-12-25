package com.tomatolive.library.p136ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$style;
import com.tomatolive.library.model.CountryCodeEntity;
import com.tomatolive.library.model.MenuEntity;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_Divider;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerBuilder;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.SysConfigInfoManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import org.slf4j.Marker;

/* renamed from: com.tomatolive.library.ui.view.dialog.BottomDialogUtils */
/* loaded from: classes3.dex */
public class BottomDialogUtils {

    /* renamed from: com.tomatolive.library.ui.view.dialog.BottomDialogUtils$BottomPromptMenuListener */
    /* loaded from: classes3.dex */
    public interface BottomPromptMenuListener {
        void onCancel();

        void onSure();
    }

    /* renamed from: com.tomatolive.library.ui.view.dialog.BottomDialogUtils$CountryCodeListener */
    /* loaded from: classes3.dex */
    public interface CountryCodeListener {
        void onCountryCodeListener(CountryCodeEntity countryCodeEntity, int i);
    }

    /* renamed from: com.tomatolive.library.ui.view.dialog.BottomDialogUtils$LiveBottomBannedMenuListener */
    /* loaded from: classes3.dex */
    public interface LiveBottomBannedMenuListener {
        void onLiveBottomBannedMenuListener(long j);
    }

    /* renamed from: com.tomatolive.library.ui.view.dialog.BottomDialogUtils$LiveBottomMoreMenuListener */
    /* loaded from: classes3.dex */
    public interface LiveBottomMoreMenuListener {
        void onLiveBottomLotteryMenuListener(BaseQuickAdapter<MenuEntity, BaseViewHolder> baseQuickAdapter, MenuEntity menuEntity, int i);

        void onLiveBottomMoreMenuListener(BaseQuickAdapter<MenuEntity, BaseViewHolder> baseQuickAdapter, MenuEntity menuEntity, int i);
    }

    /* renamed from: com.tomatolive.library.ui.view.dialog.BottomDialogUtils$OnSpeakSettingListener */
    /* loaded from: classes3.dex */
    public interface OnSpeakSettingListener {
        void speakSettingDone(boolean z, String str, String str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getBanndDuration(int i) {
        switch (i) {
            case 0:
                return 900L;
            case 1:
                return 1800L;
            case 2:
                return 3600L;
            case 3:
                return 28800L;
            case 4:
                return 86400L;
            case 5:
                return 259200L;
            case 6:
                return 604800L;
            case 7:
                return 2592000L;
            default:
                return -1L;
        }
    }

    private BottomDialogUtils() {
    }

    public static Dialog getLiveBottomDialog(Context context, boolean z, final LiveBottomMoreMenuListener liveBottomMoreMenuListener) {
        final Dialog dialog = new Dialog(context, R$style.ActionSheet);
        View inflate = LayoutInflater.from(context).inflate(R$layout.fq_layout_bottom_menu_view, (ViewGroup) new LinearLayout(context), false);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R$id.rv_operate);
        RecyclerView recyclerView2 = (RecyclerView) inflate.findViewById(R$id.rv_lottery);
        View findViewById = inflate.findViewById(R$id.v_divider);
        LiveBottomMenuAdapter liveBottomMenuAdapter = new LiveBottomMenuAdapter(R$layout.fq_item_bottom_menu_view, getMenuList(context, z));
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        recyclerView.addItemDecoration(new RVDividerGrid(context, 17170445));
        recyclerView.setAdapter(liveBottomMenuAdapter);
        liveBottomMenuAdapter.bindToRecyclerView(recyclerView);
        liveBottomMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$BottomDialogUtils$B1HMJ1OAjcHNqsZXOPvRXcCiD58
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BottomDialogUtils.lambda$getLiveBottomDialog$0(dialog, liveBottomMoreMenuListener, baseQuickAdapter, view, i);
            }
        });
        List<MenuEntity> lotteryMenuList = getLotteryMenuList(context);
        LiveBottomMenuAdapter liveBottomMenuAdapter2 = new LiveBottomMenuAdapter(R$layout.fq_item_bottom_menu_view, lotteryMenuList);
        recyclerView2.setLayoutManager(new GridLayoutManager(context, 4));
        recyclerView2.addItemDecoration(new RVDividerGrid(context, 17170445));
        recyclerView2.setAdapter(liveBottomMenuAdapter2);
        liveBottomMenuAdapter2.bindToRecyclerView(recyclerView2);
        liveBottomMenuAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$BottomDialogUtils$yKJuCYPaFSiLlvqh3yenfEC5VIo
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BottomDialogUtils.lambda$getLiveBottomDialog$1(dialog, liveBottomMoreMenuListener, baseQuickAdapter, view, i);
            }
        });
        if (lotteryMenuList == null || lotteryMenuList.isEmpty()) {
            recyclerView2.setVisibility(8);
            findViewById.setVisibility(8);
        } else {
            recyclerView2.setVisibility(0);
            findViewById.setVisibility(0);
        }
        Window window = dialog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams attributes = window.getAttributes();
            if (attributes != null) {
                attributes.height = -2;
                attributes.width = -1;
                attributes.dimAmount = 0.0f;
                attributes.gravity = 80;
                window.setAttributes(attributes);
            }
        }
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(inflate);
        return dialog;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$getLiveBottomDialog$0(Dialog dialog, LiveBottomMoreMenuListener liveBottomMoreMenuListener, BaseQuickAdapter baseQuickAdapter, View view, int i) {
        dialog.dismiss();
        MenuEntity menuEntity = (MenuEntity) baseQuickAdapter.getItem(i);
        if (menuEntity == null) {
            return;
        }
        menuEntity.isSelected = !menuEntity.isSelected;
        baseQuickAdapter.setData(i, menuEntity);
        if (liveBottomMoreMenuListener == null) {
            return;
        }
        liveBottomMoreMenuListener.onLiveBottomMoreMenuListener(baseQuickAdapter, menuEntity, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$getLiveBottomDialog$1(Dialog dialog, LiveBottomMoreMenuListener liveBottomMoreMenuListener, BaseQuickAdapter baseQuickAdapter, View view, int i) {
        dialog.dismiss();
        MenuEntity menuEntity = (MenuEntity) baseQuickAdapter.getItem(i);
        if (menuEntity == null) {
            return;
        }
        baseQuickAdapter.setData(i, menuEntity);
        if (liveBottomMoreMenuListener == null) {
            return;
        }
        liveBottomMoreMenuListener.onLiveBottomLotteryMenuListener(baseQuickAdapter, menuEntity, i);
    }

    private static List<MenuEntity> getMenuList(Context context, boolean z) {
        ArrayList arrayList = new ArrayList();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        String[] stringArray = context.getResources().getStringArray(R$array.live_more_menu_title);
        int[] iArr = {R$drawable.fq_icon_beauty_selector, R$drawable.fq_icon_microphone_selector, R$drawable.fq_icon_flashlight_selector, R$drawable.fq_icon_big_size_selector, R$drawable.fq_icon_mirror_black_selector, R$drawable.fq_icon_speak_setting_selector, R$drawable.fq_icon_modify_theme_selector, R$drawable.fq_icon_slogan_selector, R$drawable.fq_icon_gift_close_selector, R$drawable.fq_icon_popular_selector, R$drawable.fq_ic_anchor_pay};
        int[] iArr2 = {0, 1, 2, 3, 4, 5, 6, 7, 10, 8, 12};
        for (int i = 0; i < iArr.length; i++) {
            MenuEntity menuEntity = new MenuEntity();
            menuEntity.setMenuTitle(stringArray[i]);
            menuEntity.setMenuIcon(iArr[i]);
            menuEntity.setMenuType(iArr2[i]);
            if (iArr[i] == R$drawable.fq_icon_mirror_black_selector) {
                menuEntity.setSelected(z);
            }
            linkedHashMap.put(Integer.valueOf(iArr2[i]), menuEntity);
        }
        if (!SysConfigInfoManager.getInstance().isEnableSticker()) {
            linkedHashMap.remove(7);
        }
        Collection values = linkedHashMap.values();
        arrayList.clear();
        arrayList.addAll(values);
        return arrayList;
    }

    private static List<MenuEntity> getLotteryMenuList(Context context) {
        ArrayList arrayList = new ArrayList();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        String[] stringArray = context.getResources().getStringArray(R$array.live_more_menu_lottery_title);
        int[] iArr = {R$drawable.fq_ic_qm_menu};
        int[] iArr2 = {13};
        for (int i = 0; i < iArr.length; i++) {
            MenuEntity menuEntity = new MenuEntity();
            menuEntity.setMenuTitle(stringArray[i]);
            menuEntity.setMenuIcon(iArr[i]);
            menuEntity.setMenuType(iArr2[i]);
            linkedHashMap.put(Integer.valueOf(iArr2[i]), menuEntity);
        }
        if (!SysConfigInfoManager.getInstance().isEnableQMInteract()) {
            linkedHashMap.remove(13);
        }
        Collection values = linkedHashMap.values();
        arrayList.clear();
        arrayList.addAll(values);
        return arrayList;
    }

    public static void showBottomSpeakSettingDialog(Context context, String str, String str2, boolean z, final OnSpeakSettingListener onSpeakSettingListener) {
        final HideSoftInputDialog hideSoftInputDialog = new HideSoftInputDialog(context, R$style.ActionSheet);
        View inflate = LayoutInflater.from(context).inflate(R$layout.layout_bottom_speak_setting, (ViewGroup) new LinearLayout(context), false);
        final EditText editText = (EditText) inflate.findViewById(R$id.et_time_limit);
        final EditText editText2 = (EditText) inflate.findViewById(R$id.et_level);
        editText2.setText(str2);
        editText.setText(str);
        editText2.addTextChangedListener(inputWatch(editText2));
        final CheckBox checkBox = (CheckBox) inflate.findViewById(R$id.cb_banned);
        checkBox.setChecked(z);
        inflate.findViewById(R$id.iv_done).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.BottomDialogUtils.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                HideSoftInputDialog.this.dismiss();
                if (onSpeakSettingListener != null) {
                    String obj = editText.getEditableText().toString();
                    if (TextUtils.isEmpty(obj)) {
                        obj = "1";
                    }
                    if (obj.length() == 2 && obj.startsWith("0")) {
                        obj = obj.substring(1, 2);
                    }
                    String obj2 = editText2.getEditableText().toString();
                    if (TextUtils.isEmpty(obj2)) {
                        obj2 = "1";
                    }
                    if (obj2.length() == 2 && obj2.startsWith("0")) {
                        obj2 = obj2.substring(1, 2);
                    }
                    onSpeakSettingListener.speakSettingDone(checkBox.isChecked(), obj, obj2);
                }
            }
        });
        Window window = hideSoftInputDialog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams attributes = window.getAttributes();
            if (attributes != null) {
                attributes.height = -2;
                attributes.width = -1;
                attributes.dimAmount = 0.5f;
                attributes.gravity = 80;
                window.setAttributes(attributes);
            }
        }
        hideSoftInputDialog.setCanceledOnTouchOutside(true);
        hideSoftInputDialog.setContentView(inflate);
        hideSoftInputDialog.show();
    }

    private static TextWatcher inputWatch(final EditText editText) {
        return new TextWatcher() { // from class: com.tomatolive.library.ui.view.dialog.BottomDialogUtils.2
            private String outStr = "";

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                String charSequence2 = charSequence.toString();
                if (charSequence2.length() != 1 || Integer.parseInt(charSequence2) < 6) {
                    return;
                }
                this.outStr = charSequence2;
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (NumberUtils.string2int(editable.toString()) > 60) {
                    editText.setText(this.outStr);
                    editText.setSelection(this.outStr.length());
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.dialog.BottomDialogUtils$LiveBottomMenuAdapter */
    /* loaded from: classes3.dex */
    public static class LiveBottomMenuAdapter extends BaseQuickAdapter<MenuEntity, BaseViewHolder> {
        private LiveBottomMenuAdapter(int i, @Nullable List<MenuEntity> list) {
            super(i, list);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder baseViewHolder, MenuEntity menuEntity) {
            baseViewHolder.setImageResource(R$id.iv_menu_icon, menuEntity.menuIcon).setText(R$id.tv_menu_title, menuEntity.menuTitle).getView(R$id.iv_menu_icon).setSelected(menuEntity.isSelected);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.dialog.BottomDialogUtils$RVDividerGrid */
    /* loaded from: classes3.dex */
    public static class RVDividerGrid extends Y_DividerItemDecoration {
        private final int colorRes;
        private final Context context;

        private RVDividerGrid(Context context, @ColorRes int i) {
            super(context);
            this.context = context;
            this.colorRes = i;
        }

        @Override // com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration
        public Y_Divider getDivider(int i) {
            return new Y_DividerBuilder().setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 20.0f, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 20.0f, 0.0f, 0.0f).create();
        }
    }

    public static void showBannedDialog(Context context, final LiveBottomBannedMenuListener liveBottomBannedMenuListener) {
        final Dialog dialog = new Dialog(context, R$style.ActionSheet);
        View inflate = LayoutInflater.from(context).inflate(R$layout.fq_dialog_live_banned, (ViewGroup) new LinearLayout(context), false);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R$id.rv_operate);
        final BannedAdapter bannedAdapter = new BannedAdapter(R$layout.fq_item_list_banned_view, getBannedMenuList(context));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        bannedAdapter.bindToRecyclerView(recyclerView);
        recyclerView.setAdapter(bannedAdapter);
        bannedAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.BottomDialogUtils.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BannedAdapter.this.setSelectedPosition(i);
            }
        });
        inflate.findViewById(R$id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.BottomDialogUtils.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        inflate.findViewById(R$id.tv_submit).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.BottomDialogUtils.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MenuEntity selectedItem = BannedAdapter.this.getSelectedItem();
                if (selectedItem == null) {
                    return;
                }
                dialog.dismiss();
                LiveBottomBannedMenuListener liveBottomBannedMenuListener2 = liveBottomBannedMenuListener;
                if (liveBottomBannedMenuListener2 == null) {
                    return;
                }
                liveBottomBannedMenuListener2.onLiveBottomBannedMenuListener(BottomDialogUtils.getBanndDuration(selectedItem.position));
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams attributes = window.getAttributes();
            if (attributes != null) {
                attributes.height = -2;
                attributes.width = -1;
                attributes.dimAmount = 0.5f;
                attributes.gravity = 80;
                window.setAttributes(attributes);
            }
        }
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(inflate);
        dialog.show();
    }

    public static Dialog showPhoneCountryCodeDialog(Context context, List<CountryCodeEntity> list, final CountryCodeListener countryCodeListener) {
        final Dialog dialog = new Dialog(context, R$style.ActionSheet);
        View inflate = LayoutInflater.from(context).inflate(R$layout.fq_dialog_country_code, (ViewGroup) new LinearLayout(context), false);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R$id.rv_operate);
        final CountryCodeAdapter countryCodeAdapter = new CountryCodeAdapter(R$layout.fq_item_list_banned_view, list);
        inflate.findViewById(R$id.tv_dialog_title).setVisibility(4);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        countryCodeAdapter.bindToRecyclerView(recyclerView);
        recyclerView.setAdapter(countryCodeAdapter);
        countryCodeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.BottomDialogUtils.6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                CountryCodeAdapter.this.setSelectedPosition(i);
            }
        });
        inflate.findViewById(R$id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.BottomDialogUtils.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        inflate.findViewById(R$id.tv_submit).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.BottomDialogUtils.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CountryCodeEntity selectedItem = CountryCodeAdapter.this.getSelectedItem();
                if (selectedItem == null) {
                    return;
                }
                dialog.dismiss();
                CountryCodeListener countryCodeListener2 = countryCodeListener;
                if (countryCodeListener2 == null) {
                    return;
                }
                countryCodeListener2.onCountryCodeListener(selectedItem, CountryCodeAdapter.this.getSelectedPosition());
            }
        });
        Window window = dialog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams attributes = window.getAttributes();
            if (attributes != null) {
                attributes.height = -2;
                attributes.width = -1;
                attributes.dimAmount = 0.5f;
                attributes.gravity = 80;
                window.setAttributes(attributes);
            }
        }
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(inflate);
        dialog.show();
        return dialog;
    }

    private static List<MenuEntity> getBannedMenuList(Context context) {
        ArrayList arrayList = new ArrayList();
        String[] stringArray = context.getResources().getStringArray(R$array.banned_text);
        for (int i = 0; i < stringArray.length; i++) {
            MenuEntity menuEntity = new MenuEntity();
            menuEntity.menuTitle = stringArray[i];
            menuEntity.position = i;
            arrayList.add(menuEntity);
        }
        return arrayList;
    }

    private static List<MenuEntity> getPhoneCountryCodeMenuList(Context context) {
        ArrayList arrayList = new ArrayList();
        String[] stringArray = context.getResources().getStringArray(R$array.phone_country_code_key);
        String[] stringArray2 = context.getResources().getStringArray(R$array.phone_country_code_value);
        for (int i = 0; i < stringArray2.length; i++) {
            arrayList.add(new MenuEntity(stringArray2[i], stringArray[i]));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.dialog.BottomDialogUtils$BannedAdapter */
    /* loaded from: classes3.dex */
    public static class BannedAdapter extends BaseQuickAdapter<MenuEntity, BaseViewHolder> {
        private int selectedPosition;

        private BannedAdapter(int i, @Nullable List<MenuEntity> list) {
            super(i, list);
            this.selectedPosition = -1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSelectedPosition(int i) {
            this.selectedPosition = i;
            notifyDataSetChanged();
        }

        public int getSelectedPosition() {
            return this.selectedPosition;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public MenuEntity getSelectedItem() {
            return getItem(this.selectedPosition);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder baseViewHolder, MenuEntity menuEntity) {
            boolean z = this.selectedPosition == baseViewHolder.getAdapterPosition();
            baseViewHolder.setText(R$id.tv_item_title, menuEntity.menuTitle).setBackgroundColor(R$id.tv_item_title, ContextCompat.getColor(this.mContext, z ? R$color.fq_list_item_bg_pressed : R$color.fq_list_item_bg_normal)).setTextColor(R$id.tv_item_title, ContextCompat.getColor(this.mContext, z ? R$color.fq_colorPrimary : R$color.fq_text_black));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.dialog.BottomDialogUtils$CountryCodeAdapter */
    /* loaded from: classes3.dex */
    public static class CountryCodeAdapter extends BaseQuickAdapter<CountryCodeEntity, BaseViewHolder> {
        private int selectedPosition;

        private CountryCodeAdapter(int i, @Nullable List<CountryCodeEntity> list) {
            super(i, list);
            this.selectedPosition = -1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSelectedPosition(int i) {
            this.selectedPosition = i;
            notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getSelectedPosition() {
            return this.selectedPosition;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public CountryCodeEntity getSelectedItem() {
            return getItem(this.selectedPosition);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder baseViewHolder, CountryCodeEntity countryCodeEntity) {
            boolean z = this.selectedPosition == baseViewHolder.getAdapterPosition();
            baseViewHolder.setText(R$id.tv_item_title, getCodeStr(countryCodeEntity)).setBackgroundColor(R$id.tv_item_title, ContextCompat.getColor(this.mContext, z ? R$color.fq_list_item_bg_pressed : R$color.fq_list_item_bg_normal)).setTextColor(R$id.tv_item_title, ContextCompat.getColor(this.mContext, z ? R$color.fq_colorPrimary : R$color.fq_text_black));
        }

        private String getCodeStr(CountryCodeEntity countryCodeEntity) {
            return countryCodeEntity.countryName + "(" + Marker.ANY_NON_NULL_MARKER + countryCodeEntity.countryCode + ")";
        }
    }
}
