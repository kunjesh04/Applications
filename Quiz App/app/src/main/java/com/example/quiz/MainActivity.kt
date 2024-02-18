package com.example.quiz

import QuestionModel
import QuizListAdapter
import QuizModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var quizModelList: MutableList<QuizModel>
    lateinit var adapter: QuizListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizModelList = mutableListOf()
        getDataFromFirebase()

    }

    private fun setupRecyclerView(){
        adapter = QuizListAdapter(quizModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun getDataFromFirebase(){
        // Dummy Data

        val listQuestionModel = mutableListOf<QuestionModel>()
        listQuestionModel.add(QuestionModel("What is Android ?", mutableListOf("Language", "OS","System", "Service"), "OS"))
        listQuestionModel.add(QuestionModel("Who owns Android ?", mutableListOf("Meta", "IBM","Google", "Microsoft"), "Google"))

        quizModelList.add(QuizModel("1", "Programming", "Code Questions", "10", listQuestionModel))
        quizModelList.add(QuizModel("2", "Geography", "Geography Knowledge", "15", listQuestionModel))
        quizModelList.add(QuizModel("3", "Arts", "Art related Questions", "12", listQuestionModel))
        quizModelList.add(QuizModel("4", "Maths", "Math Questions", "5", listQuestionModel))
        setupRecyclerView()
    }
}