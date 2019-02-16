package news.browser.page.home

import androidx.fragment.app.Fragment
import news.browser.utils.IMainPage
import news.browser.page.news.NewsFragment

class MainPresenter(private val view: IMainPage) {
    fun fetchFragments(num: Int) {
        val fragments = ArrayList<Fragment>()
        var i = 0
        while (i < num) {
            fragments.add(NewsFragment.newInstance(i))
            i++
        }
        view.onFragmentsFetched(fragments)
    }
}