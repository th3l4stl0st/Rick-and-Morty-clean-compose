package com.habernaud.rickandmorty.presentation.character.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.habernaud.rickandmorty.commons.extensions.toPrettyDate
import com.habernaud.rickandmorty.presentation.preview.PreviewComponents
import com.habernaud.rickandmorty.presentation.theme.Dark100
import java.util.Date

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EpisodeItem(
    modifier : Modifier = Modifier,
    episode : String,
    name : String,
    airDate : Date,
    expanded : Boolean = true,
    characterListImage : List<String> = emptyList(),
    onClick : () -> Unit = { }
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
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "$episode - $name",
                style = MaterialTheme.typography.titleMedium)


            Text(
                text = airDate.toPrettyDate(),
                style = MaterialTheme.typography.titleSmall)

            AnimatedVisibility(visible = expanded) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    characterListImage.forEach {
                        AsyncImage(
                            model = it,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = !expanded) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(characterListImage){
                        AsyncImage(
                            model = it,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                    }
                }

            }
        }
    }
}

@Composable
@Preview
private fun EpisodeItemPreview() = PreviewComponents {
    EpisodeItem(
        modifier = Modifier,
        name = "Pilot",
        episode = "s01e01",
        airDate = Date(),
        characterListImage = listOf(
            "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            "https://rickandmortyapi.com/api/character/avatar/3.jpeg"
        )
    )
}