package com.example.admin.moogle.factory

import com.example.admin.moogle.model.albums.Albums
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("2.0/?")
    fun performAlbumSearch(@Query("method") searchType: String,
                           @Query("album") searchString: String,
                           @Query("api_key") apiKey: String,
                           @Query("format")format: String) : Observable<Albums>//Observable<Results>
}