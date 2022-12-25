package com.one.tomato.p085ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.papa.view.NewPaPaListVideoActivity;
import com.one.tomato.mvp.p080ui.post.view.NewPostDetailViewPagerActivity;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.tomatolive.library.http.RequestParams;
import java.util.ArrayList;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_my_browse)
/* renamed from: com.one.tomato.ui.mine.MyBrowseListActivity */
/* loaded from: classes3.dex */
public class MyBrowseListActivity extends BaseRecyclerViewActivity {
    private BaseRecyclerViewAdapter<PostList> browseadapter;
    private CustomAlertDialog dialog;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MyBrowseListActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.refreshLayout.autoRefresh(100);
        initTitleBar();
        this.titleTV.setText(R.string.browse_list_title);
        this.rightTV.setVisibility(0);
        this.rightTV.setText(R.string.common_clear);
        initAdapter();
        setListener();
    }

    private void setListener() {
        this.rightTV.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.mine.MyBrowseListActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (MyBrowseListActivity.this.browseadapter.getData().isEmpty()) {
                    return;
                }
                if (MyBrowseListActivity.this.dialog != null) {
                    MyBrowseListActivity.this.dialog.show();
                    return;
                }
                MyBrowseListActivity myBrowseListActivity = MyBrowseListActivity.this;
                myBrowseListActivity.dialog = new CustomAlertDialog(((BaseActivity) myBrowseListActivity).mContext);
                MyBrowseListActivity.this.dialog.setMessage(R.string.browse_list_clear_tip);
                MyBrowseListActivity.this.dialog.setConfirmButton(R.string.common_confirm, new View.OnClickListener() { // from class: com.one.tomato.ui.mine.MyBrowseListActivity.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        MyBrowseListActivity.this.clearList();
                    }
                });
                MyBrowseListActivity.this.dialog.setCancelButton(R.string.common_cancel, new View.OnClickListener() { // from class: com.one.tomato.ui.mine.MyBrowseListActivity.1.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        MyBrowseListActivity.this.dialog.dismiss();
                    }
                });
            }
        });
    }

    public void getListFromServer(int i) {
        if (i == 1) {
            this.pageNo = 1;
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/browse/list");
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback(this, 1, new TypeToken<ArrayList<PostList>>(this) { // from class: com.one.tomato.ui.mine.MyBrowseListActivity.2
        }.getType(), i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearList() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/browse/delete");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback(this, 2));
    }

    private void initAdapter() {
        this.browseadapter = new BaseRecyclerViewAdapter<PostList>(this, R.layout.item_my_browse, this.recyclerView) { // from class: com.one.tomato.ui.mine.MyBrowseListActivity.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, PostList postList) {
                super.convert(baseViewHolder, (BaseViewHolder) postList);
                if (1 == postList.getPostType()) {
                    baseViewHolder.getView(R.id.ll_my_publish_img).setVisibility(0);
                    baseViewHolder.getView(R.id.rl_my_publish_text).setVisibility(8);
                    baseViewHolder.getView(R.id.ll_my_publish_video).setVisibility(8);
                    TextView textView = (TextView) baseViewHolder.getView(R.id.tv_post_img_name);
                    TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_post_img_date);
                    ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_post_image);
                    TextView textView3 = (TextView) baseViewHolder.getView(R.id.tv_post_image_num);
                    if (TextUtils.isEmpty(postList.getDescription())) {
                        textView.setText("[" + AppUtil.getString(R.string.circle_post_img) + "]");
                    } else {
                        textView.setText(postList.getDescription());
                    }
                    textView2.setText(postList.getCreateTime());
                    ImageBean imageBean = new ImageBean();
                    imageBean.setImage(postList.getSecImageUrl().split(";")[0]);
                    imageBean.setSecret(true);
                    ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, imageView, imageBean);
                    textView3.setText(postList.getPicNum() + AppUtil.getString(R.string.circle_post_img_num));
                } else if (2 == postList.getPostType() || 4 == postList.getPostType()) {
                    baseViewHolder.getView(R.id.ll_my_publish_video).setVisibility(0);
                    baseViewHolder.getView(R.id.rl_my_publish_text).setVisibility(8);
                    baseViewHolder.getView(R.id.ll_my_publish_img).setVisibility(8);
                    TextView textView4 = (TextView) baseViewHolder.getView(R.id.tv_post_video_name);
                    TextView textView5 = (TextView) baseViewHolder.getView(R.id.tv_post_video_date);
                    ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.iv_post_video);
                    if (TextUtils.isEmpty(postList.getDescription())) {
                        textView4.setText("[" + AppUtil.getString(R.string.circle_post_video) + "]");
                    } else {
                        textView4.setText(postList.getDescription());
                    }
                    textView5.setText(postList.getCreateTime());
                    ImageBean imageBean2 = new ImageBean();
                    imageBean2.setImage(postList.getSecVideoCover());
                    imageBean2.setSecret(true);
                    ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, imageView2, imageBean2);
                } else if (3 != postList.getPostType()) {
                } else {
                    baseViewHolder.getView(R.id.rl_my_publish_text).setVisibility(0);
                    baseViewHolder.getView(R.id.ll_my_publish_video).setVisibility(8);
                    baseViewHolder.getView(R.id.ll_my_publish_img).setVisibility(8);
                    ((TextView) baseViewHolder.getView(R.id.tv_post_text_name)).setText(postList.getDescription());
                    ((TextView) baseViewHolder.getView(R.id.tv_post_text_date)).setText(postList.getCreateTime());
                }
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                super.onRecyclerItemClick(baseQuickAdapter, view, i);
                PostList postList = (PostList) MyBrowseListActivity.this.browseadapter.getItem(i);
                if (MyBrowseListActivity.this.isBlack(postList.getBlackFlag())) {
                    return;
                }
                if (postList.getPostType() == 4) {
                    ArrayList<PostList> arrayList = new ArrayList<>();
                    arrayList.add(postList);
                    NewPaPaListVideoActivity.Companion.startAct(this.mContext, arrayList, "collect", "", 0, 0, 0, true);
                    return;
                }
                NewPostDetailViewPagerActivity.Companion.startActivity(this.mContext, postList.getId(), false, false, false);
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
                MyBrowseListActivity.this.refresh();
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
                MyBrowseListActivity.this.loadMore();
            }
        };
        this.recyclerView.setAdapter(this.browseadapter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void loadMore() {
        getListFromServer(2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void refresh() {
        getListFromServer(1);
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 1) {
            updateData(message.arg1, (ArrayList) baseModel.obj);
        } else if (i != 2) {
        } else {
            this.dialog.dismiss();
            this.browseadapter.getData().clear();
            this.browseadapter.notifyDataSetChanged();
            this.browseadapter.setEmptyViewState(2, this.refreshLayout);
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        hideWaitingDialog();
        int i = message.what;
        if (i != 1) {
            if (i != 2) {
                return false;
            }
            ToastUtil.showCenterToast((int) R.string.common_clear_fail);
            return false;
        }
        if (message.arg1 == 1) {
            this.refreshLayout.mo6481finishRefresh();
        } else {
            this.browseadapter.loadMoreFail();
        }
        if (this.browseadapter.getData().size() != 0) {
            return false;
        }
        this.browseadapter.setEmptyViewState(1, this.refreshLayout);
        return false;
    }

    private void updateData(int i, ArrayList<PostList> arrayList) {
        boolean z = true;
        if (arrayList == null || arrayList.isEmpty()) {
            if (i == 1) {
                this.refreshLayout.mo6481finishRefresh();
                this.browseadapter.setEmptyViewState(2, this.refreshLayout);
            }
            if (i != 2) {
                return;
            }
            this.browseadapter.loadMoreEnd();
            return;
        }
        if (i == 1) {
            this.refreshLayout.mo6481finishRefresh();
            this.pageNo = 2;
            this.browseadapter.setNewData(arrayList);
        } else {
            this.pageNo++;
            this.browseadapter.addData(arrayList);
        }
        if (arrayList.size() < this.pageSize) {
            z = false;
        }
        if (z) {
            this.browseadapter.loadMoreComplete();
        } else {
            this.browseadapter.loadMoreEnd();
        }
    }
}
