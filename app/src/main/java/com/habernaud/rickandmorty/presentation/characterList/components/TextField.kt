package com.habernaud.rickandmorty.presentation.characterList.components

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.habernaud.rickandmorty.presentation.theme.DarkGray

@Composable
fun TextField(
    text: String,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit= {},
    placeholder: String = "",
    trailingIcon: @Composable (() -> Unit)? = null,
    readOnly : Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLength : Int = -1){

    TextField(
        value = TextFieldValue(text, selection = TextRange(text.length)),
        onValueChange = {
            if (maxLength == -1 || it.text.length <= maxLength){
                onTextChange(it.text)
            }
        },
        label = { Text(placeholder, style = MaterialTheme.typography.bodyMedium) },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .border(width = 1.dp, color = DarkGray, shape = RoundedCornerShape(8.dp)),
        colors = TextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onBackground,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedLabelColor = DarkGray,
            disabledLabelColor = DarkGray,
            unfocusedLabelColor = DarkGray
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        trailingIcon = trailingIcon,
        readOnly = readOnly,
        enabled = !readOnly
    )
}