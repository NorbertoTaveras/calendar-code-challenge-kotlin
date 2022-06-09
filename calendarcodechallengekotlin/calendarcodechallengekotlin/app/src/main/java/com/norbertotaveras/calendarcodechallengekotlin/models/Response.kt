package com.norbertotaveras.calendarcodechallengekotlin.models

import android.os.Parcelable
import com.norbertotaveras.calendarcodechallengekotlin.models.Holiday
import kotlinx.parcelize.Parcelize

@Parcelize
data class Response(
    val holidays: MutableList<Holiday>
): Parcelable
