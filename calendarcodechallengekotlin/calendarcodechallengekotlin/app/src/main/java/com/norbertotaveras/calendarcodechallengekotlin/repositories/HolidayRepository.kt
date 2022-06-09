package com.norbertotaveras.calendarcodechallengekotlin.repositories

import android.app.Application
import com.norbertotaveras.calendarcodechallengekotlin.network.client.RetrofitInstance

class HolidayRepository constructor(application: Application) {
    suspend fun getHolidays(country: String, year: Int, type: String?) =
        RetrofitInstance.api.getHolidays(country, year, type)
}