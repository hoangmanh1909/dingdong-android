package com.ems.dingdong.functions.mainhome.chihobtxh.payment

import android.text.TextUtils
import android.view.View
import butterknife.BindView
import butterknife.OnClick
import com.core.base.viper.ViewFragment
import com.ems.dingdong.R
import com.ems.dingdong.extensions.dateToString
import com.ems.dingdong.model.Item
import com.ems.dingdong.model.UserInfo
import com.ems.dingdong.model.request.SeaBankPaymentRequest
import com.ems.dingdong.network.NetWorkController
import com.ems.dingdong.utiles.Constants
import com.ems.dingdong.utiles.SharedPref
import com.ems.dingdong.views.CustomEditText
import com.ems.dingdong.views.form.FormItemTextView
import com.ems.dingdong.views.picker.ItemBottomSheetPickerUIFragment
import com.tsongkha.spinnerdatepicker.DatePicker
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import java.util.*

/**
 * The PaymentChiHoBtxh Fragment
 */
class PaymentChiHoBtxhFragment : ViewFragment<PaymentChiHoBtxhContract.Presenter>(), PaymentChiHoBtxhContract.View,
        com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {


    @BindView(R.id.edt_agent)
    lateinit var edtAgent: CustomEditText

    @BindView(R.id.edt_ReceiverName)
    lateinit var edtReceiverName: CustomEditText

    @BindView(R.id.edt_addressReceiver)
    lateinit var edtAddressReceiver: CustomEditText

    @BindView(R.id.edt_phone)
    lateinit var edtPhoneReceiver: CustomEditText

    @BindView(R.id.tv_typeId_receiver)
    lateinit var tvTypeIdReceiver: FormItemTextView

    @BindView(R.id.edt_id_receiver)
    lateinit var edtIdReceiver: CustomEditText

    @BindView(R.id.edt_id_date_receiver)
    lateinit var edtIdDateReceiver: FormItemTextView

    @BindView(R.id.edt_id_place_receiver)
    lateinit var edtIdPlaceReceiver: CustomEditText
    var pickerTypeId: ItemBottomSheetPickerUIFragment? = null
    var mTypeId: String = ""
    lateinit var mCalendar: Calendar
    override fun getLayoutId(): Int {
        return R.layout.fragment_payment_chi_ho_btxh
    }

    companion object {

        val instance: PaymentChiHoBtxhFragment
            get() = PaymentChiHoBtxhFragment()
    }

    override fun initLayout() {
        super.initLayout()
        mCalendar = Calendar.getInstance()
    }

    @OnClick(R.id.img_back, R.id.tv_typeId_receiver, R.id.edt_id_date_receiver, R.id.btn_check)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.img_back -> {
                mPresenter.back()
            }
            R.id.tv_typeId_receiver -> {
                showTypeId()
            }
            R.id.edt_id_date_receiver -> {
                SpinnerDatePickerDialogBuilder()
                        .context(context)
                        .callback(this)
                        .spinnerTheme(R.style.DatePickerSpinner)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH))
                        .minDate(1979, 0, 1)
                        .build()
                        .show()
            }
            R.id.btn_check -> {
                val item = mPresenter.getSeaBankInquiryModel()
                val sharedPref = SharedPref(activity!!)
                val userJson = sharedPref.getString(Constants.KEY_USER_INFO, "")
                if (!TextUtils.isEmpty(userJson)) {
                    val userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo::class.java)
                    val seaBankPaymentRequest = SeaBankPaymentRequest(userInfo.mobileNumber, item.stringInfo!!, item.stringHeader!!, item.stringValue!!,
                            item.seaBankRetRefNumber!!, edtReceiverName.text.toString(), edtAddressReceiver.text.toString(), edtPhoneReceiver.text.toString(),
                            mTypeId, edtIdReceiver.text.toString(), edtIdDateReceiver.text.toString(), edtIdPlaceReceiver.text.toString(), "", "", "", "")
                    mPresenter.toOtp(seaBankPaymentRequest)
                }
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        mCalendar.set(year, monthOfYear, dayOfMonth)
        edtIdDateReceiver.text = mCalendar.dateToString("dd/MM/yyyy")
    }

    private fun showTypeId() {
        val items = ArrayList<Item>()
        for (item in mPresenter.getList()) {
            items.add(Item(item.id, item.name))
        }
        if (pickerTypeId == null) {
            pickerTypeId = ItemBottomSheetPickerUIFragment(items, "Chọn loại GTTT",
                    ItemBottomSheetPickerUIFragment.PickerUiListener { item, _ ->
                        tvTypeIdReceiver.text = item.text
                        mTypeId = item.value
                    }, 0)
            pickerTypeId?.show(activity!!.supportFragmentManager, pickerTypeId?.tag)
        } else {
            pickerTypeId?.setData(items, 0)
            if (!pickerTypeId?.isShow!!) {
                pickerTypeId?.show(activity!!.supportFragmentManager, pickerTypeId?.tag)
            }
        }
    }
}
