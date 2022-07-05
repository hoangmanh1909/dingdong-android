package com.ems.dingdong.observer

interface Observer<T> {
    fun update(data: T)
}