package com.example.solitairecollection

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.solitairecollection.databinding.ActivityScorpionBinding

class ScorpionActivity : SuperActivity() {

    private lateinit var binding: ActivityScorpionBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScorpionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var card: Card? = Card(1, Card.CLUBS)
        if (intent.hasExtra("cardInfo")) {
            val array = intent.getIntegerArrayListExtra("cardInfo")
            card = array?.get(0)?.let { Card(it, array[1]) }

            card?.let {
                binding.textView2.text = card.name
                binding.imageView2.setImageResource(it.getImage())
            }
        }

//        binding.imageView2.setOnLongClickListener(LongClickListener())
//        binding.imageView2.setOnClickListener { v ->
//            val tag = "card image"
//            val item = ClipData.Item(v?.tag as? CharSequence)
//            val dragData = ClipData(
//                v?.tag as? CharSequence,
//                arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
//                item
//            )
//
//            v?.startDragAndDrop(dragData, View.DragShadowBuilder(v), null, 0)
//            true
//        }

//        setOnTouchListener와 같이 사용하면 둘 다 실행됨
//        ACTION_DOWN에서 범위를 벗어났을 때 true를 반환 하면 onClick으로 넘어오지 않음
//        binding.imageView2.setOnLongClickListener { v ->
//            card?.let { if (card.number == 1 && card.shape == Card.CLOVER) {
//                card.setCard(8, Card.DIAMOND)
//            } else {
//                card.setCard(1, Card.CLOVER)
//            }
//                binding.textView2.text = card.name
//                binding.imageView2.setImageResource(card.getImage()) }
//
//            false
//        }

        var mouseX = 0f  // 뷰와 클릭 위치의 -x 거리
        var mouseY = 0f
        var areaX = 0f
        var areaY = 0f
        binding.view.post {
            areaX = binding.view.x + binding.view.width / 2
            areaY = binding.view.y + binding.view.height / 2
        }
        binding.imageView2.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mouseX = v.x - event.rawX
                    mouseY = v.y - event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    val nx = event.rawX + mouseX
                    val ny = event.rawY + mouseY
                    v.animate()
                        .x(nx)
                        .y(ny)
                        .setDuration(0)
                        .start()
                }
                MotionEvent.ACTION_UP -> {
                    // 박스 안에 카드를 놓으면 박스 중앙으로 이동
                    val rangeX = binding.view.width/2
                    val rangeY = binding.view.height/2
                    if (v.x + v.width / 2 in areaX - rangeX..areaX + rangeX && v.y + v.height / 2 in areaY - rangeY..areaY + rangeY) {
                        v.animate()
                            .x(areaX - v.width / 2)
                            .y(areaY - v.height / 2)
                            .setDuration(0)
                            .start()
                    }
                }
            }

            true  // false로 하면 setOnClickListener 실행 가능
            // 추가 설명 -> https://www.masterqna.com/android/2054/ontouch%EC%99%80-onlongclick
        }


//        binding.imageView2.setOnDragListener { v, event ->
//            DragListener().onDrag(v, event)
//        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@ScorpionActivity, MainActivity::class.java))
        finish()
    }

//    class LongClickListener() : View.OnLongClickListener {
//        override fun onLongClick(v: View?): Boolean {
//            val tag = "card image"
//            val item = ClipData.Item(v?.tag as? CharSequence)
//            val dragData = ClipData(
//                v?.tag as? CharSequence,
//                arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
//                item
//            )
//
//            v?.startDragAndDrop(dragData, View.DragShadowBuilder(v), null, 0)
//            return true
//        }
//
//    }
//
//    class DragListener() : View.OnDragListener {
//        override fun onDrag(v: View?, event: DragEvent?): Boolean {
//            when (event?.action) {
//                DragEvent.ACTION_DRAG_STARTED -> {
//                    Log.i("Drag", "drag start")
//                }
//                DragEvent.ACTION_DROP -> {
//                    Log.i("Drag", "drag drop")
//                }
//                DragEvent.ACTION_DRAG_ENDED -> {
//                    Log.i("Drag", "drag ended")
//                }
//            }
//
//            return true
//        }
//    }
}