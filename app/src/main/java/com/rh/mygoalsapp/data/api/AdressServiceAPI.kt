package com.rh.mygoalsapp.data.api

import com.rh.mygoalsapp.data.models.AddressServiceDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AdressServiceAPI {

    @GET("{cep}/json/")
    suspend fun getAddressInfoByPostCode(@Path("cep") cep:String) : Response<AddressServiceDto>

}