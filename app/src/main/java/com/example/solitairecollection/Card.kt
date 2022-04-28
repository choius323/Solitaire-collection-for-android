package com.example.solitairecollection

data class Card( var number: Int,  var shape: Int, var name:String="NONE") {
    companion object CardCompanion{
        const val HEARTS = 0
        const val SPADES = 1
        const val DIAMONDS = 2
        const val CLUBS = 3
    }

    private val shapeList = arrayOf("spades", "hearts", "diamonds", "clubs")
    private var shapeName = shapeList[shape]

    constructor(number:Int, shape:Int) : this(number, shape, "NONE") {
        this.name = "$number ${shapeList[shape]}"
    }

    fun setCard(number:Int, shape:Int){
        this.number = number
        this.shape = shape
        this.name = "$number ${shapeList[shape]}"
        this.shapeName = shapeList[shape]
    }

    fun getShapeName() = shapeName
}