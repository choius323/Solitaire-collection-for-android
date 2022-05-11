package com.example.solitairecollection

import com.google.firebase.Timestamp

data class UserScore(
    var name: String = "",
    val score: Int = 0,
    val time: Timestamp = Timestamp.now(),
    var rank: Int = -1
) {
    override fun toString(): String {
        return "$name $score ${time.toDate()}"
    }
}