package md.vnastasi.trainplanner.api.auth

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthorizationInterceptor(
    private val authorizationProvider: AuthorizationProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val newRequest = if (oldRequest.headers.contains("X-Authorized" to "true")) {
            oldRequest.newBuilder()
                .removeHeader("X-Authorized")
                .addHeader("Authorization", createBasicAuthorization())
                .build()
        } else {
            oldRequest
        }

        return chain.proceed(newRequest)
    }

    private fun createBasicAuthorization(): String {
        val authorization = authorizationProvider.invoke()
        return Credentials.basic(authorization.username, authorization.password)
    }
}
