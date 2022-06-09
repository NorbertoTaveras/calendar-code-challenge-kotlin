package com.norbertotaveras.calendarcodechallengekotlin.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Holiday(
    val country: Country,
    val date: Date,
    val description: String,
    val locations: String,
    val name: String,
    val states: @RawValue Any?,
    val type: MutableList<String>,
    val urlid: String
): Parcelable
