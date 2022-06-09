package com.norbertotaveras.calendarcodechallengekotlin.responses

import android.os.Parcelable
import com.norbertotaveras.calendarcodechallengekotlin.models.Meta
import com.norbertotaveras.calendarcodechallengekotlin.models.Response
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalendarResponse(
    val meta: Meta,
    val response: Response
): Parcelable
