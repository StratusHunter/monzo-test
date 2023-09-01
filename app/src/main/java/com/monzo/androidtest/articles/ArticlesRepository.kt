package com.monzo.androidtest.articles

import article.model.ArticleDetail
import article.model.ArticleDetailMapper
import com.monzo.androidtest.api.GuardianService
import com.monzo.androidtest.articles.model.Article
import com.monzo.androidtest.articles.model.ArticleMapper
import io.reactivex.Single

class ArticlesRepository(
    private val guardianService: GuardianService,
    private val articleMapper: ArticleMapper,
    private val articleDetailMapper: ArticleDetailMapper
) {
    fun latestFintechArticles(): Single<List<Article>> {
        return guardianService.searchArticles("fintech,brexit")
            .map { articleMapper.map(it) }
    }

    fun getArticle(articleUrl: String): Single<ArticleDetail> {
        return guardianService.getArticle(articleUrl, "main,body,headline,thumbnail")
            .map { articleDetailMapper.map(it) }
    }
}
