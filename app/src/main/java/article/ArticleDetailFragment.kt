package article

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.monzo.androidtest.R
import com.monzo.androidtest.common.setupToolbar

class ArticleDetailFragment : Fragment(R.layout.fragment_article_detail) {

    private val viewModel by viewModels<ArticleDetailViewModel> {
        val navArgs by navArgs<ArticleDetailFragmentArgs>()
        ArticleDetailViewModel.Factory(navArgs.url)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        setupToolbar(view.findViewById(R.id.toolbar))

        val titleTextView = view.findViewById<TextView>(R.id.title_view)
        val bodyTextView = view.findViewById<TextView>(R.id.body_view)
        val imageView = view.findViewById<ImageView>(R.id.image_view)

        viewModel.state.observe(viewLifecycleOwner) { state ->

            state.article?.let {

                titleTextView.text = it.title
                bodyTextView.text = HtmlCompat.fromHtml(it.body, HtmlCompat.FROM_HTML_MODE_LEGACY)
                bodyTextView.movementMethod = LinkMovementMethod.getInstance()
                Glide.with(view).load(it.thumbnail).into(imageView)
            }
        }
    }
}