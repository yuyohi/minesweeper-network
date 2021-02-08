import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path


interface PlayerDBService {
    @GET("users.json")
    fun get(): Call<Map<String, Player>>

    @PUT("users/{userName}.json")
    fun put(@Path("userName") userName: String, @Body player: Player): Call<Player>
}