package retrofit

import arrow.Kind
import arrow.effects.IO
import repositories.dto.GithubAnswerDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient<F> {

    @GET("search/repositories")
    fun getRepositories(@Query("q") language: String,
                        @Query("sort") order: String,
                        @Query("page") page: Int): Call<GithubAnswerDto>

    @GET("search/repositories")
    fun getRepositoriesIO(@Query("q") language: String,
                          @Query("sort") order: String,
                          @Query("page") page: Int): IO<GithubAnswerDto>

    @GET("search/repositories")
    fun <F> getRepositoriesEffect(@Query("q") language: String,
                                  @Query("sort") order: String,
                                  @Query("page") page: Int): Kind<F, GithubAnswerDto>
}

fun apiClient(): ApiClient = retrofit().create(ApiClient::class.java)