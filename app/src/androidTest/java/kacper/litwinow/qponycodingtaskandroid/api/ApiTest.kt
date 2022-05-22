package kacper.litwinow.qponycodingtaskandroid.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.squareup.moshi.Moshi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kacper.litwinow.qponycodingtaskandroid.model.Rates
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsNull
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@HiltAndroidTest
class ApiTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var fixerApi: FixerApi

    lateinit var mockWebServer: MockWebServer

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        fixerApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
            .create(FixerApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun should_fetch_newest_data_given_200_response() = runBlocking {
        enqueueResponseCode200()
        val currency = fixerApi.getNewestCurrency().body()
        val request = mockWebServer.takeRequest()

        MatcherAssert.assertThat(request.path, `is`(MocksApi.latestMockApiPath))
        MatcherAssert.assertThat(currency, IsNull.notNullValue())
        MatcherAssert.assertThat(currency?.success, `is`(true))
        MatcherAssert.assertThat(currency?.base, `is`("EUR"))
        MatcherAssert.assertThat(currency?.date, `is`("2022-05-22"))
        MatcherAssert.assertThat(currency?.timestamp, `is`(1587734561))
        MatcherAssert.assertThat(
            currency?.rates,
            `is`(
                Rates(
                    aud = 1.502456,
                    cad = 1.366870,
                    pln = 4.632246,
                    usd = 1.067331
                )
            )
        )
    }

    @Test
    fun should_fetch_historical_data_given_200_response() = runBlocking {
        enqueueResponseCode200()
        val currency = fixerApi.getHistoricalCurrencies("2022-05-22").body()
        val request = mockWebServer.takeRequest()

        MatcherAssert.assertThat(request.path, `is`(MocksApi.historicalMockApiPath))
        MatcherAssert.assertThat(currency, IsNull.notNullValue())
        MatcherAssert.assertThat(currency?.success, `is`(true))
        MatcherAssert.assertThat(currency?.base, `is`("EUR"))
        MatcherAssert.assertThat(currency?.date, `is`("2022-05-22"))
        MatcherAssert.assertThat(currency?.timestamp, `is`(1587734561))
        MatcherAssert.assertThat(
            currency?.rates,
            `is`(
                Rates(
                    aud = 1.502456,
                    cad = 1.366870,
                    pln = 4.632246,
                    usd = 1.067331
                )
            )
        )
    }

    @Test
    fun should_fetch_historical_data_given_500_null_response() = runBlocking {
        enqueueResponseCode500()

        val currency = fixerApi.getHistoricalCurrencies("2022-05-22").body()
        MatcherAssert.assertThat(currency, IsNull())
    }

    @Test
    fun should_fetch_newest_data_given_500_null_response() = runBlocking {
        enqueueResponseCode500()

        val currency = fixerApi.getNewestCurrency().body()
        MatcherAssert.assertThat(currency, IsNull())
    }

    private fun enqueueResponseCode200() {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(
                    MocksApi.mockApi
                )
        )
    }

    private fun enqueueResponseCode500() {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(500)
        )
    }
}
