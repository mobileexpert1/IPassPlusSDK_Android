package com.app.ipassplus.ui.dashboard.fragment.result.adapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ScenariosResultAdapter(fm: FragmentManager,) : FragmentStatePagerAdapter(fm) {

    private var fragments: MutableList<Fragment> = arrayListOf()
    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }
    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }
    override fun getCount(): Int {
        return fragments.size
    }
}