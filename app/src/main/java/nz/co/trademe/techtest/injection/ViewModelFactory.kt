package nz.co.trademe.techtest.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nz.co.trademe.techtest.ui.category.CategoryViewModel
import nz.co.trademe.techtest.ui.generalSearch.GeneralSearchViewModel
import nz.co.trademe.techtest.ui.listedItemDetail.ListedItemDetailViewModel
import nz.co.trademe.techtest.utils.Constants.SUPPRESS_UNCHECKED_CAST


class ViewModelFactory : ViewModelProvider.Factory {

    private var mViewModelFactoryParameters: ViewModelFactoryParameters = ViewModelFactoryParameters

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(CategoryViewModel::class.java) ->
                @Suppress(SUPPRESS_UNCHECKED_CAST)
                return CategoryViewModel() as T
            modelClass.isAssignableFrom(GeneralSearchViewModel::class.java) ->
                @Suppress(SUPPRESS_UNCHECKED_CAST)
                return GeneralSearchViewModel(
                    mViewModelFactoryParameters.categoryNumber,
                    mViewModelFactoryParameters.categoryName
                ) as T
            modelClass.isAssignableFrom(ListedItemDetailViewModel::class.java) ->
                @Suppress(SUPPRESS_UNCHECKED_CAST)
                return ListedItemDetailViewModel(
                    mViewModelFactoryParameters.listingId,
                    mViewModelFactoryParameters.listingTitle
                ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }

    companion object ViewModelFactoryParameters {
        var categoryNumber: String = ""
        var categoryName: String = ""
        var listingId: Long = 0
        var listingTitle: String = ""

        fun clearViewModelFactoryParameters() {
            categoryNumber = ""
            categoryName = ""
            listingId = 0
            listingTitle = ""
        }
    }
}