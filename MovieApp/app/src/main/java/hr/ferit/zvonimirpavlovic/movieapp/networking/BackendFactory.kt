package hr.ferit.zvonimirpavlovic.movieapp.networking

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import hr.ferit.zvonimirpavlovic.movieapp.common.API_KEY
import hr.ferit.zvonimirpavlovic.movieapp.common.AUTHENTICATION
import hr.ferit.zvonimirpavlovic.movieapp.common.BASE_URL
import hr.ferit.zvonimirpavlovic.movieapp.networking.interactors.MovieInteractor
import hr.ferit.zvonimirpavlovic.movieapp.networking.interactors.MovieInteractorImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendFactory {

    private var retrofit: Retrofit? = null
    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private fun provideAuthenticationInterceptor() = Interceptor {
        var request = it.request()
        val url = request.url().newBuilder().addQueryParameter(AUTHENTICATION, API_KEY).build()
        request = request.newBuilder().url(url).build()
        it.proceed(request)
    }

    private val httpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(provideAuthenticationInterceptor())
            .build()

    private val client: Retrofit? = if (retrofit == null) createRetrofit() else retrofit

    private fun createRetrofit(): Retrofit? {
        retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

    private fun getService(): MovieApiService = this.client!!.create(MovieApiService::class.java)

    fun getMovieInteractor(): MovieInteractor = MovieInteractorImpl(getService())
}