package com.foundy.presentation.extension

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.os.SystemClock
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.DimenRes
import androidx.annotation.IntRange
import androidx.core.content.res.getDrawableOrThrow
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.google.android.material.textfield.TextInputLayout


fun View.addRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
    setBackgroundResource(resourceId)
}

fun RecyclerView.addDividerDecoration(@DimenRes horizontalPaddingDimen: Int? = null) {
    val attr = intArrayOf(android.R.attr.listDivider)
    val typeArray = context.obtainStyledAttributes(attr)
    val divider = typeArray.getDrawable(0)
    val inset = horizontalPaddingDimen?.let { resources.getDimensionPixelSize(it) } ?: 0
    val insetDivider = InsetDrawable(divider, inset, 0, inset, 0)
    val decoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
    decoration.setDrawable(insetDivider)
    addItemDecoration(decoration)
}

fun View.setBackgroundColor(@AttrRes attrRes: Int, @IntRange(from = 0, to = 255) alpha: Int) {
    val rawColor = MaterialColors.getColor(this, attrRes)
    val red: Int = Color.red(rawColor)
    val blue: Int = Color.blue(rawColor)
    val green: Int = Color.green(rawColor)
    val color = Color.argb(alpha, red, green, blue)
    setBackgroundColor(color)
}

fun Context.getProgressBarDrawable(): Drawable {
    val value = TypedValue()
    theme.resolveAttribute(android.R.attr.progressBarStyleSmall, value, false)
    val progressBarStyle = value.data
    val attributes = intArrayOf(android.R.attr.indeterminateDrawable)
    val array = obtainStyledAttributes(progressBarStyle, attributes)
    val drawable = array.getDrawableOrThrow(0)
    array.recycle()
    return drawable
}

fun TextView.setOnEditorActionListenerWithDebounce(
    debounceTime: Long = 1000L,
    action: (actionId: Int) -> Boolean
) {
    this.setOnEditorActionListener(object : TextView.OnEditorActionListener {
        private var lastClickTime: Long = 0

        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) {
                return true
            }

            lastClickTime = SystemClock.elapsedRealtime()
            return action(actionId)
        }
    })
}

fun TextInputLayout.setEndIconOnClickListenerWithDebounce(
    debounceTime: Long = 1000L,
    action: () -> Unit
) {
    this.setEndIconOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View?) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) {
                return
            }

            lastClickTime = SystemClock.elapsedRealtime()
            action()
        }
    })
}