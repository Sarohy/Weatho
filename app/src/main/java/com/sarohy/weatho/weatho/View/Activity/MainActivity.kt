package com.sarohy.weatho.weatho.View.Activity

import android.app.ActivityOptions
import android.app.AlarmManager
import android.app.PendingIntent
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.sarohy.weatho.weatho.Model.DBModel.Location
import com.sarohy.weatho.weatho.R
import com.sarohy.weatho.weatho.SharedPreferencesClass
import com.sarohy.weatho.weatho.View.Fragment.WeatherFragment
import com.sarohy.weatho.weatho.ViewModel.MainActivityViewModel
import com.sarohy.weatho.weatho.WeatherUpdateReceiver
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private lateinit var viewModel: MainActivityViewModel
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
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Vancouver"),Locale.US)
        Log.d("Tested",(calendar.time).toString())
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this as FragmentActivity).get(MainActivityViewModel::class.java)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
    }

    private fun setup() {
        container.adapter = mSectionsPagerAdapter
    }

    private fun alarmManager() {
        val prefs = SharedPreferencesClass(this)
        val autoUpdate = Integer.parseInt(prefs.autoUpdate!!)
        if (autoUpdate != -1) {
            val alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, WeatherUpdateReceiver::class.java)
            val alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
            val calendar = Calendar.getInstance()
            Log.d(LOG_TAG, autoUpdate.toString())
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis + (1000 * 60 * autoUpdate).toLong(), (1000 * 60 * autoUpdate).toLong(), alarmIntent)
        }
        else{
            val intent = Intent(this, WeatherUpdateReceiver::class.java)
            PendingIntent.getBroadcast(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT).cancel()
        }
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

        var array:ArrayList<Location> = ArrayList()

        override fun getItem(position: Int): Fragment {
            val f = WeatherFragment.newInstance(array[position])
            f.retainInstance = true
            return f
        }

        override fun getCount(): Int {
            return array.size
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

        fun update() {
            notifyDataSetChanged()
        }

    }

    override fun onResume() {
        alarmManager()
        mSectionsPagerAdapter?.update()
        super.onResume()
    }
}
