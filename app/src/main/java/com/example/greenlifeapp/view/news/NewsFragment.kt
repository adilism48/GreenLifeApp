package com.example.greenlifeapp.view.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenlifeapp.data.ApiService
import com.example.greenlifeapp.databinding.FragmentNewsBinding
import com.example.greenlifeapp.model.NewsRepository
import com.example.greenlifeapp.viewmodel.NewsViewModel
import com.example.greenlifeapp.viewmodel.NewsViewModelFactory

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        newsViewModel.users.observe(viewLifecycleOwner) { users ->
            adapter.submitList(users)
        }

        initRcView()
        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val newsViewModel: NewsViewModel by viewModels {
        NewsViewModelFactory(NewsRepository(ApiService.create()))
    }

    private fun initRcView() = with(binding) {
        adapter = NewsAdapter()
        rcViewNews.layoutManager = LinearLayoutManager(context)
        rcViewNews.adapter = adapter
    }
}