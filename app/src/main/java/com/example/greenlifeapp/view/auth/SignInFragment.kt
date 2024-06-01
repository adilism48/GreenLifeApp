package com.example.greenlifeapp.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.greenlifeapp.R
import com.example.greenlifeapp.databinding.FragmentSignInBinding
import com.example.greenlifeapp.view.MainActivity
import com.example.greenlifeapp.viewmodel.AuthViewModel

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]


        checkAuthState()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.signIn(email, password).observe(viewLifecycleOwner) {
                    if (it.isSuccess) {
                        Toast.makeText(
                            context,
                            "Signed In.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        checkAuthState()
                    } else if (it.isFailure) {
                        Toast.makeText(
                            context,
                            "Incorrect Email or Password.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
        }

        binding.tvSignUp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.auth_container, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.tvForgetPass.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.auth_container, ForgetPassFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkAuthState() {
        if (viewModel.currentUser() != null) {
            val i = Intent(context, MainActivity::class.java)
            startActivity(i)
        }
    }
}