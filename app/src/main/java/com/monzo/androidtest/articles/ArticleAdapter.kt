package com.monzo.androidtest.articles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.monzo.androidtest.R
import com.monzo.androidtest.articles.model.Article
import java.util.*

private var context: Context? = null

internal class ArticleAdapter(
        ctx: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val articles: MutableList<Article> = ArrayList()

    init {
        context = ctx
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.list_item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val articleViewHolder = holder as ArticleViewHolder
        articleViewHolder.bind(articles[position])
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun showArticles(articles: List<Article>) {
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(article: Article) {
            val headlineView = itemView.findViewById<TextView>(R.id.article_headline_textview)
            val thumbnailView = itemView.findViewById<ImageView>(R.id.article_thumbnail_imageview)

            headlineView.text = article.title
            Glide.with(context!!).load(article.thumbnail).into(thumbnailView)
        }
    }
}
