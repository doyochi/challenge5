package id.hikmah.binar.challenge5.service

import id.hikmah.binar.challenge5.model.MovieItem
import id.hikmah.binar.challenge5.model.ResultMovie
import retrofit2.Call
import retrofit2.http.GET

interface APIService {

    @GET("now_playing?api_key=4269d3c97cce73360841fef62c58ca7b&language=en-US&page=1")
    fun getNowPlaying() : Call<List<ResultMovie>>


}