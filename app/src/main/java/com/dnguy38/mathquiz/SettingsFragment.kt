package com.dnguy38.mathquiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Switch

private const val TAG = "com.dnguy38.mathquiz.SettingsFragment"

class SettingsFragment : Fragment() {
    private lateinit var soundSwitch: Switch
    private lateinit var timeLimitRadioGroup: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val settings = (activity as MainActivity).settings

        val timeLimit1 = resources.getInteger(R.integer.time_limit_1)
        val timeLimit2 = resources.getInteger(R.integer.time_limit_2)
        val timeLimit3 = resources.getInteger(R.integer.time_limit_3)
        val timeLimit4 = resources.getInteger(R.integer.time_limit_4)
        val timeLimit5 = resources.getInteger(R.integer.time_limit_5)

        soundSwitch = view.findViewById(R.id.sound_switch)
        soundSwitch.isChecked = settings.soundOn
        soundSwitch.setOnCheckedChangeListener { _, isChecked -> settings.soundOn = isChecked }

        timeLimitRadioGroup = view.findViewById(R.id.time_limit_radioGroup)
        timeLimitRadioGroup.check(when (settings.timeLimitSeconds) {
            timeLimit1 -> R.id.time_limit_1_radioButton
            timeLimit2 -> R.id.time_limit_2_radioButton
            timeLimit3 -> R.id.time_limit_3_radioButton
            timeLimit4 -> R.id.time_limit_4_radioButton
            timeLimit5 -> R.id.time_limit_5_radioButton
            else -> Log.e(TAG, "Current time limit stored in settings is not a valid")
        })
        timeLimitRadioGroup.setOnCheckedChangeListener { _, id ->
            settings.timeLimitSeconds = when (id) {
                R.id.time_limit_1_radioButton -> timeLimit1
                R.id.time_limit_2_radioButton -> timeLimit2
                R.id.time_limit_3_radioButton -> timeLimit3
                R.id.time_limit_4_radioButton -> timeLimit4
                R.id.time_limit_5_radioButton -> timeLimit5
                else -> Log.e(TAG, "An unrecognized radio button was checked")
            }
        }

        return view
    }
}