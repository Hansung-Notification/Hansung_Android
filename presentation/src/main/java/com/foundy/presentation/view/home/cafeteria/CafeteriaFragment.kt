package com.foundy.presentation.view.home.cafeteria

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.foundy.presentation.R
import com.foundy.presentation.databinding.FragmentCafeteriaBinding
import com.google.android.material.composethemeadapter3.Mdc3Theme

class CafeteriaFragment: Fragment(R.layout.fragment_cafeteria) {

    private val viewModel: CafeteriaViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FragmentCafeteriaBinding.bind(view).composeView.setContent {
            Mdc3Theme {
                CafeteriaScreen(viewModel)
            }
        }
    }
}