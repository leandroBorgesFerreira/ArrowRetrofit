package retrofit.effectAdapter

import arrow.Kind
import arrow.effects.typeclasses.Effect
import kotlinx.coroutines.experimental.CommonPool
import retrofit2.Call
import kotlin.coroutines.experimental.CoroutineContext

class KindedCall<R>(private val call: Call<R>) {

    fun <F> executeCall(effect: Effect<F>, ctx: CoroutineContext = CommonPool): Kind<F, R> =
        effect(ctx) {
            call.execute().body()!!
        }
}