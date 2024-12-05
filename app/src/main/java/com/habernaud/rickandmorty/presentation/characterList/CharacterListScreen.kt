package com.habernaud.rickandmorty.presentation.characterList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habernaud.rickandmorty.R
import com.habernaud.rickandmorty.domain.models.CharacterListModel
import com.habernaud.rickandmorty.domain.models.Gender
import com.habernaud.rickandmorty.domain.models.Status
import com.habernaud.rickandmorty.presentation.characterList.components.CharacterListItem
import com.habernaud.rickandmorty.presentation.characterList.components.Loader
import com.habernaud.rickandmorty.presentation.characterList.components.TextField
import com.habernaud.rickandmorty.presentation.characterList.vo.CharacterListVO
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel,
    onCharacterSelected: (CharacterListVO) -> Unit
){
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit){
        viewModel.onEvent(CharacterListEvent.LoadCharacterList)
    }

    ScreenLayout(uiState = uiState, onEvent = viewModel::onEvent, onClickItem = onCharacterSelected)
}

@Composable
private fun ScreenLayout(uiState: CharacterListUiState, onEvent: (CharacterListEvent) -> Unit, onClickItem: (CharacterListVO) -> Unit = {}) {
    val scrollState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp)
                .padding(bottom = 4.dp),
            placeholder = stringResource(id = R.string.search_character),
            text = uiState.query,
            onTextChange = { onEvent(CharacterListEvent.OnQueryChanged(it)) },
            keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = null
                )
            }
        )


        if (uiState.loading){
            Loader(
                modifier = Modifier.fillMaxSize(),
                message = uiState.loadingMessage
            )
        }
        else {
            LazyColumn(
                state = scrollState,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.characterList.filter {
                    it.name.contains(uiState.query, ignoreCase = true) ||
                            it.gender.name.contains(uiState.query, ignoreCase = true) ||
                            it.species.contains(uiState.query, ignoreCase = true) ||
                            it.status.name.contains(uiState.query, ignoreCase = true)
                }){ character ->
                    CharacterListItem(
                        modifier = Modifier.fillMaxWidth(),
                        name = character.name,
                        status = character.status,
                        species = character.species,
                        image = character.image,
                        gender = character.gender
                    ){
                        onClickItem(character)
                    }
                }
            }
        }
    }
}
@Composable
@Preview
private fun CharacterListScreenPreview2(){
    Surface {
        ScreenLayout(
            uiState = CharacterListUiState(loading = true),
            onEvent = {}
        )
    }
}


@Composable
@Preview
private fun CharacterListScreenPreview(){
    Surface {
        ScreenLayout(
            uiState = CharacterListUiState(characterList = listOf(
                CharacterListVO(
                    id = 1,
                    name = "Rick",
                    status = Status.Alive,
                    species = "Human",
                    gender = Gender.Male,
                    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                )
            )),
            onEvent = {}
        )
    }
}