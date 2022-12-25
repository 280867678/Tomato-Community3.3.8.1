package com.one.tomato.p085ui.publish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.widget.NestedScrollView;
import android.support.p005v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.broccoli.p150bh.R;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.one.tomato.adapter.PublishTagAdapter;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.dialog.PublishTipDialog;
import com.one.tomato.entity.CircleList;
import com.one.tomato.entity.PublishInfo;
import com.one.tomato.entity.event.SelectCircleEvent;
import com.one.tomato.entity.p079db.Tag;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.p080ui.circle.view.CircleAllActivity;
import com.one.tomato.p085ui.tag.TagSelectActivity;
import com.one.tomato.service.PublishService;
import com.one.tomato.thirdpart.pictureselector.FullyGridLayoutManager;
import com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter;
import com.one.tomato.thirdpart.pictureselector.SelectPicTypeUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.post.PapaPublishUtil;
import com.one.tomato.utils.post.PublishUtil;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_publish)
/* renamed from: com.one.tomato.ui.publish.PublishActivity */
/* loaded from: classes3.dex */
public class PublishActivity extends BaseActivity {
    private boolean checkCircle;
    @ViewInject(R.id.check_down_pay)
    private ImageView check_down_pay;
    @ViewInject(R.id.check_original)
    private ImageView check_original;
    @ViewInject(R.id.check_pay)
    private ImageView check_pay;
    @ViewInject(R.id.check_tip)
    private CheckBox check_tip;
    @ViewInject(R.id.et_content)
    private EditText et_content;
    @ViewInject(R.id.et_title)
    private EditText et_title;
    private PublishInfo imagePublishInfo;
    private CircleList intentCircleList;
    @ViewInject(R.id.iv_arrow_right)
    private ImageView iv_arrow_right;
    private float lastTouchY;
    @ViewInject(R.id.ll_add_media)
    private LinearLayout ll_add_media;
    private PublishInfo papaPublishInfo;
    private PublishTagAdapter publishTagAdapter;
    private int publishType;
    private PublishInfo readPublishInfo;
    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;
    @ViewInject(R.id.recycler_tag)
    private RecyclerView recycler_tag;
    @ViewInject(R.id.relate_check_agree)
    private RelativeLayout relate_check_agree;
    @ViewInject(R.id.relate_down_need_pay)
    private RelativeLayout relate_down_need_pay;
    @ViewInject(R.id.relate_look_need_pay)
    private RelativeLayout relate_look_need_pay;
    @ViewInject(R.id.rl_circle)
    private RelativeLayout rl_circle;
    @ViewInject(R.id.rl_media)
    private RelativeLayout rl_media;
    @ViewInject(R.id.rl_original)
    private RelativeLayout rl_original;
    @ViewInject(R.id.scrollView)
    private NestedScrollView scrollView;
    private SelectGridImageAdapter selectGridImageAdapter;
    private SelectPicTypeUtil selectPicTypeUtil;
    @ViewInject(R.id.text_agree)
    private TextView text_agree;
    @ViewInject(R.id.text_reward_down_num)
    private TextView text_reward_down_num;
    @ViewInject(R.id.text_reward_look_num)
    private TextView text_reward_look_num;
    @ViewInject(R.id.tv_circle)
    private TextView tv_circle;
    @ViewInject(R.id.tv_papa_tip)
    private TextView tv_papa_tip;
    @ViewInject(R.id.tv_publish)
    private TextView tv_publish;
    @ViewInject(R.id.tv_tag_choosed)
    private TextView tv_tag_choosed;
    private UserInfo userInfo;
    private PublishInfo videoPublishInfo;
    private ArrayList<String> optionList = new ArrayList<>();
    private HashMap<String, Integer> priceMap = new HashMap<>();

    public static void startActivity(Context context, PublishInfo publishInfo) {
        Intent intent = new Intent();
        intent.setClass(context, PublishActivity.class);
        intent.putExtra("publishInfo", publishInfo);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ImmersionBarUtil.init(this, findViewById(R.id.ll_title));
        initGridImgAdapter();
        setListener();
        initData();
    }

    private void initData() {
        PublishInfo publishInfo = (PublishInfo) getIntent().getParcelableExtra("publishInfo");
        if (publishInfo != null) {
            this.publishType = publishInfo.getPostType();
            if (this.publishType == 0) {
                this.publishType = 2;
                publishInfo.setPostType(this.publishType);
            }
            this.intentCircleList = publishInfo.getCircleList();
            int i = this.publishType;
            if (i == 1) {
                this.imagePublishInfo = publishInfo;
            } else if (i == 2) {
                this.videoPublishInfo = publishInfo;
            } else if (i == 3) {
                this.readPublishInfo = publishInfo;
            } else if (i == 4) {
                this.papaPublishInfo = publishInfo;
            } else {
                this.videoPublishInfo = publishInfo;
            }
            initView();
        } else {
            setPublishInfo(2);
        }
        updateCheckTip();
    }

    private void initChoosePriceList(String str) {
        double d;
        this.optionList.clear();
        this.priceMap.clear();
        int maxPrice = str.equals(AppUtil.getString(R.string.up_look_tiltle)) ? DBUtil.getUserInfo().getMaxPrice() : 50;
        int i = 0;
        while (i < maxPrice) {
            i++;
            int i2 = i * 10;
            ArrayList<String> arrayList = this.optionList;
            StringBuilder sb = new StringBuilder();
            sb.append("¥");
            sb.append(FormatUtil.formatTomato2RMB(i2));
            arrayList.add(sb.toString());
            this.priceMap.put("¥" + FormatUtil.formatTomato2RMB(d), Integer.valueOf(i2));
        }
    }

    private void setPublishInfo(int i) {
        if (this.publishType == i) {
            return;
        }
        this.publishType = i;
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    if (this.readPublishInfo == null) {
                        this.readPublishInfo = new PublishInfo();
                        this.readPublishInfo.setPostType(i);
                    }
                } else if (i == 4 && this.papaPublishInfo == null) {
                    this.papaPublishInfo = new PublishInfo();
                    this.papaPublishInfo.setPostType(i);
                }
            } else if (this.videoPublishInfo == null) {
                this.videoPublishInfo = new PublishInfo();
                this.videoPublishInfo.setPostType(i);
            }
        } else if (this.imagePublishInfo == null) {
            this.imagePublishInfo = new PublishInfo();
            this.imagePublishInfo.setPostType(i);
        }
        initView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PublishInfo getPublishInfo() {
        int i = this.publishType;
        if (i != 1) {
            if (i == 2) {
                return this.videoPublishInfo;
            }
            if (i == 3) {
                return this.readPublishInfo;
            }
            if (i == 4) {
                return this.papaPublishInfo;
            }
            return null;
        }
        return this.imagePublishInfo;
    }

    private void initView() {
        this.userInfo = DBUtil.getUserInfo();
        selectItem();
        PublishInfo publishInfo = getPublishInfo();
        if (this.publishType == 4) {
            this.rl_circle.setVisibility(8);
        } else {
            this.rl_circle.setVisibility(0);
        }
        CircleList circleList = this.intentCircleList;
        if (circleList != null) {
            publishInfo.setCircleList(circleList);
            this.iv_arrow_right.setVisibility(4);
        }
        CircleList circleList2 = publishInfo.getCircleList();
        if (circleList2 != null && !TextUtils.isEmpty(circleList2.getName())) {
            this.tv_circle.setText(circleList2.getName());
        } else {
            this.tv_circle.setText("");
        }
        if (this.publishType == 3) {
            this.et_title.getPaint().setTypeface(Typeface.defaultFromStyle(1));
        } else {
            this.et_title.getPaint().setTypeface(Typeface.defaultFromStyle(0));
            if (this.publishType == 4) {
                this.et_title.setHint(R.string.papa_publish_title_hint);
            }
        }
        if (!TextUtils.isEmpty(publishInfo.getTitle())) {
            this.et_title.setText(publishInfo.getTitle());
            this.et_title.setSelection(publishInfo.getTitle().trim().length());
        } else {
            this.et_title.setText("");
        }
        if (this.publishType == 3) {
            this.et_content.setVisibility(0);
            if (!TextUtils.isEmpty(publishInfo.getDescription())) {
                this.et_content.setText(publishInfo.getDescription());
                this.et_content.setSelection(publishInfo.getDescription().trim().length());
            } else {
                this.et_content.setText("");
            }
        } else {
            this.et_content.setVisibility(8);
        }
        if (this.publishType == 3) {
            this.rl_media.setVisibility(8);
        } else {
            this.rl_media.setVisibility(0);
            if (publishInfo.getSelectList() != null && !publishInfo.getSelectList().isEmpty()) {
                this.ll_add_media.setVisibility(8);
                this.tv_papa_tip.setVisibility(8);
                this.recyclerView.setVisibility(0);
                if (publishInfo.getPostType() == 1) {
                    this.selectGridImageAdapter.setSelectMax(9);
                } else if (publishInfo.getPostType() == 2) {
                    this.selectGridImageAdapter.setSelectMax(1);
                } else if (publishInfo.getPostType() == 4) {
                    this.selectGridImageAdapter.setSelectMax(1);
                }
                this.selectGridImageAdapter.setList(publishInfo.getSelectList());
                this.selectGridImageAdapter.notifyDataSetChanged();
            } else {
                this.ll_add_media.setVisibility(0);
                this.recyclerView.setVisibility(8);
            }
        }
        initTag(publishInfo);
        this.check_down_pay.setImageResource(R.drawable.icon_select_n);
        this.check_down_pay.setSelected(false);
        this.check_pay.setImageResource(R.drawable.icon_select_n);
        this.check_pay.setSelected(false);
        this.text_reward_down_num.setText(AppUtil.getString(R.string.up_pay_tip));
        this.text_reward_look_num.setText(AppUtil.getString(R.string.up_pay_tip));
        String upHostType = DBUtil.getUserInfo().getUpHostType();
        if (!TextUtils.isEmpty(upHostType) && upHostType.equals("3")) {
            this.rl_original.setVisibility(0);
        } else {
            this.rl_original.setVisibility(8);
        }
        if (DBUtil.getUserInfo().getUpLevel() > 0 || upHostType.equals("3")) {
            this.relate_look_need_pay.setVisibility(0);
            this.relate_down_need_pay.setVisibility(0);
            if (this.userInfo.getMaxPublishCnt() != -1) {
                TextView textView = this.text_reward_look_num;
                textView.setText(AppUtil.getString(R.string.up_pay_tip) + ConstantUtils.PLACEHOLDER_STR_ONE + (this.userInfo.getMaxPublishCnt() - this.userInfo.getAlreadyPublishCnt()) + "/" + this.userInfo.getMaxPublishCnt());
            }
        } else {
            this.relate_look_need_pay.setVisibility(8);
            this.relate_down_need_pay.setVisibility(8);
        }
        if (this.publishType == 3) {
            this.relate_down_need_pay.setVisibility(8);
        } else {
            this.relate_down_need_pay.setVisibility(0);
        }
    }

    private void selectItem() {
        hideKeyBoard(this);
        checkIsPublish();
    }

    private void initGridImgAdapter() {
        this.selectPicTypeUtil = new SelectPicTypeUtil(this);
        this.recyclerView.setLayoutManager(new FullyGridLayoutManager(this, 3, 1, false));
        this.selectGridImageAdapter = new SelectGridImageAdapter(this);
        this.recyclerView.setAdapter(this.selectGridImageAdapter);
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00e8  */
    @Event({R.id.iv_finish, R.id.tv_publish, R.id.rl_circle, R.id.ll_add_media, R.id.rl_tag})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void onClick(View view) {
        int i;
        switch (view.getId()) {
            case R.id.iv_finish /* 2131297193 */:
                onBackPressed();
                return;
            case R.id.ll_add_media /* 2131297492 */:
                int i2 = this.publishType;
                if (i2 == 1) {
                    this.selectPicTypeUtil.selectCommonPhoto(9, false, false, false, getPublishInfo().getSelectList());
                    return;
                } else if (i2 == 2) {
                    this.selectPicTypeUtil.selectCommonVideo(1, getPublishInfo().getSelectList());
                    return;
                } else if (i2 != 4) {
                    return;
                } else {
                    this.selectPicTypeUtil.selectLimitSecondVideo(1, 600, 1, 600, getPublishInfo().getSelectList());
                    return;
                }
            case R.id.rl_circle /* 2131298048 */:
                CircleAllActivity.Companion.startAct(this, true);
                return;
            case R.id.rl_tag /* 2131298147 */:
                int i3 = 6;
                int i4 = this.publishType == 4 ? 3 : 6;
                if (getPublishInfo().getTagList() != null && getPublishInfo().getTagList().size() >= i4) {
                    ToastUtil.showCenterToast(AppUtil.getString(R.string.publish_tag_choose_manle));
                    return;
                }
                ArrayList<Tag> tagList = getPublishInfo().getTagList();
                if (this.publishType == 4) {
                    i3 = 3;
                }
                TagSelectActivity.startActivity(this, tagList, i3);
                return;
            case R.id.tv_publish /* 2131299270 */:
                if (TextUtils.isEmpty(getPublishInfo().getTitle()) || getPublishInfo().getTitle().length() < 4) {
                    ToastUtil.showCenterToast(AppUtil.getString(R.string.publish_pelase_input_title));
                    return;
                }
                int i5 = this.publishType;
                if ((i5 == 1 || i5 == 2 || i5 == 4) && (getPublishInfo().getSelectList() == null || getPublishInfo().getSelectList().size() == 0)) {
                    if (this.publishType == 1) {
                        ToastUtil.showCenterToast(AppUtil.getString(R.string.publish_pelase_input_pic));
                        return;
                    } else {
                        ToastUtil.showCenterToast(AppUtil.getString(R.string.publish_pelase_input_video));
                        return;
                    }
                }
                int i6 = this.publishType;
                if ((i6 == 1 || i6 == 2 || i6 == 3) && getPublishInfo().getCircleList() == null) {
                    ToastUtil.showCenterToast(AppUtil.getString(R.string.publish_pelase_input_circle));
                    return;
                } else if (this.publishType == 3 && TextUtils.isEmpty(getPublishInfo().getDescription())) {
                    ToastUtil.showCenterToast(AppUtil.getString(R.string.publish_pelase_input_content));
                    return;
                } else {
                    String upHostType = DBUtil.getUserInfo().getUpHostType();
                    int upLevel = DBUtil.getUserInfo().getUpLevel();
                    int i7 = 0;
                    if (upLevel > 0 || "3".equals(upHostType)) {
                        if (this.check_pay.isSelected()) {
                            String charSequence = this.text_reward_look_num.getText().toString();
                            if (!TextUtils.isEmpty(charSequence)) {
                                String trim = charSequence.trim();
                                if (this.priceMap.containsKey(trim)) {
                                    i = this.priceMap.get(trim).intValue();
                                    if (i >= 10) {
                                        ToastUtil.showCenterToast(AppUtil.getString(R.string.up_input_10));
                                        return;
                                    }
                                    PublishInfo publishInfo = getPublishInfo();
                                    if (publishInfo.getPostType() == 1 && publishInfo.getSelectList().size() <= 1) {
                                        ToastUtil.showCenterToast(AppUtil.getString(R.string.post_publish_image_2));
                                        return;
                                    }
                                }
                            }
                            i = 0;
                            if (i >= 10) {
                            }
                        } else {
                            i = 0;
                        }
                        if ("3".equals(upHostType) && this.check_original.isSelected()) {
                            getPublishInfo().setOriginal(true);
                        }
                    } else {
                        i = 0;
                    }
                    if (upLevel <= 0 && !"3".equals(upHostType)) {
                        i7 = -1;
                    } else if (this.check_down_pay.isSelected()) {
                        String charSequence2 = this.text_reward_down_num.getText().toString();
                        if (!TextUtils.isEmpty(charSequence2)) {
                            String trim2 = charSequence2.trim();
                            if (this.priceMap.containsKey(trim2)) {
                                i7 = this.priceMap.get(trim2).intValue();
                            }
                        }
                    }
                    if (!this.check_tip.isChecked()) {
                        ToastUtil.showCenterToast(AppUtil.getString(R.string.publish_post_guize));
                        return;
                    }
                    finish();
                    PublishService.startService(this, getPublishInfo(), i, i7);
                    return;
                }
            default:
                return;
        }
    }

    private void setListener() {
        this.selectGridImageAdapter.setOnItemClickListener(new SelectGridImageAdapter.OnItemClickListener() { // from class: com.one.tomato.ui.publish.PublishActivity.1
            @Override // com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter.OnItemClickListener
            public void onItemClick(int i, View view) {
                if (PublishActivity.this.getPublishInfo().getSelectList().size() > 0) {
                    LocalMedia localMedia = PublishActivity.this.getPublishInfo().getSelectList().get(i);
                    int pictureToVideo = PictureMimeType.pictureToVideo(localMedia.getPictureType());
                    if (pictureToVideo == 1) {
                        PictureSelector.create(PublishActivity.this).themeStyle(2131821197).openExternalPreview(i, PublishActivity.this.getPublishInfo().getSelectList());
                    } else if (pictureToVideo != 2) {
                    } else {
                        PictureSelector.create(PublishActivity.this).externalPictureVideo(localMedia.getPath());
                    }
                }
            }
        });
        this.selectGridImageAdapter.setOnItemChangeListener(new SelectGridImageAdapter.OnItemChangeListener() { // from class: com.one.tomato.ui.publish.PublishActivity.2
            @Override // com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter.OnItemChangeListener
            public void onItemRemove(int i) {
            }

            @Override // com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter.OnItemChangeListener
            public void onItemClear() {
                PublishInfo publishInfo = PublishActivity.this.getPublishInfo();
                publishInfo.setSelectList(null);
                publishInfo.setImgUrl("");
                publishInfo.setVideoUrl("");
                PublishActivity.this.ll_add_media.setVisibility(0);
                PublishActivity.this.recyclerView.setVisibility(8);
                PublishActivity.this.checkIsPublish();
            }
        });
        this.selectGridImageAdapter.setOnAddPicClickListener(new SelectGridImageAdapter.OnAddPicClickListener() { // from class: com.one.tomato.ui.publish.PublishActivity.3
            @Override // com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter.OnAddPicClickListener
            public void onAddPicClick() {
                if (PublishActivity.this.publishType == 1) {
                    PublishActivity.this.selectPicTypeUtil.selectCommonPhoto(9, false, false, false, PublishActivity.this.getPublishInfo().getSelectList());
                } else if (PublishActivity.this.publishType == 2) {
                    PublishActivity.this.selectPicTypeUtil.selectCommonVideo(1, PublishActivity.this.getPublishInfo().getSelectList());
                } else if (PublishActivity.this.publishType != 4) {
                } else {
                    PublishActivity.this.selectPicTypeUtil.selectLimitSecondVideo(1, 600, 1, 600, PublishActivity.this.getPublishInfo().getSelectList());
                }
            }
        });
        this.et_title.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.publish.PublishActivity.4
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                PublishActivity.this.getPublishInfo().setTitle(editable.toString().trim());
                PublishActivity.this.checkIsPublish();
            }
        });
        this.et_title.setOnFocusChangeListener(new View.OnFocusChangeListener(this) { // from class: com.one.tomato.ui.publish.PublishActivity.5
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
            }
        });
        this.et_content.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.publish.PublishActivity.6
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                PublishActivity.this.getPublishInfo().setDescription(editable.toString().trim());
                PublishActivity.this.checkIsPublish();
            }
        });
        this.et_content.setOnFocusChangeListener(new View.OnFocusChangeListener(this) { // from class: com.one.tomato.ui.publish.PublishActivity.7
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
            }
        });
        this.scrollView.setOnTouchListener(new View.OnTouchListener() { // from class: com.one.tomato.ui.publish.PublishActivity.8
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action != 0) {
                    if (action != 1 || motionEvent.getY() - PublishActivity.this.lastTouchY <= 50.0f) {
                        return false;
                    }
                    PublishActivity publishActivity = PublishActivity.this;
                    publishActivity.hideKeyBoard(publishActivity);
                    return false;
                }
                PublishActivity.this.lastTouchY = 0.0f;
                PublishActivity.this.lastTouchY = motionEvent.getY();
                return false;
            }
        });
        this.relate_look_need_pay.setOnClickListener(new View.OnClickListener(this) { // from class: com.one.tomato.ui.publish.PublishActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.relate_down_need_pay.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.publish.PublishActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PublishActivity.this.check_down_pay.setSelected(true);
            }
        });
        this.check_pay.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.publish.PublishActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int maxPublishCnt = PublishActivity.this.userInfo.getMaxPublishCnt();
                int alreadyPublishCnt = PublishActivity.this.userInfo.getAlreadyPublishCnt();
                if (PublishActivity.this.check_pay.isSelected()) {
                    PublishActivity.this.check_pay.setSelected(false);
                    PublishActivity.this.check_pay.setImageResource(R.drawable.icon_select_n);
                    TextView textView = PublishActivity.this.text_reward_look_num;
                    textView.setText(AppUtil.getString(R.string.up_pay_tip) + ConstantUtils.PLACEHOLDER_STR_ONE + (PublishActivity.this.userInfo.getMaxPublishCnt() - PublishActivity.this.userInfo.getAlreadyPublishCnt()) + "/" + PublishActivity.this.userInfo.getMaxPublishCnt());
                } else if (maxPublishCnt == -1 || maxPublishCnt - alreadyPublishCnt > 0) {
                    PublishActivity.this.chooseCionNum(AppUtil.getString(R.string.up_look_tiltle));
                } else {
                    final CustomAlertDialog customAlertDialog = new CustomAlertDialog(((BaseActivity) PublishActivity.this).mContext);
                    customAlertDialog.setTitle(AppUtil.getString(R.string.common_notify));
                    customAlertDialog.setMessage(AppUtil.getString(R.string.up_publish_pay_num_over));
                    customAlertDialog.setBottomVerticalLineVisible(false);
                    customAlertDialog.setBottomHorizontalLineVisible(false);
                    customAlertDialog.bottomButtonVisiblity(1);
                    customAlertDialog.setCancelButton(AppUtil.getString(R.string.up_publish_no_pay_num_tip), new View.OnClickListener(this) { // from class: com.one.tomato.ui.publish.PublishActivity.11.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view2) {
                            customAlertDialog.dismiss();
                        }
                    });
                }
            }
        });
        this.check_down_pay.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.publish.PublishActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PublishActivity.this.check_down_pay.isSelected()) {
                    PublishActivity.this.check_down_pay.setSelected(false);
                    PublishActivity.this.check_down_pay.setImageResource(R.drawable.icon_select_n);
                    PublishActivity.this.text_reward_down_num.setText(AppUtil.getString(R.string.up_pay_tip));
                    return;
                }
                String upHostType = DBUtil.getUserInfo().getUpHostType();
                if ("1".equals(upHostType) || "2".equals(upHostType) || "3".equals(upHostType)) {
                    PublishActivity.this.chooseCionNum(AppUtil.getString(R.string.up_down_price));
                    return;
                }
                PublishActivity.this.check_down_pay.setSelected(true);
                PublishActivity.this.check_down_pay.setImageResource(R.drawable.icon_select_s);
            }
        });
        this.relate_check_agree.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.publish.PublishActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PublishActivity.this.updateCheckTip();
            }
        });
        this.check_tip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this) { // from class: com.one.tomato.ui.publish.PublishActivity.14
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    PreferencesUtil.getInstance().putBoolean("publish_tip_check", true);
                } else {
                    PreferencesUtil.getInstance().putBoolean("publish_tip_check", false);
                }
            }
        });
        this.check_original.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.publish.-$$Lambda$PublishActivity$kN_gcxE-jWln03RygInp7jGT2vA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PublishActivity.this.lambda$setListener$0$PublishActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$setListener$0$PublishActivity(View view) {
        if (this.check_original.isSelected()) {
            this.check_original.setSelected(false);
            this.check_original.setImageResource(R.drawable.icon_select_n);
            return;
        }
        this.check_original.setSelected(true);
        this.check_original.setImageResource(R.drawable.icon_select_s);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkIsPublish() {
        int i = this.publishType;
        if (i == 1 || i == 2) {
            if (getPublishInfo().getCircleList() != null && !TextUtils.isEmpty(getPublishInfo().getTitle()) && getPublishInfo().getTitle().length() >= 4 && getPublishInfo().getSelectList() != null && getPublishInfo().getSelectList().size() > 0) {
                this.tv_publish.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner5_coloraccent));
            } else {
                this.tv_publish.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner5_ffcbcb));
            }
        } else if (i == 3) {
            if (getPublishInfo().getCircleList() != null && !TextUtils.isEmpty(getPublishInfo().getTitle()) && getPublishInfo().getTitle().length() >= 4 && !TextUtils.isEmpty(getPublishInfo().getDescription())) {
                this.tv_publish.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner5_coloraccent));
            } else {
                this.tv_publish.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner5_ffcbcb));
            }
        } else if (i != 4) {
        } else {
            if (!TextUtils.isEmpty(getPublishInfo().getTitle()) && getPublishInfo().getTitle().length() >= 4 && getPublishInfo().getSelectList() != null && getPublishInfo().getSelectList().size() > 0) {
                this.tv_publish.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner5_coloraccent));
            } else {
                this.tv_publish.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_solid_corner5_ffcbcb));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.checkCircle) {
            checkIsPublish();
            this.checkCircle = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCheckTip() {
        String string = AppUtil.getString(R.string.publish_post_tishi_2);
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#5B92E1")), string.indexOf("《"), string.indexOf("》"), 18);
        this.text_agree.setText(spannableString);
        if (PreferencesUtil.getInstance().getBoolean("publish_tip_check", true)) {
            PreferencesUtil.getInstance().putBoolean("publish_tip_check", true);
            this.check_tip.setChecked(true);
        } else {
            this.check_tip.setChecked(false);
        }
        PublishTipDialog publishTipDialog = new PublishTipDialog(this.mContext);
        publishTipDialog.dismissCallBack(new Functions<Unit>() { // from class: com.one.tomato.ui.publish.PublishActivity.15
            @Override // kotlin.jvm.functions.Functions
            /* renamed from: invoke  reason: collision with other method in class */
            public Unit mo6822invoke() {
                if (!PreferencesUtil.getInstance().getBoolean("publish_tip_check", true)) {
                    PublishActivity.this.check_tip.setChecked(false);
                    return null;
                }
                PreferencesUtil.getInstance().putBoolean("publish_tip_check", true);
                PublishActivity.this.check_tip.setChecked(true);
                return null;
            }
        });
        publishTipDialog.show();
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        if (this.publishType == 4) {
            PapaPublishUtil.getInstance().onPublishCancel();
        } else {
            PublishUtil.getInstance().onPublishCancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i == 4) {
                getPublishInfo().setTagList(intent.getExtras().getParcelableArrayList("intent_list"));
                initTag(getPublishInfo());
            } else if (i == 20) {
                CircleList circleList = (CircleList) intent.getExtras().getParcelable("select_data");
                if (circleList == null) {
                    return;
                }
                getPublishInfo().setCircleList(circleList);
                this.tv_circle.setText(circleList.getName());
                this.checkCircle = true;
            } else if (i != 188) {
            } else {
                this.recyclerView.setVisibility(0);
                this.ll_add_media.setVisibility(8);
                this.tv_papa_tip.setVisibility(8);
                ArrayList<LocalMedia> arrayList = new ArrayList<>();
                arrayList.addAll(PictureSelector.obtainMultipleResult(intent));
                Iterator<LocalMedia> it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    Log.i("图片-----》", it2.next().getPath());
                }
                getPublishInfo().setSelectList(arrayList);
                if (getPublishInfo().getPostType() == 1) {
                    this.selectGridImageAdapter.setSelectMax(9);
                } else if (getPublishInfo().getPostType() == 2) {
                    this.selectGridImageAdapter.setSelectMax(1);
                } else if (getPublishInfo().getPostType() == 4) {
                    this.selectGridImageAdapter.setSelectMax(1);
                }
                this.selectGridImageAdapter.setList(arrayList);
                this.selectGridImageAdapter.notifyDataSetChanged();
                checkIsPublish();
            }
        }
    }

    private void initTag(PublishInfo publishInfo) {
        if (publishInfo.getTagList() != null && !publishInfo.getTagList().isEmpty()) {
            ArrayList<Tag> tagList = publishInfo.getTagList();
            if (this.publishTagAdapter == null) {
                this.publishTagAdapter = new PublishTagAdapter();
                FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this.mContext);
                flexboxLayoutManager.setFlexWrap(1);
                flexboxLayoutManager.setAlignItems(0);
                this.recycler_tag.setLayoutManager(flexboxLayoutManager);
                this.recycler_tag.setAdapter(this.publishTagAdapter);
            }
            this.publishTagAdapter.setNewData(tagList);
            this.publishTagAdapter.addCallBackRemoveItem(new Function1<Integer, Unit>() { // from class: com.one.tomato.ui.publish.PublishActivity.16
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public Unit mo6794invoke(Integer num) {
                    if (PublishActivity.this.publishType == 4) {
                        TextView textView = PublishActivity.this.tv_tag_choosed;
                        textView.setText(num + "/3");
                        return null;
                    }
                    TextView textView2 = PublishActivity.this.tv_tag_choosed;
                    textView2.setText(num + "/6");
                    return null;
                }
            });
            if (this.publishType == 4) {
                TextView textView = this.tv_tag_choosed;
                textView.setText(tagList.size() + "/3");
                return;
            }
            TextView textView2 = this.tv_tag_choosed;
            textView2.setText(tagList.size() + "/6");
            return;
        }
        this.tv_tag_choosed.setText(AppUtil.getString(R.string.post_publish_hint_tag));
        PublishTagAdapter publishTagAdapter = this.publishTagAdapter;
        if (publishTagAdapter == null) {
            return;
        }
        publishTagAdapter.getData().clear();
        this.publishTagAdapter.notifyDataSetChanged();
    }

    public void onEventMainThread(SelectCircleEvent selectCircleEvent) {
        CircleList circleList = selectCircleEvent.circleList;
        getPublishInfo().setCircleList(circleList);
        this.tv_circle.setText(circleList.getName());
        this.checkCircle = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void chooseCionNum(final String str) {
        initChoosePriceList(str);
        PickerOptions pickerOptions = new PickerOptions(1);
        pickerOptions.context = this;
        pickerOptions.textContentCancel = str;
        pickerOptions.textColorCancel = getResources().getColor(R.color.text_dark);
        pickerOptions.textColorConfirm = getResources().getColor(R.color.colorAccent);
        pickerOptions.bgColorTitle = getResources().getColor(R.color.white);
        pickerOptions.textSizeSubmitCancel = 16;
        pickerOptions.textSizeContent = 24;
        pickerOptions.lineSpacingMultiplier = 2.0f;
        pickerOptions.optionsSelectListener = new OnOptionsSelectListener() { // from class: com.one.tomato.ui.publish.PublishActivity.17
            @Override // com.bigkoo.pickerview.listener.OnOptionsSelectListener
            public void onOptionsSelect(int i, int i2, int i3, View view) {
                String str2 = (String) PublishActivity.this.optionList.get(i);
                if (str.equals(AppUtil.getString(R.string.up_look_tiltle))) {
                    PublishActivity.this.text_reward_look_num.setText(str2);
                    PublishActivity.this.check_pay.setSelected(true);
                    PublishActivity.this.check_pay.setImageResource(R.drawable.icon_select_s);
                }
                if (str.equals(AppUtil.getString(R.string.up_down_price))) {
                    PublishActivity.this.check_down_pay.setSelected(true);
                    PublishActivity.this.check_down_pay.setImageResource(R.drawable.icon_select_s);
                    PublishActivity.this.text_reward_down_num.setText(str2);
                }
            }
        };
        OptionsPickerView optionsPickerView = new OptionsPickerView(pickerOptions);
        optionsPickerView.setPicker(this.optionList);
        optionsPickerView.show();
    }
}
