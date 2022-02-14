package com.the.firsttask


import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.the.firsttask.databinding.ActivityDrawerBinding
import com.the.firsttask.sharedpreference.SettingsSharedPreference
import com.the.firsttask.ui.alarm.AlarmFragment
import com.the.firsttask.ui.calculator.CalculatorFragment
import com.the.firsttask.ui.converter.ConverterFragment
import com.the.firsttask.ui.movie.MovieListFragment
import com.the.firsttask.ui.setting.SettingFragment
import com.the.firsttask.utils.ConstantHelper
import com.the.firsttask.utils.LanguageUtils
import com.the.firsttask.utils.NetworkUtils
import com.the.firsttask.utils.ThemeUtils
import java.util.*


class DrawerActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDrawerBinding
    private var drawerToggle: ActionBarDrawerToggle? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.onActivityCreateSetTheme(this)
        LanguageUtils.setLocale(this@DrawerActivity)

        binding = ActivityDrawerBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.appBarDrawer.toolbar)
        supportActionBar?.elevation = 0F
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.drawerLayout

        setupDrawerContent(binding.navView)

        drawerToggle = setupDrawerToggle()
        drawerToggle?.isDrawerIndicatorEnabled = true

        drawerToggle?.syncState()


        binding.drawerLayout.addDrawerListener(drawerToggle!!)

        if (savedInstanceState == null) {
            binding.navView.menu.performIdentifierAction(R.id.nav_converter, 0)
        }

        if (intent.extras !=null){

            if(intent.extras!!.getString(ConstantHelper.ALARM)=="Alarm")
            {
                binding.navView.menu.performIdentifierAction(R.id.nav_alarm, 0)
            }
        }

        binding.tvVersionName.text = getString(R.string.version_name) + BuildConfig.VERSION_NAME

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            SettingsSharedPreference.getInstance(this).setFirebaseId(token)
            Log.e("TAG", token.toString())

        })


    }


    private fun setupDrawerToggle(): ActionBarDrawerToggle {

        return ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarDrawer.toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }


    private fun selectDrawerItem(menuItem: MenuItem) {

        var fragment: Fragment? = null
        val fragmentClass: Class<*>
        when (menuItem.itemId) {
            R.id.nav_converter -> fragmentClass = ConverterFragment::class.java
            R.id.nav_calculator -> fragmentClass = CalculatorFragment::class.java
            R.id.nav_movie -> fragmentClass = MovieListFragment::class.java
            R.id.nav_setting -> fragmentClass = SettingFragment::class.java
            R.id.nav_alarm -> fragmentClass = AlarmFragment::class.java
            else -> fragmentClass = ConverterFragment::class.java
        }

        try {
            fragment = fragmentClass.newInstance() as Fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragment != null) {
            fragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_content_drawer, fragment).commit()
        }

        menuItem.isChecked = true

        title = menuItem.title

        binding.drawerLayout.closeDrawers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle?.onOptionsItemSelected(item) == true) {
            return true
        }
        when (item.itemId) {
            R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {

        NetworkUtils.checkConnectivity(this@DrawerActivity)
        super.onResume()
    }

    override fun onPause() {
        NetworkUtils.unregisterCallback(this@DrawerActivity)
        super.onPause()
    }


}