package com.foundy.presentation.extension

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.InsetDrawable
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.IntRange
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView


fun View.addRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
    setBackgroundResource(resourceId)
}

fun RecyclerView.addDividerDecoration(
    context: Context,
    @DimenRes horizontalPaddingDimen: Int? = null
) {
    val attr = intArrayOf(android.R.attr.listDivider)
    val typeArray = context.obtainStyledAttributes(attr)
    val divider = typeArray.getDrawable(0)
    val inset = horizontalPaddingDimen?.let { resources.getDimensionPixelSize(it) } ?: 0
    val insetDivider = InsetDrawable(divider, inset, 0, inset, 0)
    val decoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
    decoration.setDrawable(insetDivider)
    addItemDecoration(decoration)
}

fun View.setBackgroundColor(@ColorRes id: Int, @IntRange(from = 0, to = 255) alpha: Int) {
    val resourceColor = resources.getColor(id, null)
    val red: Int = Color.red(resourceColor)
    val blue: Int = Color.blue(resourceColor)
    val green: Int = Color.green(resourceColor)
    val color = Color.argb(alpha, red, green, blue)
    setBackgroundColor(color)
}