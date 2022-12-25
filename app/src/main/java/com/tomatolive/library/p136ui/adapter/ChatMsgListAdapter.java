package com.tomatolive.library.p136ui.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.download.CarDownLoadManager;
import com.tomatolive.library.model.ChatEntity;
import com.tomatolive.library.model.IconEntity;
import com.tomatolive.library.p136ui.view.custom.UserGradeView;
import com.tomatolive.library.p136ui.view.span.NetImageSpan;
import com.tomatolive.library.p136ui.view.span.VerticalImageSpan;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.FileUtils;
import com.tomatolive.library.utils.NumberUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.adapter.ChatMsgListAdapter */
/* loaded from: classes3.dex */
public class ChatMsgListAdapter extends BaseQuickAdapter<ChatEntity, BaseViewHolder> {
    private static final int MAX_ITEM_COUNT = 400;
    private OnItemClickListener listener;
    private boolean isBig = false;
    private final int invalidImgResID = -1;
    private final String GIFT_NUM_1 = "1";

    /* renamed from: com.tomatolive.library.ui.adapter.ChatMsgListAdapter$OnItemClickListener */
    /* loaded from: classes3.dex */
    public interface OnItemClickListener {
        void onItemClick(ChatEntity chatEntity);

        void onItemTextClick(Object obj);
    }

    public ChatMsgListAdapter(int i) {
        super(i);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    public void setTextSize(boolean z) {
        this.isBig = z;
        notifyDataSetChanged();
    }

    public void addMsg(ChatEntity chatEntity) {
        if (chatEntity == null) {
            return;
        }
        if (this.mData == null) {
            this.mData = new LinkedList();
        }
        ensureMessageListNotOver(1);
        this.mData.add(chatEntity);
        notifyItemInserted(this.mData.size());
        if (getRecyclerView().canScrollVertically(1)) {
            return;
        }
        ((LinearLayoutManager) getRecyclerView().getLayoutManager()).scrollToPositionWithOffset(this.mData.size() - 1, 0);
    }

    public void addMsgs(List<ChatEntity> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        if (this.mData == null) {
            this.mData = new LinkedList();
        }
        ensureMessageListNotOver(list.size());
        this.mData.addAll(list);
        notifyDataSetChanged();
        if (getRecyclerView().canScrollVertically(1)) {
            return;
        }
        ((LinearLayoutManager) getRecyclerView().getLayoutManager()).scrollToPositionWithOffset(this.mData.size() - 1, 0);
    }

    private void ensureMessageListNotOver(int i) {
        if (i + this.mData.size() > 400) {
            this.mData.removeAll(new ArrayList<>(this.mData.subList(100, 200)));
            notifyItemRangeRemoved(100, 100);
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, final ChatEntity chatEntity) {
        Drawable formatDrawableBounds;
        int length;
        int indexOf;
        LinearLayout linearLayout = (LinearLayout) baseViewHolder.getView(R$id.ll_msg_bg);
        TextView textView = (TextView) baseViewHolder.getView(R$id.tv_chat_text);
        UserGradeView userGradeView = (UserGradeView) baseViewHolder.getView(R$id.user_grade_view);
        userGradeView.setVisibility(8);
        ImageView imageView = (ImageView) baseViewHolder.getView(R$id.iv_mark);
        imageView.setVisibility(8);
        if (chatEntity != null && !TextUtils.isEmpty(chatEntity.getMsgText())) {
            linearLayout.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_msg_bg_gray));
            textView.setTextSize(2, this.isBig ? 18.0f : 12.0f);
            textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_white_color));
            textView.setMovementMethod(null);
            int msgType = chatEntity.getMsgType();
            String str = ConstantUtils.PLACEHOLDER_STR_TWO;
            boolean z = false;
            switch (msgType) {
                case 0:
                    IconEntity iconEntity = getIconEntity(chatEntity, userGradeView);
                    String msgSendName = chatEntity.getMsgSendName();
                    int length2 = iconEntity.value.length();
                    int length3 = msgSendName.length() + length2;
                    StringBuilder sb = new StringBuilder();
                    sb.append(iconEntity.value);
                    sb.append(msgSendName);
                    sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
                    sb.append(chatEntity.getMsgText());
                    if (!TextUtils.isEmpty(chatEntity.getCarIcon())) {
                        sb.append(str);
                    }
                    String sb2 = sb.toString();
                    iconEntity.value = sb2;
                    SpannableString spannableString = new SpannableString(sb2);
                    setIcon(iconEntity, spannableString, textView);
                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, getMsgTextJoinColorRes(chatEntity.getNobilityType()))), length2, length3, 33);
                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_white_color)), length3, iconEntity.value.length(), 33);
                    if (!TextUtils.isEmpty(chatEntity.getCarIcon())) {
                        File downloadFile = CarDownLoadManager.getInstance().getDownloadFile(chatEntity.getCarIcon());
                        int length4 = iconEntity.value.length() - 1;
                        int length5 = iconEntity.value.length();
                        if (downloadFile != null && FileUtils.isFile(downloadFile)) {
                            Bitmap scale = ImageUtils.scale(ImageUtils.getBitmap(downloadFile), ConvertUtils.dp2px(35.0f), ConvertUtils.dp2px(30.0f));
                            if (scale != null && (formatDrawableBounds = formatDrawableBounds(ImageUtils.bitmap2Drawable(scale))) != null) {
                                spannableString.setSpan(new VerticalImageSpan(formatDrawableBounds), length4, length5, 33);
                            }
                        } else {
                            NetImageSpan netImageSpan = new NetImageSpan(textView);
                            netImageSpan.setUrl(chatEntity.getCarIcon());
                            netImageSpan.setHeight(ConvertUtils.dp2px(30.0f));
                            spannableString.setSpan(netImageSpan, length4, length5, 33);
                        }
                    }
                    textView.setText(spannableString);
                    break;
                case 1:
                case 3:
                    if (chatEntity.getGuardType() > 0) {
                        linearLayout.setBackground(ContextCompat.getDrawable(this.mContext, chatEntity.getGuardType() == NumberUtils.string2int("3") ? R$drawable.fq_guard_year_chat_bg_shape : R$drawable.fq_guard_week_chat_bg_shape));
                    }
                    IconEntity iconEntity2 = getIconEntity(chatEntity, userGradeView);
                    String msgSendName2 = chatEntity.getMsgSendName();
                    int length6 = iconEntity2.value.length();
                    int length7 = msgSendName2.length() + length6 + getColonStr(chatEntity.getRole(), chatEntity.getMsgType()).length();
                    String str2 = iconEntity2.value + msgSendName2 + getColonStr(chatEntity.getRole(), chatEntity.getMsgType()) + chatEntity.getMsgText();
                    iconEntity2.value = str2;
                    SpannableString spannableString2 = new SpannableString(str2);
                    setIcon(iconEntity2, spannableString2, textView);
                    spannableString2.setSpan(new ForegroundColorSpan(getRoleMsgColor(chatEntity.getMsgType(), chatEntity.getRole())), length6, length7, 33);
                    if (chatEntity.getMsgType() == 1) {
                        try {
                            String giftName = chatEntity.getGiftName();
                            String giftNum = chatEntity.getGiftNum();
                            int length8 = this.mContext.getString(R$string.fq_give_to_anchor).length() + length7;
                            if (TextUtils.equals("1", giftNum)) {
                                length = giftName.length() + length8;
                            } else {
                                length = giftName.length() + length8 + giftNum.length() + 1;
                            }
                            if (length > iconEntity2.value.length()) {
                                length = iconEntity2.value.length();
                            }
                            spannableString2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_white_color)), length7, length8, 33);
                            spannableString2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_gift_color)), length8, length, 33);
                            spannableString2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_white_color)), length, iconEntity2.value.length(), 33);
                        } catch (Exception unused) {
                            spannableString2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_white_color)), length7, iconEntity2.value.length(), 33);
                        }
                    } else {
                        spannableString2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, AppUtils.isHouseSuperManager(chatEntity.getRole()) ? R$color.fq_live_msg_red_dark_color : R$color.fq_live_msg_white_color)), length7, iconEntity2.value.length(), 33);
                    }
                    if (isSendNameDrawableRight(chatEntity.getRole()) && (indexOf = iconEntity2.value.indexOf(chatEntity.getMsgText())) >= 2) {
                        spannableString2.setSpan(new ForegroundColorSpan(getRoleMsgColor(chatEntity.getMsgType(), chatEntity.getRole())), indexOf - 2, indexOf, 33);
                    }
                    textView.setText(spannableString2);
                    break;
                case 2:
                    linearLayout.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_msg_bg_transparent));
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_notice_color));
                    textView.setText(Html.fromHtml(this.mContext.getString(R$string.fq_socket_reconnet_click)));
                    break;
                case 4:
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_notice_color));
                    if (AppUtils.isHouseSuperManager(chatEntity.getRole()) && !TextUtils.equals(chatEntity.getUid(), chatEntity.getAnchorId())) {
                        textView.setText(Html.fromHtml(this.mContext.getString(R$string.fq_banned_forever, chatEntity.getTargetName(), chatEntity.getMsgSendName())));
                        return;
                    } else {
                        textView.setText(Html.fromHtml(this.mContext.getString(chatEntity.isSomeoneBanPost() ? R$string.fq_banned : R$string.fq_banned_cancel, chatEntity.getTargetName(), chatEntity.getMsgSendName())));
                        break;
                    }
                case 5:
                    textView.setText(AppUtils.getLiveSysMsgStr() + chatEntity.getMsgText());
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_notice_color));
                    break;
                case 6:
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_notice_color));
                    textView.setText(Html.fromHtml(this.mContext.getString(chatEntity.isSetManager() ? R$string.fq_appoint_house_manage : R$string.fq_cancel_appoint_house_manage, chatEntity.getTargetName())));
                    break;
                case 7:
                    linearLayout.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_msg_bg_transparent));
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_notice_color));
                    textView.setText(chatEntity.getMsgText());
                    break;
                case 8:
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_notice_color));
                    textView.setText(this.mContext.getString(R$string.fq_modify_title_tip, chatEntity.getMsgText()));
                    break;
                case 9:
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_notice_color));
                    textView.setText(Html.fromHtml(this.mContext.getString(AppUtils.isHouseSuperManager(chatEntity.getRole()) ? R$string.fq_warn_content_out_room_forever : R$string.fq_warn_content_out_room, chatEntity.getTargetName(), chatEntity.getMsgSendName())));
                    break;
                case 10:
                    linearLayout.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_msg_bg_transparent));
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_notice_color));
                    textView.setText(chatEntity.getMsgText());
                    break;
                case 11:
                    String expGrade = chatEntity.getExpGrade();
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_notice_color));
                    textView.setText(Html.fromHtml(this.mContext.getString(R$string.fq_speak_level_tip_for_socket, expGrade)));
                    break;
                case 12:
                    int i = AppUtils.isYearGuard(chatEntity.getGuardType()) ? R$drawable.fq_guard_year_bg_shape : R$drawable.fq_guard_mouth_bg_shape;
                    int i2 = AppUtils.isYearGuard(chatEntity.getGuardType()) ? R$color.fq_guard_year_text_bg : R$color.fq_guard_mouth_text_bg;
                    linearLayout.setBackground(ContextCompat.getDrawable(this.mContext, i));
                    IconEntity iconEntity3 = getIconEntity(chatEntity, userGradeView);
                    String str3 = chatEntity.getMsgSendName() + ConstantUtils.PLACEHOLDER_STR_ONE;
                    String string = this.mContext.getString(R$string.fq_open_guard_tip);
                    int length9 = iconEntity3.value.length() + str3.length() + string.length();
                    String str4 = iconEntity3.value + str3 + string + chatEntity.getMsgText();
                    iconEntity3.value = str4;
                    SpannableString spannableString3 = new SpannableString(str4);
                    setIcon(iconEntity3, spannableString3, textView);
                    spannableString3.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, i2)), length9, iconEntity3.value.length(), 33);
                    textView.setText(spannableString3);
                    break;
                case 13:
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_white_color));
                    textView.setText(Html.fromHtml(this.mContext.getString(R$string.fq_gift_box_broadcast_tips, chatEntity.getMsgSendName(), chatEntity.getTargetName(), chatEntity.getPropNum(), chatEntity.getPropName())));
                    break;
                case 14:
                    linearLayout.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_msg_bg_gray));
                    IconEntity iconEntity4 = getIconEntity(chatEntity, userGradeView);
                    String msgSendName3 = chatEntity.getMsgSendName();
                    int length10 = iconEntity4.value.length();
                    int length11 = msgSendName3.length() + length10;
                    String str5 = iconEntity4.value + msgSendName3 + ConstantUtils.PLACEHOLDER_STR_ONE + chatEntity.getMsgText() + str;
                    iconEntity4.value = str5;
                    SpannableString spannableString4 = new SpannableString(str5);
                    setIcon(iconEntity4, spannableString4, textView);
                    spannableString4.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, getMsgTextJoinColorRes(chatEntity.getNobilityType()))), length10, length11, 33);
                    spannableString4.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_white_color)), length11, iconEntity4.value.length(), 33);
                    int nobilityBadgeDrawableRes = getNobilityBadgeDrawableRes(chatEntity.getNobilityType());
                    if (nobilityBadgeDrawableRes != -1) {
                        spannableString4.setSpan(new VerticalImageSpan(formatDrawableBounds(ContextCompat.getDrawable(this.mContext, nobilityBadgeDrawableRes))), iconEntity4.value.length() - 1, iconEntity4.value.length(), 33);
                    }
                    textView.setText(spannableString4);
                    break;
                case 15:
                    linearLayout.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_msg_bg_gray));
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_notice_color));
                    textView.setText(this.mContext.getString(R$string.fq_congratulations_exp_grade_update, chatEntity.getMsgSendName(), chatEntity.getExpGrade()));
                    break;
                case 16:
                    String msgText = chatEntity.getMsgText();
                    if (msgText.contains("${}$")) {
                        msgText = msgText.replace("${}$", "");
                    }
                    linearLayout.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_msg_bg_notice));
                    imageView.setVisibility(0);
                    imageView.setImageResource(R$drawable.fq_ic_live_msg_chess);
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_white_color));
                    textView.setText(msgText);
                    break;
                case 17:
                case 18:
                    linearLayout.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_msg_bg_notice));
                    imageView.setVisibility(0);
                    if (chatEntity.getMsgType() == 17) {
                        z = true;
                    }
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_notice_color));
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(chatEntity.getMsgText());
                    if (!z) {
                        str = "   ";
                    }
                    sb3.append(str);
                    String sb4 = sb3.toString();
                    SpannableString spannableString5 = new SpannableString(sb4);
                    int length12 = sb4.length() - 1;
                    int length13 = sb4.length();
                    if (TextUtils.isEmpty(chatEntity.getTargetId())) {
                        imageView.setImageResource(z ? R$drawable.fq_ic_live_msg_nobility : R$drawable.fq_ic_live_msg_attention);
                        spannableString5.setSpan(getVerticalImageSpan(z ? R$drawable.fq_ic_live_msg_yellow_arrow : R$drawable.fq_ic_live_msg_attention_add), length12, length13, 33);
                    } else if (!z) {
                        imageView.setImageResource(R$drawable.fq_ic_live_msg_attention_gray);
                        spannableString5 = new SpannableString(chatEntity.getMsgText());
                    }
                    textView.setText(spannableString5);
                    break;
                case 19:
                    linearLayout.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_msg_bg_notice));
                    imageView.setVisibility(0);
                    imageView.setImageResource(R$drawable.fq_ic_live_msg_turntable);
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_white_color));
                    textView.setText(Html.fromHtml(this.mContext.getString(R$string.fq_msg_luck_notice_tips_1, chatEntity.getMsgSendName(), chatEntity.getPropId(), chatEntity.getPropName(), chatEntity.getPropNum())));
                    break;
                case 20:
                    linearLayout.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_msg_bg_notice));
                    imageView.setVisibility(0);
                    imageView.setImageResource(R$drawable.fq_ic_live_msg_horn);
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_white_color));
                    textView.setText(Html.fromHtml(this.mContext.getString(R$string.fq_msg_nobility_horn, chatEntity.getMsgSendName(), chatEntity.getMsgText(), chatEntity.getTargetName())));
                    break;
                case 21:
                    linearLayout.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_msg_bg_gray));
                    textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_live_msg_notice_color));
                    textView.setText(this.mContext.getString(R$string.fq_msg_sys_broadcast, chatEntity.getMsgText()));
                    break;
            }
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.adapter.-$$Lambda$ChatMsgListAdapter$7DxDN8qA86vTkNOKLDY7MPPMtfo
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ChatMsgListAdapter.this.lambda$convert$0$ChatMsgListAdapter(chatEntity, view);
                }
            });
        }
    }

    public /* synthetic */ void lambda$convert$0$ChatMsgListAdapter(ChatEntity chatEntity, View view) {
        OnItemClickListener onItemClickListener = this.listener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(chatEntity);
        }
    }

    private void setIcon(IconEntity iconEntity, SpannableString spannableString, TextView textView) {
        setIcon(iconEntity, spannableString, "", textView);
    }

    private void setIcon(IconEntity iconEntity, SpannableString spannableString, String str, TextView textView) {
        List<String> markUrls = iconEntity.getMarkUrls();
        int length = TextUtils.isEmpty(str) ? 0 : str.length();
        for (int i = 0; i < markUrls.size(); i++) {
            String str2 = markUrls.get(i);
            int i2 = (length + 2) - 1;
            char c = 65535;
            switch (str2.hashCode()) {
                case -2082608924:
                    if (str2.equals(ConstantUtils.NOBILITY_TYPE_ICON_KEY)) {
                        c = 3;
                        break;
                    }
                    break;
                case -1962995782:
                    if (str2.equals(ConstantUtils.EXP_GRADE_ICON_KEY)) {
                        c = 0;
                        break;
                    }
                    break;
                case -186628225:
                    if (str2.equals(ConstantUtils.GUARD_TYPE_ICON_KEY)) {
                        c = 2;
                        break;
                    }
                    break;
                case 3506294:
                    if (str2.equals(ConstantUtils.ROLE_ICON_KEY)) {
                        c = 1;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                Drawable drawable = iconEntity.userGradeDrawable;
                if (drawable != null) {
                    spannableString.setSpan(new VerticalImageSpan(drawable), length, i2, 33);
                }
            } else if (c == 1) {
                Drawable drawable2 = iconEntity.roleDrawable;
                if (drawable2 != null) {
                    spannableString.setSpan(new VerticalImageSpan(drawable2), length, i2, 33);
                }
            } else if (c == 2) {
                Drawable drawable3 = iconEntity.guardDrawable;
                if (drawable3 != null) {
                    spannableString.setSpan(new VerticalImageSpan(drawable3), length, i2, 33);
                }
            } else if (c == 3) {
                Drawable drawable4 = iconEntity.nobilityDrawable;
                if (drawable4 != null) {
                    spannableString.setSpan(new VerticalImageSpan(drawable4), length, i2, 33);
                }
            } else if (textView != null) {
                NetImageSpan netImageSpan = new NetImageSpan(textView);
                netImageSpan.setUrl(str2);
                netImageSpan.setHeight(ConvertUtils.dp2px(21.0f));
                spannableString.setSpan(netImageSpan, length, i2, 33);
            }
            length = i2 + 1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private IconEntity getIconEntity(ChatEntity chatEntity, UserGradeView userGradeView) {
        char c;
        List<String> markUrls = chatEntity.getMarkUrls();
        IconEntity iconEntity = new IconEntity();
        iconEntity.urls = new ArrayList();
        if (markUrls != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < markUrls.size(); i++) {
                String str = markUrls.get(i);
                switch (str.hashCode()) {
                    case -2082608924:
                        if (str.equals(ConstantUtils.NOBILITY_TYPE_ICON_KEY)) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1962995782:
                        if (str.equals(ConstantUtils.EXP_GRADE_ICON_KEY)) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -186628225:
                        if (str.equals(ConstantUtils.GUARD_TYPE_ICON_KEY)) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3506294:
                        if (str.equals(ConstantUtils.ROLE_ICON_KEY)) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                if (c == 0) {
                    userGradeView.initUserGradeMsg(chatEntity.getExpGrade(), true);
                    Bitmap viewBitmap = getViewBitmap(userGradeView);
                    if (viewBitmap != null) {
                        iconEntity.userGradeDrawable = formatDrawableBounds(ImageUtils.bitmap2Drawable(viewBitmap));
                        if (iconEntity.userGradeDrawable != null) {
                            sb.append(ConstantUtils.PLACEHOLDER_STR_TWO);
                            iconEntity.urls.add(str);
                        }
                    }
                } else if (c != 1) {
                    if (c == 2) {
                        int guardIconRes = getGuardIconRes(chatEntity.getGuardType());
                        if (guardIconRes != -1) {
                            iconEntity.guardDrawable = formatDrawableBounds(ContextCompat.getDrawable(this.mContext, guardIconRes));
                            if (iconEntity.guardDrawable != null) {
                                sb.append(ConstantUtils.PLACEHOLDER_STR_TWO);
                                iconEntity.urls.add(str);
                            }
                        }
                    } else if (c == 3) {
                        if (chatEntity.getMsgType() == 14) {
                            iconEntity.nobilityDrawable = null;
                        } else {
                            int nobilityBadgeDrawableRes = getNobilityBadgeDrawableRes(chatEntity.getNobilityType());
                            if (nobilityBadgeDrawableRes != -1) {
                                iconEntity.nobilityDrawable = formatDrawableBounds(ContextCompat.getDrawable(this.mContext, nobilityBadgeDrawableRes));
                                if (iconEntity.nobilityDrawable != null) {
                                    sb.append(ConstantUtils.PLACEHOLDER_STR_TWO);
                                    iconEntity.urls.add(str);
                                }
                            }
                        }
                    } else if (!TextUtils.isEmpty(str)) {
                        iconEntity.urls.add(str);
                        sb.append(ConstantUtils.PLACEHOLDER_STR_TWO);
                    }
                } else if (getRoleMsgIconRes(chatEntity.getRole()) != -1) {
                    iconEntity.roleDrawable = formatDrawableBounds(ContextCompat.getDrawable(this.mContext, getRoleMsgIconRes(chatEntity.getRole())));
                    if (iconEntity.roleDrawable != null) {
                        sb.append(ConstantUtils.PLACEHOLDER_STR_TWO);
                        iconEntity.urls.add(str);
                    }
                }
            }
            iconEntity.value = sb.toString();
        }
        return iconEntity;
    }

    private Bitmap getViewBitmap(View view) {
        try {
            view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache();
            return Bitmap.createBitmap(view.getDrawingCache());
        } catch (Exception unused) {
            return null;
        }
    }

    private int getRoleMsgColor(int i, String str) {
        if (AppUtils.isAnchor(str)) {
            return ContextCompat.getColor(this.mContext, R$color.fq_live_msg_anchor_color);
        }
        if (AppUtils.isHouseSuperManager(str)) {
            return ContextCompat.getColor(this.mContext, R$color.fq_live_msg_super_color);
        }
        if (AppUtils.isHouseManager(str)) {
            return ContextCompat.getColor(this.mContext, i == 1 ? R$color.fq_live_msg_gift_manager_color : R$color.fq_live_msg_manager_color);
        }
        return ContextCompat.getColor(this.mContext, i == 1 ? R$color.fq_live_msg_gift_audience_color : R$color.fq_live_msg_audience_color);
    }

    private String getColonStr(String str, int i) {
        return isSendNameDrawableRight(str) ? i == 1 ? ConstantUtils.PLACEHOLDER_STR_ONE : " : " : i == 1 ? ConstantUtils.PLACEHOLDER_STR_ONE : " : ";
    }

    private boolean isSendNameDrawableRight(String str) {
        return AppUtils.isAnchor(str) || AppUtils.isHouseManager(str) || AppUtils.isHouseSuperManager(str);
    }

    @DrawableRes
    private int getRoleMsgIconRes(String str) {
        if (AppUtils.isAnchor(str)) {
            boolean z = this.isBig;
            return R$drawable.fq_ic_live_msg_anchor_big;
        } else if (AppUtils.isHouseManager(str)) {
            boolean z2 = this.isBig;
            return R$drawable.fq_ic_live_msg_manager_big;
        } else if (!AppUtils.isHouseSuperManager(str)) {
            return -1;
        } else {
            boolean z3 = this.isBig;
            return R$drawable.fq_ic_live_msg_super_big_2;
        }
    }

    @DrawableRes
    private int getGuardIconRes(int i) {
        if (i != 0) {
            if (i == 1 || i == 2) {
                boolean z = this.isBig;
                return R$drawable.fq_ic_live_msg_mouth_guard_big;
            } else if (i != 3) {
                return -1;
            } else {
                boolean z2 = this.isBig;
                return R$drawable.fq_ic_live_msg_year_guard_big;
            }
        }
        return -1;
    }

    private Drawable formatDrawableBounds(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    @DrawableRes
    private int getNobilityBadgeDrawableRes(int i) {
        switch (i) {
            case 1:
                return R$drawable.fq_ic_nobility_badge_msg_1;
            case 2:
                return R$drawable.fq_ic_nobility_badge_msg_2;
            case 3:
                return R$drawable.fq_ic_nobility_badge_msg_3;
            case 4:
                return R$drawable.fq_ic_nobility_badge_msg_4;
            case 5:
                return R$drawable.fq_ic_nobility_badge_msg_5;
            case 6:
                return R$drawable.fq_ic_nobility_badge_msg_6;
            case 7:
                return R$drawable.fq_ic_nobility_badge_msg_7;
            default:
                return -1;
        }
    }

    @ColorRes
    private int getMsgTextJoinColorRes(int i) {
        return R$color.fq_live_msg_audience_color;
    }

    private VerticalImageSpan getVerticalImageSpan(@DrawableRes int i) {
        return new VerticalImageSpan(formatDrawableBounds(ContextCompat.getDrawable(this.mContext, i)));
    }
}
