package com.ems.dingdong.observer

import com.ems.dingdong.enumClass.StateEWallet
import com.ems.dingdong.model.response.SmartBankLink

object EWalletData : Subject<Any> {

    var stateEWallet: StateEWallet = StateEWallet.NOTHING

    var smartBankLink : SmartBankLink?=null


    private val observers: ArrayList<Observer<Any>> = arrayListOf()

    @JvmStatic
    fun setMeasurements(stateEWallet: StateEWallet,smartBankLink: SmartBankLink?) {
        this.stateEWallet = stateEWallet
        this.smartBankLink = smartBankLink
        onMeasurementsChanged()
    }

    private fun onMeasurementsChanged() {
        notifyObservers()
    }

    override fun registerObserver(observer: Observer<Any>) {
        observers.add(observer)
    }

    override fun removeObserver(observer: Observer<Any>) {
        observers.remove(observer)
    }

    override fun notifyObservers() {
        observers.forEach {
            it.update(this)
        }
    }
}