import arrow.effects.IO
import arrow.effects.async
import arrow.effects.fix
import repositories.dto.GithubAnswerDto
import retrofit.apiClient
import retrofit2.Response

fun main(args: Array<String>) {
    apiClient()
        .getRepositoriesEffect("Java", "star", 1)
        .async(IO.async())
        .fix()
        .unsafeRunSync()
        .items
        .forEach { repo ->
            repo.description
        }

    println("End")
}