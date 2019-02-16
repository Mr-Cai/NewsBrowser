package news.browser.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class BaseLazyFragment : Fragment() {
    protected var isVisibleToUser: Boolean = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?) = inflater.inflate(getContentId(), container, false)!!

    open fun getContentId() = 0
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        when {
            userVisibleHint -> {
                this.isVisibleToUser = true
                onVisible()
            }
            else -> {
                this.isVisibleToUser = false
                onInvisible()
            }
        }
    }

    open fun onVisible() = lazyLoad()
    open fun lazyLoad() = Unit
    open fun onInvisible() = Unit
}