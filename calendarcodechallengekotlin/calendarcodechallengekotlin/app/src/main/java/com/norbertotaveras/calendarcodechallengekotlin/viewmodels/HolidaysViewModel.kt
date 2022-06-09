package com.norbertotaveras.calendarcodechallengekotlin.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.norbertotaveras.calendarcodechallengekotlin.repositories.HolidayRepository
import com.norbertotaveras.calendarcodechallengekotlin.responses.CalendarResponse
import com.norbertotaveras.calendarcodechallengekotlin.utils.Constants
import com.norbertotaveras.calendarcodechallengekotlin.utils.Constants.Companion.COUNTRY
import com.norbertotaveras.calendarcodechallengekotlin.utils.Constants.Companion.YEAR
import com.norbertotaveras.calendarcodechallengekotlin.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class HolidaysViewModel constructor(
    private val repository: HolidayRepository,
    application: Application
) : AndroidViewModel(application) {

    var calendarResponse: MutableLiveData<Resource<CalendarResponse>> = MutableLiveData()

    init {
        getHolidays()
    }

    fun getHolidays() = viewModelScope.launch {
        getHolidaysSafeCall(COUNTRY, YEAR, null)
    }

    private suspend fun getHolidaysSafeCall(country: String, year: Int, type: String?) {
        calendarResponse.postValue(Resource.Loading())
        val response = repository.getHolidays(country, year, type)
        calendarResponse.postValue(handleCalendarResponse(response))
    }

    private fun handleCalendarResponse(response: Response<CalendarResponse>): Resource<CalendarResponse> {
        if (response.isSuccessful) {
            response.body()?.let {  result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}