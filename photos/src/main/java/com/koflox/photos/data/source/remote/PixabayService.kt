package com.koflox.photos.data.source.remote

import com.koflox.photos.data.response.photos.PhotoListResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val PIXABAY_MIN_ITEMS_PER_PAGE = 3
private const val PIXABAY_MAX_ITEMS_PER_PAGE = 200

@Suppress("DeferredIsResult")
interface PixabayService {

    /**
     * Fetches list of photos
     * @param query - search term
     * @param category - search filter, places category used by default
     * @param page - page to get, default server's value is 1
     * @param itemPerPage - items per page, default server's value is 20
     * @param isSafeSearch - true to get images suitable for all ages, false otherwise
     */
    @GET(".")
    suspend fun getPhotos(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") itemPerPage: Int = PIXABAY_MIN_ITEMS_PER_PAGE,
        @Query("category") category: String = "places",
        @Query("safesearch") isSafeSearch: Boolean = true
    ): PhotoListResponse

}
