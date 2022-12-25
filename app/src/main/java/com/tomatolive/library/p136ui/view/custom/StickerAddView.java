package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.blankj.utilcode.util.ToastUtils;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.p135db.StickerEntity;
import com.tomatolive.library.p136ui.view.custom.StickerAddView;
import com.tomatolive.library.p136ui.view.dialog.StickerEditTipsDialog;
import com.tomatolive.library.p136ui.view.dialog.StickerHelpTipsDialog;
import com.tomatolive.library.p136ui.view.sticker.IMGTextEditDialog;
import com.tomatolive.library.p136ui.view.sticker.core.IMGText;
import com.tomatolive.library.p136ui.view.sticker.view.IMGStickerTextView;
import com.tomatolive.library.p136ui.view.sticker.view.IMGView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.DBUtils;
import com.tomatolive.library.utils.IOUtils;
import com.tomatolive.library.utils.RxViewUtils;
import com.tomatolive.library.utils.StringUtils;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.custom.StickerAddView */
/* loaded from: classes3.dex */
public class StickerAddView extends RelativeLayout {
    private Context mContext;
    private FragmentManager mFragmentManager;
    private IMGTextEditDialog mImgTextEditDialog;
    private IMGView mStickerShowView;
    private OnAddStickerCallback onAddStickerCallback;

    /* renamed from: com.tomatolive.library.ui.view.custom.StickerAddView$OnAddStickerCallback */
    /* loaded from: classes3.dex */
    public interface OnAddStickerCallback {
        void onSaveStickerClick();
    }

    public StickerAddView(Context context) {
        this(context, null);
    }

    public StickerAddView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView();
    }

    private void initView() {
        setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_sticker_bg));
        RelativeLayout.inflate(this.mContext, R$layout.fq_layout_sticker_add_view, this);
        addStickerView();
        this.mImgTextEditDialog = new IMGTextEditDialog(this.mContext, new IMGTextEditDialog.Callback() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$StickerAddView$gBikn53EvhY9J_MmAEeYehRLjcc
            @Override // com.tomatolive.library.p136ui.view.sticker.IMGTextEditDialog.Callback
            public final void onText(IMGText iMGText) {
                StickerAddView.this.lambda$initView$0$StickerAddView(iMGText);
            }
        });
        addLastTextList(DBUtils.getStickerList());
        initListener();
    }

    public /* synthetic */ void lambda$initView$0$StickerAddView(IMGText iMGText) {
        iMGText.setId(StringUtils.getUUID());
        this.mStickerShowView.addStickerText(iMGText);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addStickerView() {
        this.mStickerShowView = new IMGView(this.mContext);
        this.mStickerShowView.setBackgroundColor(0);
        addView(this.mStickerShowView, 0, new RelativeLayout.LayoutParams(-1, -1));
    }

    private void initListener() {
        RxViewUtils.getInstance().throttleFirst(findViewById(R$id.iv_sticker_back), 800, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$StickerAddView$q3wrxbEUKUDSve3avYi9jG45xkI
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                StickerAddView.this.lambda$initListener$1$StickerAddView(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(findViewById(R$id.tv_sticker_save), 800, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$StickerAddView$o_V978VojdWdeggrh5uwfuTbMpc
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                StickerAddView.this.lambda$initListener$2$StickerAddView(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(findViewById(R$id.tv_sticker_help), 800, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$StickerAddView$TCuZhNxUp8tIECP73V1SHnJMNPw
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                StickerAddView.this.lambda$initListener$3$StickerAddView(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(findViewById(R$id.rl_sticker_add_text), 800, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$StickerAddView$WKlRA8WBKn9N8PiW3y-gTjJ5nh4
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                StickerAddView.this.lambda$initListener$4$StickerAddView(obj);
            }
        });
        this.mImgTextEditDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$StickerAddView$i1m_4SmRYtbZYsj6i0YW-QTMPLo
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                StickerAddView.this.lambda$initListener$5$StickerAddView(dialogInterface);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$1$StickerAddView(Object obj) {
        onBackListener();
    }

    public /* synthetic */ void lambda$initListener$2$StickerAddView(Object obj) {
        if (this.onAddStickerCallback != null) {
            Observable.just(getStickerWaterImgPath()).subscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new C44891());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.view.custom.StickerAddView$1 */
    /* loaded from: classes3.dex */
    public class C44891 extends SimpleRxObserver<String> {
        C44891() {
        }

        @Override // com.tomatolive.library.utils.live.SimpleRxObserver
        public void accept(String str) {
            StickerAddView.this.mStickerShowView.post(new Runnable() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$StickerAddView$1$FpljAqQM41Ur-cjDDvTGjsQt29s
                @Override // java.lang.Runnable
                public final void run() {
                    StickerAddView.C44891.this.lambda$accept$0$StickerAddView$1();
                }
            });
            StickerAddView.this.onAddStickerCallback.onSaveStickerClick();
            ArrayList arrayList = new ArrayList();
            int childCount = StickerAddView.this.mStickerShowView.getChildCount();
            if (childCount == 0) {
                DBUtils.deleteStickerList();
                return;
            }
            for (int i = 0; i < childCount; i++) {
                View childAt = StickerAddView.this.mStickerShowView.getChildAt(i);
                if (childAt instanceof IMGStickerTextView) {
                    IMGStickerTextView iMGStickerTextView = (IMGStickerTextView) childAt;
                    arrayList.add(new StickerEntity(iMGStickerTextView.getText().getId(), UserInfoManager.getInstance().getUserId(), childAt.getTranslationX(), childAt.getTranslationY(), iMGStickerTextView.getScale(), childAt.getRotation(), iMGStickerTextView.getText().getColor(), iMGStickerTextView.getText().getText()));
                }
            }
            DBUtils.saveStickerList(arrayList);
        }

        public /* synthetic */ void lambda$accept$0$StickerAddView$1() {
            StickerAddView.this.setVisibility(4);
        }
    }

    public /* synthetic */ void lambda$initListener$3$StickerAddView(Object obj) {
        StickerHelpTipsDialog.newInstance().show(this.mFragmentManager);
    }

    public /* synthetic */ void lambda$initListener$4$StickerAddView(Object obj) {
        if (this.mStickerShowView.getChildCount() >= 5) {
            ToastUtils.showShort(this.mContext.getString(R$string.fq_sticker_add_max_tips));
            return;
        }
        IMGTextEditDialog iMGTextEditDialog = this.mImgTextEditDialog;
        if (iMGTextEditDialog == null) {
            return;
        }
        iMGTextEditDialog.show();
        setVisibility(4);
    }

    public /* synthetic */ void lambda$initListener$5$StickerAddView(DialogInterface dialogInterface) {
        setVisibility(0);
    }

    public void initData(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    public void onBackListener() {
        Observable.just(Boolean.valueOf(isStickerEdit())).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new C44902());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.view.custom.StickerAddView$2 */
    /* loaded from: classes3.dex */
    public class C44902 extends SimpleRxObserver<Boolean> {
        C44902() {
        }

        @Override // com.tomatolive.library.utils.live.SimpleRxObserver
        public void accept(Boolean bool) {
            if (bool.booleanValue()) {
                StickerEditTipsDialog.newInstance(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$StickerAddView$2$-jDJGxbFELtdqSzo-LiOX4WCeTk
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        StickerAddView.C44902.this.lambda$accept$0$StickerAddView$2(view);
                    }
                }).show(StickerAddView.this.mFragmentManager);
                return;
            }
            StickerAddView.this.setVisibility(4);
            StickerAddView.this.onAddStickerCallback.onSaveStickerClick();
        }

        public /* synthetic */ void lambda$accept$0$StickerAddView$2(View view) {
            StickerAddView.this.removeViewAt(0);
            StickerAddView.this.mStickerShowView = null;
            StickerAddView.this.setVisibility(4);
            StickerAddView.this.addStickerView();
            StickerAddView.this.addLastTextList(DBUtils.getStickerList());
            StickerAddView.this.onAddStickerCallback.onSaveStickerClick();
        }
    }

    public OnAddStickerCallback getOnAddStickerCallback() {
        return this.onAddStickerCallback;
    }

    public void setOnAddStickerCallback(OnAddStickerCallback onAddStickerCallback) {
        this.onAddStickerCallback = onAddStickerCallback;
    }

    public void addLastTextList(List<StickerEntity> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (StickerEntity stickerEntity : list) {
            addLastText(stickerEntity);
        }
    }

    public void addLastText(StickerEntity stickerEntity) {
        this.mStickerShowView.addLastStickerText(new IMGText(stickerEntity.uuID, stickerEntity.text, stickerEntity.color), stickerEntity);
    }

    private boolean isStickerEdit() {
        int childCount = this.mStickerShowView.getChildCount();
        if (childCount != DBUtils.getStickerListCount()) {
            return true;
        }
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mStickerShowView.getChildAt(i);
            boolean z = childAt instanceof IMGStickerTextView;
            if (z && ((IMGStickerTextView) childAt).isShowing()) {
                return true;
            }
            if (z && isEdit((IMGStickerTextView) childAt)) {
                return true;
            }
        }
        return false;
    }

    private boolean isEdit(IMGStickerTextView iMGStickerTextView) {
        StickerEntity stickerByUUID;
        if (!TextUtils.isEmpty(iMGStickerTextView.getText().getId()) && (stickerByUUID = DBUtils.getStickerByUUID(iMGStickerTextView.getText().getId())) != null) {
            return (iMGStickerTextView.getText().getColor() == stickerByUUID.color && TextUtils.equals(iMGStickerTextView.getText().getText(), stickerByUUID.text) && iMGStickerTextView.getTranslationX() == stickerByUUID.translationX && iMGStickerTextView.getTranslationY() == stickerByUUID.translationY && iMGStickerTextView.getRotation() == stickerByUUID.rotation && iMGStickerTextView.getScale() == stickerByUUID.scale) ? false : true;
        }
        return false;
    }

    private String getStickerWaterImgPath() {
        FileOutputStream fileOutputStream;
        String stickerWaterImgPath = AppUtils.getStickerWaterImgPath();
        Bitmap saveBitmap = this.mStickerShowView.saveBitmap();
        if (saveBitmap != null) {
            FileOutputStream fileOutputStream2 = null;
            try {
                try {
                    fileOutputStream = new FileOutputStream(stickerWaterImgPath);
                } catch (Throwable th) {
                    th = th;
                }
            } catch (FileNotFoundException e) {
                e = e;
            }
            try {
                Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;
                saveBitmap.compress(compressFormat, 100, fileOutputStream);
                IOUtils.closeQuietly(fileOutputStream);
                fileOutputStream2 = compressFormat;
            } catch (FileNotFoundException e2) {
                e = e2;
                fileOutputStream2 = fileOutputStream;
                e.printStackTrace();
                IOUtils.closeQuietly(fileOutputStream2);
                fileOutputStream2 = fileOutputStream2;
                return stickerWaterImgPath;
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream2 = fileOutputStream;
                IOUtils.closeQuietly(fileOutputStream2);
                throw th;
            }
        }
        return stickerWaterImgPath;
    }
}
