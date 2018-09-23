package news.browser.module.news

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import news.browser.R
import news.browser.base.BaseViewHolder
import news.browser.module.news.model.NewsBean
import news.browser.module.web.WebActivity

/**
 * Created by Jim on 2017/12/5.
 */
class NewsAdapter(var datas: ArrayList<NewsBean>, var context: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    private var footers: SparseArray<View> = SparseArray()
    private var headers: SparseArray<View> = SparseArray()
    private val baseItemFooter = 10001

    fun addData(data: List<NewsBean>) {
        this.datas.addAll(data)
        notifyItemRangeChanged(this.datas.size - data.size, data.size)
    }

    fun addFooters(view: View) {
        footers.put(baseItemFooter + footers.size(), view)
    }

    fun clearData() {
        datas.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (headers.get(viewType) != null) {
            return BaseViewHolder(headers.get(viewType), context)
        } else if (footers.get(viewType) != null) {
            return BaseViewHolder(footers.get(viewType), context)
        }
        val v = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false)
        return BaseViewHolder(v, context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (isHeaderView(position)) {
            return
        } else if (isFooterView(position)) {
            return
        }
        holder.setText(R.id.news_title, datas[position].title)
        holder.setText(R.id.news_time, datas[position].ctime)
        holder.setImageWithUrl(R.id.news_head_img, datas[position].picUrl)
        holder.setOnClickListener(R.id.news_layout, View.OnClickListener { WebActivity.start(context, datas[position].url) })
    }

    override fun getItemCount(): Int {
        return datas.size + headers.size() + footers.size()
    }

    private fun isHeaderView(position: Int): Boolean {
        return position < headers.size()
    }

    private fun isFooterView(position: Int): Boolean {
        return position >= headers.size() + datas.size
    }

    override fun getItemViewType(position: Int): Int {
        if (isFooterView(position)) {
            return footers.keyAt(position - datas.size - headers.size())
        } else if (isHeaderView(position)) {
            return headers.keyAt(position)
        }
        return super.getItemViewType(position)
    }

}