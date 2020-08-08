package com.x.projectxx.global

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.x.projectxx.R
import com.x.projectxx.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    val db = Firebase.firestore

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.loginFlowFragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        setSupportActionBar(binding.toolbar)

    }

    private fun testCreateUser() {
//
//        val user = hashMapOf(
//            "first" to "Ada",
//            "last" to "Lovelace",
//            "born" to 1815
//        )

// Add a new document with a generated ID
//        db.collection("users")
//            .add(user)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }

    }


}