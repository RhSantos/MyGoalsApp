package com.rh.mygoalsapp.data.api

import com.rh.mygoalsapp.util.Constants.addressRestfulBaseURL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

    val api: AdressServiceAPI by lazy {
        Retrofit.Builder()
            .baseUrl(addressRestfulBaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AdressServiceAPI::class.java)
    }
}