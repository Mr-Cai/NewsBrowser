package news.browser.page.news

import android.content.Intent
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_news.view.*
import news.browser.R
import news.browser.model.NewsBean
import news.browser.page.web.WebActivity

class NewsItem(private var news: NewsBean) : Item<ViewHolder>() {
    override fun getLayout() = R.layout.item_news
    override fun bind(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val itemView = holder.itemView
        itemView.startAnimation(AnimationUtils.loadAnimation(context,
                androidx.appcompat.R.anim.abc_slide_in_bottom))
        itemView.descTxT.text = news.description
        itemView.titleTxT.text = news.title
        itemView.updateTimeTxT.text = news.updateTime
        Glide.with(context).load(news.picUrl).into(itemView.coverPic)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("url", news.url)
            context.startActivity(intent)
        }
    }
}