package com.norbertotaveras.calendarcodechallengekotlin.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meta(
    val code: Int
): Parcelable
