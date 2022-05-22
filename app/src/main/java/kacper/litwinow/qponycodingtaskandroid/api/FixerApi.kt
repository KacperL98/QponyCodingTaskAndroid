package kacper.litwinow.qponycodingtaskandroid.api

import kacper.litwinow.qponycodingtaskandroid.constants.*
import kacper.litwinow.qponycodingtaskandroid.model.Currency
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FixerApi {

    @GET("latest")
    suspend fun getNewestCurrency(
        @Query("access_key") accessKey: String = API_KEY,
        @Query("symbols") symbols: String = listOf(USD, AUD, CAD, PLN).joinToString(),
    ): Response<Currency?>

    @GET("{date}")
    suspend fun getHistoricalCurrencies(
        @Path("date") date: String,
        @Query("access_key") accessKey: String = API_KEY,
        @Query("symbols") symbols: String = listOf(USD, AUD, CAD, PLN).joinToString(),
    ): Response<Currency?>
}
