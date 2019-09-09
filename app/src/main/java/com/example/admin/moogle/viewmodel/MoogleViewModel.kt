package com.example.admin.moogle.viewmodel

import android.arch.lifecycle.ViewModel
import com.example.admin.moogle.factory.SearchFactory
import com.example.admin.moogle.model.albums.Album
import com.example.admin.moogle.model.albums.Albums
import com.example.admin.moogle.model.artists.Artist
import com.example.admin.moogle.model.artists.Artists
import com.example.admin.moogle.model.tracks.Track
import com.example.admin.moogle.model.tracks.Tracks
import com.example.admin.moogle.util.rx.CustomObserveOptionalValue
import com.example.admin.moogle.util.rx.ErrorLog
import com.example.admin.moogle.util.rx.Optional
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoogleViewModel : ViewModel() {

    private val searchFactory: SearchFactory = SearchFactory()

    private var artistResults: BehaviorRelay<Optional<Artists>> = BehaviorRelay.create()
    private var songResults: BehaviorRelay<Optional<Tracks>> = BehaviorRelay.create()
    private var albumResults: BehaviorRelay<Optional<Albums>> = BehaviorRelay.create()


    fun getArtists(): Observable<Artists> {
        return artistResults.compose(CustomObserveOptionalValue())
    }

    fun getSongs(): Observable<Tracks> {
        return songResults.compose(CustomObserveOptionalValue())
    }

    fun getAlbums(): Observable<Albums> {
        return albumResults.compose(CustomObserveOptionalValue())
    }

    fun searchMediaText(searchText: String, page: Int) {
        //Trigger album search
        searchFactory.searchForAlbum(searchText, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ albumSearchResults ->
                    albumResults.accept(Optional.Some(albumSearchResults))
                }, { throwable ->
                    ErrorLog.logError(throwable)
                })

        //Trigger song search
        searchFactory.searchForSong(searchText, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ songSearchResults ->
                    songResults.accept(Optional.Some(songSearchResults))
                }, { throwable ->
                    ErrorLog.logError(throwable)
                })

        //Trigger artist search
        searchFactory.searchForArtist(searchText, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ artistSearchResults ->
                    artistResults.accept(Optional.Some(artistSearchResults))
                }, { throwable ->
                    ErrorLog.logError(throwable)
                })
    }

//    fun loadMoreArtists(){
//        if(finalSearchText == this.searchText) {
//            artistPage++
//            searchMediaText(finalSearchText, artistPage)
//        }
//    }
//
//    fun loadMoreSongs(){
//
//    }
//
//    fun loadMoreAlbums(){
//
//    }
}