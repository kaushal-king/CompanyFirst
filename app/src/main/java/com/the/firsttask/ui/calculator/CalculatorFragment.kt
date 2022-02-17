package com.the.firsttask.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.the.firsttask.R
import com.the.firsttask.databinding.FragmentCalculatorBinding
import com.the.firsttask.utils.MyFirebaseAnalytics


class CalculatorFragment : Fragment() {


    private var _binding: FragmentCalculatorBinding? = null


    private val binding get() = _binding!!
    private var flag: Boolean = false
    var flagOperation: Boolean = false
    var operation: String? = null
    var ans: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator, container, false)
        //_binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btDot.setOnClickListener {
            setText(".")
        }
        binding.btOne.setOnClickListener {
            setText("1")
        }
        binding.btTwo.setOnClickListener {
            setText("2")
        }
        binding.btThree.setOnClickListener {
            setText("3")
        }
        binding.btFour.setOnClickListener {
            setText("4")
        }
        binding.btFive.setOnClickListener {
            setText("5")
        }
        binding.btSix.setOnClickListener {
            setText("6")
        }
        binding.btSeven.setOnClickListener {
            setText("7")
        }
        binding.btEight.setOnClickListener {
            setText("8")
        }
        binding.btNine.setOnClickListener {
            setText("9")
        }
        binding.btZero.setOnClickListener {
            setText("0")
        }
        binding.btCut.setOnClickListener {
            setCut()
        }
        binding.btEqual.setOnClickListener {
            getresult()
        }
        binding.btPlus.setOnClickListener {
            operationSelect("+")
        }
        binding.btMinus.setOnClickListener {
            operationSelect("-")
        }
        binding.btMul.setOnClickListener {
            operationSelect("*")
        }
        binding.btDiv.setOnClickListener {
            operationSelect("/")
        }
        binding.btMod.setOnClickListener {
            operationSelect("%")
        }
        binding.btClear.setOnClickListener {
            flag = false
            binding.tvValue1.text = ""
            binding.tvValue2.text = ""
            operation = ""
            binding.tvAns.text = ""
            flagOperation = false
            binding.tvSign.text = ""
        }


        return root
    }

    private fun getresult() {
        var value1: Double = 0.0
        var value2: Double = 0.0
        if (binding.tvValue1.text.toString().isNotEmpty()) {
            value1 = binding.tvValue1.text.toString().toDouble()
        }
        if (binding.tvValue2.text.toString().isNotEmpty()) {
            value2 = binding.tvValue2.text.toString().toDouble()
        }
        if (operation!= null) {
            when (operation) {
                "+" -> {
                    ans = value1 + value2
                    binding.tvValue2.textSize = 20f
                    binding.tvSign.textSize = 20f
                    binding.tvAns.textSize = 40f
                    binding.tvAns.text = ans.toString()
                    flagOperation = true
                }
                "-" -> {
                    ans = value1 - value2
                    binding.tvValue2.textSize = 20f
                    binding.tvSign.textSize = 20f
                    binding.tvAns.textSize = 40f
                    binding.tvAns.text = ans.toString()
                    flagOperation = true
                }
                "*" -> {
                    ans = value1 * value2
                    binding.tvValue2.textSize = 20f
                    binding.tvSign.textSize = 20f
                    binding.tvAns.textSize = 40f
                    binding.tvAns.text = ans.toString()
                    flagOperation = true
                }
                "/" -> {
                    ans = value1 / value2
                    binding.tvValue2.textSize = 20f
                    binding.tvSign.textSize = 20f
                    binding.tvAns.textSize = 40f
                    binding.tvAns.text = ans.toString()
                    flagOperation = true
                }
                "%" -> {
                    ans = value1 % value2
                    binding.tvValue2.textSize = 20f
                    binding.tvSign.textSize = 20f
                    binding.tvAns.textSize = 40f
                    binding.tvAns.text = ans.toString()
                    flagOperation = true
                }
            }
        }
    }

    private fun operationSelect(operationType: String) {
        operation = operationType
        if (flagOperation) {
            binding.tvValue1.text = ans.toString()
            binding.tvValue2.text = ""
            binding.tvAns.text = ""
            binding.tvValue1.textSize = 40f
            flag = true
            binding.tvSign.text = operationType
        } else {
            binding.tvSign.text = operationType
            if(binding.tvValue2.text.isNotEmpty()){
                getresult()
            }

            flag = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setText(number: String) {
        var value: String
        if (!flag) {
            value = binding.tvValue1.text.toString()
            value = value + number
            binding.tvValue1.textSize = 40F
            binding.tvValue1.text = value
        } else {
            value = binding.tvValue2.text.toString()
            value = value + number
            binding.tvValue1.textSize = 20f
            binding.tvValue2.textSize = 40f
            binding.tvSign.textSize = 40f
            binding.tvValue2.text = value
        }

    }

    fun setCut() {
        var value: String
        if (!flag) {
            value = binding.tvValue1.text.toString()
            if (value != null && value.length > 0) {
                value = value.substring(0, value.length - 1)
            }
            binding.tvValue1.textSize = 40f
            binding.tvValue1.text = value
        } else {
            value = binding.tvValue2.text.toString()
            if (value != null && value.length > 0) {
                value = value.substring(0, value.length - 1)
            }
            binding.tvValue1.textSize = 20f
            binding.tvValue2.textSize = 40f
            binding.tvSign.textSize = 40f
            binding.tvValue2.text = value
        }
    }


    override fun onResume() {
        super.onResume()
        MyFirebaseAnalytics.addScreenView("CalculatorScreen","MainActivity")

    }
}