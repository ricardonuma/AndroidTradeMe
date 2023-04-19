package nz.co.trademe.techtest.ui.category

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import nz.co.trademe.techtest.R
import nz.co.trademe.techtest.databinding.ActivityCategoryBinding
import nz.co.trademe.techtest.injection.ViewModelFactory
import nz.co.trademe.techtest.ui.base.BaseActivity
import nz.co.trademe.techtest.utils.Constants.CATEGORY_ROOT_NAME
import nz.co.trademe.techtest.utils.Constants.CATEGORY_ROOT_NUMBER
import nz.co.trademe.techtest.utils.contentView
import nz.co.trademe.techtest.widget.EmptyLayout


class CategoryActivity : BaseActivity() {

    lateinit var mCategoryViewModel: CategoryViewModel
    private var mCategoryName = ""
    private var mCategoryNumber = ""
    private val binding: ActivityCategoryBinding by contentView(R.layout.activity_category)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setViewModel()
        initUI()
        setRecyclerView()
        returnCallCategoryList()
    }

    private fun setViewModel() {
        ViewModelFactory.clearViewModelFactoryParameters()

        mCategoryViewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(CategoryViewModel::class.java)

        mCategoryViewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null)
                showError(
                    this,
                    binding.llParentCategory,
                    errorMessage,
                    "",
                    mCategoryViewModel.errorClickListener
                )
            else
                hideError()
        })

        binding.categoryViewModel = mCategoryViewModel
    }

    override fun initUI() {
        super.initUI()

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setRecyclerView() {
        binding.rvListCategory.layoutManager = LinearLayoutManager(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun returnCallCategoryList() {
        mCategoryViewModel.mCategory.observe(this, Observer { category ->

            if (category?.subcategories.isNullOrEmpty()) {
                showEmptyLayout(binding.emptyLayoutCategory, EmptyLayout.EmptyViewType.CATEGORY, mCategoryName)
            } else {
                binding.emptyLayoutCategory.visibility = View.GONE

                title =
                    if (category.name == CATEGORY_ROOT_NAME)
                        getString(R.string.title_category)
                    else
                        category.name

                mCategoryName = category.name
                mCategoryNumber = category.id

                if (mCategoryName == CATEGORY_ROOT_NAME)
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                else
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        })
    }

    override fun onBackPressed() {
        if (mCategoryName == CATEGORY_ROOT_NAME)
            super.onBackPressed()
        else {
            var previousCategoryNumber = mCategoryNumber.removeSuffix("-").dropLast(4)
            mCategoryViewModel.callCategoryList(
                if (previousCategoryNumber.isBlank())
                    CATEGORY_ROOT_NUMBER
                else
                    previousCategoryNumber
            )
        }
    }
}