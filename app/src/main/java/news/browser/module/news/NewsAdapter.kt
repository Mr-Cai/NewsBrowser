package news.browser.module.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import news.browser.R
import news.browser.base.BaseViewHolder
import news.browser.module.news.model.NewsBean
import news.browser.module.web.WebActivity

class NewsAdapter(private var news: ArrayList<NewsBean>) :
        RecyclerView.Adapter<BaseViewHolder>() {
    fun addData(data: List<NewsBean>) {
        this.news.addAll(data)
        notifyItemRangeChanged(this.news.size - data.size, data.size)
    }

    fun clearData() {
        news.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.setText(R.id.news_title, news[position].title)
        holder.setText(R.id.news_time, news[position].ctime)
        holder.setImageWithUrl(R.id.news_head_img, news[position].picUrl)
        holder.setOnClickListener(R.id.news_layout, View.OnClickListener {
            WebActivity.start(holder.itemView.context, news[position].url)
        })
    }
}