package com.luck.picture.lib;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.p002v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.luck.picture.lib.adapter.SimpleFragmentAdapter;
import com.luck.picture.lib.anim.OptAnimationLoader;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.EventEntity;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.observable.ImagesObservable;
import com.luck.picture.lib.rxbus2.RxBus;
import com.luck.picture.lib.tools.ScreenUtils;
import com.luck.picture.lib.tools.ToastManage;
import com.luck.picture.lib.tools.VoiceUtils;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.tomato.ucrop.UCropMulti;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class PicturePreviewActivity extends PictureBaseActivity implements View.OnClickListener, Animation.AnimationListener, SimpleFragmentAdapter.OnCallBackActivity {
    private SimpleFragmentAdapter adapter;
    private Animation animation;
    private TextView check;
    private LinearLayout id_ll_ok;
    private int index;
    private LinearLayout ll_check;
    private Handler mHandler;
    private ImageView picture_left_back;
    private int position;
    private boolean refresh;
    private int screenWidth;
    private TextView tv_img_num;
    private TextView tv_ok;
    private TextView tv_title;
    private PreviewViewPager viewPager;
    private List<LocalMedia> images = new ArrayList();
    private List<LocalMedia> selectImages = new ArrayList();

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationRepeat(Animation animation) {
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationStart(Animation animation) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.luck.picture.lib.PictureBaseActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        String string;
        super.onCreate(bundle);
        setContentView(R$layout.picture_preview);
        if (!RxBus.getDefault().isRegistered(this)) {
            RxBus.getDefault().register(this);
        }
        this.mHandler = new Handler();
        this.screenWidth = ScreenUtils.getScreenWidth(this);
        this.animation = OptAnimationLoader.loadAnimation(this, R$anim.modal_in);
        this.animation.setAnimationListener(this);
        this.picture_left_back = (ImageView) findViewById(R$id.picture_left_back);
        this.viewPager = (PreviewViewPager) findViewById(R$id.preview_pager);
        this.ll_check = (LinearLayout) findViewById(R$id.ll_check);
        this.id_ll_ok = (LinearLayout) findViewById(R$id.id_ll_ok);
        this.check = (TextView) findViewById(R$id.check);
        this.picture_left_back.setOnClickListener(this);
        this.tv_ok = (TextView) findViewById(R$id.tv_ok);
        this.id_ll_ok.setOnClickListener(this);
        this.tv_img_num = (TextView) findViewById(R$id.tv_img_num);
        this.tv_title = (TextView) findViewById(R$id.picture_title);
        this.position = getIntent().getIntExtra("position", 0);
        TextView textView = this.tv_ok;
        if (this.numComplete) {
            int i = R$string.picture_done_front_num;
            Object[] objArr = new Object[2];
            objArr[0] = 0;
            PictureSelectionConfig pictureSelectionConfig = this.config;
            objArr[1] = Integer.valueOf(pictureSelectionConfig.selectionMode == 1 ? 1 : pictureSelectionConfig.maxSelectNum);
            string = getString(i, objArr);
        } else {
            string = getString(R$string.picture_please_select);
        }
        textView.setText(string);
        this.tv_img_num.setSelected(this.config.checkNumMode);
        this.selectImages = (List) getIntent().getSerializableExtra("selectList");
        if (getIntent().getBooleanExtra("bottom_preview", false)) {
            this.images = (List) getIntent().getSerializableExtra("previewSelectList");
        } else {
            this.images = ImagesObservable.getInstance().readLocalMedias();
        }
        initViewPageAdapterData();
        this.ll_check.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.PicturePreviewActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                boolean z;
                if (PicturePreviewActivity.this.images == null || PicturePreviewActivity.this.images.size() <= 0) {
                    return;
                }
                LocalMedia localMedia = (LocalMedia) PicturePreviewActivity.this.images.get(PicturePreviewActivity.this.viewPager.getCurrentItem());
                String pictureType = PicturePreviewActivity.this.selectImages.size() > 0 ? ((LocalMedia) PicturePreviewActivity.this.selectImages.get(0)).getPictureType() : "";
                if (TextUtils.isEmpty(pictureType) || PictureMimeType.mimeToEqual(pictureType, localMedia.getPictureType())) {
                    if (!PicturePreviewActivity.this.check.isSelected()) {
                        PicturePreviewActivity.this.check.setSelected(true);
                        PicturePreviewActivity.this.check.startAnimation(PicturePreviewActivity.this.animation);
                        z = true;
                    } else {
                        PicturePreviewActivity.this.check.setSelected(false);
                        z = false;
                    }
                    int size = PicturePreviewActivity.this.selectImages.size();
                    PicturePreviewActivity picturePreviewActivity = PicturePreviewActivity.this;
                    int i2 = picturePreviewActivity.config.maxSelectNum;
                    if (size >= i2 && z) {
                        ToastManage.m3846s(picturePreviewActivity.mContext, picturePreviewActivity.getString(R$string.picture_message_max_num, new Object[]{Integer.valueOf(i2)}));
                        PicturePreviewActivity.this.check.setSelected(false);
                        return;
                    }
                    if (!z) {
                        Iterator it2 = PicturePreviewActivity.this.selectImages.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            LocalMedia localMedia2 = (LocalMedia) it2.next();
                            if (localMedia2.getPath().equals(localMedia.getPath())) {
                                PicturePreviewActivity.this.selectImages.remove(localMedia2);
                                PicturePreviewActivity.this.subSelectPosition();
                                PicturePreviewActivity.this.notifyCheckChanged(localMedia2);
                                break;
                            }
                        }
                    } else {
                        PicturePreviewActivity picturePreviewActivity2 = PicturePreviewActivity.this;
                        VoiceUtils.playVoice(picturePreviewActivity2.mContext, picturePreviewActivity2.config.openClickSound);
                        PicturePreviewActivity picturePreviewActivity3 = PicturePreviewActivity.this;
                        if (picturePreviewActivity3.config.selectionMode == 1) {
                            picturePreviewActivity3.singleRadioMediaImage();
                        }
                        PicturePreviewActivity.this.selectImages.add(localMedia);
                        localMedia.setNum(PicturePreviewActivity.this.selectImages.size());
                        PicturePreviewActivity picturePreviewActivity4 = PicturePreviewActivity.this;
                        if (picturePreviewActivity4.config.checkNumMode) {
                            picturePreviewActivity4.check.setText(String.valueOf(localMedia.getNum()));
                        }
                    }
                    PicturePreviewActivity.this.onSelectNumChange(true);
                    return;
                }
                PicturePreviewActivity picturePreviewActivity5 = PicturePreviewActivity.this;
                ToastManage.m3846s(picturePreviewActivity5.mContext, picturePreviewActivity5.getString(R$string.picture_rule));
            }
        });
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.luck.picture.lib.PicturePreviewActivity.3
            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i2) {
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i2, float f, int i3) {
                PicturePreviewActivity picturePreviewActivity = PicturePreviewActivity.this;
                picturePreviewActivity.isPreviewEggs(picturePreviewActivity.config.previewEggs, i2, i3);
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i2) {
                PicturePreviewActivity.this.position = i2;
                TextView textView2 = PicturePreviewActivity.this.tv_title;
                textView2.setText((PicturePreviewActivity.this.position + 1) + "/" + PicturePreviewActivity.this.images.size());
                LocalMedia localMedia = (LocalMedia) PicturePreviewActivity.this.images.get(PicturePreviewActivity.this.position);
                PicturePreviewActivity.this.index = localMedia.getPosition();
                PicturePreviewActivity picturePreviewActivity = PicturePreviewActivity.this;
                PictureSelectionConfig pictureSelectionConfig2 = picturePreviewActivity.config;
                if (!pictureSelectionConfig2.previewEggs) {
                    if (pictureSelectionConfig2.checkNumMode) {
                        TextView textView3 = picturePreviewActivity.check;
                        textView3.setText(localMedia.getNum() + "");
                        PicturePreviewActivity.this.notifyCheckChanged(localMedia);
                    }
                    PicturePreviewActivity picturePreviewActivity2 = PicturePreviewActivity.this;
                    picturePreviewActivity2.onImageChecked(picturePreviewActivity2.position);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void isPreviewEggs(boolean z, int i, int i2) {
        List<LocalMedia> list;
        if (!z || this.images.size() <= 0 || (list = this.images) == null) {
            return;
        }
        if (i2 < this.screenWidth / 2) {
            LocalMedia localMedia = list.get(i);
            this.check.setSelected(isSelected(localMedia));
            if (!this.config.checkNumMode) {
                return;
            }
            int num = localMedia.getNum();
            TextView textView = this.check;
            textView.setText(num + "");
            notifyCheckChanged(localMedia);
            onImageChecked(i);
            return;
        }
        int i3 = i + 1;
        LocalMedia localMedia2 = list.get(i3);
        this.check.setSelected(isSelected(localMedia2));
        if (!this.config.checkNumMode) {
            return;
        }
        int num2 = localMedia2.getNum();
        TextView textView2 = this.check;
        textView2.setText(num2 + "");
        notifyCheckChanged(localMedia2);
        onImageChecked(i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void singleRadioMediaImage() {
        List<LocalMedia> list = this.selectImages;
        if (list == null || list.size() <= 0) {
            return;
        }
        RxBus.getDefault().post(new EventEntity(2774, this.selectImages, this.selectImages.get(0).getPosition()));
        this.selectImages.clear();
    }

    private void initViewPageAdapterData() {
        TextView textView = this.tv_title;
        textView.setText((this.position + 1) + "/" + this.images.size());
        this.adapter = new SimpleFragmentAdapter(this.images, this, this);
        this.viewPager.setAdapter(this.adapter);
        this.viewPager.setCurrentItem(this.position);
        onSelectNumChange(false);
        onImageChecked(this.position);
        if (this.images.size() > 0) {
            LocalMedia localMedia = this.images.get(this.position);
            this.index = localMedia.getPosition();
            if (!this.config.checkNumMode) {
                return;
            }
            this.tv_img_num.setSelected(true);
            TextView textView2 = this.check;
            textView2.setText(localMedia.getNum() + "");
            notifyCheckChanged(localMedia);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCheckChanged(LocalMedia localMedia) {
        if (this.config.checkNumMode) {
            this.check.setText("");
            for (LocalMedia localMedia2 : this.selectImages) {
                if (localMedia2.getPath().equals(localMedia.getPath())) {
                    localMedia.setNum(localMedia2.getNum());
                    this.check.setText(String.valueOf(localMedia.getNum()));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void subSelectPosition() {
        int size = this.selectImages.size();
        int i = 0;
        while (i < size) {
            i++;
            this.selectImages.get(i).setNum(i);
        }
    }

    public void onImageChecked(int i) {
        List<LocalMedia> list = this.images;
        if (list != null && list.size() > 0) {
            this.check.setSelected(isSelected(this.images.get(i)));
        } else {
            this.check.setSelected(false);
        }
    }

    public boolean isSelected(LocalMedia localMedia) {
        for (LocalMedia localMedia2 : this.selectImages) {
            if (localMedia2.getPath().equals(localMedia.getPath())) {
                return true;
            }
        }
        return false;
    }

    public void onSelectNumChange(boolean z) {
        this.refresh = z;
        if (this.selectImages.size() != 0) {
            this.tv_ok.setSelected(true);
            this.id_ll_ok.setEnabled(true);
            if (this.numComplete) {
                TextView textView = this.tv_ok;
                int i = R$string.picture_done_front_num;
                Object[] objArr = new Object[2];
                objArr[0] = Integer.valueOf(this.selectImages.size());
                PictureSelectionConfig pictureSelectionConfig = this.config;
                objArr[1] = Integer.valueOf(pictureSelectionConfig.selectionMode == 1 ? 1 : pictureSelectionConfig.maxSelectNum);
                textView.setText(getString(i, objArr));
            } else {
                if (this.refresh) {
                    this.tv_img_num.startAnimation(this.animation);
                }
                this.tv_img_num.setVisibility(0);
                this.tv_img_num.setText(String.valueOf(this.selectImages.size()));
                this.tv_ok.setText(getString(R$string.picture_completed));
            }
        } else {
            this.id_ll_ok.setEnabled(false);
            this.tv_ok.setSelected(false);
            if (this.numComplete) {
                TextView textView2 = this.tv_ok;
                int i2 = R$string.picture_done_front_num;
                Object[] objArr2 = new Object[2];
                objArr2[0] = 0;
                PictureSelectionConfig pictureSelectionConfig2 = this.config;
                objArr2[1] = Integer.valueOf(pictureSelectionConfig2.selectionMode == 1 ? 1 : pictureSelectionConfig2.maxSelectNum);
                textView2.setText(getString(i2, objArr2));
            } else {
                this.tv_img_num.setVisibility(4);
                this.tv_ok.setText(getString(R$string.picture_please_select));
            }
        }
        updateSelector(this.refresh);
    }

    private void updateSelector(boolean z) {
        if (z) {
            RxBus.getDefault().post(new EventEntity(2774, this.selectImages, this.index));
        }
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        updateSelector(this.refresh);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R$id.picture_left_back) {
            onBackPressed();
        }
        if (id == R$id.id_ll_ok) {
            int size = this.selectImages.size();
            LocalMedia localMedia = this.selectImages.size() > 0 ? this.selectImages.get(0) : null;
            String pictureType = localMedia != null ? localMedia.getPictureType() : "";
            PictureSelectionConfig pictureSelectionConfig = this.config;
            int i = pictureSelectionConfig.minSelectNum;
            if (i > 0 && size < i && pictureSelectionConfig.selectionMode == 2) {
                ToastManage.m3846s(this.mContext, pictureType.startsWith("image") ? getString(R$string.picture_min_img_num, new Object[]{Integer.valueOf(this.config.minSelectNum)}) : getString(R$string.picture_min_video_num, new Object[]{Integer.valueOf(this.config.minSelectNum)}));
            } else if (this.config.enableCrop && pictureType.startsWith("image")) {
                if (this.config.selectionMode == 1) {
                    this.originalPath = localMedia.getPath();
                    startCrop(this.originalPath);
                    return;
                }
                ArrayList<String> arrayList = new ArrayList<>();
                for (LocalMedia localMedia2 : this.selectImages) {
                    arrayList.add(localMedia2.getPath());
                }
                startCrop(arrayList);
            } else if (!this.config.isCompress) {
                super.onResult(this.selectImages);
            } else {
                showPleaseDialog();
            }
        }
    }

    @Override // com.luck.picture.lib.PictureBaseActivity
    public void onResult(List<LocalMedia> list) {
        RxBus.getDefault().post(new EventEntity(2771, list));
        if (!this.config.isCompress) {
            onBackPressed();
        } else {
            showPleaseDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 != -1) {
            if (i2 != 96) {
                return;
            }
            ToastManage.m3846s(this.mContext, ((Throwable) intent.getSerializableExtra("com.one.tomato.ucrop.Error")).getMessage());
        } else if (i == 69) {
            if (intent != null) {
                setResult(-1, intent);
            }
            finish();
        } else if (i != 609) {
        } else {
            setResult(-1, new Intent().putExtra("com.one.tomato.ucrop.OutputUriList", (Serializable) UCropMulti.getOutput(intent)));
            finish();
        }
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        closeActivity();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.luck.picture.lib.PictureBaseActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        if (RxBus.getDefault().isRegistered(this)) {
            RxBus.getDefault().unregister(this);
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.mHandler = null;
        }
        Animation animation = this.animation;
        if (animation != null) {
            animation.cancel();
            this.animation = null;
        }
    }

    @Override // com.luck.picture.lib.adapter.SimpleFragmentAdapter.OnCallBackActivity
    public void onActivityBackPressed() {
        onBackPressed();
    }
}
