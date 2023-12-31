package app.seftian.gitusercompose.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.seftian.gitusercompose.data.local.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: UserDatabase? = null
        fun getInstance(context : Context) : UserDatabase =
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java, "Users.db"
            ).build()
    }
}