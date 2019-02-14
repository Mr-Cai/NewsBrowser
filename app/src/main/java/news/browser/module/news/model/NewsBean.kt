package news.browser.module.news.model

import com.google.gson.annotations.SerializedName

data class BaseBean<T>(
        val code: Int,
        val msg: String,
        @SerializedName("newslist")
        var newsData: T
)

data class NewsBean(
        @SerializedName("ctime")
        val updateTime: String,
        var title: String,
        var description: String,
        var picUrl: String,
        var url: String
)