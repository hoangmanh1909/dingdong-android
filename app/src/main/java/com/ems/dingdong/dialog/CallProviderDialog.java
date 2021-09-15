package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.core.base.BaseActivity;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.CallProviderCallBack;
import com.ems.dingdong.model.CallProvider;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CallProviderDialog extends Dialog {
    private final BaseActivity mActivity;
    private final CallProviderCallBack mDelegate;
    ArrayList<Item> items = new ArrayList<>();
    private List<CallProvider> mCallProvider;
    private ItemBottomSheetPickerUIFragment pickerUIProvider;
    private Item mItem;
    private CallProvider mItemCallProvider;
    @BindView(R.id.tv_provider)
    FormItemTextView tv_provider;

    public CallProviderDialog(@NonNull Context context, CallProviderCallBack mDelegate) {//, List<CallProvider> callProviders
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = mDelegate;

        View view = View.inflate(getContext(), R.layout.dialog_call_provider, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mActivity = (BaseActivity) context;
        //mListCallProvider = callProviders;
        items.add(new Item("", "CTEL"));
        items.add(new Item("", "VHT"));
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_provider, R.id.tv_select_provider, R.id.tv_cancel_provider})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.tv_select_provider:
                if (TextUtils.isEmpty(tv_provider.getText())){
                    Toast.showToast(tv_provider.getContext(), "Xin vui lòng chọn nhà cung cấp.");
                }else {
                    if (mDelegate != null){
                        mDelegate.onCallProviderOptionResponse(mItem, mItemCallProvider);
                        dismiss();
                    }
                }
                break;

            case R.id.tv_provider:
                showUICallProvider();
                break;

            case R.id.tv_cancel_provider:
                dismiss();
                break;
        }
    }

    private void showUICallProvider() {
        if (pickerUIProvider == null) {
            pickerUIProvider = new ItemBottomSheetPickerUIFragment(items, mActivity.getResources().getString(R.string.select_provider),
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            tv_provider.setText(item.getText());
                            mItem = item;
                            try {
                                mItemCallProvider = mCallProvider.get(position);
                            }catch (NullPointerException nullPointerException){}

                        }
                    }, 0);
            pickerUIProvider.show(mActivity.getSupportFragmentManager(), pickerUIProvider.getTag());
        } else {
            pickerUIProvider.setData(items, 0);
            if (!pickerUIProvider.isShow) {
                pickerUIProvider.show(mActivity.getSupportFragmentManager(), pickerUIProvider.getTag());
            }
        }
    }
}
