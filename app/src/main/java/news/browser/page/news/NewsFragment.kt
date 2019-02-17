package news.browser.page.news

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_news.*
import news.browser.R
import news.browser.base.BaseLazyFragment
import news.browser.model.BaseBean
import news.browser.model.NewsBean
import news.browser.utils.INewsView

open class NewsFragment : BaseLazyFragment(), INewsView {
    var newsType = 0
    var newsPresenter: NewsPresenter? = null
    var page = 0
    var num = 10
    private var dataList = ArrayList<NewsBean>()
    var isRefresh: Boolean = false
    private val newsAdapter = GroupAdapter<ViewHolder>()
    private var isPrepared: Boolean = false
    override fun getContentId() = R.layout.fragment_news
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isPrepared = true
        lazyLoad()
    }

    override fun lazyLoad() {
        when {
            !isPrepared or !isVisibleToUser -> return
            else -> {
                initData()
                initViews()
                newsPresenter!!.displayNews(newsType, page, num)
                isPrepared = false
            }
        }
    }

    var totalItemCount = 0
    var lastVisibleItem = 0
    var visibleItemCount = 0
    var firstVisibleItem = 0
    private fun initViews() {
        newsRecycler.layoutManager = LinearLayoutManager(activity)
        newsRecycler.adapter = newsAdapter
        newsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> when {
                        !isRefresh && totalItemCount <= lastVisibleItem + 1 && totalItemCount >
                                visibleItemCount && visibleItemCount > 0 -> {
                            this@NewsFragment.page++
                            newsPresenter!!.displayNews(newsType, page, num)
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                totalItemCount = layoutManager.itemCount
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                visibleItemCount = layoutManager.childCount
            }

        })
        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = true
            page = 0
            newsPresenter!!.displayNews(newsType, page, num)
        }
    }

    private fun initData() {
        refreshLayout.isRefreshing = true
        newsType = arguments!!.getInt("type")
        newsPresenter = NewsPresenter(this)
        page = 0
    }

    override fun onNewsFetched(response: BaseBean<List<NewsBean>>) {
        when (page) {
            0 -> newsAdapter.clear()
        }
        response.newsData.forEach {
            newsAdapter.add(NewsItem(it))
        }
        refreshLayout.isRefreshing = false
    }

    override fun onNewsFetchedFailed(throwable: Throwable) = Unit

    companion object {
        fun newInstance(type: Int): NewsFragment {
            val fragment = NewsFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }
}
