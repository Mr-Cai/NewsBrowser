package news.browser.module.news

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import news.browser.common.utils.RetrofitUtils
import news.browser.config.Constant
import news.browser.module.news.model.NewsCommand

class NewsPresenter(private val view: INewsView) {
    fun fetchNews(newType: Int, page: Int, num: Int) {
        fetchNewsReal(Constant.URLS[newType], NewsCommand(page, num))
    }

    private fun fetchNewsReal(path: String, cmd: NewsCommand) {
        RetrofitUtils.fetchNews(path, cmd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ resp -> view.onNewsFetched(resp) },
                        { t -> view.onNewsFetchedFailed(t) }).isDisposed
    }

}