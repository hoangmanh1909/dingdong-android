package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.log.Logger;
import com.core.base.viper.ViewFragment;
import com.core.utils.NetworkUtils;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.ChonAnhCallback;
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.callback.PickerCallback;
import com.ems.dingdong.dialog.ConfirmDialog;
import com.ems.dingdong.dialog.ConfirmPKTCDialog;
import com.ems.dingdong.dialog.ConfirmPhiHuyDonHangDialog;
import com.ems.dingdong.dialog.DiallogChonAnh;
import com.ems.dingdong.dialog.DialogNhapKhongThanhCong;
import com.ems.dingdong.dialog.DialogNhaptienCOD;
import com.ems.dingdong.dialog.DialogReason;
import com.ems.dingdong.dialog.DialogText;
import com.ems.dingdong.dialog.PickerDialog;
import com.ems.dingdong.dialog.SignDialog;
import com.ems.dingdong.functions.mainhome.hinhanh.ImageAdapter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.loadhinhanh.DataModel;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.loadhinhanh.JavaImageResizer;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.loadhinhanh.ScalingUtilities;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.more.AddHangDoiTraDialog;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.more.HangDoiTraAdapter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.more.HangDoiTraCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.more.LadingProduct;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.more.PhoneCodeEdit;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.parital.CreateDeliveryParialDialog;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.parital.CuocAdapter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.parital.DeliveryPartialAdapter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.parital.ModeFee;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.parital.PhiThuHoAdapter;
import com.ems.dingdong.model.BuuCucHuyenMode;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.DistrictModels;
import com.ems.dingdong.model.InfoVerify;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.PhithuhoModel;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ProductModel;
import com.ems.dingdong.model.ProvinceModels;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.WardModels;
import com.ems.dingdong.model.request.DeliveryProductRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.BitmapUtils;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.EditTextUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.MediaUltisV1;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.StringUtils;
import com.ems.dingdong.utiles.TimeUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.utiles.Utils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemTextView;
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment;
import com.google.android.gms.common.util.DataUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
//import com.sip.cmc.SipCmc;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

public class XacNhanBaoPhatFragment extends ViewFragment<XacNhanBaoPhatContract.Presenter> implements XacNhanBaoPhatContract.View {

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.tv_title)
    CustomBoldTextView tv_title;
    @BindView(R.id.img_send)
    Button img_send;
    @BindView(R.id.tv_quantity)
    CustomBoldTextView tv_quantity;
    @BindView(R.id.tv_total_amount)
    CustomBoldTextView tv_total_amount;
    @BindView(R.id.rad_success)
    TextView rad_success;
    @BindView(R.id.rad_change_route)
    TextView rad_change_route;
    @BindView(R.id.rad_fail)
    TextView rad_fail;
    @BindView(R.id.ll_confirm_fail)
    LinearLayout ll_confirm_fail;
    @BindView(R.id.ll_change_route)
    LinearLayout ll_change_route;
    @BindView(R.id.tv_reason)
    FormItemTextView tv_reason;
    @BindView(R.id.tv_solution)
    FormItemTextView tv_solution;
    @BindView(R.id.tv_Description)
    EditText tv_Description;
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
    EditText tvGTTT;
    @BindView(R.id.edt_relationship)
    TextView edtRelationship;
    @BindView(R.id.tv_receiver_name)
    TextView tvRealReceiverName;
    @BindView(R.id.tv_address_user)
    CustomTextView tvAddressUser;
    @BindView(R.id.tv_providers)
    CustomTextView tvProviders;
    @BindView(R.id.rl_relationship)
    LinearLayout rlRelationship;
    @BindView(R.id.layout_real_receiver_name)
    LinearLayout linearLayoutName;
    @BindView(R.id.ll_other_relationship)
    LinearLayout llOtherRelationship;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.edt_other_relationship)
    EditText edtOtherRelationship;
    @BindView(R.id.edt_note)
    EditText edtNote;
    @BindView(R.id.recycler_image_verify_avatar)
    RecyclerView recyclerImageVerifyAvatar;
    @BindView(R.id.recycler_image)
    RecyclerView recyclerViewImage;
    @BindView(R.id.recycler_image_verify)
    RecyclerView recyclerViewImageVerify;
    @BindView(R.id.recycler_image_other)
    RecyclerView recyclerImageOther;
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
    @BindView(R.id.ll_delivery)
    LinearLayout ll_delivery;
    @BindView(R.id.edt_date_of_birth)
    CustomTextView edtDateOfBirth;
    @BindView(R.id.edt_GTTT_date_accepted)
    CustomTextView edtGTTTDateAccepted;
    @BindView(R.id.edt_GTTT_located_accepted)
    TextInputEditText edtGTTTLocatedAccepted;
    @BindView(R.id.edt_user_address)
    TextInputEditText edtUserAddress;
    @BindView(R.id.tv_pt_des)
    TextView tv_pt_des;
    @BindView(R.id.et_pt_amount)
    TextInputEditText et_pt_amount;
    @BindView(R.id.et_pt_amountlayout)
    TextInputLayout et_pt_amountlayout;
    @BindView(R.id.recycler_delivery_partial)
    RecyclerView recycler_delivery_partial;
    @BindView(R.id.iv_camera_partial_d)
    ImageView iv_camera_partial_d;
    @BindView(R.id.recycler_image_partial_d)
    RecyclerView recycler_image_partial_d;
    @BindView(R.id.iv_add_delivery)
    ImageView iv_add_delivery;
    @BindView(R.id.iv_add_refund)
    ImageView iv_add_refund;
    @BindView(R.id.rad_partial)
    TextView rad_partial;
    @BindView(R.id.ll_partial)
    LinearLayout ll_partial;
    @BindView(R.id.tv_pt_amount_r)
    TextView tv_pt_amount_r;
    @BindView(R.id.recycler_refund_partial)
    RecyclerView recycler_refund_partial;
    @BindView(R.id.iv_camera_partial_r)
    ImageView iv_camera_partial_r;
    @BindView(R.id.recycler_image_partial_r)
    RecyclerView recycler_image_partial_r;
    @BindView(R.id.rl_image_partial_d)
    RelativeLayout rl_image_partial_d;
    @BindView(R.id.rl_image_partial_r)
    RelativeLayout rl_image_partial_r;
    @BindView(R.id.ll_image)
    LinearLayout ll_image;
    @BindView(R.id.rad_dop1)
    RadioButton rad_dop1;
    @BindView(R.id.rad_dop2)
    RadioButton rad_dop2;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.cb_selected)
    CheckBox cbSelected;
    @BindView(R.id.recyclerds)
    RecyclerView recyclerds;
    @BindView(R.id.recyclercuoc)
    RecyclerView recyclercuoc;
    @BindView(R.id.ll_chontuyen)
    LinearLayout _llChontuyen;
    @BindView(R.id.tv_buu_cuc)
    FormItemTextView _tvBuuCuc;
    @BindView(R.id.cb_selected_edtcod)
    CheckBox checkBoxedtCod;
    //    @BindView(R.id.cb_selected_phathoan)
//    CheckBox cbSelectedPhathoan;
    @BindView(R.id.ll_tong_tien_tam_thu)
    LinearLayout llTongTienTamThu;
    @BindView(R.id.tv_tong_tien_nop_edit)
    TextView tvTongTienTamthu;
    @BindView(R.id.ll_tong_tien_tam_thu1)
    LinearLayout ll_tong_tien_tam_thu1;
    @BindView(R.id.tv_tong_tien_nop_edit1)
    TextView tvTongTienTamthu1;
    @BindView(R.id.tv_time)
    FormItemTextView tvTime;
    @BindView(R.id.tv_date_hoantra)
    FormItemTextView tvDateHoantra;
    @BindView(R.id.tv_tiem_hoantra)
    FormItemTextView tvTiemHoantra;
    long tiem_tam = 0;
    @BindView(R.id.ll_edt_cod)
    LinearLayout lledtcod;
    @BindView(R.id.ll_doitra)
    View llDoitra;
    @BindView(R.id.tv_buu_cuc_hoantra)
    FormItemTextView tvBuuCucHoantra;
    @BindView(R.id.tv_route_hoantra)
    FormItemTextView tvRouteHoantra;
    @BindView(R.id.recycler_hang_doi_tra)
    RecyclerView recyclerHangDoiTra;
    @BindView(R.id.recycler_image_hang_doi_tra)
    RecyclerView recyclerUmageHangDoiTra;
    @BindView(R.id.tv_hang_doi_tra)
    TextView tvHangDoiTra;
    @BindView(R.id.tv_xaphuong)
    TextView tvXaphuong;
    @BindView(R.id.tv_tinhtp)
    TextView tvTinhtp;
    @BindView(R.id.tv_quanhuyen)
    TextView tvQuanhuyen;
    @BindView(R.id.tv_xaphuong_bosung)
    TextView tvXaphuongBosung;
    @BindView(R.id.tv_quanhuyen_bosung)
    TextView tvQuanhuyenBosung;
    @BindView(R.id.edt_ma_buu_gui)
    EditText edtMaBuuGui;
    @BindView(R.id.rl_xaphuong_bosung)
    LinearLayout rlXaphuongBosung;
    @BindView(R.id.rl_xaphuong)
    LinearLayout rlXaphuong;
    @BindView(R.id.rl_tinhtp)
    LinearLayout rlTinhtp;
    @BindView(R.id.rl_quanhuyen)
    LinearLayout rlQuanhuyen;
    @BindView(R.id.rl_sogtt)
    LinearLayout rlSogtt;
    @BindView(R.id.rl_ghichu)
    LinearLayout rlGhichu;
    @BindView(R.id.rl_quanhuyen_bosung)
    LinearLayout rlQuanhuyenBosung;
    @BindView(R.id.tv_tinhtp_new)
    TextView tv_tinhtp_new;
    @BindView(R.id.tv_quanhuyen_new)
    TextView tv_quanhuyen_new;
    @BindView(R.id.tv_xaphuong_new)
    TextView tv_xaphuong_new;
    @BindView(R.id.tv_thoi_gian_du_kien)
    TextView tv_thoi_gian_du_kien;
    @BindView(R.id.tv_diachi_khac)
    LinearLayout tv_diachi_khac;
    @BindView(R.id.ll_thoi_gian_du_kien)
    LinearLayout ll_thoi_gian_du_kien;
    @BindView(R.id.radio_group_new)
    RadioGroup radio_group;
    @BindView(R.id.radio_group_doituong)
    RadioGroup radio_group_doituong;
    @BindView(R.id.tv_Description_new)
    EditText tv_Description_new;
    String ghichunew = "";
    String doiTuong = "";


    List<WardModels> mListXaPhuong = new ArrayList<>();
    List<DistrictModels> mListQuanHuyen = new ArrayList<>();
    int idXaphuong = 0;
    int idQuanhuyen = 0;

    String toPoCode = "";
    private Calendar calDateOfBirth = Calendar.getInstance();
    private Calendar calendar = Calendar.getInstance();
    private Calendar calendarHoanTra = Calendar.getInstance();
    private Calendar calendarDuKien = Calendar.getInstance();
    private Calendar calendarmin = Calendar.getInstance();
    private Calendar calDateAccepted = Calendar.getInstance();
    private XacNhanBaoPhatAdapter adapter;
    private CuocAdapter cAdapter;
    private List<ModeFee> cList;
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
    private boolean isCapture = false;
    private boolean isCaptureVerify = false;
    private boolean isCaptureAvatar = false;
    private boolean isCaptureOther = false;

    private boolean isCaptureDelivery = false;
    private boolean isCaptureRefund = false;
    private boolean isCaptureHoanTra = false;

    //private int authenType = -2;
    private int authenType;
    private List<Item> listImages;
    private List<Item> listImagesAvatar;//
    private List<Item> listImageVerify;
    private List<Item> listImageOther;
    private ImageAdapter imageAvatarAdapter;
    private ImageAdapter imageAdapter;
    private ImageAdapter imageVerifyAdapter;
    private ImageAdapter imageOtherAdapter;

    private List<Item> listImageDelivery;
    private List<Item> listImageRefund;

    private List<Item> listImageHangDoiTra;
    private List<PhithuhoModel> phithuhoModelList;
    private ImageAdapter imageHangDoiTraAdapter;

    private PhiThuHoAdapter phiThuHoAdapter;

    private ImageAdapter imageDeliveryAdapter;
    private ImageAdapter imageRefundAdapter;
    List<ProductModel> listProductDelivery;
    List<ProductModel> listProductRefund;
    List<ProductModel> listProductDeliveryRequest;
    DeliveryPartialAdapter deliveryPartialAdapter;
    DeliveryPartialAdapter refundPartialAdapter;

    private boolean IsExchange;
    private List<LadingProduct> mHoanDoiTra;
    private HangDoiTraAdapter mHoanDoiTraAdapter;

    private RouteInfo routeInfo;
    private BuuCucHuyenMode[] buuCucHuyenMode;
    private UserInfo userInfo;
    private PostOffice postOffice;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private UploadSingleResult uploadSingleResult;
    //private ListReceiverName listReceiverName;
    private List<String> listReceiverName;
    private String receiverName = "";
    private boolean checkInfoClick = false;
    private boolean checkImageClick = false;
    String modePartial = "INIT";
    static final int OPEN_MEDIA_PICKER = 1;
    private long lastClickTime = 0;
    List<BuuCucHuyenMode> list1 = new ArrayList<>();

    private LocationManager mLocationManager;
    private Location mLocation;
    public final String APP_TAG = "DingDong";

    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    String currenImaPath = null;

    String mData = "";
    List<String> mDataList = new ArrayList<>();
    int mTypeTrangThai = 0;
    int mDemTrangThai = 0;
    private int mHour;
    private int mMinute;
    String mBuuCuc = "";
    String mTuyen = "";
    String hinhthucPhat = "";
    String mExchangeLadingCode = "";
    int mExchangeDeliveryTime = 0;
    long mExchangeDeliveryDate = 0;
    List<LadingProduct> mExchangeDetails = new ArrayList<>();
    String imgAnhHoanTra;
    String diachiNew = "";

    private File getImageFile() throws IOException {
        String timeStr = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_" + timeStr + "_";
        File stora = Objects.requireNonNull(getActivity()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageName, ".jpg", stora);
        currenImaPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    public static XacNhanBaoPhatFragment getInstance() {
        return new XacNhanBaoPhatFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xac_nhan_bao_phat;
    }

    @Override
    public void onDisplay() {
        super.onDisplay();

        mLocation = getLastKnownLocation();
        if (mLocation == null) {
            new DialogText(getContext(), "(Không thể hiển thị vị trí. Bạn đã đã bật định vị trên thiết bị chưa?)").show();
            mPresenter.back();
            return;
        }

        for (int i = 0; i < mBaoPhatBangke.size(); i++) {
            double setDeliveryLat = 0.0;
            double setDeliveryLon = 0.0;
            if (mLocation != null) {
                setDeliveryLat = mLocation.getLatitude();
                setDeliveryLon = mLocation.getLongitude();
            }
            mBaoPhatBangke.get(i).setDeliveryLat(setDeliveryLat);
            mBaoPhatBangke.get(i).setDeliveryLon(setDeliveryLon);
        }
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
        }
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
        mLocation = getLastKnownLocation();

        tvRealReceiverName.setText(StringUtils.fromHtml("Tên người nhận: "));
        tvAddressUser.setText(StringUtils.fromHtml("Địa chỉ người sử dụng: " + "<font color=\"red\">*</font>"));
        tvProviders.setText(StringUtils.fromHtml("Nơi cấp: " + "<font color=\"red\">*</font>"));

        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String buuCuchuyen = sharedPref.getString(Constants.KEY_BUU_CUC_HUYEN, "");
        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        hinhthucPhat = "1";
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rad_bc_phat) {
                    hinhthucPhat = "1";
                } else {
                    hinhthucPhat = "2";
                }
            }
        });
        doiTuong = "1";
        radio_group_doituong.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rad_nguoi_gui) {
                    doiTuong = "1";
                } else {
                    doiTuong = "2";
                }
            }
        });
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!routeJson.isEmpty()) {
            mCurrentRouteInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
        }

        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }

        if (!routeJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
        }

        if (!buuCuchuyen.isEmpty()) {
            buuCucHuyenMode = NetWorkController.getGson().fromJson(buuCuchuyen, BuuCucHuyenMode[].class);
            list1 = Arrays.asList(buuCucHuyenMode);
            for (int i = 0; i < list1.size(); i++) {
                if (userInfo.getUnitCode().equals(list1.get(i).getPOCode())) {
                    _tvBuuCuc.setText(list1.get(i).getPOCode() + " - " + list1.get(i).getPOName());
                    toPoCode = userInfo.getUnitCode();
                    break;
                }
            }
        }

        ll_change_route.setVisibility(LinearLayout.GONE);
        ll_confirm_fail.setVisibility(LinearLayout.GONE);
        ll_partial.setVisibility(View.GONE);
        mBaoPhatBangke = mPresenter.getBaoPhatBangke();

        if (mBaoPhatBangke.size() == 1) {
            lledtcod.setVisibility(View.VISIBLE);
            rlXaphuongBosung.setVisibility(View.VISIBLE);
            rlXaphuong.setVisibility(View.VISIBLE);
            tvXaphuong.setText(mBaoPhatBangke.get(0).getDeliveryWardName());
            tvTinhtp.setText(mBaoPhatBangke.get(0).getDeliveryProvinceName());
            tvQuanhuyen.setText(mBaoPhatBangke.get(0).getDeliveryDistrictName());
            if (mBaoPhatBangke.get(0).getDeliveryProvinceId() > 0)
                mPresenter.getQuanHuyen((int) mBaoPhatBangke.get(0).getDeliveryProvinceId());
            if (mBaoPhatBangke.get(0).getDeliveryDistrictId() > 0)
                mPresenter.getXaPhuong((int) mBaoPhatBangke.get(0).getDeliveryDistrictId());
        } else {
            rlXaphuongBosung.setVisibility(View.GONE);
            rlXaphuong.setVisibility(View.GONE);
            rlQuanhuyen.setVisibility(View.GONE);
            rlTinhtp.setVisibility(View.GONE);
            lledtcod.setVisibility(View.GONE);
        }
        checkBoxedtCod.setOnCheckedChangeListener((v1, v2) -> {
            if (v2) {
                llTongTienTamThu.setVisibility(View.VISIBLE);
                ll_tong_tien_tam_thu1.setVisibility(View.VISIBLE);
                new DialogNhaptienCOD(getViewContext(), tvTongTienTamthu.getText().toString().replaceAll("\\.", ""), new IdCallback() {
                    @Override
                    public void onResponse(String id) {
                        if (id.equals("GOI")) {
                            llTongTienTamThu.setVisibility(View.GONE);
                            ll_tong_tien_tam_thu1.setVisibility(View.GONE);
                            mPresenter.callForwardEditCOD(mBaoPhatBangke.get(0).getSenderMobile(), mBaoPhatBangke.get(0).getMaE());
                        } else if (id.equals("false")) {
                            checkBoxedtCod.setChecked(false);
                            tvTongTienTamthu.setText("");
                            llTongTienTamThu.setVisibility(View.GONE);
                            ll_tong_tien_tam_thu1.setVisibility(View.GONE);
                        } else {
                            tvTongTienTamthu.setText(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(id))));
                            tvTongTienTamthu1.setText(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(id) + totalFee)));
                        }
                    }
                }).show();
            } else {
                //flase
                ll_tong_tien_tam_thu1.setVisibility(View.GONE);
                llTongTienTamThu.setVisibility(View.GONE);
            }
        });

        tvTime.setText(DateTimeUtils.convertDateToString(calendarHoanTra.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT));
        tv_thoi_gian_du_kien.setText(DateTimeUtils.convertDateToString(calendarDuKien.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT));
        if (mBaoPhatBangke.size() == 1) {
            cList = new ArrayList();
            if (mBaoPhatBangke.get(0).getFeeCOD() != 0)
                cList.add(new ModeFee("Cước COD: ", mBaoPhatBangke.get(0).getFeeCOD()));
            if (mBaoPhatBangke.get(0).getFeeC() != 0)
                cList.add(new ModeFee("Phí C: ", mBaoPhatBangke.get(0).getFeeC()));
            if (mBaoPhatBangke.get(0).getFeePPA() != 0)
                cList.add(new ModeFee("Cước PPA: ", mBaoPhatBangke.get(0).getFeePPA()));
            if (mBaoPhatBangke.get(0).getFeeCollectLater() != 0)
                cList.add(new ModeFee("Lệ phí HCC: ", mBaoPhatBangke.get(0).getFeeCollectLater()));
            if (mBaoPhatBangke.get(0).getFeeShip() != 0)
                cList.add(new ModeFee("Phí ship: ", mBaoPhatBangke.get(0).getFeeShip()));

            if (mBaoPhatBangke.get(0).getFeePA() != 0)
                cList.add(new ModeFee("Cước thu hộ HCC: ", mBaoPhatBangke.get(0).getFeePA()));


            cAdapter = new CuocAdapter(getContext(), cList);
            RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recyclercuoc);
            recyclercuoc.setAdapter(cAdapter);
        }

        if (mBaoPhatBangke.get(0).

                getIsDOP() == 1) {
            rad_dop1.setChecked(true);
            rad_dop2.setChecked(false);
        } else if (mBaoPhatBangke.get(0).

                getIsDOP() == 2) {
            rad_dop2.setChecked(true);
            rad_dop1.setChecked(false);
        }

        adapter = new

                XacNhanBaoPhatAdapter(getViewContext(), mBaoPhatBangke) {
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
                            final Handler handler = new Handler();

                            updateTotalPackage();
                        });
                    }
                }

        ;
        RecyclerUtils.setupVerticalRecyclerView(

                getViewContext(), recycler);
        recycler.setAdapter(adapter);

        if (

                getItemSelected().

                        size() == 1 ||

                        checkSameAddress()) {
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

        listImageRefund = new ArrayList<>();

        listProductDelivery = new ArrayList<>();
        listProductRefund = new ArrayList<>();
        listProductDeliveryRequest = new ArrayList<>();


        if (mBaoPhatBangke.size() == 1) {
            rad_partial.setVisibility(View.VISIBLE);
            listProductDelivery.addAll(mBaoPhatBangke.get(0).getListProducts());
            for (ProductModel productModel : listProductDelivery) {
                ProductModel productModel1 = new ProductModel();
                productModel1.setQuantity(productModel.getQuantity());
                productModel1.setProductName(productModel.getProductName());
                productModel1.setWeight(productModel.getWeight());
                productModel1.setPrice(productModel.getPrice());
                productModel1.setUnitName(productModel.getUnitName());
                listProductDeliveryRequest.add(productModel1);
            }

            for (ProductModel productModel : listProductDelivery) {
                ProductModel productModel1 = new ProductModel();
                productModel1.setQuantity(0);
                productModel1.setProductName(productModel.getProductName());
                productModel1.setWeight(productModel.getWeight());
                productModel1.setPrice(productModel.getPrice());
                productModel1.setUnitName(productModel.getUnitName());
                listProductRefund.add(productModel1);
            }

            if (listProductDelivery.size() > 0) {
                String content = "";
                int i = 0;
                String join = " - ";
                for (ProductModel productModel : listProductDelivery) {
                    ++i;
                    content += i + ". " + productModel.getProductName() + join + productModel.getQuantity() + "(" + productModel.getUnitName() + ")" + join + productModel.getWeight() + "g" + " Đơn giá:" + NumberUtils.formatAmount(productModel.getPrice()) + join + " Thành tiền: " + NumberUtils.formatAmount(productModel.getAmounts()) + "\n";
                }
                content = content.substring(0, content.length() - 1);
                tv_pt_des.setText(content);
            } else {
                tv_pt_des.setText(mBaoPhatBangke.get(0).getDescription());
                modePartial = "EMPTY";
            }

        } else {
            modePartial = "EMPTY";
            ll_delivery.setWeightSum(3);
            rad_partial.setVisibility(View.GONE);
        }

        recycler_delivery_partial.setLayoutManager(new

                LinearLayoutManager(getContext()));
        recycler_refund_partial.setLayoutManager(new

                LinearLayoutManager(getContext()));

        if (modePartial.equals("EMPTY")) {
            deliveryPartialAdapter = new DeliveryPartialAdapter(getContext(), listProductDelivery, modePartial) {
                @Override
                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);

                    ((HolderView) holder).iv_delete.setOnClickListener(v -> {
                        if (SystemClock.elapsedRealtime() - lastClickTime < 3000) {
                            return;
                        }
                        lastClickTime = SystemClock.elapsedRealtime();
                        listProductDelivery.remove(position);
                        listProductDeliveryRequest.remove(position);
                        deliveryPartialAdapter.removeItem(position);
                        deliveryPartialAdapter.notifyItemRangeChanged(position, listProductDelivery.size());
                    });
                }
            };
            recycler_delivery_partial.setAdapter(deliveryPartialAdapter);

            refundPartialAdapter = new DeliveryPartialAdapter(getContext(), listProductRefund, modePartial) {
                @Override
                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                    ((HolderView) holder).iv_delete.setOnClickListener(v -> {
                        listProductRefund.remove(position);
                        refundPartialAdapter.removeItem(position);
                        refundPartialAdapter.notifyItemRangeChanged(position, listProductRefund.size());
                    });
                }
            };
            recycler_refund_partial.setAdapter(refundPartialAdapter);
        } else {
            iv_add_delivery.setVisibility(View.GONE);
            iv_add_refund.setVisibility(View.GONE);
            deliveryPartialAdapter = new DeliveryPartialAdapter(getContext(), listProductDelivery, "ADD") {
                @Override
                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);

                    ((HolderView) holder).iv_increase.setOnClickListener(v -> {
                        ProductModel productModel = listProductDelivery.get(position);
                        int quantity = Integer.parseInt(((HolderView) holder).tv_quantity.getText().toString());
                        if (quantity < productModel.getQuantity()) {
                            quantity += 1;

                            listProductDeliveryRequest.get(position).setQuantity(quantity);
                            ((HolderView) holder).tv_quantity.setText(quantity + "");

                            int refund = listProductRefund.get(position).getQuantity();
                            refund -= 1;

                            listProductRefund.get(position).setQuantity(refund);
                            refundPartialAdapter.notifyItemChanged(position);
                        }
                    });
                    ((HolderView) holder).iv_decrease.setOnClickListener(v -> {
                        int quantity = Integer.parseInt(((HolderView) holder).tv_quantity.getText().toString());
                        if (quantity > 0) {
                            quantity -= 1;

                            listProductDeliveryRequest.get(position).setQuantity(quantity);
                            ((HolderView) holder).tv_quantity.setText(quantity + "");

                            int refund = listProductRefund.get(position).getQuantity();
                            refund += 1;

                            listProductRefund.get(position).setQuantity(refund);
                            refundPartialAdapter.notifyItemChanged(position);
                        }
                    });
                }
            };
            recycler_delivery_partial.setAdapter(deliveryPartialAdapter);
            refundPartialAdapter = new DeliveryPartialAdapter(getContext(), listProductRefund, "REFUND");
            recycler_refund_partial.setAdapter(refundPartialAdapter);

        }

        imageAvatarAdapter = new

                ImageAdapter(getViewContext(), listImagesAvatar) {
                    @Override
                    public void onBindViewHolder(@NonNull HolderView holder, int position) {
                        super.onBindViewHolder(holder, position);

                        holder.ivDelete.setOnClickListener(view -> {
                            if (SystemClock.elapsedRealtime() - lastClickTime < 3000) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            listImagesAvatar.remove(position);
                            imageAvatarAdapter.notifyItemRemoved(position);
                            imageAvatarAdapter.notifyItemRangeChanged(position, listImagesAvatar.size());
                        });
                    }
                }

        ;
        imageAdapter = new

                ImageAdapter(getViewContext(), listImages) {
                    @Override
                    public void onBindViewHolder(@NonNull HolderView holder, int position) {
                        super.onBindViewHolder(holder, position);

                        holder.ivDelete.setOnClickListener(view -> {
                            if (SystemClock.elapsedRealtime() - lastClickTime < 3000) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            listImages.remove(position);
                            imageAdapter.notifyItemRemoved(position);
                            imageAdapter.notifyItemRangeChanged(position, listImages.size());
                        });
                    }
                }

        ;
        imageVerifyAdapter = new

                ImageAdapter(getViewContext(), listImageVerify) {
                    @Override
                    public void onBindViewHolder(@NonNull HolderView holder, int position) {
                        super.onBindViewHolder(holder, position);

                        holder.ivDelete.setOnClickListener(view -> {
                            if (SystemClock.elapsedRealtime() - lastClickTime < 3000) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            listImageVerify.remove(position);
                            imageVerifyAdapter.notifyItemRemoved(position);
                            imageVerifyAdapter.notifyItemRangeChanged(position, listImageVerify.size());
                        });
                    }
                }

        ;
        imageOtherAdapter = new

                ImageAdapter(getViewContext(), listImageOther) {
                    @Override
                    public void onBindViewHolder(@NonNull HolderView holder, int position) {
                        super.onBindViewHolder(holder, position);
                        holder.ivDelete.setOnClickListener(view -> {
                            if (SystemClock.elapsedRealtime() - lastClickTime < 3000) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            listImageOther.remove(position);
                            imageOtherAdapter.notifyItemRemoved(position);
                            imageOtherAdapter.notifyItemRangeChanged(position, listImageOther.size());
                        });
                    }
                }

        ;
        listImageDelivery = new ArrayList<>();
        imageDeliveryAdapter = new

                ImageAdapter(getViewContext(), listImageDelivery) {
                    @Override
                    public void onBindViewHolder(@NonNull HolderView holder, int position) {
                        super.onBindViewHolder(holder, position);

                        holder.ivDelete.setOnClickListener(view -> {
                            if (SystemClock.elapsedRealtime() - lastClickTime < 3000) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            listImageDelivery.remove(position);
                            imageDeliveryAdapter.notifyItemRemoved(position);
                            imageDeliveryAdapter.notifyItemRangeChanged(position, listImageDelivery.size());
                        });
                    }
                }

        ;
        RecyclerUtils.setupHorizontalRecyclerView(

                getViewContext(), recycler_image_partial_d);
        recycler_image_partial_d.setAdapter(imageDeliveryAdapter);
        imageRefundAdapter = new

                ImageAdapter(getViewContext(), listImageRefund) {
                    @Override
                    public void onBindViewHolder(@NonNull HolderView holder, int position) {
                        super.onBindViewHolder(holder, position);

                        holder.ivDelete.setOnClickListener(view -> {
                            if (SystemClock.elapsedRealtime() - lastClickTime < 3000) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            listImageRefund.remove(position);
                            imageRefundAdapter.notifyItemRemoved(position);
                            imageRefundAdapter.notifyItemRangeChanged(position, listImageRefund.size());
                        });
                    }
                }

        ;


        RecyclerUtils.setupHorizontalRecyclerView(

                getViewContext(), recyclerImageVerifyAvatar);
        RecyclerUtils.setupHorizontalRecyclerView(

                getViewContext(), recyclerViewImage);
        RecyclerUtils.setupHorizontalRecyclerView(

                getViewContext(), recyclerViewImageVerify);
        RecyclerUtils.setupHorizontalRecyclerView(

                getViewContext(), recyclerImageOther);
        RecyclerUtils.setupHorizontalRecyclerView(

                getViewContext(), recycler_image_partial_r);

        recyclerImageVerifyAvatar.setAdapter(imageAvatarAdapter);//
        recyclerViewImage.setAdapter(imageAdapter);
        recyclerViewImageVerify.setAdapter(imageVerifyAdapter);
        recyclerImageOther.setAdapter(imageOtherAdapter);
        recycler_image_partial_r.setAdapter(imageRefundAdapter);

        verifyInfo();

        verifyImage();

        edtDateOfBirth.setText(DateTimeUtils.convertDateToString(calDateOfBirth.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT));
        edtGTTTDateAccepted.setText(DateTimeUtils.convertDateToString(calDateOfBirth.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT));

        checkVerify();

        EditTextUtils.editTextListener(et_pt_amount);


        et_pt_amountlayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et_pt_amount.setText("0");
                tv_pt_amount_r.setText("0");

            }
        });
        et_pt_amount.addTextChangedListener(new

                                                    TextWatcher() {
                                                        @Override
                                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                            et_pt_amount.removeTextChangedListener(this);
                                                            if (!TextUtils.isEmpty(s.toString())) {
                                                                try {
                                                                    if ((totalFee + totalAmount) > 0) {
                                                                        et_pt_amount.setText(NumberUtils.formatVinatti(Long.parseLong(s.toString().replace(".", ""))));
                                                                        tv_pt_amount_r.setText(NumberUtils.formatVinatti((totalFee + totalAmount) - Long.parseLong(s.toString().replace(".", ""))));
                                                                    } else
                                                                        et_pt_amount.setText(NumberUtils.formatVinatti(Long.parseLong(s.toString().replace(".", ""))));
                                                                } catch (Exception ex) {
                                                                    com.ems.dingdong.utiles.Logger.w(ex);
                                                                }
                                                            } else {
                                                                et_pt_amount.setText("0");
                                                                tv_pt_amount_r.setText("0");
                                                            }
                                                            et_pt_amount.addTextChangedListener(this);
                                                            et_pt_amount.setSelection(et_pt_amount.getText().length());
                                                        }

                                                        @Override
                                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                            et_pt_amount.removeTextChangedListener(this);
                                                            if (!TextUtils.isEmpty(s.toString())) {
                                                                try {
                                                                    if ((totalFee + totalAmount) > 0) {
                                                                        et_pt_amount.setText(NumberUtils.formatVinatti(Long.parseLong(s.toString().replace(".", ""))));
                                                                        tv_pt_amount_r.setText(NumberUtils.formatVinatti((totalFee + totalAmount) - Long.parseLong(s.toString().replace(".", ""))));
                                                                    } else
                                                                        et_pt_amount.setText(NumberUtils.formatVinatti(Long.parseLong(s.toString().replace(".", ""))));
                                                                } catch (Exception ex) {
                                                                    com.ems.dingdong.utiles.Logger.w(ex);
                                                                }
                                                            } else {
                                                                et_pt_amount.setText("0");
                                                                tv_pt_amount_r.setText("0");
                                                            }
                                                            et_pt_amount.addTextChangedListener(this);
                                                            et_pt_amount.setSelection(et_pt_amount.getText().length());
                                                        }

                                                        @Override
                                                        public void afterTextChanged(Editable s) {

                                                        }
                                                    });
        recyclerds.setVisibility(View.GONE);
        for (
                int i = 0; i < mBaoPhatBangke.size(); i++) {
            if (mBaoPhatBangke.get(i).getFeeCancelOrder() == 0) {
                mBaoPhatBangke.get(i).setCheck(false);
                mBaoPhatBangke.get(i).setAnItem(false);
                cbSelected.setChecked(false);
                recyclerds.setVisibility(View.VISIBLE);
                break;
            } else {
                cbSelected.setChecked(true);
                mBaoPhatBangke.get(i).setCheck(true);
                mBaoPhatBangke.get(i).setAnItem(true);
                recyclerds.setVisibility(View.VISIBLE);
            }

        }
        for (
                int i = 0; i < mBaoPhatBangke.size(); i++) {
            if (mBaoPhatBangke.get(i).getFeeCancelOrder() == 0) {
                mBaoPhatBangke.get(i).setAnItem(false);
            } else {
                mBaoPhatBangke.get(i).setAnItem(true);
            }
        }

        if (mBaoPhatBangke.size() > 1) for (
                int i = 0; i < mBaoPhatBangke.size(); i++)
            if (mBaoPhatBangke.get(i).

                    getFeeCancelOrder() > 0) mBaoPhatBangke.get(i).

                    setCheckFeeCancelOrder(true);
            else {
                mDemTrangThai++;
                mBaoPhatBangke.get(i).setCheckFeeCancelOrder(false);
            }

        phiThuHoAdapter = new

                PhiThuHoAdapter(getViewContext(), mBaoPhatBangke) {
                    @Override
                    public void onBindViewHolder(@NonNull HolderView holder, int position) {
                        super.onBindViewHolder(holder, position);
                        if (cbSelected.isChecked()) {
                            holder.tv_monney.setEnabled(true);
                        } else holder.tv_monney.setEnabled(false);
                        holder.tv_monney.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                //setting data to array, when changed

                                if (!TextUtils.isEmpty(s.toString())) {
                                    mBaoPhatBangke.get(position).setFeeCancelOrder(Long.parseLong(s.toString().replaceAll("\\.", "")));
                                } else mBaoPhatBangke.get(position).setFeeCancelOrder(0);

                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                //blank
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                //blank
                            }
                        });
                    }
                }

        ;
        RecyclerUtils.setupVerticalRecyclerView(

                getViewContext(), recyclerds);
        recyclerds.setAdapter(phiThuHoAdapter);
        if (mBaoPhatBangke.size() == 1) {
            String gtgt[] = mBaoPhatBangke.get(0).getVatCode().split(",");
            for (int i = 0; i < gtgt.length; i++) {
                if (gtgt[i].equals("RT")) {
                    IsExchange = true;
                    llDoitra.setVisibility(View.VISIBLE);
                    if (!postOfficeJson.isEmpty()) {
                        PostOffice postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
                        tvBuuCucHoantra.setText(String.format("%s %s", postOffice.getCode(), postOffice.getName()));
                        mBuuCuc = postOffice.getCode();
                    }
                    String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
                    if (!routeInfoJson.isEmpty()) {
                        routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
                        tvRouteHoantra.setText(routeInfo.getRouteName());
                        mTuyen = routeInfo.getRouteId();
                    }

                    mHoanDoiTra = new ArrayList<>();
                    mHoanDoiTraAdapter = new HangDoiTraAdapter(getViewContext(), mHoanDoiTra) {
                        @Override
                        public void onBindViewHolder(@NonNull HolderView holder, int position) {
                            super.onBindViewHolder(holder, position);
                            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mHoanDoiTra.remove(position);
                                    mHoanDoiTraAdapter.notifyDataSetChanged();
                                    double t = 0;
                                    for (int i = 0; i < mHoanDoiTra.size(); i++) {
                                        t += mHoanDoiTra.get(i).getAmount();
                                    }
                                    tvHangDoiTra.setText(NumberUtils.formatVinatti(t));
                                }
                            });
                        }
                    };
                    RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recyclerHangDoiTra);
                    recyclerHangDoiTra.setAdapter(mHoanDoiTraAdapter);
                    listImageHangDoiTra = new ArrayList<>();
                    imageHangDoiTraAdapter = new ImageAdapter(getViewContext(), listImageHangDoiTra) {
                        @Override
                        public void onBindViewHolder(@NonNull HolderView holder, int position) {
                            super.onBindViewHolder(holder, position);
                            holder.ivDelete.setOnClickListener(view -> {
                                if (SystemClock.elapsedRealtime() - lastClickTime < 3000) {
                                    return;
                                }
                                lastClickTime = SystemClock.elapsedRealtime();
                                listImageHangDoiTra.remove(position);
                                imageHangDoiTraAdapter.notifyItemRemoved(position);
                                imageHangDoiTraAdapter.notifyItemRangeChanged(position, listImageHangDoiTra.size());
                            });
                        }
                    };
                    RecyclerUtils.setupHorizontalRecyclerView(getViewContext(), recyclerUmageHangDoiTra);
                    recyclerUmageHangDoiTra.setAdapter(imageHangDoiTraAdapter);
                    tvDateHoantra.setText(DateTimeUtils.convertDateToString(calendarHoanTra.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT));
                    tvTiemHoantra.setText(DateTimeUtils.convertDateToString(calendarHoanTra.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT9));

                    mHour = Integer.parseInt(DateTimeUtils.convertDateToString(calendarHoanTra.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT8).split(":")[0]);
                    mMinute = Integer.parseInt(DateTimeUtils.convertDateToString(calendarHoanTra.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT8).split(":")[1]);
//                    Log.d("ASDASDASDD", mHour + ":" + mMinute);
                    break;
                } else {
                    IsExchange = false;
                    llDoitra.setVisibility(View.GONE);
                }
            }
        } else {
            IsExchange = false;
            llDoitra.setVisibility(View.GONE);
        }

    }

    private void showBuuCucden() {
        ArrayList<Item> items = new ArrayList<>();
        int i = 0;
        for (BuuCucHuyenMode item : list1) {
            items.add(new Item(String.valueOf(item.getPOCode()), item.getPOCode() + " - " + item.getPOName()));
            i++;
        }
        new DialogReason(getViewContext(), "Chọn bưu cục đến", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                _tvBuuCuc.setText(item.getText().trim());
                toPoCode = item.getValue();
                if (toPoCode.equals(userInfo.getUnitCode())) {
                    _llChontuyen.setVisibility(View.VISIBLE);
                } else _llChontuyen.setVisibility(View.GONE);
            }
        }).show();
    }

    @SuppressLint("NotifyDataSetChanged")
    @OnClick({R.id.img_back, R.id.img_send, R.id.tv_reason, R.id.tv_solution, R.id.tv_route, R.id.tv_postman, R.id.btn_sign, R.id.rl_relationship, R.id.rl_image_capture, R.id.edt_date_of_birth, R.id.edt_GTTT_date_accepted, R.id.rl_image_capture_verify, R.id.rl_image_capture_avatar, R.id.rl_image_other, R.id.rad_success, R.id.rad_fail, R.id.rad_change_route, R.id.rad_partial, R.id.iv_add_delivery, R.id.iv_add_refund, R.id.rl_image_partial_d, R.id.rl_image_partial_r, R.id.cb_selected, R.id.tv_buu_cuc, R.id.rl_e_wallet, R.id.tv_time, R.id.iv_add_hang_doi_tra, R.id.tv_date_hoantra, R.id.tv_tiem_hoantra, R.id.rl_image_hang_doi_tra, R.id.tv_xaphuong_bosung, R.id.tv_quanhuyen_bosung, R.id.tv_tinhtp_new, R.id.tv_quanhuyen_new, R.id.tv_xaphuong_new, R.id.tv_thoi_gian_du_kien})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tinhtp_new:
                showTinhThanhPho();
                break;
            case R.id.tv_quanhuyen_new:
                showQuanHuyenNew();
                break;
            case R.id.tv_xaphuong_new:
                showXaPhuongNew();
                break;
            case R.id.tv_xaphuong_bosung:
                showXaPhuong();
                break;
            case R.id.tv_quanhuyen_bosung:
                showQuanHuyen();
                break;
            case R.id.rl_e_wallet:
                new DialogNhaptienCOD(getViewContext(), tvTongTienTamthu.getText().toString().replaceAll("\\.", ""), new IdCallback() {
                    @Override
                    public void onResponse(String id) {
                        if (id.equals("GOI")) {
                            mPresenter.callForwardEditCOD(mBaoPhatBangke.get(0).getSenderMobile(), mBaoPhatBangke.get(0).getMaE());
                        } else if (id.equals("false")) {

                        } else {
                            tvTongTienTamthu.setText(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(id))));
                            tvTongTienTamthu1.setText(String.format("%s", NumberUtils.formatPriceNumber(Long.parseLong(id) + totalFee)));
                        }

                    }
                }).show();
                break;
            case R.id.tv_buu_cuc:
                showBuuCucden();
                break;
            case R.id.cb_selected:
                showProgress();
                if (cbSelected.isChecked()) {
                    for (int i = 0; i < mBaoPhatBangke.size(); i++) {
                        if (mBaoPhatBangke.get(i).getFeeCancelOrder() != 0) {
                            mBaoPhatBangke.get(i).setCheck(true);
                        } else mBaoPhatBangke.get(i).setCheck(false);
                        mBaoPhatBangke.get(i).setAnItem(true);
                    }
                    phiThuHoAdapter.notifyDataSetChanged();
                    recyclerds.setVisibility(View.VISIBLE);
                } else {
//                    recyclerds.setVisibility(View.GONE);
                    for (int i = 0; i < mBaoPhatBangke.size(); i++) {
                        mBaoPhatBangke.get(i).setCheck(false);
                    }
                    phiThuHoAdapter.notifyDataSetChanged();
                }

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideProgress();
                    }
                }, 500);

                break;
            case R.id.rad_success:
                rbVerifyInfo.setChecked(false);
                rbVerifyImage.setChecked(false);
                mDeliveryType = 2;
                ll_change_route.setVisibility(LinearLayout.GONE);
                ll_confirm_fail.setVisibility(LinearLayout.GONE);
                ll_partial.setVisibility(View.GONE);
                linearLayoutName.setVisibility(View.VISIBLE);
                rlSogtt.setVisibility(View.VISIBLE);
                rlGhichu.setVisibility(View.VISIBLE);
                rlRelationship.setVisibility(View.VISIBLE);
                if (mBaoPhatBangke.size() == 1) {
                    String gtgt[] = mBaoPhatBangke.get(0).getVatCode().split(",");
                    for (int i = 0; i < gtgt.length; i++) {
                        if (gtgt[i].equals("RT")) {
                            llDoitra.setVisibility(View.VISIBLE);
                            break;
                        } else {
                            llDoitra.setVisibility(View.GONE);
                        }

                    }
                }
                llVerify.setVisibility(View.VISIBLE);
                lledtcod.setVisibility(View.VISIBLE);
                ll_image.setVisibility(View.VISIBLE);

                rad_success.setBackgroundResource(R.drawable.bg_form_success);
                rad_fail.setBackgroundResource(R.color.color_rad_fail);
                rad_change_route.setBackgroundResource(R.color.color_rad_change_route);
                rad_partial.setBackgroundResource(R.color.color_rad_partial);

                rad_success.setTextColor(getResources().getColor(R.color.color_yellow));
                rad_fail.setTextColor(getResources().getColor(R.color.white));
                rad_change_route.setTextColor(getResources().getColor(R.color.white));
                rad_partial.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.rad_fail:
                rbVerifyInfo.setChecked(false);
                rbVerifyImage.setChecked(false);
//                cbSelectedPhathoan.setVisibility(View.GONE);
//                cbSelectedPhathoan.setChecked(false);
                mDeliveryType = 1;
                ll_confirm_fail.setVisibility(LinearLayout.VISIBLE);
                ll_change_route.setVisibility(LinearLayout.GONE);
                ll_partial.setVisibility(View.GONE);
                linearLayoutName.setVisibility(View.VISIBLE);
                llDoitra.setVisibility(View.GONE);
                llVerify.setVisibility(View.GONE);
                ll_image.setVisibility(View.VISIBLE);
                lledtcod.setVisibility(View.GONE);
                rlSogtt.setVisibility(View.GONE);
                rlGhichu.setVisibility(View.GONE);
                rlRelationship.setVisibility(View.GONE);
                rad_success.setBackgroundResource(R.color.color_rad_success);
                rad_fail.setBackgroundResource(R.drawable.bg_form_fail);
                rad_change_route.setBackgroundResource(R.color.color_rad_change_route);
                rad_partial.setBackgroundResource(R.color.color_rad_partial);
                rad_success.setTextColor(getResources().getColor(R.color.white));
                rad_fail.setTextColor(getResources().getColor(R.color.color_yellow));
                rad_change_route.setTextColor(getResources().getColor(R.color.white));
                rad_partial.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.rad_change_route:
                rbVerifyInfo.setChecked(false);
                rbVerifyImage.setChecked(false);
                mDeliveryType = 3;
                ll_partial.setVisibility(View.GONE);
                ll_confirm_fail.setVisibility(LinearLayout.GONE);
                ll_change_route.setVisibility(LinearLayout.VISIBLE);
                linearLayoutName.setVisibility(View.GONE);
                llDoitra.setVisibility(View.GONE);
                llVerify.setVisibility(View.GONE);
                ll_image.setVisibility(View.VISIBLE);
                lledtcod.setVisibility(View.GONE);
                rad_fail.setBackgroundResource(R.color.color_rad_fail);
                rad_success.setBackgroundResource(R.color.color_rad_success);
                rad_change_route.setBackgroundResource(R.drawable.bg_form_change_route);
                rad_partial.setBackgroundResource(R.color.color_rad_partial);

                rad_success.setTextColor(getResources().getColor(R.color.white));
                rad_fail.setTextColor(getResources().getColor(R.color.white));
                rad_change_route.setTextColor(getResources().getColor(R.color.color_yellow));
                rad_partial.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.rad_partial:
                rbVerifyInfo.setChecked(false);
                rbVerifyImage.setChecked(false);
                mDeliveryType = 4;
                ll_confirm_fail.setVisibility(LinearLayout.GONE);
                ll_change_route.setVisibility(LinearLayout.GONE);
                ll_partial.setVisibility(View.VISIBLE);
                linearLayoutName.setVisibility(View.VISIBLE);
                llDoitra.setVisibility(View.GONE);
                // hinh anh vaf thong tin
                llVerify.setVisibility(View.VISIBLE);

                ll_image.setVisibility(View.GONE);

                lledtcod.setVisibility(View.GONE);
                rlSogtt.setVisibility(View.VISIBLE);
                rlGhichu.setVisibility(View.VISIBLE);
                rlRelationship.setVisibility(View.VISIBLE);
                rad_success.setBackgroundResource(R.color.color_rad_success);
                rad_fail.setBackgroundResource(R.color.color_rad_fail);
                rad_change_route.setBackgroundResource(R.color.color_rad_change_route);
                rad_partial.setBackgroundResource(R.drawable.bg_form_parital);

                rad_success.setTextColor(getResources().getColor(R.color.white));
                rad_fail.setTextColor(getResources().getColor(R.color.white));
                rad_change_route.setTextColor(getResources().getColor(R.color.white));
                rad_partial.setTextColor(getResources().getColor(R.color.color_yellow));
                break;
            case R.id.img_back:
                finishView();
                break;
            case R.id.img_send:
                if (SystemClock.elapsedRealtime() - lastClickTime < 3000) {
                    Toast.showToast(getViewContext(), "Bạn thao tác quá nhanh");
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                if (checkInfoClick) {
                    if (TextUtils.isEmpty(edtGTTTDateAccepted.getText()) || TextUtils.isEmpty(edtGTTTLocatedAccepted.getText()) || TextUtils.isEmpty(edtDateOfBirth.getText()) || TextUtils.isEmpty(tvGTTT.getText()) || TextUtils.isEmpty(edtUserAddress.getText())) {
                        if (TextUtils.isEmpty(tvGTTT.getText())) {
                            showErrorToast("Vui lòng nhập Số GTTT");
                            return;
                        }
                        if (TextUtils.isEmpty(edtGTTTDateAccepted.getText())) {
                            showErrorToast("Vui lòng nhập Ngày cấp");
                            return;
                        }
                        if (TextUtils.isEmpty(edtDateOfBirth.getText())) {
                            showErrorToast("Vui lòng nhập Ngày tháng năm sinh");
                            return;
                        }
                        if (TextUtils.isEmpty(edtUserAddress.getText())) {
                            showErrorToast("Vui lòng nhập Địa chỉ người sử dụng");
                            return;
                        }
                        if (TextUtils.isEmpty(edtGTTTLocatedAccepted.getText())) {
                            showErrorToast("Vui lòng nhập Nơi cấp");
                            return;
                        }

                    } else {
                        submit();
                    }
                } else {
                    submit();
                }
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
            case R.id.rl_image_capture_avatar:
                if (imageAvatarAdapter.getListFilter().size() < 1) {
                    new DiallogChonAnh(getViewContext(), new ChonAnhCallback() {
                        @Override
                        public void onResponse(int type) {
                            if (type == 2) {
                                setIsCapture("AVATAR");
                            } else {
                                pickImage("AVATAR", 1);
                            }
                        }
                    }).show();

                } else {
                    showErrorToast(getString(R.string.do_not_allow_take_over_one_photos));
                }
                break;

            case R.id.rl_image_other:
                if (imageOtherAdapter.getListFilter().size() < 5) {
                    new DiallogChonAnh(getViewContext(), new ChonAnhCallback() {
                        @Override
                        public void onResponse(int type) {
                            if (type == 2) {
                                setIsCapture("OTHER");
                            } else pickImage("OTHER", 5);
                        }
                    }).show();
                } else {
                    showErrorToast(getString(R.string.do_not_allow_take_over_five_photos));
                }
                break;

            case R.id.rl_image_capture:
                if (imageAdapter.getListFilter().size() < 3) {
                    new DiallogChonAnh(getViewContext(), new ChonAnhCallback() {
                        @Override
                        public void onResponse(int type) {
                            if (type == 2) {
                                setIsCapture("DEFAULT");
                            } else pickImage("DEFAULT", 3);
                        }
                    }).show();
                } else {
                    showErrorToast(getViewContext().getString(R.string.do_not_allow_take_over_three_photos));
                }
                break;

            case R.id.rl_image_capture_verify:
                if (imageVerifyAdapter.getListFilter().size() < 2) {
                    new DiallogChonAnh(getViewContext(), new ChonAnhCallback() {
                        @Override
                        public void onResponse(int type) {
                            if (type == 2) {
                                setIsCapture("VERIFY");
                            } else pickImage("VERIFY", 2);
                        }
                    }).show();
                } else {
                    showErrorToast(getString(R.string.do_not_allow_take_over_two_photos));
                }
                break;
            case R.id.rl_image_partial_d:
                if (listImageDelivery.size() < 3)
                    new DiallogChonAnh(getViewContext(), new ChonAnhCallback() {
                        @Override
                        public void onResponse(int type) {
                            if (type == 2) {
                                setIsCapture("PARTIAL_D");
                            } else pickImage("PARTIAL_D", 10);
                        }
                    }).show();
                else {
                    showErrorToast(getString(R.string.do_not_allow_take_over_three_photos));
                }
                break;
            case R.id.rl_image_partial_r:
                if (listImageRefund.size() < 3) {
                    new DiallogChonAnh(getViewContext(), new ChonAnhCallback() {
                        @Override
                        public void onResponse(int type) {
                            if (type == 2) {
                                setIsCapture("PARTIAL_R");
                            } else pickImage("PARTIAL_R", 3);
                        }
                    }).show();
                } else {
                    showErrorToast(getString(R.string.do_not_allow_take_over_three_photos));
                }
                break;
            case R.id.edt_date_of_birth:
                new SpinnerDatePickerDialogBuilder().context(getViewContext()).callback((view1, year, monthOfYear, dayOfMonth) -> {
                    calDateOfBirth.set(year, monthOfYear, dayOfMonth);
                    edtDateOfBirth.setText(DateTimeUtils.convertDateToString(calDateOfBirth.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT));
                }).spinnerTheme(R.style.DatePickerSpinner).showTitle(true).showDaySpinner(true).defaultDate(calDateOfBirth.get(Calendar.YEAR), calDateOfBirth.get(Calendar.MONTH), calDateOfBirth.get(Calendar.DAY_OF_MONTH)).maxDate(calDateOfBirth.get(Calendar.YEAR), calDateOfBirth.get(Calendar.MONTH), calDateOfBirth.get(Calendar.DAY_OF_MONTH)).minDate(1600, 0, 1).build().show();
                break;
            case R.id.tv_time:
                new SpinnerDatePickerDialogBuilder().context(getViewContext()).callback((view1, year, monthOfYear, dayOfMonth) -> {
                    calendarHoanTra.set(year, monthOfYear, dayOfMonth);
                    tvTime.setText(DateTimeUtils.convertDateToString(calendarHoanTra.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT));
                }).spinnerTheme(R.style.DatePickerSpinner).showTitle(true).showDaySpinner(true).defaultDate(calendarHoanTra.get(Calendar.YEAR), calendarHoanTra.get(Calendar.MONTH), calendarHoanTra.get(Calendar.DAY_OF_MONTH)).maxDate(6000, 12, 31).minDate(calendarmin.get(Calendar.YEAR), calendarmin.get(Calendar.MONTH), calendarmin.get(Calendar.DAY_OF_MONTH)).build().show();
                break;
            case R.id.tv_thoi_gian_du_kien:
                new SpinnerDatePickerDialogBuilder().context(getViewContext()).callback((view1, year, monthOfYear, dayOfMonth) -> {
                    calendarDuKien.set(year, monthOfYear, dayOfMonth);
                    tv_thoi_gian_du_kien.setText(DateTimeUtils.convertDateToString(calendarDuKien.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT));
                }).spinnerTheme(R.style.DatePickerSpinner).showTitle(true).showDaySpinner(true).defaultDate(calendarDuKien.get(Calendar.YEAR), calendarDuKien.get(Calendar.MONTH), calendarDuKien.get(Calendar.DAY_OF_MONTH)).maxDate(6000, 12, 31).minDate(calendarDuKien.get(Calendar.YEAR), calendarDuKien.get(Calendar.MONTH), calendarDuKien.get(Calendar.DAY_OF_MONTH)).build().show();
                break;
//            case R.id.tv_date_hoantra:
//                new SpinnerDatePickerDialogBuilder()
//                        .context(getViewContext())
//                        .callback((view1, year, monthOfYear, dayOfMonth) -> {
//                            calendarHoanTra.set(year, monthOfYear, dayOfMonth);
//                            tvDateHoantra.setText(DateTimeUtils
//                                    .convertDateToString(calendarHoanTra.getTime(),
//                                            DateTimeUtils.SIMPLE_DATE_FORMAT));
//                        })
//                        .spinnerTheme(R.style.DatePickerSpinner)
//                        .showTitle(true)
//                        .showDaySpinner(true)
//                        .defaultDate(calendarHoanTra.get(Calendar.YEAR),
//                                calendarHoanTra.get(Calendar.MONTH),
//                                calendarHoanTra.get(Calendar.DAY_OF_MONTH))
//                        .maxDate(6000, 12, 31)
//                        .minDate(2000, 1, 1)
//                        .build()
//                        .show();
//                break;
//            case R.id.tv_tiem_hoantra:
//                android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(getViewContext(),
//                        android.R.style.Theme_Holo_Light_Dialog, new android.app.TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        mHour = hourOfDay;
//                        mMinute = minute;
//                        String mHours = "";
//                        String mMinutes = "";
//                        if (mHour < 10)
//                            mHours = "0" + mHour;
//                        else mHours = mHour + "";
//                        if (mMinute < 10)
//                            mMinutes = "0" + mMinute;
//                        else mMinutes = mMinute + "";
////                        if (mHour > 12) {
////                            tvTiemHoantra.setText(String.format("%s:%s PM", mHour - 12, mMinute));
////                        } else {
//                        tvTiemHoantra.setText(String.format("%s:%s:00", mHours, mMinutes));
////                        }
//                    }
//                }, mHour, mMinute, true);
//                timePickerDialog.show();
//                break;
            case R.id.edt_GTTT_date_accepted:
                new SpinnerDatePickerDialogBuilder().context(getViewContext()).callback((view1, year, monthOfYear, dayOfMonth) -> {
                    calDateAccepted.set(year, monthOfYear, dayOfMonth);
                    edtGTTTDateAccepted.setText(DateTimeUtils.convertDateToString(calDateAccepted.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT));
                }).spinnerTheme(R.style.DatePickerSpinner).showTitle(true).showDaySpinner(true).defaultDate(calDateAccepted.get(Calendar.YEAR), calDateAccepted.get(Calendar.MONTH), calDateAccepted.get(Calendar.DAY_OF_MONTH)).maxDate(calDateAccepted.get(Calendar.YEAR), calDateAccepted.get(Calendar.MONTH), calDateAccepted.get(Calendar.DAY_OF_MONTH)).minDate(1600, 0, 1).build().show();
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
            case R.id.iv_add_delivery:
                productAdd("D");
                break;
            case R.id.iv_add_refund:
                productAdd("R");
                break;
            case R.id.iv_add_hang_doi_tra:
                new AddHangDoiTraDialog(getViewContext(), new HangDoiTraCallback() {
                    @Override
                    public void OnResponse(LadingProduct hangDoiTraMode) {
                        mHoanDoiTra.add(hangDoiTraMode);
                        mHoanDoiTraAdapter.notifyDataSetChanged();
                        double t = 0;
                        for (int i = 0; i < mHoanDoiTra.size(); i++) {
                            t += mHoanDoiTra.get(i).getAmount();
                        }
                        tvHangDoiTra.setText(NumberUtils.formatVinatti(t));
                    }
                }).show();
                break;
            case R.id.rl_image_hang_doi_tra:
                if (imageHangDoiTraAdapter.getListFilter().size() < 3) {
                    new DiallogChonAnh(getViewContext(), new ChonAnhCallback() {
                        @Override
                        public void onResponse(int type) {
                            if (type == 2) {
                                setIsCapture("HOANTRA");
                            } else pickImage("HOANTRA", 3);
                        }
                    }).show();
                } else {
                    showErrorToast(getViewContext().getString(R.string.do_not_allow_take_over_three_photos));
                }
                break;
        }
    }


    @SuppressLint("IntentReset")
    private void chooseFromGallery() {
        @SuppressLint("IntentReset") Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, OPEN_MEDIA_PICKER);
//        }


    }

    public void pickImage(String type, int max) {
        switch (type) {
            case "AVATAR":
                isCaptureHoanTra = false;
                isCaptureAvatar = true;
                isCaptureVerify = false;
                isCapture = false;
                isCaptureOther = false;
                isCaptureDelivery = false;
                isCaptureRefund = false;
//                startActivityForResult(intent, OPEN_MEDIA_PICKER);
                break;
            case "OTHER":
                isCaptureHoanTra = false;
                isCaptureOther = true;
                isCaptureAvatar = false;
                isCaptureVerify = false;
                isCapture = false;
                isCaptureDelivery = false;
                isCaptureRefund = false;
//                startActivityForResult(intent, OPEN_MEDIA_PICKER);
                break;
            case "DEFAULT":
                isCaptureHoanTra = false;
                isCapture = true;
                isCaptureAvatar = false;
                isCaptureVerify = false;
                isCaptureOther = false;
                isCaptureDelivery = false;
                isCaptureRefund = false;
//                startActivityForResult(intent, OPEN_MEDIA_PICKER);
                break;
            case "VERIFY":
                isCaptureHoanTra = false;
                isCaptureVerify = true;
                isCaptureAvatar = false;
                isCapture = false;
                isCaptureOther = false;
                isCaptureDelivery = false;
                isCaptureRefund = false;
//                startActivityForResult(intent, OPEN_MEDIA_PICKER);
                break;
            case "PARTIAL_D":
                isCaptureHoanTra = false;
                isCaptureDelivery = true;
                isCaptureAvatar = false;
                isCaptureVerify = false;
                isCapture = false;
                isCaptureOther = false;
                isCaptureRefund = false;
//                startActivityForResult(intent, OPEN_MEDIA_PICKER);
                break;
            case "PARTIAL_R":
                isCaptureHoanTra = false;
                isCaptureRefund = true;
                isCaptureDelivery = false;
                isCaptureAvatar = false;
                isCaptureVerify = false;
                isCapture = false;
                isCaptureOther = false;
//                startActivityForResult(intent, OPEN_MEDIA_PICKER);
                break;

            case "HOANTRA":
                isCaptureHoanTra = true;
                isCaptureRefund = false;
                isCaptureDelivery = false;
                isCaptureAvatar = false;
                isCaptureVerify = false;
                isCapture = false;
                isCaptureOther = false;
                break;
        }

        chooseFromGallery();

    }

    private final static int CAMERA_RQ = 6969;

    void setIsCapture(String type) {
        Log.d("AAAAASDASDASD", new Gson().toJson(listImageHangDoiTra));
        switch (type) {
            case "AVATAR":
                isCaptureHoanTra = false;
                isCaptureAvatar = true;
                isCaptureVerify = false;
                isCapture = false;
                isCaptureOther = false;
                isCaptureDelivery = false;
                isCaptureRefund = false;
                break;
            case "OTHER":
                isCaptureHoanTra = false;
                isCaptureOther = true;
                isCaptureAvatar = false;
                isCaptureVerify = false;
                isCapture = false;
                isCaptureDelivery = false;
                isCaptureRefund = false;
                break;
            case "DEFAULT":
                isCaptureHoanTra = false;
                isCapture = true;
                isCaptureAvatar = false;
                isCaptureVerify = false;
                isCaptureOther = false;
                isCaptureDelivery = false;
                isCaptureRefund = false;
                break;
            case "VERIFY":
                isCaptureHoanTra = false;
                isCaptureVerify = true;
                isCaptureAvatar = false;
                isCapture = false;
                isCaptureOther = false;
                isCaptureDelivery = false;
                isCaptureRefund = false;
                break;
            case "PARTIAL_D":
                isCaptureHoanTra = false;
                isCaptureDelivery = true;
                isCaptureAvatar = false;
                isCaptureVerify = false;
                isCapture = false;
                isCaptureOther = false;
                isCaptureRefund = false;
                break;
            case "PARTIAL_R":
                isCaptureHoanTra = false;
                isCaptureRefund = true;
                isCaptureDelivery = false;
                isCaptureAvatar = false;
                isCaptureVerify = false;
                isCapture = false;
                isCaptureOther = false;
                break;
            case "HOANTRA":
                isCaptureHoanTra = true;
                isCaptureRefund = false;
                isCaptureDelivery = false;
                isCaptureAvatar = false;
                isCaptureVerify = false;
                isCapture = false;
                isCaptureOther = false;
                break;
        }
//        TakePictureIntent();
        MediaUltisV1.captureImage(this);
//        dispatchTakePictureIntent();
    }

    void productAdd(String type) {
        new CreateDeliveryParialDialog(getContext(), v -> {
            if (type.equals("D")) {
                listProductDelivery.add(v);
                listProductDeliveryRequest.add(v);
                deliveryPartialAdapter.setItems(listProductDelivery);
            } else {
                listProductRefund.add(v);
                refundPartialAdapter.setItems(listProductRefund);
            }
        }).show();
    }

    private void submit() {
        String[] arrImages = new String[listImages.size()];
        for (int i = 0; i < listImages.size(); i++) {
            if (!TextUtils.isEmpty(listImages.get(i).getText()))
                arrImages[i] = listImages.get(i).getText();
        }
        mFile = TextUtils.join(";", arrImages);

        List<DeliveryPostman> listSelected = getItemSelected();
        if (listSelected.size() == 0) {
            showErrorToast(getViewContext().getString(R.string.you_have_not_pick_any_package));
            return;
        }

        if (mDeliveryType == 2 || mDeliveryType == 4) {
            boolean isCanVerify = canVerify();
            if (!isCanVerify) {
                showErrorToast(getViewContext().getString(R.string.there_is_one_package_needed_to_particular_delivered));
                return;
            }


            if (!tvGTTT.getText().toString().equals("") && tvGTTT.getText().toString().length() < 8) {
                Toast.showToast(getViewContext(), "Vui lòng nhập số GTTT tối thiểu 8 ký tự");
                return;
            }
            // 0 ko chon gi // 1 thong tin // 2 hinh anh / 3 ca haim
            if (rbVerifyImage.isChecked() && rbVerifyInfo.isChecked()) authenType = 3;
            else if (rbVerifyInfo.isChecked()) authenType = 1;
            else if (rbVerifyImage.isChecked()) authenType = 2;
            if (llVerifyInfo.getVisibility() == View.VISIBLE || llVerifyImage.getVisibility() == View.VISIBLE) {//llCaptureVerify -> llVerifyImage
                if (authenType == 1 && !checkInfo(authenType)) {
                    return;
                } else if (authenType == 2 && !checkImage(authenType)) {
                    return;
                } else if (authenType == 0) {
                    return;
                } else if (authenType == 3) {
                    if (!checkImage(authenType) || !checkInfo(authenType)) {
                        showErrorToast(getViewContext().getString(R.string.you_must_take_verify_photos_or_enter_enough_verification_info));
                        return;
                    }
                } else if (authenType == 3 && (!checkInfo(authenType) || !checkImage(authenType))) {
                    return;
                }
            }

            if ((TextUtils.isEmpty(edtOtherRelationship.getText()) || Utils.isBlank(edtOtherRelationship.getText().toString())) && edtRelationship.getText().equals(getViewContext().getString(R.string.other))) {
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

            long _amountShow = 0;
            if (mDeliveryType == 4) {
                if (totalAmount > 0) {
                    if (TextUtils.isEmpty(et_pt_amount.getText())) {
                        Toast.showToast(getContext(), "Bạn chưa nhập số tiền COD phát");
                        return;
                    } else {
                        long amount = Long.parseLong(et_pt_amount.getText().toString().replaceAll("\\.", ""));
                        if (amount > totalAmount) {
                            Toast.showToast(getContext(), "Số tiền COD phát lớn hơn tổng tiền COD");
                            return;
                        }
                        _amountShow = amount;
                    }
                } else {
                    if (!TextUtils.isEmpty(et_pt_amount.getText())) {
                        long amount = Long.parseLong(et_pt_amount.getText().toString().replaceAll("\\.", ""));
                        if (amount > totalAmount) {
                            Toast.showToast(getContext(), "Số tiền COD phát lớn hơn tổng tiền COD");
                            return;
                        }
                    }
                }
            } else _amountShow = totalAmount;

            if (TextUtils.isEmpty(tvTongTienTamthu.getText().toString())) tiem_tam = 0;
            else
                tiem_tam = Long.parseLong(tvTongTienTamthu.getText().toString().replaceAll("\\.", ""));

            if (checkBoxedtCod.isChecked()) {
                _amountShow = tiem_tam;
            } else tiem_tam = 0;

            if (IsExchange) {
                if (!TextUtils.isEmpty(edtMaBuuGui.getText().toString().trim()) && !edtMaBuuGui.getText().toString().trim().matches("[\\w-]+")) {
                    Toast.showToast(getContext(), "Vui lòng kiểm tra mã bưu gửi");
                    return;
                }

                mExchangeLadingCode = edtMaBuuGui.getText().toString().trim();
                mExchangeDeliveryDate = Long.parseLong(DateTimeUtils.convertDateToString(calendarHoanTra.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
                String mHours = "";
                String mMinutes = "";
                if (mHour < 10) mHours = "0" + mHour;
                else mHours = mHour + "";
                if (mMinute < 10) mMinutes = "0" + mMinute;
                else mMinutes = mMinute + "";
                mExchangeDeliveryTime = Integer.parseInt(String.format("%s%s00", mHours, mMinutes));
                mExchangeDetails.clear();
                mExchangeDetails.addAll(mHoanDoiTra);
//                if (listImageHangDoiTra.size() > 0) {
//                    if (mHoanDoiTra.size() == 0) {
//                        Toast.showToast(getViewContext(), "Vui lòng nhập sản phẩm hàng hóa");
//                        return;
//                    }
//                }
//                if (mHoanDoiTra.size() == 0) {
//                    Toast.showToast(getViewContext(), "Vui lòng nhập sản phẩm hàng hóa");
//                    return;
//                }
                if (listImageHangDoiTra.size() > 0) {
                    String[] arrImg = new String[listImageHangDoiTra.size()];
                    for (int i = 0; i < listImageHangDoiTra.size(); i++) {
                        if (!TextUtils.isEmpty(listImageHangDoiTra.get(i).getText()))
                            arrImg[i] = listImageHangDoiTra.get(i).getText();
                    }
                    imgAnhHoanTra = TextUtils.join(";", arrImg);
                }
            }
            long final_amountShow = _amountShow;


            new ConfirmDialog(getViewContext(), listSelected.size(), final_amountShow, totalFee).setOnCancelListener(Dialog::dismiss).setOnOkListener(confirmDialog -> {
                confirmDialog.dismiss();
                InfoVerify infoVerify = new InfoVerify();//llVerifyImage.setVisibility(View.VISIBLE);
                if (llVerifyInfo.getVisibility() == View.VISIBLE || llVerifyImage.getVisibility() == View.VISIBLE) {//llCaptureVerify -> llVerifyImage
                    infoVerify.setReceiverPIDWhere(edtGTTTLocatedAccepted.getText().toString());
                    infoVerify.setReceiverAddressDetail(edtUserAddress.getText().toString());
                    infoVerify.setReceiverPIDDate(edtGTTTDateAccepted.getText().toString());
                    infoVerify.setReceiverBirthday(edtDateOfBirth.getText().toString());
                    infoVerify.setGtgt(tvGTTT.getText().toString());
                    if (authenType == 0) infoVerify.setAuthenType(3);
                    else infoVerify.setAuthenType(authenType);
                }
                // baophat thanh cong
                if (mDeliveryType == 2) {
                    if (!TextUtils.isEmpty(edtOtherRelationship.getText())) {
                        mPresenter.paymentDelivery(mFile, mFileAvatar + ";" + mFileVerify + ";" + mFileOther, mSign, edtReceiverName.getText().toString(), edtOtherRelationship.getText().toString(), infoVerify, checkBoxedtCod.isChecked(), tiem_tam, edtNote.getText().toString(), IsExchange, mBuuCuc, mTuyen, mExchangeLadingCode, mExchangeDeliveryDate, mExchangeDeliveryTime, mExchangeDetails, imgAnhHoanTra, idXaphuong, idQuanhuyen, phoneCodeEdit.getId());
                    } else {
                        mPresenter.paymentDelivery(mFile, mFileAvatar + ";" + mFileVerify + ";" + mFileOther, mSign, edtReceiverName.getText().toString(), edtRelationship.getText().toString(), infoVerify, checkBoxedtCod.isChecked(), tiem_tam, edtNote.getText().toString(), IsExchange, mBuuCuc, mTuyen, mExchangeLadingCode, mExchangeDeliveryDate, mExchangeDeliveryTime, mExchangeDetails, imgAnhHoanTra, idXaphuong, idQuanhuyen, phoneCodeEdit.getId());
                    }
                } else {
                    if (totalAmount > 0) {
                        int amount = Integer.parseInt(et_pt_amount.getText().toString().replaceAll("\\.", ""));
                        if (!TextUtils.isEmpty(et_pt_amount.getText())) {
                            if (amount <= totalAmount) deliveryPartial(infoVerify, amount);
                            else
                                Toast.showToast(getContext(), "Số tiền COD phát lớn hơn tổng tiền COD");
                        } else Toast.showToast(getContext(), "Bạn chưa nhập số tiền COD phát");
                    } else deliveryPartial(infoVerify, 0);
                }
            }).setWarning(getViewContext().getString(R.string.are_you_sure_deliver_successfully)).show();
        } else if (mDeliveryType == 1) {
            if (TextUtils.isEmpty(tv_reason.getText())) {
                Toast.showToast(tv_reason.getContext(), getViewContext().getString(R.string.please_pick_reason));
                return;
            }
            if (TextUtils.isEmpty(tv_solution.getText())) {
                Toast.showToast(tv_solution.getContext(), getViewContext().getString(R.string.you_have_not_chosen_solution));
                return;
            }
            if (TextUtils.isEmpty(tvTime.getText())) {
                Toast.showToast(tvTime.getContext(), getViewContext().getString(R.string.you_have_not_time));
                return;
            }
            List<DeliveryPostman> deliveryPostmen = getItemSelected();
            if (cbSelected.isChecked()) {
                if (deliveryPostmen.size() > 1) {
                    for (int i = 0; i < deliveryPostmen.size(); i++) {
                        if (deliveryPostmen.get(i).getFeeCancelOrder() < 1000) {
                            Toast.showToast(getViewContext(), "Vui lòng nhập số tiền tối thiểu là 1,000 đồng");
                            return;
                        }
                    }
                } else {
                    if (deliveryPostmen.get(0).getFeeCancelOrder() < 1000) {
                        Toast.showToast(getViewContext(), "Vui lòng nhập số tiền tối thiểu là 1,000 đồng");
                        return;
                    }
                }
            }
            String time = TimeUtils.convertDateToString(calendarHoanTra.getTime(), TimeUtils.DATE_FORMAT_18).replaceAll("/", "");
            long t = 0;
            if (cbSelected.isChecked()) {
                for (int i = 0; i < deliveryPostmen.size(); i++)
                    t += deliveryPostmen.get(i).getFeeCancelOrder();
            } else {
                for (int i = 0; i < deliveryPostmen.size(); i++)
                    if (deliveryPostmen.get(i).getFeeCancelOrder() > 0)
                        t += deliveryPostmen.get(i).getFeeCancelOrder();
            }

            String mess = null;

            mess = "Danh sách bưu gửi có Tổng phí hủy đơn hàng: " + "<font color=\"red\">" + String.format("%s", NumberUtils.formatPriceNumber(t)) + "</font>" + " VNĐ, bạn có thu đủ tiền hay không?";
            String timeDukien = TimeUtils.convertDateToString(calendarDuKien.getTime(), TimeUtils.DATE_FORMAT_18).replaceAll("/", "");

            if (mReasonInfo.getCode().equals("02")) {
                if (mSolutionInfo.getCode().equals("C") || mSolutionInfo.getCode().equals("I") || mSolutionInfo.getCode().equals("A") || mSolutionInfo.getCode().equals("R")) {
                    if (idTinhnew == 0) {
                        tv_tinhtp_new.setTextColor(getViewContext().getResources().getColor(R.color.red));
                        tv_tinhtp_new.setText("Vui lòng chọn Tỉnh/ Thành Phố");
//                        Toast.showToast(getViewContext(), "Vui lòng chọn địa chỉ mới");
                        return;
                    }
                    if (idQuanHuyenNew == 0) {
                        tv_quanhuyen_new.setTextColor(getViewContext().getResources().getColor(R.color.red));
                        tv_quanhuyen_new.setText("Vui lòng chọn Quận/Huyện");
//                        tv_quanhuyen_new.setError("Vui lòng chọn ");
//                        Toast.showToast(getViewContext(), "Vui lòng chọn địa chỉ mới");
                        return;
                    }
                    if (idXaPhuongNew == 0) {
                        tv_xaphuong_new.setTextColor(getViewContext().getResources().getColor(R.color.red));
                        tv_xaphuong_new.setText("Vui lòng chọn Xã/ Phường");
//                        tv_xaphuong_new.setError("Vui lòng chọn Xã/ Phường");
//                        Toast.showToast(getViewContext(), "Vui lòng chọn địa chỉ mới");
                        return;
                    }
                    if (tv_Description_new.getText().toString().trim().isEmpty()) {
                        Toast.showToast(getViewContext(), "Vui lòng nhập ghi chú");
                        tv_Description_new.requestFocus();
                        return;
                    }
                    diachiNew = "";
                    diachiNew = idTinhnew + ";" + idQuanHuyenNew + ";" + idXaPhuongNew;
                    ghichunew = tv_Description_new.getText().toString().trim();
                    timeDukien = "0";
                    doiTuong = "";
                }
            }
            if (mReasonInfo.getCode().equals("71")) {
                if (mSolutionInfo.getCode().equals("A") || mSolutionInfo.getCode().equals("I") || mSolutionInfo.getCode().equals("R")) {
                    diachiNew = "";
                    ghichunew = "";
                    hinhthucPhat = "";
                }
            }

            if (cbSelected.isChecked()) {
                String finalTimeDukien = timeDukien;
                String finalTimeDukien1 = timeDukien;
                new ConfirmPhiHuyDonHangDialog(getViewContext(), listSelected.size(), t).setOnCallBacklClickListener(confirmDialog -> {
                    confirmDialog.dismiss();
                }).setOnCancelListener((ConfirmPhiHuyDonHangDialog.OnCancelClickListener) confirmDialog1 -> {
                    confirmDialog1.dismiss();
                    new DialogNhapKhongThanhCong(getViewContext(), new IdCallback() {
                        @Override
                        public void onResponse(String id) {
                            mPresenter.submitToPNS(mReasonInfo.getCode(), mSolutionInfo.getCode(), tv_Description.getText().toString(), mFile, mFileAvatar + ";" + mFileVerify + ";" + mFileOther, mSign, time, false, id, idXaphuong, idQuanhuyen, diachiNew, hinhthucPhat, ghichunew, doiTuong, Integer.parseInt(finalTimeDukien));
                        }
                    }).show();
                }).setOnOkListener(confirmDialog -> {
                    confirmDialog.dismiss();
                    mPresenter.submitToPNS(mReasonInfo.getCode(), mSolutionInfo.getCode(), tv_Description.getText().toString(), mFile, mFileAvatar + ";" + mFileVerify + ";" + mFileOther, mSign, time, true, "", idXaphuong, idQuanhuyen, diachiNew, hinhthucPhat, ghichunew, doiTuong, Integer.parseInt(finalTimeDukien1));
                }).setWarning(mess).show();

            } else {
                String finalTimeDukien2 = timeDukien;
                new ConfirmPKTCDialog(getViewContext(), listSelected.size()).setOnCancelListener(Dialog::dismiss).setOnOkListener(confirmDialog -> {
                    confirmDialog.dismiss();
                    mPresenter.submitToPNS(mReasonInfo.getCode(), mSolutionInfo.getCode(), tv_Description.getText().toString(), mFile, mFileAvatar + ";" + mFileVerify + ";" + mFileOther, mSign, time, false, "", idXaphuong, idQuanhuyen, diachiNew, hinhthucPhat, ghichunew, doiTuong, Integer.parseInt(finalTimeDukien2));
                }).setWarning(getViewContext().getString(R.string.are_you_sure_deliver_un_successfully)).show();
            }
//            }
        } else if (mDeliveryType == 3) {
            /**
             * mDeliveryType = 3 -> chuyển tuyến
             */
            if (_llChontuyen.getVisibility() == View.VISIBLE) {
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
                mPresenter.cancelDivided(toPoCode, Integer.parseInt(mRouteInfo.getRouteId()), postmanId, mSign, mFile);
            } else mPresenter.cancelDivided(toPoCode, 0, 0, mSign, mFile);

        }
    }


    void deliveryPartial(InfoVerify infoVerify, int amount) {
        String mFileDelivery = "";
        String mFileRefund = "";

        if (listImageDelivery.size() > 0) {
            String[] arrImg = new String[listImageDelivery.size()];
            for (int i = 0; i < listImageDelivery.size(); i++) {
                if (!TextUtils.isEmpty(listImageDelivery.get(i).getText()))
                    arrImg[i] = listImageDelivery.get(i).getText();
            }
            mFileDelivery = TextUtils.join(";", arrImg);
        }
        if (listImageRefund.size() > 0) {
            String[] arrImg = new String[listImageRefund.size()];
            for (int i = 0; i < listImageRefund.size(); i++) {
                if (!TextUtils.isEmpty(listImageRefund.get(i).getText()))
                    arrImg[i] = listImageRefund.get(i).getText();
            }
            mFileRefund = TextUtils.join(";", arrImg);
        }

        DeliveryProductRequest request = new DeliveryProductRequest();

        Calendar calDate = Calendar.getInstance();
        int mHour = calDate.get(Calendar.HOUR_OF_DAY);
        int mMinute = calDate.get(Calendar.MINUTE);

        String postmanID = userInfo.getiD();
        String mobileNumber = userInfo.getMobileNumber();
        String deliveryPOCode = postOffice.getCode();
        String routeCode = routeInfo.getRouteCode();
        String deliveryDate = DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        String deliveryTime = (mHour < 10 ? "0" + mHour : mHour + "") + (mMinute < 10 ? "0" + mMinute : mMinute + "") + "00";

        DeliveryPostman item = getItemSelected().get(0);

        String receiverName;

        if (TextUtils.isEmpty(edtReceiverName.getText())) {
            receiverName = item.getReciverName();
        } else {
            receiverName = Objects.requireNonNull(edtReceiverName.getText()).toString();
        }

        String parcelCode = item.getMaE();
        String reasonCode = "";
        String solutionCode = "";
        String status = "C44";
        String note = edtNote.getText().toString();

        final String paymentChannel = "1";
        SharedPref sharedPref = new SharedPref(getActivity());
        String signature = Utils.SHA256(parcelCode + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        request.setCollectAmount((int) totalAmount);
        request.setDeliveryAmount(amount);
        request.setReturnAmount((int) totalAmount - amount);
        request.setPostmanID(Integer.parseInt(postmanID));
        request.setParcelCode(parcelCode);
        request.setMobileNumber(mobileNumber);
        request.setDeliveryPOCode(deliveryPOCode);
        request.setDeliveryDate(deliveryDate);
        request.setDeliveryTime(deliveryTime);
        request.setReceiverName(receiverName);
        request.setReasonCode(reasonCode);
        request.setSolutionCode(solutionCode);
        request.setStatus(status);
        request.setPaymentChannel(paymentChannel);
        request.setSignatureCapture(mSign);
        request.setNote(note);
        request.setCollectAmount(item.getAmount());
        request.setShiftID(item.getShiftId());
        request.setRouteCode(routeCode);
        request.setLadingPostmanID(item.getId());
        request.setSignature(signature);

        request.setImageDelivery(mFileDelivery);
        request.setReturnImage(mFileRefund);
        request.setImageAuthen(mFileAvatar + ";" + mFileVerify + ";" + mFileOther);

        request.setBatchCode(item.getBatchCode());
        request.setItemsInBatch(item.getItemsInBatch());
        request.setIsItemReturn(item.isItemReturn());
        request.setAmountForBatch(item.getAmountForBatch());
        request.setReceiverReference(edtRelationship.getText().toString());
        request.setReplaceCode(item.getReplaceCode());
        request.setPostmanCode(userInfo.getUserName());
        request.setCustomerCode(item.getCustomerCode());
        request.setReceiverPIDWhere(infoVerify.getReceiverPIDWhere());
        request.setReceiverPIDDate(infoVerify.getReceiverPIDDate());
        request.setReceiverBirthday(infoVerify.getReceiverBirthday());
        request.setReceiverAddressDetail(infoVerify.getReceiverAddressDetail());
        request.setAuthenType(infoVerify.getAuthenType());
        request.setReceiverIDNumber(infoVerify.getGtgt());
        request.setVATCode(item.getVatCode());
        request.setIsDOP(item.getIsDOP());

        request.setDeliveryLat(item.getDeliveryLat());
        request.setDeliveryLon(item.getDeliveryLon());
        request.setReceiverLat(item.getReceiverLat() == null || item.getReceiverLat().isEmpty() ? 0.0 : Double.parseDouble(item.getReceiverLat()));
        request.setReceiverLon(item.getReceiverLon() == null || item.getReceiverLon().isEmpty() ? 0.0 : Double.parseDouble(item.getReceiverLon()));
        request.setSourceChanel("DD_ANDROID");

        request.setPODeliveryLat(NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getPOLat());
        request.setPODeliveryLon(NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getPOLon());
        ///// bor sung them LadingToPostmanId ,LadingCode, PODeliveryCode  an toan cho tung san pham phat hoan

        for (ProductModel productModel : listProductRefund) {
            productModel.setLadingToPostmanId(item.getId());
            productModel.setpODeliveryCode(deliveryPOCode);
            productModel.setLadingCode(parcelCode);
        }

        for (ProductModel productModel : listProductDeliveryRequest) {
            productModel.setLadingToPostmanId(item.getId());
            productModel.setpODeliveryCode(deliveryPOCode);
            productModel.setLadingCode(parcelCode);
        }

        request.setReturnProducts(listProductRefund);
        request.setDeliveryProducts(listProductDeliveryRequest);
        request.setDeliveryWardIdAdditional(idXaphuong);
        request.setDeliveryDistrictIdAdditional(idQuanhuyen);
        String bankCode = new String();

        for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
            if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
                if (userInfo.getSmartBankLink() != null && userInfo.getSmartBankLink().get(i).getIsDefaultPayment()) {
                    bankCode = userInfo.getSmartBankLink().get(i).getBankCode();
                }
            }
        }
        request.setPaymentBankCode(bankCode);
        mPresenter.deliveryPartial(request);
    }

    private void verifyInfo() {
        rbVerifyInfo.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                checkInfoClick = true;
                authenType = 1;
                llVerifyInfo.setVisibility(View.VISIBLE);
            } else {
                checkInfoClick = false;
                llVerifyInfo.setVisibility(View.GONE);
                edtGTTTLocatedAccepted.setText("");
                edtUserAddress.setText("");
                tvGTTT.setText("");
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

    public String getPath(Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return uri.getPath();
    }

    private void TakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                if (resultCode == getActivity().RESULT_OK) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        attemptSendMedia(data.getData().getPath().replace("///", "//"), 0);
                    } else {
                        attemptSendMedia(data.getData().getPath(), 0);

                    }
                }

            } else if (requestCode == 1) {
                if (data != null) {
                    Uri selectedImageUri = data.getData();
                    File file = new File(getPath(selectedImageUri));
                    long length = file.length();
                    length = length / 1024;
                    attemptSendMediaFolder(JavaImageResizer.resizeAndCompressImageBeforeSend(getViewContext(), getPath(selectedImageUri), "DINGDONG.jpg"), 1);
                    System.out.println("File Path : " + file.getPath() + ", File size : " + length + " KB");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static File savebitmap(Bitmap bmp) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + imageFileName + ".jpg");
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        return f;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void attemptSendMediaFolder(String path_media, int type) {
        File file = new File(path_media);
        Observable.fromCallable(() -> {
            Uri uri = Uri.fromFile(new File(path_media));
            return BitmapUtils.processingBitmap(uri, getViewContext());
        }).subscribeOn(Schedulers.computation()).observeOn(Schedulers.io()).map(bitmap -> BitmapUtils.saveImage(bitmap, file.getParent(), "Process_" + file.getName(), Bitmap.CompressFormat.JPEG, 40)).observeOn(AndroidSchedulers.mainThread()).subscribe(isSavedImage -> {
            if (true) {
                String path = file.getParent() + File.separator + "Process_" + file.getName();
                // mSignPosition = false;
                //mPresenter.postImageAvatar(pathAvatar);
                if (isCaptureAvatar) {
                    mPresenter.postImageAvatar(path);
                } else {
                    mPresenter.postImage(path);
                }
                if (type == 0) if (file.exists()) file.delete();
            } else {
                String path = file.getParent() + File.separator + "Process_" + file.getName();
                Log.d("ASDASDSADasd", path);
                mPresenter.postImage(path_media);
            }
        }, onError -> Logger.e("error save image"));
    }

    public static File bitmapToFile(Context context, Bitmap bitmap, String fileNameToSave) { // File name like "image.png"
        //create a file to write bitmap data
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory() + File.separator + fileNameToSave);
            file.createNewFile();

//Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos); // YOU can also save it in JPEG
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return file; // it will return null
        }
    }

    private void attemptSendMedia(String path_media, int type) {
        File file = new File(path_media);
        Observable.fromCallable(() -> {
            Uri uri = Uri.fromFile(new File(path_media));
            return BitmapUtils.processingBitmap(uri, getViewContext());
        }).subscribeOn(Schedulers.computation()).observeOn(Schedulers.io()).map(bitmap -> BitmapUtils.saveImage(bitmap, file.getParent(), "Process_" + file.getName(), Bitmap.CompressFormat.JPEG, 40)).observeOn(AndroidSchedulers.mainThread()).subscribe(isSavedImage -> {
            if (isSavedImage) {
                String path = file.getParent() + File.separator + "Process_" + file.getName();
                // mSignPosition = false;
                //mPresenter.postImageAvatar(pathAvatar);
                if (isCaptureAvatar) {
                    mPresenter.postImageAvatar(path);
                } else {
                    mPresenter.postImage(path);
                }
                if (type == 0) if (file.exists()) file.delete();
            } else {
                mPresenter.postImage(path_media);
            }
        }, onError -> Logger.e("error save image"));
    }

    private void showUIReason() {
        ArrayList<Item> items = new ArrayList<>();
        if (mListReason != null) {
            for (ReasonInfo item : mListReason) {
                items.add(new Item(item.getCode(), item.getName()));
            }
        }
        new PickerDialog(getViewContext(), "Chọn lý do", items, item -> {
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
                tv_diachi_khac.setVisibility(View.GONE);
                ll_thoi_gian_du_kien.setVisibility(View.GONE);
            }
        }).show();
    }

    private void loadSolution() {
        if (mReasonInfo != null) mPresenter.loadSolution(mReasonInfo.getCode());
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
            if (mClickSolution) showUISolution();
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
    public void showPaymentV2Success(String message, String data) {
        if (null != getViewContext()) {
            new SweetAlertDialog(getViewContext()).setTitleText("Thông báo").setContentText(message).setConfirmText("Ok").setConfirmClickListener(v -> {
                v.dismiss();
                mData = data;
                hideProgress();
                finishView();
            }).show();
        } else showSuccessToast(message);
    }

    @Override
    public void showPaymentV2Error(String message) {
        if (null != getViewContext()) {
            new SweetAlertDialog(getViewContext()).setTitleText("Thông báo").setContentText(message).setConfirmText("Ok").setConfirmClickListener(v -> {
                v.dismiss();
                mTypeTrangThai = 2; // bao phat that bai
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
            pickerUIRoute = new ItemBottomSheetPickerUIFragment(items, getViewContext().getString(R.string.pick_route), (item, position) -> {
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
                pickerUIPostman = new ItemBottomSheetPickerUIFragment(items, getViewContext().getString(R.string.pick_postman), (item, position) -> {
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
                Item item = new Item(BuildConfig.URL_IMAGE + file, file);
                listImagesAvatar.add(item);
                imageAvatarAdapter.notifyDataSetChanged();
            }

            if (isCaptureVerify) {
                Item item = new Item(BuildConfig.URL_IMAGE + file, file);
                listImageVerify.add(item);
                imageVerifyAdapter.notifyDataSetChanged();
            }

            if (isCapture) {
                Item item = new Item(BuildConfig.URL_IMAGE + file, file);
                listImages.add(item);
                imageAdapter.notifyDataSetChanged();
            }

            if (isCaptureOther) {
                Item item = new Item(BuildConfig.URL_IMAGE + file, file);
                listImageOther.add(item);
                imageOtherAdapter.notifyDataSetChanged();
            }

            if (isCaptureDelivery) {
                Item item = new Item(BuildConfig.URL_IMAGE + file, file);
                listImageDelivery.add(item);
                imageDeliveryAdapter.notifyDataSetChanged();
            }

            if (isCaptureRefund) {
                Item item = new Item(BuildConfig.URL_IMAGE + file, file);
                listImageRefund.add(item);
                imageRefundAdapter.notifyDataSetChanged();
            }

            if (isCaptureHoanTra) {
                Item item = new Item(BuildConfig.URL_IMAGE + file, file);
                listImageHangDoiTra.add(item);
                imageHangDoiTraAdapter.notifyDataSetChanged();
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
                mTypeTrangThai = 2; // bao phat that bai
                showFinish(mTypeTrangThai);
            }
        }
    }

    @Override
    public void showCheckAmountPaymentError(String message, String amountPP, String amountPNS) {
        if (null != getViewContext()) {
            new SweetAlertDialog(getViewContext(), SweetAlertDialog.NORMAL_TYPE).setTitleText(getString(R.string.notification)).setContentText(message + "\nTiền trên hệ thông Paypost: " + amountPP + "\nTiền trên hệ thông PNS: " + amountPNS + " \nBạn có muốn cập nhật theo số tiền trên PayPost không?").setCancelText(getString(R.string.no)).setConfirmText(getString(R.string.yes)).setCancelClickListener(v -> {
                mPresenter.paymentV2(false);
                v.dismiss();
                hideProgress();
            }).setConfirmClickListener(v -> {
                mPresenter.paymentV2(true);
                v.dismiss();
                hideProgress();
            }).show();
        }
    }

    @Override
    public void showSuccess(String code, String id, String mess) {
        if (mBaoPhatBangke.size() > 1) {
            if (null != getViewContext()) {
                if (code.equals("00")) {
                    mDeliverySuccess += 1;
                    mDataList.add(id);
                } else {
                    mDeliveryError += 1;
                }

                int total = mDeliverySuccess + mDeliveryError;
                if (total == getItemSelected().size()) {
                    DataModel dataModel = new DataModel();
                    dataModel.setSuccess(mDataList);
                    dataModel.setError(new ArrayList<>());
                    mData = NetWorkController.getGson().toJson(dataModel, DataModel.class);
                    mTypeTrangThai = 1;// baso phat thanh cong
                    Log.d("asdasda123khiem", mData + "");
                    showFinish(mTypeTrangThai);
                }
            }
        } else {
            if (code.equals("00")) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE).setConfirmText("Đóng").setTitleText(getString(R.string.notification)).setContentText(mess).setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    DataModel dataModel = new DataModel();
                    mDataList.add(id);
                    dataModel.setSuccess(mDataList);
                    dataModel.setError(new ArrayList<>());
                    mData = NetWorkController.getGson().toJson(dataModel, DataModel.class);
                    mTypeTrangThai = 1;// baso phat thanh cong
                    finishView();
//                    showFinish(mTypeTrangThai);
                }).show();
            } else {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE).setConfirmText("Đóng").setTitleText(getString(R.string.notification)).setContentText(mess).setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                }).show();
            }


        }
    }

    @Override
    public void showCancelDivided(String message) {
        if (null != getViewContext()) {
            Toast.showToast(getActivity(), message);
            finishView();
        }
    }

    private void showFinish(int type) {
        hideProgress();
        if (getActivity() != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE).setConfirmText("OK").setTitleText(getString(R.string.notification)).setContentText("Báo phát BD13 hoàn tất. Thành công [" + mDeliverySuccess + "] thất bại [" + mDeliveryError + "]").setConfirmClickListener(sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                finishView();

            }).show();
        }
    }

    @Override
    public void finishView() {
        try {
            mPresenter.back();
            mPresenter.onTabRefresh(mData, mDeliveryType);
        } catch (NullPointerException nullPointerException) {
        }
    }

    @Override
    public List<DeliveryPostman> getItemSelected() {
        if (null != adapter) return adapter.getItemsSelected();
        else return new ArrayList<>();
    }

    private void updateTotalPackage() {
        totalAmount = 0;
        totalFee = 0;
        for (DeliveryPostman i : getItemSelected()) {
            totalAmount += i.getAmount();
            totalFee += i.getFeeShip() + i.getFeeCollectLater() + i.getFeePPA() + i.getFeeCOD() + i.getFeePA();
        }
        tv_quantity.setText(String.format(" %s", getItemSelected().size()));
        tv_total_amount.setText(String.format(" %s đ", NumberUtils.formatPriceNumber(totalAmount)));
        tvTotalFee.setText(String.format(" %s đ", NumberUtils.formatPriceNumber(totalFee)));
        tvTotal.setText(String.format(" %s đ", NumberUtils.formatPriceNumber(totalFee + totalAmount)));
    }

    private boolean checkSameAddress() {
        List<DeliveryPostman> itemSelected = getItemSelected();
        if (null == itemSelected || 0 == itemSelected.size()) return false;
        DeliveryPostman firstItem = itemSelected.get(0);
        for (int i = 1; i < itemSelected.size(); i++) {
            if (!firstItem.getReciverName().equals(itemSelected.get(i).getReciverName()) || !firstItem.getReciverAddress().equals(itemSelected.get(i).getReciverAddress()))
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
            new PickerDialog(getViewContext(), getString(R.string.pick_solution), items, item -> {
                for (SolutionInfo info : mListSolution) {
                    if (item.getValue().equals(info.getCode())) {
                        mSolutionInfo = info;
                        break;
                    }
                }
                if (mSolutionInfo != null) {
                    tv_solution.setText(mSolutionInfo.getName());
                }
                if (mReasonInfo.getCode().equals("02")) {
                    if (mSolutionInfo.getCode().equals("C") || mSolutionInfo.getCode().equals("I") || mSolutionInfo.getCode().equals("A") || mSolutionInfo.getCode().equals("R")) {
                        tv_diachi_khac.setVisibility(View.VISIBLE);
                    } else tv_diachi_khac.setVisibility(View.GONE);
                }

                if (mReasonInfo.getCode().equals("71")) {
                    if (mSolutionInfo.getCode().equals("A") || mSolutionInfo.getCode().equals("I") || mSolutionInfo.getCode().equals("R")) {
                        ll_thoi_gian_du_kien.setVisibility(View.VISIBLE);
                    } else ll_thoi_gian_du_kien.setVisibility(View.GONE);
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
        Log.d("MEMIT", authenType + "");
        if (authenType == 0) {
            rbVerifyInfo.setVisibility(View.VISIBLE);
            rbVerifyImage.setVisibility(View.VISIBLE);
            llVerify.setVisibility(View.VISIBLE);
            if (rbVerifyInfo.isChecked()) {
                llVerifyInfo.setVisibility(View.VISIBLE);
                llVerifyImage.setVisibility(View.GONE);//add
            } else {
                llVerifyInfo.setVisibility(View.GONE);
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
            rbVerifyInfo.setEnabled(false);
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
            rbVerifyImage.setEnabled(false);
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
            rbVerifyInfo.setEnabled(false);
            rbVerifyImage.setChecked(true);
            rbVerifyImage.setEnabled(false);
            verifyInfo();
            verifyImage();
            llVerifyImage.setVisibility(View.VISIBLE);
        } else if (authenType == -2) {
            llVerify.setVisibility(View.GONE);
        }
    }

    private boolean canVerify() {
        if (getAuthenType() == -1 || getAuthenType() == -2) return false;
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

        if (arrAvartar.length == 0 && arrOther.length == 0 && arrVerify.length == 0) {
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


    @SuppressLint("MissingPermission")
    private Location getLastKnownLocation() {
        Location l = null;
        mLocationManager = (LocationManager) getViewContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override
    public void showCallError(String message) {
        Toast.showToast(getViewContext(), message);
    }

    @Override
    public void showCallSuccess(String phone) {
        callProvidertoCSKH(phone);
    }

    PhoneCodeEdit phoneCodeEdit = new PhoneCodeEdit();

    @Override
    public void showCallEdit(String x) {
        PhoneCodeEdit phoneCodeEdit = NetWorkController.getGson().fromJson(x, PhoneCodeEdit.class);
        this.phoneCodeEdit = phoneCodeEdit;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneCodeEdit.getPhoneNumber()));
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            startActivity(intent);
        }
    }

    private void callProvidertoCSKH(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void showXaPhuong(List<WardModels> list) {
        mListXaPhuong = new ArrayList<>();
        mListXaPhuong = list;
    }

    @Override
    public void showQuanHuyen(List<DistrictModels> list) {
        mListQuanHuyen = new ArrayList<>();
        mListQuanHuyen = list;
    }

    @Override
    public void showXaPhuongNew(List<WardModels> list) {
        mListXaPhuongNew = new ArrayList<>();
        mListXaPhuongNew = list;
    }

    @Override
    public void showQuanHuyenNew(List<DistrictModels> list) {
        mListQuanHuyenNew = new ArrayList<>();
        mListQuanHuyenNew = list;
    }

    private void showXaPhuong() {
        ArrayList<Item> items = new ArrayList<>();
        int i = 0;
        for (WardModels item : mListXaPhuong) {
            items.add(new Item(String.valueOf(item.getWardsId()), item.getWardsName()));
            i++;
        }
        new DialogReason(getViewContext(), "Chọn Xã/Phường", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                tvXaphuongBosung.setText(item.getText());
                idXaphuong = Integer.parseInt(item.getValue());
            }
        }).show();
    }

    private void showQuanHuyen() {
        ArrayList<Item> items = new ArrayList<>();
        int i = 0;
        for (DistrictModels item : mListQuanHuyen) {
            items.add(new Item(String.valueOf(item.getDistrictId()), item.getDistrictName()));
            i++;
        }
        new DialogReason(getViewContext(), "Chọn Quận/Huyện", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                tvQuanhuyenBosung.setText(item.getText());
                idQuanhuyen = Integer.parseInt(item.getValue());
                mPresenter.getXaPhuong(idQuanhuyen);
            }
        }).show();
    }

    List<ProvinceModels> mListTinhThanhPhoNew = new ArrayList<>();
    List<DistrictModels> mListQuanHuyenNew = new ArrayList<>();
    List<WardModels> mListXaPhuongNew = new ArrayList<>();
    int idTinhnew, idQuanHuyenNew, idXaPhuongNew;

    @Override
    public void showTinhThanhPho(List<ProvinceModels> list) {
        mListTinhThanhPhoNew = new ArrayList<>();
        mListTinhThanhPhoNew = list;
    }

    private void showTinhThanhPho() {
        ArrayList<Item> items = new ArrayList<>();
        int i = 0;
        for (ProvinceModels item : mListTinhThanhPhoNew) {
            items.add(new Item(String.valueOf(item.getProvinceId()), item.getProvinceName()));
            i++;
        }
        new DialogReason(getViewContext(), "Chọn Tỉnh/Thành Phố", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                tv_tinhtp_new.setText(item.getText().trim());
                tv_tinhtp_new.setTextColor(getViewContext().getResources().getColor(R.color.black));
                idTinhnew = Integer.parseInt(item.getValue());
                idXaPhuongNew = 0;
                tv_xaphuong_new.setText("");
                idQuanHuyenNew = 0;
                tv_quanhuyen_new.setText("");
                mListQuanHuyenNew = new ArrayList<>();
                mListXaPhuongNew = new ArrayList<>();
                mPresenter.getQuanHuyenNew(idTinhnew);

            }
        }).show();
    }

    private void showXaPhuongNew() {
        ArrayList<Item> items = new ArrayList<>();
        int i = 0;
        for (WardModels item : mListXaPhuongNew) {
            items.add(new Item(String.valueOf(item.getWardsId()), item.getWardsName()));
            i++;
        }
        new DialogReason(getViewContext(), "Chọn Xã/Phường", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                tv_xaphuong_new.setText(item.getText());
                tv_xaphuong_new.setTextColor(getViewContext().getResources().getColor(R.color.black));
                idXaPhuongNew = Integer.parseInt(item.getValue());
            }
        }).show();
    }

    private void showQuanHuyenNew() {
        ArrayList<Item> items = new ArrayList<>();
        int i = 0;
        for (DistrictModels item : mListQuanHuyenNew) {
            items.add(new Item(String.valueOf(item.getDistrictId()), item.getDistrictName()));
            i++;
        }
        new DialogReason(getViewContext(), "Chọn Quận/Huyện", items, new PickerCallback() {
            @Override
            public void onClickItem(Item item) {
                tv_quanhuyen_new.setText(item.getText());
                tv_quanhuyen_new.setTextColor(getViewContext().getResources().getColor(R.color.black));
                idQuanHuyenNew = Integer.parseInt(item.getValue());
                idXaPhuongNew = 0;
                tv_xaphuong_new.setText("");
                mListXaPhuongNew = new ArrayList<>();
                mPresenter.getQuanHuyenNew(idTinhnew);
                mPresenter.getXaPhuongNew(idQuanHuyenNew);
            }
        }).show();
    }
}