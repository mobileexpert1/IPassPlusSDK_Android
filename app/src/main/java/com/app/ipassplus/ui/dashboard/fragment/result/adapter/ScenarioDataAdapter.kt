package com.app.ipassplus.ui.dashboard.fragment.result.adapter
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.ipassplus.ui.dashboard.fragment.result.ChecksDetailsFragment
import com.app.ipassplus.ui.dashboard.fragment.result.FaceAuthFragment
import com.app.ipassplus.ui.dashboard.fragment.result.ImageListFragment
import com.app.ipassplus.ui.dashboard.fragment.result.ScenarioResultFragment
import com.app.ipassplus.ui.dashboard.fragment.result.TextDataFragment

class ScenarioDataAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 5
    override fun createFragment(position: Int): Fragment {

        return when(position) {
            0 -> { ScenarioResultFragment() }
            1 -> { TextDataFragment() }
            2 -> { ImageListFragment() }
            3 -> { FaceAuthFragment() }
            else -> { ChecksDetailsFragment() }
        }
    }
}