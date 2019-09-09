package com.example.admin.moogle.viewmodel

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.admin.moogle.factory.SearchFactory
import com.example.admin.moogle.model.albums.Albums
import com.example.admin.moogle.model.artists.Artists
import com.example.admin.moogle.model.tracks.Tracks
import com.example.admin.moogle.util.rx.CustomObserveOptionalValue
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

    fun searchMediaText(searchText: String) {

        //Trigger album search
        searchFactory.searchForAlbum(searchText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ albumSearchResults ->
                    Log.d("TAG_X", "Album results are in VM")
                    albumResults.accept(Optional.Some(albumSearchResults))
                }, { throwable ->
                    Log.d("TAG_E", "An error occured. ${throwable.message}")
                })

        //Trigger song search
        searchFactory.searchForSong(searchText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ songSearchResults ->
                    Log.d("TAG_X", "Song results are in VM")
                    songResults.accept(Optional.Some(songSearchResults))
                }, { throwable ->
                    Log.d("TAG_E", "An error occured. ${throwable.message}")
                })

        //Trigger artist search
        searchFactory.searchForArtist(searchText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ artistSearchResults ->
                    Log.d("TAG_X", "Artist results are in VM")
                    artistResults.accept(Optional.Some(artistSearchResults))
                }, { throwable ->
                    Log.d("TAG_E", "An error occured. ${throwable.message}")
                })
    }
}