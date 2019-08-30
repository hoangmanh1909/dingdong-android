package com.ems.dingdong.functions.mainhome.chihobtxh.check

import com.core.base.viper.Interactor
import com.ems.dingdong.callback.CommonCallback
import com.ems.dingdong.model.request.BankAccountNumberRequest
import com.ems.dingdong.model.response.BankAccountNumberResponse
import com.ems.dingdong.network.NetWorkController

/**
 * The CheckReference interactor
 */
internal class CheckReferenceInteractor(presenter: CheckReferenceContract.Presenter) : Interactor<CheckReferenceContract.Presenter>(presenter), CheckReferenceContract.Interactor {
    override fun getBankAccountNumber(bankAccountNumberRequest: BankAccountNumberRequest, callback: CommonCallback<BankAccountNumberResponse>) {
        NetWorkController.getBankAccountNumber(bankAccountNumberRequest, callback)
    }

}
