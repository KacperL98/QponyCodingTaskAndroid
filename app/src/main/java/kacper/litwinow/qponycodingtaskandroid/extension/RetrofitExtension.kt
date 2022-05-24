package kacper.litwinow.qponycodingtaskandroid.extension

import kacper.litwinow.qponycodingtaskandroid.constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitExtension {

    inline fun <reified T> createAqiWebService(url: String): T =
        create(url, createOkHttpClient())

    inline fun <reified T> createAqiWebService(): T =
        createAqiWebService(BASE_URL)

    inline fun <reified T> create(baseUrl: String, okHttpClient: OkHttpClient): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(T::class.java)

    fun createOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            addInterceptor(logging)
        }.build()
}