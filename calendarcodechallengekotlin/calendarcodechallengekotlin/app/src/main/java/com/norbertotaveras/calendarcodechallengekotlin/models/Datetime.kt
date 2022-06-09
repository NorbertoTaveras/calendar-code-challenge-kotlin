package com.norbertotaveras.calendarcodechallengekotlin.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Datetime(
    val day: Int,
    val hour: Int,
    val minute: Int,
    val month: Int,
    val second: Int,
    val year: Int
): Parcelable
