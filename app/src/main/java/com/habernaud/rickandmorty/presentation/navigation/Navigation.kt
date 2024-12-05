package com.habernaud.rickandmorty.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.habernaud.rickandmorty.presentation.character.CharacterScreen
import com.habernaud.rickandmorty.presentation.character.CharacterViewModel
import com.habernaud.rickandmorty.presentation.characterList.CharacterListScreen
import com.habernaud.rickandmorty.presentation.characterList.CharacterListViewModel
import com.habernaud.rickandmorty.presentation.episodes.EpisodesScreen
import com.habernaud.rickandmorty.presentation.episodes.EpisodesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = CharacterList){
        composable<CharacterList>(
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            }
        ){
            val viewModel:CharacterListViewModel = koinViewModel()
            CharacterListScreen(viewModel){
                navController.navigate(Character(it.id))
            }
        }

        composable<Character>(
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ){backStackEntry ->
            val viewModel:CharacterViewModel = koinViewModel()
            val id = backStackEntry.arguments?.getLong("id") ?: 1L
            CharacterScreen(viewModel,id)
        }


        composable<Episodes>(
            enterTransition = {
                if (initialState.destination.route == Locations::class.qualifiedName) {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                } else {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                }
            },
            exitTransition = {
                if (targetState.destination.route == Locations::class.qualifiedName) {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                } else {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                }
            }
        ) {
            val viewModel:EpisodesViewModel = koinViewModel()
            EpisodesScreen(viewModel)
        }


        composable<Locations>(
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ){
        }
    }
}