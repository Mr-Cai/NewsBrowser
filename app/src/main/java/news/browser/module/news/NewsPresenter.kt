package news.browser.module.news

import news.browser.base.BaseCommand
import news.browser.base.BasePresenter
import news.browser.common.utils.RetrofitUtils
import news.browser.config.Constant
import news.browser.module.news.model.NewsCommand
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Jim on 2017/12/3.
 */
class NewsPresenter(val view: INewsView?): BasePresenter(){
    fun fetchNews(newType:Int,page:Int,num:Int){
        val cmd= NewsCommand(page, num)
        fetchNewsReal(Constant.URLS[newType],cmd)

    }

    fun fetchNewsReal(path:String,cmd: BaseCommand){
        RetrofitUtils.fetchNews(path,cmd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ resp->view?.onNewsFetched(resp) },{t ->view?.onNewsFetchedFailed(t)})
    }

    fun fetchWorldNews(cmd: BaseCommand) {
        RetrofitUtils.fetchWorldNews(cmd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ resp -> view?.onNewsFetched(resp) }, { t -> view?.onNewsFetchedFailed(t) })
    }


    fun fetchTravel(cmd: BaseCommand){
        RetrofitUtils.fetchTravel(cmd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ resp->view?.onNewsFetched(resp) },{t ->view?.onNewsFetchedFailed(t)})

    }

    fun fetchTiYu(cmd: BaseCommand){
        RetrofitUtils.fetchTiYu(cmd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ resp->view?.onNewsFetched(resp) },{t ->view?.onNewsFetchedFailed(t)})

    }

    fun fetchQiwen(cmd: BaseCommand){
        RetrofitUtils.fetchQiwen(cmd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ resp->view?.onNewsFetched(resp) },{t ->view?.onNewsFetchedFailed(t)})
    }

    fun fetchKeji(cmd: BaseCommand){
        RetrofitUtils.fetchKeji(cmd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ resp->view?.onNewsFetched(resp) },{t ->view?.onNewsFetchedFailed(t)})

    }

    fun fetchHuaBian(cmd: BaseCommand){
        RetrofitUtils.fetchHuaBian(cmd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ resp->view?.onNewsFetched(resp) },{t ->view?.onNewsFetchedFailed(t)})

    }

    fun fetchHealth(cmd: BaseCommand){
        RetrofitUtils.fetchHealth(cmd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ resp->view?.onNewsFetched(resp) },{t ->view?.onNewsFetchedFailed(t)})

    }
}