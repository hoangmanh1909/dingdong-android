package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.core.base.BaseActivity;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.AddSupportTypeCallback;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.SupportTypeResponse;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DialogAddSupportType extends Dialog {

    @BindView(R.id.edt_description)
    FormItemEditText description;
    @BindView(R.id.tv_support_type)
    FormItemTextView tvSupportType;

    private final AddSupportTypeCallback mDelegate;
    private final List<SupportTypeResponse> mListSupportType;
    private final BaseActivity mActivity;
    private ItemBottomSheetPickerUIFragment pickerUIReason;
    private SupportTypeResponse supportTypeResponse;

    public DialogAddSupportType(Context context, List<SupportTypeResponse> listReason, AddSupportTypeCallback reasonCallback) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        this.mListSupportType = listReason;
        mActivity = (BaseActivity) context;
        View view = View.inflate(getContext(), R.layout.dialog_add_support_type, null);
        setContentView(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void show() {
        super.show();
    }


    @OnClick({R.id.iv_close, R.id.tv_support_type, R.id.tv_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_support_type:
                showUIReason();
                break;
            case R.id.tv_update:
                if (TextUtils.isEmpty(tvSupportType.getText())) {
                    Toast.showToast(mActivity, "Xin vui lòng chọn loại hỗ trợ");
                    return;
                }

                if (mDelegate != null) {
                    mDelegate.onResponse(description.getText(), supportTypeResponse.getId());
                    dismiss();
                }

                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }

    private void showUIReason() {
        ArrayList<Item> items = new ArrayList<>();
        for (SupportTypeResponse item : mListSupportType) {
            items.add(new Item(String.valueOf(item.getId()), item.getName()));
        }
        if (pickerUIReason == null) {
            pickerUIReason = new ItemBottomSheetPickerUIFragment(items, "Chọn loại hỗ trợ",
                    (item, position) -> {
                        tvSupportType.setText(item.getText());
                        supportTypeResponse = mListSupportType.get(position);
                    }, 0);
            pickerUIReason.show(mActivity.getSupportFragmentManager(), pickerUIReason.getTag());
        } else {
            pickerUIReason.setData(items, 0);
            if (!pickerUIReason.isShow) {
                pickerUIReason.show(mActivity.getSupportFragmentManager(), pickerUIReason.getTag());
            }


        }
    }
}
