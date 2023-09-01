package com.monzo.androidtest.articles

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.monzo.androidtest.R
import com.monzo.androidtest.articles.model.Article

internal class ArticleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val articles: MutableList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
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
        this.articles.addAll(articles.sortedByDescending { it.published })
        notifyDataSetChanged()
    }

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(article: Article) {
            val headlineView = itemView.findViewById<TextView>(R.id.article_headline_textview)
            val dateView = itemView.findViewById<TextView>(R.id.article_date_textview)
            val thumbnailView = itemView.findViewById<ImageView>(R.id.article_thumbnail_imageview)
            val context = itemView.context

            headlineView.text = article.title
            dateView.text = DateFormat.getDateFormat(context).format(article.published)
            Glide.with(context).load(article.thumbnail).circleCrop().into(thumbnailView)
        }
    }
}
