package id.hikmah.binar.challenge5.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {

    //base url untuk hit API, cons val
    const val BASE_URL = "https://api.themoviedb.org/3/movie/"

    // untuk interceptor di level body
    private val logging : HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    //create client untuk retrofit
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    //create instance Apiservice pakai lazy, supaya bikin dan seterusnya bakal manggil dari memory (yang udah dibikin)
    val instance : APIService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(APIService::class.java)
    }
}