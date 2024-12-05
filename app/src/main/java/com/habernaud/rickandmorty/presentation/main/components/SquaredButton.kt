package com.habernaud.rickandmorty.presentation.main.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SquaredButton(
    modifier : Modifier = Modifier,
    contentModifier : Modifier = Modifier,
    @DrawableRes resource: Int,
    onClick: () -> Unit,
    backgroundColor : Color = MaterialTheme.colorScheme.primary,
    color : Color = Color.White,
) {
    Box(
        modifier = modifier
            .size(44.dp)
            .background(backgroundColor, RoundedCornerShape(size = 8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
    )  {
        Icon(
            painter = painterResource(resource),
            contentDescription = "SquaredButton icon",
            tint = color,
            modifier = contentModifier.align(Alignment.Center))
    }
}