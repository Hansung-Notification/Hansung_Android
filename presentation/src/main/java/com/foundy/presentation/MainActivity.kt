package com.foundy.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.foundy.presentation.databinding.ActivityMainBinding
import com.foundy.presentation.notice.ErrorFragment
import com.foundy.presentation.notice.NoticeFragment
import com.foundy.presentation.notice.NoticeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: NoticeViewModel by viewModels()
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isError.observe(this) { isError ->
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                val fragmentClass = if (isError) {
                    ErrorFragment::class.java
                } else {
                    NoticeFragment::class.java
                }
                replace(R.id.fragmentContainer, fragmentClass, null)
                addToBackStack(null)
            }
        }
    }
}