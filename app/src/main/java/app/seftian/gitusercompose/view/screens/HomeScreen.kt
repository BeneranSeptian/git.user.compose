package app.seftian.gitusercompose.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.seftian.gitusercompose.utils.ConnectivityObserver
import app.seftian.gitusercompose.view.components.InputTextWithButton
import app.seftian.gitusercompose.view.components.LoadingAnimation
import app.seftian.gitusercompose.view.components.SearchResultList
import app.seftian.gitusercompose.view.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    navigateToDetail: (String) -> Unit,
) {
    var textInput by remember { mutableStateOf("") }
    val listUsers by mainViewModel.users.observeAsState()
    val isLoading by mainViewModel.isLoading.observeAsState()
    var isButtonSearchAndTextFieldEnabled by remember { mutableStateOf(true) }
    var networkStatusText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        mainViewModel.status.collect { status ->
            when (status) {
                ConnectivityObserver.Status.Available -> {
                    networkStatusText =
                        "Koneksi internet tersedia, silahkan cari user yang anda inginkan"
                    mainViewModel.getUsers("seftian")
                    isButtonSearchAndTextFieldEnabled = true
                }
                ConnectivityObserver.Status.Unavailable -> {
                    networkStatusText =
                        "Koneksi internet tidak tersedia, fitur pencarian dimatikan"
                    isButtonSearchAndTextFieldEnabled = false
                }
                ConnectivityObserver.Status.Lost -> {
                    networkStatusText =
                        "Koneksi internet hilang, fitur pencarian dimatikan"
                    isButtonSearchAndTextFieldEnabled = false
                }
                ConnectivityObserver.Status.Losing -> {
                    networkStatusText = "Anda mulai kehilangan koneksi internet"
                    isButtonSearchAndTextFieldEnabled = false
                }
            }
        }

    }
    Column(
        modifier = androidx.compose.ui.Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
    ) {
        InputTextWithButton(
            text = textInput,
            onClick = {
                mainViewModel.getUsers(textInput)
            },
            onTextChange = { value ->
                textInput = value
            },
            isEnabled = isButtonSearchAndTextFieldEnabled
        )
        Text(networkStatusText)
        if (isLoading == true) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingAnimation(
                    circleSize = 12.dp,
                    circleColor = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            listUsers?.let { SearchResultList(listUsers = it.items, navigateToDetail = navigateToDetail) }
        }
    }
}