package com.foundy.presentation.view.keyword

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.foundy.presentation.R
import com.foundy.presentation.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by activityViewModels()

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var launcher: ActivityResultLauncher<Intent>

    companion object {
        private const val TAG = "LoginFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launcher = registerForActivityResult(StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    viewModel.signInWith(account.idToken!!).observe(
                        viewLifecycleOwner,
                        ::onCompleteSignIn
                    )
                    Log.d(TAG, "Success google sign in: " + account.id)
                } catch (e: ApiException) {
                    showSnackBar(getString(R.string.failed_to_sign_in))
                    Log.e(TAG, "Failed google sign in: " + e.message)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginBinding.bind(view)

        initGoogleSignInClient(view.context)
        initButton(binding)
    }

    private fun initGoogleSignInClient(context: Context) {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
    }

    private fun initButton(binding: FragmentLoginBinding) {
        binding.googleSignInButton.apply {
            val textView = getChildAt(0) as? TextView
            textView?.let { it.text = context.getString(R.string.sign_in_with_google) }
            setOnClickListener { signIn() }
        }
    }

    private fun signIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private fun onCompleteSignIn(result: Result<Any>) {
        if (result.isSuccess) {
            try {
                viewModel.subscribeAllDbKeywords()
            } catch (e: Exception) {
                showSnackBar(getString(R.string.failed_to_subscribe_previous_keywords))
            } finally {
                findNavController().navigate(R.id.action_loginFragment_to_keywordFragment)
            }
        } else {
            showSnackBar(getString(R.string.failed_to_sign_in))
            Log.e(TAG, "Failed firebase sign in: " + result.exceptionOrNull())
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}