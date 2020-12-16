package jp.techacademy.moe.hatori.kadaiapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(private val fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    val titleIds = listOf(R.string.tab_title_api, R.string.tab_title_favorite)

    val fragments = listOf(ApiFragment(), FavoriteFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {

        val fragment = fragments[position]
        if (fragment is ApiFragment) {
            if(fragmentActivity is MainActivity) {
                fragment.callback = fragmentActivity //fragmentActivityはMainActivityってことになる
            }
        }

        return fragments[position]
    }
}