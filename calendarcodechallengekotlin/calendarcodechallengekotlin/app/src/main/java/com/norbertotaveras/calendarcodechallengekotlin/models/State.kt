package com.norbertotaveras.calendarcodechallengekotlin.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class State(
    val abbrev: String,
    val id: Int,
    val iso: String,
    val name: String
): Parcelable
