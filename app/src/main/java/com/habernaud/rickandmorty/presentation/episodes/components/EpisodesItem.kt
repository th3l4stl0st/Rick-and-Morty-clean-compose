package com.habernaud.rickandmorty.presentation.episodes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habernaud.rickandmorty.commons.extensions.toPrettyDate
import com.habernaud.rickandmorty.presentation.preview.PreviewComponents
import com.habernaud.rickandmorty.presentation.theme.Dark100
import java.util.Date

@Composable
fun EpisodesItem(
    modifier : Modifier = Modifier,
    name : String,
    episode : String,
    airDate : Date,
    onClick : () -> Unit = {}
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Dark100
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall)

            Text(
                text = "$episode - ${airDate.toPrettyDate()}",
                style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
@Preview
private fun EpisodesItemPreview() = PreviewComponents {
    EpisodesItem(name = "Pilot", episode = "S01E01", airDate = Date())
    EpisodesItem(name = "Pilot 2", episode = "S02E01", airDate = Date())
}