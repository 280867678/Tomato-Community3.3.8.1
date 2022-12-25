package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.support.p005v7.widget.SimpleItemAnimator;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.model.TaskBoxEntity;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.task.TaskBoxAdapter;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.TaskBottomDialog */
/* loaded from: classes3.dex */
public class TaskBottomDialog extends BaseBottomDialogFragment {
    private static final String KEY_LAYOUT_RES = "bottom_layout_res";
    private List<TaskBoxEntity> mData;
    @LayoutRes
    private int mLayoutRes;
    private TaskBoxAdapter mTaskBoxAdapter;
    private TaskClickListener taskSendClickListener;

    /* renamed from: com.tomatolive.library.ui.view.dialog.TaskBottomDialog$TaskClickListener */
    /* loaded from: classes3.dex */
    public interface TaskClickListener {
        void onTaskCallback(TaskBoxEntity taskBoxEntity);
    }

    public static TaskBottomDialog create(TaskClickListener taskClickListener) {
        TaskBottomDialog taskBottomDialog = new TaskBottomDialog();
        taskBottomDialog.setOnSendClickListener(taskClickListener);
        return taskBottomDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment, com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment, com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.mLayoutRes = bundle.getInt(KEY_LAYOUT_RES);
        }
    }

    @Override // android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt(KEY_LAYOUT_RES, this.mLayoutRes);
        super.onSaveInstanceState(bundle);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public int getLayoutRes() {
        this.mLayoutRes = R$layout.fq_layout_task_view;
        return this.mLayoutRes;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected void initView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R$id.rl_content);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 3));
        this.mTaskBoxAdapter = new TaskBoxAdapter(getData());
        recyclerView.setAdapter(this.mTaskBoxAdapter);
        this.mTaskBoxAdapter.bindToRecyclerView(recyclerView);
        this.mTaskBoxAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$TaskBottomDialog$xq85uQClvyvtg4Fiv2jlfM1OgIA
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                TaskBottomDialog.this.lambda$initView$0$TaskBottomDialog(baseQuickAdapter, view2, i);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$TaskBottomDialog(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        TaskClickListener taskClickListener;
        TaskBoxEntity taskBoxEntity = (TaskBoxEntity) baseQuickAdapter.getItem(i);
        if (taskBoxEntity == null || (taskClickListener = this.taskSendClickListener) == null) {
            return;
        }
        taskClickListener.onTaskCallback(taskBoxEntity);
    }

    public List<TaskBoxEntity> getData() {
        return this.mData;
    }

    public void setmData(List<TaskBoxEntity> list) {
        this.mData = list;
    }

    private void setOnSendClickListener(TaskClickListener taskClickListener) {
        this.taskSendClickListener = taskClickListener;
    }

    public void updateSingleData(TaskBoxEntity taskBoxEntity) {
        TaskBoxAdapter taskBoxAdapter = this.mTaskBoxAdapter;
        if (taskBoxAdapter != null) {
            taskBoxAdapter.updateItemBy(taskBoxEntity);
        }
    }
}
