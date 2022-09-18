package pham.hien.honeylibrary.Utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


fun <R> CoroutineScope.executeAsyncTask(
    onPreExecute: () -> Unit,
    doInBackground: () -> R,
    onPostExecute: (R) -> Unit,
) = launch {
    onPreExecute()
    val result =
        withContext(Dispatchers.IO) { // runs in background thread without blocking the Main
            doInBackground()
        }
    onPostExecute(result)
}

fun <R> CoroutineScope.launch(
    onPreExecute: () -> Unit,
    doInBackground: () -> R,
    onPostExecute: (R) -> Unit,
) = launch {
    onPreExecute()
    val result =
        withContext(Dispatchers.IO) { // runs in background thread without blocking the Main
            doInBackground()
        }
    onPostExecute(result)
}

fun Calendar.startOfYear(): Calendar {
    this[Calendar.MONTH] = 1
    this[Calendar.DAY_OF_MONTH] = 1
    this[Calendar.MILLISECOND] = 0
    this[Calendar.SECOND] = 0
    this[Calendar.MINUTE] = 0
    this[Calendar.HOUR_OF_DAY] = 0
    return this
}

fun Calendar.endOfYear(): Calendar {
    this[Calendar.MONTH] = 11
    this[Calendar.SECOND] = 59
    this[Calendar.MINUTE] = 59
    this[Calendar.HOUR_OF_DAY] = 23
    this[Calendar.DAY_OF_MONTH] = this.getActualMaximum(Calendar.DAY_OF_MONTH)
    return this
}


fun Calendar.startOfMonth(): Calendar {
    this[Calendar.DAY_OF_MONTH] = 1
    this[Calendar.MILLISECOND] = 0
    this[Calendar.SECOND] = 0
    this[Calendar.MINUTE] = 0
    this[Calendar.HOUR_OF_DAY] = 0
    return this
}

fun Calendar.endOfMonth(): Calendar {
    this[Calendar.SECOND] = 59
    this[Calendar.MINUTE] = 59
    this[Calendar.HOUR_OF_DAY] = 23
    this[Calendar.DAY_OF_MONTH] = this.getActualMaximum(Calendar.DAY_OF_MONTH)
    return this
}


fun Calendar.startOfDay(): Calendar {

    this[Calendar.MILLISECOND] = 0
    this[Calendar.SECOND] = 0
    this[Calendar.MINUTE] = 0
    this[Calendar.HOUR_OF_DAY] = 0

    return this
}

fun Calendar.endOfDay(): Calendar {
    this[Calendar.MILLISECOND] = 999
    this[Calendar.SECOND] = 59
    this[Calendar.MINUTE] = 59
    this[Calendar.HOUR_OF_DAY] = 23
    return this
}


