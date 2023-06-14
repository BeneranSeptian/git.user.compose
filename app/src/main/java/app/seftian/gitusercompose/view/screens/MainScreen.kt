package app.seftian.gitusercompose.view.screens

import ScreenRoute
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.seftian.gitusercompose.ui.theme.GitUserComposeTheme
import app.seftian.gitusercompose.view.components.BottomBar
import app.seftian.gitusercompose.view.viewmodel.DetailViewModel
import app.seftian.gitusercompose.view.viewmodel.FavoriteViewModel
import app.seftian.gitusercompose.view.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    detailViewModel: DetailViewModel,
    favoriteViewModel: FavoriteViewModel,
    navController: NavHostController = rememberNavController()
) {
    var showMenu by remember { mutableStateOf(false) }
    val isDarkTheme by mainViewModel.getThemeSettings().observeAsState(initial = false)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route



    GitUserComposeTheme(darkTheme = isDarkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            if(currentRoute == ScreenRoute.Detail.route ){
                                Text("Detail", color = Color.White)
                            }
                            if(currentRoute == ScreenRoute.Home.route ){
                                Text("Git User Compose", color = Color.White)
                            }
                            if(currentRoute == ScreenRoute.Favourite.route ){
                                Text("Favorite", color = Color.White)
                            }
                            if(currentRoute == ScreenRoute.About.route ){
                                Text("Tentang", color = Color.White)
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                        actions = {
                            IconButton(onClick = { showMenu = true }) {
                                Icon(
                                    Icons.Filled.Settings,
                                    contentDescription = "setting icon",
                                    tint = Color.White
                                )
                            }
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false },
                            ) {
                                DropdownMenuItem(
                                    onClick = { mainViewModel.saveThemeSetting(!isDarkTheme) },
                                    text = { Text("Dark Mode") },
                                    trailingIcon = {
                                        Switch(
                                            checked = isDarkTheme,
                                            onCheckedChange = { mainViewModel.saveThemeSetting(!isDarkTheme) })
                                    }
                                )
                                DropdownMenuItem(
                                    onClick = { navController.navigate(ScreenRoute.About.route) },
                                    text = { Text("Tentang Saya") })
                            }
                        }
                    )
                },
                bottomBar = {
                    if (currentRoute == ScreenRoute.Home.route || currentRoute == ScreenRoute.Favourite.route) {
                        BottomBar(navController = navController)
                    }
                },
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = ScreenRoute.Home.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(ScreenRoute.Home.route) {
                        HomeScreen(mainViewModel, navigateToDetail = { username ->
                            navController.navigate(ScreenRoute.Detail.createRoute(username))
                        })
                    }

                    composable(ScreenRoute.Favourite.route) {
                        FavoriteScreen(favoriteViewModel = favoriteViewModel, navigateToDetail= {username ->
                            navController.navigate(ScreenRoute.Detail.createRoute(username))
                        })
                    }

                    composable(
                        route = ScreenRoute.Detail.route,
                        arguments = listOf(navArgument("username") { type = NavType.StringType }),
                    ) {
                        val username = it.arguments?.getString("username")
                        if (username != null) {
                            DetailScreen(username = username, detailViewModel = detailViewModel)
                        }
                    }

                    composable(
                        route = ScreenRoute.About.route
                    ){
                        AboutScreen()
                    }
                }
            }
        }
    }
}