package effectAdapter

import arrow.Kind
import arrow.core.left
import arrow.core.right
import arrow.effects.IO
import arrow.effects.typeclasses.Async
import kotlinx.coroutines.experimental.CommonPool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.experimental.CoroutineContext

class KindedCall<R>(private val call: Call<R>) {

    fun <F> execute(async: Async<F>): Kind<F, R> = async.async<R> { proc -> call.enqueue(ProcCallBack(proc)) }
}