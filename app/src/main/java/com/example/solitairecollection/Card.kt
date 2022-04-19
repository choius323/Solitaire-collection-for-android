package com.example.solitairecollection

data class Card(var number: Int, var shape: Int, var name:String="NONE") {
    companion object {
        const val HEARTS = 0
        const val SPADES = 1
        const val DIAMONDS = 2
        const val CLUBS = 3
    }
    private val shapeList = arrayOf("spades", "hearts", "diamonds", "clubs")
    private var image = R.drawable.clubs_1
    private var shapeName = shapeList[3]

    constructor(number:Int, shape:Int) : this(number, shape, "NONE") {
        this.name = "$number ${shapeList[shape]}"
    }

    fun getImage():Int{
        return image
    }

    fun setCard(number:Int, shape:Int){
        this.number = number
        this.shape = shape
        this.name = "$number ${shapeList[shape]}"
        if (number==1 && shape==3){
            image = R.drawable.c1_clover
        } else{
            image = R.drawable.c8_diamond
        }
        this.shapeName = shapeList[shape]
    }

    fun getShapeName() = shapeName

    fun getImageName() = shapeName + "_" + number
}