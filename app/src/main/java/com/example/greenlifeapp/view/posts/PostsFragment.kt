package com.example.greenlifeapp.view.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenlifeapp.R
import com.example.greenlifeapp.databinding.FragmentPostsBinding
import com.example.greenlifeapp.model.Post
import com.example.greenlifeapp.view.auth.ForgetPassFragment
import com.example.greenlifeapp.view.news.NewsFragment
import com.example.greenlifeapp.view.settings.SettingsFragment
import com.example.greenlifeapp.viewmodel.PostViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PostsFragment : Fragment() {
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: PostViewModel
    private lateinit var adapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        viewModel = ViewModelProvider(this)[PostViewModel::class.java]

        binding.bNews.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, NewsFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.bSettings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.bSend.setOnClickListener {
            val userUID = auth.currentUser?.uid
            val text = binding.etNewPost.text.toString()
            val tags = binding.groupTag.checkedRadioButtonId

            if (tags == -1) {
                Toast.makeText(
                    context,
                    "You need to choose tag",
                    Toast.LENGTH_LONG,
                ).show()
            } else {
                val tag = (when (tags) {
                    binding.rbVolunteer.id -> "volunteer"
                    binding.rbSponsor.id -> "sponsor"
                    else -> "other"
                })
                viewModel.addPost(Post(userUID, text, tag))
            }
        }

        val myRef = Firebase.database.getReference("users")

        myRef.child(auth.currentUser?.uid ?: "No UID").get().addOnSuccessListener {
            val currentTag = it.child("interest").value.toString()

            binding.sRec.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.getRecommendedPosts(postsListener, currentTag)
                } else {
                    viewModel.getPost(postsListener)
                }
            }

            if (binding.sRec.isChecked) {
                viewModel.getRecommendedPosts(postsListener, currentTag)
            } else {
                viewModel.getPost(postsListener)
            }
        }

        initRcView()
        return binding.root
    }

    private fun initRcView() = with(binding) {
        adapter = PostAdapter()
        rcViewPosts.layoutManager = LinearLayoutManager(context)
        rcViewPosts.adapter = adapter
    }

    private val postsListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val posts = mutableListOf<Post>()
            for (dataSnapshot in snapshot.children) {
                val post = dataSnapshot.getValue(Post::class.java)
                post?.let { posts.add(it) }
            }

            adapter.submitList(posts)
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}