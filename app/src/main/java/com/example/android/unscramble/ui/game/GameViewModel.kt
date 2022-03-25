package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    private var _score = 0
    private var _currentWordCount = 0
    private lateinit var _currentScrambledWord: String

    val score: Int get() = _score
    val currentWordCount: Int get() = _currentWordCount
    val currentScrambledWord: String get() = _currentScrambledWord

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    init {
        Log.d("GameFragment", "GameViewModel created")
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()

        Log.d("GameFragment", "GameViewModel destroyed")
    }

    private fun getNextWord() {
        currentWord = allWordsList.random()

        val shuffledWord = currentWord.toCharArray()
        shuffledWord.shuffle()

        while(String(shuffledWord).equals(currentWord, false)) {
            shuffledWord.shuffle()
        }

        if (wordsList.contains(currentWord))
            getNextWord()
        else {
            _currentScrambledWord = String(shuffledWord)
            wordsList.add(currentWord)
            _currentWordCount++
        }
    }

}