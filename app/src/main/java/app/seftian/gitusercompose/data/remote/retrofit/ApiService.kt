package app.seftian.gitusercompose.data.remote.retrofit



import app.seftian.gitusercompose.BuildConfig
import app.seftian.gitusercompose.data.remote.model.SearchResponse
import app.seftian.gitusercompose.data.remote.model.UserDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token"+ BuildConfig.API_KEY)
    suspend fun getUsers(@Query("q") username : String) : Response<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token"+ BuildConfig.API_KEY)
    suspend fun getSingleUser(@Path("username") username: String) : Response<UserDetail>

}