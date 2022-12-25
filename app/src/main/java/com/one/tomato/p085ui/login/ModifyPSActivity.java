package com.one.tomato.p085ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.widget.ClearEditText;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_ps_modify)
/* renamed from: com.one.tomato.ui.login.ModifyPSActivity */
/* loaded from: classes3.dex */
public class ModifyPSActivity extends BaseActivity {
    @ViewInject(R.id.et_input_old_ps)
    private ClearEditText et_input_old_ps;
    @ViewInject(R.id.et_input_ps)
    private ClearEditText et_input_ps;
    @ViewInject(R.id.et_input_ps_twice)
    private ClearEditText et_input_ps_twice;
    @ViewInject(R.id.tv_commit)
    private TextView tv_commit;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ModifyPSActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initView();
        setListener();
    }

    private void initView() {
        initTitleBar();
        this.titleTV.setText(R.string.modify_ps_title);
    }

    private void setListener() {
        this.tv_commit.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.login.ModifyPSActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ModifyPSActivity.this.commit();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void commit() {
        String trim = this.et_input_old_ps.getText().toString().trim();
        int length = trim.length();
        if (TextUtils.isEmpty(trim)) {
            ToastUtil.showCenterToast((int) R.string.ps_input_origin);
        } else if (length < 6) {
            ToastUtil.showCenterToast((int) R.string.ps_length_tip1);
        } else {
            String trim2 = this.et_input_ps.getText().toString().trim();
            if (TextUtils.isEmpty(trim2)) {
                ToastUtil.showCenterToast((int) R.string.ps_input_new);
            } else if (trim2.length() < 6) {
                ToastUtil.showCenterToast((int) R.string.ps_length_tip1);
            } else {
                String trim3 = this.et_input_ps_twice.getText().toString().trim();
                if (TextUtils.isEmpty(trim3)) {
                    ToastUtil.showCenterToast((int) R.string.ps_input_new_twice);
                } else if (trim3.length() < 6) {
                    ToastUtil.showCenterToast((int) R.string.ps_length_tip1);
                } else if (!trim2.equals(trim3)) {
                    ToastUtil.showCenterToast((int) R.string.ps_twice_tip);
                } else {
                    showWaitingDialog();
                    TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/memberInfo/update");
                    tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
                    tomatoParams.addParameter("oldPassword", trim);
                    tomatoParams.addParameter("newPassword", trim2);
                    tomatoParams.addParameter("confirmPassword", trim3);
                    tomatoParams.post(new TomatoCallback(this, 1));
                }
            }
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        hideWaitingDialog();
        if (message.what != 1) {
            return;
        }
        ToastUtil.showCenterToast((int) R.string.modify_ps_success);
        finish();
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        return message.what == 1;
    }
}
