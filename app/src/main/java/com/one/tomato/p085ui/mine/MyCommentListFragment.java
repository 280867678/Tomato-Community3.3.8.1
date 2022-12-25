package com.one.tomato.p085ui.mine;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.adapter.ImageGridAdapter;
import com.one.tomato.dialog.DeleMsgDialog;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.MyCommentList;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.mvp.p080ui.papa.view.NewPaPaListVideoActivity;
import com.one.tomato.mvp.p080ui.post.view.NewPostDetailViewPagerActivity;
import com.one.tomato.mvp.p080ui.showimage.ImageShowActivity;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.emotion.EmotionUtil;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.NoScrollGridView;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.fragment_my_comment)
/* renamed from: com.one.tomato.ui.mine.MyCommentListFragment */
/* loaded from: classes3.dex */
public class MyCommentListFragment extends BaseRecyclerViewFragment {
    private String businessType;
    private BaseRecyclerViewAdapter<MyCommentList> commentAdapter;
    private DeleMsgDialog msgDialog = null;
    private int myPosition = ConstantUtils.MAX_ITEM_NUM;
    private String url;
    private View view;

    public static MyCommentListFragment getInstance(String str, String str2) {
        MyCommentListFragment myCommentListFragment = new MyCommentListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", str);
        bundle.putString("business", str2);
        myCommentListFragment.setArguments(bundle);
        return myCommentListFragment;
    }

    @Override // com.one.tomato.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.url = arguments.getString("url");
            this.businessType = arguments.getString("business");
        }
        this.msgDialog = new DeleMsgDialog(this.mContext);
        this.msgDialog.setMessgeListener(new DeleMsgDialog.onSetMessgeListener(this) { // from class: com.one.tomato.ui.mine.MyCommentListFragment.1
            @Override // com.one.tomato.dialog.DeleMsgDialog.onSetMessgeListener
            public void onsetTitle(TextView textView) {
                textView.setText(R.string.common_notify);
            }

            @Override // com.one.tomato.dialog.DeleMsgDialog.onSetMessgeListener
            public void onsetContent(TextView textView) {
                textView.setText(R.string.post_comment_delete_tip);
            }
        });
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment, com.one.tomato.base.BaseFragment, android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = super.onCreateView(layoutInflater, viewGroup, bundle);
        if ("post".equals(this.businessType)) {
            setUserVisibleHint(true);
        }
        return this.view;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        this.refreshLayout.autoRefresh(100);
        initAdapter();
    }

    private void initAdapter() {
        this.commentAdapter = new BaseRecyclerViewAdapter<MyCommentList>(this.mContext, R.layout.item_mine_comment_list, this.recyclerView) { // from class: com.one.tomato.ui.mine.MyCommentListFragment.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, MyCommentList myCommentList) {
                super.convert(baseViewHolder, (BaseViewHolder) myCommentList);
                TextView textView = (TextView) baseViewHolder.getView(R.id.tv_content);
                NoScrollGridView noScrollGridView = (NoScrollGridView) baseViewHolder.getView(R.id.gridView);
                ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_post_thumb);
                TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_desc);
                TextView textView3 = (TextView) baseViewHolder.getView(R.id.tv_group);
                baseViewHolder.getView(R.id.tv_deles);
                baseViewHolder.addOnClickListener(R.id.iv_head).addOnClickListener(R.id.rl_post).addOnClickListener(R.id.tv_deles);
                ImageLoaderUtil.loadHeadImage(this.mContext, (ImageView) baseViewHolder.getView(R.id.iv_head), new ImageBean(myCommentList.getAvatar()));
                ((TextView) baseViewHolder.getView(R.id.tv_name)).setText(myCommentList.getName());
                ((TextView) baseViewHolder.getView(R.id.tv_time)).setText(myCommentList.getCreateTime());
                if (TextUtils.isEmpty(myCommentList.getContent())) {
                    textView.setVisibility(8);
                } else {
                    textView.setVisibility(0);
                    EmotionUtil.spannableEmoticonFilter(textView, myCommentList.getContent());
                }
                if (!TextUtils.isEmpty(myCommentList.getSecCommentThumbnail()) && myCommentList.getSecCommentThumbnail().split(";").length > 0) {
                    noScrollGridView.setVisibility(0);
                    final ArrayList arrayList = new ArrayList();
                    String[] split = !TextUtils.isEmpty(myCommentList.getSecCommentImageUrl()) ? myCommentList.getSecCommentImageUrl().split(";") : new String[0];
                    String[] split2 = !TextUtils.isEmpty(myCommentList.getSecCommentThumbnail()) ? myCommentList.getSecCommentThumbnail().split(";") : new String[0];
                    for (int i = 0; i < split2.length; i++) {
                        ImageBean imageBean = new ImageBean();
                        imageBean.setImage(split[i]);
                        imageBean.setSecret(true);
                        arrayList.add(imageBean);
                    }
                    noScrollGridView.setAdapter((ListAdapter) new ImageGridAdapter(this.mContext, arrayList, R.layout.item_img_grid));
                    noScrollGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.one.tomato.ui.mine.MyCommentListFragment.2.1
                        @Override // android.widget.AdapterView.OnItemClickListener
                        public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
                            ImageShowActivity.Companion.startActivity(((BaseQuickAdapter) C28492.this).mContext, arrayList, i2);
                        }
                    });
                } else {
                    noScrollGridView.setVisibility(8);
                }
                if (!TextUtils.isEmpty(myCommentList.getSecArticleThumbnail())) {
                    imageView.setVisibility(0);
                    ImageBean imageBean2 = new ImageBean();
                    imageBean2.setImage(myCommentList.getSecArticleThumbnail().split(";")[0]);
                    imageBean2.setSecret(true);
                    ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, imageView, imageBean2);
                } else if (!TextUtils.isEmpty(myCommentList.getSecArticleVideoCover())) {
                    imageView.setVisibility(0);
                    ImageBean imageBean3 = new ImageBean();
                    imageBean3.setImage(myCommentList.getSecArticleVideoCover());
                    imageBean3.setSecret(true);
                    ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, imageView, imageBean3);
                } else {
                    imageView.setVisibility(8);
                }
                if (!TextUtils.isEmpty(myCommentList.getDescription())) {
                    try {
                        textView2.setText(myCommentList.getDescription());
                    } catch (Exception e) {
                        Log.i("eeeeee", e.getMessage());
                    }
                } else if (myCommentList.getPostType() == 1) {
                    textView2.setText("[" + AppUtil.getString(R.string.circle_post_img) + "]");
                } else if (myCommentList.getPostType() == 2) {
                    textView2.setText("[" + AppUtil.getString(R.string.circle_post_video) + "]");
                } else if (myCommentList.getPostType() == 4) {
                    textView2.setText("[" + AppUtil.getString(R.string.circle_post_video) + "]");
                }
                textView3.setText(myCommentList.getGroupName());
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onRecyclerItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
                super.onRecyclerItemChildClick(baseQuickAdapter, view, i);
                final MyCommentList myCommentList = (MyCommentList) MyCommentListFragment.this.commentAdapter.getItem(i);
                int id = view.getId();
                if (id == R.id.iv_head) {
                    NewMyHomePageActivity.Companion.startActivity(this.mContext, DBUtil.getMemberId());
                } else if (id == R.id.rl_post) {
                    NewPostDetailViewPagerActivity.Companion.startActivity(this.mContext, myCommentList.getArticleId(), false, false, false);
                } else if (id != R.id.tv_deles) {
                } else {
                    MyCommentListFragment.this.msgDialog.show();
                    MyCommentListFragment.this.msgDialog.setYesOnclickListener(new DeleMsgDialog.onYesOnclickListener() { // from class: com.one.tomato.ui.mine.MyCommentListFragment.2.2
                        @Override // com.one.tomato.dialog.DeleMsgDialog.onYesOnclickListener
                        public void onYesClick() {
                            MyCommentListFragment.this.msgDialog.dismiss();
                            if (MyCommentListFragment.this.businessType.equals("post")) {
                                MyCommentListFragment myCommentListFragment = MyCommentListFragment.this;
                                myCommentListFragment.requestPostDelete("2", "" + myCommentList.getId());
                                MyCommentListFragment.this.myPosition = i;
                                return;
                            }
                            MyCommentListFragment myCommentListFragment2 = MyCommentListFragment.this;
                            myCommentListFragment2.requestVideoDelete(myCommentList.getId() + "");
                            MyCommentListFragment.this.myPosition = i;
                        }
                    });
                }
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
                MyCommentListFragment.this.refresh();
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
                MyCommentListFragment.this.loadMore();
            }
        };
        this.recyclerView.setAdapter(this.commentAdapter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment
    public void loadMore() {
        char c;
        String str = this.businessType;
        int hashCode = str.hashCode();
        if (hashCode != 3446944) {
            if (hashCode == 112202875 && str.equals("video")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals("post")) {
                c = 0;
            }
            c = 65535;
        }
        if (c == 0 || c == 1) {
            getListFromServer(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewFragment
    public void refresh() {
        char c;
        String str = this.businessType;
        int hashCode = str.hashCode();
        if (hashCode != 3446944) {
            if (hashCode == 112202875 && str.equals("video")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals("post")) {
                c = 0;
            }
            c = 65535;
        }
        if (c == 0 || c == 1) {
            getListFromServer(1);
        }
    }

    public void getListFromServer(int i) {
        if (1 == i) {
            this.pageNo = 1;
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), this.url);
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("sort", 1);
        tomatoParams.addParameter("type", Integer.valueOf("post".equals(this.businessType) ? 1 : 2));
        tomatoParams.post(new TomatoCallback(this, 1, new TypeToken<ArrayList<MyCommentList>>(this) { // from class: com.one.tomato.ui.mine.MyCommentListFragment.3
        }.getType(), i));
    }

    @Override // com.one.tomato.base.BaseFragment, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 1) {
            updateData(message.arg1, (ArrayList) baseModel.obj);
        } else if (i == 2) {
            ToastUtil.showCenterToast(baseModel.message);
            this.commentAdapter.remove(this.myPosition);
        } else if (i == 3) {
            ToastUtil.showCenterToast(baseModel.message);
            this.commentAdapter.remove(this.myPosition);
        } else if (i != 4) {
        } else {
            ArrayList<PostList> arrayList = new ArrayList<>();
            arrayList.add((PostList) baseModel.obj);
            NewPaPaListVideoActivity.Companion.startAct(this.mContext, arrayList, this.businessType, "", 0, 0, 0, true);
        }
    }

    @Override // com.one.tomato.base.BaseFragment, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i != 1) {
            if (i == 2) {
                ToastUtil.showCenterToast(baseModel.message);
            } else if (i == 3) {
                ToastUtil.showCenterToast(baseModel.message);
            }
            return false;
        }
        if (message.arg1 == 1) {
            this.refreshLayout.mo6481finishRefresh();
        } else {
            this.commentAdapter.loadMoreFail();
        }
        if (this.commentAdapter.getData().size() != 0) {
            return true;
        }
        this.commentAdapter.setEmptyViewState(1, this.refreshLayout);
        return true;
    }

    private void updateData(int i, ArrayList<MyCommentList> arrayList) {
        boolean z = true;
        if (arrayList == null || arrayList.isEmpty()) {
            if (i == 1) {
                this.refreshLayout.mo6481finishRefresh();
                this.commentAdapter.setEmptyViewState(2, this.refreshLayout);
            }
            if (i != 2) {
                return;
            }
            this.commentAdapter.loadMoreEnd();
            return;
        }
        if (i == 1) {
            this.refreshLayout.mo6481finishRefresh();
            this.pageNo = 2;
            this.commentAdapter.setNewData(arrayList);
        } else {
            this.pageNo++;
            this.commentAdapter.addData(arrayList);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void requestPostDelete(String str, String str2) {
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/comment/delete");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("type", str);
        tomatoParams.addParameter("businessId", str2);
        tomatoParams.post(new TomatoCallback(this, 2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestVideoDelete(String str) {
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/videoComments/delete");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter(DatabaseFieldConfigLoader.FIELD_NAME_ID, str);
        tomatoParams.post(new TomatoCallback(this, 3));
    }
}
