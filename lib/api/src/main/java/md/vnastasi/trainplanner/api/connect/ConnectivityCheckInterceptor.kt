package md.vnastasi.trainplanner.api.connect

import md.vnastasi.trainplanner.api.exception.MissingNetworkConnectionException
import okhttp3.Interceptor
import okhttp3.Response

internal class ConnectivityCheckInterceptor(
        private val connectivityChecker: ConnectivityChecker
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (connectivityChecker.invoke()) {
            return chain.proceed(chain.request())
        }

        throw MissingNetworkConnectionException()
    }
}
