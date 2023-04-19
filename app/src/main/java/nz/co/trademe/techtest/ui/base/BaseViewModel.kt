package nz.co.trademe.techtest.ui.base

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import nz.co.trademe.techtest.injection.component.DaggerViewModelInjector
import nz.co.trademe.techtest.injection.component.ViewModelInjector
import nz.co.trademe.techtest.injection.module.NetworkModule
import nz.co.trademe.wrapper.TradeMeApiService
import javax.inject.Inject


abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    @Inject
    lateinit var mTradeMeApiService: TradeMeApiService

    internal lateinit var mSubscription: Disposable

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val contentVisibility: MutableLiveData<Int> = MutableLiveData()
    val emptyLayoutVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    init {
        inject()
    }

    private fun inject() {
        injector.inject(this)
    }

    override fun onCleared() {
        super.onCleared()
        mSubscription.dispose()
    }

    protected fun onRetrieveStart() {
        loadingVisibility.value = View.VISIBLE
        contentVisibility.value = View.GONE
        errorMessage.value = null
    }

    protected fun onRetrieveFinish() {
        loadingVisibility.value = View.GONE
    }

    protected fun onRetrieveError(e: Throwable) {
        errorMessage.value = e.message
    }
}