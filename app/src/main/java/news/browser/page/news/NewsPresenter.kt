package news.browser.page.news

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import news.browser.utils.INewsView
import news.browser.utils.RetrofitUtils
import news.browser.utils.Constant
import news.browser.model.NewsCommand

class NewsPresenter(private val view: INewsView) {
    fun fetchNews(newType: Int, page: Int, num: Int) {
        RetrofitUtils.fetchNews(Constant.URLS[newType], NewsCommand(page, num))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ resp -> view.onNewsFetched(resp) },
                        { t -> view.onNewsFetchedFailed(t) }).isDisposed
    }
}