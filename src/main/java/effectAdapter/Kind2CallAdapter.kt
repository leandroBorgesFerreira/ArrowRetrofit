package effectAdapter

import arrow.Kind
import arrow.core.left
import arrow.core.right
import arrow.effects.typeclasses.Async
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class Kind2CallAdapter<F>(private val type: Type, private val AS: Async<F>) : CallAdapter<Any, Any>, Async<F> by AS {
    override fun adapt(call: Call<Any>): Kind<F, Any> = async { callBack ->
        call.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                try {
                    callBack(response.body()!!.right())
                } catch (t: Throwable) {
                    callBack(t.left())
                }
            }

            override fun onFailure(call: Call<Any>, throwable: Throwable) {
                callBack(throwable.left())
            }
        })
    }

    override fun responseType(): Type = type
}