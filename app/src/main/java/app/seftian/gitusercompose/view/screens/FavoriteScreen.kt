package app.seftian.gitusercompose.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.seftian.gitusercompose.view.components.CardWithImageAndText
import app.seftian.gitusercompose.view.viewmodel.FavoriteViewModel

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    favoriteViewModel: FavoriteViewModel,
    navigateToDetail: (String) -> Unit
) {
    val favoriteLocalUser by favoriteViewModel.getFavoriteUser().observeAsState()

    if (favoriteLocalUser == null || favoriteLocalUser?.isEmpty() == true) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Belum ada user favorite")
        }
        return
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        items(favoriteLocalUser!!, key = { it.login }) { user ->
            CardWithImageAndText(
                username = user.login,
                imageUrl = user.avatarUrl!!,
                navigateToDetail = navigateToDetail,
                deletable = true,
                onClickDelete = {
                    user.favorite = false
                    favoriteViewModel.updateFavorite(user)
                }
            )
        }
    }
}