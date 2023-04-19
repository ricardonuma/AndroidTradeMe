package nz.co.trademe.techtest.widget

import android.content.Context
import android.view.View
import android.widget.TextView
import nz.co.trademe.techtest.R


class EmptyLayout(private var mContext: Context, mEmptyView: View, mType: EmptyViewType, mText: String) {

    enum class EmptyViewType {
        CATEGORY,
        GENERAL_SEARCH,
        LISTED_ITEM_DETAIL
    }

    private var mText: TextView = mEmptyView.findViewById(R.id.tv_text_empty_layout)

    init {
        setEmptyView(mType, mText)
    }

    private fun setEmptyView(type: EmptyViewType, title: String) {
        when (type) {
            EmptyViewType.CATEGORY ->
                mText.text =
                    String.format(
                        mContext.getString(R.string.empty_title_category),
                        title
                    )

            EmptyViewType.GENERAL_SEARCH ->
                mText.text =
                    String.format(
                        mContext.getString(R.string.empty_title_general_search),
                        title
                    )

            EmptyViewType.LISTED_ITEM_DETAIL ->
                mText.text =
                    String.format(
                        mContext.getString(R.string.empty_title_general_search),
                        title
                    )
        }
    }
}
