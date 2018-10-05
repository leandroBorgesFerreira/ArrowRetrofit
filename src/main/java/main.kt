import retrofit.apiClient

fun main(args: Array<String>) {
    apiClient()
        .getRepositoriesObservable("Java", "star", 1)
        .subscribe { dto ->
            dto.items
                .forEach { repo ->
                    println(repo.description)
                }
        }

//    apiClient()
//        .getRepositoriesEffect("Java", "star", 1)
//        .execute(ObservableK.async())
//        .fix()

//        .unsafeRunSync()
//        .items
//        .forEach { repo -> println(repo.description) }

    println("End")
}