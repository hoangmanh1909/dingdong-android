package com.ems.dingdong.functions.mainhome.chihobtxh.check

import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.widget.PopupMenu
import butterknife.BindView
import butterknife.OnClick
import com.core.base.viper.ViewFragment
import com.ems.dingdong.R
import com.ems.dingdong.extensions.editTextListener
import com.ems.dingdong.extensions.fillColor
import com.ems.dingdong.model.UserInfo
import com.ems.dingdong.model.request.BankAccountNumberRequest
import com.ems.dingdong.model.request.SeaBankInquiryRequest
import com.ems.dingdong.model.request.SeaBankPaymentRequest
import com.ems.dingdong.model.response.SeaBankInquiryModel
import com.ems.dingdong.network.NetWorkController
import com.ems.dingdong.utiles.Constants
import com.ems.dingdong.utiles.SharedPref
import com.ems.dingdong.utiles.Toast
import com.ems.dingdong.views.CustomEditText
import com.ems.dingdong.views.CustomTextView
import kotlinx.android.synthetic.main.fragment_check_reference.*
import java.util.*

/**
 * The CheckReference Fragment
 */
class CheckReferenceFragment : ViewFragment<CheckReferenceContract.Presenter>(), CheckReferenceContract.View {


    @BindView(R.id.edt_reference)
    lateinit var edtReference: CustomEditText

    @BindView(R.id.titleSodienthoaiChuthe)
    lateinit var titleSodienthoaiChuthe: CustomTextView

    @BindView(R.id.titleSodienthoaiGDV)
    lateinit var titleSodienthoaiGDV: CustomTextView

    @BindView(R.id.titleSotaikhoan)
    lateinit var titleSotaikhoan: CustomTextView

    @BindView(R.id.titleSogiayto)
    lateinit var titleSogiayto: CustomTextView

    @BindView(R.id.titleTenchuthe)
    lateinit var titleTenchuthe: CustomTextView

    @BindView(R.id.titleSotienrut)
    lateinit var titleSotienrut: CustomTextView

    @BindView(R.id.edtSodienthoaiGDV)
    lateinit var edtSodienthoaiGDV: CustomEditText

    @BindView(R.id.edtSogiayto)
    lateinit var edtSogiayto: CustomEditText

    @BindView(R.id.edtSotaikhoan)
    lateinit var edtSotaikhoan: CustomEditText

    @BindView(R.id.edtTenchuthe)
    lateinit var edtTenchuthe: CustomEditText

    @BindView(R.id.edtSodienthoaiChuthe)
    lateinit var edtSodienthoaiChuthe: CustomEditText

    @BindView(R.id.edtNoidung)
    lateinit var edtNoidung: CustomEditText

    @BindView(R.id.edtSotienrut)
    lateinit var edtSotienrut: CustomEditText

    lateinit var maBaohiem: String;
    override fun getLayoutId(): Int {
        return R.layout.fragment_check_reference
    }

    companion object {
        val instance: CheckReferenceFragment
            get() = CheckReferenceFragment()
    }

    override fun initLayout() {
        super.initLayout()
//        maBaohiem = "2110";

//        edtSotaikhoan.text = "102000692572"
//        edtTenchuthe.text = "Customer Name"
//        edtSogiayto.text = "800145740"
//        edtSodienthoaiChuthe.text = "0919743436"
//        edtSotienrut.text = "500000"

        titleSotaikhoan.text = "Số tài khoản ngân hàng (*)"
        titleSotaikhoan.fillColor("(*)", R.color.red_light)

        titleSotienrut.text = "Số tiền rút (*)"
        titleSotienrut.fillColor("(*)", R.color.red_light)

        titleTenchuthe.text = "Tên chủ thẻ (*)"
        titleTenchuthe.fillColor("(*)", R.color.red_light)

        titleSogiayto.text = "Số giấy tờ tuỳ thân (*)"
        titleSogiayto.fillColor("(*)", R.color.red_light)

        edtSotienrut.editTextListener()
    }

    @OnClick(R.id.img_back, R.id.btn_check, R.id.tvMabaohiem, R.id.btn_check_viettinbank)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.btn_check_viettinbank -> {
                if (edtTenchuthe.text.isNullOrEmpty()) {
                    Toast.showToast(context, "Vui lòng nhập tên chủ thẻ")
                    return
                }
                if (edtSotaikhoan.text.isNullOrEmpty()) {
                    Toast.showToast(context, "Vui lòng nhập số tài khoản ngân hàng")
                    return
                }

                if (edtSotienrut.text.isNullOrEmpty()) {
                    Toast.showToast(context, "Vui lòng nhập số tiền rút")
                    return
                }

                if (edtSogiayto.text.isNullOrEmpty()) {
                    Toast.showToast(context, "Vui lòng nhập số giấy tờ tuỳ thân")
                    return
                }
                var amount = edtSotienrut.text.toString().replace(" VNĐ", "").replace(Regex("[$,.]"), "").toInt()
                val sharedPref = SharedPref(activity!!)
                val userJson = sharedPref.getString(Constants.KEY_USER_INFO, "")
                val userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo::class.java)
                val seaBankInquiryRequest = SeaBankInquiryRequest(maBaohiem, userInfo.mobileNumber,
                        edtSogiayto.text.toString()!!, edtSotaikhoan.text.toString()!!, edtTenchuthe.text.toString(), edtSodienthoaiChuthe.text.toString(), amount, "", ""
                )

                mPresenter.seaBankInquiry(seaBankInquiryRequest)
            }
            R.id.tvMabaohiem -> {
                pickFilter(tvMabaohiem)
            }
            R.id.img_back -> {
                mPresenter.back()
            }
            R.id.btn_check -> {
                if (edt_reference.text.isNullOrEmpty()) {
                    Toast.showToast(context, "Vui lòng nhập số giấy tờ")
                    return
                }
                val sharedPref = SharedPref(activity!!)
                val userJson = sharedPref.getString(Constants.KEY_USER_INFO, "")
                if (!TextUtils.isEmpty(userJson)) {
                    val userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo::class.java)
                    val bankAccountNumberRequest = BankAccountNumberRequest(userInfo.mobileNumber, edtReference?.text.toString(), "")
                    bankAccountNumberRequest.apply {
                        //Signature= Utils.SHA256(MobileNumber + IdentifyNumber + BankAccountNumber + BuildConfig.PRIVATE_KEY).toUpperCase(Locale.getDefault())
                    }
                    mPresenter.getBankAccountNumber(bankAccountNumberRequest)
                }
            }
        }
    }

    override fun clearText() {
        edt_reference.setText("")
        edtTenchuthe.setText("")
        edtSotienrut.setText("")
        edtSotaikhoan.setText("")
        edtSogiayto.setText("")
        edtSodienthoaiChuthe.setText("")
    }

    override fun showOTP(seaBankInquiryModel: SeaBankInquiryModel) {
        val item = seaBankInquiryModel;
        val sharedPref = SharedPref(activity!!)
        val userJson = sharedPref.getString(Constants.KEY_USER_INFO, "")
        if (!TextUtils.isEmpty(userJson)) {
            val userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo::class.java)
            val mSeaBankPaymentRequest = SeaBankPaymentRequest(userInfo.mobileNumber, item?.stringInfo!!, item.stringHeader!!, item.stringValue!!,
                    "", "", "", "",
                    "", "", "", "", "", "2205", item.traceId, item.bankAccountMobileNumber)

            mPresenter.otp(mSeaBankPaymentRequest)
        }
    }

    private fun pickFilter(anchor: View) {
        val list: MutableList<ItemMode> = ArrayList<ItemMode>()
        list.add(ItemMode(1, "2110", "Chi hộ đối tượng BTXH SeaBank"))
        list.add(ItemMode(2, "2205", "Chi hộ đối tượng BTXH VietinBank"))
        if (list != null) {
            val popupMenu = PopupMenu(activity!!, anchor)
            popupMenu.menu.add(1, 1, 2110, "2110 - Chi hộ đối tượng BTXH SeaBank")
            popupMenu.menu.add(2, 2, 2205, "2205 - Chi hộ đối tượng BTXH VietinBank")
            popupMenu.setOnMenuItemClickListener { item ->
                tvMabaohiem.setText(item.title)

                if (item.itemId == 2) {
                    maBaohiem = "2205";
                    tvTenBaohiem.setText("Chi hộ đối tượng BTXH VietinBank");
                    llSeaBank.visibility = View.GONE
                    llVietinBank.visibility = View.VISIBLE
                } else {
                    maBaohiem = "2110";
                    tvTenBaohiem.setText("Chi hộ đối tượng BTXH SeaBank");
                    llSeaBank.visibility = View.VISIBLE
                    llVietinBank.visibility = View.GONE
                }
                true
            }
            popupMenu.show()
        }
    }
}
