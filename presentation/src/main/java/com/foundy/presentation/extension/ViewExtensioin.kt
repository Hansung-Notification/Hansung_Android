package com.foundy.presentation.extension

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun View.addRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
    setBackgroundResource(resourceId)
}

fun RecyclerView.addDivider(context: Context) {
    val decoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
    addItemDecoration(decoration)
}