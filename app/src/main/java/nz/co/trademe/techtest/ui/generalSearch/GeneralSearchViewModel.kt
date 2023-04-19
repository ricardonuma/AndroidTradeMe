package nz.co.trademe.techtest.ui.generalSearch

import android.view.View
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import nz.co.trademe.techtest.ui.base.BaseViewModel
import nz.co.trademe.techtest.utils.Constants.GENERAL_SEARCH_ROWS
import nz.co.trademe.wrapper.models.SearchCollection
import nz.co.trademe.wrapper.models.SearchListing


class GeneralSearchViewModel(mCategoryNumber: String, var mCategoryName: String) : BaseViewModel() {

    private var mFilters = HashMap<String, String>()
    var generalSearchAdapter: GeneralSearchAdapter = GeneralSearchAdapter()
    var mSearchCollection: MutableLiveData<SearchCollection> = MutableLiveData()
    val errorClickListener = View.OnClickListener { callGeneralSearchList() }

    init {
        mFilters["rows"] = GENERAL_SEARCH_ROWS
        mFilters["category"] = mCategoryNumber

        callGeneralSearchList()
    }

    fun callGeneralSearchList() {
        mSubscription =
            mTradeMeApiService
                .generalSearch(mFilters)
                .concatMap { t -> Observable.just(t) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveStart() }
                .doOnTerminate { onRetrieveFinish() }
                .subscribe(
                    { result -> onRetrieveSuccess(result) },
                    { error -> onRetrieveError(error) }
                )
    }

    private fun onRetrieveSuccess(t: SearchCollection?) {
        mSearchCollection.value = t
        generalSearchAdapter.setItems(t?.list as List<SearchListing?>)
        contentVisibility.value = View.VISIBLE
    }
}