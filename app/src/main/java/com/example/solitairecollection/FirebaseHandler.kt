package com.example.solitairecollection

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject

class FirebaseHandler {
    private val db = FirebaseFirestore.getInstance()

    fun addScoreData(score: UserScore): Boolean? {
        val data = hashMapOf("score" to score.score, "time" to score.time)
        var isSuccess: Boolean? = null
        db.collection("scores_classic").document(score.name).set(data)
            .addOnSuccessListener { Log.i("addScoreData", "$score Success");isSuccess = true }
            .addOnFailureListener { e ->
                Log.e(
                    "addScoreData",
                    "$e $score Failure"
                )
                isSuccess = false
            }
        return isSuccess
    }

    fun getAllScoreData(): ArrayList<UserScore> {
        val scoreArr: ArrayList<UserScore> = ArrayList()
        val job = db.collection("scores_classic").orderBy("score", Query.Direction.DESCENDING)
            .orderBy("time").limit(100).get()
            .addOnSuccessListener { document ->
                for ((i, doc) in document.withIndex()) {
                    Log.i("doc", "$i $doc")
                    val score = doc.toObject<UserScore>()
                    score.name = doc.id
                    score.rank = i + 1
                    scoreArr.add(score)
                }
            }
            .addOnFailureListener { e ->
                Log.e("getAllScoreData", "$e")
            }
        Tasks.await(job) // job이 끝날 때 까지 대기
        return scoreArr
    }
}

