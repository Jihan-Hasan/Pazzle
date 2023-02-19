package com.example.pazzle.layer


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pazzle.data.MAX_NUMBER_OF_WORDS
import com.example.pazzle.data.SCORE_INCREASE
import com.example.pazzle.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    // Set of words used in the game
    private var usedWords: MutableSet<String> = mutableSetOf()
     lateinit var currentWord: String
    var userGuess by mutableStateOf("")
        private set


    private fun pickRandomWordAndShuffle(): String {
        currentWord = allWords.random()
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }

    private fun shuffleCurrentWord(currentWord: String): String {
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()
        while (String(tempWord).equals(currentWord)) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }


    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    init {
        usedWords.clear()
        _uiState.value = GameState(currentScrambledWord = pickRandomWordAndShuffle())
    }


    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

    fun checkUserGuess() {
        if (userGuess.contains(currentWord, ignoreCase = true)) {

            val updateScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updateScore)
        } else {
            _uiState.update {
                it.copy(isGuessedWordWrong = true)
            }
        }
        updateUserGuess("")
    }

    fun skiptheWord() {
        updateGameState(_uiState.value.score)
        updateUserGuess("")
    }

    fun updateGameState(updatedScore: Int) {

        if (usedWords.size == MAX_NUMBER_OF_WORDS) {
            _uiState.update {
                it.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    currentWordCount = _uiState.value.currentWordCount.inc(),
                    isGameOver = true
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    score = updatedScore,
                    currentWordCount = _uiState.value.currentWordCount.inc()
                )
            }
        }
    }


}