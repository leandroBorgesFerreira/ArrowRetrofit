package effectAdapter

import arrow.effects.IO
import arrow.effects.ObservableK
import arrow.effects.async
import arrow.effects.fix
import arrow.effects.monadDefer
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit.retrofit

@RunWith(JUnit4::class)
class ProcCallBackTest {

    lateinit var server: MockWebServer
    lateinit var baseUrl: HttpUrl

    @Before
    fun setUp() {
        server = MockWebServer().apply {
            enqueue(MockResponse().setBody("\"hello, world!\"").setResponseCode(200))
            start()
        }

        baseUrl = server.url("/")
    }

    @Test
    fun `should be able to parse answer with ForIO`() {
        val result = retrofit(baseUrl)
            .create(ApiClientTest::class.java)
            .test()
            .async(IO.async())
            .fix()
            .unsafeRunSync()

        assertEquals(result, "hello, world!")
    }

    @Test
    fun `should be able to parse answer with ForObservableK`() {
        retrofit(baseUrl)
            .create(ApiClientTest::class.java)
            .test()
            .defer(ObservableK.monadDefer())
            .fix()
            .observable
            .test()
            .assertValue("hello, world!")
    }


    @Test
    fun `should be able to parse answer with IO`() {
        val result = retrofit(baseUrl)
            .create(ApiClientTest::class.java)
            .testIO()
            .unsafeRunSync()

        assertEquals(result, "hello, world!")
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}

