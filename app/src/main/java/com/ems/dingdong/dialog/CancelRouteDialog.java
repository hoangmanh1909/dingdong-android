package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.ems.dingdong.R;
import com.ems.dingdong.views.CustomBoldTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CancelRouteDialog extends Dialog {

    @BindView(R.id.tv_lading_code)
    CustomBoldTextView ladingCode;
    @BindView(R.id.end_route_name_content)
    CustomBoldTextView routeName;
    @BindView(R.id.end_postman_content)
    CustomBoldTextView postmanName;

    private OnOkClickListener okClickListener;

    public CancelRouteDialog(Context context, String ladingCode, String postmanName, String routeName) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_cancel_route, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.ladingCode.setText(ladingCode);
        this.postmanName.setText(postmanName);
        this.routeName.setText(routeName);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_cancel_dialog, R.id.tv_ok_dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel_dialog:
                dismiss();
                break;

            case R.id.tv_ok_dialog:
                okClickListener.onClick(this);
                break;
        }
    }


    public CancelRouteDialog setOnOkListener(OnOkClickListener listener) {
        this.okClickListener = listener;
        return this;
    }

    public interface OnOkClickListener {
        void onClick(CancelRouteDialog cancelRouteDialog);
    }

}
