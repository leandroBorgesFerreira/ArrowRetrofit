package effectAdapter

import arrow.Kind
import arrow.effects.ForIO
import arrow.effects.IO
import arrow.effects.async
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class Effect2CallAdapterFactory : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val rawType : Class<*> = CallAdapter.Factory.getRawType(returnType)

        if (returnType !is ParameterizedType) {
            throw IllegalStateException("${returnType.typeName} return type must be parameterized as IO<Foo> or IO<out Foo>")
        }
        return when (rawType) {
            IO::class.java -> {
                val effectType = CallAdapter.Factory.getParameterUpperBound(0, returnType)
                IO2CallAdapter<Any>(effectType)
            }
            Kind::class.java -> {
                val effectType = CallAdapter.Factory.getParameterUpperBound(1, returnType)

                if (effectType == ForIO::class.java) {
                    KindedCall2CallAdapter<ForIO, Any>(IO.async(), effectType)
                } else {
                    KindedCall2CallAdapter<ForIO, Any>(IO.async(), effectType)
                }
            }
            else -> null
        }
    }
}