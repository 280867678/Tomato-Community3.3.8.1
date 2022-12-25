package com.one.tomato.mvp.p080ui.post.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.p005v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.one.tomato.adapter.PostCommentReplyAdapter;
import com.one.tomato.adapter.PostDetailImgUploadAdapter;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.dialog.ImageVerifyDialog;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.CommentReplyList;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.ReplysVoListBean;
import com.one.tomato.entity.event.AddOrDeleteReplyEvent;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.emotion.EmotionEditText;
import com.one.tomato.thirdpart.emotion.EmotionUtil;
import com.one.tomato.thirdpart.emotion.PostKeyboard;
import com.one.tomato.thirdpart.pictureselector.SelectPicTypeUtil;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity;
import com.one.tomato.utils.AppSecretUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.TTUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UserPermissionUtil;
import com.one.tomato.utils.ViewUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.utils.post.CommentCheckUtil;
import com.p096sj.emoji.EmojiBean;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.utils.ConstantUtils;
import de.greenrobot.event.EventBus;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import sj.keyboard.data.EmoticonEntity;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.utils.EmoticonsKeyboardUtils;

@ContentView(R.layout.activity_post_comment_reply)
/* renamed from: com.one.tomato.mvp.ui.post.view.PostCommentReplyActivity */
/* loaded from: classes3.dex */
public class PostCommentReplyActivity extends BaseRecyclerViewActivity {
    private static int articleIds;
    @ViewInject(R.id.back)
    private ImageView back;
    private int commentId;
    private CommentReplyList commentReplyList;
    private int commentType;
    @ViewInject(R.id.comment_title_content_t)
    private TextView comment_title_content_t;
    @ViewInject(R.id.comment_title_gb)
    private View comment_title_gb;
    @ViewInject(R.id.comment_title_icon_i)
    private ImageView comment_title_icon_i;
    @ViewInject(R.id.comment_title_name_t)
    private TextView comment_title_name_t;
    @ViewInject(R.id.comment_title_timet_t)
    private TextView comment_title_timet_t;
    protected EmotionEditText et_input;
    private ImageVerifyDialog imageVerifyDialog;
    protected ImageView iv_choose_img;
    protected ImageView iv_emotion;
    private LevelBean levelBean;
    private int postMemberId;
    @ViewInject(R.id.post_keyboard)
    private PostKeyboard post_keyboard;
    private RecyclerView recyclerView_upload;
    private PostCommentReplyAdapter replyAdapter;
    private String replyTitle;
    private SelectPicTypeUtil selectPicTypeUtil;
    @ViewInject(R.id.title)
    private TextView title;
    private TTUtil ttUtil;
    private PostDetailImgUploadAdapter uploadAdapter;
    private List<LocalMedia> selectList = new ArrayList();
    private Map<String, String> contentReplyMap = new HashMap();
    private int tempCommentId = 0;
    private boolean replySending = false;
    private ArrayList<LocalMedia> uploadSuccessList = new ArrayList<>();

    public static void startActivity(Context context, int i, int i2) {
        Intent intent = new Intent(context, PostCommentReplyActivity.class);
        intent.putExtra("commentId", i);
        intent.putExtra("postMemberId", i2);
        ((Activity) context).startActivityForResult(intent, 111);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initData();
        initKeyboard();
        initAdapter();
        setListener();
        getPostDetailFromServer();
    }

    private void initData() {
        this.levelBean = DBUtil.getLevelBean();
        this.refreshLayout.autoRefresh(100);
        this.commentId = getIntent().getExtras().getInt("commentId");
        this.postMemberId = getIntent().getExtras().getInt("postMemberId");
        this.commentType = 1;
        this.title.setText(R.string.post_comment_detail);
        this.selectPicTypeUtil = new SelectPicTypeUtil(this);
    }

    private void initKeyboard() {
        this.post_keyboard.setAdapter(EmotionUtil.getCommonAdapter(this, new EmoticonClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PostCommentReplyActivity.1
            @Override // sj.keyboard.interfaces.EmoticonClickListener
            public void onEmoticonClick(Object obj, int i, boolean z) {
                if (z) {
                    EmotionUtil.delClick(PostCommentReplyActivity.this.post_keyboard.getEtChat());
                } else if (obj == null) {
                } else {
                    String str = null;
                    if (obj instanceof EmojiBean) {
                        str = ((EmojiBean) obj).emoji;
                    } else if (obj instanceof EmoticonEntity) {
                        str = ((EmoticonEntity) obj).getContent();
                    }
                    if (TextUtils.isEmpty(str)) {
                        return;
                    }
                    PostCommentReplyActivity.this.et_input.getText().insert(PostCommentReplyActivity.this.et_input.getSelectionStart(), str);
                }
            }
        }));
        this.recyclerView_upload = this.post_keyboard.getRecyclerView_upload();
        this.recyclerView_upload.setVisibility(0);
        this.iv_emotion = this.post_keyboard.getIv_emotion();
        this.iv_emotion.setVisibility(0);
        this.et_input = this.post_keyboard.getEtChat();
        this.et_input.setVisibility(0);
        this.iv_choose_img = this.post_keyboard.getIv_choose_img();
        this.iv_choose_img.setVisibility(0);
        this.post_keyboard.getRl_comment().setVisibility(0);
        if (isLogin()) {
            if (DBUtil.getUserInfo().getVipType() == 1) {
                this.et_input.setHint(R.string.post_comment_reply_hint);
                return;
            } else if (this.levelBean.getCurrentLevelIndex() < 2) {
                this.et_input.setHint(R.string.level_tip_dialog);
                return;
            } else if (this.levelBean.getReplyCount() == 0) {
                this.et_input.setHint(R.string.credit_edit_reply_deny);
                return;
            } else if (this.levelBean.getReplyCount_times() == this.levelBean.getReplyCount()) {
                this.et_input.setHint(R.string.credit_edit_reply_no_left);
                return;
            } else {
                this.et_input.setHint(R.string.post_comment_reply_hint);
                return;
            }
        }
        this.et_input.setHint(R.string.post_comment_reply_hint);
    }

    private void initAdapter() {
        this.replyAdapter = new PostCommentReplyAdapter(this, this.recyclerView, this.postMemberId);
        this.recyclerView.setAdapter(this.replyAdapter);
    }

    private void setListener() {
        this.back.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PostCommentReplyActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PostCommentReplyActivity.this.onBackPressed();
            }
        });
        this.iv_choose_img.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PostCommentReplyActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PostCommentReplyActivity.this.startLoginActivity()) {
                    return;
                }
                if (DBUtil.getSystemParam().getPinglunPic() != 0) {
                    PostCommentReplyActivity.this.selectResource();
                } else {
                    ToastUtil.showCenterToast((int) R.string.post_comment_image_not_support);
                }
            }
        });
        this.et_input.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.post.view.PostCommentReplyActivity.4
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() == 0) {
                    PostCommentReplyActivity.this.commentType = 1;
                }
            }
        });
        this.et_input.setOnTouchListener(new View.OnTouchListener() { // from class: com.one.tomato.mvp.ui.post.view.PostCommentReplyActivity.5
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    if (!DBUtil.getLoginInfo().isLogin()) {
                        LoginActivity.Companion.startActivity(((BaseActivity) PostCommentReplyActivity.this).mContext);
                        return true;
                    } else if (!UserPermissionUtil.getInstance().isPermissionEnable(3)) {
                        return true;
                    }
                }
                PostCommentReplyActivity.this.et_input.setFocusable(true);
                PostCommentReplyActivity.this.et_input.setFocusableInTouchMode(true);
                return false;
            }
        });
        this.et_input.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.one.tomato.mvp.ui.post.view.PostCommentReplyActivity.6
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 4) {
                    PostCommentReplyActivity.this.sendCommentReply();
                    return true;
                }
                return false;
            }
        });
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void loadMore() {
        getListFromServer(2);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void refresh() {
        getListFromServer(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectResource() {
        new RxPermissions(this).request("android.permission.CAMERA").subscribe(new Observer<Boolean>() { // from class: com.one.tomato.mvp.ui.post.view.PostCommentReplyActivity.7
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Boolean bool) {
                if (bool.booleanValue()) {
                    PostCommentReplyActivity.this.selectPicTypeUtil.selectCommonPhoto(9, false, false, false, PostCommentReplyActivity.this.selectList);
                } else {
                    ToastUtil.showCenterToast((int) R.string.permission_camera);
                }
            }
        });
    }

    private void getListFromServer(int i) {
        if (i == 1) {
            this.pageNo = 1;
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/comment/detail");
        tomatoParams.addParameter("commentId", Integer.valueOf(this.commentId));
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 1, CommentReplyList.class, i));
    }

    private void getPostDetailFromServer() {
        if (articleIds != 0) {
            this.comment_title_gb.setVisibility(0);
            TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/article/recommend/detail");
            tomatoParams.addParameter(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(articleIds));
            tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
            tomatoParams.post(new TomatoCallback((ResponseObserver) this, 9, PostList.class));
            return;
        }
        this.comment_title_gb.setVisibility(8);
    }

    public void onItemContentClick(int i, ReplysVoListBean replysVoListBean) {
        if (i == 0) {
            this.et_input.setText("");
            showKeyBoard(this);
            this.commentType = 1;
            this.contentReplyMap.clear();
        } else if (DBUtil.getMemberId() == replysVoListBean.getFromUserId()) {
            ToastUtil.showCenterToast((int) R.string.post_comment_reply_for_self_tip);
        } else if (replysVoListBean.getFromUserId() == this.commentReplyList.getMemberId()) {
            this.replyTitle = AppUtil.getString(R.string.video_comment_reply) + ConstantUtils.PLACEHOLDER_STR_ONE + replysVoListBean.getFromMemberName() + "：";
            this.et_input.setText("");
            this.et_input.setHint(this.replyTitle);
            showKeyBoard(this);
            this.commentType = 2;
            this.contentReplyMap.clear();
            Map<String, String> map = this.contentReplyMap;
            map.put("commentId", this.commentReplyList.getId() + "");
            Map<String, String> map2 = this.contentReplyMap;
            map2.put("fromUserId", DBUtil.getMemberId() + "");
            Map<String, String> map3 = this.contentReplyMap;
            map3.put("toUserId", replysVoListBean.getFromUserId() + "");
            this.contentReplyMap.put("replyType", "2");
            Map<String, String> map4 = this.contentReplyMap;
            map4.put("articleId", this.commentReplyList.getArticleId() + "");
            this.contentReplyMap.put("fromUserMsg", replysVoListBean.getReplyMsg());
            Map<String, String> map5 = this.contentReplyMap;
            map5.put("memberId", this.commentReplyList.getMemberId() + "");
            this.contentReplyMap.put("toUserName", replysVoListBean.getFromMemberName());
        } else {
            this.replyTitle = AppUtil.getString(R.string.video_comment_reply) + ConstantUtils.PLACEHOLDER_STR_ONE + replysVoListBean.getFromMemberName() + "：";
            this.et_input.setText("");
            this.et_input.setHint(this.replyTitle);
            showKeyBoard(this);
            this.commentType = 3;
            this.contentReplyMap.clear();
            Map<String, String> map6 = this.contentReplyMap;
            map6.put("commentId", this.commentReplyList.getId() + "");
            Map<String, String> map7 = this.contentReplyMap;
            map7.put("fromUserId", DBUtil.getMemberId() + "");
            Map<String, String> map8 = this.contentReplyMap;
            map8.put("toUserId", replysVoListBean.getFromUserId() + "");
            this.contentReplyMap.put("replyType", "2");
            Map<String, String> map9 = this.contentReplyMap;
            map9.put("articleId", this.commentReplyList.getArticleId() + "");
            this.contentReplyMap.put("fromUserMsg", replysVoListBean.getReplyMsg());
            Map<String, String> map10 = this.contentReplyMap;
            map10.put("memberId", this.commentReplyList.getMemberId() + "");
            this.contentReplyMap.put("toUserName", replysVoListBean.getFromMemberName());
        }
    }

    private void buildTempComment(int i, int i2, String str, String str2) {
        this.replySending = true;
        ReplysVoListBean replysVoListBean = new ReplysVoListBean();
        replysVoListBean.setSendStatus(1);
        this.tempCommentId--;
        replysVoListBean.setId(this.tempCommentId);
        replysVoListBean.setCommentId(this.commentReplyList.getId());
        replysVoListBean.setFromUserId(DBUtil.getMemberId());
        replysVoListBean.setToUserId(i);
        replysVoListBean.setReplyType(i2);
        replysVoListBean.setArticleId(this.commentReplyList.getArticleId());
        replysVoListBean.setFromUserMsg(str);
        replysVoListBean.setMemberId(this.commentReplyList.getMemberId());
        replysVoListBean.setToMemberName(str2);
        replysVoListBean.setReplyMsg(this.et_input.getText().toString().trim());
        replysVoListBean.setStatus(0);
        replysVoListBean.setCreateTimeStr(AppUtil.getString(R.string.post_comment_recent));
        replysVoListBean.setFromMemberName(DBUtil.getUserInfo().getName());
        replysVoListBean.setFromMemberAvatar(DBUtil.getUserInfo().getAvatar());
        if (this.selectList.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i3 = 0; i3 < this.selectList.size(); i3++) {
                sb.append(this.selectList.get(i3).getPath());
                if (i3 < this.selectList.size() - 1) {
                    sb.append(";");
                }
            }
            replysVoListBean.setSecImageUrl(sb.toString());
            replysVoListBean.setUploadUrl(sb.toString());
        }
        this.replyAdapter.addData(1, (int) replysVoListBean);
        AppUtil.recyclerViewScroll(this.recyclerView, 1, 0, 100);
        this.et_input.setText("");
        this.post_keyboard.reset();
        this.recyclerView_upload.setVisibility(8);
    }

    public void sendCommentReply() {
        if (DBUtil.getLoginInfo().isLogin() && this.commentReplyList != null && UserPermissionUtil.getInstance().isPermissionEnable(3)) {
            if (this.replySending) {
                ToastUtil.showCenterToast((int) R.string.post_comment_send_ing_tip);
                return;
            }
            if (!CommentCheckUtil.isValid(this.et_input, this.et_input.getText().toString().trim())) {
                return;
            }
            if (this.commentType == 1) {
                this.contentReplyMap.clear();
                Map<String, String> map = this.contentReplyMap;
                map.put("commentId", this.commentReplyList.getId() + "");
                Map<String, String> map2 = this.contentReplyMap;
                map2.put("fromUserId", DBUtil.getMemberId() + "");
                Map<String, String> map3 = this.contentReplyMap;
                map3.put("toUserId", this.commentReplyList.getMemberId() + "");
                this.contentReplyMap.put("replyType", "1");
                Map<String, String> map4 = this.contentReplyMap;
                map4.put("articleId", this.commentReplyList.getArticleId() + "");
                this.contentReplyMap.put("fromUserMsg", this.commentReplyList.getContent());
                Map<String, String> map5 = this.contentReplyMap;
                map5.put("memberId", this.commentReplyList.getMemberId() + "");
                this.contentReplyMap.put("toUserName", this.commentReplyList.getMemberName());
            }
            buildTempComment(Integer.parseInt(this.contentReplyMap.get("toUserId")), Integer.parseInt(this.contentReplyMap.get("replyType")), this.contentReplyMap.get("fromUserMsg"), this.contentReplyMap.get("toUserName"));
            if (this.selectList.size() > 0) {
                uploadImg();
            } else {
                sendFirstCommentReply();
            }
        }
    }

    private void uploadImg() {
        this.uploadSuccessList.clear();
        this.ttUtil = new TTUtil(1, new TTUtil.UploadFileToTTListener() { // from class: com.one.tomato.mvp.ui.post.view.PostCommentReplyActivity.8
            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void start() {
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadSuccess(LocalMedia localMedia) {
                PostCommentReplyActivity.this.uploadSuccessList.add(localMedia);
                if (PostCommentReplyActivity.this.uploadSuccessList.size() == PostCommentReplyActivity.this.selectList.size()) {
                    ReplysVoListBean item = PostCommentReplyActivity.this.replyAdapter.getItem(1);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < PostCommentReplyActivity.this.uploadSuccessList.size(); i++) {
                        String ceph = PostCommentReplyActivity.this.ttUtil.getCeph((LocalMedia) PostCommentReplyActivity.this.uploadSuccessList.get(i));
                        sb.append("/");
                        sb.append(PostCommentReplyActivity.this.ttUtil.getBucketName());
                        sb.append("/");
                        sb.append(ceph);
                        if (i < PostCommentReplyActivity.this.uploadSuccessList.size() - 1) {
                            sb.append(";");
                        }
                    }
                    PostCommentReplyActivity.this.contentReplyMap.put("imageUrl", sb.toString());
                    item.setUploadUrl(sb.toString());
                    item.setSendStatus(2);
                    PostCommentReplyActivity.this.replyAdapter.notifyDataSetChanged();
                    PostCommentReplyActivity.this.sendFirstCommentReply();
                    PostCommentReplyActivity.this.selectList.clear();
                    PostCommentReplyActivity.this.replySending = true;
                }
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadFail() {
                PostCommentReplyActivity.this.selectList.clear();
                PostCommentReplyActivity.this.replyAdapter.getItem(1).setSendStatus(3);
                PostCommentReplyActivity.this.replyAdapter.notifyDataSetChanged();
                PostCommentReplyActivity.this.replySending = false;
                ToastUtil.showCenterToast((int) R.string.common_upload_img_fail);
            }
        });
        this.ttUtil.getStsToken(this.selectList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendFirstCommentReply() {
        this.contentReplyMap.put("replyMsg", AppSecretUtil.decodeResponse(this.replyAdapter.getItem(1).getReplyMsg()));
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/acticleRelys/save");
        for (Map.Entry<String, String> entry : this.contentReplyMap.entrySet()) {
            if (!entry.getKey().equals("toUserName")) {
                tomatoParams.addParameter(entry.getKey(), entry.getValue());
            }
        }
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 2, ReplysVoListBean.class));
    }

    public void sendRetryCommentReply(ReplysVoListBean replysVoListBean, int i, String str) {
        if (replysVoListBean.getSendStatus() == 3) {
            if (TextUtils.isEmpty(replysVoListBean.getUploadUrl())) {
                return;
            }
            uploadImageRetry(replysVoListBean, i);
        } else if (replysVoListBean.getSendStatus() != 4) {
        } else {
            TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/acticleRelys/save");
            tomatoParams.addParameter("commentId", Integer.valueOf(replysVoListBean.getCommentId()));
            tomatoParams.addParameter("fromUserId", Integer.valueOf(replysVoListBean.getFromUserId()));
            tomatoParams.addParameter("toUserId", Integer.valueOf(replysVoListBean.getToUserId()));
            tomatoParams.addParameter("replyType", Integer.valueOf(replysVoListBean.getReplyType()));
            tomatoParams.addParameter("articleId", Integer.valueOf(replysVoListBean.getArticleId()));
            tomatoParams.addParameter("fromUserMsg", replysVoListBean.getFromUserMsg());
            tomatoParams.addParameter("memberId", Integer.valueOf(replysVoListBean.getMemberId()));
            tomatoParams.addParameter("replyMsg", AppSecretUtil.decodeResponse(replysVoListBean.getReplyMsg()));
            if (!TextUtils.isEmpty(replysVoListBean.getUploadUrl())) {
                tomatoParams.addParameter("imageUrl", replysVoListBean.getUploadUrl());
            }
            if (!TextUtils.isEmpty(str)) {
                tomatoParams.addParameter("verifyCode", str);
            }
            tomatoParams.post(new TomatoCallback((ResponseObserver) this, 6, ReplysVoListBean.class, i));
            replysVoListBean.setSendStatus(1);
            this.replySending = true;
            this.replyAdapter.notifyDataSetChanged();
        }
    }

    private void uploadImageRetry(final ReplysVoListBean replysVoListBean, final int i) {
        this.ttUtil = new TTUtil(1, new TTUtil.UploadFileToTTListener() { // from class: com.one.tomato.mvp.ui.post.view.PostCommentReplyActivity.9
            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void start() {
                PostCommentReplyActivity.this.uploadSuccessList.clear();
                PostCommentReplyActivity.this.selectList.clear();
                replysVoListBean.setSendStatus(1);
                PostCommentReplyActivity.this.replySending = true;
                PostCommentReplyActivity.this.replyAdapter.notifyDataSetChanged();
                for (String str : replysVoListBean.getUploadUrl().split(";")) {
                    PostCommentReplyActivity.this.selectList.add(new LocalMedia(str));
                }
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadSuccess(LocalMedia localMedia) {
                PostCommentReplyActivity.this.uploadSuccessList.add(localMedia);
                if (PostCommentReplyActivity.this.uploadSuccessList.size() == PostCommentReplyActivity.this.selectList.size()) {
                    StringBuilder sb = new StringBuilder();
                    for (int i2 = 0; i2 < PostCommentReplyActivity.this.uploadSuccessList.size(); i2++) {
                        String ceph = PostCommentReplyActivity.this.ttUtil.getCeph((LocalMedia) PostCommentReplyActivity.this.uploadSuccessList.get(i2));
                        sb.append("/");
                        sb.append(PostCommentReplyActivity.this.ttUtil.getBucketName());
                        sb.append("/");
                        sb.append(ceph);
                        if (i2 < PostCommentReplyActivity.this.uploadSuccessList.size() - 1) {
                            sb.append(";");
                        }
                    }
                    replysVoListBean.setSendStatus(4);
                    replysVoListBean.setUploadUrl(sb.toString());
                    PostCommentReplyActivity.this.replyAdapter.notifyDataSetChanged();
                    PostCommentReplyActivity.this.sendRetryCommentReply(replysVoListBean, i, null);
                    PostCommentReplyActivity.this.replySending = true;
                    PostCommentReplyActivity.this.selectList.clear();
                }
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadFail() {
                replysVoListBean.setSendStatus(3);
                PostCommentReplyActivity.this.replyAdapter.notifyDataSetChanged();
                PostCommentReplyActivity.this.replySending = false;
                PostCommentReplyActivity.this.selectList.clear();
            }
        });
        this.ttUtil.getStsToken(this.selectList);
    }

    private void showImageVerifyDialog(final ReplysVoListBean replysVoListBean, final int i) {
        ImageVerifyDialog imageVerifyDialog = this.imageVerifyDialog;
        if (imageVerifyDialog == null) {
            this.imageVerifyDialog = new ImageVerifyDialog(this, AppUtil.getString(R.string.post_comment_img_verify));
        } else {
            imageVerifyDialog.show();
        }
        this.imageVerifyDialog.getVerifyImage();
        this.imageVerifyDialog.setImageVerifyConfirmListener(new ImageVerifyDialog.ImageVerifyConfirmListener() { // from class: com.one.tomato.mvp.ui.post.view.PostCommentReplyActivity.10
            @Override // com.one.tomato.dialog.ImageVerifyDialog.ImageVerifyConfirmListener
            public void imageVerifyConfirm(String str) {
                PostCommentReplyActivity.this.sendRetryCommentReply(replysVoListBean, i, str);
            }
        });
    }

    public void requestPostDelete(String str, String str2, int i) {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/comment/delete");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("type", str);
        tomatoParams.addParameter("businessId", str2);
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 8, (Class) null, i));
    }

    public void requestAuthorCommentDelete(int i, int i2, int i3, int i4) {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/comment/authorDeleteComment");
        tomatoParams.addParameter("articleId", Integer.valueOf(i2));
        tomatoParams.addParameter("commentId", Integer.valueOf(i3));
        tomatoParams.addParameter("type", Integer.valueOf(i4));
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 10, (Class) null, i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 1) {
            this.commentReplyList = (CommentReplyList) baseModel.obj;
            CommentReplyList commentReplyList = this.commentReplyList;
            if (commentReplyList == null) {
                this.replyAdapter.setEmptyViewState(2, this.refreshLayout);
                return;
            }
            this.replyAdapter.setCommentReplyList(commentReplyList);
            updateData(message.arg1, this.commentReplyList.getReplysVoList());
            this.et_input.setEnabled(true);
        } else if (i == 2) {
            ToastUtil.showCenterToast((int) R.string.post_comment_send_success);
            ReplysVoListBean item = this.replyAdapter.getItem(1);
            item.setId(((ReplysVoListBean) baseModel.obj).getId());
            item.setSendStatus(2);
            this.replyAdapter.notifyDataSetChanged();
            this.replySending = false;
            AddOrDeleteReplyEvent addOrDeleteReplyEvent = new AddOrDeleteReplyEvent();
            addOrDeleteReplyEvent.add = true;
            addOrDeleteReplyEvent.reply = item;
            EventBus.getDefault().post(addOrDeleteReplyEvent);
            DBUtil.saveLevelBean(3);
        } else if (i == 6) {
            ReplysVoListBean item2 = this.replyAdapter.getItem(message.arg1);
            item2.setId(((ReplysVoListBean) baseModel.obj).getId());
            item2.setSendStatus(2);
            this.replyAdapter.notifyDataSetChanged();
            this.replySending = false;
            AddOrDeleteReplyEvent addOrDeleteReplyEvent2 = new AddOrDeleteReplyEvent();
            addOrDeleteReplyEvent2.add = true;
            addOrDeleteReplyEvent2.reply = item2;
            EventBus.getDefault().post(addOrDeleteReplyEvent2);
        } else {
            switch (i) {
                case 8:
                case 10:
                    ToastUtil.showCenterToast(baseModel.message);
                    int i2 = message.arg1;
                    AddOrDeleteReplyEvent addOrDeleteReplyEvent3 = new AddOrDeleteReplyEvent();
                    addOrDeleteReplyEvent3.add = false;
                    addOrDeleteReplyEvent3.reply = this.replyAdapter.getData().get(i2);
                    EventBus.getDefault().post(addOrDeleteReplyEvent3);
                    this.replyAdapter.remove(i2);
                    return;
                case 9:
                    PostList postList = (PostList) baseModel.obj;
                    this.postMemberId = postList.getMemberId();
                    ImageLoaderUtil.loadHeadImage(this, this.comment_title_icon_i, new ImageBean(postList.getAvatar()));
                    String groupName = postList.getGroupName();
                    String description = postList.getDescription();
                    String createTimeStr = postList.getCreateTimeStr();
                    TextView textView = this.comment_title_name_t;
                    textView.setText("" + groupName);
                    this.comment_title_content_t.setText(description);
                    this.comment_title_timet_t.setText(createTimeStr);
                    return;
                default:
                    return;
            }
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 1) {
            if (message.arg1 == 1) {
                this.refreshLayout.mo6481finishRefresh();
            } else {
                this.replyAdapter.loadMoreFail();
            }
            if (this.replyAdapter.getData().size() == 0) {
                this.replyAdapter.setEmptyViewState(1, this.refreshLayout);
            }
            this.et_input.setEnabled(true);
        } else if (i == 2) {
            ReplysVoListBean item = this.replyAdapter.getItem(1);
            item.setSendStatus(4);
            this.replyAdapter.notifyDataSetChanged();
            this.replySending = false;
            if (baseModel.code == -5) {
                showImageVerifyDialog(item, 1);
            }
        } else if (i == 6) {
            int i2 = message.arg1;
            ReplysVoListBean item2 = this.replyAdapter.getItem(i2);
            item2.setSendStatus(4);
            this.replyAdapter.notifyDataSetChanged();
            this.replySending = false;
            if (baseModel.code == -5) {
                showImageVerifyDialog(item2, i2);
            }
        }
        return true;
    }

    private void updateData(int i, ArrayList<ReplysVoListBean> arrayList) {
        boolean z = false;
        if (arrayList == null || arrayList.isEmpty()) {
            if (i == 1) {
                this.refreshLayout.mo6481finishRefresh();
                this.replyAdapter.getData().clear();
                arrayList.add(0, new ReplysVoListBean());
                this.replyAdapter.setNewData(arrayList);
                this.replyAdapter.loadMoreEnd();
            }
            if (i != 2) {
                return;
            }
            this.replyAdapter.loadMoreEnd();
            return;
        }
        if (i == 1) {
            this.refreshLayout.mo6481finishRefresh();
            this.replyAdapter.getData().clear();
            this.pageNo = 2;
            arrayList.add(0, new ReplysVoListBean());
            this.replyAdapter.setNewData(arrayList);
        } else {
            this.pageNo++;
            this.replyAdapter.addData((Collection) arrayList);
        }
        if (arrayList.size() >= this.pageSize) {
            z = true;
        }
        if (z) {
            this.replyAdapter.loadMoreComplete();
        } else {
            this.replyAdapter.loadMoreEnd();
        }
        this.post_keyboard.reset();
    }

    public void setReply(CommentReplyList commentReplyList, ReplysVoListBean replysVoListBean, TextView textView) {
        if (replysVoListBean.getToUserId() == 0) {
            replysVoListBean.setToMemberName(commentReplyList.getMemberName());
            replysVoListBean.setReplyType(1);
        } else if (replysVoListBean.getToUserId() == commentReplyList.getMemberId()) {
            replysVoListBean.setReplyType(1);
        } else {
            replysVoListBean.setReplyType(2);
        }
        textView.setTextSize(2, 14.0f);
        if (replysVoListBean.getReplyType() == 1) {
            textView.setTextColor(getResources().getColor(R.color.text_dark));
            textView.setText(replysVoListBean.getReplyMsg());
            EmotionUtil.spannableEmoticonFilter(textView, textView.getText().toString());
            return;
        }
        ViewUtil.initTextViewWithSpannableEmotionString(textView, "：" + replysVoListBean.getReplyMsg(), new String[]{AppUtil.getString(R.string.video_comment_reply) + ConstantUtils.PLACEHOLDER_STR_ONE, replysVoListBean.getToMemberName()}, new String[]{String.valueOf(getResources().getColor(R.color.text_dark)), String.valueOf(getResources().getColor(R.color.colorAccent))}, new String[]{"14", "14"});
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 188) {
            this.et_input.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.post.view.PostCommentReplyActivity.11
                @Override // java.lang.Runnable
                public void run() {
                    ((InputMethodManager) ((BaseActivity) PostCommentReplyActivity.this).mContext.getSystemService("input_method")).toggleSoftInput(0, 2);
                }
            }, 100L);
            this.recyclerView_upload.setVisibility(0);
            initUploadAdapter();
            this.selectList = PictureSelector.obtainMultipleResult(intent);
            ArrayList arrayList = new ArrayList();
            for (LocalMedia localMedia : this.selectList) {
                arrayList.add(localMedia.getPath());
                Log.i("图片-----》", localMedia.getPath());
            }
            this.uploadAdapter.getData().clear();
            this.uploadAdapter.addData((Collection) arrayList);
            this.uploadAdapter.notifyDataSetChanged();
        }
    }

    private void initUploadAdapter() {
        if (this.uploadAdapter == null) {
            this.uploadAdapter = new PostDetailImgUploadAdapter(this, R.layout.layout_comment_send_pic, this.recyclerView_upload);
            this.recyclerView_upload.setHasFixedSize(true);
            configLinearLayoutHorizontalManager(this.recyclerView_upload);
            this.uploadAdapter.setEnableLoadMore(false);
            this.recyclerView_upload.setAdapter(this.uploadAdapter);
        }
    }

    public void startImgShowActivity(int i) {
        PictureSelector.create(this).themeStyle(2131821197).openExternalPreview(i, this.selectList);
    }

    public void removeLocalImg(int i) {
        this.selectList.remove(i);
        this.uploadAdapter.remove(i);
        this.uploadAdapter.notifyDataSetChanged();
        if (this.selectList.size() == 0 && this.uploadAdapter.getData().size() == 0) {
            this.recyclerView_upload.setVisibility(8);
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        PostKeyboard postKeyboard = this.post_keyboard;
        if (postKeyboard != null) {
            postKeyboard.reset();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        PostKeyboard postKeyboard = this.post_keyboard;
        if (postKeyboard != null) {
            postKeyboard.reset();
        }
    }

    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (EmoticonsKeyboardUtils.isFullScreen(this)) {
            boolean dispatchKeyEventInFullScreen = this.post_keyboard.dispatchKeyEventInFullScreen(keyEvent);
            return dispatchKeyEventInFullScreen ? dispatchKeyEventInFullScreen : super.dispatchKeyEvent(keyEvent);
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
    }
}
