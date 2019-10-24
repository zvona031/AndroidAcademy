package hr.ferit.zvonimirpavlovic.taskie.networking

import hr.ferit.zvonimirpavlovic.taskie.common.BASE_URL
import hr.ferit.zvonimirpavlovic.taskie.common.KEY_AUTHORIZATION
import hr.ferit.zvonimirpavlovic.taskie.networking.interactor.TaskieInteractor
import hr.ferit.zvonimirpavlovic.taskie.networking.interactor.TaskieInteractorImpl
import hr.ferit.zvonimirpavlovic.taskie.prefs.SharedPrefsHelper
import hr.ferit.zvonimirpavlovic.taskie.prefs.provideSharedPrefs
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendFactory {

    private var retrofit: Retrofit? = null
    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val prefs = provideSharedPrefs()

    private fun provideAuthenticationInterceptor(preferences: SharedPrefsHelper) = Interceptor {
        val authentication = it.request().newBuilder()
            .addHeader(KEY_AUTHORIZATION, preferences.getUserToken())
            .build()
        it.proceed(authentication)
    }

    private val httpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(provideAuthenticationInterceptor(prefs))
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

    private fun getService(): TaskieApiService = this.client!!.create(TaskieApiService::class.java)

    fun getTaskieInteractor(): TaskieInteractor = TaskieInteractorImpl(getService())
}
