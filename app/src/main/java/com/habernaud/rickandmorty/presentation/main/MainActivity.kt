package com.habernaud.rickandmorty.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.habernaud.rickandmorty.R
import com.habernaud.rickandmorty.commons.extensions.getSubtitle
import com.habernaud.rickandmorty.commons.extensions.getTitle
import com.habernaud.rickandmorty.commons.helpers.ErrorHelper
import com.habernaud.rickandmorty.commons.helpers.NavigationHelper
import com.habernaud.rickandmorty.presentation.character.CharacterScreen
import com.habernaud.rickandmorty.presentation.character.CharacterViewModel
import com.habernaud.rickandmorty.presentation.characterList.CharacterListScreen
import com.habernaud.rickandmorty.presentation.characterList.CharacterListViewModel
import com.habernaud.rickandmorty.presentation.episodes.EpisodesScreen
import com.habernaud.rickandmorty.presentation.episodes.EpisodesViewModel
import com.habernaud.rickandmorty.presentation.main.components.NavigationBar
import com.habernaud.rickandmorty.presentation.navigation.Character
import com.habernaud.rickandmorty.presentation.navigation.CharacterList
import com.habernaud.rickandmorty.presentation.navigation.Episodes
import com.habernaud.rickandmorty.presentation.navigation.Locations
import com.habernaud.rickandmorty.presentation.navigation.Navigation
import com.habernaud.rickandmorty.presentation.navigation.TopLevelRoute
import com.habernaud.rickandmorty.presentation.theme.RickAndMortyTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent.get

class MainActivity(
    private val navigationHelper: NavigationHelper = get(NavigationHelper::class.java),
    private val errorHelper: ErrorHelper = get(ErrorHelper::class.java)
) : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                val title = navController.currentBackStackEntry?.getTitle() ?: ""
                val subtitle = navController.currentBackStackEntry?.getSubtitle() ?: ""
                val showBackButton = navController.currentBackStackEntryAsState().value?.destination?.route?.contains("/") ?: false

                val topLevelRoutes = listOf(
                    TopLevelRoute(route = CharacterList, icon = R.drawable.character, label = R.string.characterList),
                    TopLevelRoute(route = Episodes, icon = R.drawable.episode, label = R.string.episodes),
                    TopLevelRoute(route = Locations, icon = R.drawable.location, label = R.string.locations)
                )

                Scaffold(
                    topBar = {
                        NavigationBar(
                            title = title,
                            subtitle = subtitle,
                            showBackButton = showBackButton,
                            onBack = navigationHelper::goBack
                        )
                    },
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        if(currentDestination?.route != Character::class.qualifiedName+"/{id}") {

                            BottomAppBar(
                                containerColor = MaterialTheme.colorScheme.primary,
                            ) {

                                topLevelRoutes.forEach { topLevelRoute ->

                                    NavigationBarItem(
                                        selected = currentDestination?.route == topLevelRoute.route::class.qualifiedName,
                                        icon = {
                                            Icon(
                                                painter = painterResource(id = topLevelRoute.icon),
                                                contentDescription = stringResource(id = topLevelRoute.label)
                                            )
                                        },
                                        label = {
                                            Text(text = stringResource(id = topLevelRoute.label))
                                        },
                                        alwaysShowLabel = true,
                                        onClick = {
                                            navController.navigate(topLevelRoute.route) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            unselectedIconColor = Color.Gray,
                                            unselectedTextColor = Color.Gray,
                                            selectedIconColor = Color.White,
                                            selectedTextColor = Color.White,
                                            indicatorColor = MaterialTheme.colorScheme.secondary
                                        )
                                    )
                                }
                            }
                        }
                    },
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) {
                    LaunchedEffect(Unit) {
                        navigationHelper.sharedFlow.onEach { event ->
                            when(event){
                                is NavigationHelper.NavigationEvent.NavigateTo -> {
                                    navController.navigate(event.destination.route) {
                                        // avoiding multiple copies on the top of the back stack
                                        launchSingleTop = true

                                        if(!event.popupTo.isNullOrEmpty())
                                            popUpTo(event.popupTo)
                                    }
                                }
                                is NavigationHelper.NavigationEvent.GoBack -> {
                                    navController.navigateUp()
                                }
                            }
                        }.launchIn(this)
                    }

                    LaunchedEffect(Unit){
                        errorHelper.sharedFlow.onEach { message ->
                            scope.launch {
                                if (snackbarHostState.currentSnackbarData != null)
                                    snackbarHostState.currentSnackbarData!!.dismiss()

                                snackbarHostState.showSnackbar(message, withDismissAction = true)
                            }
                        }.launchIn(this)
                    }

                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Navigation(navController)
                    }
                }
            }
        }
    }
}