package news.browser.module.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import news.browser.R

class MainActivity : AppCompatActivity(), MainActivityView {

    private lateinit var adapter: HomeVpAdapter
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
        presenter.fetchFragments(7)
    }

    private fun initView() {
        adapter = HomeVpAdapter(supportFragmentManager, fragments)
        vp.adapter = adapter
        vp.offscreenPageLimit = 5
        tabLayout.setupWithViewPager(vp)
    }

    override fun onFragmentsFetched(fragments: List<Fragment>) {
        this.fragments = fragments
    }
}
