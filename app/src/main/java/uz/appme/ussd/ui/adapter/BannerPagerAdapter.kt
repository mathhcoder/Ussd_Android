package uz.appme.ussd.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uz.appme.ussd.model.data.Banner
import uz.appme.ussd.ui.home.BannerFragment

class BannerPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var data: List<Banner> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItem(position: Int): Fragment {
        val d = data.getOrNull(position) ?: Banner(id = 0)
        return BannerFragment.newInstance(d)
    }

    override fun getCount() = data.size

    override fun getItemPosition(`object`: Any) = POSITION_NONE


}