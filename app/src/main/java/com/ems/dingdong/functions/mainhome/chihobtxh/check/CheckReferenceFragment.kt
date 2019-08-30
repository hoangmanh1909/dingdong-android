package com.ems.dingdong.functions.mainhome.chihobtxh.check

import android.text.TextUtils
import android.view.View
import butterknife.BindView
import butterknife.BindViews
import butterknife.OnClick
import com.core.base.viper.ViewFragment
import com.ems.dingdong.R
import com.ems.dingdong.model.UserInfo
import com.ems.dingdong.model.request.BankAccountNumberRequest
import com.ems.dingdong.network.NetWorkController
import com.ems.dingdong.utiles.Constants
import com.ems.dingdong.utiles.SharedPref
import com.ems.dingdong.utiles.Toast
import com.ems.dingdong.views.CustomEditText
import kotlinx.android.synthetic.main.fragment_check_reference.*

/**
 * The CheckReference Fragment
 */
class CheckReferenceFragment : ViewFragment<CheckReferenceContract.Presenter>(), CheckReferenceContract.View {
    @BindView(R.id.edt_reference)
    lateinit var edtReference: CustomEditText

    override fun getLayoutId(): Int {
        return R.layout.fragment_check_reference
    }

    companion object {

        val instance: CheckReferenceFragment
            get() = CheckReferenceFragment()
    }

    override fun initLayout() {
        super.initLayout()
    }

    @OnClick(R.id.img_back, R.id.btn_check)
    fun onViewClicked(view: View) {
        when (view.id) {
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
                    var bankAccountNumberRequest = BankAccountNumberRequest(userInfo.mobileNumber, edtReference?.text.toString(), "")
                    mPresenter.getBankAccountNumber(bankAccountNumberRequest)
                }
            }
        }
    }
}
