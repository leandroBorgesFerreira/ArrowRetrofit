package retrofit

import effectAdapter.Effect2CallAdapterFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val baseUrl = "https://api.github.com/"

private fun logInterceptor() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

private fun getRetrofitBuilderDefaults() = Retrofit.Builder().baseUrl(baseUrl)

private fun provideOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder().apply {
        this.addInterceptor(logInterceptor())
    }.build()

private fun configRetrofit(retrofitBuilder: Retrofit.Builder) =
    retrofitBuilder
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(Effect2CallAdapterFactory.create())
        .client(provideOkHttpClient())

private fun getRetrofitBuilderDefaults(baseUrl: HttpUrl) = Retrofit.Builder().baseUrl(baseUrl)

fun retrofit(): Retrofit = configRetrofit(getRetrofitBuilderDefaults()).build()

fun retrofit(baseUrl: HttpUrl): Retrofit = configRetrofit(getRetrofitBuilderDefaults(baseUrl)).build()