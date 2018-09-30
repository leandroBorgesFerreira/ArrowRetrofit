package retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit.effectAdapter.Effect2CallAdapterFactory
import retrofit.rxAdapter.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val baseUrl = "https://api.github.com/"

private fun logInterceptor() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

private fun httpClientBuilder(): OkHttpClient.Builder =
    OkHttpClient.Builder().apply {
        this.addInterceptor(logInterceptor())
    }

private fun getRetrofitBuilderDefaults() =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addCallAdapterFactory(Effect2CallAdapterFactory())

private fun provideOkHttpClientOAuth() : OkHttpClient = httpClientBuilder().build()

fun retrofit(): Retrofit = getRetrofitBuilderDefaults().client(provideOkHttpClientOAuth()).build()