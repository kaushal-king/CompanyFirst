package com.the.firsttask.ui.converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.the.firsttask.R
import com.the.firsttask.databinding.FragmentConverterBinding


class ConverterFragment : Fragment() {


    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!

    lateinit var editText: EditText
    lateinit var answer: TextView
    var amounttype: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentConverterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        editText = binding.edAmount
        answer = binding.txtAllAns

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                amounttype = p0.toString()
                calculateamount(amounttype)

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun calculateamount(inr: String) {
        if (inr.isEmpty()) {
            answer.text = getString(R.string.txt_all_answer)
        } else {
            val enternumber: Double = inr.toDouble()
            var result: Double
            var txt: String

            //USD
            result = enternumber * 0.013
            result = "%.5f".format(result).toDouble()
            txt = "USD : $result"

            //POUND
            result = enternumber * 0.0098
            result = "%.5f".format(result).toDouble()
            txt = "$txt\nPOUND : $result"

            //EURO
            result = enternumber * 0.012
            result = "%.5f".format(result).toDouble()
            txt = "$txt\nEURO : $result"

            answer.text = txt
        }
    }
}