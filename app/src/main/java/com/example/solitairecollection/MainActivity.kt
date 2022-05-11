package com.example.solitairecollection

import android.content.Intent
import android.os.Bundle
import com.example.solitairecollection.databinding.ActivityMainBinding

class MainActivity : SuperActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val card = Card(1, Card.CLUBS)
//        binding.card = card
//        binding.button.setOnClickListener {
//            Toast.makeText(this, "onClick", Toast.LENGTH_SHORT).show()
//            Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//            if (card.number == 1 && card.shape == Card.CLUBS) {
//                card.setCard(8, Card.DIAMONDS)
//            } else {
//                card.setCard(1, Card.CLUBS)
//            }
//            binding.button.text = card.name
////            mainBinding.imageView.setImageResource(card.getImage())
//            binding.imageView.setImageResource(
//                resources.getIdentifier(
//                    card.getImageName(),
//                    "drawable",
//                    this@MainActivity.packageName
//                )
//            )
//        }

        binding.scorpionButton.setOnClickListener {
            val intent = Intent(this@MainActivity, ScorpionActivity::class.java)
//            intent.putIntegerArrayListExtra("cardInfo", arrayListOf(card.number, card.shape))
            startActivity(intent)
            finish()
        }

        binding.scoreBoard.setOnClickListener {
            val intent = Intent(this, ScoreBoardActivity::class.java)
            startActivity(intent)
        }
    }
}


/*
<a href="https://www.flaticon.com/free-icons/poker-cards" title="poker cards icons">Poker cards icons created by rizal2109 - Flaticon</a>
 */