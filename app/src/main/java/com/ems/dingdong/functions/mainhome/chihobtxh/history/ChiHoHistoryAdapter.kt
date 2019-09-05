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

class ChiHoHistoryAdapter(items: List<CommonObject>, private val mFilterDone: FilterDone?) : RecyclerView.Adapter<ChiHoHistoryAdapter.HolderView>(), Filterable {

    var listFilter: List<CommonObject>? = null
        private set
    private val mList: List<CommonObject>

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
                    val filteredList = ArrayList<CommonObject>()
                    for (row in mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.code.toLowerCase().contains(charString.toLowerCase())) {
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
                listFilter = filterResults.values as ArrayList<CommonObject>
                if (mFilterDone != null) {
                    var amount: Long = 0
                    for (item in listFilter!!) {
                        if (!TextUtils.isEmpty(item.amount))
                            amount += java.lang.Long.parseLong(item.amount)
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
            val item = model as CommonObject


        }
    }

    interface FilterDone {
        fun getCount(count: Int, amount: Long)
    }
}
