package app.seftian.gitusercompose.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.seftian.gitusercompose.data.remote.model.User

@Composable
fun SearchResultList(
    modifier: Modifier = Modifier,
    listUsers: List<User>,
    navigateToDetail : (String) -> Unit
) {
    if(listUsers.isEmpty()){
        Text("User tidak ditemukan", color = Color.Red)
        return
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(listUsers, key = { it.id }) { user ->
            CardWithImageAndText(username = user.login!!, imageUrl = user.avatarUrl!!, navigateToDetail = navigateToDetail)
        }
    }
}