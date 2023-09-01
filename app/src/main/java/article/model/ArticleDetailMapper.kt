package article.model

import com.monzo.androidtest.api.model.ApiArticleResponse

class ArticleDetailMapper {

    fun map(apiArticleResponse: ApiArticleResponse): ArticleDetail {

        val content = apiArticleResponse.response.content
        val fields = content.fields

        return ArticleDetail(
            id = content.id,
            thumbnail = fields?.thumbnail ?: "",
            title = fields?.headline ?: "",
            body = fields?.body ?: "",
        )
    }
}