package com.example.admin.moogle.viewmodel

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.admin.moogle.factory.SearchFactory
import com.example.admin.moogle.model.albums.Albums
import com.example.admin.moogle.model.albums.Results
import com.example.admin.moogle.util.rx.CustomObserveOptionalValue
import com.example.admin.moogle.util.rx.Optional
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoogleViewModel : ViewModel() {

    private val searchFactory: SearchFactory = SearchFactory()

    private var artistResults: BehaviorRelay<Optional<String>> = BehaviorRelay.create()
    private var musicResults: BehaviorRelay<Optional<String>> = BehaviorRelay.create()
    private var albumResults: BehaviorRelay<Optional<Albums>> = BehaviorRelay.create()


    fun getArtists(): Observable<String> {
        return artistResults.compose(CustomObserveOptionalValue())
    }

    fun getMusic(): Observable<String> {
        return musicResults.compose(CustomObserveOptionalValue())
    }

    fun getAlbums(): Observable<Albums> {
        return albumResults.compose(CustomObserveOptionalValue())
    }

    fun searchMediaText(searchText: String) {
        // TODO: Search for text in all three areas...
        Log.d("TAG_X", "SEARCHING : $searchText")
        searchFactory.searchForArtist(searchText)
        /*        .enqueue(object: Callback<Albums>{
                    override fun onFailure(call: Call<Albums>?, t: Throwable?) {
                        Log.d("TAG_X", call?.request()?.url().toString())
                    }

                    override fun onResponse(call: Call<Albums>?, response: Response<Albums>?) {
                        Log.d("TAG_X", "Results are in ${response?.body()?.results?.albummatches?.album?.size} url: ${call?.request()?.url().toString()}")
                    }

                })*/
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ albumSearchResults ->
                    albumResults.accept(Optional.Some(albumSearchResults))
                }, { throwable ->
                    Log.d("TAG_E", "An error occured. ${throwable.message}")
                })
    }
}