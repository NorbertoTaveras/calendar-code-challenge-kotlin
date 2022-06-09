package com.norbertotaveras.calendarcodechallengekotlin.network.service

import com.norbertotaveras.calendarcodechallengekotlin.responses.CalendarResponse
import com.norbertotaveras.calendarcodechallengekotlin.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("/api/v2/holidays")
    suspend fun getHolidays(
        @Query("country")
        country: String,
        @Query("year")
        year: Int,
        @Query("type")
        type: String?,
        @Query("api_key")
        apiKey: String = API_KEY
    ) : Response<CalendarResponse>
}