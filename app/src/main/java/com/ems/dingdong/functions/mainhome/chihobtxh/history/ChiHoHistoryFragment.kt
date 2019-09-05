package com.ems.dingdong.functions.mainhome.chihobtxh.inquiry

import android.os.Handler
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.core.base.viper.ViewFragment
import com.ems.dingdong.R
import com.ems.dingdong.callback.OnChooseDay
import com.ems.dingdong.dialog.EditDayDialog
import com.ems.dingdong.functions.mainhome.chihobtxh.history.ChiHoHistoryAdapter
import com.ems.dingdong.model.UserInfo
import com.ems.dingdong.model.response.SeaBankHistoryPaymentModel
import com.ems.dingdong.network.NetWorkController
import com.ems.dingdong.utiles.Constants
import com.ems.dingdong.utiles.DateTimeUtils
import com.ems.dingdong.utiles.NumberUtils
import com.ems.dingdong.utiles.SharedPref
import com.ems.dingdong.views.CustomBoldTextView
import com.ems.dingdong.views.CustomEditText
import java.util.*

/**
 * The InquiryThuHo Fragment
 */
class ChiHoHistoryFragment : ViewFragment<ChiHoHistoryContract.Presenter>(), ChiHoHistoryContract.View {


    @BindView(R.id.recycler)
    lateinit var recycler: RecyclerView
    @BindView(R.id.tv_nodata)
    lateinit var tvNodata: TextView
    @BindView(R.id.edt_search)
    lateinit var edtSearch: CustomEditText
    @BindView(R.id.tv_count)
    lateinit var tvCount: CustomBoldTextView
    @BindView(R.id.tv_amount)
    lateinit var tvAmount: CustomBoldTextView

    private var fromDate: String? = null
    private var toDate: String? = null
    lateinit var mAdapter: ChiHoHistoryAdapter
    lateinit var mList: ArrayList<SeaBankHistoryPaymentModel>
    private var isLoading: Boolean = false
    override fun getLayoutId(): Int {
        return R.layout.fragment_chi_ho_history
    }


    companion object {

        val instance: ChiHoHistoryFragment
            get() = ChiHoHistoryFragment()
    }

    override fun initLayout() {
        super.initLayout()
        fromDate = DateTimeUtils.convertDateToString(Date(), DateTimeUtils.SIMPLE_DATE_FORMAT)
        toDate = DateTimeUtils.convertDateToString(Date(), DateTimeUtils.SIMPLE_DATE_FORMAT)
        search()
        edtSearch.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {


            }

            override fun afterTextChanged(s: Editable) {
                mAdapter.filter.filter(s.toString())
            }
        })
        mList = ArrayList()
        mAdapter = ChiHoHistoryAdapter(mList, object : ChiHoHistoryAdapter.FilterDone {
            override fun getCount(count: Int, amount: Long) {
                Handler().postDelayed({
                    while (isLoading) {
                        try {
                            Thread.sleep(1000)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }

                    }
                    tvCount.text = String.format(" %s", count.toString() + "")
                    tvAmount.text = String.format(" %s VNĐ", NumberUtils.formatPriceNumber(amount))
                }, 1000)
            }
        })
        recycler.apply {
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
    }


    @OnClick(R.id.img_back, R.id.img_search)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.img_back -> {
                mPresenter.back()
            }
            R.id.img_search -> {
                showDialog()
            }
        }
    }

    private fun search() {
        val sharedPref = SharedPref(activity!!)
        val userJson = sharedPref.getString(Constants.KEY_USER_INFO, "")
        if (!TextUtils.isEmpty(userJson)) {
            val userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo::class.java)
            isLoading = true
            mPresenter.getHistory(userInfo.mobileNumber, fromDate, toDate)
        }
    }

    private fun showDialog() {
        EditDayDialog(activity, OnChooseDay { calFrom, calTo ->
            fromDate = DateTimeUtils.convertDateToString(calFrom.time, DateTimeUtils.SIMPLE_DATE_FORMAT)
            toDate = DateTimeUtils.convertDateToString(calTo.time, DateTimeUtils.SIMPLE_DATE_FORMAT)
            search()
        }).show()
    }

    override fun showResponseSuccess(data: List<SeaBankHistoryPaymentModel>) {
        var amount: Long = 0
        for (item in data) {
            mList.add(item)
            if (!TextUtils.isEmpty(item.stringValue)) {
                val values = item.stringValue?.split("#")
                val amountString = values!![1].replace("Số tiền rút: ", "")
                amount += amountString.toLong()
            }
        }
        mAdapter.notifyDataSetChanged()
        tvCount.text = String.format(" %s", mList.size)
        tvAmount.text = String.format(" %s VNĐ", NumberUtils.formatPriceNumber(amount))
        isLoading = false
    }
}
