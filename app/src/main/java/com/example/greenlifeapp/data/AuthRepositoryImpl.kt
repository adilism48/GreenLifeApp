package com.example.greenlifeapp.data


import com.example.greenlifeapp.model.AuthRepository
import com.example.greenlifeapp.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object AuthRepositoryImpl : AuthRepository {
    private val auth = Firebase.auth
    private val myRef = Firebase.database.getReference("users")

    override fun signIn(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    override fun signUp(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    override fun currentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun addUser(user: User) {
        myRef.child(auth.currentUser?.uid ?: "noUID").setValue(user)
    }
}