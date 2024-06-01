package com.example.greenlifeapp.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.greenlifeapp.R
import com.example.greenlifeapp.databinding.FragmentSignUpBinding
import com.example.greenlifeapp.model.User
import com.example.greenlifeapp.viewmodel.AuthViewModel

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val name = binding.etName.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.signUp(email, password).observe(viewLifecycleOwner) {
                    if (it.isSuccess) {
                        val currentUserEmail = viewModel.currentUserEmail()
                        viewModel.addUser(User(name, currentUserEmail, true, "other"))
                        Toast.makeText(
                            context,
                            "Account created",
                            Toast.LENGTH_SHORT,
                        ).show()
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.auth_container, SignInFragment())
                            .addToBackStack(null)
                            .commit()
                    } else if (it.isFailure) {
                        Toast.makeText(
                            context,
                            "Already been registered",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}