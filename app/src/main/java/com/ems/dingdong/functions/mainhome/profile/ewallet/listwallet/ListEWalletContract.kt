package com.ems.dingdong.functions.mainhome.profile.ewallet.listwallet

import com.core.base.viper.interfaces.IInteractor
import com.core.base.viper.interfaces.IPresenter
import com.core.base.viper.interfaces.PresentView
import com.ems.dingdong.model.SimpleResult
import com.ems.dingdong.model.thauchi.DanhSachNganHangRepsone
import io.reactivex.Single

interface ListEWalletContract {
    interface Interactor : IInteractor<Presenter> {
        fun getDanhSachNganHang(): Single<SimpleResult>
    }

    interface View : PresentView<Presenter>{
        fun showListBank(list: List<DanhSachNganHangRepsone>)
    }

    interface Presenter : IPresenter<View, Interactor> {
//        fun getSeaBankPaymentRequest(): SeaBankPaymentRequest
//        fun getSeaBankInquiryModel(): SeaBankInquiryModel
//        fun payment(otp: String)
//
//        fun int(): String;

        fun getDanhSachNganHang()

        fun typeFragment ():Int

        fun showEWallet(typeEWallet:Int)


    }
}