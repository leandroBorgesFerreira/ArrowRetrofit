import arrow.effects.IO
import arrow.effects.effect
import arrow.effects.fix
import retrofit.apiClient

fun main(args: Array<String>) {
    apiClient()
        .getRepositoriesEffect("Java", "star", 1)
        .executeCall(IO.effect())
        .fix()
        .unsafeRunSync()
        .items
        .forEach { repo -> println(repo.description) }

    println("End")
}