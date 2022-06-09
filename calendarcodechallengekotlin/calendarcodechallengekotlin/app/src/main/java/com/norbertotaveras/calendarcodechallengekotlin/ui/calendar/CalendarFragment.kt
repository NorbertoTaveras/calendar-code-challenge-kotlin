package com.norbertotaveras.calendarcodechallengekotlin.ui.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.norbertotaveras.calendarcodechallengekotlin.R
import com.norbertotaveras.calendarcodechallengekotlin.databinding.FragmentCalendarBinding
import com.norbertotaveras.calendarcodechallengekotlin.factory.HolidayViewModelFactory
import com.norbertotaveras.calendarcodechallengekotlin.models.Holiday
import com.norbertotaveras.calendarcodechallengekotlin.repositories.HolidayRepository
import com.norbertotaveras.calendarcodechallengekotlin.utils.ConnectionManager
import com.norbertotaveras.calendarcodechallengekotlin.utils.CurrentDayDecorator
import com.norbertotaveras.calendarcodechallengekotlin.utils.Resource
import com.norbertotaveras.calendarcodechallengekotlin.utils.UI
import com.norbertotaveras.calendarcodechallengekotlin.viewmodels.HolidaysViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import org.threeten.bp.LocalDate.*
import org.threeten.bp.Month
import org.threeten.bp.format.DateTimeFormatter

class CalendarFragment : Fragment(), OnDateSelectedListener {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HolidaysViewModel

    private var localHolidays: MutableList<Holiday> = mutableListOf()
    private var nationalHolidays: MutableList<Holiday> = mutableListOf()
    private lateinit var decorator: CurrentDayDecorator
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        initViewModel()
        initialSetup()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        when (ConnectionManager.isNetworkAvailable(requireContext())) {
            true -> { calendarResponseObserver() }
            false -> { networkSnackBarHandler() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViewModel() {
        val repo = HolidayRepository(requireActivity().application)
        val factory = HolidayViewModelFactory(repo, requireActivity().application)
        activity.run {
            viewModel = ViewModelProvider(requireActivity(), factory).get(HolidaysViewModel::class.java)
        }
    }

    private fun initialSetup() {
        binding.apply {
            val instance = now()
            val min = of(instance.year, Month.JANUARY, 1)
            val max = of(instance.year, Month.DECEMBER, 31)
            calendarView.state().edit()
                .setMinimumDate(min)
                .setMaximumDate(max)
            calendarView.selectedDate = CalendarDay.from(instance)
            decorator = CurrentDayDecorator(requireActivity(),
                                            CalendarDay.from(instance),
                                            R.drawable.default_circular_inset)
            calendarView.addDecorator(decorator)
            dateTextView.text = getString(R.string.no_selection)
            calendarView.setOnDateChangedListener(this@CalendarFragment)
        }
    }

    private fun setCalendarDecorators(holidays: MutableList<Holiday>,
                                      view: MaterialCalendarView,
                                      @DrawableRes background: Int) {
        for (holiday in holidays) {
            val dateTime = holiday.date.datetime
            val year = dateTime.year
            val month = dateTime.month
            val day = dateTime.day
            val date = CalendarDay.from(year, month, day)
            view.addDecorator(CurrentDayDecorator(requireActivity(), date, background))
        }
    }

    private fun calendarResponseObserver() {
        viewModel.calendarResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { calendarResponse ->
                        binding.progressBar.visibility = View.INVISIBLE
                        for (holiday in calendarResponse.response.holidays) {
                            for (type in holiday.type) {
                                when {
                                    type.contains("local", ignoreCase = true) -> {
                                        localHolidays.add(holiday)
                                    }
                                    type.contains("national", ignoreCase = true) -> {
                                        nationalHolidays.add(holiday)
                                    }
                                }
                            }
                        }
                        binding.apply {
                            if (localHolidays.size > 0 && nationalHolidays.size > 0) {
                                setCalendarDecorators(localHolidays, calendarView,
                                    R.drawable.local_circular_inset
                                )
                                setCalendarDecorators(nationalHolidays, calendarView,
                                    R.drawable.national_circular_inset
                                )
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    response.msg.let { message ->
                        binding.progressBar.visibility = View.INVISIBLE
                        Log.e(this@CalendarFragment.toString(), "An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d(this@CalendarFragment.toString(), "Loading data from response")
                }
            }
        })
    }

    private fun networkSnackBarHandler() {
        view?.let {
            UI.showNetworkSnackBar(
                requireContext(),
                it,
                resources.getString(R.string.network_connection),
                viewModel,
            )
        }
    }

    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean) {
        binding.apply {
            calendarView.removeDecorator(decorator)
            decorator = CurrentDayDecorator(requireActivity(),
                date,
                R.drawable.default_circular_inset)
            calendarView.addDecorator(decorator)
            calendarView.invalidateDecorators()
            dateTextView.text =
                if (selected) formatter.format(date.date) else getString(R.string.no_selection)
        }
    }
}