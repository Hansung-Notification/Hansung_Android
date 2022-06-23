package com.foundy.presentation.notice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.foundy.presentation.R

class NoticeFragment : Fragment(R.layout.fragment_notice) {

    private val viewModel: NoticeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isError.observe(viewLifecycleOwner) { isError ->
            parentFragmentManager.commit {
                setReorderingAllowed(false)
                val fragmentClass = if (isError) {
                    ErrorFragment::class.java
                } else {
                    NoticeRecyclerFragment::class.java
                }
                replace(R.id.notice_fragment_container_view, fragmentClass, null)
                addToBackStack(null)
            }
        }
    }
}