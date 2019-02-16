package news.browser.page.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm: FragmentManager, private val fragments: List<Fragment>) : FragmentStatePagerAdapter(fm) {
    private var titles = arrayOf("微信精选", "世界", "花边", "奇闻", "健康", "体育", "科技", "旅游")
    override fun getCount(): Int = titles.size
    override fun getItem(position: Int): Fragment = fragments[position]
    override fun getPageTitle(position: Int): CharSequence = titles[position]
}
