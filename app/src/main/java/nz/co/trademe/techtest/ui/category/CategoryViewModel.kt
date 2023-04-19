package nz.co.trademe.techtest.ui.category

import android.view.View
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import nz.co.trademe.techtest.ui.base.BaseViewModel
import nz.co.trademe.techtest.utils.Constants.CATEGORY_ROOT_NUMBER
import nz.co.trademe.wrapper.models.Category


open class CategoryViewModel : BaseViewModel() {

    var categoryAdapter: CategoryAdapter = CategoryAdapter()
    var mCategory: MutableLiveData<Category> = MutableLiveData()
    val errorClickListener = View.OnClickListener { callCategoryList(CATEGORY_ROOT_NUMBER) }

    init {
        callCategoryList(CATEGORY_ROOT_NUMBER)
    }

    fun callCategoryList(number: String) {
        mSubscription =
            mTradeMeApiService
                .getCategory(number)
                .concatMap { t -> Observable.just(t) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveStart() }
                .doOnTerminate { onRetrieveFinish() }
                .subscribe(
                    { result -> onRetrieveSuccess(result) },
                    { error -> onRetrieveError(error) }
                )
    }

    private fun onRetrieveSuccess(t: Category?) {
        mCategory.value = t
        categoryAdapter.setItems(t?.subcategories as List<Category?>)
        contentVisibility.value = View.VISIBLE
    }
}