package nz.co.trademe.techtest.ui.category

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import nz.co.trademe.techtest.R


class CategoryViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val mName: TextView = itemView.findViewById(R.id.tv_name_category)
    val mContentLayout: ConstraintLayout = itemView.findViewById(R.id.cl_content_item_category)
}
