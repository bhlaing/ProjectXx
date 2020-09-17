package com.x.projectxx.ui.content.addcontent

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.x.projectxx.R
import com.x.projectxx.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContentActivity : BaseActivity() {

    companion object {
        fun makeAddContentIntent(context: Context) = Intent(context, AddContentActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_content)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AddContentFragment.newInstance())
                .commitNow()
        }
    }
}