package com.example.admin.moogle.factory

import com.example.admin.moogle.model.albums.Albums
import com.example.admin.moogle.model.artists.Artists
import com.example.admin.moogle.model.tracks.Tracks
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class SearchFactory {
    companion object {
        const val BASE_URL = "https://ws.audioscrobbler.com/"
        const val API_KEY = "2ee1986ec8c312f50dd002630a01d6b0"
        const val ALBUM_SEARCH = "album.search"
        const val TRACK_SEARCH = "track.search"
        const val ARTIST_SEARCH = "artist.search"
        const val FORMAT = "json"
    }

    private var searchService: SearchService

    init {
        searchService = instantiateSearchService(getRetrofitInstance())
    }

    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private fun instantiateSearchService(retrofitInstance: Retrofit): SearchService {
        return retrofitInstance.create(SearchService::class.java)
    }

    fun searchForAlbum(searchText: String): Observable<Albums> {
        return searchService.performAlbumSearch(ALBUM_SEARCH, searchText, API_KEY, FORMAT)
    }
    fun searchForSong(searchText: String): Observable<Tracks>{
        return searchService.performSongSearch(TRACK_SEARCH, searchText, API_KEY, FORMAT)
    }
    fun searchForArtist(searchText: String): Observable<Artists>{
        return searchService.performArtistSearch(ARTIST_SEARCH, searchText, API_KEY, FORMAT)
    }
}