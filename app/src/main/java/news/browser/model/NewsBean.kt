package news.browser.model

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
open class NewsCommand(
        var page: Int,
        var num: Int,
        val key: String = "53a9f2b7b849346cb13fb4b56cac9ec8"
)
class MapFiled( val key: String,  val value: String)
