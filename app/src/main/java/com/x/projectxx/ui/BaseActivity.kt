package com.x.projectxx.ui

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.x.projectxx.R
import com.x.projectxx.ui.settings.MainSettingsActivity

open class BaseActivity: AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId ) {
            android.R.id.home -> onBackPressed()
            R.id.settingsMenuItem ->startActivity(MainSettingsActivity.makeChatIntent(this))
        }

        return super.onOptionsItemSelected(item)
    }

    protected fun showBackButton(toolbar: Toolbar) {
        setSupportActionBar(toolbar)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true);
            supportActionBar?.setDisplayShowHomeEnabled(true);
        }
    }
}