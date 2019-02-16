package news.browser.common.net

import androidx.fragment.app.Fragment
import io.reactivex.Observable
import news.browser.module.news.model.BaseBean
import news.browser.module.news.model.NewsBean
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface NetApi {
    @GET("{path}")
    fun fetchNews(
            @Path("path") path: String,
            @QueryMap maps: Map<String, String>
    ): Observable<BaseBean<List<NewsBean>>>
}

interface INewsView {
    fun onNewsFetched(resp: BaseBean<List<NewsBean>>)
    fun onNewsFetchedFailed(throwable: Throwable)
}

interface IMainPage {
    fun onFragmentsFetched(fragments: List<Fragment>)
}