package news.browser.utils

import android.util.Log
import news.browser.model.MapFiled
import news.browser.model.NewsCommand
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitUtils {
    private val okHttp: OkHttpClient
        get() {
            val okHttpBuilder = OkHttpClient.Builder().apply {
                connectTimeout(3, TimeUnit.SECONDS)
                readTimeout(3, TimeUnit.SECONDS)
                writeTimeout(3, TimeUnit.SECONDS)
                addInterceptor { chain ->
                    Log.i("新闻接口", chain.request().toString())
                    chain.proceed(chain.request())
                }
            }
            return okHttpBuilder.build()
        }

    private fun createMaps(list: List<MapFiled>): Map<String, String> {
        val map = HashMap<String, String>()
        (0 until list.size).map { list[it] }.forEach { map[it.key] = it.value }
        return map
    }

    private fun createListFiled(command: NewsCommand): List<MapFiled> {
        val list = ArrayList<MapFiled>()
        for (filed in command.javaClass.declaredFields) {
            filed.isAccessible = true
            list.add(MapFiled(filed.name, filed.get(command).toString()))
        }
        return list
    }

    fun fetchNews(path: String, cmd: NewsCommand) = Retrofit.Builder()
            .baseUrl(Constant.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .build().create(NetApi::class.java)
            .observableNews(path, createMaps(createListFiled(cmd)))
}

