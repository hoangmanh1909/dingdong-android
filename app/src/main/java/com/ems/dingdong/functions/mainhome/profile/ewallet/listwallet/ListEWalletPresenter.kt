package com.ems.dingdong.functions.mainhome.profile.ewallet.listwallet

import android.util.Log
import com.core.base.viper.Presenter
import com.core.base.viper.interfaces.ContainerView
import com.ems.dingdong.functions.mainhome.profile.ewallet.EWalletPresenter
import com.ems.dingdong.model.SimpleResult
import com.ems.dingdong.model.thauchi.DanhSachNganHangRepsone
import com.ems.dingdong.network.NetWorkController
import com.ems.dingdong.utiles.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit


class ListEWalletPresenter(containerView: ContainerView,val typeFragment: Int) : Presenter<ListEWalletContract.View, ListEWalletContract.Interactor>(containerView), ListEWalletContract.Presenter {


    override fun onCreateView(): ListEWalletContract.View {
        return ListEWalletFragment.instance
    }

    override fun start() {
        // Start getting data here
        try {
            getDanhSachNganHang()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateInteractor(): ListEWalletContract.Interactor {
        return ListEWalletInteractor(this)
    }

    override fun getDanhSachNganHang() {
        mView.showProgress()
        mInteractor.getDanhSachNganHang()
            .delay(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { simpleResult: SimpleResult? ->
                if (simpleResult != null) {
                    if (simpleResult.errorCode == "00") {
                        val list =
                            NetWorkController.getGson().fromJson(
                                simpleResult.data,
                                Array<DanhSachNganHangRepsone>::class.java
                            )
                        val list1 =
                            Arrays.asList(*list)
                        mView.showListBank(list1)
                        mView.hideProgress()
                    } else Toast.showToast(viewContext, simpleResult.message)
                    mView.hideProgress()
                }
            }
    }

    override fun typeFragment(): Int  = typeFragment
    override fun showEWallet(typeFragment: Int) {
        EWalletPresenter(mContainerView).setTypeEWallet(typeFragment).pushView()
    }

}