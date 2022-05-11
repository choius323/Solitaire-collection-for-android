package com.example.solitairecollection

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.solitairecollection.databinding.ScoreBoardItemBinding

class ScoreBoardAdapter(val context: Context) :
    RecyclerView.Adapter<ScoreBoardAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ScoreBoardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
//            길게 클릭하면 해당 정보 출력
            binding.root.setOnLongClickListener {
                Log.i("Long_click", it.id.toString())
                val score = data[adapterPosition] // adapterPosition은 자동 생성
                val builder = AlertDialog.Builder(context)
                    .setTitle(score.name)
                    .setMessage("Rank : ${score.rank}\nScore : ${score.score}\nTime : ${score.time.toDate()}")
                builder.show()
                true
            }
        }

        fun bind(score: UserScore) {
            binding.rankTextview.text = score.rank.toString()
            binding.nameTextview.text = score.name
            binding.scoreTextview.text = score.score.toString()
        }
    }

    //    뷰에 설정할 데이터
    lateinit var data: ArrayList<UserScore>

    //    아이템 개수 반환
    override fun getItemCount() = data.size

    //    뷰 홀더가 없을 때 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ScoreBoardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    //    데이터를 연결할 때 호출. 적절한 데이터를 가져와서 레이아웃에 채움.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}