package com.example.pazzle.layer

data class GameState(
    val currentScrambledWord: String = "",
    val isGuessedWordWrong : Boolean = false,
    val score: Int = 0,
    val currentWordCount : Int = 1,
    val  isGameOver : Boolean = false
) {

}
