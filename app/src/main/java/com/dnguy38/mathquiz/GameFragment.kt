package com.dnguy38.mathquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dnguy38.mathquiz.models.Problem
import com.dnguy38.mathquiz.ui.main.MainViewModel

class GameFragment : Fragment() {
    private val args: GameFragmentArgs by navArgs()
    private lateinit var viewModel: MainViewModel
    private lateinit var recycler: RecyclerView
    private lateinit var newGameButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        recycler = view.findViewById(R.id.game_recyclerView)
        recycler.layoutManager = LinearLayoutManager(context)

        newGameButton = view.findViewById(R.id.game_fragment_new_game_button)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.generateProblemList(args.configuration.operation, args.configuration.operandLimit)
        viewModel.problemList.observe(viewLifecycleOwner) {
            recycler.adapter = ProblemAdapter(it)
        }
    }

    private inner class ProblemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private lateinit var problem: Problem
        private val firstOperandTextView: TextView = view.findViewById(R.id.first_operand_textView)
        private val operationSymbolTextView: TextView = view.findViewById(R.id.operation_symbol_textView)
        private val secondOperandTextView: TextView = view.findViewById(R.id.second_operand_textView)
        private val resultTextView: TextView = view.findViewById(R.id.result_textView)

        init {
            // TODO: implement event listeners for the game
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
}