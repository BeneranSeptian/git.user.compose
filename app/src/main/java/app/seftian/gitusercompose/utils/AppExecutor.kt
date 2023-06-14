package app.seftian.gitusercompose.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutor {
    val diskIO : Executor = Executors.newSingleThreadExecutor()

}