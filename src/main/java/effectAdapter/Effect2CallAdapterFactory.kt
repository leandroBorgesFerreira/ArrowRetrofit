package effectAdapter

import arrow.effects.IO
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class Effect2CallAdapterFactory : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val rawType = CallAdapter.Factory.getRawType(returnType)

        if (returnType !is ParameterizedType) {
            throw IllegalStateException("${returnType.typeName} return type must be parameterized as IO<Foo> or IO<out Foo>")
        }

        val effectType = CallAdapter.Factory.getParameterUpperBound(0, returnType)

        return when (rawType) {
            IO::class.java -> IO2CallAdapter<Type>(effectType)
            KindedCall::class.java -> KindedCall2CallAdapter<Type>(effectType)
            else -> null
        }
    }
}