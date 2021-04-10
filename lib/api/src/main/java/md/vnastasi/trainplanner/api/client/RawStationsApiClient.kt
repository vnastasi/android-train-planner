package md.vnastasi.trainplanner.api.client

import md.vnastasi.trainplanner.api.domain.station.DistanceAwareStationWrapper
import md.vnastasi.trainplanner.api.domain.station.StationWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

internal interface RawStationsApiClient {

    @GET("/api/v1/station")
    @Headers("X-Authorized: true")
    suspend fun getStations(): Response<List<StationWrapper>>

    @GET("/api/v1/station/nearby")
    @Headers("X-Authorized: true")
    suspend fun getNearbyStations(
            @Query("latitude") latitude: Double,
            @Query("longitude") longitude: Double,
            @Query("limit") limit: Int
    ): Response<List<DistanceAwareStationWrapper>>
}
