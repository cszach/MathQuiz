package com.dnguy38.mathquiz

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dnguy38.mathquiz.models.Game
import com.dnguy38.mathquiz.models.Problem
import com.dnguy38.mathquiz.models.Settings
import com.dnguy38.mathquiz.models.Statistics
import com.dnguy38.mathquiz.ui.main.MainViewModel

private const val CORRECT_EMOJI_ALPHA_ANIM_DURATION = 300L
private const val SHAKE_ANIM_DURATION = 100L

class GameFragment : Fragment() {
    private val args: GameFragmentArgs by navArgs()
    private lateinit var game: Game
    private lateinit var viewModel: MainViewModel
    private lateinit var recycler: RecyclerView
    private lateinit var newGameButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var timer: CountDownTimer
    private lateinit var settings: Settings
    private lateinit var stats: Statistics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        recycler = view.findViewById(R.id.game_recyclerView)
        recycler.layoutManager = LinearLayoutManager(context)

        newGameButton = view.findViewById(R.id.game_fragment_new_game_button)
        progressBar = view.findViewById(R.id.progressBar)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as MainActivity

        settings = mainActivity.settings
        stats = mainActivity.stats

        val numProblems = resources.getInteger(R.integer.num_problems)

        game = Game(numProblems)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.generateProblemList(
            numProblems,
            args.configuration.operation,
            args.configuration.operandLimit,
            settings.allowZero,
            settings.allowNegatives)
        viewModel.problemList.observe(viewLifecycleOwner) {
            recycler.adapter = ProblemAdapter(it)
        }

        newGameButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_gameFragment_to_welcomeFragment)
        }

        val settings = (activity as MainActivity).settings
        val timeLimitMillis = settings.timeLimitSeconds * 1000

        timer = object : CountDownTimer(timeLimitMillis.toLong(), 1) {
            override fun onTick(timeRemainingMillis: Long) {
                stats.timeSpentSeconds += 0.001
                progressBar.progress =
                    (timeRemainingMillis / timeLimitMillis.toDouble() * progressBar.max).toInt()
            }

            override fun onFinish() {
                onGameEnd()
            }
        }.start()
    }

    private inner class ProblemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private lateinit var problem: Problem
        private val firstOperandTextView: TextView = view.findViewById(R.id.first_operand_textView)
        private val operationSymbolTextView: TextView = view.findViewById(R.id.operation_symbol_textView)
        private val secondOperandTextView: TextView = view.findViewById(R.id.second_operand_textView)
        private val answerEditText: EditText = view.findViewById(R.id.answer_editText)
        private val correctEmojiTextView: TextView = view.findViewById(R.id.correct_emoji_textView)

        init {
            answerEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    val answerString = answerEditText.text.toString()

                    if (answerString.isNotEmpty() && answerString.toInt() == problem.result) {
                        game.addCorrectAnswer()
                        stats.numCalculations++
                        stats.numCorrectAnswers++

                        answerEditText.isEnabled = false

                        val fadeIn = ValueAnimator.ofFloat(0f, 1f)

                        fadeIn.addUpdateListener {
                            correctEmojiTextView.alpha = it.animatedValue as Float
                        }

                        fadeIn.interpolator = LinearInterpolator()
                        fadeIn.duration = CORRECT_EMOJI_ALPHA_ANIM_DURATION

                        fadeIn.start()

                        if (game.playerHasWon()) {
                            onGameWin()
                        }
                    }
                }
            })

            answerEditText.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    val answerString = answerEditText.text.toString()

                    if (answerString.isNotEmpty() && answerString.toInt() != problem.result) {
                        stats.numCalculations++

                        val shake = AnimatorSet()

                        shake.playSequentially(
                            ObjectAnimator.ofFloat(answerEditText, "rotation", 0f, 5f),
                            ObjectAnimator.ofFloat(answerEditText, "rotation", 5f, -5f),
                            ObjectAnimator.ofFloat(answerEditText, "rotation", -5f, 0f)
                        )

                        shake.duration = SHAKE_ANIM_DURATION
                        shake.start()
                    }
                }
            }
        }

        fun bind(problem: Problem) {
            this.problem = problem

            firstOperandTextView.text = problem.operand1.toString()
            operationSymbolTextView.text = problem.operation.symbol
            secondOperandTextView.text = problem.operand2.toString()
        }
    }

    private inner class ProblemAdapter(private val list: List<Problem>): RecyclerView.Adapter<ProblemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemViewHolder {
            val view = layoutInflater.inflate(R.layout.problem_item, parent, false)

            return ProblemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ProblemViewHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount() = list.size
    }

    private fun onGameWin() {
        timer.cancel()

        // Win animation

        val zoomIn = AnimatorSet()
        zoomIn.playTogether(
            ObjectAnimator.ofFloat(recycler, "scaleX", 1f, 1.1f),
            ObjectAnimator.ofFloat(recycler, "scaleY", 1f, 1.1f)
        )

        val shake = AnimatorSet()
        shake.playSequentially(
            ObjectAnimator.ofFloat(recycler, "rotation", 0f, 5f),
            ObjectAnimator.ofFloat(recycler, "rotation", 5f, -5f),
            ObjectAnimator.ofFloat(recycler, "rotation", -5f, 5f),
            ObjectAnimator.ofFloat(recycler, "rotation", 5f, -5f),
            ObjectAnimator.ofFloat(recycler, "rotation", -5f, 5f),
            ObjectAnimator.ofFloat(recycler, "rotation", 5f, -5f),
            ObjectAnimator.ofFloat(recycler, "rotation", -5f, 0f)
        )

        val zoomOut = AnimatorSet()
        zoomOut.playTogether(
            ObjectAnimator.ofFloat(recycler, "scaleX", 1.1f, 1f),
            ObjectAnimator.ofFloat(recycler, "scaleY", 1.1f, 1f)
        )

        val winAnimator = AnimatorSet()

        winAnimator.playSequentially(zoomIn, shake, zoomOut)
        winAnimator.duration = SHAKE_ANIM_DURATION

        winAnimator.start()
        onGameEnd()
    }

    private fun onGameEnd() {
        val fadeIn = ValueAnimator.ofFloat(0f, 1f)

        fadeIn.addUpdateListener {
            newGameButton.alpha = it.animatedValue as Float
        }

        fadeIn.interpolator = LinearInterpolator()
        fadeIn.duration = CORRECT_EMOJI_ALPHA_ANIM_DURATION

        fadeIn.start()
    }
}