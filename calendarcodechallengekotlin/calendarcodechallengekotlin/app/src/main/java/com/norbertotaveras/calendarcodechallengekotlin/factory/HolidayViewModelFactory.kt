package com.norbertotaveras.calendarcodechallengekotlin.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.norbertotaveras.calendarcodechallengekotlin.repositories.HolidayRepository
import com.norbertotaveras.calendarcodechallengekotlin.viewmodels.HolidaysViewModel

class HolidayViewModelFactory(private val holidays: HolidayRepository,
                              private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HolidaysViewModel(holidays, application) as T
    }
}