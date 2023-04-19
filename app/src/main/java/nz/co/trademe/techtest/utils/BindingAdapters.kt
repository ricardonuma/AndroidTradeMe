package nz.co.trademe.techtest.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.gahfy.mvvmposts.utils.extension.getParentActivity
import nz.co.trademe.techtest.R
import nz.co.trademe.techtest.utils.Constants.DATA_BINDING_ADAPTER
import nz.co.trademe.techtest.utils.Constants.DATA_BINDING_IMAGE
import nz.co.trademe.techtest.utils.Constants.DATA_BINDING_INVERSE_MUTABLE_VISIBILITY
import nz.co.trademe.techtest.utils.Constants.DATA_BINDING_MUTABLE_TEXT
import nz.co.trademe.techtest.utils.Constants.DATA_BINDING_MUTABLE_VISIBILITY


@BindingAdapter(DATA_BINDING_ADAPTER)
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter(DATA_BINDING_MUTABLE_VISIBILITY)
fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value -> view.visibility = value ?: View.VISIBLE })
    }
}

@BindingAdapter(DATA_BINDING_INVERSE_MUTABLE_VISIBILITY)
fun setInverseMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value ->
            view.visibility = if (value == View.GONE) View.VISIBLE else value
        })
    }
}

@BindingAdapter(DATA_BINDING_MUTABLE_TEXT)
fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value ?: "" })
    }
}

@BindingAdapter(DATA_BINDING_IMAGE)
fun loadImage(view: ImageView, imageUrl: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && imageUrl != null) {
        imageUrl.observe(parentActivity, Observer { value ->
            Picasso.get().load(value).placeholder(R.drawable.ic_alert_error).into(view)
        })
    }
}