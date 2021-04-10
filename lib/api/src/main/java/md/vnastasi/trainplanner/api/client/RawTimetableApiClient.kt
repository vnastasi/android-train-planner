package md.vnastasi.trainplanner.api.client

import md.vnastasi.trainplanner.api.domain.board.ArrivalWrapper
import md.vnastasi.trainplanner.api.domain.board.DepartureWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

internal interface RawTimetableApiClient {

    @GET("/api/v1/station/{code}/departures")
    @Headers("X-Authorized: true")
    suspend fun getDepartures(
            @Path("code") code: String
    ): Response<List<DepartureWrapper>>

    @GET("/api/v1/station/{code}/arrivals")
    @Headers("X-Authorized: true")
    suspend fun getArrivals(
            @Path("code") code: String
    ): Response<List<ArrivalWrapper>>
}