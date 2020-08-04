package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.ems.dingdong.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class PhoneDecisionDialog extends Dialog {

    private Context mContext;
    private OnClickListener callback;

    public PhoneDecisionDialog(Context context, OnClickListener callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        mContext = context;
        this.callback = callback;
        View view = View.inflate(getContext(), R.layout.dialog_call_decision, null);
        setContentView(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_call_by_sim, R.id.tv_call_by_vht})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_call_by_sim:
                callback.onCallBySimClicked(this);
                break;

            case R.id.tv_call_by_vht:
                callback.onCallByVHTClicked(this);
                break;
        }
    }

    public interface OnClickListener {

        void onCallBySimClicked(PhoneDecisionDialog dialog);

        void onCallByVHTClicked(PhoneDecisionDialog dialog);
    }
}
