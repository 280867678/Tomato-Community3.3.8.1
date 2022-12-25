package com.tomatolive.library.p136ui.view.task;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.TaskBoxEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.task.TaskBoxAdapter */
/* loaded from: classes2.dex */
public class TaskBoxAdapter extends BaseQuickAdapter<TaskBoxEntity, BaseViewHolder> {
    private List<TaskBoxEntity> mData;
    private int selectedPosition = -1;

    private float setAlpha(int i) {
        return i == 3 ? 0.5f : 1.0f;
    }

    public TaskBoxAdapter(@Nullable List<TaskBoxEntity> list) {
        super(R$layout.fq_item_grid_task, list);
        this.mData = list;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, TaskBoxEntity taskBoxEntity) {
        boolean z = true;
        boolean z2 = this.selectedPosition == baseViewHolder.getAdapterPosition();
        BaseViewHolder gone = baseViewHolder.setGone(R$id.tv_item_num, taskBoxEntity.getStatus() == 2);
        int i = R$id.tv_item_name;
        if (taskBoxEntity.getStatus() == 2) {
            z = false;
        }
        gone.setGone(i, z).setText(R$id.tv_item_name, getDesc(taskBoxEntity)).setBackgroundRes(R$id.tv_item_name, setBackColor(taskBoxEntity)).setText(R$id.tv_item_num, getDesc(taskBoxEntity)).setAlpha(R$id.iv_item_icon, setAlpha(taskBoxEntity.getStatus())).getView(R$id.ll_item_layout).setSelected(z2);
        ImageView imageView = (ImageView) baseViewHolder.getView(R$id.iv_item_icon);
        String propImg = taskBoxEntity.getPropImg();
        if (!TextUtils.isEmpty(propImg) && taskBoxEntity.getStatus() == 2) {
            GlideUtils.loadImage(this.mContext, imageView, propImg, R$drawable.fq_ic_gift_default);
        } else {
            GlideUtils.loadImage(this.mContext, imageView, R$drawable.fq_task);
        }
    }

    private int setBackColor(TaskBoxEntity taskBoxEntity) {
        int i = R$drawable.fq_btn_round_blue_selector;
        int status = taskBoxEntity.getStatus();
        if (status != 0) {
            if (status == 1) {
                return R$drawable.fq_btn_round_red_selector;
            }
            return (status == 2 || status != 3) ? i : R$drawable.fq_btn_round_blue_selector;
        }
        return R$drawable.fq_btn_round_blue_selector;
    }

    private String getDesc(TaskBoxEntity taskBoxEntity) {
        int status = taskBoxEntity.getStatus();
        if (status != 0) {
            if (status == 1) {
                return this.mContext.getString(AppUtils.isConsumptionPermissionUser() ? R$string.fq_receive_task : R$string.fq_receive_task_box);
            } else if (status != 2) {
                return status != 3 ? "" : this.mContext.getString(R$string.fq_please_wait);
            } else {
                return "x" + taskBoxEntity.getPropNumber();
            }
        }
        return DateUtils.stringForTime(NumberUtils.string2long(taskBoxEntity.getOpenTime(), 1L) * 1000);
    }

    public void updateItemBy(TaskBoxEntity taskBoxEntity) {
        int indexOf = this.mData.indexOf(taskBoxEntity);
        if (indexOf < 0) {
            return;
        }
        this.mData.set(indexOf, taskBoxEntity);
        notifyItemChanged(indexOf);
    }

    public void setSelectedPosition(int i) {
        this.selectedPosition = i;
        notifyDataSetChanged();
    }

    public void clearSelectedPosition() {
        View viewByPosition;
        if (isPosition(this.selectedPosition) && (viewByPosition = getViewByPosition(this.selectedPosition, R$id.ll_item_layout)) != null) {
            viewByPosition.setSelected(false);
        }
    }

    private boolean isPosition(int i) {
        return i >= 0 && i < getData().size();
    }
}
