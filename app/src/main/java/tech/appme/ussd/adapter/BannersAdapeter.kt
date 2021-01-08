package tech.appme.ussd.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import uz.appme.ussd.Banner
import tech.appme.ussd.BannerFragment

class BannerPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var data: List<Banner> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItem(position: Int): Fragment {
        val d = data.getOrNull(position) ?: Banner(id = 0)
        return BannerFragment.newInstance(d)
    }

    override fun getCount(): Int {
        return data.size
    }
}