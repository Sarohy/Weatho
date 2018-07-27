package com.sarohy.weatho.weatho.View.Activity

import android.app.*
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.sarohy.weatho.weatho.View.Fragment.WeatherFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.*
import com.sarohy.weatho.weatho.Model.DBModel.Location
import com.sarohy.weatho.weatho.ViewModel.MainActivityViewModel
import android.content.Context
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.sarohy.weatho.weatho.*
import java.util.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.view.PagerAdapter


class MainActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    lateinit var viewModel: MainActivityViewModel
    private val LOG_TAG = MainActivity::class.java.simpleName + "Test: In Detail Fragment"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        init()
        setup()
        observers()
        alarmManager()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this as FragmentActivity).get(MainActivityViewModel::class.java)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
    }

    private fun setup() {
        container.adapter = mSectionsPagerAdapter
    }

    private fun alarmManager() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val autoUpdate = Integer.parseInt(prefs.getString("autoUpdate", "60")!!)
        val alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, WeatherUpdateReceiver::class.java)
        val alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        val calendar = Calendar.getInstance()
        Log.d(LOG_TAG, autoUpdate.toString());
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis+(1000 * 60 * autoUpdate).toLong(), (1000 * 60 * autoUpdate).toLong(), alarmIntent)
    }

    private fun observers() {
        viewModel.cities.observe(this as LifecycleOwner, Observer<List<Location>> { fetch ->
            if (fetch!=null) {
                (mSectionsPagerAdapter as SectionsPagerAdapter).updateList(fetch as ArrayList<Location>?)
                mSectionsPagerAdapter!!.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            val bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            startActivity(Intent(this, SettingActivity::class.java), bundle)
            return true
        }
        if (id == R.id.action_location) {
            val bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            startActivity(Intent(this, LocationActivity::class.java), bundle)
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        var array:ArrayList<Location>
        init {
            array = ArrayList<Location>()
        }

        override fun getItem(position: Int): Fragment {
            return WeatherFragment.newInstance(array.get(position))
        }

        override fun getCount(): Int {
            return array.size ?: 0
        }

        fun updateList(cities: ArrayList<Location>?) {
            if (cities != null) {
                array.clear()
                array.addAll(cities)
            }
        }

        override fun getItemPosition(`object`: Any): Int {
            return POSITION_NONE
        }

    }

    override fun onResume() {
        //viewModel.load()
        alarmManager()
        super.onResume()
    }
}
