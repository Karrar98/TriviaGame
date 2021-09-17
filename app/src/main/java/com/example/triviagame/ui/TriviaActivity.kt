package com.example.triviagame.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.triviagame.databinding.ActivityTriviaBinding
import com.example.triviagame.fragment.QuestionFragment

class TriviaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTriviaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTriviaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val response = intent.getStringExtra("response")
        setFragment(QuestionFragment(response), ADD_FRAGMENT)
    }

    private fun setFragment(fragment: Fragment, status_fragment: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        when(status_fragment){
            ADD_FRAGMENT -> transaction.add(binding.fragmentContainerView.id, fragment)
            REPLACE_FRAGMENT -> transaction.replace(binding.fragmentContainerView.id, fragment)
        }
        transaction.commit()
    }

    companion object {
        val ADD_FRAGMENT = 1
        val REPLACE_FRAGMENT = 2
    }
}