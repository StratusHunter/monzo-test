package com.monzo.androidtest.articles

import com.monzo.androidtest.articles.model.Article
import com.monzo.androidtest.common.BaseViewModel
import com.monzo.androidtest.common.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArticlesViewModel(
    private val repository: ArticlesRepository
) : BaseViewModel<ArticlesState>(ArticlesState()) {
    init {
        onRefresh()
    }

    fun onRefresh() {
        setState { copy(refreshing = true) }
        disposables += repository.latestFintechArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { articles, error ->
                setState { copy(refreshing = false, articles = articles.takeIf { error == null }) }
            }
    }
}

data class ArticlesState(
    val refreshing: Boolean = true,
    val articles: List<Article>? = emptyList()
)
