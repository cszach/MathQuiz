package com.dnguy38.mathquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dnguy38.mathquiz.models.Configuration
import com.dnguy38.mathquiz.models.Operation
import com.dnguy38.mathquiz.ui.main.MainViewModel

class ConfigurationFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_configuration, container, false)

        recycler = view.findViewById(R.id.configuration_recyclerView)
        recycler.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.configurationList.observe(viewLifecycleOwner) {
            recycler.adapter = ConfigurationAdapter(it)
        }
    }

    private inner class ConfigurationViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var configuration: Configuration
        private val configurationNameTextView: TextView = itemView.findViewById(R.id.configuration_name_textView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val actionEval =
                ConfigurationFragmentDirections.actionConfigurationFragmentToGameFragment(configuration)
            Navigation.findNavController(itemView).navigate(actionEval)
        }

        fun bind(configuration: Configuration) {
            this.configuration = configuration
            val operationText = when (configuration.operation) {
                Operation.Addition -> resources.getString(R.string.text_addition)
                Operation.Subtraction -> resources.getString(R.string.text_subtraction)
                Operation.Multiplication -> resources.getString(R.string.text_multiplication)
            }
            configurationNameTextView.text = resources.getString(R.string.configuration_name_text, operationText, configuration.operandLimit)
        }
    }

    private inner class ConfigurationAdapter(private val list: Array<Configuration>): RecyclerView.Adapter<ConfigurationViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfigurationViewHolder {
            val view = layoutInflater.inflate(R.layout.configuration_item, parent, false)

            return ConfigurationViewHolder(view)
        }

        override fun onBindViewHolder(holder: ConfigurationViewHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount() = list.size
    }
}