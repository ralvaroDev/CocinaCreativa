package pe.ralvaro.cocinacreativa.util

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pe.ralvaro.cocinacreativa.R
import timber.log.Timber

/**
 * Launches a new coroutine and repeats `block` every time the Fragment's viewLifecycleOwner
 * is in and out of `minActiveState` lifecycle state.
 */
inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}

/**
 * This ensure that code is only applied when object exist. Maps produce headaches
 */
fun <T> T?.applyIfNotNull(block: T.() -> Unit) {
    this?.block()
}

fun ignoreException(block: () -> Unit) = try {
    block()
} catch (e: Exception) {
    Timber.e("Ignoring\n$e")

}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Fragment.showKeyboard(view: View) {
    activity?.showKeyboard(view)
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, 0)
}

fun Context.generateMarker(title: String): BitmapDescriptor {
    val markerView = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
        R.layout.marker_layout,
        null
    )
    val text = markerView.findViewById<TextView>(R.id.tv_location)
    val layout = markerView.findViewById<ConstraintLayout>(R.id.container_mark)

    text.text = title

    val bitmap = Bitmap.createScaledBitmap(viewToBitmap(layout), layout.width, layout.height, false)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun viewToBitmap(view: View): Bitmap {
    view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    val bitmap =
        Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    view.draw(canvas)
    return bitmap
}

fun Context.filterChipTint(color: Int): ColorStateList {
    val tintColor = if (color != Color.TRANSPARENT) {
        color
    } else {
        ContextCompat.getColor(this, R.color.default_filter_icon_color)
    }
    return ColorStateList.valueOf(tintColor)
}