package news.browser.page.news

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import news.browser.model.NewsCommand
import news.browser.utils.Constant
import news.browser.utils.INewsView
import news.browser.utils.RetrofitUtils

class NewsPresenter(private val view: INewsView) {
    fun displayNews(newType: Int, page: Int, num: Int) {
        RetrofitUtils.fetchNews(Constant.URLS[newType], NewsCommand(page, num))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response -> view.onNewsFetched(response) },
                        { t -> view.onNewsFetchedFailed(t) }).isDisposed
    }
}