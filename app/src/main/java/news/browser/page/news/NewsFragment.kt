package news.browser.page.news

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_news.*
import news.browser.R
import news.browser.base.BaseLazyFragment
import news.browser.utils.INewsView
import news.browser.model.BaseBean
import news.browser.model.NewsBean

open class NewsFragment : BaseLazyFragment(), INewsView {

    var newsType: Int = 0
    var mPresenter: NewsPresenter? = null
    var page: Int = 0
    var num: Int = 10
    private var adapter: NewsAdapter? = null
    private var dataList = ArrayList<NewsBean>()
    var isRefresh: Boolean = false

    companion object {
        fun newInstance(type: Int): NewsFragment {
            val fragment = NewsFragment()
            val bd = Bundle()
            bd.putInt("type", type)
            fragment.arguments = bd
            return fragment
        }
    }

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
                mPresenter!!.displayNews(newsType, page, num)
                isPrepared = false
            }
        }
    }

    var totalItemCount: Int = 0
    var lastVisibleItem: Int = 0
    var visibleItemCount: Int = 0
    var firstVisibleItem: Int = 0
    private fun initViews() {
        newsRecycler.layoutManager = LinearLayoutManager(activity)
        newsRecycler.adapter = adapter
        newsRecycler.itemAnimator!!.addDuration = 0
        val mScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> when {
                        !isRefresh && totalItemCount <= lastVisibleItem + 1 && totalItemCount >
                                visibleItemCount && visibleItemCount > 0 -> {
                            this@NewsFragment.page++
                            mPresenter!!.displayNews(newsType, page, num)
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

        }
        newsRecycler.addOnScrollListener(mScrollListener)
        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = true
            page = 0
            mPresenter!!.displayNews(newsType, page, num)
        }
    }

    private fun initData() {
        refreshLayout.isRefreshing = true
        newsType = arguments!!.getInt("type")
        mPresenter = NewsPresenter(this)
        adapter = NewsAdapter(dataList)
        page = 0
    }

    override fun onNewsFetched(resp: BaseBean<List<NewsBean>>) {
        when (page) {
            0 -> adapter!!.clearData()
        }
        adapter!!.addData(resp.newsData)
        refreshLayout.isRefreshing = false
    }

    override fun onNewsFetchedFailed(throwable: Throwable) = Unit
}