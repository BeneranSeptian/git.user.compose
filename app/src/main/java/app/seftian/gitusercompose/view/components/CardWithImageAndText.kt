package app.seftian.gitusercompose.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun CardWithImageAndText(
    modifier: Modifier = Modifier,
    username: String,
    imageUrl: String,
    deletable: Boolean = false,
    onClickDelete: () -> Unit = {},
    navigateToDetail: (String) -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp)
            .clickable {
                       navigateToDetail(username)
            },
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "foto profil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Text(username, fontSize = 16.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier=Modifier.weight(1f))

            if(deletable){
                Icon(
                    Icons.Default.Delete, contentDescription = "icon delete",
                    tint = Color.Red,
                    modifier = Modifier
                        .clickable { onClickDelete() }
                        .padding(end = 16.dp)
                )
            }
        }
    }
}
