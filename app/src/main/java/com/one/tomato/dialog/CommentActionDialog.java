package com.one.tomato.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.p080ui.post.view.PostAndMemberReportActivity;
import com.one.tomato.utils.DBUtil;

/* loaded from: classes3.dex */
public class CommentActionDialog extends BottomSheetDialog implements View.OnClickListener {
    private int articleId;
    private CommentDeleteListener commentDeleteListener;
    private int commentId;
    private Context context;
    private LinearLayout ll_delete_comment;
    private LinearLayout ll_manage_menu;
    private LinearLayout ll_one_manage;
    private LinearLayout ll_report_comment;
    private LinearLayout ll_report_down_comment;
    private int position;
    private TextView tv_cancel;
    private int type;

    /* loaded from: classes3.dex */
    public interface CommentDeleteListener {
        void showCommentDeleteDialog(int i, int i2, int i3, int i4);
    }

    public CommentActionDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_comment_action, (ViewGroup) null);
        setContentView(inflate);
        this.ll_delete_comment = (LinearLayout) inflate.findViewById(R.id.ll_delete_comment);
        this.ll_report_comment = (LinearLayout) inflate.findViewById(R.id.ll_report_comment);
        this.ll_report_down_comment = (LinearLayout) inflate.findViewById(R.id.ll_report_down_comment);
        this.ll_manage_menu = (LinearLayout) inflate.findViewById(R.id.ll_manage_menu);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.ll_content);
        this.tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        this.ll_one_manage = (LinearLayout) inflate.findViewById(R.id.ll_one_manage);
        this.ll_delete_comment.setOnClickListener(this);
        this.ll_report_comment.setOnClickListener(this);
        this.ll_report_down_comment.setOnClickListener(this);
        this.tv_cancel.setOnClickListener(this);
        inflate.findViewById(R.id.tv_down_comment).setOnClickListener(this);
        inflate.findViewById(R.id.tv_down_no_publish_comment).setOnClickListener(this);
        inflate.findViewById(R.id.tv_down_black).setOnClickListener(this);
    }

    public void setCommentList(int i, int i2, int i3, int i4, int i5, int i6) {
        this.position = i;
        this.articleId = i3;
        this.commentId = i4;
        this.type = i6;
        this.ll_delete_comment.setVisibility(8);
        this.ll_report_comment.setVisibility(8);
        this.ll_one_manage.setVisibility(0);
        this.ll_manage_menu.setVisibility(8);
        if (DBUtil.getMemberId() == i2) {
            this.ll_delete_comment.setVisibility(0);
            if (DBUtil.getMemberId() == i5) {
                this.ll_report_comment.setVisibility(8);
            } else {
                this.ll_report_comment.setVisibility(0);
            }
        } else if (DBUtil.getMemberId() == i5) {
            this.ll_delete_comment.setVisibility(0);
            this.ll_report_comment.setVisibility(8);
        } else {
            this.ll_delete_comment.setVisibility(8);
            this.ll_report_comment.setVisibility(0);
        }
        if (DBUtil.getUserInfo().getRoleType() == 2) {
            this.ll_report_down_comment.setVisibility(0);
        } else {
            this.ll_report_down_comment.setVisibility(8);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_delete_comment /* 2131297541 */:
                dismiss();
                CommentDeleteListener commentDeleteListener = this.commentDeleteListener;
                if (commentDeleteListener == null) {
                    return;
                }
                commentDeleteListener.showCommentDeleteDialog(this.position, this.articleId, this.commentId, this.type);
                return;
            case R.id.ll_report_comment /* 2131297656 */:
                dismiss();
                PostAndMemberReportActivity.startActivity(this.context, "comment", this.commentId);
                return;
            case R.id.ll_report_down_comment /* 2131297657 */:
                this.ll_one_manage.setVisibility(8);
                this.ll_manage_menu.setVisibility(0);
                return;
            case R.id.tv_cancel /* 2131298712 */:
                dismiss();
                return;
            case R.id.tv_down_black /* 2131298854 */:
                PostAndMemberReportActivity.startActivity(this.context, "down_comment", this.articleId, 3, this.commentId);
                dismiss();
                return;
            case R.id.tv_down_comment /* 2131298855 */:
                PostAndMemberReportActivity.startActivity(this.context, "down_comment", this.articleId, 4, this.commentId);
                dismiss();
                return;
            case R.id.tv_down_no_publish_comment /* 2131298857 */:
                PostAndMemberReportActivity.startActivity(this.context, "down_comment", this.articleId, 5, this.commentId);
                dismiss();
                return;
            default:
                return;
        }
    }

    public void setCommentDeleteListener(CommentDeleteListener commentDeleteListener) {
        this.commentDeleteListener = commentDeleteListener;
    }
}
