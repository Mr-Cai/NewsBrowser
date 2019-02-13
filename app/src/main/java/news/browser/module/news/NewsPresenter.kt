package news.browser.module.news

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import news.browser.base.BaseCommand
import news.browser.base.BasePresenter
import news.browser.common.utils.RetrofitUtils
import news.browser.config.Constant
import news.browser.module.news.model.NewsCommand

class NewsPresenter(private val view: INewsView?) : BasePresenter() {
    fun fetchNews(newType: Int, page: Int, num: Int) {
        val cmd = NewsCommand(page, num)
        fetchNewsReal(Constant.URLS[newType], cmd)

    }

    private fun fetchNewsReal(path: String, cmd: BaseCommand) {
        RetrofitUtils.fetchNews(path, cmd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ resp -> view?.onNewsFetched(resp) },
                        { t -> view?.onNewsFetchedFailed(t) }).isDisposed
    }

}