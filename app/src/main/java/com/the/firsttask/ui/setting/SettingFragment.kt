package com.the.firsttask.ui.setting

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.the.firsttask.utils.ConstantHelper
import com.the.firsttask.R
import com.the.firsttask.databinding.FragmentSettingBinding
import com.the.firsttask.sharedpreference.SettingsSharedPreference
import com.the.firsttask.utils.LanguageUtils
import com.the.firsttask.utils.ThemeUtils


class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_setting,container,false)
       // _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        loadTheme()
        loadLanguage()

        binding.tvFirebaseId.text=SettingsSharedPreference.getInstance(requireContext()).getFirebaseId()
        binding.ivFirebaseId.setOnClickListener{
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", binding.tvFirebaseId.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(),"copy successfully",Toast.LENGTH_LONG).show()
        }
        binding.llDarkTheme.setOnClickListener {
            SettingsSharedPreference.getInstance(requireContext())
                .setTheme(ConstantHelper.THEME_NIGHT)
            ThemeUtils.changeToTheme(requireActivity())

        }

        binding.llNormalTheme.setOnClickListener{
            SettingsSharedPreference.getInstance(requireContext()).setTheme(ConstantHelper.THEME_NORMAL)
            ThemeUtils.changeToTheme(requireActivity())

        }

        binding.llChristmasTheme.setOnClickListener{
            SettingsSharedPreference.getInstance(requireContext()).setTheme(ConstantHelper.THEME_CHRISTMAS)
            ThemeUtils.changeToTheme(requireActivity())

        }

        binding.llEnglishLanguage.setOnClickListener{
            SettingsSharedPreference.getInstance(requireContext()).setLanguage(ConstantHelper.LANGUAGE_ENGLISH)
            LanguageUtils.changeLanguage(requireActivity())
        }

        binding.llHindiLanguage.setOnClickListener{
            SettingsSharedPreference.getInstance(requireContext()).setLanguage(ConstantHelper.LANGUAGE_HINDI)
            LanguageUtils.changeLanguage(requireActivity())
        }
        return root

    }



    private fun loadTheme(){
        var currentTheme= SettingsSharedPreference.getInstance(requireContext()).getTheme()

        when(currentTheme){
            ConstantHelper.THEME_NORMAL->normalTheme()
            ConstantHelper.THEME_NIGHT->nighrTheme()
            ConstantHelper.THEME_CHRISTMAS->christmasTheme()
        }

    }

    private fun loadLanguage(){
        val currentTheme= SettingsSharedPreference.getInstance(requireContext()).getLanguage()

        when(currentTheme){
            ConstantHelper.LANGUAGE_HINDI->hindiLanguage()
            ConstantHelper.LANGUAGE_ENGLISH->englishLanguage()
        }

    }

    private fun hindiLanguage() {
        binding.llHindiLanguage.setBackgroundColor(resources.getColor(R.color.selectedColor))
        binding.ivHindiLanguage.visibility=View.VISIBLE
        binding.ivEnglishLanguage.visibility=View.GONE
    }

    private fun englishLanguage() {
        binding.llEnglishLanguage.setBackgroundColor(resources.getColor(R.color.selectedColor))
        binding.ivHindiLanguage.visibility=View.GONE
        binding.ivEnglishLanguage.visibility=View.VISIBLE

    }

    private fun christmasTheme() {


        binding.llChristmasTheme.setBackgroundColor(resources.getColor(R.color.selectedColor))
        binding.ivNormalTheme.visibility=View.GONE
        binding.ivChristmasTheme.visibility=View.VISIBLE
        binding.ivDarkTheme.visibility=View.GONE


    }

    private fun nighrTheme() {

        binding.llDarkTheme.setBackgroundColor(resources.getColor(R.color.selectedColor))

        binding.ivNormalTheme.visibility=View.GONE
        binding.ivChristmasTheme.visibility=View.GONE
        binding.ivDarkTheme.visibility=View.VISIBLE

    }


    private fun normalTheme() {
        binding.llNormalTheme.setBackgroundColor(resources.getColor(R.color.selectedColor))

        binding.ivNormalTheme.visibility=View.VISIBLE
        binding.ivChristmasTheme.visibility=View.GONE
        binding.ivDarkTheme.visibility=View.GONE

    }


}