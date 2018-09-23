package news.browser.module.news.model

data class BaseBean<T>(val code: Int, val msg: String, var newslist: T)
data class NewsBean(val ctime: String, var title: String, var description: String, var picUrl: String, var url: String)