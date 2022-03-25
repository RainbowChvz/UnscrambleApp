package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    init {
        Log.d("GameFragment", "GameViewModel created")
    }

    private var _score = 0
    private var _currentWordCount = 0
    private var _currentScrambledWord = "test"

    val score: Int get() = _score
    val currentWordCount: Int get() = _currentWordCount
    val currentScrambledWord: String get() = _currentScrambledWord

    override fun onCleared() {
        super.onCleared()

        Log.d("GameFragment", "GameViewModel destroyed")
    }

}