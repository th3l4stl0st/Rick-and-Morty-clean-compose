package com.habernaud.rickandmorty.presentation.episodes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.habernaud.rickandmorty.R
import com.habernaud.rickandmorty.presentation.episodes.components.EpisodesItem

@Composable
fun EpisodesScreen(
    viewModel: EpisodesViewModel,
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit){
        viewModel.onEvent(EpisodesEvent.OnLoadEpisodes)
    }
    ScreenLayout(uiState = uiState)
}
@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun ScreenLayout(uiState: EpisodesUiState) {

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        uiState.episodes.forEach { (season, episodes) ->
            stickyHeader {
                Text(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth(),
                    text = "${stringResource(id = R.string.season_number)}$season",
                )
            }

            items(items = episodes){ episode ->
                EpisodesItem(
                    name = episode.name,
                    episode = episode.episode,
                    airDate = episode.airDate
                ) {

                }
            }
        }
    }
}