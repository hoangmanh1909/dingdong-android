package com.ems.dingdong.functions.mainhome.chihobtxh.inquiry

import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.core.base.viper.ViewFragment
import com.ems.dingdong.BuildConfig
import com.ems.dingdong.R
import com.ems.dingdong.extensions.editTextListener
import com.ems.dingdong.extensions.fillColor
import com.ems.dingdong.model.Item
import com.ems.dingdong.model.UserInfo
import com.ems.dingdong.model.request.SeaBankInquiryRequest
import com.ems.dingdong.model.response.BankAccountNumber
import com.ems.dingdong.network.NetWorkController
import com.ems.dingdong.utiles.Constants
import com.ems.dingdong.utiles.SharedPref
import com.ems.dingdong.utiles.Toast
import com.ems.dingdong.utiles.Utils
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment
import java.util.*

/**
 * The InquiryThuHo Fragment
 */
class InquiryChiHoFragment : ViewFragment<InquiryChiHoContract.Presenter>(), InquiryChiHoContract.View {
    @BindView(R.id.tvTitleAccount)
    lateinit var tvTitleAccount: TextView
    @BindView(R.id.tvTitleMoney)
    lateinit var tvTitleMoney: TextView
    @BindView(R.id.tvAccount)
    lateinit var tvAccount: TextView
    @BindView(R.id.edt_amount)
    lateinit var edtAmount: EditText
    @BindView (R.id.tvTaiKhoan)
    lateinit var tvTaiKhoan : TextView

    lateinit var bankAccountNumber: BankAccountNumber
    override fun getLayoutId(): Int {
        return R.layout.fragment_inquiry_thu_ho
    }

    var picker: ItemBottomSheetPickerUIFragment? = null

    companion object {

        val instance: InquiryChiHoFragment
            get() = InquiryChiHoFragment()
    }

    override fun initLayout() {
        super.initLayout()

        tvTitleAccount.text = "Số tài khoản (*)"
        tvTitleAccount.fillColor("(*)", R.color.red_light)

        tvTitleMoney.text = "Số tiền rút (*)"
        tvTitleMoney.fillColor("(*)", R.color.red_light)
        val list = mPresenter.getListAccount()
        edtAmount.editTextListener()
        if (list != null && list.isNotEmpty()) {
            if (list.size == 1) {
                bankAccountNumber = list[0]
                tvAccount.text = list[0].bankAccountNumber
            } else {
                showAccount()
            }
        }
    }

    @OnClick(R.id.img_back, R.id.btn_check, R.id.tvAccount)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.img_back -> {
                mPresenter.back()
            }
            R.id.btn_check -> {
                if (tvAccount.text.isNotEmpty()) {
                    if (edtAmount.text.isNullOrEmpty()) {
                        Toast.showToast(context, "Vui lòng nhập số tiền")
                        return
                    }
                    var amount = edtAmount.text.toString().replace(" VNĐ", "").replace(Regex("[$,.]"), "").toInt()
                    if (amount <= 0) {
                        Toast.showToast(context, "Vui lòng nhập số tiền lớn hơn 0 đồng")
                        return
                    }
                    val sharedPref = SharedPref(activity!!)
                    val userJson = sharedPref.getString(Constants.KEY_USER_INFO, "")
                    if (!TextUtils.isEmpty(userJson)) {
                        val userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo::class.java)

                        val seaBankInquiryRequest = SeaBankInquiryRequest("2110",userInfo.mobileNumber,
                                bankAccountNumber.identifyNumber!!, bankAccountNumber.bankAccountNumber!!, "","",amount, bankAccountNumber.assigneeName!!, bankAccountNumber.mandatorName!!)
                        mPresenter.seaBankInquiry(seaBankInquiryRequest)
                    }

                } else {
                    Toast.showToast(context, "Không có thông tin số tài khoản")
                }

            }
            R.id.tvAccount -> {
                showAccount()
            }
        }
    }

    private fun showAccount() {
        val items = ArrayList<Item>()
        val list = mPresenter.getList()
        if (list != null) {
            for (item in list) {
                items.add(Item(item.bankAccountNumber, item.bankAccountNumber))
            }
            if (picker == null) {
                picker = ItemBottomSheetPickerUIFragment(items, "Chọn số tài khoản",
                        ItemBottomSheetPickerUIFragment.PickerUiListener { item, _ ->
                            tvAccount.text = item.text
                            bankAccountNumber = mPresenter.getListAccount()?.find { it.bankAccountNumber == item.value }!!
                        }, 0)
                picker?.show(activity!!.supportFragmentManager, picker?.tag)
            } else {
                picker?.setData(items, 0)
                if (!picker?.isShow!!) {
                    picker?.show(activity!!.supportFragmentManager, picker?.tag)
                }
            }
        } else {
            Toast.showToast(activity, "Không lấy được danh sách tài khoản")
        }
    }
}
