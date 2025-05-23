import de.kazzutils.KazzUtils
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

@Serializable
data class BazaarResponse(
    val success: Boolean,
    val lastUpdated: Long,
    val products: Map<String, Product>
)

@Serializable
data class Product(
    val product_id: String,
    val sell_summary: List<OrderSummary> = emptyList(),
    val buy_summary: List<OrderSummary> = emptyList(),
    val quick_status: QuickStatus
)

@Serializable
data class OrderSummary(
    val amount: Int,
    val pricePerUnit: Double,
    val orders: Int
)

@Serializable
data class QuickStatus(
    val productId: String,
    val sellPrice: Double,
    val sellVolume: Int,
    val sellMovingWeek: Long,
    val sellOrders: Int,
    val buyPrice: Double,
    val buyVolume: Int,
    val buyMovingWeek: Long,
    val buyOrders: Int
)

fun fetchProduct(productId: String): JsonObject? = runBlocking {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    try {
        val fullJson: JsonObject = client.get("https://api.hypixel.net/v2/skyblock/bazaar").body()
        val products = fullJson["products"]?.jsonObject
        products?.get(productId)?.jsonObject
    } catch (e: Exception) {
        println("Error: ${e.message}")
        null
    } finally {
        client.close()
    }
}
