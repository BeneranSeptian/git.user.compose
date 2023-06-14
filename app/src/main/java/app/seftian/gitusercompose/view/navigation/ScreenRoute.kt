sealed class ScreenRoute (val route: String){
    object Home: ScreenRoute("home")
    object About: ScreenRoute("about")
    object Favourite: ScreenRoute("favourite")
    object Detail: ScreenRoute("home/{username}"){
        fun createRoute(username: String) = "home/$username"
    }
}