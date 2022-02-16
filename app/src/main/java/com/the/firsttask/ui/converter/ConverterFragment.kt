package com.the.firsttask.ui.converter


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.the.firsttask.R
import com.the.firsttask.databinding.FragmentConverterBinding
import com.the.firsttask.utils.MyFirebaseAnalytics


class ConverterFragment : Fragment() {

    lateinit var binding: FragmentConverterBinding
    var viewModel=ConverterViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_converter, container, false)
        val view: View = binding.root
        binding.apply {
            data=viewModel
            lifecycleOwner=requireActivity()
        }

        return view
    }


    override fun onResume() {
        super.onResume()
        MyFirebaseAnalytics.addScreenView("ConverterScreen", "MainActivity")
    }


}