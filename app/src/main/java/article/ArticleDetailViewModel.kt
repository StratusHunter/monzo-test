package article

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import article.model.ArticleDetail
import com.monzo.androidtest.HeadlinesApp
import com.monzo.androidtest.articles.ArticlesRepository
import com.monzo.androidtest.common.BaseViewModel
import com.monzo.androidtest.common.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArticleDetailViewModel(
    private val url: String,
    private val repository: ArticlesRepository
) : BaseViewModel<ArticleDetailState>(ArticleDetailState()) {

    init {
        onRefresh()
    }

    companion object {
        fun Factory(url: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HeadlinesApp)
                ArticleDetailViewModel(url, HeadlinesApp.from(application).createRepository(application))
            }
        }
    }

    private fun onRefresh() {
        disposables += repository.getArticle(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { article, error ->
                setState { copy(article = article.takeIf { error == null }) }
            }
    }
}

data class ArticleDetailState(
    val article: ArticleDetail? = null
)
