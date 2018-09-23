package news.browser.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class BaseLazyFragment : Fragment() {

    protected var isVisbileToUser: Boolean = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = inflater.inflate(getContentId(), container, false)

    open fun getContentId(): Int = 0

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isVisbileToUser = true
            onVisible()
        } else {
            isVisbileToUser = false
            onInvisible()
        }
    }

    open fun onVisible() {
        lazyLoad()
    }

    open fun lazyLoad() {}

    open fun onInvisible() {}
}