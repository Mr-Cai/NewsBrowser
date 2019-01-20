package news.browser.base

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import news.browser.R

class BaseViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
    private var mViews: SparseArray<View> = SparseArray()


    private fun <T> getView(viewId: Int): T {
        var v = mViews.get(viewId)
        if (v == null) {
            v = itemView.findViewById(viewId)
            mViews.put(viewId, v)
        }
        return v as T
    }

    fun setText(viewId: Int, value: String) {
        getView<TextView>(viewId).text = value
    }

    fun setImageWithUrl(viewId: Int, url: String) {
        val options = RequestOptions().centerCrop().placeholder(R.mipmap.icon).error(R.mipmap.icon)
        Glide.with(context).load(url).apply(options).into(getView(viewId))
    }

    fun setOnClickListener(viewId: Int, listener: View.OnClickListener) {
        getView<View>(viewId).setOnClickListener(listener)
    }

}