package com.ems.dingdong.observer

interface DisplayElement<T> {
    fun display(data: T)
}