package news.browser.page.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import news.browser.R
import news.browser.utils.Constant
import news.browser.utils.IMainPage

class MainActivity : AppCompatActivity(), IMainPage {

    private lateinit var adapter: ViewPagerAdapter
    private lateinit var fragments: List<Fragment>
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initView()
    }

    private fun initData() {
        presenter = MainPresenter(this)
        fragments = ArrayList()
        presenter.fetchFragments(Constant.URLS.size)
    }

    private fun initView() {
        adapter = ViewPagerAdapter(supportFragmentManager, fragments)
        vp.adapter = adapter
        tabLayout.setupWithViewPager(vp)
    }

    override fun onFragmentsFetched(fragments: List<Fragment>) {
        this.fragments = fragments
    }
}
