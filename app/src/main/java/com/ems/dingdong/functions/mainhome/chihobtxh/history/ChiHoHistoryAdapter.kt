package com.ems.dingdong.functions.mainhome.chihobtxh.history

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

import com.ems.dingdong.R
import com.ems.dingdong.model.CommonObject
import com.ems.dingdong.utiles.NumberUtils
import com.ems.dingdong.views.CustomBoldTextView
import com.ems.dingdong.views.CustomMediumTextView
import com.ems.dingdong.views.CustomTextView

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import com.ems.dingdong.model.response.SeaBankHistoryPaymentModel

class ChiHoHistoryAdapter(items: List<SeaBankHistoryPaymentModel>, private val mFilterDone: FilterDone?) : RecyclerView.Adapter<ChiHoHistoryAdapter.HolderView>(), Filterable {

    var listFilter: List<SeaBankHistoryPaymentModel>? = null
        private set
    private val mList: List<SeaBankHistoryPaymentModel>

    init {

        listFilter = items
        mList = items

    }

    override fun getItemCount(): Int {
        return listFilter!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderView {
        return HolderView(LayoutInflater.from(parent.context).inflate(R.layout.item_history_chiho, parent, false))
    }

    override fun onBindViewHolder(holder: HolderView, position: Int) {
        holder.bindView(listFilter!![position])
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                listFilter = if (charString.isEmpty()) {
                    mList
                } else {
                    val filteredList = ArrayList<SeaBankHistoryPaymentModel>()
                    for (row in mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.stringValue?.toLowerCase()!!.contains(charString.toLowerCase())
                                || row.payPostRetRefNumber?.toLowerCase()!!.contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }

                    filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = listFilter
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                listFilter = filterResults.values as ArrayList<SeaBankHistoryPaymentModel>
                if (mFilterDone != null) {
                    var amount: Long = 0
                    for (item in listFilter!!) {

                        if (!TextUtils.isEmpty(item.stringValue)) {
                            val values = item.stringValue?.split("#")
                            val amountString = values!![1].replace("Số tiền rút: ", "")
                            amount += amountString.toLong()
                        }
                    }
                    mFilterDone.getCount(listFilter!!.size, amount)
                }
                notifyDataSetChanged()
            }
        }
    }

    inner class HolderView(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.tv_code)
        lateinit var tvCode: CustomBoldTextView
        @BindView(R.id.tv_time)
        lateinit var tvTime: CustomTextView
        @BindView(R.id.tv_amount)
        lateinit var tvAmount: CustomMediumTextView
        @BindView(R.id.tv_id)
        lateinit var tvId: CustomTextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bindView(model: Any) {
            val item = model as SeaBankHistoryPaymentModel
            val values = item.stringValue?.split("#")
            val amountString = values!![1].replace("Số tiền rút: ", "")
            val gttt = values!![2]
            tvAmount.text = "${NumberUtils.formatPriceNumber(amountString.toLong())} VNĐ"
            tvTime.text = item.pIdDate
            tvId.text = gttt
            tvCode.text = item.payPostRetRefNumber

        }
    }

    interface FilterDone {
        fun getCount(count: Int, amount: Long)
    }
}
