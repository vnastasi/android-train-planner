package md.vnastasi.trainplanner.api.client

import md.vnastasi.trainplanner.api.domain.disturbance.DisruptionWrapper
import md.vnastasi.trainplanner.api.domain.disturbance.DisturbanceTypeWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

internal interface RawDisruptionsApiClient {

    @JvmSuppressWildcards
    @GET("/api/v1/disruption")
    @Headers("X-Authorized: true")
    suspend fun getDisruptions(@Query("types") types: List<DisturbanceTypeWrapper>): Response<List<DisruptionWrapper>>
}
