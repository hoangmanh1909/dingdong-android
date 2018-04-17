package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.list;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.core.widget.BaseViewHolder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.BaoPhatBangKeFailCallback;
import com.vinatti.dingdong.callback.BaoPhatbangKeConfirmCallback;
import com.vinatti.dingdong.callback.BaoPhatbangKeSearchCallback;
import com.vinatti.dingdong.dialog.BaoPhatBangKeConfirmDialog;
import com.vinatti.dingdong.dialog.BaoPhatBangKeFailDialog;
import com.vinatti.dingdong.dialog.BaoPhatBangKeSearchDialog;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.ReasonInfo;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.utiles.SharedPref;
import com.vinatti.dingdong.utiles.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * The CommonObject Fragment
 */
public class ListBaoPhatBangKeFragment extends ViewFragment<ListBaoPhatBangKeContract.Presenter> implements ListBaoPhatBangKeContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_view)
    ImageView imgView;

    ArrayList<CommonObject> mList;
    private ListBaoPhatBangKeAdapter mAdapter;
    private UserInfo mUserInfo;
    private String mDate;
    private String mOrder;
    private String mRoute;
    private Calendar mCalendar;
    private ArrayList<ReasonInfo> mListReason;

    public static ListBaoPhatBangKeFragment getInstance() {
        return new ListBaoPhatBangKeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bao_phat_bang_ke;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        showDialog();
        mList = new ArrayList<>();
        mCalendar = Calendar.getInstance();
        mAdapter = new ListBaoPhatBangKeAdapter(getActivity(), mPresenter.getType(), mList) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.showDetailView(mList.get(position));
                    }
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(mAdapter);
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!TextUtils.isEmpty(userJson)) {
            mUserInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (mPresenter.getType() == 3) {
            tvTitle.setText("Danh sách vận đơn");
        }
        mPresenter.getReasons();
    }

    private void showDialog() {
        if (mPresenter.getType() == 3) {

            new BaoPhatBangKeSearchDialog(getActivity(), mCalendar, new BaoPhatbangKeSearchCallback() {
                @Override
                public void onResponse(String fromDate, String order, String route) {
                    mDate = fromDate;
                    mCalendar.setTime(DateTimeUtils.convertStringToDate(fromDate, DateTimeUtils.SIMPLE_DATE_FORMAT5));
                    mOrder = order;
                    mRoute = route;
                    mPresenter.searchDeliveryPostman(mUserInfo.getiD(), fromDate, order, route);

                }
            }).show();
        }
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        if (mPresenter.getType() == 3 && !TextUtils.isEmpty(mDate) && mUserInfo != null) {
            mPresenter.searchDeliveryPostman(mUserInfo.getiD(), mDate, mOrder, mRoute);
        }
    }

    @OnClick({R.id.img_back, R.id.img_view, R.id.btn_confirm_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_view:
                showDialog();
                break;
            case R.id.btn_confirm_all:
                final List<CommonObject> commonObjects = mAdapter.getItemsSelected();
                if (commonObjects.isEmpty()) {
                    Toast.showToast(getActivity(), "Chưa chọn giá trị nào để xác nhận");
                    return;
                }
                new BaoPhatBangKeConfirmDialog(getActivity(), new BaoPhatbangKeConfirmCallback() {
                    @Override
                    public void onResponse(int deliveryType) {
                        if (deliveryType == 2) {
                            //next view
                            for (CommonObject item : commonObjects) {
                                item.setDeliveryType("2");

                            }
                            mPresenter.nextReceverPerson(commonObjects);
                        } else {
                            //show dialog
                            if (mListReason != null) {
                                new BaoPhatBangKeFailDialog(getActivity(), mListReason, new BaoPhatBangKeFailCallback() {
                                    @Override
                                    public void onResponse(String reason, String solution, String note, String sign) {
                                        mPresenter.submitToPNS(commonObjects, reason, solution, note, sign);
                                    }
                                }).show();
                            } else {
                                Toast.showToast(getActivity(), "Đang lấy dữ liệu");
                            }

                        }
                    }
                }).show();
                break;
        }
    }

    @Override
    public void showResponseSuccess(ArrayList<CommonObject> list) {
        mList = list;
        mAdapter.refresh(list);
    }

    @Override
    public void showError(String message) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                .setConfirmText("OK")
                .setTitleText("Thông báo")
                .setContentText(message)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos) {
        mListReason = reasonInfos;
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.showToast(getActivity(), message);
    }


}
