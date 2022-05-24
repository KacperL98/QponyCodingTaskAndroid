package kacper.litwinow.qponycodingtaskandroid.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kacper.litwinow.qponycodingtaskandroid.api.FixerApi
import kacper.litwinow.qponycodingtaskandroid.extension.RetrofitExtension
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    internal fun provideRetrofitFixerApi(): FixerApi = RetrofitExtension.createAqiWebService()
}
