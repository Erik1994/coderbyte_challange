package com.example.coderbytechallange.ui.extensions

import android.view.View
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import com.example.coderbytechallange.R
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun EditText.validate(): Boolean = if (text.toString().isEmpty()) {
    hint = resources.getString(R.string.validation_error)
    false
} else true


fun View.clicks(): Flow<Unit> = callbackFlow {
    setOnClickListener { safeOffer(Unit) }
    awaitClose{ setOnClickListener { null } }
}

fun <T> ProducerScope<T>.safeOffer(element: T) {
    if (!isClosedForSend) {
        trySend(element)
    }
}