package effectAdapter

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class CallKind2CallAdapter(private val type: Type) : CallAdapter<Any, Any> {
  override fun adapt(call: Call<Any>): Any = CallK(call)

  override fun responseType(): Type = type
}
