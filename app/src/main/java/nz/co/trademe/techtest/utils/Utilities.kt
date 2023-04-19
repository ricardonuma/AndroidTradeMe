package nz.co.trademe.techtest.utils

import java.text.NumberFormat
import java.util.*


object Utilities {

    fun formatNumberToCurrency(number: Double): String {
        return NumberFormat.getCurrencyInstance(Locale.CANADA).format(number)
    }
}