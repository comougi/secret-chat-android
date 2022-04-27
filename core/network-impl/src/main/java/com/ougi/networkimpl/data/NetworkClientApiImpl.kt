package com.ougi.networkimpl.data

import com.ougi.networkapi.data.NetworkClientApi
import com.ougi.networkapi.data.NetworkService
import retrofit2.Retrofit
import javax.inject.Inject

internal class NetworkClientApiImpl @Inject constructor(private val retrofit: Retrofit) :
    NetworkClientApi {

    override fun <S : NetworkService> create(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}