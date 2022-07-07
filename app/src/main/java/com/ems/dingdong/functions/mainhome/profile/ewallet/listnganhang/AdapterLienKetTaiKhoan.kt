package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.ems.dingdong.R
import com.ems.dingdong.model.response.DanhSachTaiKhoanRespone
import com.ems.dingdong.model.thauchi.DanhSachNganHangRepsone

class AdapterLienKetTaiKhoan(val listLienKet : ArrayList<DanhSachNganHangRepsone>, val mContext: Context, val itemClick:ItemClickListener) :
    RecyclerView.Adapter<AdapterLienKetTaiKhoan.LienKetTaiKhoanViewHolder>(){

    private val filterList by lazy { ArrayList<DanhSachNganHangRepsone>() }

     fun filterSingleData(){
        val distinctList = listLienKet.distinctBy{
            it.groupType
        }
        filterList.clear()
         distinctList.let {
             filterList.addAll(it)
         }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LienKetTaiKhoanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_danhsach_lienket, parent, false)
        return LienKetTaiKhoanViewHolder(view)
    }

    override fun onBindViewHolder(holder: LienKetTaiKhoanViewHolder, position: Int) {
        val item = filterList.get(position)
        holder.binData(item)
    }

    override fun getItemCount(): Int  = filterList.size

    inner class LienKetTaiKhoanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        @BindView(R.id.img_logo)
        lateinit var imgLogo: AppCompatImageView

        @BindView(R.id.tv_name)
        lateinit var tvName: AppCompatTextView

        @BindView(R.id.rootView)
        lateinit var rootView: ConstraintLayout

        init {
            ButterKnife.bind(this, itemView)
        }

        fun binData(item: DanhSachNganHangRepsone) {
            try {
                Glide.with(mContext).load(item.Logo).into(imgLogo)
                tvName.text = item.GroupName
                rootView.setOnClickListener {
                    itemClick.onItemClicked(item)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
    interface ItemClickListener{
        fun onItemClicked(item:DanhSachNganHangRepsone)
    }
}