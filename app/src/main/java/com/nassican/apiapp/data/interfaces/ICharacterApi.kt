package com.nassican.apiapp.data.interfaces

import com.nassican.apiapp.data.models.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface ICharacterApi {
    @GET("characters")
    suspend fun getCharacters(@Query("page") page: Int, @Query("pageSize") pageSize: Int): List<Result>
}