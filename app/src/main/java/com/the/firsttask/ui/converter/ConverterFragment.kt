package com.the.firsttask.ui.converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.the.firsttask.databinding.FragmentConverterBinding
import java.lang.String.format


class ConverterFragment : Fragment() {


    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!

    lateinit var editText: EditText

    var amounttype: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentConverterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        editText = binding.edAmount
        binding.tvUsd.text = "0"
        binding.tvEuro.text = "0"
        binding.tvPound.text = "0"

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
            binding.tvUsd.text = "0"
            binding.tvEuro.text = "0"
            binding.tvPound.text = "0"
        } else {
            val enternumber: Double = inr.toDouble()
            var result: Double


            //USD
            result = enternumber * 0.0133535335
            binding.tvUsd.text = "%.2f".format(result)


            //POUND
            result = enternumber * 0.0098
            binding.tvPound.text = "%.2f".format(result)

            //EURO
            result = enternumber * 0.012
            binding.tvEuro.text = "%.2f".format(result)


        }
    }
}