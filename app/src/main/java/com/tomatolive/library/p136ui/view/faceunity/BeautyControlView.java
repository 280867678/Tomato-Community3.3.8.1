package com.tomatolive.library.p136ui.view.faceunity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.blankj.utilcode.util.SPUtils;
import com.faceunity.beautycontrolview.BeautyBox;
import com.faceunity.beautycontrolview.BeautyBoxGroup;
import com.faceunity.beautycontrolview.CheckGroup;
import com.faceunity.beautycontrolview.FilterEnum;
import com.faceunity.beautycontrolview.OnFaceUnityControlListener;
import com.faceunity.beautycontrolview.entity.Effect;
import com.faceunity.beautycontrolview.entity.Filter;
import com.faceunity.beautycontrolview.seekbar.DiscreteSeekBar;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.tomatolive.library.ui.view.faceunity.BeautyControlView */
/* loaded from: classes3.dex */
public class BeautyControlView extends FrameLayout {
    private static final float FINAL_CHANE = 1000.0f;
    private static final String FaceBeautyFilterLevel = "FaceBeautyFilterLevel_";
    private List<Filter> mBeautyFilters;
    private FrameLayout mBeautyMidLayout;
    private DiscreteSeekBar mBeautySeekBar;
    private FrameLayout mBeautySeekBarLayout;
    private float mBeautyTeethLevel;
    private CheckGroup mBottomCheckGroup;
    private ValueAnimator mBottomLayoutAnimator;
    private float mBrightEyesLevel;
    private float mChinLevel;
    private BeautyBox mChinLevelBox;
    private Context mContext;
    private int mEffectPositionSelect;
    private List<Effect> mEffects;
    private float mFaceBeautyALLBlurLevel;
    private float mFaceBeautyBlurLevel;
    private float mFaceBeautyCheekThin;
    private float mFaceBeautyCheekThin_old;
    private float mFaceBeautyColorLevel;
    private float mFaceBeautyEnlargeEye;
    private float mFaceBeautyEnlargeEye_old;
    private float mFaceBeautyFaceShape;
    private float mFaceBeautyRedLevel;
    private float mFaceBeautyType;
    private BeautyBoxGroup mFaceShapeBeautyBoxGroup;
    private BeautyBox mFaceShapeBox;
    private RadioGroup mFaceShapeRadioGroup;
    private HorizontalScrollView mFaceShapeSelect;
    private Map<String, Float> mFilterLevelIntegerMap;
    private int mFilterPositionSelect;
    private FilterRecyclerAdapter mFilterRecyclerAdapter;
    private RecyclerView mFilterRecyclerView;
    private int mFilterTypeSelect;
    private List<Filter> mFilters;
    private float mForeheadLevel;
    private BeautyBox mForeheadLevelBox;
    private float mMouthShape;
    private BeautyBox mMouthShapeBox;
    private OnBottomAnimatorChangeListener mOnBottomAnimatorChangeListener;
    private OnDescriptionShowListener mOnDescriptionShowListener;
    private OnFaceUnityControlListener mOnFaceUnityControlListener;
    private BeautyBoxGroup mSkinBeautyBoxGroup;
    private HorizontalScrollView mSkinBeautySelect;
    private float mThinNoseLevel;
    private BeautyBox mThinNoseLevelBox;
    private static final String TAG = BeautyControlView.class.getSimpleName();
    private static final List<Integer> FaceShapeIdList = Arrays.asList(Integer.valueOf(R$id.face_shape_0_nvshen), Integer.valueOf(R$id.face_shape_1_wanghong), Integer.valueOf(R$id.face_shape_2_ziran), Integer.valueOf(R$id.face_shape_3_default), Integer.valueOf(R$id.face_shape_4));

    /* renamed from: com.tomatolive.library.ui.view.faceunity.BeautyControlView$OnBottomAnimatorChangeListener */
    /* loaded from: classes3.dex */
    public interface OnBottomAnimatorChangeListener {
        void onBottomAnimatorChangeListener(float f);
    }

    /* renamed from: com.tomatolive.library.ui.view.faceunity.BeautyControlView$OnDescriptionShowListener */
    /* loaded from: classes3.dex */
    public interface OnDescriptionShowListener {
        void onDescriptionShowListener(String str);
    }

    public void setOnFaceUnityControlListener(@NonNull OnFaceUnityControlListener onFaceUnityControlListener) {
        this.mOnFaceUnityControlListener = onFaceUnityControlListener;
    }

    public BeautyControlView(Context context) {
        this(context, null);
    }

    public BeautyControlView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BeautyControlView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mFilterLevelIntegerMap = new HashMap();
        this.mFaceBeautyALLBlurLevel = 1.0f;
        this.mFaceBeautyType = 0.0f;
        this.mFaceBeautyBlurLevel = 0.7f;
        this.mFaceBeautyColorLevel = 0.5f;
        this.mFaceBeautyRedLevel = 0.5f;
        this.mBrightEyesLevel = 1000.7f;
        this.mBeautyTeethLevel = 1000.7f;
        this.mFaceBeautyFaceShape = 4.0f;
        this.mFaceBeautyEnlargeEye = 0.4f;
        this.mFaceBeautyCheekThin = 0.4f;
        this.mFaceBeautyEnlargeEye_old = 0.4f;
        this.mFaceBeautyCheekThin_old = 0.4f;
        this.mChinLevel = 0.3f;
        this.mForeheadLevel = 0.3f;
        this.mThinNoseLevel = 0.5f;
        this.mMouthShape = 0.4f;
        this.mEffectPositionSelect = 0;
        this.mFilterPositionSelect = 0;
        this.mFilterTypeSelect = 1;
        this.mContext = context;
        LayoutInflater.from(context).inflate(R$layout.layout_beauty_control, this);
        initView();
        post(new Runnable() { // from class: com.tomatolive.library.ui.view.faceunity.BeautyControlView.1
            @Override // java.lang.Runnable
            public void run() {
                BeautyControlView.this.updateViewSkinBeauty();
                BeautyControlView.this.updateViewFaceShape();
                BeautyControlView.this.mSkinBeautyBoxGroup.check(-1);
                BeautyControlView.this.mFaceShapeBeautyBoxGroup.check(-1);
            }
        });
        this.mBeautyFilters = FilterEnum.getFiltersByFilterType(1);
        this.mFilters = FilterEnum.getFiltersByFilterType(0);
        getLastParam();
    }

    private void getLastParam() {
        this.mFaceBeautyALLBlurLevel = SPUtils.getInstance().getFloat(FaceConstant.FACE_BEAUTY_ALLBLUR_LEVEL, 1.0f);
        this.mFaceBeautyType = SPUtils.getInstance().getFloat(FaceConstant.FACE_BEAUTYTYPE, 0.0f);
        this.mFaceBeautyBlurLevel = SPUtils.getInstance().getFloat(FaceConstant.FACE_BEAUTYBLUR_LEVEL, 0.7f);
        this.mFaceBeautyColorLevel = SPUtils.getInstance().getFloat(FaceConstant.FACE_BEAUTYCOLOR_LEVEL, 0.5f);
        this.mFaceBeautyRedLevel = SPUtils.getInstance().getFloat(FaceConstant.FACE_BEAUTYRED_LEVEL, 0.5f);
        this.mBrightEyesLevel = SPUtils.getInstance().getFloat(FaceConstant.BRIGHTEYES_LEVEL, 0.0f);
        this.mBeautyTeethLevel = SPUtils.getInstance().getFloat(FaceConstant.BEAUTYTEETH_LEVEL, 0.0f);
        this.mFaceBeautyFaceShape = SPUtils.getInstance().getFloat(FaceConstant.FACEBEAUTY_FACESHAPE, 4.0f);
        this.mFaceBeautyEnlargeEye = SPUtils.getInstance().getFloat(FaceConstant.FACEBEAUTY_ENLARGEEYE, 0.4f);
        this.mFaceBeautyCheekThin = SPUtils.getInstance().getFloat(FaceConstant.FACEBEAUTY_CHEEKTHIN, 0.4f);
        this.mChinLevel = SPUtils.getInstance().getFloat(FaceConstant.CHIN_LEVEL, 0.3f);
        this.mForeheadLevel = SPUtils.getInstance().getFloat(FaceConstant.FOREHEAD_LEVEL, 0.3f);
        this.mThinNoseLevel = SPUtils.getInstance().getFloat(FaceConstant.THINNOSE_LEVEL, 0.5f);
        this.mMouthShape = SPUtils.getInstance().getFloat(FaceConstant.Mouth_Shape, 0.4f);
        this.mFilterLevelIntegerMap.clear();
        for (Filter filter : this.mBeautyFilters) {
            float f = SPUtils.getInstance().getFloat(filter.filterName(), 1.0f);
            Map<String, Float> map = this.mFilterLevelIntegerMap;
            map.put(FaceBeautyFilterLevel + filter.filterName(), Float.valueOf(f));
        }
        for (Filter filter2 : this.mFilters) {
            float f2 = SPUtils.getInstance().getFloat(filter2.filterName(), 1.0f);
            Map<String, Float> map2 = this.mFilterLevelIntegerMap;
            map2.put(FaceBeautyFilterLevel + filter2.filterName(), Float.valueOf(f2));
        }
    }

    private void initView() {
        initViewBottomRadio();
        this.mBeautyMidLayout = (FrameLayout) findViewById(R$id.beauty_mid_layout);
        initViewSkinBeauty();
        initViewFaceShape();
        initViewRecyclerView();
        initViewTop();
    }

    private void initViewBottomRadio() {
        this.mBottomCheckGroup = (CheckGroup) findViewById(R$id.beauty_radio_group);
        this.mBottomCheckGroup.setOnCheckedChangeListener(new CheckGroup.OnCheckedChangeListener() { // from class: com.tomatolive.library.ui.view.faceunity.BeautyControlView.2
            @Override // com.faceunity.beautycontrolview.CheckGroup.OnCheckedChangeListener
            public void onCheckedChanged(CheckGroup checkGroup, int i) {
                BeautyControlView.this.clickViewBottomRadio(i);
                BeautyControlView.this.changeBottomLayoutAnimator(true);
            }
        });
    }

    private void initViewSkinBeauty() {
        this.mSkinBeautySelect = (HorizontalScrollView) findViewById(R$id.skin_beauty_select_block);
        this.mSkinBeautyBoxGroup = (BeautyBoxGroup) findViewById(R$id.beauty_box_skin_beauty);
        this.mSkinBeautyBoxGroup.setOnCheckedChangeListener(new BeautyBoxGroup.OnCheckedChangeListener() { // from class: com.tomatolive.library.ui.view.faceunity.BeautyControlView.3
            @Override // com.faceunity.beautycontrolview.BeautyBoxGroup.OnCheckedChangeListener
            public void onCheckedChanged(BeautyBoxGroup beautyBoxGroup, int i, boolean z) {
                BeautyControlView.this.mFaceShapeRadioGroup.setVisibility(8);
                BeautyControlView.this.mBeautySeekBarLayout.setVisibility(8);
                float f = 1.0f;
                if (i == R$id.beauty_all_blur_box) {
                    BeautyControlView beautyControlView = BeautyControlView.this;
                    if (!z) {
                        f = 0.0f;
                    }
                    beautyControlView.mFaceBeautyALLBlurLevel = f;
                    BeautyControlView beautyControlView2 = BeautyControlView.this;
                    beautyControlView2.setDescriptionShowStr(beautyControlView2.mFaceBeautyALLBlurLevel == 0.0f ? "精准美肤 关闭" : "精准美肤 开启");
                    BeautyControlView beautyControlView3 = BeautyControlView.this;
                    beautyControlView3.onChangeFaceBeautyLevel(i, beautyControlView3.mFaceBeautyALLBlurLevel);
                } else if (i == R$id.beauty_type_box) {
                    BeautyControlView beautyControlView4 = BeautyControlView.this;
                    if (!z) {
                        f = 0.0f;
                    }
                    beautyControlView4.mFaceBeautyType = f;
                    BeautyControlView beautyControlView5 = BeautyControlView.this;
                    beautyControlView5.setDescriptionShowStr(beautyControlView5.mFaceBeautyType == 0.0f ? "当前为 清晰磨皮 模式" : "当前为 朦胧磨皮 模式");
                    BeautyControlView beautyControlView6 = BeautyControlView.this;
                    beautyControlView6.onChangeFaceBeautyLevel(i, beautyControlView6.mFaceBeautyType);
                } else if (i == R$id.beauty_blur_box) {
                    if (!z || BeautyControlView.this.mFaceBeautyBlurLevel < BeautyControlView.FINAL_CHANE) {
                        if (!z && BeautyControlView.this.mFaceBeautyBlurLevel < BeautyControlView.FINAL_CHANE) {
                            BeautyControlView.this.mFaceBeautyBlurLevel += BeautyControlView.FINAL_CHANE;
                            BeautyControlView.this.setDescriptionShowStr("磨皮 关闭");
                        }
                    } else {
                        BeautyControlView.this.mFaceBeautyBlurLevel -= BeautyControlView.FINAL_CHANE;
                        BeautyControlView.this.setDescriptionShowStr("磨皮 开启");
                    }
                    BeautyControlView beautyControlView7 = BeautyControlView.this;
                    beautyControlView7.seekToSeekBar(beautyControlView7.mFaceBeautyBlurLevel);
                    BeautyControlView beautyControlView8 = BeautyControlView.this;
                    beautyControlView8.onChangeFaceBeautyLevel(i, beautyControlView8.mFaceBeautyBlurLevel);
                } else if (i == R$id.beauty_color_box) {
                    if (!z || BeautyControlView.this.mFaceBeautyColorLevel < BeautyControlView.FINAL_CHANE) {
                        if (!z && BeautyControlView.this.mFaceBeautyColorLevel < BeautyControlView.FINAL_CHANE) {
                            BeautyControlView.this.mFaceBeautyColorLevel += BeautyControlView.FINAL_CHANE;
                            BeautyControlView.this.setDescriptionShowStr("美白 关闭");
                        }
                    } else {
                        BeautyControlView.this.mFaceBeautyColorLevel -= BeautyControlView.FINAL_CHANE;
                        BeautyControlView.this.setDescriptionShowStr("美白 开启");
                    }
                    BeautyControlView beautyControlView9 = BeautyControlView.this;
                    beautyControlView9.seekToSeekBar(beautyControlView9.mFaceBeautyColorLevel);
                    BeautyControlView beautyControlView10 = BeautyControlView.this;
                    beautyControlView10.onChangeFaceBeautyLevel(i, beautyControlView10.mFaceBeautyColorLevel);
                } else if (i == R$id.beauty_red_box) {
                    if (!z || BeautyControlView.this.mFaceBeautyRedLevel < BeautyControlView.FINAL_CHANE) {
                        if (!z && BeautyControlView.this.mFaceBeautyRedLevel < BeautyControlView.FINAL_CHANE) {
                            BeautyControlView.this.mFaceBeautyRedLevel += BeautyControlView.FINAL_CHANE;
                            BeautyControlView.this.setDescriptionShowStr("红润 关闭");
                        }
                    } else {
                        BeautyControlView.this.mFaceBeautyRedLevel -= BeautyControlView.FINAL_CHANE;
                        BeautyControlView.this.setDescriptionShowStr("红润 开启");
                    }
                    BeautyControlView beautyControlView11 = BeautyControlView.this;
                    beautyControlView11.seekToSeekBar(beautyControlView11.mFaceBeautyRedLevel);
                    BeautyControlView beautyControlView12 = BeautyControlView.this;
                    beautyControlView12.onChangeFaceBeautyLevel(i, beautyControlView12.mFaceBeautyRedLevel);
                } else if (i == R$id.beauty_bright_eyes_box) {
                    if (!z || BeautyControlView.this.mBrightEyesLevel < BeautyControlView.FINAL_CHANE) {
                        if (!z && BeautyControlView.this.mBrightEyesLevel < BeautyControlView.FINAL_CHANE) {
                            BeautyControlView.this.mBrightEyesLevel += BeautyControlView.FINAL_CHANE;
                            BeautyControlView.this.setDescriptionShowStr("亮眼 关闭");
                        }
                    } else {
                        BeautyControlView.this.mBrightEyesLevel -= BeautyControlView.FINAL_CHANE;
                        BeautyControlView.this.setDescriptionShowStr("亮眼 开启");
                    }
                    BeautyControlView beautyControlView13 = BeautyControlView.this;
                    beautyControlView13.seekToSeekBar(beautyControlView13.mBrightEyesLevel);
                    BeautyControlView beautyControlView14 = BeautyControlView.this;
                    beautyControlView14.onChangeFaceBeautyLevel(i, beautyControlView14.mBrightEyesLevel);
                } else if (i == R$id.beauty_teeth_box) {
                    if (!z || BeautyControlView.this.mBeautyTeethLevel < BeautyControlView.FINAL_CHANE) {
                        if (!z && BeautyControlView.this.mBeautyTeethLevel < BeautyControlView.FINAL_CHANE) {
                            BeautyControlView.this.mBeautyTeethLevel += BeautyControlView.FINAL_CHANE;
                            BeautyControlView.this.setDescriptionShowStr("美牙 关闭");
                        }
                    } else {
                        BeautyControlView.this.mBeautyTeethLevel -= BeautyControlView.FINAL_CHANE;
                        BeautyControlView.this.setDescriptionShowStr("美牙 开启");
                    }
                    BeautyControlView beautyControlView15 = BeautyControlView.this;
                    beautyControlView15.seekToSeekBar(beautyControlView15.mBeautyTeethLevel);
                    BeautyControlView beautyControlView16 = BeautyControlView.this;
                    beautyControlView16.onChangeFaceBeautyLevel(i, beautyControlView16.mBeautyTeethLevel);
                }
                BeautyControlView.this.changeBottomLayoutAnimator(false);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateViewSkinBeauty() {
        boolean z = true;
        ((BeautyBox) findViewById(R$id.beauty_all_blur_box)).setChecked(this.mFaceBeautyALLBlurLevel == 1.0f);
        ((BeautyBox) findViewById(R$id.beauty_type_box)).setChecked(this.mFaceBeautyType == 1.0f);
        ((BeautyBox) findViewById(R$id.beauty_blur_box)).setChecked(this.mFaceBeautyBlurLevel < FINAL_CHANE);
        ((BeautyBox) findViewById(R$id.beauty_color_box)).setChecked(this.mFaceBeautyColorLevel < FINAL_CHANE);
        ((BeautyBox) findViewById(R$id.beauty_red_box)).setChecked(this.mFaceBeautyRedLevel < FINAL_CHANE);
        ((BeautyBox) findViewById(R$id.beauty_bright_eyes_box)).setChecked(this.mBrightEyesLevel < FINAL_CHANE);
        BeautyBox beautyBox = (BeautyBox) findViewById(R$id.beauty_teeth_box);
        if (this.mBeautyTeethLevel >= FINAL_CHANE) {
            z = false;
        }
        beautyBox.setChecked(z);
    }

    private void initViewFaceShape() {
        this.mFaceShapeSelect = (HorizontalScrollView) findViewById(R$id.face_shape_select_block);
        this.mFaceShapeBeautyBoxGroup = (BeautyBoxGroup) findViewById(R$id.beauty_box_face_shape);
        this.mFaceShapeBeautyBoxGroup.setOnCheckedChangeListener(new BeautyBoxGroup.OnCheckedChangeListener() { // from class: com.tomatolive.library.ui.view.faceunity.BeautyControlView.4
            @Override // com.faceunity.beautycontrolview.BeautyBoxGroup.OnCheckedChangeListener
            public void onCheckedChanged(BeautyBoxGroup beautyBoxGroup, int i, boolean z) {
                BeautyControlView.this.mFaceShapeRadioGroup.setVisibility(8);
                BeautyControlView.this.mBeautySeekBarLayout.setVisibility(8);
                if (i == R$id.face_shape_box) {
                    BeautyControlView.this.mFaceShapeRadioGroup.setVisibility(0);
                } else if (i == R$id.enlarge_eye_level_box) {
                    if (BeautyControlView.this.mFaceBeautyFaceShape == 4.0f) {
                        if (!z || BeautyControlView.this.mFaceBeautyEnlargeEye < BeautyControlView.FINAL_CHANE) {
                            if (!z && BeautyControlView.this.mFaceBeautyEnlargeEye < BeautyControlView.FINAL_CHANE) {
                                BeautyControlView.this.mFaceBeautyEnlargeEye += BeautyControlView.FINAL_CHANE;
                                BeautyControlView.this.setDescriptionShowStr("大眼 关闭");
                            }
                        } else {
                            BeautyControlView.this.mFaceBeautyEnlargeEye -= BeautyControlView.FINAL_CHANE;
                            BeautyControlView.this.setDescriptionShowStr("大眼 开启");
                        }
                        BeautyControlView beautyControlView = BeautyControlView.this;
                        beautyControlView.seekToSeekBar(beautyControlView.mFaceBeautyEnlargeEye);
                        BeautyControlView beautyControlView2 = BeautyControlView.this;
                        beautyControlView2.onChangeFaceBeautyLevel(i, beautyControlView2.mFaceBeautyEnlargeEye);
                    } else {
                        if (!z || BeautyControlView.this.mFaceBeautyEnlargeEye_old < BeautyControlView.FINAL_CHANE) {
                            if (!z && BeautyControlView.this.mFaceBeautyEnlargeEye_old < BeautyControlView.FINAL_CHANE) {
                                BeautyControlView.this.mFaceBeautyEnlargeEye_old += BeautyControlView.FINAL_CHANE;
                                BeautyControlView.this.setDescriptionShowStr("大眼 关闭");
                            }
                        } else {
                            BeautyControlView.this.mFaceBeautyEnlargeEye_old -= BeautyControlView.FINAL_CHANE;
                            BeautyControlView.this.setDescriptionShowStr("大眼 开启");
                        }
                        BeautyControlView beautyControlView3 = BeautyControlView.this;
                        beautyControlView3.seekToSeekBar(beautyControlView3.mFaceBeautyEnlargeEye_old);
                        BeautyControlView beautyControlView4 = BeautyControlView.this;
                        beautyControlView4.onChangeFaceBeautyLevel(i, beautyControlView4.mFaceBeautyEnlargeEye_old);
                    }
                } else if (i == R$id.cheek_thin_level_box) {
                    if (BeautyControlView.this.mFaceBeautyFaceShape == 4.0f) {
                        if (!z || BeautyControlView.this.mFaceBeautyCheekThin < BeautyControlView.FINAL_CHANE) {
                            if (!z && BeautyControlView.this.mFaceBeautyCheekThin < BeautyControlView.FINAL_CHANE) {
                                BeautyControlView.this.mFaceBeautyCheekThin += BeautyControlView.FINAL_CHANE;
                                BeautyControlView.this.setDescriptionShowStr("瘦脸 关闭");
                            }
                        } else {
                            BeautyControlView.this.mFaceBeautyCheekThin -= BeautyControlView.FINAL_CHANE;
                            BeautyControlView.this.setDescriptionShowStr("瘦脸 开启");
                        }
                        BeautyControlView beautyControlView5 = BeautyControlView.this;
                        beautyControlView5.seekToSeekBar(beautyControlView5.mFaceBeautyCheekThin);
                        BeautyControlView beautyControlView6 = BeautyControlView.this;
                        beautyControlView6.onChangeFaceBeautyLevel(i, beautyControlView6.mFaceBeautyCheekThin);
                    } else {
                        if (!z || BeautyControlView.this.mFaceBeautyCheekThin_old < BeautyControlView.FINAL_CHANE) {
                            if (!z && BeautyControlView.this.mFaceBeautyCheekThin_old < BeautyControlView.FINAL_CHANE) {
                                BeautyControlView.this.mFaceBeautyCheekThin_old += BeautyControlView.FINAL_CHANE;
                                BeautyControlView.this.setDescriptionShowStr("瘦脸 关闭");
                            }
                        } else {
                            BeautyControlView.this.mFaceBeautyCheekThin_old -= BeautyControlView.FINAL_CHANE;
                            BeautyControlView.this.setDescriptionShowStr("瘦脸 开启");
                        }
                        BeautyControlView beautyControlView7 = BeautyControlView.this;
                        beautyControlView7.seekToSeekBar(beautyControlView7.mFaceBeautyCheekThin_old);
                        BeautyControlView beautyControlView8 = BeautyControlView.this;
                        beautyControlView8.onChangeFaceBeautyLevel(i, beautyControlView8.mFaceBeautyCheekThin_old);
                    }
                } else if (i == R$id.chin_level_box) {
                    if (!z || BeautyControlView.this.mChinLevel < BeautyControlView.FINAL_CHANE) {
                        if (!z && BeautyControlView.this.mChinLevel < BeautyControlView.FINAL_CHANE) {
                            BeautyControlView.this.mChinLevel += BeautyControlView.FINAL_CHANE;
                            BeautyControlView.this.setDescriptionShowStr("下巴 关闭");
                        }
                    } else {
                        BeautyControlView.this.mChinLevel -= BeautyControlView.FINAL_CHANE;
                        BeautyControlView.this.setDescriptionShowStr("下巴 开启");
                    }
                    BeautyControlView beautyControlView9 = BeautyControlView.this;
                    beautyControlView9.seekToSeekBar(beautyControlView9.mChinLevel, -50, 50);
                    BeautyControlView beautyControlView10 = BeautyControlView.this;
                    beautyControlView10.onChangeFaceBeautyLevel(i, beautyControlView10.mChinLevel);
                } else if (i == R$id.forehead_level_box) {
                    if (!z || BeautyControlView.this.mForeheadLevel < BeautyControlView.FINAL_CHANE) {
                        if (!z && BeautyControlView.this.mForeheadLevel < BeautyControlView.FINAL_CHANE) {
                            BeautyControlView.this.mForeheadLevel += BeautyControlView.FINAL_CHANE;
                            BeautyControlView.this.setDescriptionShowStr("额头 关闭");
                        }
                    } else {
                        BeautyControlView.this.mForeheadLevel -= BeautyControlView.FINAL_CHANE;
                        BeautyControlView.this.setDescriptionShowStr("额头 开启");
                    }
                    BeautyControlView beautyControlView11 = BeautyControlView.this;
                    beautyControlView11.seekToSeekBar(beautyControlView11.mForeheadLevel, -50, 50);
                    BeautyControlView beautyControlView12 = BeautyControlView.this;
                    beautyControlView12.onChangeFaceBeautyLevel(i, beautyControlView12.mForeheadLevel);
                } else if (i == R$id.thin_nose_level_box) {
                    if (!z || BeautyControlView.this.mThinNoseLevel < BeautyControlView.FINAL_CHANE) {
                        if (!z && BeautyControlView.this.mThinNoseLevel < BeautyControlView.FINAL_CHANE) {
                            BeautyControlView.this.mThinNoseLevel += BeautyControlView.FINAL_CHANE;
                            BeautyControlView.this.setDescriptionShowStr("瘦鼻 关闭");
                        }
                    } else {
                        BeautyControlView.this.mThinNoseLevel -= BeautyControlView.FINAL_CHANE;
                        BeautyControlView.this.setDescriptionShowStr("瘦鼻 开启");
                    }
                    BeautyControlView beautyControlView13 = BeautyControlView.this;
                    beautyControlView13.seekToSeekBar(beautyControlView13.mThinNoseLevel);
                    BeautyControlView beautyControlView14 = BeautyControlView.this;
                    beautyControlView14.onChangeFaceBeautyLevel(i, beautyControlView14.mThinNoseLevel);
                } else if (i == R$id.mouth_shape_box) {
                    if (!z || BeautyControlView.this.mMouthShape < BeautyControlView.FINAL_CHANE) {
                        if (!z && BeautyControlView.this.mMouthShape < BeautyControlView.FINAL_CHANE) {
                            BeautyControlView.this.mMouthShape += BeautyControlView.FINAL_CHANE;
                            BeautyControlView.this.setDescriptionShowStr("嘴形 关闭");
                        }
                    } else {
                        BeautyControlView.this.mMouthShape -= BeautyControlView.FINAL_CHANE;
                        BeautyControlView.this.setDescriptionShowStr("嘴形 开启");
                    }
                    BeautyControlView beautyControlView15 = BeautyControlView.this;
                    beautyControlView15.seekToSeekBar(beautyControlView15.mMouthShape, -50, 50);
                    BeautyControlView beautyControlView16 = BeautyControlView.this;
                    beautyControlView16.onChangeFaceBeautyLevel(i, beautyControlView16.mMouthShape);
                }
                BeautyControlView.this.changeBottomLayoutAnimator(false);
            }
        });
        this.mFaceShapeBox = (BeautyBox) findViewById(R$id.face_shape_box);
        this.mChinLevelBox = (BeautyBox) findViewById(R$id.chin_level_box);
        this.mForeheadLevelBox = (BeautyBox) findViewById(R$id.forehead_level_box);
        this.mThinNoseLevelBox = (BeautyBox) findViewById(R$id.thin_nose_level_box);
        this.mMouthShapeBox = (BeautyBox) findViewById(R$id.mouth_shape_box);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateViewFaceShape() {
        boolean z = true;
        if (this.mFaceBeautyFaceShape == 4.0f) {
            ((BeautyBox) findViewById(R$id.enlarge_eye_level_box)).setChecked(this.mFaceBeautyEnlargeEye < FINAL_CHANE);
            ((BeautyBox) findViewById(R$id.cheek_thin_level_box)).setChecked(this.mFaceBeautyCheekThin < FINAL_CHANE);
        } else {
            ((BeautyBox) findViewById(R$id.enlarge_eye_level_box)).setChecked(this.mFaceBeautyEnlargeEye_old < FINAL_CHANE);
            ((BeautyBox) findViewById(R$id.cheek_thin_level_box)).setChecked(this.mFaceBeautyCheekThin_old < FINAL_CHANE);
        }
        ((BeautyBox) findViewById(R$id.chin_level_box)).setChecked(this.mChinLevel < FINAL_CHANE);
        ((BeautyBox) findViewById(R$id.forehead_level_box)).setChecked(this.mForeheadLevel < FINAL_CHANE);
        ((BeautyBox) findViewById(R$id.thin_nose_level_box)).setChecked(this.mThinNoseLevel < FINAL_CHANE);
        BeautyBox beautyBox = (BeautyBox) findViewById(R$id.mouth_shape_box);
        if (this.mMouthShape >= FINAL_CHANE) {
            z = false;
        }
        beautyBox.setChecked(z);
        float f = this.mFaceBeautyFaceShape;
        if (f != 4.0f) {
            this.mFaceShapeRadioGroup.check(FaceShapeIdList.get((int) f).intValue());
            this.mChinLevelBox.setVisibility(8);
            this.mForeheadLevelBox.setVisibility(8);
            this.mThinNoseLevelBox.setVisibility(8);
            this.mMouthShapeBox.setVisibility(8);
            return;
        }
        this.mFaceShapeRadioGroup.check(R$id.face_shape_4);
        this.mChinLevelBox.setVisibility(0);
        this.mForeheadLevelBox.setVisibility(0);
        this.mThinNoseLevelBox.setVisibility(0);
        this.mMouthShapeBox.setVisibility(0);
    }

    private void initViewRecyclerView() {
        this.mFilterRecyclerView = (RecyclerView) findViewById(R$id.filter_recycle_view);
        this.mFilterRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, 0, false));
        RecyclerView recyclerView = this.mFilterRecyclerView;
        FilterRecyclerAdapter filterRecyclerAdapter = new FilterRecyclerAdapter();
        this.mFilterRecyclerAdapter = filterRecyclerAdapter;
        recyclerView.setAdapter(filterRecyclerAdapter);
    }

    private void initViewTop() {
        this.mFaceShapeRadioGroup = (RadioGroup) findViewById(R$id.face_shape_radio_group);
        this.mFaceShapeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.tomatolive.library.ui.view.faceunity.BeautyControlView.5
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                float f;
                float f2;
                boolean z = false;
                if (i == R$id.face_shape_4) {
                    BeautyControlView.this.mChinLevelBox.setVisibility(0);
                    BeautyControlView.this.mForeheadLevelBox.setVisibility(0);
                    BeautyControlView.this.mThinNoseLevelBox.setVisibility(0);
                    BeautyControlView.this.mMouthShapeBox.setVisibility(0);
                } else {
                    BeautyControlView.this.mChinLevelBox.setVisibility(8);
                    BeautyControlView.this.mForeheadLevelBox.setVisibility(8);
                    BeautyControlView.this.mThinNoseLevelBox.setVisibility(8);
                    BeautyControlView.this.mMouthShapeBox.setVisibility(8);
                }
                BeautyControlView.this.mFaceBeautyFaceShape = BeautyControlView.FaceShapeIdList.indexOf(Integer.valueOf(i));
                if (BeautyControlView.this.mOnFaceUnityControlListener != null) {
                    BeautyControlView.this.mOnFaceUnityControlListener.onFaceShapeSelected(BeautyControlView.this.mFaceBeautyFaceShape);
                }
                if (BeautyControlView.this.mFaceBeautyFaceShape == 4.0f) {
                    f = BeautyControlView.this.mFaceBeautyEnlargeEye;
                    f2 = BeautyControlView.this.mFaceBeautyCheekThin;
                } else {
                    f = BeautyControlView.this.mFaceBeautyEnlargeEye_old;
                    f2 = BeautyControlView.this.mFaceBeautyCheekThin_old;
                }
                BeautyControlView.this.onChangeFaceBeautyLevel(R$id.enlarge_eye_level_box, f);
                BeautyControlView.this.onChangeFaceBeautyLevel(R$id.cheek_thin_level_box, f2);
                ((BeautyBox) BeautyControlView.this.findViewById(R$id.enlarge_eye_level_box)).setChecked(f < BeautyControlView.FINAL_CHANE);
                ((BeautyBox) BeautyControlView.this.findViewById(R$id.cheek_thin_level_box)).setChecked(f2 < BeautyControlView.FINAL_CHANE);
                BeautyBox beautyBox = BeautyControlView.this.mFaceShapeBox;
                if (i != R$id.face_shape_3_default) {
                    z = true;
                }
                beautyBox.setChecked(z);
            }
        });
        this.mBeautySeekBarLayout = (FrameLayout) findViewById(R$id.beauty_seek_bar_layout);
        this.mBeautySeekBar = (DiscreteSeekBar) findViewById(R$id.beauty_seek_bar);
        this.mBeautySeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() { // from class: com.tomatolive.library.ui.view.faceunity.BeautyControlView.6
            @Override // com.faceunity.beautycontrolview.seekbar.DiscreteSeekBar.OnProgressChangeListener
            public void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar) {
            }

            @Override // com.faceunity.beautycontrolview.seekbar.DiscreteSeekBar.OnProgressChangeListener
            public void onStopTrackingTouch(DiscreteSeekBar discreteSeekBar) {
            }

            @Override // com.faceunity.beautycontrolview.seekbar.DiscreteSeekBar.OnProgressChangeListener
            public void onProgressChanged(DiscreteSeekBar discreteSeekBar, int i, boolean z) {
                if (!z) {
                    return;
                }
                float min = ((i - discreteSeekBar.getMin()) * 1.0f) / 100.0f;
                if (BeautyControlView.this.mBottomCheckGroup.getCheckedCheckBoxId() != R$id.beauty_radio_skin_beauty) {
                    if (BeautyControlView.this.mBottomCheckGroup.getCheckedCheckBoxId() != R$id.beauty_radio_face_shape) {
                        if (BeautyControlView.this.mBottomCheckGroup.getCheckedCheckBoxId() != R$id.beauty_radio_beauty_filter && BeautyControlView.this.mBottomCheckGroup.getCheckedCheckBoxId() != R$id.beauty_radio_filter) {
                            return;
                        }
                        BeautyControlView.this.mFilterRecyclerAdapter.setFilterLevels(min);
                        if (BeautyControlView.this.mOnFaceUnityControlListener == null) {
                            return;
                        }
                        BeautyControlView.this.mOnFaceUnityControlListener.onFilterLevelSelected(min);
                        return;
                    }
                    BeautyControlView beautyControlView = BeautyControlView.this;
                    beautyControlView.onChangeFaceBeautyLevel(beautyControlView.mFaceShapeBeautyBoxGroup.getCheckedBeautyBoxId(), min);
                    return;
                }
                BeautyControlView beautyControlView2 = BeautyControlView.this;
                beautyControlView2.onChangeFaceBeautyLevel(beautyControlView2.mSkinBeautyBoxGroup.getCheckedBeautyBoxId(), min);
            }
        });
    }

    private void updateTopView(int i) {
        this.mFaceShapeRadioGroup.setVisibility(8);
        this.mBeautySeekBarLayout.setVisibility(8);
        if (i == R$id.beauty_blur_box) {
            seekToSeekBar(this.mFaceBeautyBlurLevel);
        } else if (i == R$id.beauty_color_box) {
            seekToSeekBar(this.mFaceBeautyColorLevel);
        } else if (i == R$id.beauty_red_box) {
            seekToSeekBar(this.mFaceBeautyRedLevel);
        } else if (i == R$id.beauty_bright_eyes_box) {
            seekToSeekBar(this.mBrightEyesLevel);
        } else if (i == R$id.beauty_teeth_box) {
            seekToSeekBar(this.mBeautyTeethLevel);
        } else if (i == R$id.face_shape_box) {
            this.mFaceShapeRadioGroup.setVisibility(0);
        } else if (i == R$id.enlarge_eye_level_box) {
            if (this.mFaceBeautyFaceShape == 4.0f) {
                seekToSeekBar(this.mFaceBeautyEnlargeEye);
            } else {
                seekToSeekBar(this.mFaceBeautyEnlargeEye_old);
            }
        } else if (i == R$id.cheek_thin_level_box) {
            if (this.mFaceBeautyFaceShape == 4.0f) {
                seekToSeekBar(this.mFaceBeautyCheekThin);
            } else {
                seekToSeekBar(this.mFaceBeautyCheekThin_old);
            }
        } else if (i == R$id.chin_level_box) {
            seekToSeekBar(this.mChinLevel, -50, 50);
        } else if (i == R$id.forehead_level_box) {
            seekToSeekBar(this.mForeheadLevel, -50, 50);
        } else if (i == R$id.thin_nose_level_box) {
            seekToSeekBar(this.mThinNoseLevel);
        } else if (i == R$id.mouth_shape_box) {
            seekToSeekBar(this.mMouthShape, -50, 50);
        }
        changeBottomLayoutAnimator(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onChangeFaceBeautyLevel(int i, float f) {
        boolean z = f >= FINAL_CHANE;
        if (i == R$id.beauty_all_blur_box) {
            OnFaceUnityControlListener onFaceUnityControlListener = this.mOnFaceUnityControlListener;
            if (onFaceUnityControlListener == null) {
                return;
            }
            onFaceUnityControlListener.onALLBlurLevelSelected(f);
        } else if (i == R$id.beauty_type_box) {
            OnFaceUnityControlListener onFaceUnityControlListener2 = this.mOnFaceUnityControlListener;
            if (onFaceUnityControlListener2 == null) {
                return;
            }
            onFaceUnityControlListener2.onBeautySkinTypeSelected(f);
        } else {
            float f2 = 0.0f;
            if (i == R$id.beauty_blur_box) {
                this.mFaceBeautyBlurLevel = f;
                OnFaceUnityControlListener onFaceUnityControlListener3 = this.mOnFaceUnityControlListener;
                if (onFaceUnityControlListener3 == null) {
                    return;
                }
                if (!z) {
                    f2 = this.mFaceBeautyBlurLevel;
                }
                onFaceUnityControlListener3.onBlurLevelSelected(f2);
            } else if (i == R$id.beauty_color_box) {
                this.mFaceBeautyColorLevel = f;
                OnFaceUnityControlListener onFaceUnityControlListener4 = this.mOnFaceUnityControlListener;
                if (onFaceUnityControlListener4 == null) {
                    return;
                }
                if (!z) {
                    f2 = this.mFaceBeautyColorLevel;
                }
                onFaceUnityControlListener4.onColorLevelSelected(f2);
            } else if (i == R$id.beauty_red_box) {
                this.mFaceBeautyRedLevel = f;
                OnFaceUnityControlListener onFaceUnityControlListener5 = this.mOnFaceUnityControlListener;
                if (onFaceUnityControlListener5 == null) {
                    return;
                }
                if (!z) {
                    f2 = this.mFaceBeautyRedLevel;
                }
                onFaceUnityControlListener5.onRedLevelSelected(f2);
            } else if (i == R$id.beauty_bright_eyes_box) {
                this.mBrightEyesLevel = f;
                OnFaceUnityControlListener onFaceUnityControlListener6 = this.mOnFaceUnityControlListener;
                if (onFaceUnityControlListener6 == null) {
                    return;
                }
                if (!z) {
                    f2 = this.mBrightEyesLevel;
                }
                onFaceUnityControlListener6.onBrightEyesSelected(f2);
            } else if (i == R$id.beauty_teeth_box) {
                this.mBeautyTeethLevel = f;
                OnFaceUnityControlListener onFaceUnityControlListener7 = this.mOnFaceUnityControlListener;
                if (onFaceUnityControlListener7 == null) {
                    return;
                }
                if (!z) {
                    f2 = this.mBeautyTeethLevel;
                }
                onFaceUnityControlListener7.onBeautyTeethSelected(f2);
            } else if (i == R$id.enlarge_eye_level_box) {
                if (this.mFaceBeautyFaceShape == 4.0f) {
                    this.mFaceBeautyEnlargeEye = f;
                    OnFaceUnityControlListener onFaceUnityControlListener8 = this.mOnFaceUnityControlListener;
                    if (onFaceUnityControlListener8 == null) {
                        return;
                    }
                    if (!z) {
                        f2 = this.mFaceBeautyEnlargeEye;
                    }
                    onFaceUnityControlListener8.onEnlargeEyeSelected(f2);
                    return;
                }
                this.mFaceBeautyEnlargeEye_old = f;
                OnFaceUnityControlListener onFaceUnityControlListener9 = this.mOnFaceUnityControlListener;
                if (onFaceUnityControlListener9 == null) {
                    return;
                }
                if (!z) {
                    f2 = this.mFaceBeautyEnlargeEye_old;
                }
                onFaceUnityControlListener9.onEnlargeEyeSelected(f2);
            } else if (i == R$id.cheek_thin_level_box) {
                if (this.mFaceBeautyFaceShape == 4.0f) {
                    this.mFaceBeautyCheekThin = f;
                    OnFaceUnityControlListener onFaceUnityControlListener10 = this.mOnFaceUnityControlListener;
                    if (onFaceUnityControlListener10 == null) {
                        return;
                    }
                    if (!z) {
                        f2 = this.mFaceBeautyCheekThin;
                    }
                    onFaceUnityControlListener10.onCheekThinSelected(f2);
                    return;
                }
                this.mFaceBeautyCheekThin_old = f;
                OnFaceUnityControlListener onFaceUnityControlListener11 = this.mOnFaceUnityControlListener;
                if (onFaceUnityControlListener11 == null) {
                    return;
                }
                if (!z) {
                    f2 = this.mFaceBeautyCheekThin_old;
                }
                onFaceUnityControlListener11.onCheekThinSelected(f2);
            } else {
                float f3 = 0.5f;
                if (i == R$id.chin_level_box) {
                    this.mChinLevel = f;
                    OnFaceUnityControlListener onFaceUnityControlListener12 = this.mOnFaceUnityControlListener;
                    if (onFaceUnityControlListener12 == null) {
                        return;
                    }
                    if (!z) {
                        f3 = this.mChinLevel;
                    }
                    onFaceUnityControlListener12.onChinLevelSelected(f3);
                } else if (i == R$id.forehead_level_box) {
                    this.mForeheadLevel = f;
                    OnFaceUnityControlListener onFaceUnityControlListener13 = this.mOnFaceUnityControlListener;
                    if (onFaceUnityControlListener13 == null) {
                        return;
                    }
                    if (!z) {
                        f3 = this.mForeheadLevel;
                    }
                    onFaceUnityControlListener13.onForeheadLevelSelected(f3);
                } else if (i == R$id.thin_nose_level_box) {
                    this.mThinNoseLevel = f;
                    OnFaceUnityControlListener onFaceUnityControlListener14 = this.mOnFaceUnityControlListener;
                    if (onFaceUnityControlListener14 == null) {
                        return;
                    }
                    if (!z) {
                        f2 = this.mThinNoseLevel;
                    }
                    onFaceUnityControlListener14.onThinNoseLevelSelected(f2);
                } else if (i != R$id.mouth_shape_box) {
                } else {
                    this.mMouthShape = f;
                    OnFaceUnityControlListener onFaceUnityControlListener15 = this.mOnFaceUnityControlListener;
                    if (onFaceUnityControlListener15 == null) {
                        return;
                    }
                    if (!z) {
                        f3 = this.mMouthShape;
                    }
                    onFaceUnityControlListener15.onMouthShapeSelected(f3);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clickViewBottomRadio(int i) {
        this.mBeautyMidLayout.setVisibility(8);
        this.mSkinBeautySelect.setVisibility(8);
        this.mFaceShapeSelect.setVisibility(8);
        this.mFilterRecyclerView.setVisibility(8);
        this.mFaceShapeRadioGroup.setVisibility(8);
        this.mBeautySeekBarLayout.setVisibility(8);
        if (i == R$id.beauty_radio_skin_beauty) {
            this.mBeautyMidLayout.setVisibility(0);
            this.mSkinBeautySelect.setVisibility(0);
            updateTopView(this.mSkinBeautyBoxGroup.getCheckedBeautyBoxId());
        } else if (i == R$id.beauty_radio_face_shape) {
            this.mBeautyMidLayout.setVisibility(0);
            this.mFaceShapeSelect.setVisibility(0);
            updateTopView(this.mFaceShapeBeautyBoxGroup.getCheckedBeautyBoxId());
        } else if (i == R$id.beauty_radio_beauty_filter) {
            this.mFilterRecyclerAdapter.setFilterType(1);
            this.mBeautyMidLayout.setVisibility(0);
            this.mFilterRecyclerView.setVisibility(0);
            if (this.mFilterTypeSelect != 1) {
                return;
            }
            this.mFilterRecyclerAdapter.setFilterProgress();
        } else if (i != R$id.beauty_radio_filter) {
        } else {
            this.mFilterRecyclerAdapter.setFilterType(0);
            this.mBeautyMidLayout.setVisibility(0);
            this.mFilterRecyclerView.setVisibility(0);
            if (this.mFilterTypeSelect != 0) {
                return;
            }
            this.mFilterRecyclerAdapter.setFilterProgress();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void seekToSeekBar(float f) {
        seekToSeekBar(f, 0, 100);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void seekToSeekBar(float f, int i, int i2) {
        if (f < FINAL_CHANE) {
            this.mBeautySeekBarLayout.setVisibility(0);
            this.mBeautySeekBar.setMin(i);
            this.mBeautySeekBar.setMax(i2);
            this.mBeautySeekBar.setProgress((int) ((f * (i2 - i)) + i));
        }
    }

    public float getFaceBeautyFilterLevel(String str) {
        Map<String, Float> map = this.mFilterLevelIntegerMap;
        Float f = map.get(FaceBeautyFilterLevel + str);
        float floatValue = f == null ? 1.0f : f.floatValue();
        setFaceBeautyFilterLevel(str, floatValue);
        return floatValue;
    }

    public void setFaceBeautyFilterLevel(String str, float f) {
        SPUtils.getInstance().put(str, f);
        Map<String, Float> map = this.mFilterLevelIntegerMap;
        map.put(FaceBeautyFilterLevel + str, Float.valueOf(f));
        OnFaceUnityControlListener onFaceUnityControlListener = this.mOnFaceUnityControlListener;
        if (onFaceUnityControlListener != null) {
            onFaceUnityControlListener.onFilterLevelSelected(f);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.view.faceunity.BeautyControlView$FilterRecyclerAdapter */
    /* loaded from: classes3.dex */
    public class FilterRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerHolder> {
        int filterType;

        FilterRecyclerAdapter() {
        }

        @Override // android.support.p005v7.widget.RecyclerView.Adapter
        @NonNull
        /* renamed from: onCreateViewHolder  reason: collision with other method in class */
        public HomeRecyclerHolder mo6739onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new HomeRecyclerHolder(LayoutInflater.from(BeautyControlView.this.mContext).inflate(R$layout.layout_beauty_control_recycler, viewGroup, false));
        }

        @Override // android.support.p005v7.widget.RecyclerView.Adapter
        public void onBindViewHolder(@NonNull HomeRecyclerHolder homeRecyclerHolder, @SuppressLint({"RecyclerView"}) final int i) {
            final List<Filter> items = getItems(this.filterType);
            homeRecyclerHolder.filterImg.setBackgroundResource(items.get(i).resId());
            homeRecyclerHolder.filterName.setText(items.get(i).description());
            if (BeautyControlView.this.mFilterPositionSelect == i && this.filterType == BeautyControlView.this.mFilterTypeSelect) {
                homeRecyclerHolder.filterImg.setImageResource(R$drawable.control_filter_select);
            } else {
                homeRecyclerHolder.filterImg.setImageResource(0);
            }
            homeRecyclerHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.faceunity.BeautyControlView.FilterRecyclerAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    BeautyControlView.this.mFilterPositionSelect = i;
                    FilterRecyclerAdapter filterRecyclerAdapter = FilterRecyclerAdapter.this;
                    BeautyControlView.this.mFilterTypeSelect = filterRecyclerAdapter.filterType;
                    FilterRecyclerAdapter.this.setFilterProgress();
                    FilterRecyclerAdapter.this.notifyDataSetChanged();
                    BeautyControlView.this.mBeautySeekBarLayout.setVisibility(0);
                    BeautyControlView.this.changeBottomLayoutAnimator(false);
                    if (BeautyControlView.this.mOnFaceUnityControlListener != null) {
                        BeautyControlView.this.mOnFaceUnityControlListener.onFilterSelected((Filter) items.get(BeautyControlView.this.mFilterPositionSelect));
                    }
                }
            });
        }

        @Override // android.support.p005v7.widget.RecyclerView.Adapter
        public int getItemCount() {
            return getItems(this.filterType).size();
        }

        public void setFilterType(int i) {
            this.filterType = i;
            notifyDataSetChanged();
        }

        public void setFilterLevels(float f) {
            BeautyControlView beautyControlView = BeautyControlView.this;
            beautyControlView.setFaceBeautyFilterLevel(getItems(beautyControlView.mFilterTypeSelect).get(BeautyControlView.this.mFilterPositionSelect).filterName(), f);
        }

        public void setFilterProgress() {
            BeautyControlView beautyControlView = BeautyControlView.this;
            beautyControlView.seekToSeekBar(beautyControlView.getFaceBeautyFilterLevel(getItems(beautyControlView.mFilterTypeSelect).get(BeautyControlView.this.mFilterPositionSelect).filterName()));
        }

        public List<Filter> getItems(int i) {
            if (i != 0 && i == 1) {
                return BeautyControlView.this.mBeautyFilters;
            }
            return BeautyControlView.this.mFilters;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: com.tomatolive.library.ui.view.faceunity.BeautyControlView$FilterRecyclerAdapter$HomeRecyclerHolder */
        /* loaded from: classes3.dex */
        public class HomeRecyclerHolder extends RecyclerView.ViewHolder {
            ImageView filterImg;
            TextView filterName;

            public HomeRecyclerHolder(View view) {
                super(view);
                this.filterImg = (ImageView) view.findViewById(R$id.control_recycler_img);
                this.filterName = (TextView) view.findViewById(R$id.control_recycler_text);
            }
        }
    }

    public void setOnBottomAnimatorChangeListener(OnBottomAnimatorChangeListener onBottomAnimatorChangeListener) {
        this.mOnBottomAnimatorChangeListener = onBottomAnimatorChangeListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeBottomLayoutAnimator(final boolean z) {
        ValueAnimator valueAnimator = this.mBottomLayoutAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mBottomLayoutAnimator.end();
        }
        final int height = getHeight();
        measure(0, View.MeasureSpec.makeMeasureSpec(0, 0));
        final int measuredHeight = getMeasuredHeight();
        if (height == measuredHeight) {
            return;
        }
        this.mBottomLayoutAnimator = ValueAnimator.ofInt(height, measuredHeight).setDuration(50L);
        this.mBottomLayoutAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.tomatolive.library.ui.view.faceunity.BeautyControlView.7
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                int intValue = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                ViewGroup.LayoutParams layoutParams = BeautyControlView.this.getLayoutParams();
                layoutParams.height = intValue;
                BeautyControlView.this.setLayoutParams(layoutParams);
                if (!z || BeautyControlView.this.mOnBottomAnimatorChangeListener == null) {
                    return;
                }
                int i = height;
                float f = ((intValue - i) * 1.0f) / (measuredHeight - i);
                OnBottomAnimatorChangeListener onBottomAnimatorChangeListener = BeautyControlView.this.mOnBottomAnimatorChangeListener;
                if (height > measuredHeight) {
                    f = 1.0f - f;
                }
                onBottomAnimatorChangeListener.onBottomAnimatorChangeListener(f);
            }
        });
        this.mBottomLayoutAnimator.start();
    }

    public void hideBottomLayoutAnimator() {
        clickViewBottomRadio(0);
        changeBottomLayoutAnimator(true);
    }

    public void setOnDescriptionShowListener(OnDescriptionShowListener onDescriptionShowListener) {
        this.mOnDescriptionShowListener = onDescriptionShowListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDescriptionShowStr(String str) {
        OnDescriptionShowListener onDescriptionShowListener = this.mOnDescriptionShowListener;
        if (onDescriptionShowListener != null) {
            onDescriptionShowListener.onDescriptionShowListener(str);
        }
    }

    public void setBeautyFilters(List<Filter> list) {
        this.mBeautyFilters = list;
    }

    public void setFilters(List<Filter> list) {
        this.mFilters = list;
    }
}
