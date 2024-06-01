package com.example.greenlifeapp.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.greenlifeapp.R
import com.example.greenlifeapp.databinding.FragmentForgetPassBinding
import com.example.greenlifeapp.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ForgetPassFragment : Fragment() {
    private var _binding: FragmentForgetPassBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgetPassBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        val myRef = Firebase.database.getReference("users")
        val emailList = mutableListOf<String>()

        myRef.get().addOnSuccessListener { dataSnapshot ->
            dataSnapshot.children.forEach { dataSnapshot1 ->
                emailList.add(dataSnapshot1.child("email").value.toString())
            }
        }

        binding.etReset.setOnClickListener {
            if (emailList.contains(binding.etEmail.text.toString())) {
                auth.sendPasswordResetEmail(binding.etEmail.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Check your email to reset password",
                            Toast.LENGTH_LONG,
                        ).show()
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.auth_container, SignInFragment())
                            .commit()
                    }
                }
            } else {
                Toast.makeText(
                    context,
                    "Incorrect email",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}