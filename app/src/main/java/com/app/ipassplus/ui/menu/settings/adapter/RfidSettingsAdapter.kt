package com.app.ipassplus.ui.menu.settings.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.ipassplus.ui.menu.settings.athentication_settings.AuthenticationFragment
import com.app.ipassplus.ui.menu.settings.rfid_settings.DataGroupSettingFragment


class RfidSettingsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        return when(position) {
            0 -> { AuthenticationFragment() }
            1 -> { DataGroupSettingFragment() }
            else -> { AuthenticationFragment() }
        }
    }
}