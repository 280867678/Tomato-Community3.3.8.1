package com.one.tomato.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.adapter.CommonBaseAdapter;
import com.one.tomato.adapter.ViewHolder;
import com.one.tomato.entity.PostList;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.widget.NoScrollGridView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PostAdDeletePop extends PopupWindow implements ResponseObserver {
    private NoScrollGridView gridView;
    private PostAdDeleteSuccessListener listener;
    private MyAdapter myAdapter;
    private int position;
    private PostList postList;
    private RelativeLayout rl_root;
    private TextView tv_close;
    private TextView tv_title;
    private List<String> reasons = new ArrayList();
    private List<String> selections = new ArrayList();

    /* loaded from: classes3.dex */
    public interface PostAdDeleteSuccessListener {
        void deletePostAdFail(int i);

        void deletePostAdIng(int i);

        void deletePostAdSuccess(int i);
    }

    @Override // com.one.tomato.net.ResponseObserver
    public boolean handleRequestCancel(Message message) {
        return false;
    }

    public PostAdDeletePop(Context context, PostAdDeleteSuccessListener postAdDeleteSuccessListener) {
        super(context);
        this.listener = postAdDeleteSuccessListener;
        View inflate = LayoutInflater.from(context).inflate(R.layout.pop_post_ad_delete, (ViewGroup) null);
        setContentView(inflate);
        setWidth((int) (DisplayMetricsUtils.getWidth() - 60.0f));
        setHeight((int) DisplayMetricsUtils.dp2px(150.0f));
        setBackgroundDrawable(new ColorDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        this.rl_root = (RelativeLayout) inflate.findViewById(R.id.rl_root);
        this.tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        this.tv_close = (TextView) inflate.findViewById(R.id.tv_close);
        this.gridView = (NoScrollGridView) inflate.findViewById(R.id.gridView);
        this.reasons.add(AppUtil.getString(R.string.post_ad_delete_reason1));
        this.reasons.add(AppUtil.getString(R.string.post_ad_delete_reason2));
        this.reasons.add(AppUtil.getString(R.string.post_ad_delete_reason3));
        this.myAdapter = new MyAdapter(context, this.reasons, R.layout.item_deleted_post_ad);
        this.gridView.setAdapter((ListAdapter) this.myAdapter);
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.one.tomato.popup.PostAdDeletePop.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                String str = (String) PostAdDeletePop.this.reasons.get(i);
                if (PostAdDeletePop.this.selections.contains(str)) {
                    PostAdDeletePop.this.selections.remove(str);
                } else {
                    PostAdDeletePop.this.selections.add(str);
                }
                PostAdDeletePop.this.myAdapter.notifyDataSetChanged();
                if (PostAdDeletePop.this.selections.size() > 0) {
                    PostAdDeletePop.this.tv_title.setText(AppUtil.getString(R.string.post_ad_delete_reasons, Integer.valueOf(PostAdDeletePop.this.selections.size())));
                    PostAdDeletePop.this.tv_close.setText(AppUtil.getString(R.string.common_confirm));
                    return;
                }
                PostAdDeletePop.this.tv_title.setText(R.string.post_ad_delete_tip);
                PostAdDeletePop.this.tv_close.setText(R.string.post_ad_delete);
            }
        });
        this.tv_close.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.popup.PostAdDeletePop.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PostAdDeletePop.this.delete();
            }
        });
    }

    public void setPostList(int i, PostList postList) {
        this.position = i;
        this.postList = postList;
    }

    public void setBackground(boolean z) {
        RelativeLayout relativeLayout = this.rl_root;
        if (relativeLayout == null) {
            return;
        }
        if (z) {
            relativeLayout.setBackgroundResource(R.drawable.post_ad_bg_up);
        } else {
            relativeLayout.setBackgroundResource(R.drawable.post_ad_bg_down);
        }
    }

    public void init() {
        this.postList = null;
        this.selections.clear();
        this.myAdapter.notifyDataSetChanged();
        this.tv_title.setText(R.string.post_ad_delete_title);
        this.tv_close.setText(R.string.post_ad_delete);
    }

    /* loaded from: classes3.dex */
    class MyAdapter extends CommonBaseAdapter<String> {
        private Context context;
        private TextView tv_reason;

        public MyAdapter(Context context, List<String> list, int i) {
            super(context, list, i);
            this.context = context;
        }

        @Override // com.one.tomato.adapter.CommonBaseAdapter
        public void convert(ViewHolder viewHolder, String str, int i) {
            this.tv_reason = (TextView) viewHolder.getView(R.id.tv_reason);
            this.tv_reason.setText(str);
            if (PostAdDeletePop.this.selections.size() > 0 && PostAdDeletePop.this.selections.contains(str)) {
                this.tv_reason.setTextColor(this.context.getResources().getColor(R.color.colorPrimary));
                this.tv_reason.setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
                return;
            }
            this.tv_reason.setTextColor(Color.parseColor("#FF666666"));
            this.tv_reason.setBackgroundResource(R.drawable.common_shape_stroke_corner30_divider);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delete() {
        if (this.postList == null) {
            return;
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/advert/close");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("articleId", Integer.valueOf(this.postList.getId()));
        if (this.selections.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.selections.size(); i++) {
                sb.append(this.selections.get(i));
                if (i < this.selections.size() - 1) {
                    sb.append(";");
                }
            }
            tomatoParams.addParameter("reason", sb.toString());
        }
        tomatoParams.post(new TomatoCallback(this, 1));
        if (this.listener == null) {
            return;
        }
        dismiss();
        this.listener.deletePostAdIng(this.position);
    }

    @Override // com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        if (message.what != 1 || this.listener == null) {
            return;
        }
        dismiss();
        this.listener.deletePostAdSuccess(this.position);
    }

    @Override // com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        if (message.what != 1 || this.listener == null) {
            return false;
        }
        dismiss();
        this.listener.deletePostAdFail(this.position);
        return false;
    }

    @Override // com.one.tomato.net.ResponseObserver
    public boolean handleHttpRequestError(Message message) {
        if (message.what != 1 || this.listener == null) {
            return false;
        }
        dismiss();
        this.listener.deletePostAdFail(this.position);
        return false;
    }
}
