package com.example.solitairecollection

data class CardPlace(val cards:ArrayDeque<Card>, val kind:Int, val view:CardPlaceCustom, val statusBarHeight:Int) {
    val width = view.width
    val height = view.height
    val pos = IntArray(2)
    init {
        view.getLocationOnScreen(pos)
        pos[1] -= statusBarHeight
    }

    fun addCard(card:Card){
        cards.add(card)
        if (kind==1) {
            pos[1] += 30
        }
    }

    fun removeCard(){
        cards.removeLast()
        if (kind == 1) {
            pos[1] -= 30
        }
    }
}