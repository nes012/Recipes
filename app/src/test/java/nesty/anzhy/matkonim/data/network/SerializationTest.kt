package nesty.anzhy.matkonim.data.network;

import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit
class SerializationTest {
    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FoodRecipesApi::class.java)

    private val remoteDataSource = RemoteDataSource(api)

    @Test
    fun `should fetch recipes correctly given 200 response`() {
        mockWebServer.enqueue(getMockResponse("recipes-response.json", 200))

        runBlocking {
            val queries: HashMap<String, String> = HashMap()
            val result = remoteDataSource.getRecipes(queries)

            assert(result.isSuccessful)
            assert(result.body()!!.results.isNotEmpty())

            val recipe = result.body()!!.results.first()
            assert(recipe.recipeId > 0)
        }
    }

    private fun getMockResponse(fileName: String, code: Int) : MockResponse {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val source = inputStream?.let { inputStream.source().buffer() }
        return MockResponse()
            .setResponseCode(code)
            .setBody(source!!.readString(StandardCharsets.UTF_8))
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

}