package effectAdapter

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class KindedCall2CallAdapter<R>(private val type: Type) : CallAdapter<R, KindedCall<R>> {
    override fun adapt(call: Call<R>): KindedCall<R> = KindedCall(call)

    override fun responseType(): Type = type
}