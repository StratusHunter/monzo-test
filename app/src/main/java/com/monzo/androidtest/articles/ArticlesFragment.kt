package com.monzo.androidtest.articles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.monzo.androidtest.R
import com.monzo.androidtest.articles.model.Article
import com.monzo.androidtest.common.setupToolbar

class ArticlesFragment : Fragment(R.layout.fragment_article_list), ArticleAdapter.ArticleViewHolder.ArticleClickListener {

    private val viewModel by viewModels<ArticlesViewModel> { ArticlesViewModel.Factory }
    private val adapter = ArticleAdapter(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        setupToolbar(view.findViewById(R.id.toolbar))

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.articles_swiperefreshlayout)
        swipeRefreshLayout.setOnRefreshListener { viewModel.onRefresh() }

        val recyclerView = view.findViewById<RecyclerView>(R.id.articles_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner) { state ->
            swipeRefreshLayout.isRefreshing = state.refreshing
            adapter.showArticles(state.articles ?: emptyList())

            if (state.articles == null) {
                Snackbar.make(recyclerView, R.string.error_loading_articles, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    //region ArticleClickListener
    override fun onArticleClick(article: Article) {
        findNavController().navigate(ArticlesFragmentDirections.actionArticlesFragmentToArticleDetailFragment(article.url))
    }
    //endregion
}
