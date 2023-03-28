package com.dnguy38.mathquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dnguy38.mathquiz.models.Statistics
import org.w3c.dom.Text

class StatisticsFragment : Fragment() {
    private lateinit var numAttemptsTextView: TextView
    private lateinit var numCorrectAnswersTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var stats: Statistics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)

        numAttemptsTextView = view.findViewById(R.id.num_attempts_textView)
        numCorrectAnswersTextView = view.findViewById(R.id.num_correct_answers_textView)
        timeTextView = view.findViewById(R.id.time_textView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stats = (activity as MainActivity).stats

        numAttemptsTextView.text = stats.numCalculations.toString()
        numCorrectAnswersTextView.text = stats.numCorrectAnswers.toString()

        val secondsSpent = stats.timeSpentSeconds.toInt()
        val h: Int = secondsSpent / 3600
        val m: Int = (secondsSpent % 3600) / 60
        val s: Int = secondsSpent % 60

        var timeSpent = s.toString() + "s"

        if (m > 0) {
            timeSpent = m.toString() + "m " + timeSpent
        }

        if (h > 0) {
            timeSpent = h.toString() + "h " + timeSpent
        }

        timeTextView.text = timeSpent
    }
}