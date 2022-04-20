package com.example.solitairecollection

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
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
        for (i in 1..1) {
            val viewId = resources.getIdentifier(
                "cardPlace$i",
                "id",
                this@ScorpionActivity.packageName
            )

//            액티비티 생성 됐을 때는 위치가 제대로 나오지 않을 수 있어서 post를 사용함
//            binding.cardPlace1.post {
//            }
            binding.imageView2.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        mouseX = v.x - event.rawX
                        mouseY = v.y - event.rawY
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val nx = event.rawX + mouseX
                        val ny = event.rawY + mouseY
//                        v.animate()
//                            .x(nx)
//                            .y(ny)
//                            .setDuration(0)
//                            .start()
                        v.moveTo(nx, ny)
                    }
                    MotionEvent.ACTION_UP -> {
                        val view = binding.cardPlace1
                        val pos = IntArray(2)
                        view.getLocationOnScreen(pos)
                        val statusBarHeight = statusBarHeight
                        pos[1] -= statusBarHeight // 상태바 끝나는 지점 : 75
                        // 박스 안에 카드를 놓으면 박스 중앙으로 이동
                        binding.textView3.text = """
                            pos ${pos[0]} ${pos[1]}
                            view size ${view.width} ${view.height}
                            v ${v.x} ${v.y}
                            v size ${v.width} ${v.height}
                            if ${v.x.toInt() + v.width / 2 - pos[0]} ${v.y.toInt() + v.height / 2 - pos[1]}
                            $statusBarHeight
                        """.trimIndent()
                        if (v.x.toInt() + v.width / 2 - pos[0] in 0..view.width &&
                            v.y.toInt() + v.height / 2 - pos[1] in 0..view.height
                        ) {
                            v.moveTo(
                                pos[0].toFloat() + (view.width - v.width)/2,
                                pos[1].toFloat() + (view.height - v.height)/2
                            )
                        }
                    }
                }

                true  // false로 하면 setOnClickListener 실행 가능
                // 추가 설명 -> https://www.masterqna.com/android/2054/ontouch%EC%99%80-onlongclick
            }
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

    fun View.moveTo(x: Float, y: Float) {
        this.animate()
            .x(x)
            .y(y)
            .setDuration(0)
            .start()
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