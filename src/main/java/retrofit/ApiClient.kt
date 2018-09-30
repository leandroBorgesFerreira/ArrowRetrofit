package retrofit

import arrow.effects.IO
import io.reactivex.Observable
import repositories.dto.GithubAnswerDto
import retrofit.effectAdapter.KindedCall
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("search/repositories")
    fun getRepositories(@Query("q") language: String,
                        @Query("sort") order: String,
                        @Query("page") page: Int): Call<GithubAnswerDto>

    @GET("search/repositories")
    fun getRepositoriesObs(@Query("q") language: String,
                           @Query("sort") order: String,
                           @Query("page") page: Int): Observable<GithubAnswerDto>

    @GET("search/repositories")
    fun getRepositoriesIO(@Query("q") language: String,
                          @Query("sort") order: String,
                          @Query("page") page: Int): IO<GithubAnswerDto>

    @GET("search/repositories")
    fun getRepositoriesEffect(@Query("q") language: String,
                              @Query("sort") order: String,
                              @Query("page") page: Int): KindedCall<GithubAnswerDto>
}

fun apiClient(): ApiClient = retrofit().create(ApiClient::class.java)