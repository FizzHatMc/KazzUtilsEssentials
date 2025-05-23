import com.google.gson.Gson
import com.google.gson.JsonObject
import de.kazzutils.KazzUtils
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

object HypixelAPI {

    private const val BASE_URL = "https://api.hypixel.net/v2"
    private var apiKey: String? = KazzUtils.config.apiKey // Store the API key
    private val logger: Logger = LogManager.getLogger("HypixelAPI")

    // Use a thread pool to avoid freezing the game.
    private val threadPool: ExecutorService = Executors.newFixedThreadPool(4) // Or a number that suits your needs

    // Initialize the API key. This should be set early in your mod's initialization.
    fun initialize(key: String) {
        apiKey = key
    }

    // Helper function to make the HTTP request and handle errors
    private fun makeHttpRequest(url: URL): JsonObject {
        val connection = url.openConnection() as HttpURLConnection
        try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000 // 5 seconds timeout
            connection.readTimeout = 5000    // 5 seconds timeout

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = reader.readLine()
                reader.close()
                return Gson().fromJson(response, JsonObject::class.java)
            } else {
                // Handle HTTP errors more gracefully. Include the error message from Hypixel if available.
                val errorResponse = if (connection.errorStream != null) {
                    BufferedReader(InputStreamReader(connection.errorStream)).readLine() ?: "No error message"
                } else {
                    "No error stream"
                }
                logger.error("Hypixel API Error: $responseCode - $errorResponse for URL: $url") // Log the error with the URL
                throw Exception("Hypixel API request failed with code $responseCode.  Error details: $errorResponse")
            }
        } catch (e: Exception) {
            // Wrap the exception to provide more context.
            logger.error("Error during Hypixel API request to URL: $url", e) // Log the error with the URL and exception
            throw RuntimeException("Error during Hypixel API request: ${e.message}", e)
        } finally {
            connection.disconnect()
        }
    }

    /**
     * Makes a request to the Hypixel API asynchronously.  This prevents the game from freezing.
     *
     * @param endpoint The API endpoint to call (e.g., "player").
     * @param parameters A map of query parameters (e.g., "uuid" to the player's UUID).
     * @param callback A function to be called when the API request is complete.  This function will
     * receive either the JsonObject or an error message (as a String).
     * @return A Future representing the ongoing task.  You can use this to check if the task is done,
     * but you should not call get() on it, as that will block the main thread.
     * Returns null if the API key is not initialized.
     */
    fun getAsync(
        endpoint: String,
        parameters: Map<String, String>,
        callback: (Result<JsonObject>) -> Unit
    ): Future<*>? {
        val key = apiKey
        if (key == null) {
            callback(Result.failure(RuntimeException("Hypixel API key is not initialized. Call HypixelAPI.initialize() first.")))
            return null
        }

        val urlBuilder = StringBuilder("$BASE_URL/$endpoint?key=$key")
        parameters.forEach { (key, value) ->
            urlBuilder.append("&$key=$value")
        }
        val url = URL(urlBuilder.toString())

        // Submit the task to the thread pool.
        return threadPool.submit {
            try {
                val result = makeHttpRequest(url)
                callback(Result.success(result))
            } catch (e: Exception) {
                callback(Result.failure(e)) // Pass the exception to the callback
            }
        }
    }

    /**
     * Makes a request to the Hypixel API.
     *
     * @param endpoint The API endpoint to call (e.g., "player").
     * @param parameters A map of query parameters (e.g., "uuid" to the player's UUID).
     * @return A JsonObject representing the API response.
     * @throws RuntimeException if the API key is not initialized or if the request fails.
     */
    fun get(endpoint: String, parameters: Map<String, String>): JsonObject {
        val key = apiKey
        if (key == null) {
            throw RuntimeException("Hypixel API key is not initialized. Call HypixelAPI.initialize() first.")
        }

        val urlBuilder = StringBuilder("$BASE_URL/$endpoint?key=$key")
        parameters.forEach { (key, value) ->
            urlBuilder.append("&$key=$value")
        }

        val url = URL(urlBuilder.toString())
        return makeHttpRequest(url)
    }
}
