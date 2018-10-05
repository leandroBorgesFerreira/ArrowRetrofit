package effectAdapter

import arrow.effects.IO
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class IO2CallAdapter<R>(private val type: Type) : CallAdapter<R, IO<R>> {
    override fun adapt(call: Call<R>): IO<R> = IO.async { proc -> call.enqueue(ProcCallBack(proc)) }

    override fun responseType(): Type = type
}
