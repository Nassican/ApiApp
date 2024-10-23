package com.nassican.apiapp.data.interfaces

import com.nassican.apiapp.data.models.Result
import retrofit2.http.GET

interface ICharacterApi {
    @GET("characters")
    suspend fun getCharacters(): List<Result>
}