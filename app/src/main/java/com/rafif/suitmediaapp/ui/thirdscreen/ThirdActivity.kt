package com.rafif.suitmediaapp.ui.thirdscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafif.suitmediaapp.databinding.ActivityThirdBinding
import com.rafif.suitmediaapp.ui.ViewModelFactory

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private lateinit var thirdViewModel: ThirdViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
        }

        // Provide viewmodel
        thirdViewModel = obtainViewModel()

        getData()

    }

    private fun obtainViewModel(): ThirdViewModel {
        val factory = ViewModelFactory.getInstance(this)
        return ViewModelProvider(this, factory)[ThirdViewModel::class.java]
    }

    private fun getData() {
        val adapter = UserListAdapter()
        val loadingStateAdapter = LoadingStateAdapter { adapter.retry() }
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
        binding.rvUsers.adapter = adapter.withLoadStateFooter(
            footer = loadingStateAdapter
        )

        thirdViewModel.user.observe(this) { pagingData ->
            adapter.addLoadStateListener { loadState ->
                adapter.apply {
                    binding.tvDataEmpty.isGone =
                        !(itemCount <= 0 && !loadState.source.refresh.endOfPaginationReached)
                }
            }
            adapter.submitData(lifecycle, pagingData)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}