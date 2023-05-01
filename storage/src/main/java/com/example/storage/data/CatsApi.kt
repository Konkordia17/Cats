package com.example.storage.data

import com.example.storage.CatModel
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "live_lskvLXBaYSdInMYgdl1B2cyR5o3bR8Hso0sc0Fs1tRmnlFjLP08VYjmh9w3OLqEb"

interface CatsApi {

    @GET("/v1/images/search")
    suspend fun getCatsByPage(
        @Query("page") page: Int,
        @Query("api_key") api_key: String = API_KEY
    ): List<CatModel>
}