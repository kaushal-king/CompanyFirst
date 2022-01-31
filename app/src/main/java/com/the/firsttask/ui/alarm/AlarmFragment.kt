package com.the.firsttask.ui.alarm

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.the.firsttask.databinding.FragmentAlarmBinding
import com.the.firsttask.utils.ConstantHelper
import java.text.SimpleDateFormat
import java.util.*


class AlarmFragment : Fragment() {
    private var _binding: FragmentAlarmBinding? = null

    private val binding get() = _binding!!
    var timePickerDialog: TimePickerDialog? = null
    var datePickerDialog: DatePickerDialog? = null
    var calendar = Calendar.getInstance()
    var alarmManager: AlarmManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAlarmBinding.inflate(inflater, container, false)
        val root: View = binding.root
        alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager

        binding.edAlarmTime.setOnFocusChangeListener { _, isTrue ->
            if(isTrue) {
                binding.edAlarmTime.setText("")
                openTimePickerDialog(false)
            }
        }
        binding.edAlarmDate.setOnFocusChangeListener { _, isTrue ->
            if(isTrue) {
                binding.edAlarmDate.setText("")
                openDatePickerDialog()
            }
        }
        binding.btnSaveAlarm.setOnClickListener {

            val intent = Intent(requireContext(), AlarmReceiver::class.java)
            Log.e("TAG", binding.edDescription.text.toString())
            intent.putExtra(ConstantHelper.ALARM_DESCRIPTION, binding.edDescription.text.toString())
            val pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager!!.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }


        return root
    }

    private fun openDatePickerDialog() {
        datePickerDialog = DatePickerDialog(
            requireContext(),
            onDateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog?.setTitle("Selct Date")
        datePickerDialog?.show()
    }

    var onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, day ->
        val date = String.format("%02d-%02d-%04d", day, month + 1, year)
        val formateDate = SimpleDateFormat("dd-MMM-yyyy")
        binding.edAlarmDate.setText(formateDate.format(SimpleDateFormat("dd-MM-yyyy").parse(date)))
        calendar.set(year, month, day)

    }

    private fun openTimePickerDialog(is24r: Boolean) {

        timePickerDialog = TimePickerDialog(
            requireContext(),
            onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE), is24r
        )
        timePickerDialog?.setTitle("Set Alarm Time")
        timePickerDialog?.show()

    }

    var onTimeSetListener =
        OnTimeSetListener { view, hourOfDay, minute ->
            binding.edAlarmTime.setText("$hourOfDay:$minute")
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            timePickerDialog?.show()
        }


}