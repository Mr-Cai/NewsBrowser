package news.browser.module.news

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*
import news.browser.R
import news.browser.module.news.model.NewsBean
import news.browser.module.web.WebActivity

class NewsAdapter(private var news: ArrayList<NewsBean>) :
        RecyclerView.Adapter<NewsAdapter.BaseViewHolder>() {
    fun addData(data: List<NewsBean>) {
        this.news.addAll(data)
        notifyItemRangeChanged(this.news.size - data.size, data.size)
    }

    fun clearData() {
        news.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(LayoutInflater
            .from(parent.context).inflate(R.layout.item_news, parent, false))

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val context = holder.itemView.context
        val itemView = holder.itemView
        itemView.news_title.text = news[position].title
        itemView.news_time.text = news[position].updateTime
        Glide.with(context).load(news[position].picUrl).into(itemView.news_head_img)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("url", news[position].url)
            context.startActivity(intent)
        }
    }

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}