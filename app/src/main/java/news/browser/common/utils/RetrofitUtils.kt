package news.browser.common.utils

import android.util.Log
import io.reactivex.Observable
import news.browser.BuildConfig
import news.browser.base.BaseCommand
import news.browser.common.net.MapFiled
import news.browser.common.net.NetApi
import news.browser.config.Constant
import news.browser.module.news.model.BaseBean
import news.browser.module.news.model.NewsBean
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitUtils {

    private var retrofit: Retrofit? = null

    private val okHttp: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            when {
                BuildConfig.DEBUG -> {
                    builder.connectTimeout(5, TimeUnit.SECONDS)
                            .readTimeout(5, TimeUnit.SECONDS)
                            .writeTimeout(5, TimeUnit.SECONDS)
                    builder.addInterceptor { chain ->
                        Log.i("调试", chain.request().toString())
                        chain.proceed(chain.request())
                    }
                }
                else -> builder.connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
            }
            return builder.build()
        }

    private fun getRetrofit(): Retrofit {
        when (retrofit) {
            null -> retrofit = Retrofit.Builder()
                    .baseUrl(Constant.baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttp)
                    .build()
        }
        return retrofit!!
    }

    private fun createMaps(list: List<MapFiled>): Map<String, String> {
        val map = HashMap<String, String>()
        val size = list.size
        (0 until size)
                .map { list[it] }
                .forEach { map[it.getKey()] = it.getValue() }
        return map
    }

    private fun createListFiled(command: BaseCommand): List<MapFiled> {
        val list = ArrayList<MapFiled>()
        val fields = command.javaClass.declaredFields
        for (filed in fields) {
            val isAccess = filed.isAccessible
            filed.isAccessible = true
            when {

                filed.get(command) != null -> list.add(MapFiled(filed.name, filed.get(command).toString()))
            }
            filed.isAccessible = isAccess
        }
        return list
    }

    fun fetchNews(path: String, cmd: BaseCommand) =
            getRetrofit().create(NetApi::class.java).fetchNews(path, createMaps(createListFiled(cmd)))

    fun fetchWorldNews(cmd: BaseCommand): Observable<BaseBean<List<NewsBean>>> {
        return getRetrofit().create(NetApi::class.java).fetchWorldNews(createMaps(createListFiled(cmd)))
    }

    fun fetchHuaBian(cmd: BaseCommand): Observable<BaseBean<List<NewsBean>>> {
        return getRetrofit().create(NetApi::class.java).fetchHuaBian(createMaps(createListFiled(cmd)))
    }

    fun fetchQiwen(cmd: BaseCommand): Observable<BaseBean<List<NewsBean>>> {
        return getRetrofit().create(NetApi::class.java).fetchQiwen(createMaps(createListFiled(cmd)))
    }

    fun fetchHealth(cmd: BaseCommand): Observable<BaseBean<List<NewsBean>>> {
        return getRetrofit().create(NetApi::class.java).fetchHealth(createMaps(createListFiled(cmd)))
    }

    fun fetchTiYu(cmd: BaseCommand): Observable<BaseBean<List<NewsBean>>> {
        return getRetrofit().create(NetApi::class.java).fetchTiYu(createMaps(createListFiled(cmd)))
    }

    fun fetchKeji(cmd: BaseCommand): Observable<BaseBean<List<NewsBean>>> {
        return getRetrofit().create(NetApi::class.java).fetchKeji(createMaps(createListFiled(cmd)))
    }

    fun fetchTravel(cmd: BaseCommand): Observable<BaseBean<List<NewsBean>>> {
        return getRetrofit().create(NetApi::class.java).fetchTravel(createMaps(createListFiled(cmd)))
    }
}