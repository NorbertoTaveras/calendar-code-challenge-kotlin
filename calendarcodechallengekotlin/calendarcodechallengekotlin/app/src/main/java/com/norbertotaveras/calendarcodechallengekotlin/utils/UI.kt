package com.norbertotaveras.calendarcodechallengekotlin.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.snackbar.Snackbar
import com.norbertotaveras.calendarcodechallengekotlin.R
import com.norbertotaveras.calendarcodechallengekotlin.viewmodels.HolidaysViewModel


object UI {
    @JvmStatic
    fun showSnackBar(view: View, message: String, duration: Int) {
        Snackbar.make(view, message, duration)
            .show()
    }

    @JvmStatic
    fun showNetworkSnackBar(context: Context, view: View, message: String,
                            viewModel: HolidaysViewModel) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction(view.resources.getString(R.string.retry)) {
                if (ConnectionManager.isNetworkAvailable(context)) {
                    viewModel.getHolidays()
                    showSnackBar(view, view.resources.getString(R.string.network_established),
                        Snackbar.LENGTH_LONG)
                } else {
                    showSnackBar(view, view.resources.getString(R.string.network_error),
                        Snackbar.LENGTH_LONG)
                }
            }.show()
    }
}