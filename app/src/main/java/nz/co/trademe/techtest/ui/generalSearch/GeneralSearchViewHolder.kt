package nz.co.trademe.techtest.ui.generalSearch

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import nz.co.trademe.techtest.R


class GeneralSearchViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val mTitle: TextView = itemView.findViewById(R.id.tv_title_general_search)
    val mStartPrice: TextView = itemView.findViewById(R.id.tv_start_price_general_search)
    val mBuyNowPrice: TextView = itemView.findViewById(R.id.tv_buy_now_price_general_search)
    val mPictureHref: ImageView = itemView.findViewById(R.id.iv_picture_href_general_search)
    val mParentLayout: CardView = itemView.findViewById(R.id.cv_parent_layout_general_search)
}