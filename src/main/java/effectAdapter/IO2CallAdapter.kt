package effectAdapter

import arrow.effects.IO
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class IO2CallAdapter<R>(private val type: Type) : CallAdapter<R, Any> {
    override fun adapt(call: Call<R>): Any = IO.async<R> { proc -> call.enqueue(ProcCallBack(proc)) }

    override fun responseType(): Type = type
}
