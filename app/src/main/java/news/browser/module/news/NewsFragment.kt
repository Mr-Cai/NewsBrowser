package news.browser.module.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_news.*
import news.browser.R
import news.browser.base.BaseLazyFragment
import news.browser.module.news.model.BaseBean
import news.browser.module.news.model.NewsBean

open class NewsFragment : BaseLazyFragment(), INewsView, SwipeRefreshLayout.OnRefreshListener {

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

    override fun getContentId(): Int {
        return R.layout.fragment_news
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isPrepared = true
        lazyLoad()
    }

    override fun lazyLoad() {
        if (!isPrepared or !isVisbileToUser) return
        initData()
        initViews()
        mPresenter?.fetchNews(newsType, page, num)
        isPrepared = false
    }

    var totalItemCount: Int = 0
    var lastVisibleItem: Int = 0
    var visibleItemCount: Int = 0
    var firstVisibleItem: Int = 0
    private fun initViews() {
        news_rcv.layoutManager = LinearLayoutManager(activity)
        news_rcv.adapter = adapter
        news_rcv.itemAnimator!!.addDuration = 0
        val mScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    if (!isRefresh && totalItemCount <= lastVisibleItem + 1 && totalItemCount > visibleItemCount && visibleItemCount > 0) {
                        this@NewsFragment.page++
                        mPresenter?.fetchNews(newsType, page, num)
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
        news_rcv.addOnScrollListener(mScrollListener)
        adapter?.addFooters(LayoutInflater.from(activity).inflate(R.layout.item_footer, news_rcv, false))
        swl.setOnRefreshListener(this)
    }

    private fun initData() {
        newsType = arguments!!.getInt("type")
        mPresenter = NewsPresenter(this)
        adapter = NewsAdapter(dataList, activity!!)
        page = 0
    }

    override fun onNewsFetched(resp: BaseBean<List<NewsBean>>) {
        swl?.isRefreshing = false
        Log.d("TAG", "get resp: " + resp.toString())
        if (resp.code == 200) {
            Log.d("TAG", "news fetched")
            if (page == 0) {
                Log.d("TAG", "clear data")
                adapter?.clearData()
            }
            adapter?.addData(resp.newslist)
        }
    }

    override fun onNewsFetchedFailed(throwable: Throwable) {
        swl?.isRefreshing = false
        Log.d("TAG", "get resp error: " + throwable.toString())

    }

    override fun onRefresh() {
        swl?.isRefreshing = true
        page = 0
        mPresenter?.fetchNews(newsType, page, num)
    }
}