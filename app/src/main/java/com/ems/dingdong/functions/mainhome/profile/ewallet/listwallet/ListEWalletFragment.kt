package com.ems.dingdong.functions.mainhome.profile.ewallet.listwallet

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.core.base.viper.ViewFragment
import com.ems.dingdong.R
import com.ems.dingdong.functions.mainhome.chihobtxh.otp.OtpChiHoBtxhFragment
import com.ems.dingdong.model.thauchi.DanhSachNganHangRepsone
import com.ems.dingdong.utiles.Constants.EXTRA_TYPE_FRAGMENT

class ListEWalletFragment: ViewFragment<ListEWalletContract.Presenter>(), ListEWalletContract.View {

    private lateinit var adapter : ListEWalletAdapter
    @BindView(R.id.rcView)
    lateinit var rcView:RecyclerView
    @BindView(R.id.img_back)
    lateinit var imgBack:ImageView
    @BindView(R.id.tv_e_wallet_or_bank)
    lateinit var tvEWalletOrBank:AppCompatTextView

    companion object {
        const val LIST_E_WALLET_FRAGMENT = 1
        const val LIST_BANK_FRAGMENT = 2
        val instance: ListEWalletFragment
            get() = ListEWalletFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_list_e_wallet


    override fun initLayout() {
        super.initLayout()
        initAdapter()
        imgBack.setOnClickListener {
            mPresenter.back()
        }
        initView()

    }

    private fun initAdapter(){
        adapter = ListEWalletAdapter(requireContext(),mPresenter.typeFragment()){ item,position->
            if (item.groupType==1){
                if (item.bankName=="Ví điện tử PostPay") mPresenter.showEWallet(1)
                else mPresenter.showEWallet(2)
            }
        }
        rcView.adapter = adapter
    }
    private fun initView(){
        if (mPresenter.typeFragment() == LIST_BANK_FRAGMENT) tvEWalletOrBank.text = "Tài khoản thấu chi NHTM"
        else tvEWalletOrBank.text = "Ví điện tử"
    }

    override fun showListBank(list: List<DanhSachNganHangRepsone>) {
        try {
            if (mPresenter.typeFragment()==LIST_E_WALLET_FRAGMENT){
                list.filter {
                    it.groupType == 1
                }.let {
                    adapter.submitList(it)
                }

            }else{
                list.filter {
                    it.groupType == 2
                }.let {
                    adapter.submitList(it)
                }
            }

        }catch (e:Exception){
            e.printStackTrace()
        }


    }
}