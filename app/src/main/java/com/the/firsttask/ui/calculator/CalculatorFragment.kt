package com.the.firsttask.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.the.firsttask.ConstantHelper
import com.the.firsttask.R
import com.the.firsttask.databinding.FragmentCalculatorBinding


class CalculatorFragment : Fragment() {


    private var _binding: FragmentCalculatorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var first: TextInputEditText
    lateinit var second: TextInputEditText
    lateinit var answer: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        val root: View = binding.root
        first = binding.etFirstnumber
        second = binding.etSecondnumber
        answer = binding.txtAnswer
//        var plus:TextView=binding.plus
//        var minus:TextView=binding.minus
//        var multiply:TextView=binding.multiply
//        var devide:TextView=binding.devide

        binding.btnPlus.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                findanswer(ConstantHelper.CAL_PLUS)
            }
        }
        )

        binding.btnMinus.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                findanswer(ConstantHelper.CAL_MINUS)
            }
        }
        )

        binding.btnDevide.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                findanswer(ConstantHelper.CAL_DEVIDE)
            }
        }
        )



        binding.btnMultiply.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                findanswer(ConstantHelper.CAL_MULTIPLY)
            }
        }
        )

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun findanswer(type: String) {

        var firsts: String = first.text.toString()
        var seconds: String = second.text.toString()
        if (firsts.isEmpty()) {
            first.error = getString(R.string.et_error)
        } else if (seconds.isEmpty()) {
            second.error = getString(R.string.et_error)
        } else {
            var firstentry: Double = firsts.toDouble()
            var secondentry: Double = seconds.toDouble()

            if (type == ConstantHelper.CAL_PLUS) {
                var ans: Double
                ans = firstentry + secondentry
                ans = "%.5f".format(ans).toDouble()
                answer.text = ans.toString()

            } else if (type == ConstantHelper.CAL_MINUS) {
                var ans: Double
                ans = firstentry - secondentry
                ans = "%.5f".format(ans).toDouble()
                answer.text = ans.toString()
            } else if (type == ConstantHelper.CAL_DEVIDE) {
                var ans: Double
                ans = firstentry / secondentry
                ans = "%.5f".format(ans).toDouble()
                answer.text = ans.toString()
            } else if (type == ConstantHelper.CAL_MULTIPLY) {
                var ans: Double
                ans = firstentry * secondentry
                ans = "%.5f".format(ans).toDouble()
                answer.text = ans.toString()
            }


        }


    }
}