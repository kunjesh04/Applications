package com.example.quiz

import QuestionModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import com.example.quiz.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {

    companion object{
        var questionModelList : List<QuestionModel> = listOf()
        var time : String = ""
    }

    lateinit var binding: ActivityQuizBinding
    var currQuestionIndex = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadQuestions()
        startTimer()
    }

    private fun startTimer() {
        val TotalTimeInMillis = time.toInt() * 60 * 1000L
        object : CountDownTimer(TotalTimeInMillis, 1000L){
            override fun onTick(msUntilFinished: Long) {
                val seconds = msUntilFinished/1000
                val minutes = seconds/60
                val remainingSeconds = seconds % 60
                binding.timeIndicator.text = String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                // Finish the Quiz
            }

        }.start()
    }

    private fun loadQuestions(){
        binding.apply {
            qIndicator.text = "Question ${currQuestionIndex+1} / ${questionModelList.size}"
            qProgressIndicator.progress =
                (currQuestionIndex.toFloat() / questionModelList.size.toFloat() * 100 ).toInt()
            questionText.text = questionModelList[currQuestionIndex].question
            Btn0.text = questionModelList[currQuestionIndex].options[0]
            Btn1.text = questionModelList[currQuestionIndex].options[1]
            Btn2.text = questionModelList[currQuestionIndex].options[2]
            Btn3.text = questionModelList[currQuestionIndex].options[3]

        }
    }
}