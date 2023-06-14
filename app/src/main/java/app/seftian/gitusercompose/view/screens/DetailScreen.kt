package app.seftian.gitusercompose.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import app.seftian.gitusercompose.view.components.LoadingAnimation
import app.seftian.gitusercompose.view.viewmodel.DetailViewModel
import coil.compose.AsyncImage

@Composable
fun DetailScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    username: String,
    detailViewModel: DetailViewModel,
) {
    val userDetail by detailViewModel.userDetail.observeAsState()
    val isLoading by detailViewModel.isLoading.observeAsState()
    val localUser by detailViewModel.getLocalUSer(username).observeAsState()

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                detailViewModel.deleteNotFavorite()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        detailViewModel.getUserDetail(username)
    }




    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.TopCenter,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize().padding(top = 150.dp),
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            DetailComponent(
                name = userDetail?.name,
                login = userDetail?.login,
                repos = userDetail?.publicRepos.toString(),
                location = userDetail?.location,
                modifier = Modifier.padding(top = 50.dp),
                isLoading = isLoading,
                companies = userDetail?.company
            )
        }
        ProfilePicture(
            isLoading,
            userDetail?.avatarUrl,
            modifier = Modifier.size(175.dp).clip(CircleShape)
        )

        FloatingActionButton(
            onClick = {
                localUser?.favorite = !localUser?.favorite!!

                detailViewModel.updateFavorite(localUser!!)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            if (localUser?.favorite == true) {
                Icon(Icons.Filled.ThumbUp, "fab")
            }else{
                Icon(Icons.Outlined.ThumbUp, "fab")
            }
        }
    }

}


@Composable
fun DetailComponent(
    name: String?,
    login: String?,
    location: String?,
    repos: String?,
    isLoading: Boolean?,
    companies: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading == true) {
            LoadingAnimation(circleColor = MaterialTheme.colorScheme.primary, circleSize = 24.dp)
            return@Column
        }

        if (name != null) {
            Text(name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        } else {
            if (login != null) {
                Text(login, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }


        if (location != null) {
            Text(location, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        if (companies != null) {
            Text(companies, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        if (repos != null) {
            AffiliationComponent("Total Repos", repos)
        }


    }
}

@Composable
fun AffiliationComponent(
    title: String,
    content: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(content)
    }

}

@Composable
fun ProfilePicture(
    isLoading: Boolean?,
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
    ) {
        if (isLoading == true) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.inversePrimary
            )
            return@Box
        }
        AsyncImage(
            model = imageUrl,
            contentDescription = "foto profil",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}


