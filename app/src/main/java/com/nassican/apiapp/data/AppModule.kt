package com.nassican.apiapp.data

import com.nassican.apiapp.data.interfaces.ICharacterApi
import com.nassican.apiapp.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

    fun provideCharacters(): ICharacterApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ICharacterApi::class.java)
    }

    // Print the URL


}