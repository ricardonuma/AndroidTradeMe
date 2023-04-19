package nz.co.trademe.techtest.ui.listedItemDetail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import nz.co.trademe.techtest.R
import nz.co.trademe.techtest.databinding.ActivityListedItemDetailBinding
import nz.co.trademe.techtest.injection.ViewModelFactory
import nz.co.trademe.techtest.ui.base.BaseActivity
import nz.co.trademe.techtest.utils.Constants.EXTRA_LISTING_ID
import nz.co.trademe.techtest.utils.Constants.EXTRA_LISTING_TITLE
import nz.co.trademe.techtest.utils.contentView
import nz.co.trademe.techtest.widget.EmptyLayout


class ListedItemDetailActivity : BaseActivity() {

    lateinit var mListedItemDetailViewModel: ListedItemDetailViewModel
    private var mListingId: Long = 0
    private var mListingTitle = ""
    private val binding: ActivityListedItemDetailBinding by contentView(R.layout.activity_listed_item_detail)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentExtra()
        setViewModel()
        initUI()
        returnCallListedItemDetail()
    }

    private fun getIntentExtra() {
        if (intent.hasExtra(EXTRA_LISTING_ID)) {
            mListingId = intent.getLongExtra(EXTRA_LISTING_ID, 0)
            mListingTitle = intent.getStringExtra(EXTRA_LISTING_TITLE)
        }
    }

    private fun setViewModel() {
        ViewModelFactory.clearViewModelFactoryParameters()
        ViewModelFactory.listingId = mListingId
        ViewModelFactory.listingTitle = mListingTitle

        mListedItemDetailViewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(ListedItemDetailViewModel::class.java)

        mListedItemDetailViewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null)
                showError(
                    this,
                    binding.clParentListedItemDetail,
                    errorMessage,
                    mListedItemDetailViewModel.title.value!!,
                    mListedItemDetailViewModel.errorClickListener
                )
            else
                hideError()
        })

        binding.listedItemDetailViewModel = mListedItemDetailViewModel
    }

    override fun initUI() {
        super.initUI()

        title = getString(R.string.title_listed_item_detail)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun returnCallListedItemDetail() {
        mListedItemDetailViewModel.listedItemDetail.observe(
            this, Observer { listedItemDetail ->
                if (listedItemDetail == null)
                    showEmptyLayout(
                        binding.emptyLayoutListedItemDetail,
                        EmptyLayout.EmptyViewType.LISTED_ITEM_DETAIL,
                        mListingTitle
                    )
                else
                    binding.emptyLayoutListedItemDetail.visibility = View.GONE
            })
    }
}