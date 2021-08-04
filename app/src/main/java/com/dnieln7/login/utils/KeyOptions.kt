package com.dnieln7.login.utils

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

object KeyOptions {
    val textNext = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
    )

    val emailNext = KeyboardOptions(
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Next
    )

    val passwordNext = KeyboardOptions(
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Next
    )

    val passwordDone = KeyboardOptions(
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Done
    )

    fun FocusManager.downAction(): KeyboardActions {
        return KeyboardActions(onNext = {
            this@downAction.moveFocus(FocusDirection.Down)
        })

    }

    fun FocusManager.doneAction(): KeyboardActions {
        return KeyboardActions(onDone = { this@doneAction.clearFocus(true) })
    }
}