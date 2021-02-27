package com.example.myapplication.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myapplication.fragments.ProductFragment
import com.example.myapplication.models.Data
import com.example.myapplication.models.SubCategory
import com.example.myapplication.models.SubData
import com.google.android.material.tabs.TabLayout

class FragmentAdapter(fm: FragmentManager, var tab: TabLayout): FragmentPagerAdapter(fm) {
    var fragmentList: ArrayList<Fragment> = ArrayList()
    var titleList: ArrayList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    fun addFragment(subCategory: SubData){
        fragmentList.add(ProductFragment.newInstance(subCategory.subId))
        titleList.add(subCategory.subName)
        notifyDataSetChanged()
    }
}