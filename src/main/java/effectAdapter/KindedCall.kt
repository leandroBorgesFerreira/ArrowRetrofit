package effectAdapter

import arrow.Kind
import arrow.core.left
import arrow.core.right
import arrow.effects.typeclasses.Async
import kotlinx.coroutines.experimental.CommonPool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.experimental.CoroutineContext

class KindedCall<R>(private val call: Call<R>) {

    fun <F> executeCall(async: Async<F>): Kind<F, R> =
        async.async { callBack ->
            call.enqueue(object : Callback<R> {
                override fun onResponse(call: Call<R>, response: Response<R>) {
                    //Todo: Deal with this '!!'
                    callBack(response.body()!!.right())
                }

                override fun onFailure(call: Call<R>, throwable: Throwable) {
                    callBack(throwable.left())
                }
            })
        }
}