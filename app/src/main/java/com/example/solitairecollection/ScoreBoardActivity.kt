package com.example.solitairecollection

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.solitairecollection.databinding.ActivityScoreBoardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScoreBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreBoardBinding
    var tableData = ArrayList<UserScore>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScoreBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = getString(R.string.score_board)
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
//        repeat(10) {
//            val number: Int = (100 * Math.random()).toInt()
//            FirebaseHandler().addScoreData(
//                Score(
//                    "nickname$number",
//                    800 + number - (100 * Math.random()).toInt()
//                )
//            )
//        }

        initRecycleView()
    }

    //    리사이클러 뷰 초기화
    private fun initRecycleView() {
        CoroutineScope(Dispatchers.Main).launch { // 동기. 안쪽 코드는 메인과 다른 쓰레드로 실행.
            val response = withContext(Dispatchers.IO) {
                FirebaseHandler().getAllScoreData()
            } // CoroutineScope(Dispatchers.IO).async{}.await 과 같음
            Log.i("data_result", response.toString())
//            if (response.size > 0) {
//                binding.nameTextview.text = response[0].name
//                binding.scoreTextview.text = response[0].score.toString()
//                binding.timeTextview.text = response[0].time.toDate().toString()
//            } else {
//
//            }
            if (response.size == 0) Log.i("data", "data is empty")
            tableData = response
            // RecycleView 설정
            setRecycleView()
        }
    }

    //    리사이클러 뷰 데이터 설정
    private fun setRecycleView() {
        val adapter = ScoreBoardAdapter(this)
        adapter.data = tableData
        binding.scoreRecycleView.adapter = adapter
        binding.scoreRecycleView.layoutManager = LinearLayoutManager(this)
    }
}