package com.sarohy.weatho.weatho.View.Activity

import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.sarohy.weatho.weatho.Model.DBModel.Location

import com.sarohy.weatho.weatho.R
import com.sarohy.weatho.weatho.View.Fragment.WeatherDetailFragment
import kotlinx.android.synthetic.main.activity_detail_weather.*
import kotlinx.android.synthetic.main.fragment_detail_weather.view.*

class DetailWeatherActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private val LOG_TAG = DetailWeatherActivity::class.java.simpleName + "Test: In Detail Fragment"
    private lateinit var location:Location;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_weather)
        setSupportActionBar(toolbar)
        location = intent.extras.get("Location") as Location
        Log.d(LOG_TAG,""+location.key)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        container.adapter = mSectionsPagerAdapter
    }


    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            return WeatherDetailFragment.newInstance(location)
        }

        override fun getCount(): Int {
            return 1
        }
    }
}
