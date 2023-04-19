package nz.co.trademe.techtest.injection.module

import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import nz.co.trademe.wrapper.TradeMeApi
import nz.co.trademe.wrapper.TradeMeApiService
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

@Module
@Suppress("unused")
object NetworkModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideTradeMeApi(): TradeMeApiService {
        return TradeMeApi().get(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
    }
}