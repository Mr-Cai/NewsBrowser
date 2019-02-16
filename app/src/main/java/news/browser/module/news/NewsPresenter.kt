package news.browser.module.news

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import news.browser.common.net.INewsView
import news.browser.common.utils.RetrofitUtils
import news.browser.config.Constant
import news.browser.module.news.model.NewsCommand

class NewsPresenter(private val view: INewsView) {
    fun fetchNews(newType: Int, page: Int, num: Int) {
        RetrofitUtils.fetchNews(Constant.URLS[newType], NewsCommand(page, num))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ resp -> view.onNewsFetched(resp) },
                        { t -> view.onNewsFetchedFailed(t) }).isDisposed
    }
}