package com.habernaud.rickandmorty.presentation.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.habernaud.rickandmorty.commons.extensions.toColor
import com.habernaud.rickandmorty.commons.extensions.toText
import com.habernaud.rickandmorty.presentation.character.components.EpisodeItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterScreen(
    viewModel: CharacterViewModel,
    id: Long
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(id) {
        viewModel.onEvent(CharacterEvent.LoadCharacter(id))
    }

    ScreenLayout(uiState = uiState)
}


@Composable
fun ScreenLayout(
    uiState: CharacterUiState,
) {
    if (uiState.status is CharacterScreenStatus.Success) {
        val data = uiState.status.character
        // Character screen layout
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Display the character's image
            item {
                AsyncImage(
                    model = data?.image,
                    contentDescription = "Character image",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            // Display character details
            item {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    data?.status?.toColor() ?: Color.DarkGray,
                                    shape = CircleShape
                                )
                                .size(10.dp)
                        )
                        Text(
                            text = "${data?.status?.toText() ?: ""} - ${data?.species ?: ""}",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Text(
                        text = data?.gender?.toText() ?: "",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

            // Display a list of episodes
            items(data?.episodeList?: emptyList()) { episode ->
                var expanded by rememberSaveable { mutableStateOf(false) }

                EpisodeItem(
                    episode = episode.episode,
                    name = episode.name,
                    airDate = episode.airDate,
                    characterListImage = episode.characterList.map { it.image },
                    expanded = expanded
                ) {
                    expanded = !expanded
                }
            }
        }
    }else{null}
}
