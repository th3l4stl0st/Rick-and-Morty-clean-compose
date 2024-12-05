package com.habernaud.rickandmorty.presentation.characterList

import com.habernaud.rickandmorty.domain.models.CharacterListModel
import com.habernaud.rickandmorty.presentation.characterList.vo.CharacterListVO


class CharacterListMapper{
    fun mapToUI(characterData : CharacterListModel)= CharacterListVO(
        id = characterData.id,
        name = characterData.name,
        status = characterData.status,
        species = characterData.species,
        image = characterData.image,
        gender = characterData.gender,
    )
    fun mapToUIList(characterList : List<CharacterListModel>)= characterList.map {
        mapToUI(it)
    }
}