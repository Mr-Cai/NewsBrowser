package news.browser.common.utils

import android.util.Log
import news.browser.BuildConfig
import news.browser.common.net.MapFiled
import news.browser.common.net.NetApi
import news.browser.config.Constant
import news.browser.module.news.model.NewsCommand
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitUtils {
    private lateinit var retrofit: Retrofit
    private val okHttp: OkHttpClient
        get() {
            val okHttpClientBuilder = OkHttpClient.Builder()
            when {
                BuildConfig.DEBUG -> {
                    okHttpClientBuilder.apply {
                        connectTimeout(5, TimeUnit.SECONDS)
                        readTimeout(5, TimeUnit.SECONDS)
                        writeTimeout(5, TimeUnit.SECONDS)
                        addInterceptor { chain ->
                            Log.i("新闻接口", chain.request().toString())
                            chain.proceed(chain.request())
                        }
                    }
                }
                else -> okHttpClientBuilder.apply {
                    connectTimeout(8, TimeUnit.SECONDS)
                    readTimeout(8, TimeUnit.SECONDS)
                    writeTimeout(8, TimeUnit.SECONDS)
                }
            }
            return okHttpClientBuilder.build()
        }

    private fun getRetrofit(): Retrofit {
        retrofit = Retrofit.Builder()
                .baseUrl(Constant.baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp)
                .build()
        return retrofit
    }

    private fun createMaps(list: List<MapFiled>): Map<String, String> {
        val map = HashMap<String, String>()
        (0 until list.size).map { list[it] }.forEach { map[it.getKey()] = it.getValue() }
        return map
    }

    private fun createListFiled(command: NewsCommand): List<MapFiled> {
        val list = ArrayList<MapFiled>()
        for (filed in command.javaClass.declaredFields) {
            val isAccess = filed.isAccessible
            filed.isAccessible = true
            when {
                filed.get(command) != null -> list.add(MapFiled(filed.name, filed.get(command).toString()))
            }
            filed.isAccessible = isAccess
        }
        return list
    }

    fun fetchNews(path: String, cmd: NewsCommand) = getRetrofit().create(NetApi::class.java)
            .fetchNews(path, createMaps(createListFiled(cmd)))
}