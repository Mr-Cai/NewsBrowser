package news.browser.module.news

import news.browser.module.news.model.BaseBean
import news.browser.module.news.model.NewsBean

interface INewsView {
    fun onNewsFetched(resp: BaseBean<List<NewsBean>>)
    fun onNewsFetchedFailed(throwable: Throwable)
}