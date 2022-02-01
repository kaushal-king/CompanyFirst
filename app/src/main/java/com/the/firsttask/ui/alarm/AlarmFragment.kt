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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.the.firsttask.adapter.AlarmAdapter
import com.the.firsttask.database.AlarmEntity
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
    lateinit var viewModel: AlarmViewModel

    private var adapter: AlarmAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAlarmBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel = ViewModelProvider(requireActivity()).get(AlarmViewModel::class.java)



        alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager

        binding.edAlarmTime.setOnFocusChangeListener { _, isTrue ->
            if (isTrue) {
                binding.edAlarmTime.setText("")
                openTimePickerDialog(false)
            }

        }
        binding.edAlarmTime.setOnClickListener {
            if (binding.edAlarmTime.hasFocus()) {
                binding.edAlarmTime.setText("")
                openTimePickerDialog(false)
            }
        }


        binding.edAlarmDate.setOnFocusChangeListener { _, isTrue ->
            if (isTrue) {
                binding.edAlarmDate.setText("")
                openDatePickerDialog()
            }
        }
        binding.edAlarmDate.setOnClickListener {
            if (binding.edAlarmDate.hasFocus()) {
                binding.edAlarmDate.setText("")
                openDatePickerDialog()
            }
        }

        binding.btnSaveAlarm.setOnClickListener {
            Log.e("notification", " " + calendar.timeInMillis)
            val _id = System.currentTimeMillis().toInt()
            val intent = Intent(requireContext(), AlarmReceiver::class.java)
            Log.e("TAG", binding.edDescription.text.toString())
            intent.putExtra(ConstantHelper.ALARM_DESCRIPTION, binding.edDescription.text.toString())
            intent.putExtra(ConstantHelper.ALARM_ID, _id)
            val pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                _id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager!!.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            viewModel.insertAlarm(
                AlarmEntity(
                    _id,
                    false,
                    binding.edAlarmDate.text.toString(),
                    binding.edAlarmTime.text.toString(),
                    binding.edDescription.text.toString()
                )
            )
        }

        loadAlarm()
        return root
    }

    private fun loadAlarm() {

        // binding.cvProgressGrid.visibility = View.VISIBLE
        viewModel.getAllAlarm()
        viewModel.getAllAlarmObservers().observe(viewLifecycleOwner) { alarm ->
            setAlarmView(alarm)
        }
    }

    private fun setAlarmView(alarmList: List<AlarmEntity>?) {
        if (alarmList!!.isNotEmpty()) {
            adapter = AlarmAdapter(alarmList, requireContext())
            binding.rvAlarm.adapter = adapter
            binding.rvAlarm.adapter?.notifyDataSetChanged()
            //binding.cvProgressGrid.visibility = View.GONE
        } else {
            Toast.makeText(requireContext(), "No data Found", Toast.LENGTH_LONG).show()
            // binding.cvProgressGrid.visibility = View.GONE
        }
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