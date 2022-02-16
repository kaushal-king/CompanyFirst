package com.the.firsttask.ui.converter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConverterViewModel : ViewModel() {
    val amountType: MutableLiveData<String> = MutableLiveData()
    var usd: MutableLiveData<String> = MutableLiveData()
    var euro: MutableLiveData<String> = MutableLiveData()
    var pound: MutableLiveData<String> = MutableLiveData()

    init {
        amountType.value = ""
        usd.value = "0"
        euro.value = "0"
        pound.value = "0"
    }

    fun setUsd(Usd: String) {
        usd.value = Usd
    }

    fun setPound(Pound: String) {
        pound.value = Pound
    }

    fun setEuro(Euro: String) {
        euro.value = Euro
    }


    fun calculateAmount(amount: CharSequence) {
        var inr = amount.toString()
        Log.e("TAG", "calculateAmount: $inr")
        if (inr.isEmpty()) {


            setUsd("0")
            setPound("0")
            setEuro("0")


        } else {
            val enternumber: Double = inr.toDouble()
            var result: Double


            //USD
            result = enternumber * 0.0133535335

            setUsd("%.2f".format(result))


            //POUND
            result = enternumber * 0.0098
            setEuro("%.2f".format(result))


            //EURO
            result = enternumber * 0.012
            setPound("%.2f".format(result))


        }
    }

}