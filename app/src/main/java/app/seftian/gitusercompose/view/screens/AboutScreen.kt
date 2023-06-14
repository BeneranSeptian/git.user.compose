package app.seftian.gitusercompose.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CreateProfileImage()
            Text(
                "Muhammad Septian Nurfaozy",
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Text("m.septiannn@gmail.com")
        }
    }
}

@Composable
fun CreateProfileImage() {
    Surface(
        modifier = Modifier
            .size(150.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, color = Color.LightGray),
        color = MaterialTheme.colorScheme.onSurface,
        shadowElevation = 4.dp
    ) {
        AsyncImage(
            model = "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/small/avatar/dos:982cfda21bed8eec36bcf5351b0ff90720220711151226.png",
            contentDescription = "foto profil",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}