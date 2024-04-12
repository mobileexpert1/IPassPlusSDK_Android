package com.app.ipassplus.ui.menu.settings.adapter
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.ipassplus.ui.menu.settings.edl_settings.eDLSettingFragment
import com.app.ipassplus.ui.menu.settings.eid_settings.eIDSettingFragment
import com.app.ipassplus.ui.menu.settings.epassport_settings.EpassportSettingFragment


class DataGroupsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        return when(position) {
            0 -> { EpassportSettingFragment() }
            1 -> { eIDSettingFragment() }
            else -> { eDLSettingFragment() }
        }
    }
}