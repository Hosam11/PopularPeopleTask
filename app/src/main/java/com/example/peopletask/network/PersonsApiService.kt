package com.example.peopletask.network

import com.example.peopletask.domain.PersonDetails
import com.example.peopletask.domain.PersonProfile
import com.example.peopletask.domain.PopularPeopleResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val BASE_URL = "https://api.themoviedb.org/3/person/"


/**
 * A retrofit service to fetch a Popular Peoples.
 */
interface PersonsApiService {
    /**
     * @return [PopularPeopleResponse] that contains list of [PersonResult]
     */
    @GET("popular")
    suspend fun getPopularPeople(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): PopularPeopleResponse

    /**
     * @return details of single person by id
     */
    @GET("{id}")
    suspend fun getPersonDetails(
        @Path("id") personId: Long,
        @Query("api_key") apiKey: String
    ): PersonDetails

    /**
     * @return [PersonProfile] object that contains list of [PersonImage]
     */
    @GET("{id}/images")
    suspend fun getPersonImages(
        @Path("id") personId: Long,
        @Query("api_key") apiKey: String
    ): PersonProfile
}

object PersonsNetwork {

    /**
     * Build the Moshi object that Retrofit will be using
     */
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val personsService: PersonsApiService = retrofit.create(PersonsApiService::class.java)
}

