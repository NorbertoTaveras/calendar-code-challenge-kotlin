package com.norbertotaveras.calendarcodechallengekotlin.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Date(
    val datetime: Datetime,
    val iso: String,
    val timezone: Timezone
): Parcelable
