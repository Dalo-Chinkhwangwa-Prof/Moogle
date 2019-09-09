package com.example.admin.moogle.factory

import com.example.admin.moogle.model.albums.Albums
import com.example.admin.moogle.model.artists.Artists
import com.example.admin.moogle.model.tracks.Tracks
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    //Album search
    @GET("2.0/?")
    fun performAlbumSearch(@Query("method") searchType: String,
                           @Query("album") searchString: String,
                           @Query("api_key") apiKey: String,
                           @Query("format") format: String,
                           @Query("limit") limit: Int,
                           @Query("page") page: Int): Observable<Albums>

    //Track search
    @GET("2.0/?")
    fun performSongSearch(@Query("method") searchType: String,
                          @Query("track") searchString: String,
                          @Query("api_key") apiKey: String,
                          @Query("format") format: String,
                          @Query("limit") limit: Int,
                          @Query("page") page: Int): Observable<Tracks>

    //Artist search
    @GET("2.0/?")
    fun performArtistSearch(@Query("method") searchType: String,
                            @Query("artist") searchString: String,
                            @Query("api_key") apiKey: String,
                            @Query("format") format: String,
                            @Query("limit") limit: Int,
                            @Query("page") page: Int): Observable<Artists>
}