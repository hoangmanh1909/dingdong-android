package com.ems.dingdong.functions.mainhome.profile.ewallet.listwallet

import com.core.base.viper.Interactor
import com.ems.dingdong.functions.mainhome.chihobtxh.otp.OtpChiHoBtxhContract
import com.ems.dingdong.model.SimpleResult
import com.ems.dingdong.model.request.DanhSachTaiKhoanRequest
import com.ems.dingdong.network.NetWorkControllerGateWay
import io.reactivex.Single

internal class ListEWalletInteractor(presenter: ListEWalletContract.Presenter) : Interactor<ListEWalletContract.Presenter>(presenter), ListEWalletContract.Interactor {
    override fun getDanhSachNganHang(): Single<SimpleResult> {
        return NetWorkControllerGateWay.getDanhSachNganHang()
    }
}