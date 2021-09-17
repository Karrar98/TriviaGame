package com.example.triviagame.fragment

import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.example.triviagame.QuestionAdapter
import com.example.triviagame.databinding.FragmentQuestionBinding
import com.example.triviagame.model.Question
import com.example.triviagame.model.Response
import com.google.gson.Gson

class QuestionFragment(private val response: String?) : BaseFragment<FragmentQuestionBinding>() {
    override val LOG_TAG: String = ""
    override val bindingInflater: (LayoutInflater) -> FragmentQuestionBinding = FragmentQuestionBinding::inflate

    override fun setup() {
        val listQuestion = Gson().fromJson(response, Response::class.java).results
        val adapter = QuestionAdapter(listQuestion)
        binding?.recyclerViewQuestion?.adapter = adapter
    }

    override fun addCallBack() {

    }
}