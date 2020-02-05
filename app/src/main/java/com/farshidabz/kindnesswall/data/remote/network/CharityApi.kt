package com.farshidabz.kindnesswall.data.remote.network

import com.farshidabz.kindnesswall.data.local.dao.charity.CharityModel
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Farshid Abazari since 25/10/19
 *
 * Usage: a interface to define end points
 *
 * How to call: just add in appInjector and repositoryImpl that you wanna use
 *
 */

interface CharityApi {
    @GET("charity/list")
    suspend fun getGifts(): Response<List<CharityModel>>

    @GET("charity/list")
    suspend fun getGiftsFirstPage(): Response<List<CharityModel>>
}