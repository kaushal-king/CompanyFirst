package com.the.firsttask.ui.converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.the.firsttask.R
import com.the.firsttask.databinding.FragmentConverterBinding


class ConverterFragment : Fragment() {


    private var _binding: FragmentConverterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var radioGroup: RadioGroup
    lateinit var editText: EditText
    lateinit var answer: TextView
    var selected: Int = 0
    var amounttype: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentConverterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        radioGroup = binding.radiogroup
        editText = binding.edAmount
        answer = binding.txtAns


        radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedid ->
                val radio: RadioButton = root.findViewById(checkedid)
                Toast.makeText(
                    context, " On checked change :" +
                            " ${radio.text}",
                    Toast.LENGTH_SHORT
                ).show()
                calculateamount(amounttype)

            }
        )

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                amounttype = p0.toString()
                calculateamount(amounttype)

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }

        )


//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
//

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun calculateamount(inr: String): Double {
        if (inr.isEmpty()) {
            answer.text =  getString(R.string.currency_ans_title)+"0.0"
            return 0.0

        } else {
            val enternumber: Double = inr.toDouble()
            var result: Double
            var id: Int = radioGroup.checkedRadioButtonId

            if (id == R.id.rd_Usd) {

                result = enternumber * 0.013
                result = "%.5f".format(result).toDouble()
                answer.text = getString(R.string.currency_ans_title)+ result.toString()
                return result

            } else if (id == R.id.rd_Pound) {

                result = enternumber * 0.0098
                result = "%.5f".format(result).toDouble()
                answer.text = getString(R.string.currency_ans_title)+ result.toString()
                return result

            } else if (id == R.id.rd_Euro) {

                result = enternumber * 0.012
                result = "%.5f".format(result).toDouble()
                answer.text = getString(R.string.currency_ans_title) + result.toString()
                return result

            }

        }
        return 0.0
    }
}