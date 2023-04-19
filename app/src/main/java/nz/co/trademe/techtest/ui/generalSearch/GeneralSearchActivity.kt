package nz.co.trademe.techtest.ui.generalSearch

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import nz.co.trademe.techtest.R
import nz.co.trademe.techtest.databinding.ActivityGeneralSearchBinding
import nz.co.trademe.techtest.injection.ViewModelFactory
import nz.co.trademe.techtest.ui.base.BaseActivity
import nz.co.trademe.techtest.utils.Constants.EXTRA_CATEGORY_NAME
import nz.co.trademe.techtest.utils.Constants.EXTRA_CATEGORY_NUMBER
import nz.co.trademe.techtest.utils.contentView
import nz.co.trademe.techtest.widget.EmptyLayout


class GeneralSearchActivity : BaseActivity() {

    lateinit var mGeneralSearchViewModel: GeneralSearchViewModel
    private var mCategoryNumber = "0"
    private var mCategoryName = ""
    private val binding: ActivityGeneralSearchBinding by contentView(R.layout.activity_general_search)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentExtra()
        setViewModel()
        initUI()
        setRecyclerView()
        returnCallGeneralSearchList()
    }

    private fun getIntentExtra() {
        if (intent.hasExtra(EXTRA_CATEGORY_NUMBER)) {
            mCategoryNumber = intent.getStringExtra(EXTRA_CATEGORY_NUMBER)
            mCategoryName = intent.getStringExtra(EXTRA_CATEGORY_NAME)
        }
    }

    private fun setViewModel() {
        ViewModelFactory.clearViewModelFactoryParameters()
        ViewModelFactory.categoryNumber = mCategoryNumber
        ViewModelFactory.categoryName = mCategoryName

        mGeneralSearchViewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(GeneralSearchViewModel::class.java)

        mGeneralSearchViewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null)
                showError(
                    this,
                    binding.llParentGeneralSearch,
                    errorMessage,
                    mGeneralSearchViewModel.mCategoryName,
                    mGeneralSearchViewModel.errorClickListener
                )
            else
                hideError()
        })

        binding.generalSearchViewModel = mGeneralSearchViewModel
    }

    override fun initUI() {
        super.initUI()

        title = mGeneralSearchViewModel.mCategoryName
    }

    private fun setRecyclerView() {
        binding.rvListGeneralSearch.layoutManager = LinearLayoutManager(this)
        binding.rvListGeneralSearch.adapter = mGeneralSearchViewModel.generalSearchAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun returnCallGeneralSearchList() {
        mGeneralSearchViewModel.mSearchCollection.observe(this, Observer { searchCollection ->
            if (searchCollection?.list.isNullOrEmpty())
                showEmptyLayout(
                    binding.emptyLayoutGeneralSearch,
                    EmptyLayout.EmptyViewType.GENERAL_SEARCH,
                    mGeneralSearchViewModel.mCategoryName
                )
            else
                binding.emptyLayoutGeneralSearch.visibility = View.GONE
        })
    }
}