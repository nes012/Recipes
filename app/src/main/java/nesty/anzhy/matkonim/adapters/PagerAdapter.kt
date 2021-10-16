package nesty.anzhy.matkonim.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(
   private val resultBundle: Bundle,
   private val fragments: ArrayList<Fragment>,
   fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        fragments[position].arguments = resultBundle
        return fragments[position]
    }
    //in this class we will have 3 parameters.
    //1st is a bundle object. we're going to use bundle to parse the data from details activity to fragments
    //2nd is list our fragments
    //3rd - fragment activity
}