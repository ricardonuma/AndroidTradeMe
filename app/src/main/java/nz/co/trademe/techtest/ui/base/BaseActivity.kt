package nz.co.trademe.techtest.ui.base

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import nz.co.trademe.techtest.R
import nz.co.trademe.techtest.ui.category.CategoryActivity
import nz.co.trademe.techtest.ui.generalSearch.GeneralSearchActivity
import nz.co.trademe.techtest.widget.EmptyLayout


open class BaseActivity : AppCompatActivity() {

    private var mToolbar: Toolbar? = null
    private var errorSnackbar: Snackbar? = null

    protected open fun initUI() {
        mToolbar = findViewById(R.id.toolbar)

        if (mToolbar != null) {
            setSupportActionBar(mToolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    protected fun showEmptyLayout(view: View, type: EmptyLayout.EmptyViewType, title: String) {
        view.visibility = View.VISIBLE

        EmptyLayout(this, view, type, title)
    }

    private fun apiErrorTreatment(msg: String?, defaultMsg: String): String {
        return if (msg!!.contains(resources.getString(R.string.error_message_timeout)) ||
            msg.contains(getResources().getString(R.string.error_message_timeout2)) ||
            msg.contains(getResources().getString(R.string.error_message_timeout3)) ||
            msg.contains(getResources().getString(R.string.error_message_timeout4)) ||
            msg.contains(getResources().getString(R.string.error_message_timeout5))
        )
            getResources().getString(R.string.message_no_connection)
        else if (msg.isEmpty())
            defaultMsg
        else
            msg
    }

    protected fun showError(
        context: Context,
        view: ViewGroup,
        errorMessage: String,
        stringArg: String,
        errorClickListener: View.OnClickListener
    ) {
        errorSnackbar = Snackbar.make(
            view,
            apiErrorTreatment(
                errorMessage,

                when (context) {
                    is CategoryActivity -> String.format(
                        getString(R.string.message_error_load_category),
                        stringArg
                    )
                    is GeneralSearchActivity -> String.format(
                        getString(R.string.message_error_load_general_search),
                        stringArg
                    )
                    else -> String.format(
                        getString(R.string.message_error_load_listed_item_detail),
                        stringArg
                    )
                }
            ),
            Snackbar.LENGTH_INDEFINITE
        )
        errorSnackbar?.setAction(R.string.retry, errorClickListener)
        errorSnackbar?.show()
    }

    protected fun hideError() {
        errorSnackbar?.dismiss()
    }
}