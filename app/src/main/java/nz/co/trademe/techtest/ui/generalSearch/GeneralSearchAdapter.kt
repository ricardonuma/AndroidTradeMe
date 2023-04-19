package nz.co.trademe.techtest.ui.generalSearch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import nz.co.trademe.techtest.R
import nz.co.trademe.techtest.ui.listedItemDetail.ListedItemDetailActivity
import nz.co.trademe.techtest.utils.Constants.EXTRA_LISTING_ID
import nz.co.trademe.techtest.utils.Constants.EXTRA_LISTING_TITLE
import nz.co.trademe.techtest.utils.Utilities
import nz.co.trademe.wrapper.models.SearchListing


class GeneralSearchAdapter : RecyclerView.Adapter<GeneralSearchViewHolder>() {

    private lateinit var mContext: Context
    private var items: List<SearchListing?> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralSearchViewHolder {
        this.mContext = parent.context
        return GeneralSearchViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_general_search, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GeneralSearchViewHolder, position: Int) {
        val item = items[position]

        if (item != null) {
            holder.mTitle.text = item.title
            holder.mStartPrice.text = Utilities.formatNumberToCurrency(item.startPrice)
            holder.mBuyNowPrice.text = Utilities.formatNumberToCurrency(item.buyNowPrice)
            Picasso
                .get()
                .load(item.pictureHref)
                .placeholder(R.drawable.ic_alert_error)
                .resize(360, 360)
                .centerCrop()
                .into(holder.mPictureHref)

            holder.mParentLayout.setOnClickListener {
                val intent = android.content.Intent(mContext, ListedItemDetailActivity::class.java)
                intent.putExtra(EXTRA_LISTING_ID, item.listingId)
                intent.putExtra(EXTRA_LISTING_TITLE, item.title)
                mContext.startActivity(intent)
            }

            if (holder.mParentLayout.visibility == View.INVISIBLE)
                holder.mParentLayout.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(searchListingList: List<SearchListing?>) {
        this.items = searchListingList
        notifyDataSetChanged()
    }
}