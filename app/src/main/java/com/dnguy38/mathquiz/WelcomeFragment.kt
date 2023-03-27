package com.dnguy38.mathquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

class WelcomeFragment : Fragment() {
    private lateinit var newGameButton: Button
    private lateinit var settingsButton: Button
    private lateinit var statsButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)

        newGameButton = view.findViewById(R.id.new_game_button)
        newGameButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_welcomeFragment_to_configurationFragment)
        }

        settingsButton = view.findViewById(R.id.settings_button)
        settingsButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_welcomeFragment_to_settingsFragment)
        }

        statsButton = view.findViewById(R.id.stats_button)
        statsButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_welcomeFragment_to_statisticsFragment)
        }

        return view
    }
}