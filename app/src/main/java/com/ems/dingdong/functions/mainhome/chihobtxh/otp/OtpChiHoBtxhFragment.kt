package com.ems.dingdong.functions.mainhome.chihobtxh.otp

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.core.base.viper.ViewFragment
import com.ems.dingdong.R
import com.ems.dingdong.model.request.SeaBankPaymentRequest
import com.ems.dingdong.model.response.SeaBankInquiryModel
import com.ems.dingdong.utiles.Log
import com.ems.dingdong.utiles.NumberUtils
import com.ems.dingdong.utiles.Toast
import com.ems.dingdong.views.CustomEditText
import com.ems.dingdong.views.CustomTextView

/**
 * The OtpChiHoBtxh Fragment
 */
class OtpChiHoBtxhFragment : ViewFragment<OtpChiHoBtxhContract.Presenter>(), OtpChiHoBtxhContract.View {
    @BindView(R.id.tvGttt)
    lateinit var tvGttt: CustomTextView

    @BindView(R.id.tv_amount)
    lateinit var tvAmount: CustomTextView

    @BindView(R.id.edt_otp)
    lateinit var edtOtp: CustomEditText

    @BindView(R.id.tv_title)
    lateinit var tvTitle: TextView

    @BindView(R.id.tt_noidung)
    lateinit var tvNoidung: TextView

    override fun getLayoutId(): Int {
        return R.layout.fragment_otp_chi_ho_btxh
    }

    companion object {
        val instance: OtpChiHoBtxhFragment
            get() = OtpChiHoBtxhFragment()
    }

    @SuppressLint("SetTextI18n")
    override fun initLayout() {
        super.initLayout()
        Log.d("tasdasdad", mPresenter.int())
//        val seaBankPaymentRequest: SeaBankPaymentRequest = mPresenter.getSeaBankPaymentRequest()
        if (mPresenter.int().toInt() == 1) {
            tvNoidung.text = "2205 - Chi hộ đối tượng BTXH VietinBank"
        } else if (mPresenter.int().toInt() == 2) tvNoidung.text = "2110 - Chi hộ đối tượng BTXH SeaBank"
        val seaBankInquiryModel: SeaBankInquiryModel = mPresenter.getSeaBankInquiryModel()
        tvAmount.text = seaBankInquiryModel.amount?.toInt()?.let { NumberUtils.formatPriceNumber(it) + " VNĐ" }
        tvGttt.text = seaBankInquiryModel.identifyNumber
    }

    @OnClick(R.id.img_back, R.id.btn_check)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.img_back -> {
                mPresenter.back()
            }
            R.id.btn_check -> {
                if (edtOtp.text.isNullOrEmpty()) {
                    Toast.showToast(context, "Vui lòng nhập OTP")
                    return
                }
                mPresenter.payment(edtOtp.text.toString())
            }
        }
    }
}
