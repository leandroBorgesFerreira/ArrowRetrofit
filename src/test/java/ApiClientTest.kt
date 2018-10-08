import effectAdapter.CallK
import retrofit.retrofit
import retrofit2.http.GET

interface ApiClientTest {

    @GET("search/repositories")
    fun test(): CallK<String>
}

fun apiClientTest(): ApiClientTest = retrofit().create(ApiClientTest::class.java)