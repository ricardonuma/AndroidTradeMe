package nz.co.trademe.techtest.ui.category

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nz.co.trademe.techtest.R
import nz.co.trademe.techtest.ui.generalSearch.GeneralSearchActivity
import nz.co.trademe.techtest.utils.Constants.EXTRA_CATEGORY_NAME
import nz.co.trademe.techtest.utils.Constants.EXTRA_CATEGORY_NUMBER
import nz.co.trademe.wrapper.models.Category


class CategoryAdapter : RecyclerView.Adapter<CategoryViewHolder>() {

    private lateinit var mContext: Context
    private var items: List<Category?> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            CategoryViewHolder {
        this.mContext = parent.context
        return CategoryViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = items[position]

        if (item != null) {
            holder.mName.text = item.name

            holder.mContentLayout.setOnClickListener {
                if (mContext is CategoryActivity && !item.isLeaf) {
                    (mContext as CategoryActivity).mCategoryViewModel.callCategoryList(item.id)
                } else {
                    val intent = android.content.Intent(mContext, GeneralSearchActivity::class.java)
                    intent.putExtra(EXTRA_CATEGORY_NUMBER, item.id)
                    intent.putExtra(EXTRA_CATEGORY_NAME, item.name)
                    mContext.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(categoryList: List<Category?>) {
        this.items = categoryList
        notifyDataSetChanged()
    }
}