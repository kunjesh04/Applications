package com.example.quiz

import QuestionModel
import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.quiz.databinding.ActivityQuizBinding
import com.example.quiz.databinding.ScoreDialogBinding

class QuizActivity : AppCompatActivity(),View.OnClickListener {

    companion object{
        var questionModelList : List<QuestionModel> = listOf()
        var time : String = ""
    }

    lateinit var binding: ActivityQuizBinding
    var currQuestionIndex = 0;
    var selectedAnswer = ""
    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            Btn0.setOnClickListener(this@QuizActivity)
            Btn1.setOnClickListener(this@QuizActivity)
            Btn2.setOnClickListener(this@QuizActivity)
            Btn3.setOnClickListener(this@QuizActivity)
            NextBtn.setOnClickListener(this@QuizActivity)
        }

        loadQuestions()
        startTimer()
    }

    private fun startTimer() {
        val totalTimeInMillis = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMillis, 1000L){
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

        if(currQuestionIndex== questionModelList.size){
            finishQuiz()
            return
        }

        binding.apply {
            qIndicator.text = "Question ${currQuestionIndex+1} / ${questionModelList.size}"
            qProgressIndicator.progress = ((currQuestionIndex+1).toFloat() / questionModelList.size.toFloat() * 100 ).toInt()
            questionText.text = questionModelList[currQuestionIndex].question
            Btn0.text = questionModelList[currQuestionIndex].options[0]
            Btn1.text = questionModelList[currQuestionIndex].options[1]
            Btn2.text = questionModelList[currQuestionIndex].options[2]
            Btn3.text = questionModelList[currQuestionIndex].options[3]

        }
    }

    override fun onClick(view: View?) {

        binding.apply{
            Btn0.setBackgroundColor(getColor(R.color.gray))
            Btn1.setBackgroundColor(getColor(R.color.gray))
            Btn2.setBackgroundColor(getColor(R.color.gray))
            Btn3.setBackgroundColor(getColor(R.color.gray))
        }

        val clickedBtn = view as Button
        if(clickedBtn.id==R.id.Next_Btn){

            // No option selected
            if(selectedAnswer.isEmpty()){
                Toast.makeText(applicationContext, "Select an Answer to Continue", Toast.LENGTH_SHORT).show()
                return
            }

            // Next Btn is clicked
            if(selectedAnswer == questionModelList[currQuestionIndex].correct){
                score++
                Log.i("Score of Quiz ", score.toString())
            }
            currQuestionIndex++
            loadQuestions()
        }else{
            // Option Btn is clicked
            selectedAnswer = clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.orange))
        }
    }

    private fun finishQuiz(){
        val totalQuestions = questionModelList.size
        val percentage = ((score.toFloat() / totalQuestions.toFloat()) * 100).toInt()

        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text = "$percentage %"
            if(percentage>=50){
                scoreTitle.text = "Congrats! You have passed"
                scoreTitle.setTextColor(Color.BLUE)
            }else{
                scoreTitle.text = "Sorry, You have failed"
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text = "$score out of $totalQuestions are correct"
            finishBtn.setOnClickListener{
                finish()
            }
        }

        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()
    }
}