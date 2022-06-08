package id.hikmah.binar.challenge5.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.hikmah.binar.challenge5.BuildConfig
import id.hikmah.binar.challenge5.model.MovieResponse
import id.hikmah.binar.challenge5.model.Result
import id.hikmah.binar.challenge5.service.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel(private val APIService: APIService): ViewModel() {

    private val _dataSuccess = MutableLiveData<MovieResponse>()
    val dataSuccess: LiveData<MovieResponse> get() = _dataSuccess

    private val _detailSuccess = MutableLiveData<Result>()
    val detailSuccess: LiveData<Result> get() = _detailSuccess

    private val _dataError = MutableLiveData<String>()
    val dataError: LiveData<String> get() = _dataError

//    fun getAllMoviePopular() {
//        APIService.getAllMovie(BuildConfig.API_KEY).enqueue(object :
//            Callback<MovieResponse> {
//            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
//                if (response.isSuccessful) {
//                    if (response.body() != null) {
//                        _dataSuccess.postValue(response.body())
//                    } else {
//                        _dataError.postValue("Data Empty")
//                    }
//                } else {
//                    _dataError.postValue("Data Gagal diambil")
//                }
//                Log.e("onResponse", "TestGetMoviePopular")
//            }
//
//            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
//                _dataError.postValue("Server Bermasalah")
//                Log.e("onFailure", "TestGetMoviePopular")
//            }
//
//        })
//    }

//    fun getDetailMovie(pilem_id: Int) {
//        APIService.getDetailMovie(pilem_id, BuildConfig.API_KEY).enqueue(object :
//            Callback<Result> {
//            override fun onResponse(call: Call<Result>, response: Response<Result>) {
//                if (response.isSuccessful) {
//                    if (response.body() != null) {
//                        _detailSuccess.postValue(response.body())
//                    } else {
//                        _dataError.postValue("Data Empty")
//                    }
//                } else {
//                    _dataError.postValue("Data Gagal diambil")
//                }
//                Log.e("onResponse", "TestGetMoviePopular")
//            }
//
//            override fun onFailure(call: Call<Result>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }

}