package app.seftian.gitusercompose.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.seftian.gitusercompose.data.local.UserEntity
import app.seftian.gitusercompose.data.local.room.UserDatabase
import app.seftian.gitusercompose.data.remote.model.UserDetail
import app.seftian.gitusercompose.data.remote.retrofit.ApiConfig
import app.seftian.gitusercompose.utils.AppExecutor
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private val executors = AppExecutor()
    private val database = UserDatabase.getInstance(getApplication())
    private val userDao = database.userDao()


    fun getUserDetail(username: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val response =
                try {
                    ApiConfig.getApiService().getSingleUser(username)
                } catch (e: HttpException) {
                    Log.e("error", e.message())
                    return@launch
                }
            if (response.isSuccessful && response.body() != null) {
                _isLoading.value = false
                _userDetail.value = response.body()!!
                val userToInsert = UserEntity(
                    name = userDetail.value?.name,
                    company = userDetail.value?.company,
                    publicRepos = userDetail.value?.publicRepos,
                    followers = userDetail.value?.followers,
                    following = userDetail.value?.following,
                    followersUrl = userDetail.value?.followersUrl,
                    followingUrl = userDetail.value?.followingUrl,
                    location = userDetail.value?.location,
                    login = userDetail.value!!.login,
                    avatarUrl = userDetail.value?.avatarUrl
                )

                executors.diskIO.execute {
                    userDao.insertUser(userToInsert)
                }
            } else {
                _isLoading.value = false
            }

        }

    }

    fun deleteNotFavorite() {
        executors.diskIO.execute {
            userDao.delete()
        }
    }

    fun getLocalUSer(search: String): LiveData<UserEntity> {
        return userDao.getUser(search)

    }

    fun updateFavorite(userEntity: UserEntity) {
        executors.diskIO.execute {
            userDao.updateUser(userEntity)
        }
    }
}