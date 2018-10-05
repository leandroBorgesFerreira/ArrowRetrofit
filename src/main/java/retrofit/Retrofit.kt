package retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import effectAdapter.Effect2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rxjava2.RxJava2CallAdapterFactory

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
        .addCallAdapterFactory(Effect2CallAdapterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

private fun provideOkHttpClientOAuth() : OkHttpClient = httpClientBuilder().build()

fun retrofit(): Retrofit = getRetrofitBuilderDefaults().client(provideOkHttpClientOAuth()).build()