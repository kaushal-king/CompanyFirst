package com.the.firsttask

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var radioGroup: RadioGroup
    lateinit var editText: EditText
    lateinit var answer: TextView

    var amountType: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGroup = findViewById(R.id.radiogroup)
        editText = findViewById(R.id.ed_amount)
        answer = findViewById(R.id.txt_ans)
        radioGroup.setOnCheckedChangeListener { group, checkedid ->
            calculateAmount(amountType)
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                amountType = p0.toString()
                calculateAmount(amountType)

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }

        )

    }

    fun calculateAmount(inr: String): Double {
        if (inr.isEmpty()) {
            answer.text = getString(R.string.currency_ans_title) + "0.0"


        } else {
            val enterNumber: Double = inr.toDouble()
            var result: Double
            var id: Int = radioGroup.checkedRadioButtonId

            if (id == R.id.rd_Usd) {

                result = enterNumber * 0.013
                result = "%.5f".format(result).toDouble()
                answer.text = getString(R.string.currency_ans_title) + result.toString()
                return result

            } else if (id == R.id.rd_Pound) {

                result = enterNumber * 0.0098
                result = "%.5f".format(result).toDouble()
                answer.text = getString(R.string.currency_ans_title) + result.toString()
                return result

            } else if (id == R.id.rd_Euro) {

                result = enterNumber * 0.012
                result = "%.5f".format(result).toDouble()
                answer.text = getString(R.string.currency_ans_title) + result.toString()
                return result

            }

        }
        return 0.0
    }
}