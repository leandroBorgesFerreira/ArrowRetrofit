package effectAdapter

import arrow.Kind
import arrow.core.ForOption
import arrow.effects.*
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

        ForOption::class.java.toString()

        val effectType = CallAdapter.Factory.getParameterUpperBound(0, returnType)

        return when (rawType) {
            IO::class.java -> IO2CallAdapter<Any>(effectType)
            Kind::class.java -> {
                val effect = when(effectType.toString()) {
                    ForIO::class.java.toString() -> IO.effect()
                    ForObservableK::class.java.toString() -> ObservableK.effect()
                    else -> throw RuntimeException("BLA")
                }
                val valueType = CallAdapter.Factory.getParameterUpperBound(1, returnType)
                Kind2CallAdapter(valueType, effect)
            }
            else -> null
        }
    }
}