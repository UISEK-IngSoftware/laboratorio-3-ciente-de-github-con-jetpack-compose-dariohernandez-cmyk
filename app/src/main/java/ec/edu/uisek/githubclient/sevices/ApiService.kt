package ec.edu.uisek.githubclient.services

import ec.edu.uisek.githubclient.models.Repository
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("user/repos")
    suspend fun getRepositories(
        @Query("sort") sort: String = "created",
        @Query("direction") direction: String = "desc"
    ): List<Repository>
}
