package com.ougi.networkapi.data

interface NetworkClientApi {
    fun <S : NetworkService> create(serviceClass: Class<S>): S
}