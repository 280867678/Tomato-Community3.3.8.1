package com.one.tomato.dialog;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.ImageVerify;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DeviceInfoUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;

/* loaded from: classes3.dex */
public class ImageVerifyDialog extends CustomAlertDialog implements ResponseObserver {
    private EditText et_input;
    private int flag = 1;
    private ImageView iv_image;
    private ProgressBar progressBar;
    private TextView tv_refresh;
    public ImageVerifyConfirmListener verifyConfirmListener;

    /* loaded from: classes3.dex */
    public interface ImageVerifyConfirmListener {
        void imageVerifyConfirm(String str);
    }

    public ImageVerifyDialog(Context context, String str) {
        super(context);
        setTitle(str);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_image_verify, (ViewGroup) null);
        setContentView(inflate);
        this.iv_image = (ImageView) inflate.findViewById(R.id.iv_image);
        this.tv_refresh = (TextView) inflate.findViewById(R.id.tv_refresh);
        this.progressBar = (ProgressBar) inflate.findViewById(R.id.progressBar);
        this.et_input = (EditText) inflate.findViewById(R.id.et_input);
        setCancelButtonBackgroundRes(R.drawable.common_shape_solid_corner30_disable);
        setCancelButtonTextColor(R.color.white);
        setCancelButton(AppUtil.getString(R.string.common_cancel), new View.OnClickListener() { // from class: com.one.tomato.dialog.ImageVerifyDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ImageVerifyDialog.this.dialog.dismiss();
            }
        });
        setConfirmButtonBackgroundRes(R.drawable.common_selector_solid_corner30_coloraccent);
        setConfirmButtonTextColor(R.color.white);
        setConfirmButton(AppUtil.getString(R.string.common_confirm), new View.OnClickListener() { // from class: com.one.tomato.dialog.ImageVerifyDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String obj = ImageVerifyDialog.this.et_input.getText().toString();
                if (TextUtils.isEmpty(obj)) {
                    return;
                }
                ImageVerifyDialog.this.dialog.dismiss();
                if (ImageVerifyDialog.this.verifyConfirmListener == null || TextUtils.isEmpty(obj)) {
                    return;
                }
                ImageVerifyDialog.this.verifyConfirmListener.imageVerifyConfirm(obj.trim());
            }
        });
        this.tv_refresh.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.ImageVerifyDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!AppUtil.isFastClick(ImageVerifyDialog.this.tv_refresh.getId(), 3000)) {
                    if (ImageVerifyDialog.this.flag == 2) {
                        ImageVerifyDialog imageVerifyDialog = ImageVerifyDialog.this;
                        imageVerifyDialog.getRegisterLoginVerifyImage(imageVerifyDialog.flag);
                        return;
                    }
                    ImageVerifyDialog.this.getVerifyImage();
                    return;
                }
                ToastUtil.showCenterToast((int) R.string.post_fast_click_tip);
            }
        });
    }

    public void getVerifyImage() {
        this.progressBar.setVisibility(0);
        this.tv_refresh.setVisibility(8);
        this.iv_image.setImageDrawable(this.context.getResources().getDrawable(R.drawable.default_imag_white));
        this.et_input.setText("");
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/verify/getUrl");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 1, ImageVerify.class));
    }

    public void getRegisterLoginVerifyImage(int i) {
        this.flag = i;
        ImageLoaderUtil.loadVerifySecImage(this.context, this.iv_image, DomainServer.getInstance().getServerUrl(), new ImageBean("/app/verify/_s3/loginCaptcha.jpg?deviceNo=" + DeviceInfoUtil.getUniqueDeviceID() + "&time=" + System.currentTimeMillis()), ImageLoaderUtil.getCenterInsideImageOptionWhite(this.iv_image));
        updateUI();
    }

    @Override // com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        String url = ((ImageVerify) ((BaseModel) message.obj).obj).getUrl();
        ImageLoaderUtil.loadVerifySecImage(this.context, this.iv_image, DomainServer.getInstance().getServerUrl(), new ImageBean(url + "&token=" + DBUtil.getToken() + "&time=" + System.currentTimeMillis()), ImageLoaderUtil.getCenterInsideImageOptionWhite(this.iv_image));
        updateUI();
    }

    @Override // com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        updateUI();
        return true;
    }

    @Override // com.one.tomato.net.ResponseObserver
    public boolean handleHttpRequestError(Message message) {
        updateUI();
        return true;
    }

    @Override // com.one.tomato.net.ResponseObserver
    public boolean handleRequestCancel(Message message) {
        updateUI();
        return true;
    }

    private void updateUI() {
        this.progressBar.setVisibility(8);
        this.tv_refresh.setVisibility(0);
    }

    public void setImageVerifyConfirmListener(ImageVerifyConfirmListener imageVerifyConfirmListener) {
        this.verifyConfirmListener = imageVerifyConfirmListener;
    }
}
