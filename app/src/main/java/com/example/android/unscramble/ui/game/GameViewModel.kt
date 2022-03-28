package com.example.android.unscramble.ui.game

import android.text.Spannable
import android.text.SpannableString
import android.text.style.TtsSpan
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    private val _score = MutableLiveData(0)
    private val _currentWordCount = MutableLiveData(0)
    private val _currentScrambledWord = MutableLiveData<String>()

    val score: LiveData<Int> get() = _score
    val currentWordCount: LiveData<Int> get() = _currentWordCount
//    val currentScrambledWord: LiveData<String> get() = _currentScrambledWord
    val currentScrambledWord: LiveData<Spannable> = Transformations.map(_currentScrambledWord) {
        if (it == null)
            SpannableString("")
        else {
            val scrambledWord = it.toString()
            val spannable: Spannable = SpannableString(scrambledWord)
            spannable.setSpan(
                TtsSpan.VerbatimBuilder(scrambledWord).build(),
                0,
                scrambledWord.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            spannable
        }
    }

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
            _currentScrambledWord.value = String(shuffledWord)
            wordsList.add(currentWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
        }
    }

    fun nextWord(): Boolean {
        return if(currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        }
        else false
    }

    fun increaseScore() {
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }

    fun isUserWordCorrect(word: String): Boolean {
        if(!word.equals(currentWord, true))
            return false
        else {
            increaseScore()
            return true
        }
    }

    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }

}