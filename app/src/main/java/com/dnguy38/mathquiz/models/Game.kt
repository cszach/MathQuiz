package com.dnguy38.mathquiz.models

class Game(private val numProblems: Int) {
    private var numCorrectAnswers: Int = 0

    init {
        reset()
    }

    fun addCorrectAnswer() {
        numCorrectAnswers++
    }

    fun reset() {
        numCorrectAnswers = 0
    }

    fun playerHasWon() = numCorrectAnswers == numProblems
}