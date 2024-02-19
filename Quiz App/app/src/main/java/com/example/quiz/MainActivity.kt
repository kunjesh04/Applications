package com.example.quiz

import QuestionModel
import QuizListAdapter
import QuizModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quiz.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase

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
        binding.loadPb.visibility = View.GONE
        adapter = QuizListAdapter(quizModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun getDataFromFirebase(){
        // Dummy Data
        //val listQuestionModel = mutableListOf<QuestionModel>()
        //listQuestionModel.add(QuestionModel("What is Android ?", mutableListOf("Language", "OS","System", "Service"), "OS"))
        //listQuestionModel.add(QuestionModel("Who owns Android ?", mutableListOf("Meta", "IBM","Google", "Microsoft"), "Google"))
        //listQuestionModel.add(QuestionModel("Who owns GitHub ?", mutableListOf("Meta", "IBM","Google", "Microsoft"), "Microsoft"))
        //listQuestionModel.add(QuestionModel("Who owns Facebook ?", mutableListOf("Meta", "IBM","Google", "Microsoft"), "Meta"))
        //listQuestionModel.add(QuestionModel("Who owns iPhone ?", mutableListOf("Meta", "IBM","Google", "Apple"), "Apple"))

        //quizModelList.add(QuizModel("1", "Programming", "Code Questions", "10", listQuestionModel))
        //quizModelList.add(QuizModel("2", "Geography", "Geography Knowledge", "15", listQuestionModel))
        //quizModelList.add(QuizModel("3", "Arts", "Art related Questions", "12", listQuestionModel))
        //quizModelList.add(QuizModel("4", "Maths", "Math Questions", "5", listQuestionModel))

        binding.loadPb.visibility = View.VISIBLE

        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener { dataSnapshot->
                if(dataSnapshot.exists()){
                    for(snapshot in dataSnapshot.children){
                        val quizModel = snapshot.getValue(QuizModel::class.java)
                        if(quizModel!=null){
                            quizModelList.add(quizModel)
                        }
                    }
                }
                setupRecyclerView()
            }
    }
}