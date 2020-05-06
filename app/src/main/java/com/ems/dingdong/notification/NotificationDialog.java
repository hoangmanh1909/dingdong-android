package com.ems.dingdong.notification;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.ems.dingdong.R;
import com.ems.dingdong.model.NotificationInfo;
import com.ems.dingdong.views.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationDialog extends Dialog {

    @BindView(R.id.tv_date)
    CustomTextView tv_date;
    @BindView(R.id.tv_ladingcode)
    CustomTextView tv_ladingcode;
    @BindView(R.id.tv_id)
    CustomTextView tv_id;
    @BindView(R.id.tv_content)
    CustomTextView tv_content;
    @BindView(R.id.tv_title)
    CustomTextView tv_title;
    @BindView(R.id.tv_phone_number)
    CustomTextView tv_phone_number;


    public NotificationDialog(@NonNull Context context, NotificationInfo item) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.fragment_notification_dialog, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        tv_id.setText(String.valueOf(item.getId()));
        tv_title.setText(item.getTitle());
        tv_phone_number.setText(item.getMobileNumber());
        tv_content.setText(item.getContent());
        tv_date.setText(item.getCreateDate());
        tv_ladingcode.setText(item.getAddInfo());
    }

    @OnClick({R.id.btnBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                dismiss();
                break;
        }
    }

}
