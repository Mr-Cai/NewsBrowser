package news.browser.module.home

import androidx.fragment.app.Fragment

interface MainActivityView {
    fun onFragmentsFetched(fragments: List<Fragment>)
}