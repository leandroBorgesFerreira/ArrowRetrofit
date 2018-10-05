package effectAdapter

import arrow.effects.IO
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class IO2CallAdapter(private val type: Type) : CallAdapter<Any, Any> {
  override fun adapt(call: Call<Any>): Any = IO.async<Any> { proc -> call.enqueue(ProcCallBack(proc)) }

  override fun responseType(): Type = type
}
