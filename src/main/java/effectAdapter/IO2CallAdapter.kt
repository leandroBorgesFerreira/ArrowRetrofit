package effectAdapter

import arrow.core.left
import arrow.core.right
import arrow.effects.IO
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class IO2CallAdapter<R>(private val type: Type) : CallAdapter<R, Any> {
    override fun adapt(call: Call<R>): Any =
        IO.async<R> { callBack ->
            call.enqueue(object: Callback<R> {
                override fun onResponse(call: Call<R>, response: Response<R>) {
                    //Todo: Deal with this '!!'
                    callBack(response.body()!!.right())
                }

                override fun onFailure(call: Call<R>, throwable: Throwable) {
                    callBack(throwable.left())
                }
            })
        }

    override fun responseType(): Type = type
}