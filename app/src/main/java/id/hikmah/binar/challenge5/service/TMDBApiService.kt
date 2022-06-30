package id.hikmah.binar.challenge5.service

import id.hikmah.binar.challenge5.model.MoviePopularResponse
import id.hikmah.binar.challenge5.model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApiService {
    @GET("movie/popular")
    fun getAllMovie(
        @Query("api_key") key: String
    ): Call<MoviePopularResponse>

    @GET("movie/{movie_id}")
    fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") key: String
    ): Call<Result>
}