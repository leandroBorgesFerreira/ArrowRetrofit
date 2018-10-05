import arrow.effects.IO
import arrow.effects.async
import arrow.effects.fix
import repositories.dto.GithubAnswerDto
import retrofit.apiClient
import retrofit2.Response

fun main(args: Array<String>) {
  //    apiClient()
  //        .getRepositoriesObservable("Java", "star", 1)
  //        .subscribe { dto ->
  //            dto.items
  //                .forEach { repo ->
  //                    println(repo.description)
  //                }
  //        }

  val unsafeRunSync: Response<GithubAnswerDto> = apiClient()
    .getRepositoriesEffect("Java", "star", 1)
    .async(IO.async())
    .fix()
    .unsafeRunSync()

  println("End")
}