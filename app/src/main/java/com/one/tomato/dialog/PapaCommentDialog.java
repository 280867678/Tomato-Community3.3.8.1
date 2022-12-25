package com.one.tomato.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.adapter.PapaCommentListAdapter;
import com.one.tomato.dialog.ImageVerifyDialog;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.VideoCommentList;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.emotion.EmotionEditText;
import com.one.tomato.thirdpart.emotion.EmotionUtil;
import com.one.tomato.thirdpart.emotion.PapaKeyboard;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.utils.AppSecretUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UserPermissionUtil;
import com.one.tomato.utils.post.CommentCheckUtil;
import com.one.tomato.widget.EmptyViewLayout;
import com.p096sj.emoji.EmojiBean;
import com.tomatolive.library.http.RequestParams;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import sj.keyboard.data.EmoticonEntity;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.utils.EmoticonsKeyboardUtils;

/* loaded from: classes3.dex */
public class PapaCommentDialog extends Dialog implements ResponseObserver {
    private VideoCommentList clickCommentList;
    private PapaCommentListAdapter commentAdapter;
    private int commentNum;
    private View emptyView;
    private EmptyViewLayout emptyViewLayout;
    protected EmotionEditText et_input;
    private ImageVerifyDialog imageVerifyDialog;
    private LevelBean levelBean;
    private BaseLinearLayoutManager linearLayoutManager;
    private Context mContext;
    private VideoCommentList newCommentList;
    protected int pageNo = 1;
    protected int pageSize = 10;
    private PapaKeyboard papa_keyboard;
    private int postId;
    private RecyclerView recyclerView;
    private VideoCommentSuccessListener successListener;
    private TextView tv_comment_num;
    private TextView tv_place;
    private View view;

    /* loaded from: classes3.dex */
    public interface VideoCommentSuccessListener {
        void commentSuccess(int i);
    }

    @Override // com.one.tomato.net.ResponseObserver
    public boolean handleHttpRequestError(Message message) {
        return false;
    }

    @Override // com.one.tomato.net.ResponseObserver
    public boolean handleRequestCancel(Message message) {
        return false;
    }

    public void setVideoCommentSuccessListener(VideoCommentSuccessListener videoCommentSuccessListener) {
        this.successListener = videoCommentSuccessListener;
    }

    public PapaCommentDialog(Context context) {
        super(context, R.style.BankListDialog_DimFalse);
        this.mContext = context;
        this.view = LayoutInflater.from(this.mContext).inflate(R.layout.dialog_comment_papa, (ViewGroup) null);
        setContentView(this.view);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = 0;
        attributes.width = -1;
        attributes.height = -1;
        getWindow().setAttributes(attributes);
        initView();
        initKeyboard();
        initAdapter();
    }

    private void initView() {
        this.papa_keyboard = (PapaKeyboard) this.view.findViewById(R.id.papa_keyboard);
        this.tv_comment_num = (TextView) this.view.findViewById(R.id.tv_comment_num);
        this.tv_place = (TextView) this.view.findViewById(R.id.tv_place);
        this.papa_keyboard.getLl_comment();
        this.papa_keyboard.getIv_emotion();
        this.et_input = this.papa_keyboard.getEtChat();
        this.recyclerView = (RecyclerView) this.view.findViewById(R.id.recyclerView);
        this.levelBean = DBUtil.getLevelBean();
        if (DBUtil.getLoginInfo().isLogin()) {
            if (DBUtil.getUserInfo().getVipType() == 1) {
                this.et_input.setHint(R.string.video_comment_input_hint);
            } else if (this.levelBean.getCurrentLevelIndex() < 2) {
                this.et_input.setHint(R.string.level_tip_dialog);
            } else if (this.levelBean.getCommentCount() == 0) {
                this.et_input.setHint(R.string.credit_edit_comment_deny);
            } else if (this.levelBean.getCommentCount_times() == this.levelBean.getCommentCount()) {
                this.et_input.setHint(R.string.credit_edit_comment_no_left);
            } else {
                this.et_input.setHint(R.string.video_comment_input_hint);
            }
        } else {
            this.et_input.setHint(R.string.video_comment_input_hint);
        }
        this.linearLayoutManager = new BaseLinearLayoutManager(this.mContext, 1, false);
        this.recyclerView.setLayoutManager(this.linearLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        this.tv_place.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PapaCommentDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PapaCommentDialog.this.dismiss();
            }
        });
        this.et_input.setOnTouchListener(new View.OnTouchListener() { // from class: com.one.tomato.dialog.PapaCommentDialog.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    if (!DBUtil.getLoginInfo().isLogin()) {
                        LoginActivity.Companion.startActivity(PapaCommentDialog.this.mContext);
                        return true;
                    } else if (!UserPermissionUtil.getInstance().isPermissionEnable(3)) {
                        return true;
                    }
                }
                PapaCommentDialog.this.et_input.setFocusable(true);
                PapaCommentDialog.this.et_input.setFocusableInTouchMode(true);
                return false;
            }
        });
        this.et_input.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.one.tomato.dialog.PapaCommentDialog.3
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 4) {
                    if (TextUtils.isEmpty(PapaCommentDialog.this.et_input.getText().toString().trim())) {
                        return true;
                    }
                    PapaCommentDialog.this.sendComment(null);
                    return true;
                }
                return false;
            }
        });
    }

    private void initKeyboard() {
        this.papa_keyboard.setAdapter(EmotionUtil.getCommonAdapter(this.mContext, new EmoticonClickListener() { // from class: com.one.tomato.dialog.PapaCommentDialog.4
            @Override // sj.keyboard.interfaces.EmoticonClickListener
            public void onEmoticonClick(Object obj, int i, boolean z) {
                if (z) {
                    EmotionUtil.delClick(PapaCommentDialog.this.et_input);
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
                    PapaCommentDialog.this.et_input.getText().insert(PapaCommentDialog.this.et_input.getSelectionStart(), str);
                }
            }
        }));
    }

    private void initAdapter() {
        this.commentAdapter = new PapaCommentListAdapter(this, R.layout.item_video_papa_comment_list, this.recyclerView);
        this.recyclerView.setAdapter(this.commentAdapter);
    }

    public void setData(int i, int i2, PostList postList) {
        this.postId = i;
        this.commentAdapter.setPostList(postList);
        this.commentNum = i2;
        this.et_input.setText("");
        this.clickCommentList = null;
        updateCommentNum();
        refresh();
    }

    public void refresh() {
        setEmptyViewState(0, this.recyclerView);
        getListFromServer(1);
    }

    public void loadMore() {
        getListFromServer(2);
    }

    public void getListFromServer(int i) {
        if (i == 1) {
            this.pageNo = 1;
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/videoComments/list");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("articleId", Integer.valueOf(this.postId));
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        tomatoParams.post(new TomatoCallback(this, 1, new TypeToken<List<VideoCommentList>>(this) { // from class: com.one.tomato.dialog.PapaCommentDialog.5
        }.getType(), i));
    }

    public void getReplyListFromServer(int i, int i2, int i3, int i4) {
        this.commentAdapter.getItem(i3).setReplyStatus(3);
        this.commentAdapter.refreshNotifyItemChanged(i3);
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/videoComments/reply/list");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("articleId", Integer.valueOf(this.postId));
        tomatoParams.addParameter("commentId", Integer.valueOf(i));
        tomatoParams.addParameter(C2516Ad.TYPE_START, Integer.valueOf(i4));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        tomatoParams.post(new TomatoCallback(this, 4, new TypeToken<List<VideoCommentList>>(this) { // from class: com.one.tomato.dialog.PapaCommentDialog.6
        }.getType(), i2, i3));
    }

    public void thumb(int i, VideoCommentList videoCommentList) {
        if (videoCommentList.getMemberId() == DBUtil.getMemberId()) {
            ToastUtil.showCenterToast((int) R.string.post_thump_for_self);
            return;
        }
        int goodNum = videoCommentList.getGoodNum();
        int i2 = 0;
        if (videoCommentList.getIsThumbsUp() == 1) {
            videoCommentList.setIsThumbsUp(0);
            int i3 = goodNum - 1;
            if (i3 > 0) {
                i2 = i3;
            }
        } else {
            videoCommentList.setIsThumbsUp(1);
            i2 = goodNum + 1;
        }
        videoCommentList.setGoodNum(i2);
        this.commentAdapter.refreshNotifyItemChanged(i);
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/videoComments/videoThumbsUp");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("commentId", Integer.valueOf(videoCommentList.getId()));
        tomatoParams.post(new TomatoCallback(this, 2));
    }

    @Override // com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 1) {
            updateCommentData(message.arg1, (ArrayList) baseModel.obj);
        } else if (i != 3) {
            if (i != 4) {
                return;
            }
            ArrayList<VideoCommentList> arrayList = (ArrayList) baseModel.obj;
            int i2 = message.arg1;
            int i3 = message.arg2;
            if (arrayList == null) {
                this.commentAdapter.getItem(i3).setReplyStatus(2);
                this.commentAdapter.refreshNotifyItemChanged(i3);
            } else if (arrayList.isEmpty()) {
                this.commentAdapter.getItem(i3).setReplyStatus(4);
                this.commentAdapter.refreshNotifyItemChanged(i3);
            } else {
                this.commentAdapter.getItem(i3).setReplyStatus(0);
                this.commentAdapter.refreshNotifyItemChanged(i3);
                updateReplyData(i2, i3, arrayList);
            }
        } else {
            this.commentNum++;
            updateCommentNum();
            this.et_input.setText("");
            int commentId = this.newCommentList.getCommentId();
            if (commentId > 0) {
                this.commentAdapter.getData().add(this.commentAdapter.getData().indexOf(new VideoCommentList(commentId)) + 1, this.newCommentList);
                DBUtil.saveLevelBean(3);
            } else {
                this.commentAdapter.addData(0, (int) this.newCommentList);
                DBUtil.saveLevelBean(2);
            }
            this.commentAdapter.notifyDataSetChanged();
            if (this.commentAdapter.getData().size() == 1) {
                this.commentAdapter.loadMoreEnd();
            }
            VideoCommentSuccessListener videoCommentSuccessListener = this.successListener;
            if (videoCommentSuccessListener != null) {
                videoCommentSuccessListener.commentSuccess(this.commentNum);
            }
            this.clickCommentList = null;
        }
    }

    @Override // com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 1) {
            if (message.arg1 != 1) {
                this.commentAdapter.loadMoreFail();
            }
            if (this.commentAdapter.getData().size() != 0) {
                return true;
            }
            setEmptyViewState(1, this.recyclerView);
            this.commentAdapter.loadMoreEnd();
            return true;
        } else if (i == 3) {
            if (baseModel.code != -5) {
                return true;
            }
            showImageVerifyDialog();
            return true;
        } else {
            if (i == 4) {
                int i2 = message.arg2;
                this.commentAdapter.getItem(i2).setReplyStatus(2);
                this.commentAdapter.refreshNotifyItemChanged(i2);
            }
            return false;
        }
    }

    private void updateCommentData(int i, ArrayList<VideoCommentList> arrayList) {
        boolean z = true;
        if (arrayList == null || arrayList.isEmpty()) {
            if (i == 1) {
                setEmptyViewState(2, this.recyclerView);
                this.commentAdapter.loadMoreEnd();
            }
            if (i != 2) {
                return;
            }
            this.commentAdapter.loadMoreEnd();
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator<VideoCommentList> it2 = arrayList.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            VideoCommentList next = it2.next();
            arrayList2.add(next);
            ArrayList<VideoCommentList> replyList = next.getReplyList();
            if (replyList != null && !replyList.isEmpty()) {
                for (int i2 = 0; i2 < replyList.size(); i2++) {
                    VideoCommentList videoCommentList = replyList.get(i2);
                    videoCommentList.setParentMemberId(next.getMemberId());
                    videoCommentList.setReply(true);
                    if (i2 == replyList.size() - 1) {
                        videoCommentList.setReplyStatus(1);
                        videoCommentList.setReplyNum(next.getReplyNum());
                    }
                    arrayList2.add(videoCommentList);
                }
            }
        }
        if (i == 1) {
            this.pageNo = 2;
            this.commentAdapter.getData().clear();
            this.commentAdapter.setNewData(arrayList2);
        } else {
            this.pageNo++;
            this.commentAdapter.addData((Collection) arrayList2);
        }
        if (arrayList.size() < this.pageSize) {
            z = false;
        }
        if (z) {
            this.commentAdapter.loadMoreComplete();
        } else {
            this.commentAdapter.loadMoreEnd();
        }
    }

    private void updateReplyData(int i, int i2, ArrayList<VideoCommentList> arrayList) {
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            VideoCommentList videoCommentList = arrayList.get(i3);
            videoCommentList.setParentMemberId(i);
            videoCommentList.setReply(true);
            if (i3 == arrayList.size() - 1) {
                videoCommentList.setReplyStatus(2);
            }
        }
        this.commentAdapter.addData(i2 + 1, (Collection) arrayList);
    }

    private void setEmptyViewState(int i, RecyclerView recyclerView) {
        if (this.emptyView == null) {
            this.emptyView = LayoutInflater.from(this.mContext).inflate(R.layout.recyclerview_empty_layout, (ViewGroup) recyclerView.getParent(), false);
            this.emptyViewLayout = (EmptyViewLayout) this.emptyView.findViewById(R.id.list_empty);
            this.emptyViewLayout.setEmptyViewBackgroundColor(this.mContext.getResources().getColor(R.color.transparent));
            this.emptyViewLayout.setButtonClickListener(new EmptyViewLayout.ButtonClickListener() { // from class: com.one.tomato.dialog.PapaCommentDialog.7
                @Override // com.one.tomato.widget.EmptyViewLayout.ButtonClickListener
                public void buttonClickListener(View view, int i2) {
                    PapaCommentDialog.this.refresh();
                }
            });
        }
        String string = i == 2 ? AppUtil.getString(R.string.post_comment_no_data) : "";
        this.emptyViewLayout.setNoDataLogo(this.mContext.getResources().getDrawable(R.drawable.post_comment_nodata));
        this.emptyViewLayout.setState(i, string);
        this.commentAdapter.setEmptyView(this.emptyView);
        this.commentAdapter.getData().clear();
        this.commentAdapter.notifyDataSetChanged();
    }

    private void updateCommentNum() {
        TextView textView = this.tv_comment_num;
        textView.setText(AppUtil.getString(R.string.video_comment_num, FormatUtil.formatNumOverTenThousand(this.commentNum + "")));
    }

    public void setClickCommentList(VideoCommentList videoCommentList) {
        this.clickCommentList = videoCommentList;
        this.et_input.requestFocus();
        this.et_input.setFocusable(true);
        this.et_input.setFocusableInTouchMode(true);
        EmoticonsKeyboardUtils.openSoftKeyboard(this.et_input);
        EmotionEditText emotionEditText = this.et_input;
        emotionEditText.setHint("@" + this.clickCommentList.getName() + "：");
    }

    public void sendComment(String str) {
        if (!DBUtil.getLoginInfo().isLogin()) {
            LoginActivity.Companion.startActivity(this.mContext);
        } else if (!UserPermissionUtil.getInstance().isPermissionEnable(3)) {
        } else {
            String trim = this.et_input.getText().toString().trim();
            if (!CommentCheckUtil.isValid(this.et_input, trim)) {
                return;
            }
            if (this.newCommentList != null) {
                this.newCommentList = null;
            }
            this.newCommentList = new VideoCommentList();
            this.newCommentList.setArticleId(this.postId);
            this.newCommentList.setMemberId(DBUtil.getMemberId());
            this.newCommentList.setContent(trim);
            VideoCommentList videoCommentList = this.clickCommentList;
            if (videoCommentList != null) {
                this.newCommentList.setToMemberId(videoCommentList.getMemberId());
                this.newCommentList.setFromContent(this.clickCommentList.getContent());
                this.newCommentList.setToMemberName(this.clickCommentList.getName());
                this.newCommentList.setToMemberAvatar(this.clickCommentList.getAvatar());
                this.newCommentList.setReply(true);
                this.newCommentList.setReplyStatus(0);
                int commentId = this.clickCommentList.getCommentId();
                this.newCommentList.setParentMemberId(this.clickCommentList.getParentMemberId());
                if (commentId == 0) {
                    commentId = this.clickCommentList.getId();
                    this.newCommentList.setParentMemberId(this.clickCommentList.getMemberId());
                }
                this.newCommentList.setCommentId(commentId);
            }
            this.newCommentList.setIsThumbsUp(0);
            this.newCommentList.setGoodNum(0);
            this.newCommentList.setName(DBUtil.getUserInfo().getName());
            this.newCommentList.setAvatar(DBUtil.getUserInfo().getAvatar());
            this.newCommentList.setCommentTime(AppUtil.getString(R.string.post_comment_recent));
            TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/videoComments/save");
            tomatoParams.addParameter("articleId", Integer.valueOf(this.postId));
            tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
            tomatoParams.addParameter("content", AppSecretUtil.decodeResponse(trim));
            if (this.clickCommentList == null) {
                tomatoParams.addParameter("type", 1);
            } else {
                tomatoParams.addParameter("type", 3);
                tomatoParams.addParameter("commentId", Integer.valueOf(this.clickCommentList.getId()));
                tomatoParams.addParameter("toMemberId", Integer.valueOf(this.clickCommentList.getMemberId()));
                tomatoParams.addParameter("fromContent", this.clickCommentList.getContent());
            }
            if (!TextUtils.isEmpty(str)) {
                tomatoParams.addParameter("verifyCode", str);
            }
            tomatoParams.post(new TomatoCallback((ResponseObserver) this, 3, VideoCommentList.class));
            this.papa_keyboard.reset();
        }
    }

    private void showImageVerifyDialog() {
        ImageVerifyDialog imageVerifyDialog = this.imageVerifyDialog;
        if (imageVerifyDialog == null) {
            this.imageVerifyDialog = new ImageVerifyDialog(this.mContext, AppUtil.getString(R.string.post_comment_img_verify));
        } else {
            imageVerifyDialog.show();
        }
        this.imageVerifyDialog.getVerifyImage();
        this.imageVerifyDialog.setImageVerifyConfirmListener(new ImageVerifyDialog.ImageVerifyConfirmListener() { // from class: com.one.tomato.dialog.PapaCommentDialog.8
            @Override // com.one.tomato.dialog.ImageVerifyDialog.ImageVerifyConfirmListener
            public void imageVerifyConfirm(String str) {
                PapaCommentDialog.this.sendComment(str);
            }
        });
    }
}
