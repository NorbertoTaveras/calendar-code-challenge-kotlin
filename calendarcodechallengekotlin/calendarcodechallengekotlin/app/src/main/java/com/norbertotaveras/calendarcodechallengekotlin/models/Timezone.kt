package com.norbertotaveras.calendarcodechallengekotlin.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Timezone(
    val offset: String,
    val zoneabb: String,
    val zonedst: Int,
    val zoneoffset: Int,
    val zonetotaloffset: Int
): Parcelable
