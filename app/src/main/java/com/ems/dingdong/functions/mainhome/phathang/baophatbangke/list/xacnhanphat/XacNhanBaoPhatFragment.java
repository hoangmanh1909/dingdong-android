package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.log.Logger;
import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.dialog.ConfirmDialog;
import com.ems.dingdong.dialog.PickerDialog;
import com.ems.dingdong.dialog.SignDialog;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CustomDOP1;
import com.ems.dingdong.model.DeliveryListRelease;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.InfoVerify;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.BitmapUtils;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.MediaUltis;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.StringUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class XacNhanBaoPhatFragment extends ViewFragment<XacNhanBaoPhatContract.Presenter> implements XacNhanBaoPhatContract.View/*, DialogAddItemPartial.ExampleDialogListener*/ {

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.tv_title)
    CustomBoldTextView tv_title;
    @BindView(R.id.img_send)
    ImageView img_send;
    @BindView(R.id.tv_quantity)
    CustomBoldTextView tv_quantity;
    @BindView(R.id.tv_total_amount)
    CustomBoldTextView tv_total_amount;
    /*@BindView(R.id.radio_group)
    RadioGroup radio_group;*/
    @BindView(R.id.rad_success)
    TextView rad_success;
    @BindView(R.id.rad_change_route)
    TextView rad_change_route;
    @BindView(R.id.rad_partial)
    TextView rad_partial;
    @BindView(R.id.rad_fail)
    TextView rad_fail;
    @BindView(R.id.ll_confirm_fail)
    LinearLayout ll_confirm_fail;
    @BindView(R.id.ll_change_route)
    LinearLayout ll_change_route;
    @BindView(R.id.ll_partial)
    ConstraintLayout llPartial;
    @BindView(R.id.tv_reason)
    FormItemTextView tv_reason;
    @BindView(R.id.tv_solution)
    FormItemTextView tv_solution;
    @BindView(R.id.tv_Description)
    TextInputEditText tv_Description;
    @BindView(R.id.tv_route)
    FormItemTextView tv_route;
    @BindView(R.id.tv_postman)
    FormItemTextView tv_postman;
    @BindView(R.id.btn_sign)
    CustomTextView btn_sign;
    @BindView(R.id.ll_signed)
    LinearLayout llSigned;
    @BindView(R.id.img_sign)
    ImageView imgSign;
    @BindView(R.id.tv_total_fee)
    CustomBoldTextView tvTotalFee;
    @BindView(R.id.tv_total)
    CustomBoldTextView tvTotal;
    @BindView(R.id.edt_receiver_name)
    TextInputEditText edtReceiverName;
    @BindView(R.id.edt_GTTT)
    TextInputEditText tvGTTT;
    @BindView(R.id.edt_relationship)
    CustomTextView edtRelationship;
    @BindView(R.id.tv_receiver_name)
    CustomTextView tvRealReceiverName;
    @BindView(R.id.tv_address_user)
    CustomTextView tvAddressUser;
    @BindView(R.id.tv_providers)
    CustomTextView tvProviders;
    @BindView(R.id.rl_relationship)
    RelativeLayout rlRelationship;
    @BindView(R.id.layout_real_receiver_name)
    LinearLayout linearLayoutName;
    @BindView(R.id.ll_other_relationship)
    LinearLayout llOtherRelationship;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.edt_other_relationship)
    FormItemEditText edtOtherRelationship;
    ///
    /*@BindView(R.id.rl_image_capture_avatar)
    RelativeLayout rlImageCaptureAvatar;*/
    @BindView(R.id.recycler_image_verify_avatar)
    RecyclerView recyclerImageVerifyAvatar;
    @BindView(R.id.recycler_image)
    RecyclerView recyclerViewImage;
    @BindView(R.id.recycler_image_verify)
    RecyclerView recyclerViewImageVerify;
    @BindView(R.id.recycler_image_other)
    RecyclerView recyclerImageOther;
    @BindView(R.id.recycler_image_partial_release)
    RecyclerView recyclerImagePartialRelease;
    @BindView(R.id.recycler_image_partial_refund)
    RecyclerView recyclerImagePartialRefund;
    @BindView(R.id.rb_verify_info)
    CheckBox rbVerifyInfo;
    @BindView(R.id.rb_verify_Image)
    CheckBox rbVerifyImage;
    @BindView(R.id.ll_verify_image)
    LinearLayout llVerifyImage;
    @BindView(R.id.ll_verify_info)
    LinearLayout llVerifyInfo;
    @BindView(R.id.ll_verify)
    LinearLayout llVerify;
    @BindView(R.id.ll_capture_verify)
    LinearLayout llCaptureVerify;
    @BindView(R.id.edt_date_of_birth)
    CustomTextView edtDateOfBirth;
    @BindView(R.id.edt_GTTT_date_accepted)
    CustomTextView edtGTTTDateAccepted;
    @BindView(R.id.edt_GTTT_located_accepted)
    TextInputEditText edtGTTTLocatedAccepted;
    @BindView(R.id.edt_user_address)
    TextInputEditText edtUserAddress;
    @BindView(R.id.edt_content_partial)
    EditText edtContentPartial;
    @BindView(R.id.edt_price_partial_release)
    EditText edtPricePartialRelease;
    @BindView(R.id.edt_price_partial_refund)
    EditText edtPricePartialRefund;
    @BindView(R.id.img_camera_partial_release)
    ImageView ivCameraPartialRelease;
    @BindView(R.id.img_camera_partial_refund)
    ImageView ivCameraPartialRefund;
    @BindView(R.id.img_add_item_release)
    ImageView imgAddItemRelease;
    @BindView(R.id.img_add_item_refund)
    ImageView imgAddItemRefund;
    @BindView(R.id.recycler_item_partial_release)
    RecyclerView recyclerItemPartialRelease;
    @BindView(R.id.recycler_item_partial_refund)
    RecyclerView recyclerItemPartialReturn;

    private Calendar calDateOfBirth = Calendar.getInstance();
    private Calendar calDateAccepted = Calendar.getInstance();
    private XacNhanBaoPhatAdapter adapter;
    private XacNhanBaoPhatPartialReleaseAdapter xacNhanBaoPhatPartialReleaseAdapter;
    private XacNhanBaoPhatPartialReturnAdapter xacNhanBaoPhatPartialReturnAdapter;

    private String mSign = "";

    private List<DeliveryPostman> mBaoPhatBangke;
    private int mDeliveryType = 2;
    private int mPaymentType = 1;

    private ArrayList<ReasonInfo> mListReason;
    private ReasonInfo mReasonInfo;

    private ArrayList<SolutionInfo> mListSolution;
    private SolutionInfo mSolutionInfo;

    private ArrayList<RouteInfo> mListRoute;
    private ItemBottomSheetPickerUIFragment pickerUIRoute;
    private RouteInfo mRouteInfo;
    private RouteInfo mCurrentRouteInfo;

    private ArrayList<UserInfo> mListPostman;
    private ItemBottomSheetPickerUIFragment pickerUIPostman;
    private UserInfo mPostmanInfo;

    private boolean mClickSolution = false;
    private boolean mReloadSolution = false;
    private int mDeliverySuccess = 0;
    private int mDeliveryError = 0;
    private long totalAmount = 0;
    private long totalFee = 0;
    private String mFile = "";
    private String mFileVerify = "";
    private String mFileAvatar = "";
    private String mFileOther = "";
    private String mFileRelease = "";
    private String mFileRefund = "";
    private boolean isCapture = false;
    private boolean isCaptureVerify = false;
    private boolean isCaptureAvatar = false;
    private boolean isCaptureOther = false;
    private boolean isCaptureRelease = false;
    private boolean isCaptureRefund = false;
    //private int authenType = -2;
    private int authenType;
    private List<Item> listImages;
    private List<Item> listImagesAvatar;//
    private List<Item> listImageVerify;
    private List<Item> listImageOther;
    private List<Item> listImageRelease;
    private List<Item> listImageRefund;
    private List<DeliveryListRelease> listItemPartialRelease = new ArrayList<>();
    ///private List<DeliveryListReturn> listItemPartialReturn;
    private List<DeliveryListRelease> listItemPartialReturn;
    private ImageCaptureAdapter imageAvatarAdapter;
    private ImageCaptureAdapter imageAdapter;
    private ImageCaptureAdapter imageVerifyAdapter;
    private ImageCaptureAdapter imageOtherAdapter;
    private ImageCaptureAdapter imageReleaseAdapter;
    private ImageCaptureAdapter imageRefundAdapter;

    private DialogAddItemPartial mDialogAddItemPartial;

    private UserInfo userInfo;
    private UploadSingleResult uploadSingleResult;
    //private ListReceiverName listReceiverName;
    private List<String> listReceiverName;
    private String receiverName = "";
    private boolean checkInfoClick = false;
    private boolean checkImageClick = false;

    String nameProductAdded;
    int quantityProductAdded;
    Long weightProductAdded;
    Long priceProductAdded;
    private String dop1 = "";
    private int quantityRelease;
    private int quantityReturn;


    public XacNhanBaoPhatFragment() {
    }


    public static XacNhanBaoPhatFragment getInstance() {
        return new XacNhanBaoPhatFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xac_nhan_bao_phat;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initLayout() {
        super.initLayout();

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }

        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setOnTouchListener((v, event) -> {
            v.requestFocusFromTouch();
            return false;
        });
        tvRealReceiverName.setText(StringUtils.fromHtml("Tên người nhận: "));
        tvAddressUser.setText(StringUtils.fromHtml("Địa chỉ người sử dụng: " + "<font color=\"red\">*</font>"));
        tvProviders.setText(StringUtils.fromHtml("Nơi cấp: " + "<font color=\"red\">*</font>"));
        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!routeJson.isEmpty()) {
            mCurrentRouteInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
        }

        ll_change_route.setVisibility(LinearLayout.GONE);
        ll_confirm_fail.setVisibility(LinearLayout.GONE);

        /*radio_group.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rad_fail) {
                mDeliveryType = 1;
                ll_confirm_fail.setVisibility(LinearLayout.VISIBLE);
                ll_change_route.setVisibility(LinearLayout.GONE);
                linearLayoutName.setVisibility(View.GONE);
                llVerify.setVisibility(View.GONE);
            } else if (checkedId == R.id.rad_success) {
                mDeliveryType = 2;
                ll_change_route.setVisibility(LinearLayout.GONE);
                ll_confirm_fail.setVisibility(LinearLayout.GONE);
                linearLayoutName.setVisibility(View.VISIBLE);
                llVerify.setVisibility(View.VISIBLE);
            } else {
                ll_confirm_fail.setVisibility(LinearLayout.GONE);
                ll_change_route.setVisibility(LinearLayout.VISIBLE);
                linearLayoutName.setVisibility(View.GONE);
                llVerify.setVisibility(View.GONE);
                mDeliveryType = 3;
            }
        });*/

        mBaoPhatBangke = mPresenter.getBaoPhatBangke();
        //listItemPartialRelease = mPresenter.getListProduct();
        //listItemPartialRelease = mBaoPhatBangke.get(0).getListProducts();

        adapter = new XacNhanBaoPhatAdapter(getViewContext(), mBaoPhatBangke) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
                    mBaoPhatBangke.get(position).setSelected(!mBaoPhatBangke.get(position).isSelected());
                    if (getItemsSelected().size() == 1 || checkSameAddress()) {
                        edtReceiverName.setText(getItemsSelected().get(0).getReciverName());
                        tvGTTT.setText("");
                    } else {
                        edtReceiverName.setText("");
                    }

                    checkVerify();
                    adapter.notifyDataSetChanged();
                    updateTotalPackage();
                });

                // add list partial
                listItemPartialRelease.addAll(mBaoPhatBangke.get(position).getListProducts());
                if (!listItemPartialRelease.isEmpty()) {
                    imgAddItemRelease.setVisibility(View.GONE);
                    imgAddItemRefund.setVisibility(View.GONE);
                } else {
                    imgAddItemRelease.setVisibility(View.VISIBLE);
                    imgAddItemRefund.setVisibility(View.VISIBLE);
                }
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recycler);
        recycler.setAdapter(adapter);

        if (getItemSelected().size() == 1 || checkSameAddress()) {
            edtReceiverName.setText(getItemSelected().get(0).getReciverName());
            tvGTTT.setText("");
        }

        updateTotalPackage();
        mPresenter.getReasons();
        mPresenter.getRouteByPoCode(userInfo.getUnitCode());
        listImagesAvatar = new ArrayList<>();//
        listImages = new ArrayList<>();
        listImageVerify = new ArrayList<>();
        listImageOther = new ArrayList<>();
        listImageRelease = new ArrayList<>();
        listImageRefund = new ArrayList<>();
        listItemPartialRelease = new ArrayList<>();
        listItemPartialReturn = listItemPartialRelease;
        ///listItemPartialReturn = new ArrayList<>();
        ///
        /*for (int i = 1; i < 4; i++) {
            //listItemPartialRelease.add(new ItemPartialRelease(i + ". " + "Áo xanh ", "450(g) ", "đơn giá: 20000 ", 3));
            listItemPartialRelease.add(new DeliveryListRelease(12l, "áo đỏ", 123, "unitName",
                    423l, 20000l, 0l, 12345l, "1234", "3211"));
        }*/

        /*listItemPartialRelease.addAll(mBaoPhatBangke.get(0).getListProducts());
        if (!listItemPartialRelease.isEmpty()) {
            imgAddItemRelease.setVisibility(View.GONE);
            imgAddItemRefund.setVisibility(View.GONE);
        } else {
            imgAddItemRelease.setVisibility(View.VISIBLE);
            imgAddItemRefund.setVisibility(View.VISIBLE);
        }*/


        ///
        imageAvatarAdapter = new ImageCaptureAdapter(getViewContext(), listImagesAvatar) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);

                holder.ivDelete.setOnClickListener(view -> {
                    listImagesAvatar.remove(position);
                    imageAvatarAdapter.notifyItemRemoved(position);
                    imageAvatarAdapter.notifyItemRangeChanged(position, listImagesAvatar.size());
                });
            }
        };
        imageAdapter = new ImageCaptureAdapter(getViewContext(), listImages) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);

                holder.ivDelete.setOnClickListener(view -> {
                    listImages.remove(position);
                    imageAdapter.notifyItemRemoved(position);
                    imageAdapter.notifyItemRangeChanged(position, listImages.size());
                });
            }
        };
        imageVerifyAdapter = new ImageCaptureAdapter(getViewContext(), listImageVerify) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);

                holder.ivDelete.setOnClickListener(view -> {
                    listImageVerify.remove(position);
                    imageVerifyAdapter.notifyItemRemoved(position);
                    imageVerifyAdapter.notifyItemRangeChanged(position, listImageVerify.size());
                });
            }
        };
        imageOtherAdapter = new ImageCaptureAdapter(getViewContext(), listImageOther) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);

                holder.ivDelete.setOnClickListener(view -> {
                    listImageOther.remove(position);
                    imageOtherAdapter.notifyItemRemoved(position);
                    imageOtherAdapter.notifyItemRangeChanged(position, listImageOther.size());
                });
            }
        };
        imageReleaseAdapter = new ImageCaptureAdapter(getViewContext(), listImageRelease) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);

                holder.ivDelete.setOnClickListener(view -> {
                    listImageRelease.remove(position);
                    imageReleaseAdapter.notifyItemRemoved(position);
                    imageReleaseAdapter.notifyItemRangeChanged(position, listImageRelease.size());
                });
            }
        };
        imageRefundAdapter = new ImageCaptureAdapter(getViewContext(), listImageRefund) {
            @Override
            public void onBindViewHolder(@NonNull HolderView holder, int position) {
                super.onBindViewHolder(holder, position);

                holder.ivDelete.setOnClickListener(view -> {
                    listImageRefund.remove(position);
                    imageRefundAdapter.notifyItemRemoved(position);
                    imageRefundAdapter.notifyItemRangeChanged(position, listImageRefund.size());
                });
            }
        };

        xacNhanBaoPhatPartialReleaseAdapter = new XacNhanBaoPhatPartialReleaseAdapter(listItemPartialRelease, getViewContext());
        xacNhanBaoPhatPartialReturnAdapter = new XacNhanBaoPhatPartialReturnAdapter(listItemPartialReturn, getViewContext());
        RecyclerUtils.setupHorizontalRecyclerView(getViewContext(), recyclerImageVerifyAvatar);
        RecyclerUtils.setupHorizontalRecyclerView(getViewContext(), recyclerViewImage);
        RecyclerUtils.setupHorizontalRecyclerView(getViewContext(), recyclerViewImageVerify);
        RecyclerUtils.setupHorizontalRecyclerView(getViewContext(), recyclerImageOther);
        RecyclerUtils.setupHorizontalRecyclerView(getViewContext(), recyclerImagePartialRelease);
        RecyclerUtils.setupHorizontalRecyclerView(getViewContext(), recyclerImagePartialRefund);
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recyclerItemPartialRelease);
        RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recyclerItemPartialReturn);
        recyclerImageVerifyAvatar.setAdapter(imageAvatarAdapter);//
        recyclerViewImage.setAdapter(imageAdapter);
        recyclerViewImageVerify.setAdapter(imageVerifyAdapter);
        recyclerImageOther.setAdapter(imageOtherAdapter);
        recyclerImagePartialRelease.setAdapter(imageReleaseAdapter);
        recyclerImagePartialRefund.setAdapter(imageRefundAdapter);
        recyclerItemPartialRelease.setAdapter(xacNhanBaoPhatPartialReleaseAdapter);
        recyclerItemPartialReturn.setAdapter(xacNhanBaoPhatPartialReturnAdapter);

        /*rbVerifyInfo.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                llVerifyInfo.setVisibility(View.VISIBLE);
                //llCaptureVerify.setVisibility(View.VISIBLE);
            } else {
                llVerifyInfo.setVisibility(View.GONE);
                //llCaptureVerify.setVisibility(View.GONE);
            }
        });

        rbVerifyImage.setOnCheckedChangeListener((v, b) -> {
            if (b){
                llVerifyImage.setVisibility(View.VISIBLE);
            }else {
                llVerifyImage.setVisibility(View.GONE);
            }
        });*/


        verifyInfo();
        verifyImage();

        edtDateOfBirth.setText(DateTimeUtils
                .convertDateToString(calDateOfBirth.getTime(),
                        DateTimeUtils.SIMPLE_DATE_FORMAT));
        edtGTTTDateAccepted.setText(DateTimeUtils
                .convertDateToString(calDateOfBirth.getTime(),
                        DateTimeUtils.SIMPLE_DATE_FORMAT));
        checkVerify();

        //scroll edittext content partial
        edtContentPartial.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_SCROLL:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                    case MotionEvent.ACTION_BUTTON_PRESS:
                        InputMethodManager imm = (InputMethodManager) getViewContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edtContentPartial, InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            }
        });
        //edtPricePartialRelease.setText(String.format(" %s đ", NumberUtils.formatPriceNumber(totalAmount)));

        llPartial.setVisibility(View.GONE);
    }

    public void removeAt(String type, int position) {
        switch (type) {
            case "AVARTAR": {
                listImagesAvatar.remove(position);
                imageAvatarAdapter.notifyItemRemoved(position);
                imageAvatarAdapter.notifyItemRangeChanged(position, listImagesAvatar.size());
            }
        }

    }

    @OnClick({R.id.img_back, R.id.img_send, R.id.tv_reason, R.id.tv_solution, R.id.tv_route,
            R.id.tv_postman, R.id.btn_sign, R.id.rl_relationship, R.id.rl_image_capture,
            R.id.edt_date_of_birth, R.id.edt_GTTT_date_accepted, R.id.rl_image_capture_verify, R.id.rl_image_capture_avatar, R.id.rl_image_other,
            R.id.rad_success, R.id.rad_fail, R.id.rad_change_route, R.id.rad_partial, R.id.img_add_item_release, R.id.img_add_item_refund,
            R.id.img_camera_partial_release, R.id.img_camera_partial_refund})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rad_success:
                mDeliveryType = 2;
                Log.d("123123", "mDeliveryType: " + mDeliveryType);
                ll_change_route.setVisibility(LinearLayout.GONE);
                ll_confirm_fail.setVisibility(LinearLayout.GONE);
                linearLayoutName.setVisibility(View.VISIBLE);
                llVerify.setVisibility(View.VISIBLE);
                llPartial.setVisibility(View.GONE);
                Log.d("123123", "dop1: " + dop1);
                rad_success.setBackgroundResource(R.drawable.bg_form_success);
                rad_fail.setBackgroundResource(R.color.color_rad_fail);
                rad_change_route.setBackgroundResource(R.color.color_rad_change_route);
                rad_partial.setBackgroundResource(R.color.bg_yellow_primary);
                rad_success.setTextColor(getResources().getColor(R.color.color_yellow));
                rad_fail.setTextColor(getResources().getColor(R.color.white));
                rad_change_route.setTextColor(getResources().getColor(R.color.white));
                rad_partial.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.rad_fail:
                mDeliveryType = 1;
                Log.d("123123", "mDeliveryType: " + mDeliveryType);
                ll_confirm_fail.setVisibility(LinearLayout.VISIBLE);
                ll_change_route.setVisibility(LinearLayout.GONE);
                linearLayoutName.setVisibility(View.GONE);
                llVerify.setVisibility(View.GONE);
                llPartial.setVisibility(View.GONE);
                rad_success.setBackgroundResource(R.color.color_rad_success);
                rad_fail.setBackgroundResource(R.drawable.bg_form_fail);
                rad_change_route.setBackgroundResource(R.color.color_rad_change_route);
                rad_partial.setBackgroundResource(R.color.bg_yellow_primary);
                rad_success.setTextColor(getResources().getColor(R.color.white));
                rad_fail.setTextColor(getResources().getColor(R.color.color_yellow));
                rad_change_route.setTextColor(getResources().getColor(R.color.white));
                rad_partial.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.rad_change_route:
                mDeliveryType = 3;
                Log.d("123123", "mDeliveryType: " + mDeliveryType);
                ll_confirm_fail.setVisibility(LinearLayout.GONE);
                ll_change_route.setVisibility(LinearLayout.VISIBLE);
                linearLayoutName.setVisibility(View.GONE);
                llVerify.setVisibility(View.GONE);
                llPartial.setVisibility(View.GONE);
                rad_fail.setBackgroundResource(R.color.color_rad_fail);
                rad_success.setBackgroundResource(R.color.color_rad_success);
                rad_change_route.setBackgroundResource(R.drawable.bg_form_change_route);
                rad_partial.setBackgroundResource(R.color.bg_yellow_primary);
                rad_success.setTextColor(getResources().getColor(R.color.white));
                rad_fail.setTextColor(getResources().getColor(R.color.white));
                rad_change_route.setTextColor(getResources().getColor(R.color.color_yellow));
                rad_partial.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.rad_partial:
                mDeliveryType = 4;
                if (dop1.equals("partialRelease")) {

                }
                Log.d("123123", "mDeliveryType: " + mDeliveryType);
                ll_confirm_fail.setVisibility(LinearLayout.GONE);
                ll_change_route.setVisibility(LinearLayout.GONE);
                linearLayoutName.setVisibility(View.GONE);
                llVerify.setVisibility(View.GONE);
                llPartial.setVisibility(View.VISIBLE);
                rad_fail.setBackgroundResource(R.color.color_rad_fail);
                rad_success.setBackgroundResource(R.color.color_rad_success);
                rad_change_route.setBackgroundResource(R.color.color_rad_change_route);
                rad_partial.setBackgroundResource(R.drawable.bg_form_partial);
                rad_success.setTextColor(getResources().getColor(R.color.white));
                rad_fail.setTextColor(getResources().getColor(R.color.white));
                rad_change_route.setTextColor(getResources().getColor(R.color.white));
                rad_partial.setTextColor(getResources().getColor(R.color.color_yellow));
                break;
            case R.id.img_back:
                mPresenter.back();
                break;
            case R.id.img_send:
                if (checkInfoClick) {
                    if (TextUtils.isEmpty(edtGTTTDateAccepted.getText()) || TextUtils.isEmpty(edtGTTTLocatedAccepted.getText()) ||
                            TextUtils.isEmpty(edtDateOfBirth.getText()) || TextUtils.isEmpty(tvGTTT.getText()) || TextUtils.isEmpty(edtUserAddress.getText())) {
                        showErrorToast(getViewContext().getString(R.string.please_enter_full_authentication_information));
                    } else {
                        submit();
                    }
                } else {
                    submit();
                }
                Log.d("123123", "click");

                /*if (checkImageClick) {
                    if (mFileAvatar.isEmpty() || mFileVerify.isEmpty() || mFileOther.isEmpty()) {
                        showSuccessToast(getViewContext().getString(R.string.you_must_take_verify_photos_or_enter_enough_verification_info));
                    } else {
                        submit();
                    }
                } else {
                    submit();
                }*/
                //submit();
                break;
            case R.id.tv_reason:
                showUIReason();
                break;
            case R.id.tv_solution:
                mClickSolution = true;
                if (mReloadSolution) {
                    mReloadSolution = false;
                    loadSolution();
                } else {
                    showUISolution();
                }
                break;
            case R.id.tv_route:
                showUIRoute();
                break;
            case R.id.tv_postman:
                showUIPostman();
                break;
            case R.id.btn_sign:
                new SignDialog(getActivity(), (sign, bitmap) -> {
                    mSign = sign;
                    imgSign.setImageBitmap(bitmap);
                    if (bitmap != null) {
                        llSigned.setVisibility(View.VISIBLE);
                    }
                }).show();
                break;
            ///file_avatar.jpg
            //
            //file_selfie_avatar.jpg
            case R.id.rl_image_capture_avatar:
                if (imageAvatarAdapter.getListFilter().size() < 1) {
                    isCaptureAvatar = true;
                    isCaptureVerify = false;
                    isCapture = false;
                    isCaptureOther = false;
                    isCaptureRelease = false;
                    isCaptureRefund = false;
                    MediaUltis.captureImage(this);
                } else {
                    showErrorToast(getString(R.string.do_not_allow_take_over_one_photos));
                }
                break;

            case R.id.rl_image_other:
                if (imageOtherAdapter.getListFilter().size() < 5) {
                    isCaptureAvatar = false;
                    isCaptureVerify = false;
                    isCapture = false;
                    isCaptureOther = true;
                    isCaptureRelease = false;
                    isCaptureRefund = false;
                    MediaUltis.captureImage(this);
                } else {
                    showErrorToast(getString(R.string.do_not_allow_take_over_five_photos));
                }
                break;

            case R.id.rl_image_capture:
                if (imageAdapter.getListFilter().size() < 3) {
                    isCaptureAvatar = false;
                    isCaptureVerify = false;
                    isCapture = true;
                    isCaptureOther = false;
                    isCaptureRelease = false;
                    isCaptureRefund = false;
                    MediaUltis.captureImage(this);
                } else {
                    showErrorToast(getViewContext().getString(R.string.do_not_allow_take_over_three_photos));
                }
                break;

            case R.id.rl_image_capture_verify:
                if (imageVerifyAdapter.getListFilter().size() < 2) {
                    isCaptureAvatar = false;
                    isCaptureVerify = true;
                    isCapture = false;
                    isCaptureOther = false;
                    isCaptureRelease = false;
                    isCaptureRefund = false;
                    MediaUltis.captureImage(this);
                } else {
                    showErrorToast(getString(R.string.do_not_allow_take_over_two_photos));
                }
                break;
            case R.id.img_camera_partial_release:
                if (imageReleaseAdapter.getListFilter().size() < 1) {
                    isCaptureAvatar = false;
                    isCaptureVerify = false;
                    isCapture = false;
                    isCaptureOther = false;
                    isCaptureRelease = true;
                    isCaptureRefund = false;
                    MediaUltis.captureImage(this);
                } else {
                    showErrorToast(getString(R.string.do_not_allow_take_over_one_photos));
                }
                break;
            case R.id.img_camera_partial_refund:
                if (imageRefundAdapter.getListFilter().size() < 1) {
                    isCaptureAvatar = false;
                    isCaptureVerify = false;
                    isCapture = false;
                    isCaptureOther = false;
                    isCaptureRelease = false;
                    isCaptureRefund = true;
                    MediaUltis.captureImage(this);
                } else {
                    showErrorToast(getString(R.string.do_not_allow_take_over_one_photos));
                }
                break;
            case R.id.edt_date_of_birth:
                new SpinnerDatePickerDialogBuilder()
                        .context(getViewContext())
                        .callback((view1, year, monthOfYear, dayOfMonth) -> {
                            calDateOfBirth.set(year, monthOfYear, dayOfMonth);
                            edtDateOfBirth.setText(DateTimeUtils
                                    .convertDateToString(calDateOfBirth.getTime(),
                                            DateTimeUtils.SIMPLE_DATE_FORMAT));
                        })
                        .spinnerTheme(R.style.DatePickerSpinner)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(calDateOfBirth.get(Calendar.YEAR),
                                calDateOfBirth.get(Calendar.MONTH),
                                calDateOfBirth.get(Calendar.DAY_OF_MONTH))
                        .maxDate(calDateOfBirth.get(Calendar.YEAR),
                                calDateOfBirth.get(Calendar.MONTH),
                                calDateOfBirth.get(Calendar.DAY_OF_MONTH))
                        .minDate(1600, 0, 1)
                        .build()
                        .show();
                break;

            case R.id.edt_GTTT_date_accepted:

                new SpinnerDatePickerDialogBuilder()
                        .context(getViewContext())
                        .callback((view1, year, monthOfYear, dayOfMonth) -> {
                            calDateAccepted.set(year, monthOfYear, dayOfMonth);
                            edtGTTTDateAccepted.setText(DateTimeUtils
                                    .convertDateToString(calDateAccepted.getTime(),
                                            DateTimeUtils.SIMPLE_DATE_FORMAT));
                        })
                        .spinnerTheme(R.style.DatePickerSpinner)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(calDateAccepted.get(Calendar.YEAR),
                                calDateAccepted.get(Calendar.MONTH),
                                calDateAccepted.get(Calendar.DAY_OF_MONTH))
                        .maxDate(calDateAccepted.get(Calendar.YEAR),
                                calDateAccepted.get(Calendar.MONTH),
                                calDateAccepted.get(Calendar.DAY_OF_MONTH))
                        .minDate(1600, 0, 1)
                        .build()
                        .show();
                break;
            case R.id.rl_relationship:
                PopupMenu popup = new PopupMenu(getViewContext(), rlRelationship);
                popup.inflate(R.menu.relationship_popup);
                popup.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.menu_other) {
                        llOtherRelationship.setVisibility(View.VISIBLE);
                    } else {
                        llOtherRelationship.setVisibility(View.GONE);
                    }
                    edtRelationship.setText(item.getTitle());
                    return true;
                });
                popup.setGravity(Gravity.END);
                popup.show();
                break;
            case R.id.img_add_item_release:
                /*AlertDialog.Builder builder = new AlertDialog.Builder(getViewContext());
                ViewGroup viewGroup = getViewContext().findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_item_partial, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();*/

                /*mDialogAddItemPartial = new DialogAddItemPartial(getViewContext()) {

                };
                mDialogAddItemPartial.show();*/
                new DialogAddItemPartial(getViewContext()/*, this::applyTexts*/).setOnBackClickListener(Dialog::dismiss).setOnSaveClickListener(Dialog::dismiss).show();

                break;

            case R.id.img_add_item_refund:
                /*mDialogAddItemPartial = new DialogAddItemPartial(getViewContext()) {

                };
                mDialogAddItemPartial.show();*/
                new DialogAddItemPartial(getViewContext()).setOnBackClickListener(Dialog::dismiss).setOnSaveClickListener(Dialog::dismiss).show();
                break;
        }
    }

    private void submit() {
        //checkInfo(authenType);
        //checkImage(authenType);

        String[] arrImages = new String[listImages.size()];
        for (int i = 0; i < listImages.size(); i++) {
            if (!TextUtils.isEmpty(listImages.get(i).getText()))
                arrImages[i] = listImages.get(i).getText();
        }
        mFile = TextUtils.join(";", arrImages);

        if (mDeliveryType == 2) {
            List<DeliveryPostman> listSelected = getItemSelected();
            if (listSelected.size() == 0) {
                showErrorToast(getViewContext().getString(R.string.you_have_not_pick_any_package));
                return;
            }
            boolean isCanVerify = canVerify();
            if (!isCanVerify) {
                showErrorToast(getViewContext().getString(R.string.there_is_one_package_needed_to_particular_delivered));
                return;
            }

            if (llVerifyInfo.getVisibility() == View.VISIBLE || llVerifyImage.getVisibility() == View.VISIBLE) {//llCaptureVerify -> llVerifyImage
                if (authenType == 1 && !checkInfo(authenType)) {
                    return;
                } else if (authenType == 2 && !checkImage(authenType)) {
                    return;
                } else if (authenType == 3 && (!checkInfo(authenType) || !checkImage(authenType))) {
                    return;
                } else if (authenType == 0 && !checkInfo(authenType) && !checkImage(authenType)) {
                    showErrorToast(getViewContext().getString(R.string.you_must_take_verify_photos_or_enter_enough_verification_info));
                    return;
                }
            }

            if (TextUtils.isEmpty(edtOtherRelationship.getText()) &&
                    edtRelationship.getText().equals(getViewContext().getString(R.string.other))) {
                showErrorToast(getViewContext().getString(R.string.you_have_not_entered_reeceiver_relationship));
                return;
            }

            /**
             * nghiệp vụ mới tên người nhận có thể không có cũng được
             */
            /*if (TextUtils.isEmpty(tvReceiverName.getText())) {
                showErrorToast(getViewContext().getString(R.string.you_have_not_entered_real_receiver_name));
                return;
            }*/


            new ConfirmDialog(getViewContext(), listSelected.size(), totalAmount, totalFee)
                    .setOnCancelListener(Dialog::dismiss)
                    .setOnOkListener(confirmDialog -> {
                        showProgress();
                        InfoVerify infoVerify = new InfoVerify();//llVerifyImage.setVisibility(View.VISIBLE);
                        if (llVerifyInfo.getVisibility() == View.VISIBLE || llVerifyImage.getVisibility() == View.VISIBLE) {//llCaptureVerify -> llVerifyImage
                            infoVerify.setReceiverPIDWhere(edtGTTTLocatedAccepted.getText().toString());
                            infoVerify.setReceiverAddressDetail(edtUserAddress.getText().toString());
                            infoVerify.setReceiverPIDDate(edtGTTTDateAccepted.getText().toString());
                            infoVerify.setReceiverBirthday(edtDateOfBirth.getText().toString());
                            infoVerify.setGtgt(tvGTTT.getText().toString());
                            if (authenType == 0)
                                infoVerify.setAuthenType(3);
                            else
                                infoVerify.setAuthenType(authenType);
                        }


                        if (!TextUtils.isEmpty(edtOtherRelationship.getText())) {
                            mPresenter.paymentDelivery(mFile, mFileAvatar + ";" + mFileVerify + ";" + mFileOther, mSign, edtReceiverName.getText().toString(),
                                    edtOtherRelationship.getText(), infoVerify);
                        } else {
                            mPresenter.paymentDelivery(mFile, mFileAvatar + ";" + mFileVerify + ";" + mFileOther, mSign, edtReceiverName.getText().toString(),
                                    edtRelationship.getText().toString(), infoVerify);
                        }

                        confirmDialog.dismiss();
                    })
                    .setWarning(getViewContext().getString(R.string.are_you_sure_deliver_successfully))
                    .show();
        } else if (mDeliveryType == 1) {
            if (TextUtils.isEmpty(tv_reason.getText())) {
                Toast.showToast(tv_reason.getContext(), getViewContext().getString(R.string.please_pick_reason));
                return;
            }
            if (TextUtils.isEmpty(tv_solution.getText())) {
                Toast.showToast(tv_solution.getContext(), getViewContext().getString(R.string.you_have_not_chosen_solution));
                return;
            }
            mPresenter.submitToPNS(
                    mReasonInfo.getCode(),
                    mSolutionInfo.getCode(),
                    tv_Description.getText().toString(),
                    mFile,
                    mFileAvatar + ";" + mFileVerify + ";" + mFileOther,
                    mSign);
        } else if (mDeliveryType == 3) {
            /**
             * mDeliveryType = 3 -> chuyển tuyến
             */

            if (TextUtils.isEmpty(tv_route.getText())) {
                Toast.showToast(tv_route.getContext(), getViewContext().getString(R.string.you_have_not_pick_route));
                return;
            }
            if (TextUtils.isEmpty(tv_postman.getText())) {
                showErrorToast(getViewContext().getString(R.string.you_have_not_picked_postman));
                return;
            }
            int postmanId = 0;
            if (mPostmanInfo != null) {
                postmanId = Integer.parseInt(mPostmanInfo.getiD());
            }
            mPresenter.cancelDivided(Integer.parseInt(mRouteInfo.getRouteId()), postmanId, mSign, mFile);
            ///mPresenter.cancelDivided(Integer.parseInt(mCurrentRouteInfo.getRouteId()), postmanId, mSign, mFile);
//            mPresenter.changeRouteInsert(Integer.parseInt(mRouteInfo.getRouteId()), postmanId, mSign, mFile);
        } else if (mDeliveryType == 4) {
            List<DeliveryPostman> listSelected = getItemSelected();
            if (listSelected.size() == 0) {
                showErrorToast(getViewContext().getString(R.string.you_have_not_pick_any_package));
                return;
            }
            boolean isCanVerify = canVerify();
            if (!isCanVerify) {
                showErrorToast(getViewContext().getString(R.string.there_is_one_package_needed_to_particular_delivered));
                return;
            }

            if (llVerifyInfo.getVisibility() == View.VISIBLE || llVerifyImage.getVisibility() == View.VISIBLE) {//llCaptureVerify -> llVerifyImage
                if (authenType == 1 && !checkInfo(authenType)) {
                    return;
                } else if (authenType == 2 && !checkImage(authenType)) {
                    return;
                } else if (authenType == 3 && (!checkInfo(authenType) || !checkImage(authenType))) {
                    return;
                } else if (authenType == 0 && !checkInfo(authenType) && !checkImage(authenType)) {
                    showErrorToast(getViewContext().getString(R.string.you_must_take_verify_photos_or_enter_enough_verification_info));
                    return;
                }
            }

            if (TextUtils.isEmpty(edtOtherRelationship.getText()) &&
                    edtRelationship.getText().equals(getViewContext().getString(R.string.other))) {
                showErrorToast(getViewContext().getString(R.string.you_have_not_entered_reeceiver_relationship));
                return;
            }

            new ConfirmDialog(getViewContext(), listSelected.size(), totalAmount, totalFee)
                    .setOnCancelListener(Dialog::dismiss)
                    .setOnOkListener(confirmDialog -> {
                        showProgress();
                        InfoVerify infoVerify = new InfoVerify();//llVerifyImage.setVisibility(View.VISIBLE);
                        if (llVerifyInfo.getVisibility() == View.VISIBLE || llVerifyImage.getVisibility() == View.VISIBLE) {//llCaptureVerify -> llVerifyImage
                            infoVerify.setReceiverPIDWhere(edtGTTTLocatedAccepted.getText().toString());
                            infoVerify.setReceiverAddressDetail(edtUserAddress.getText().toString());
                            infoVerify.setReceiverPIDDate(edtGTTTDateAccepted.getText().toString());
                            infoVerify.setReceiverBirthday(edtDateOfBirth.getText().toString());
                            infoVerify.setGtgt(tvGTTT.getText().toString());
                            if (authenType == 0)
                                infoVerify.setAuthenType(3);
                            else
                                infoVerify.setAuthenType(authenType);
                        }


//                        if (!TextUtils.isEmpty(edtOtherRelationship.getText())) {
//                            mPresenter.deliveryPartial(mFile + ";" + mFileRelease, mFileAvatar + ";" + mFileVerify + ";" + mFileOther, mFileRefund,
//                                    edtContentPartial.getText().toString(), 0 /*Long.parseLong(edtPricePartialRelease.getText().toString())*/,
//                                    0 /*Long.parseLong(edtPricePartialRefund.getText().toString())*/, mSign, edtReceiverName.getText().toString(), edtRelationship.getText().toString(),
//                                    infoVerify, listItemPartialRelease, listItemPartialReturn);
//                        } else {
//                            mPresenter.deliveryPartial(mFile + ";" + mFileRelease, mFileAvatar + ";" + mFileVerify + ";" + mFileOther, mFileRefund,
//                                    edtContentPartial.getText().toString(), 0 /*Long.parseLong(edtPricePartialRelease.getText().toString())*/,
//                                    0 /*Long.parseLong(edtPricePartialRefund.getText().toString())*/, mSign, edtReceiverName.getText().toString(), edtRelationship.getText().toString(),
//                                    infoVerify, listItemPartialRelease, listItemPartialReturn);
//                        }

                        ///
                        mPresenter.deliveryPartial(mFile + ";" + mFileRelease, mFileAvatar + ";" + mFileVerify + ";" + mFileOther, mFileRefund,
                                edtContentPartial.getText().toString(), 0 /*Long.parseLong(edtPricePartialRelease.getText().toString())*/,
                                0 /*Long.parseLong(edtPricePartialRefund.getText().toString())*/, mSign, edtReceiverName.getText().toString(), edtRelationship.getText().toString(),
                                infoVerify, listItemPartialRelease, listItemPartialReturn);

                        confirmDialog.dismiss();
                    })
                    .setWarning(getViewContext().getString(R.string.are_you_sure_deliver_partial))
                    .show();



            ///
//            InfoVerify infoVerify = new InfoVerify();//llVerifyImage.setVisibility(View.VISIBLE);
//            if (llVerifyInfo.getVisibility() == View.VISIBLE || llVerifyImage.getVisibility() == View.VISIBLE) {//llCaptureVerify -> llVerifyImage
//                infoVerify.setReceiverPIDWhere(edtGTTTLocatedAccepted.getText().toString());
//                infoVerify.setReceiverAddressDetail(edtUserAddress.getText().toString());
//                infoVerify.setReceiverPIDDate(edtGTTTDateAccepted.getText().toString());
//                infoVerify.setReceiverBirthday(edtDateOfBirth.getText().toString());
//                infoVerify.setGtgt(tvGTTT.getText().toString());
//                if (authenType == 0)
//                    infoVerify.setAuthenType(3);
//                else
//                    infoVerify.setAuthenType(authenType);
//            }
//            mPresenter.deliveryPartial(mFile + ";" + mFileRelease, mFileAvatar + ";" + mFileVerify + ";" + mFileOther, mFileRefund,
//                    edtContentPartial.getText().toString(), 0 /*Long.parseLong(edtPricePartialRelease.getText().toString())*/,
//                    0 /*Long.parseLong(edtPricePartialRefund.getText().toString())*/, mSign, edtReceiverName.getText().toString(), edtRelationship.getText().toString(),
//                    infoVerify, listItemPartialRelease, listItemPartialReturn);


        }
    }

    private void verifyInfo() {
        rbVerifyInfo.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                checkInfoClick = true;
                llVerifyInfo.setVisibility(View.VISIBLE);
                /*if (TextUtils.isEmpty(edtGTTTDateAccepted.getText())) {
                    showErrorToast(getViewContext().getString(R.string.you_have_not_provided_profile));
                }

                if (TextUtils.isEmpty(edtGTTTLocatedAccepted.getText())) {
                    //showErrorToast(getViewContext().getString(R.string.you_have_not_inputed_the_place_of_profile));
                    showErrorToast(getViewContext().getString(R.string.please_enter_full_authentication_information));
                }

                if (TextUtils.isEmpty(edtDateOfBirth.getText())) {
                    showErrorToast(getViewContext().getString(R.string.you_have_not_entered_date_of_birth));
                }

                if (TextUtils.isEmpty(tvGTTT.getText())) {
                    showErrorToast(getViewContext().getString(R.string.you_have_not_enter_number_of_profile));
                }

                if (TextUtils.isEmpty(edtUserAddress.getText())) {
                    //showErrorToast(getViewContext().getString(R.string.you_have_not_entered_addres_of_user));
                    showErrorToast(getViewContext().getString(R.string.please_enter_full_authentication_information));
                }*/
                //llCaptureVerify.setVisibility(View.VISIBLE);
            } else {
                checkInfoClick = false;
                llVerifyInfo.setVisibility(View.GONE);
                edtGTTTLocatedAccepted.setText("");
                edtUserAddress.setText("");
                tvGTTT.setText("");

                //llCaptureVerify.setVisibility(View.GONE);
            }
        });
    }

    private void verifyImage() {
        rbVerifyImage.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                checkImageClick = true;
                authenType = 2;
                llVerifyImage.setVisibility(View.VISIBLE);
            } else {
                checkImageClick = false;
                llVerifyImage.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                attemptSendMedia(data.getData().getPath());

                //attemptSendMediaAvatar(data.getData().getPath());
            }
        }
    }

    private void attemptSendMedia(String path_media) {
        File file = new File(path_media);
        Observable.fromCallable(() -> {
            Uri uri = Uri.fromFile(new File(path_media));
            return BitmapUtils.processingBitmap(uri, getViewContext());
        }).subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .map(bitmap -> BitmapUtils.saveImage(bitmap, file.getParent(), "Process_" + file.getName(), Bitmap.CompressFormat.JPEG, 50))
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                isSavedImage -> {
                    if (isSavedImage) {
                        String path = file.getParent() + File.separator + "Process_" + file.getName();
                        // mSignPosition = false;
                        mPresenter.postImage(path);
                        //mPresenter.postImageAvatar(pathAvatar);

                        if (isCaptureAvatar) {
                            mPresenter.postImageAvatar(path);
                            imageAvatarAdapter.getListFilter().add(new Item(path, ""));
                            imageAvatarAdapter.notifyDataSetChanged();
                        }

                        if (isCaptureVerify) {
                            imageVerifyAdapter.getListFilter().add(new Item(path, ""));
                            imageVerifyAdapter.notifyDataSetChanged();
                        }
                        if (isCaptureOther) {
                            imageOtherAdapter.getListFilter().add(new Item(path, ""));
                            imageOtherAdapter.notifyDataSetChanged();
                        }
                        if (isCapture) {
                            imageAdapter.getListFilter().add(new Item(path, ""));
                            imageAdapter.notifyDataSetChanged();
                        }
                        if (isCaptureRelease) {
                            imageReleaseAdapter.getListFilter().add(new Item(path, ""));
                            imageReleaseAdapter.notifyDataSetChanged();
                        }
                        if (isCaptureRefund) {
                            imageRefundAdapter.getListFilter().add(new Item(path, ""));
                            imageRefundAdapter.notifyDataSetChanged();
                        }
                        if (file.exists())
                            file.delete();
                    } else {
                        mPresenter.postImage(path_media);
                    }
                },
                onError -> Logger.e("error save image")
        );
    }

    private void showUIReason() {
        ArrayList<Item> items = new ArrayList<>();
        if (mListReason != null) {
            for (ReasonInfo item : mListReason) {
                items.add(new Item(item.getCode(), item.getName()));
            }
        }
        new PickerDialog(getViewContext(), "Chọn lý do", items,
                item -> {
                    for (ReasonInfo info : mListReason) {
                        if (item.getValue().equals(info.getCode())) {
                            mReasonInfo = info;
                            break;
                        }
                    }
                    if (mReasonInfo != null) {
                        tv_reason.setText(mReasonInfo.getName());
                        mListSolution = null;
                        tv_solution.setText("");
                        mReloadSolution = true;
                        loadSolution();
                    }
                }).show();
    }

    private void loadSolution() {
        if (mReasonInfo != null)
            mPresenter.loadSolution(mReasonInfo.getCode());
    }

    @Override
    public void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos) {
        if (null != getViewContext()) {
            mListReason = reasonInfos;
            if (mListReason != null && mListReason.size() > 0) {
                for (ReasonInfo info : mListReason) {
                    if (info.getID() == 42) {
                        mReasonInfo = info;
                        tv_reason.setText(mReasonInfo.getName());
                        break;
                    }
                }
                loadSolution();
            }
        }
    }

    @Override
    public void showSolution(ArrayList<SolutionInfo> solutionInfos) {
        if (null != getViewContext()) {
            mListSolution = solutionInfos;
            if (mListSolution != null && mListSolution.size() > 0) {
                if (mReasonInfo.getID() == 48 || mReasonInfo.getID() == 11) {
                    for (SolutionInfo info : mListSolution) {
                        if (info.getID() == 8) {
                            mSolutionInfo = info;
                            tv_solution.setText(mSolutionInfo.getName());
                            mClickSolution = false;
                        }
                    }
                } else if (mReasonInfo.getID() == 42) {
                    changeDefaultSolution();
                } else {
                    mClickSolution = true;
                }
            }
            if (mClickSolution)
                showUISolution();
        }
    }

    @Override
    public void showRoute(ArrayList<RouteInfo> routeInfos) {
        mListRoute = routeInfos;
    }

    @Override
    public void showPostman(ArrayList<UserInfo> userInfos) {
        if (mCurrentRouteInfo.getRouteCode().equals(mRouteInfo.getRouteCode())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                userInfos.removeIf(userInfo1 -> userInfo1.getiD().equals(userInfo.getiD()));
            } else {
                for (UserInfo user : userInfos) {
                    if (user.getiD().equals(userInfo.getiD())) {
                        userInfos.remove(user);
                    }
                }
            }
        }
        mListPostman = userInfos;
    }

    @Override
    public void showPaymentV2Success(String message) {
        if (null != getViewContext()) {
            new SweetAlertDialog(getViewContext())
                    .setTitleText("Thông báo")
                    .setContentText(message)
                    .setConfirmText("Ok")
                    .setConfirmClickListener(v -> {
                        v.dismiss();
                        hideProgress();
                        finishView();
                    }).show();
        } else showSuccessToast(message);
    }

    private void showUIRoute() {
        ArrayList<Item> items = new ArrayList<>();
        if (mListRoute != null) {
            for (RouteInfo item : mListRoute) {
                items.add(new Item(item.getRouteId(), item.getRouteName()));
            }
        }
        if (pickerUIRoute == null) {
            pickerUIRoute = new ItemBottomSheetPickerUIFragment(items, getViewContext().getString(R.string.pick_route),
                    (item, position) -> {
                        tv_route.setText(item.getText());
                        mRouteInfo = mListRoute.get(position);
                        mListPostman = null;
                        tv_postman.setText("");
                        mPresenter.getPostman(userInfo.getUnitCode(), Integer.parseInt(item.getValue()), "D");
                    }, 0);
            pickerUIRoute.show(getActivity().getSupportFragmentManager(), pickerUIRoute.getTag());
        } else {
            pickerUIRoute.setData(items, 0);
            if (!pickerUIRoute.isShow) {
                pickerUIRoute.show(getActivity().getSupportFragmentManager(), pickerUIRoute.getTag());
            }
        }
    }

    private void showUIPostman() {
        if (null == mListPostman || mListPostman.isEmpty()) {
            showErrorToast(getViewContext().getString(R.string.please_pick_route));
        } else {
            ArrayList<Item> items = new ArrayList<>();
            if (mListPostman != null) {
                for (UserInfo item : mListPostman) {
                    items.add(new Item(item.getiD(), item.getFullName()));
                }
            }
            if (pickerUIPostman == null) {
                pickerUIPostman = new ItemBottomSheetPickerUIFragment(items, getViewContext().getString(R.string.pick_postman),
                        (item, position) -> {
                            tv_postman.setText(item.getText());
                            mPostmanInfo = mListPostman.get(position);
                        }, 0);
                pickerUIPostman.show(getActivity().getSupportFragmentManager(), pickerUIPostman.getTag());
            } else {
                pickerUIPostman.setData(items, 0);
                if (!pickerUIPostman.isShow) {
                    pickerUIPostman.show(getActivity().getSupportFragmentManager(), pickerUIPostman.getTag());
                }
            }
        }
    }

    @Override
    public void showImage(String file, String path) {
        if (null != getViewContext()) {
            if (isCaptureAvatar) {
//                mFileAvatar = file;
                for (int i = 0; i < listImagesAvatar.size(); i++) {
                    if (listImagesAvatar.get(i).getValue().equals(path)) {
                        listImagesAvatar.get(i).setText(file);
                    }
                }
            } else if (isCaptureVerify) {
//                if (mFileVerify.equals("")) {
//                    mFileVerify = file;
//                } else {
//                    mFileVerify += ";";
//                    mFileVerify += file;
//                }
                for (int i = 0; i < listImageVerify.size(); i++) {
                    if (listImageVerify.get(i).getValue().equals(path)) {
                        listImageVerify.get(i).setText(file);
                    }
                }
            } else if (isCapture) {
                for (int i = 0; i < listImages.size(); i++) {
                    if (listImages.get(i).getValue().equals(path)) {
                        listImages.get(i).setText(file);
                    }
                }
//                if (mFile.equals("")) {
//                    mFile = file;
//                } else {
//                    mFile += ";";
//                    mFile += file;
//                }
            } else if (isCaptureOther) {
                for (int i = 0; i < listImageOther.size(); i++) {
                    if (listImageOther.get(i).getValue().equals(path)) {
                        listImageOther.get(i).setText(file);
                    }
                }
//                if (mFileOther.equals("")) {
//                    mFileOther = file;
//                } else {
//                    mFileOther += ";";
//                    mFileOther += file;
//                }
            } else if (isCaptureRelease) {
                for (int i = 0; i < listImageRelease.size(); i++) {
                    if (listImageRelease.get(i).getValue().equals(path)) {
                        listImageRelease.get(i).setText(file);
                    }
                }
            } else if (isCaptureRefund) {
                for (int i = 0; i < listImageRefund.size(); i++) {
                    if (listImageRefund.get(i).getValue().equals(path)) {
                        listImageRefund.get(i).setText(file);
                    }
                }
            }
        }
    }

    @Override
    public void deleteFile() {
        try {
            if (isCaptureVerify) {
                mFileVerify = "";
                imageVerifyAdapter.getListFilter().remove(imageVerifyAdapter.getListFilter().size() - 1);
                imageVerifyAdapter.notifyDataSetChanged();
            } else if (isCaptureAvatar) {
                try {
                    mFileAvatar = "";
                    imageAvatarAdapter.getListFilter().remove(imageAvatarAdapter.getListFilter().size() - 1);
                    imageAvatarAdapter.notifyDataSetChanged();
                } catch (Exception exception) {
                }
            } else if (isCapture) {
                mFile = "";
                imageAdapter.getListFilter().remove(imageAdapter.getListFilter().size() - 1);
                imageAdapter.notifyDataSetChanged();
            } else if (isCaptureOther) {
                mFileOther = "";
                imageOtherAdapter.getListFilter().remove(imageOtherAdapter.getListFilter().size() - 1);
                imageOtherAdapter.notifyDataSetChanged();
            } else if (isCaptureRelease) {
                mFileRelease = "";
                imageReleaseAdapter.getListFilter().remove(imageReleaseAdapter.getListFilter().size() - 1);
                imageReleaseAdapter.notifyDataSetChanged();
            } else if (isCaptureRefund) {
                mFileRefund = "";
                imageRefundAdapter.getListFilter().remove(imageRefundAdapter.getListFilter().size() - 1);
                imageRefundAdapter.notifyDataSetChanged();
            }
        } catch (Error error) {
            showError("Vui lòng chụp lại");

        }
    }

    @Override
    public void showError(String message) {
        if (null != getViewContext()) {
            mDeliveryError = +1;
            int total = mDeliverySuccess + mDeliveryError;
            if (total == getItemSelected().size()) {
                showFinish();
            }
        }
    }

    @Override
    public void showCheckAmountPaymentError(String message, String amountPP, String amountPNS) {
        if (null != getViewContext()) {
            new SweetAlertDialog(getViewContext(), SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText(getString(R.string.notification))
                    .setContentText(message + "\nTiền trên hệ thông Paypost: " + amountPP
                            + "\nTiền trên hệ thông PNS: " + amountPNS
                            + " \nBạn có muốn cập nhật theo số tiền trên PayPost không?")
                    .setCancelText(getString(R.string.no))
                    .setConfirmText(getString(R.string.yes))
                    .setCancelClickListener(v -> {
                        mPresenter.paymentV2(false);
                        v.dismiss();
                        hideProgress();
                    })
                    .setConfirmClickListener(v -> {
                        mPresenter.paymentV2(true);
                        v.dismiss();
                        hideProgress();
                    })
                    .show();
        }
    }

    @Override
    public void showSuccess(String code) {
        if (null != getViewContext()) {
            if (code.equals("00")) {
                mDeliverySuccess += 1;
            } else {
                mDeliveryError += 1;
            }
            int total = mDeliverySuccess + mDeliveryError;
            if (total == getItemSelected().size()) {
                showFinish();
            }
        }
    }

    @Override
    public void showSuccessPartial(String code) {
        showPartialFinish();
    }

    @Override
    public void showErrorPartial(String message) {
        showPartialFinish();
    }

    @Override
    public void showCancelDivided(String message) {
        if (null != getViewContext()) {
            Toast.showToast(getActivity(), message);
            finishView();
        }
    }

    private void showPartialFinish() {
        hideProgress();
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setConfirmText("OK")
                    .setTitleText(getString(R.string.notification))
                    .setContentText("Báo phát một phần hoàn tất")
                    //.setContentText("Báo phát một phần hoàn tất. Phát ["+  quantityRelease+"], hoàn ["+quantityReturn+"]")
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        finishView();
                    }).show();
        }
    }

    private void showFinish() {
        hideProgress();
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setConfirmText("OK")
                    .setTitleText(getString(R.string.notification))
                    .setContentText("Báo phát BD13 hoàn tất. Thành công [" + mDeliverySuccess + "] thất bại [" + mDeliveryError + "]")
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                        finishView();

                    }).show();
        }
    }

    @Override
    public void finishView() {
        try {
            mPresenter.back();
            mPresenter.onTabRefresh();
        } catch (NullPointerException nullPointerException) {
        }
    }

    @Override
    public List<DeliveryPostman> getItemSelected() {
        if (null != adapter)
            return adapter.getItemsSelected();
        else return new ArrayList<>();
    }

    @Override
    public List<DeliveryListRelease> getProductRelease() {
        if (null != xacNhanBaoPhatPartialReleaseAdapter)
            return xacNhanBaoPhatPartialReleaseAdapter.getListProduct();
        else return new ArrayList<>();
        //return null;
    }

    @Override
    public List<DeliveryListRelease> getProductReturn() {
        if (null != xacNhanBaoPhatPartialReturnAdapter)
            return xacNhanBaoPhatPartialReturnAdapter.getListProduct();
        else return new ArrayList<>();
    }

    /*@Override
    public void showListProducts(List<DeliveryProductRequest> list) {

    }*/

    private void updateTotalPackage() {
        totalAmount = 0;
        totalFee = 0;
        for (DeliveryPostman i : getItemSelected()) {
            totalAmount += i.getAmount();
            totalFee += i.getTotalFee();
        }
        tv_quantity.setText(String.format(" %s", getItemSelected().size()));
        tv_total_amount.setText(String.format(" %s đ", NumberUtils.formatPriceNumber(totalAmount)));
        tvTotalFee.setText(String.format(" %s đ", NumberUtils.formatPriceNumber(totalFee)));
        tvTotal.setText(String.format(" %s đ", NumberUtils.formatPriceNumber(totalFee + totalAmount)));
    }

    private boolean checkSameAddress() {
        List<DeliveryPostman> itemSelected = getItemSelected();
        if (null == itemSelected || 0 == itemSelected.size())
            return false;
        DeliveryPostman firstItem = itemSelected.get(0);
        for (int i = 1; i < itemSelected.size(); i++) {
            if (!firstItem.getReciverName().equals(itemSelected.get(i).getReciverName())
                    || !firstItem.getReciverAddress().equals(itemSelected.get(i).getReciverAddress()))
                return false;
        }
        return true;
    }

    private void showUISolution() {
        ArrayList<Item> items = new ArrayList<>();

        if (mListSolution != null) {
            for (SolutionInfo item : mListSolution) {
                items.add(new Item(item.getCode(), item.getName()));
            }
            new PickerDialog(getViewContext(), getString(R.string.pick_solution)
                    , items,
                    item -> {
                        for (SolutionInfo info : mListSolution) {
                            if (item.getValue().equals(info.getCode())) {
                                mSolutionInfo = info;
                                break;
                            }
                        }
                        if (mSolutionInfo != null) {
                            tv_solution.setText(mSolutionInfo.getName());
                        }
                    }).show();
        }
    }

    private int getAuthenType() {
        List<DeliveryPostman> list = getItemSelected();
        if (list.size() > 1) {
            DeliveryPostman firstItem = getItemSelected().get(0);
            boolean isSameAuthenType = true;
            for (int i = 1; i < list.size(); i++) {
                DeliveryPostman mediatorItem = list.get(i);
                if (!firstItem.getAuthenType().equals(mediatorItem.getAuthenType())) {
                    isSameAuthenType = false;
                    break;
                }
            }
            if (isSameAuthenType) {
                return firstItem.getAuthenType();
            } else return -1;
        } else if (list.size() == 1) {
            DeliveryPostman firstItem = getItemSelected().get(0);
            return firstItem.getAuthenType();
        } else return -2;
    }

    private void checkVerify() {
        authenType = getAuthenType();
        if (authenType == 0) {
            rbVerifyInfo.setVisibility(View.VISIBLE);
            rbVerifyImage.setVisibility(View.VISIBLE);
            llVerify.setVisibility(View.VISIBLE);
            if (rbVerifyInfo.isChecked()) {
                llVerifyInfo.setVisibility(View.VISIBLE);
                //llCaptureVerify.setVisibility(View.VISIBLE);
                llVerifyImage.setVisibility(View.GONE);//add
            } else {
                llVerifyInfo.setVisibility(View.GONE);
                //llCaptureVerify.setVisibility(View.GONE);
                llVerifyImage.setVisibility(View.GONE);//add
            }
            if (rbVerifyImage.isChecked()) {
                authenType = 2;
            }
        } else if (authenType == 1) {
            llVerifyInfo.setVisibility(View.VISIBLE);
            llVerify.setVisibility(View.VISIBLE);
            rbVerifyInfo.setVisibility(View.VISIBLE);
            rbVerifyImage.setVisibility(View.VISIBLE);
            rbVerifyInfo.setChecked(true);
            verifyInfo();
            verifyImage();
            //llCaptureVerify.setVisibility(View.GONE);
            llVerifyImage.setVisibility(View.GONE);
        } else if (authenType == 2) {
            llVerifyInfo.setVisibility(View.GONE);
            llVerify.setVisibility(View.VISIBLE);
            rbVerifyInfo.setVisibility(View.VISIBLE);
            rbVerifyImage.setVisibility(View.VISIBLE);
            rbVerifyImage.setChecked(true);
            verifyInfo();
            verifyImage();
            //llCaptureVerify.setVisibility(View.VISIBLE);
            llVerifyImage.setVisibility(View.VISIBLE);
        } else if (authenType == 3) {
            llVerifyInfo.setVisibility(View.VISIBLE);
            llVerify.setVisibility(View.VISIBLE);
            rbVerifyInfo.setVisibility(View.VISIBLE);
            rbVerifyImage.setVisibility(View.VISIBLE);
            //llCaptureVerify.setVisibility(View.VISIBLE);
            rbVerifyInfo.setChecked(true);
            rbVerifyImage.setChecked(true);
            verifyInfo();
            verifyImage();
            llVerifyImage.setVisibility(View.VISIBLE);
        } else if (authenType == -2) {
            llVerify.setVisibility(View.GONE);
        }
    }

    private boolean canVerify() {
        if (getAuthenType() == -1 || getAuthenType() == -2)
            return false;
        else return true;
    }

    private boolean checkInfo(int authenType) {
        if (TextUtils.isEmpty(edtGTTTDateAccepted.getText())) {
            if (authenType != 0)
                showErrorToast(getViewContext().getString(R.string.you_have_not_provided_profile));
            return false;
        }

        /*if (TextUtils.isEmpty(tvReceiverName.getText())) {
            if (authenType != 0)
                showErrorToast(getViewContext().getString(R.string.you_have_not_inputed_receiver_name));
            return false;
        }*/

        if (TextUtils.isEmpty(edtGTTTLocatedAccepted.getText())) {
            if (authenType != 0)
                //showErrorToast(getViewContext().getString(R.string.you_have_not_inputed_the_place_of_profile));
                showErrorToast(getViewContext().getString(R.string.please_enter_full_authentication_information));
            return false;
        }

        if (TextUtils.isEmpty(edtDateOfBirth.getText())) {
            if (authenType != 0)
                showErrorToast(getViewContext().getString(R.string.you_have_not_entered_date_of_birth));
            return false;
        }

        if (TextUtils.isEmpty(tvGTTT.getText())) {
            showErrorToast(getViewContext().getString(R.string.you_have_not_enter_number_of_profile));
            return false;
        }

        if (TextUtils.isEmpty(edtUserAddress.getText())) {
            if (authenType != 0)
                //showErrorToast(getViewContext().getString(R.string.you_have_not_entered_addres_of_user));
                showErrorToast(getViewContext().getString(R.string.please_enter_full_authentication_information));
            return false;
        }
        return true;
    }

    private boolean checkImage(int authenType) {
        mFileAvatar = "";
        mFileVerify = "";
        mFileOther = "";

        String[] arrAvartar = new String[listImagesAvatar.size()];
        for (int i = 0; i < listImagesAvatar.size(); i++) {
            if (!TextUtils.isEmpty(listImagesAvatar.get(i).getText()))
                arrAvartar[i] = listImagesAvatar.get(i).getText();
        }

        String[] arrVerify = new String[listImageVerify.size()];
        for (int i = 0; i < listImageVerify.size(); i++) {
            if (!TextUtils.isEmpty(listImageVerify.get(i).getText()))
                arrVerify[i] = listImageVerify.get(i).getText();
        }

        String[] arrOther = new String[listImageOther.size()];
        for (int i = 0; i < listImageOther.size(); i++) {
            if (!TextUtils.isEmpty(listImageOther.get(i).getText()))
                arrOther[i] = listImageOther.get(i).getText();
        }


//        if (TextUtils.isEmpty(mFileAvatar) && TextUtils.isEmpty(mFileVerify) && TextUtils.isEmpty(mFileOther)) {
//            if (authenType != 0)
//                showErrorToast(getViewContext().getString(R.string.you_have_not_taked_verify_photos));
//            return false;
//        }
        if (arrAvartar.length == 0 && arrOther.length == 0 && arrVerify.length == 0) {
            if (authenType != 0)
                showErrorToast(getViewContext().getString(R.string.you_have_not_taked_verify_photos));
            return false;
        } else {
            mFileAvatar = TextUtils.join(";", arrAvartar);
            mFileVerify = TextUtils.join(";", arrVerify);
            mFileOther = TextUtils.join(";", arrOther);

        }


        return true;
    }

    private void changeDefaultSolution() {
        if (!mListSolution.isEmpty()) {
            for (SolutionInfo info : mListSolution) {
                if (info.getID() == 1) {
                    mSolutionInfo = info;
                    tv_solution.setText(mSolutionInfo.getName());
                    break;
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void eventAddItemPartial(CustomItemPartialAdded customItemPartialAdded) {
        nameProductAdded = customItemPartialAdded.getName();
        quantityProductAdded = customItemPartialAdded.getQuantity();
        weightProductAdded = customItemPartialAdded.getWeight();
        priceProductAdded = customItemPartialAdded.getPrice();

        listItemPartialRelease.add(new DeliveryListRelease(12l, nameProductAdded, quantityProductAdded, "cái",
                weightProductAdded, priceProductAdded,0l /*Long.parseLong(edtPricePartialRelease.getText().toString())*/, 0l/*Long.parseLong("1234")*/,
                "123", "1313"));


        /*listItemPartialReturn.add(new DeliveryListRelease(23l, nameProductAdded, quantityProductAdded, "cái",
                weightProductAdded, priceProductAdded, 0l *//*Long.parseLong(edtPricePartialRelease.getText().toString())*//*, 0l *//*Long.parseLong("124")*//*,
                "123", "1313"));*/
        xacNhanBaoPhatPartialReleaseAdapter.notifyDataSetChanged();
        xacNhanBaoPhatPartialReturnAdapter.notifyDataSetChanged();

    }

    @Subscribe(sticky = true)
    public void eventDOP1(CustomDOP1 customDOP1) {
        dop1 = customDOP1.getPartialRelease();
    }

    @Subscribe(sticky = true)
    public void eventQuantityRelease(CustomQuantityReleases customQuantityProducts) {
        quantityRelease = customQuantityProducts.getQuantity();
        Log.d("123123", "quantityRelease event: "+quantityRelease);
    }

    @Subscribe(sticky = true)
    public void eventQuantityReturn(CustomQuantityReturn customQuantityReturn) {
        quantityReturn = customQuantityReturn.getQuantity();
        Log.d("123123", "quantityReturn event: "+quantityReturn);
    }

    /*@Override
    public void applyTexts(String username, String password) {
        Log.d("123123", "username: "+ username);
        Log.d("123123", "password: "+ password);
        if (!username.isEmpty() && !password.isEmpty()) {
            listItemPartialRelease.add(new ItemPartialRelease(username, 20, password, "20000"));
            xacNhanBaoPhatPartialReleaseAdapter.notifyDataSetChanged();
        }
    }*/
}
