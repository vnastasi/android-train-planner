package md.vnastasi.trainplanner.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import md.vnastasi.trainplaner.di.ModuleDefinition
import md.vnastasi.trainplanner.api.auth.AuthorizationInterceptor
import md.vnastasi.trainplanner.api.auth.AuthorizationProvider
import md.vnastasi.trainplanner.api.client.*
import md.vnastasi.trainplanner.api.client.RawStationsApiClient
import md.vnastasi.trainplanner.api.client.RawTimetableApiClient
import md.vnastasi.trainplanner.api.client.impl.DisruptionsApiClientImpl
import md.vnastasi.trainplanner.api.client.impl.StationsApiClientImpl
import md.vnastasi.trainplanner.api.client.impl.TimetableApiClientImpl
import md.vnastasi.trainplanner.api.connect.ConnectivityCheckInterceptor
import md.vnastasi.trainplanner.api.connect.ConnectivityChecker
import md.vnastasi.trainplanner.api.domain.serializers.OffsetDateTimeSerializer
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

private const val CONNECTION_TIMEOUT_SECONDS: Long = 15
private const val CACHE_SIZE: Long = 5 * 1024 * 1024

class ApiModuleDefinition(
        private val configuration: Configuration
) : ModuleDefinition {

    override val module: Module = module {

        single {
            val customSerializers = SerializersModule {
                contextual(OffsetDateTimeSerializer())
            }

            Json {
                serializersModule = customSerializers
                ignoreUnknownKeys = true
            }
        }

        single {
            OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .readTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .writeTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .cache(Cache(configuration.cacheDir, CACHE_SIZE))
                    .addInterceptor(ConnectivityCheckInterceptor(configuration.connectivityChecker))
                    .addInterceptor(AuthorizationInterceptor(configuration.authorizationProvider))
                    .addInterceptor(createHttpLoggingInterceptor(configuration))
                    .build()
        }

        single {
            Retrofit.Builder()
                    .baseUrl(configuration.baseUrl)
                    .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
                    .client(get())
                    .build()
        }

        factory<StationsApiClient> {
            StationsApiClientImpl(get<Retrofit>().create(RawStationsApiClient::class.java), get())
        }

        factory<TimetableApiClient> {
            TimetableApiClientImpl(get<Retrofit>().create(RawTimetableApiClient::class.java), get())
        }

        factory<DisruptionsApiClient> {
            DisruptionsApiClientImpl(get<Retrofit>().create(RawDisruptionsApiClient::class.java), get())
        }
    }

    private fun createHttpLoggingInterceptor(configuration: Configuration): Interceptor {
        val level = if (configuration.isLoggingAllowed) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        return HttpLoggingInterceptor().apply { this.level = level }
    }

    class Configuration(
            val baseUrl: String,
            val authorizationProvider: AuthorizationProvider,
            val connectivityChecker: ConnectivityChecker,
            val cacheDir: File,
            val isLoggingAllowed: Boolean
    )
}
