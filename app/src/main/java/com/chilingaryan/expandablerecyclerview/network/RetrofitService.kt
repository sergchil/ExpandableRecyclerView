package com.chilingaryan.expandablerecyclerview.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by sergey on 12/24/17.
 */

interface RetrofitService {
    @GET
    fun getDummyJson(@Url url: String): Call<DummyResponse>

    companion object {
        private fun create(): RetrofitService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://json-schema.org/")
                    .build()

            return retrofit.create(RetrofitService::class.java)
        }

        val instance: RetrofitService by lazy {
            RetrofitService.create()
        }
    }

}
