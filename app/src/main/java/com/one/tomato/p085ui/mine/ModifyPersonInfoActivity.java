package com.one.tomato.p085ui.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_modify_info)
/* renamed from: com.one.tomato.ui.mine.ModifyPersonInfoActivity */
/* loaded from: classes3.dex */
public class ModifyPersonInfoActivity extends BaseActivity {
    @ViewInject(R.id.et_desc)
    private EditText et_desc;
    @ViewInject(R.id.et_nick)
    private EditText et_nick;
    @ViewInject(R.id.last_t)
    private TextView last_t;
    @ViewInject(R.id.ll_desc)
    private LinearLayout ll_desc;
    @ViewInject(R.id.ll_nick)
    private LinearLayout ll_nick;
    private int requestCode;
    @ViewInject(R.id.tv_confirm)
    private TextView tv_confirm;

    public static void startActivity(Context context, int i, String str) {
        Intent intent = new Intent();
        intent.setClass(context, ModifyPersonInfoActivity.class);
        intent.putExtra("requestCode", i);
        intent.putExtra("type_intent", str);
        ((Activity) context).startActivityForResult(intent, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.requestCode = getIntent().getExtras().getInt("requestCode");
        getIntent().getExtras().getString("type_intent");
        initTitleBar();
        init();
        setListener();
    }

    private void init() {
        UserInfo userInfo = DBUtil.getUserInfo();
        int i = this.requestCode;
        if (i == 24) {
            this.titleTV.setText(R.string.modify_person_nick);
            this.ll_nick.setVisibility(0);
            this.et_nick.setText(userInfo.getName());
            EditText editText = this.et_nick;
            editText.setSelection(editText.getText().toString().trim().length());
        } else if (i != 25) {
        } else {
            this.titleTV.setText(R.string.modify_person_signature);
            this.ll_desc.setVisibility(0);
            this.et_desc.setText(userInfo.getSignature());
            EditText editText2 = this.et_desc;
            editText2.setSelection(editText2.getText().toString().trim().length());
            TextView textView = this.last_t;
            textView.setText(userInfo.getSignature().trim().length() + "/24");
        }
    }

    private void setListener() {
        this.et_nick.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.mine.ModifyPersonInfoActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    ModifyPersonInfoActivity.this.tv_confirm.setEnabled(true);
                } else {
                    ModifyPersonInfoActivity.this.tv_confirm.setEnabled(false);
                }
            }
        });
        this.et_desc.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.mine.ModifyPersonInfoActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    ModifyPersonInfoActivity.this.tv_confirm.setEnabled(true);
                } else {
                    ModifyPersonInfoActivity.this.tv_confirm.setEnabled(false);
                }
                TextView textView = ModifyPersonInfoActivity.this.last_t;
                textView.setText(editable.toString().trim().length() + "/24");
            }
        });
        this.tv_confirm.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.mine.ModifyPersonInfoActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String str;
                int i = ModifyPersonInfoActivity.this.requestCode;
                String str2 = "";
                if (i == 24) {
                    str2 = ModifyPersonInfoActivity.this.et_nick.getText().toString().trim();
                    str = "nick";
                } else if (i != 25) {
                    str = str2;
                } else {
                    str2 = ModifyPersonInfoActivity.this.et_desc.getText().toString().trim();
                    str = "signature";
                }
                if (AppUtil.containsEmoji(str2)) {
                    ToastUtil.showCenterToast((int) R.string.input_without_emoji);
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(str, str2);
                ModifyPersonInfoActivity.this.setResult(-1, intent);
                ModifyPersonInfoActivity.this.finish();
            }
        });
    }
}
