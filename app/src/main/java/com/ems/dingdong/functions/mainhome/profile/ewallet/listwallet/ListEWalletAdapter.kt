package com.ems.dingdong.functions.mainhome.profile.ewallet.listwallet

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.ems.dingdong.R
import com.ems.dingdong.model.thauchi.DanhSachNganHangRepsone
import com.ems.dingdong.utiles.loadImageWithCustomCorners
import com.ems.dingdong.utiles.setSingleClick
import java.util.concurrent.Executors

class ListEWalletAdapter(
    var context: Context,
    val typeFragment:Int,
    val itemClickListener: (item: DanhSachNganHangRepsone, position: Int) -> Unit
) : ListAdapter<DanhSachNganHangRepsone, RecyclerView.ViewHolder>(
    AsyncDifferConfig.Builder<DanhSachNganHangRepsone>(ListEWalletCallBack())
        .setBackgroundThreadExecutor(
            Executors.newSingleThreadExecutor()
        ).build()
) {
    companion object{
        const val LIST_E_WALLET_FRAGMENT = 1
        const val LIST_BANK_FRAGMENT = 2
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (typeFragment) {
            LIST_E_WALLET_FRAGMENT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_e_wallet, parent, false)
                EWalletViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_bank, parent, false)
                BankViewHolder(view)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EWalletViewHolder) {
            holder.apply {
                val item = getItem(position)
                holder.binData(item,holder.adapterPosition)
            }
        }
        if (holder is BankViewHolder) {
            holder.apply {
                val item = getItem(position)
                holder.binData(item)
            }
        }

    }


    inner class EWalletViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.img_wallet)
        lateinit var imgWallet: AppCompatImageView

        @BindView(R.id.tv_name_wallet)
        lateinit var tvNameWallet: AppCompatTextView

        @BindView(R.id.status_wallet)
        lateinit var statusWallet: AppCompatTextView

        @BindView(R.id.rootView)
        lateinit var rootView: ConstraintLayout



        init {
            ButterKnife.bind(this, itemView)
        }

        fun binData(item: DanhSachNganHangRepsone,position: Int) {
            try {
                tvNameWallet.text = item.bankName
                imgWallet.loadImageWithCustomCorners(item.groupLogo, 1)

                rootView.setSingleClick {
                    itemClickListener(item,position)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    inner class BankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.img_bank)
        lateinit var imgBank: AppCompatImageView

        @BindView(R.id.tv_name_bank)
        lateinit var tvNameBank: AppCompatTextView

        @BindView(R.id.tv_bank)
        lateinit var tvBank: AppCompatTextView


        init {
            ButterKnife.bind(this, itemView)
        }

        fun binData(item: DanhSachNganHangRepsone) {
            try {
                tvNameBank.text = item.bankName
                imgBank.loadImageWithCustomCorners(item.groupLogo, 1)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    class ListEWalletCallBack : DiffUtil.ItemCallback<DanhSachNganHangRepsone>() {

        override fun areItemsTheSame(
            oldItem: DanhSachNganHangRepsone,
            newItem: DanhSachNganHangRepsone
        ): Boolean {
            return oldItem.BankName == newItem.BankName
        }

        override fun areContentsTheSame(
            oldItem: DanhSachNganHangRepsone,
            newItem: DanhSachNganHangRepsone
        ): Boolean {
            return oldItem == newItem
        }

    }


}