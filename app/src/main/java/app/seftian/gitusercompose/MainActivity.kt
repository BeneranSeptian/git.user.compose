package app.seftian.gitusercompose

import ScreenRoute
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.seftian.gitusercompose.ui.theme.GitUserComposeTheme
import app.seftian.gitusercompose.utils.SettingPreferences
import app.seftian.gitusercompose.view.screens.MainScreen
import app.seftian.gitusercompose.view.viewmodel.DetailViewModel
import app.seftian.gitusercompose.view.viewmodel.FavoriteViewModel
import app.seftian.gitusercompose.view.viewmodel.MainViewModel
import app.seftian.gitusercompose.view.viewmodel.ViewModelFactory
import coil.compose.AsyncImage

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pref = SettingPreferences.getInstance(dataStore)
        mainViewModel =
            ViewModelProvider(this, ViewModelFactory(pref, application))[MainViewModel::class.java]
        detailViewModel =
            ViewModelProvider(this, ViewModelFactory(pref, application))[DetailViewModel::class.java]
        favoriteViewModel =
            ViewModelProvider(this, ViewModelFactory(pref, application))[FavoriteViewModel::class.java]
        setContent {
            GitUserComposeTheme {
                MainScreen(mainViewModel, detailViewModel, favoriteViewModel)
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GitUserComposeTheme {
        //ListItem()
    }
}