package com.example.triviagame

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.triviagame.databinding.ItemQuestionBinding
import com.example.triviagame.model.Question

class QuestionAdapter(private val listQuestion: List<Question>) :
    RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val currentQuestion = listQuestion[position]
        when(currentQuestion.type){
            "multiple" -> {
                holder.binding.apply {
                    when(radioGroupAnswer.checkedRadioButtonId){
                        answerOne.id -> if(answerOne.text == currentQuestion.correctAnswer) {
                            Toast.makeText(context, "true answer", Toast.LENGTH_LONG).show()
                        }
                    }
                    txtQuestion.text = currentQuestion.question.replace("&qout;", "& ")
                    answerOne.text = currentQuestion.correctAnswer.replace("&qout;", "& ")
                    answerTwo.text = currentQuestion.incorrectAnswers[0].replace("&qout;", "& ")
                    answerThree.text = currentQuestion.incorrectAnswers[1].replace("&qout;", "& ")
                    answerFour.text = currentQuestion.incorrectAnswers[2].replace("&qout;", "& ")
                }
            }
            "boolean" -> {
                holder.binding.apply {
                    txtQuestion.text = currentQuestion.question.replace("&qout;", "& ")
                    answerOne.text = currentQuestion.correctAnswer
                    answerTwo.text = currentQuestion.incorrectAnswers[0]
                    answerThree.visibility = View.GONE
                    answerFour.visibility = View.GONE
                }
            }
        }
    }

    override fun getItemCount() = listQuestion.size

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemQuestionBinding.bind(itemView)
    }
}