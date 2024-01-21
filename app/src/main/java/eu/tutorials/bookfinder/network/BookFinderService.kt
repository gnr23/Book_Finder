package eu.tutorials.bookfinder.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//base URL for Books API
private const val BASE_URL = "https://www.googleapis.com/books/v1"
//Moshi object  that Retrofit will be using, making sure to add the Kotlin adapter
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
//Retrofit object that generates the implementation  of the Service interface
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
//Service interface that Retrofit will generate an implementation for.
interface BookFinderApiService  {
    @GET("volumes?")
    suspend fun getDetails(
        @Query("q")searchString: String,
        @Query("maxResults") maxResults:String,
        @Query("printType")printType:String
    ):BookResponse
}
object BookFinderApi {
    val retrofitService: BookFinderApiService by lazy { retrofit.create(BookFinderApiService::class.java)
    }
}