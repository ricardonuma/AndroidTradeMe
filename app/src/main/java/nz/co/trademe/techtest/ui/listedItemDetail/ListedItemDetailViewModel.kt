package nz.co.trademe.techtest.ui.listedItemDetail

import android.view.View
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import nz.co.trademe.techtest.ui.base.BaseViewModel
import nz.co.trademe.techtest.utils.Constants.CATEGORY_DEPTH
import nz.co.trademe.techtest.utils.Constants.DATA_BINDING_NO_PHOTO
import nz.co.trademe.wrapper.models.ListedItemDetail


open class ListedItemDetailViewModel(listingId: Long, listingTitle: String) : BaseViewModel() {

    var listedItemDetail: MutableLiveData<ListedItemDetail> = MutableLiveData()
    var listingId: MutableLiveData<String> = MutableLiveData()
    var title: MutableLiveData<String> = MutableLiveData()
    var photoUrl: MutableLiveData<String> = MutableLiveData()
    val errorClickListener = View.OnClickListener { callListedItemDetail() }

    init {
        this.listingId.value = listingId.toString()
        this.title.value = listingTitle

        callListedItemDetail()
    }

    fun callListedItemDetail() {
        mSubscription =
            mTradeMeApiService
                .getListing(listingId.value!!.toLong(), CATEGORY_DEPTH)
                .concatMap { t -> Observable.just(t) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveStart() }
                .doOnTerminate { onRetrieveFinish() }
                .subscribe(
                    { result -> onRetrieveSuccess(result) },
                    { error -> onRetrieveError(error) }
                )
    }

    private fun onRetrieveSuccess(t: ListedItemDetail?) {
        listedItemDetail.value = t
        listingId.value = t?.listingId.toString()
        title.value = t?.title
        photoUrl.value = if (t?.photos!!.isNotEmpty()) t?.photos!![0].value.large else DATA_BINDING_NO_PHOTO
        contentVisibility.value = View.VISIBLE
    }
}