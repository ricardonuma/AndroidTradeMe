package nz.co.trademe.techtest.injection.component

import dagger.Component
import nz.co.trademe.techtest.injection.module.NetworkModule
import nz.co.trademe.techtest.ui.base.BaseViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    fun inject(baseViewModel: BaseViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}