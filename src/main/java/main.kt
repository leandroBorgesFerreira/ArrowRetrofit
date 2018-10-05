import arrow.effects.fix
import retrofit.apiClient

fun main(args: Array<String>) {
  apiClient()
    .getRepositoriesEffect("Java", "star", 1)
    .fix()
    .attempt().unsafeRunSync()

  apiClient()
    .getRepositoriesIO("Java", "star", 1)
    .fix()
    .attempt().unsafeRunSync()

  println("End")
}