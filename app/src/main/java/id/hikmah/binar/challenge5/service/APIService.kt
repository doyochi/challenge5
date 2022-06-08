package id.hikmah.binar.challenge5.service

import id.hikmah.binar.challenge5.model.MovieResponse
import id.hikmah.binar.challenge5.model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("movie/popular")
    suspend fun getAllMovie(
        @Query("api_key") apiKey: String
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Result
}