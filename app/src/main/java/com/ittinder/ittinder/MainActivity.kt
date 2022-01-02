package com.ittinder.ittinder

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.ittinder.ittinder.databinding.SwipeScreenBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: SwipeScreenBinding


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = SwipeScreenBinding.inflate(layoutInflater)
            val view = binding.root
            setContentView(view)

            val userStreamModel: RandomUserStreamViewModel by viewModels()
            val swipingModel: SwipingViewModel by viewModels()
            userStreamModel.RandomUserStreamResponse.observe(this) {
                binding.result.text = userStreamModel.RandomUserStreamResponse.value
            }
            binding.get.setOnClickListener {
                userStreamModel.getUserStream()
            }
            binding.like.setOnClickListener{
                val SwipeRight = Swiping(9, 1)
                swipingModel.postSwipeRight(SwipeRight)
            }
            binding.dislike.setOnClickListener{
                val SwipeLeft = Swiping(9,1)
                swipingModel.postSwipeLeft(SwipeLeft)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Profile -> Toast.makeText(this, "Opening profile", Toast.LENGTH_SHORT).show()
            R.id.Chat -> Toast.makeText(this, "Opening chat", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}