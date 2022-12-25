package com.one.tomato.p085ui.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.dialog.LevelUpdateDialog;
import com.one.tomato.entity.BalanceBean;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.TaskBean;
import com.one.tomato.entity.TaskBeanSection;
import com.one.tomato.entity.TaskList;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UserPermissionUtil;
import com.one.tomato.utils.ViewUtil;
import java.util.ArrayList;
import org.slf4j.Marker;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_task_center)
/* renamed from: com.one.tomato.ui.task.TaskCenterActivity */
/* loaded from: classes3.dex */
public class TaskCenterActivity extends BaseRecyclerViewActivity {
    private BaseRecyclerSectionAdapter<TaskBeanSection> mAdapter;
    private BalanceBean mBalanceBean;
    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;
    private boolean taskDone;
    private boolean taskTake;
    @ViewInject(R.id.tv_expire)
    private TextView tv_expire;
    @ViewInject(R.id.tv_expire_level)
    private TextView tv_expire_level;
    @ViewInject(R.id.tv_virtual)
    private TextView tv_virtual;

    public static void startEarnActivity(Context context) {
        ((Activity) context).startActivityForResult(new Intent(context, TaskCenterActivity.class), 1212);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.task_center_title);
        initData();
        initAdapter();
        getMyBalance();
        getTaskList();
    }

    private void initData() {
        BalanceBean balanceBean = this.mBalanceBean;
        if (balanceBean != null) {
            this.tv_virtual.setText(balanceBean.getBalance());
        }
        LevelBean levelBean = DBUtil.getLevelBean();
        TextView textView = this.tv_expire;
        textView.setText(levelBean.getCurrentLevelValue() + "");
        TextView textView2 = this.tv_expire_level;
        textView2.setText("Lv." + levelBean.getCurrentLevelIndex());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.taskDone) {
            getTaskList();
            this.taskDone = false;
        }
    }

    @Event({R.id.back, R.id.rl_virtual, R.id.rl_expire})
    private void onClick(View view) {
        BalanceBean balanceBean;
        int id = view.getId();
        if (id == R.id.back) {
            onBackPressed();
        } else if (id == R.id.rl_expire) {
            ExpireRecordInfoActivity.startActivity(this.mContext);
        } else if (id != R.id.rl_virtual || (balanceBean = this.mBalanceBean) == null) {
        } else {
            VirtualRecordInfoActivity.startActivity(this.mContext, balanceBean.getBalance());
        }
    }

    private void initAdapter() {
        this.mAdapter = new BaseRecyclerSectionAdapter<TaskBeanSection>(this.mContext, R.layout.item_task, R.layout.item_task_section, this.recyclerView) { // from class: com.one.tomato.ui.task.TaskCenterActivity.1
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter
            public void onLoadMore() {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter
            public void convertHead(BaseViewHolder baseViewHolder, TaskBeanSection taskBeanSection) {
                super.convertHead(baseViewHolder, (BaseViewHolder) taskBeanSection);
                baseViewHolder.setText(R.id.tv_task_type, taskBeanSection.header);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, TaskBeanSection taskBeanSection) {
                super.convert(baseViewHolder, (BaseViewHolder) taskBeanSection);
                ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_category);
                TextView textView = (TextView) baseViewHolder.getView(R.id.tv_virtual);
                TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_experience);
                TextView textView3 = (TextView) baseViewHolder.getView(R.id.tv_progress);
                TextView textView4 = (TextView) baseViewHolder.getView(R.id.tv_action);
                baseViewHolder.addOnClickListener(R.id.tv_action);
                TaskBean taskBean = (TaskBean) taskBeanSection.f1223t;
                ((TextView) baseViewHolder.getView(R.id.tv_content)).setText(taskBean.getTitle());
                int rewardFreeViewVideo = taskBean.getRewardFreeViewVideo();
                if (rewardFreeViewVideo == 0) {
                    textView.setText(AppUtil.getString(R.string.common_potato_virtual) + Marker.ANY_NON_NULL_MARKER + taskBean.getRewardMoney());
                    textView2.setText(AppUtil.getString(R.string.task_experience) + Marker.ANY_NON_NULL_MARKER + taskBean.getRewardExperience());
                } else if (rewardFreeViewVideo == 1) {
                    textView.setText(R.string.task_video_free);
                    textView2.setText("");
                }
                if (taskBean.getSchedule() > taskBean.getTarget()) {
                    taskBean.setSchedule(taskBean.getTarget());
                }
                ViewUtil.initTextViewWithSpannableString(textView3, new String[]{AppUtil.getString(R.string.task_progress), taskBean.getSchedule() + "/", taskBean.getTarget() + ""}, new String[]{String.valueOf(TaskCenterActivity.this.getResources().getColor(R.color.text_light)), String.valueOf(TaskCenterActivity.this.getResources().getColor(R.color.colorAccent)), String.valueOf(TaskCenterActivity.this.getResources().getColor(R.color.text_light))}, new String[]{"12", "12", "12"});
                if (taskBean.getTaskType() == 1) {
                    textView.setText(AppUtil.getString(R.string.task_vip_days, 3));
                    textView2.setText(R.string.task_vip_days_expire);
                    textView3.setText(R.string.task_vip_days_desc);
                } else if (taskBean.getTaskConfigId() == 3) {
                    textView.setText(AppUtil.getString(R.string.task_vip_days, 3));
                }
                int status = taskBean.getStatus();
                if (status == 0) {
                    textView4.setBackgroundResource(R.drawable.shape_task_center_start_bg);
                    textView4.setText(R.string.task_go);
                } else if (status == 1) {
                    textView4.setBackgroundResource(R.drawable.shape_task_center_receive_bg);
                    textView4.setText(R.string.task_receive);
                } else if (status == 2) {
                    textView4.setBackgroundResource(R.drawable.shape_task_center_finish_bg);
                    textView4.setText(R.string.task_done);
                }
                int taskConfigId = taskBean.getTaskConfigId();
                int i = R.drawable.task_center_papa_comment;
                switch (taskConfigId) {
                    case 1:
                    case 2:
                        i = R.drawable.task_center_share;
                        break;
                    case 3:
                        i = R.drawable.task_center_register;
                        break;
                    case 4:
                        i = R.drawable.task_center_person_info;
                        break;
                    case 5:
                    case 10:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 22:
                    case 29:
                    case 30:
                    default:
                        i = R.drawable.task_center_browse_post;
                        break;
                    case 6:
                    case 11:
                    case 12:
                    case 13:
                    case 23:
                    case 24:
                    case 25:
                        i = R.drawable.task_center_thumb;
                        break;
                    case 7:
                    case 18:
                        i = R.drawable.task_center_publish;
                        break;
                    case 8:
                    case 20:
                        i = R.drawable.task_center_post_comment;
                        break;
                    case 9:
                    case 21:
                        break;
                    case 19:
                        i = R.drawable.task_center_browse_papa;
                        break;
                    case 26:
                    case 27:
                        i = R.drawable.task_center_receive_comment;
                        break;
                    case 28:
                        i = R.drawable.task_center_qcode;
                        break;
                    case 31:
                        i = R.drawable.task_center_spread;
                        break;
                }
                imageView.setImageResource(i);
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter
            public void onRecyclerItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                super.onRecyclerItemChildClick(baseQuickAdapter, view, i);
                if (view.getId() != R.id.tv_action) {
                    return;
                }
                TaskBeanSection taskBeanSection = (TaskBeanSection) TaskCenterActivity.this.mAdapter.getData().get(i);
                int status = ((TaskBean) taskBeanSection.f1223t).getStatus();
                if (status != 0) {
                    if (status != 1) {
                        return;
                    }
                    TaskCenterActivity.this.taskReceive(((TaskBean) taskBeanSection.f1223t).getId(), i);
                    return;
                }
                UserPermissionUtil.getInstance().intentTask(this.mContext, ((TaskBean) taskBeanSection.f1223t).getRedirectPageParamValue(), ((TaskBean) taskBeanSection.f1223t).getRedirectPageId());
                TaskCenterActivity.this.taskDone = true;
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter
            public void onEmptyRefresh(int i) {
                setEmptyViewState(0, null);
                TaskCenterActivity.this.getTaskList();
            }
        };
        this.recyclerView.setNestedScrollingEnabled(false);
        this.recyclerView.setAdapter(this.mAdapter);
    }

    private void getMyBalance() {
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/account/accountInfo");
        tomatoParams.addParameter("memberId", "" + DBUtil.getMemberId());
        tomatoParams.get(new TomatoCallback((ResponseObserver) this, 5, BalanceBean.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getTaskList() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/task/list");
        tomatoParams.addParameter("memberId", "" + DBUtil.getMemberId());
        tomatoParams.get(new TomatoCallback((ResponseObserver) this, 3, TaskList.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void taskReceive(int i, int i2) {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/task/receiveCoin");
        tomatoParams.addParameter("memberId", "" + DBUtil.getMemberId());
        tomatoParams.addParameter(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(i));
        tomatoParams.get(new TomatoCallback((ResponseObserver) this, 4, LevelBean.class, i2));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 3) {
            updateInfo((TaskList) baseModel.obj);
        } else if (i != 4) {
            if (i != 5) {
                return;
            }
            this.mBalanceBean = (BalanceBean) baseModel.obj;
            initData();
        } else {
            LevelBean levelBean = (LevelBean) baseModel.obj;
            if (levelBean.getCurrentLevelIndex() > DBUtil.getLevelBean().getCurrentLevelIndex()) {
                new LevelUpdateDialog(this.mContext, levelBean);
            }
            initData();
            ((TaskBean) ((TaskBeanSection) this.mAdapter.getData().get(message.arg1)).f1223t).setStatus(2);
            this.mAdapter.notifyDataSetChanged();
            getMyBalance();
            this.taskTake = true;
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        int i = message.what;
        if (i == 3) {
            this.mAdapter.setEnableLoadMore(false);
            this.mAdapter.setEmptyViewState(1, null);
        } else if (i == 4) {
            ToastUtil.showCenterToast((int) R.string.task_receive_fail);
        }
        return false;
    }

    private void updateInfo(TaskList taskList) {
        ArrayList arrayList = new ArrayList();
        if (taskList.getSpreadTaskList() != null && !taskList.getSpreadTaskList().isEmpty()) {
            for (int i = 0; i < taskList.getSpreadTaskList().size(); i++) {
                if (i == 0) {
                    arrayList.add(new TaskBeanSection(true, AppUtil.getString(R.string.task_promote)));
                }
                arrayList.add(new TaskBeanSection(taskList.getSpreadTaskList().get(i)));
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (int i2 = 0; i2 < taskList.getList().size(); i2++) {
            TaskBean taskBean = taskList.getList().get(i2);
            int taskType = taskBean.getTaskType();
            if (taskType == 2) {
                arrayList2.add(taskBean);
            } else if (taskType == 3) {
                arrayList3.add(taskBean);
            }
        }
        if (!arrayList2.isEmpty()) {
            for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                if (i3 == 0) {
                    arrayList.add(new TaskBeanSection(true, AppUtil.getString(R.string.task_new)));
                }
                arrayList.add(new TaskBeanSection((TaskBean) arrayList2.get(i3)));
            }
        }
        if (!arrayList3.isEmpty()) {
            for (int i4 = 0; i4 < arrayList3.size(); i4++) {
                if (i4 == 0) {
                    arrayList.add(new TaskBeanSection(true, AppUtil.getString(R.string.task_day)));
                }
                arrayList.add(new TaskBeanSection((TaskBean) arrayList3.get(i4)));
            }
        }
        if (arrayList.isEmpty()) {
            this.mAdapter.setEmptyViewState(1, null);
            return;
        }
        this.mAdapter.setNewData(arrayList);
        this.mAdapter.setEnableLoadMore(false);
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.taskTake) {
            setResult(-1);
        }
        super.onBackPressed();
    }
}
