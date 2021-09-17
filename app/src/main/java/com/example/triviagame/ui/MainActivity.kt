package com.example.triviagame.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.triviagame.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity(), Callback {

    private lateinit var binding: ActivityMainBinding
    private val client = OkHttpClient()
    private var listCategory = listOf(
        "General Knowledge",
        "Entertainment: Books",
        "Entertainment: Film",
        "Entertainment: Music",
        "Entertainment: Musicals & Theatres",
        "Entertainment: Television",
        "Entertainment: Video Games",
        "Entertainment: Board Games",
        "Science & Nature",
        "Science: Computers",
        "Science: Mathematics",
        "Mythology",
        "Sports",
        "Geography",
        "History",
        "Politics",
        "Art",
        "Celebrities",
        "Animals",
        "Vehicles",
        "Entertainment: Comics",
        "Science: Gadgets ",
        "Entertainment: Japanese Anime & Manga",
        "Entertainment: Cartoon & Animations"
    )
    private val listDifficulty = listOf("Easy", "Medium", "Hard")
    private val listType = listOf("Multiple Choice", "True / False")
    private lateinit var categorySelected: String
    private lateinit var difficultySelected: String
    private lateinit var typeSelected: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSpinner()
        binding.btnFetch.setOnClickListener {
            val amount = binding.txtAmount.editText?.text.toString()
            getResponse(
                amount = amount,
                category = categorySelected,
                difficulty = difficultySelected,
                type = typeSelected
            )
        }
    }

    private fun makeRequest(
        amount: String,
        category: String,
        difficulty: String,
        type: String
    ): Request {
        val url = HttpUrl.Builder()
            .scheme("https")
            .host("opentdb.com")
            .addPathSegment("api.php")
            .addQueryParameter("amount", amount)
            .addQueryParameter("category", category)
            .addQueryParameter("difficulty", difficulty)
            .addQueryParameter("type", type)
            .build()

        return Request.Builder().url(url).build()
    }

    private fun getResponse(amount: String, category: String, difficulty: String, type: String) {
        val request = makeRequest(amount, category, difficulty, type)
        client.newCall(request).enqueue(this)
    }

    override fun onFailure(call: Call, e: IOException) {
        Log.i("MAIN_ACTIVITY", e.message.toString())
    }

    override fun onResponse(call: Call, response: Response) {
        runOnUiThread {
            val intent = Intent(this@MainActivity, TriviaActivity::class.java)
            intent.putExtra("response", response.body?.string())
            startActivity(intent)
        }
    }

    private fun initSpinner() {
        val adapterCategory = ArrayAdapter(this, android.R.layout.simple_spinner_item, listCategory)
        val adapterDifficulty = ArrayAdapter(this, android.R.layout.simple_spinner_item, listDifficulty)
        val adapterType = ArrayAdapter(this, android.R.layout.simple_spinner_item, listType)
        binding.spinnerCategory.apply {
            adapter = adapterCategory
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    categorySelected = "${position + 9}"
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }
        binding.spinnerDifficulty.apply {
            adapter = adapterDifficulty
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    difficultySelected = listDifficulty[p2].lowercase()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }
        binding.spinnerType.apply {
            adapter = adapterType
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    typeSelected = when(position) {
                        0 -> "multiple"
                        else -> "boolean"
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }
    }
}