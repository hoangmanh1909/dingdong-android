package com.vinatti.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.core.base.BaseActivity;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.BaoPhatBangKeFailCallback;
import com.vinatti.dingdong.callback.BaoPhatOfflineFailCallback;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.Item;
import com.vinatti.dingdong.model.ReasonInfo;
import com.vinatti.dingdong.model.SolutionInfo;
import com.vinatti.dingdong.model.SolutionResult;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.RealmUtils;
import com.vinatti.dingdong.utiles.Toast;
import com.vinatti.dingdong.views.form.FormItemEditText;
import com.vinatti.dingdong.views.form.FormItemTextView;
import com.vinatti.dingdong.views.picker.ItemBottomSheetPickerUIFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;


public class BaoPhatOfflineFailDialog extends Dialog {

    private final BaoPhatOfflineFailCallback mDelegate;
    private final List<ReasonInfo> mListReason;
    private final BaseActivity mActivity;
    @BindView(R.id.tv_reason)
    FormItemTextView tvReason;
    @BindView(R.id.edt_reason)
    FormItemEditText edtReason;
    @BindView(R.id.tv_solution)
    FormItemTextView tvSolution;
  /*  @BindView(R.id.ll_sign)
    View llSign;*/
    private ItemBottomSheetPickerUIFragment pickerUIReason;
    private ReasonInfo mReasonInfo;
    private List<SolutionInfo> mListSolution;
    private SolutionInfo mSolutionInfo;
    private ItemBottomSheetPickerUIFragment pickerUISolution;

    public BaoPhatOfflineFailDialog(Context context, List<ReasonInfo> listReason, BaoPhatOfflineFailCallback reasonCallback) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        this.mListReason = listReason;
        mActivity = (BaseActivity) context;
        View view = View.inflate(getContext(), R.layout.dialog_bao_phat_bang_ke_fail, null);
        setContentView(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void show() {
        super.show();
    }


    @OnClick({R.id.tv_reason, R.id.tv_solution, R.id.tv_update, R.id.tv_close, R.id.btn_clear_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_reason:
                showUIReason();
                break;
            case R.id.tv_solution:
                if (mListSolution != null) {
                    showUISolution();
                }
                break;
            case R.id.tv_update:
                if (TextUtils.isEmpty(tvReason.getText())) {
                    Toast.showToast(tvReason.getContext(), "Xin vui lòng chọn lý do");
                    return;
                }
                if (TextUtils.isEmpty(tvSolution.getText())) {
                    Toast.showToast(tvSolution.getContext(), "Bạn chưa chọn phương án xử lý");
                    return;
                }
                String base64 = "";
                /*if (mReasonInfo.getCode().equals("13")) {
                    if (signature.isEmpty()) {
                        Toast.showToast(tvSolution.getContext(), "Khách hàng chưa ký từ chối nhận");
                        return;
                    } else {
                        Bitmap bitmap = signature.getSignatureBitmap();
                        for (int quality = 80; quality >= 10; quality -= 10) {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
                            final byte[] dataF = baos.toByteArray();
                            if (dataF.length <= 500000) {
                                base64 = Base64.encodeToString(dataF, Base64.DEFAULT);//"data:image/jpeg;base64," +
                            }
                        }
                    }
                }*/


                if (mDelegate != null) {
                    mDelegate.onResponse(mReasonInfo, mSolutionInfo, edtReason.getText(), base64);
                    dismiss();
                }

                break;
            case R.id.tv_close:
                dismiss();
                break;
            case R.id.btn_clear_sign:
                //signature.clear();
                break;
        }
    }

    private void showUIReason() {
        ArrayList<Item> items = new ArrayList<>();
        for (ReasonInfo item : mListReason) {
            items.add(new Item(item.getCode(), item.getName()));
        }
        if (pickerUIReason == null) {
            pickerUIReason = new ItemBottomSheetPickerUIFragment(items, "Chọn lý do",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            if(mReasonInfo==null || mReasonInfo.getCode().equals(mListReason.get(position).getCode())) {
                                tvReason.setText(item.getText());
                                mReasonInfo = mListReason.get(position);
                                mListSolution = null;
                                tvSolution.setText("");
                                mSolutionInfo = null;
                                showUISolution();
                                if (mReasonInfo.getCode().equals("99") || mReasonInfo.getCode().equals("13")) {
                                    edtReason.setVisibility(View.VISIBLE);
                                } else {
                                    edtReason.setVisibility(View.GONE);
                                }
                            }
                           /* if (mReasonInfo.getCode().equals("13")) {
                                llSign.setVisibility(View.VISIBLE);
                            } else {
                                llSign.setVisibility(View.GONE);
                            }*/


                        }
                    }, 0);
            pickerUIReason.show(mActivity.getSupportFragmentManager(), pickerUIReason.getTag());
        } else {
            pickerUIReason.setData(items, 0);
            if (!pickerUIReason.isShow) {
                pickerUIReason.show(mActivity.getSupportFragmentManager(), pickerUIReason.getTag());
            }


        }
    }

    private void showUISolution() {
        ArrayList<Item> items = new ArrayList<>();
        mListSolution = RealmUtils.getSolutionByReason(mReasonInfo.getCode());
        for (SolutionInfo item : mListSolution) {
            items.add(new Item(item.getCode(), item.getName()));
        }
        if (pickerUISolution == null) {
            pickerUISolution = new ItemBottomSheetPickerUIFragment(items, "Chọn giải pháp",
                    new ItemBottomSheetPickerUIFragment.PickerUiListener() {
                        @Override
                        public void onChooseClick(Item item, int position) {
                            tvSolution.setText(item.getText());
                            mSolutionInfo = mListSolution.get(position);

                        }
                    }, 0);
            pickerUISolution.show(mActivity.getSupportFragmentManager(), pickerUISolution.getTag());
        } else {
            pickerUISolution.setData(items, 0);
            if (!pickerUISolution.isShow) {
                pickerUISolution.show(mActivity.getSupportFragmentManager(), pickerUISolution.getTag());
            }


        }
    }

}
