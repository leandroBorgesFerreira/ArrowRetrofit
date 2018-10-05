package effectAdapter

import arrow.effects.IO
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class Effect2CallAdapterFactory : CallAdapter.Factory() {

    companion object {
        fun create() = Effect2CallAdapterFactory()
    }

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val rawType = CallAdapter.Factory.getRawType(returnType)

        if (returnType !is ParameterizedType) {
            val name = parseTypeName(returnType)
            throw IllegalArgumentException("Return type must be parameterized as " +
                "$name<Foo> or $name<out Foo>")
        }

        val effectType = CallAdapter.Factory.getParameterUpperBound(0, returnType)

        return when (rawType) {
            IO::class.java -> IO2CallAdapter<Type>(effectType)
            CallK::class.java -> CallKind2CallAdapter<Type>(effectType)
            else -> null
        }
    }
}

private fun parseTypeName(type: Type) =
    type.typeName
        .split(".")
        .last()

