import arrow.effects.ForIO
import arrow.effects.IO
import arrow.effects.effect
import arrow.effects.fix
import kotlinx.coroutines.experimental.CommonPool
import retrofit.apiClient

fun main(args: Array<String>) {
    apiClient()
        .getRepositoriesEffect<ForIO>("Java", "star", 1)
        .executeCall(IO.effect())
        .fix()
        .unsafeRunSync()
        .items
        .forEach { repo ->
            println(repo.description)
        }

    println("End")
}