package de.kazzutils.features.mining.crystalhollows

import HypixelAPI.getAsync
import de.kazzutils.KazzUtils
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.utils.RenderUtils
import de.kazzutils.utils.chat.ChatUtils
import de.kazzutils.utils.graphics.ScreenRenderer
import com.google.gson.JsonObject
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.concurrent.Future

object GemstoneProfit{
    var pristineProc = 0
    var flawedAmmount = 0
    var coinHour = ""
    var trackedGem = ""
    var gemPrice = 0.0


    private fun getBazaarItemAsync(productId: String, callback: (Result<JsonObject?>) -> Unit): Future<*>? {
        return getAsync("skyblock/bazaar", emptyMap()) { result ->
            val extracted = result.mapCatching { response ->
                val products = response["products"]?.asJsonObject
                products?.get(productId)?.asJsonObject
            }
            callback(extracted)
        }
    }

    fun calculateMCoinsPerHourFormatted(
        itemCount: Int,
        pricePerItem: Double,
        startTimeMs: Long,
        currentTimeMs: Long
    ): String {
        val totalCoins = itemCount * pricePerItem
        val timeElapsedHours = (currentTimeMs - startTimeMs) / 3600000.0
        if (timeElapsedHours == 0.0) return "0.00 m/hr"
        val coinsPerHour = totalCoins / timeElapsedHours
        return "%.2f m/hr".format(coinsPerHour / 1_000_000.0)
    }

    fun formatElapsedTime(startTimeMs: Long, currentTimeMs: Long): String {
        val totalSeconds = ((currentTimeMs - startTimeMs) / 1000).toInt()

        return when {
            totalSeconds < 60 -> "${totalSeconds}s"
            totalSeconds < 3600 -> {
                val minutes = totalSeconds / 60
                val seconds = totalSeconds % 60
                "%d:%02dm".format(minutes, seconds)
            }
            else -> {
                val hours = totalSeconds / 3600
                val minutes = (totalSeconds % 3600) / 60
                "%d:%02dh".format(hours, minutes)
            }
        }
    }


    fun checkPrice(){
        if(trackedGem != "")
        //ChatUtils.messageToChat(getBazaarItemSync("FINE_${gem.uppercase()}_GEM").asString)
        getBazaarItemAsync("FLAWED_${trackedGem.uppercase()}_GEM") { result ->
            result.onSuccess { item ->
                val sellPrice = item
                    ?.getAsJsonObject("quick_status")
                    ?.get("sellPrice")
                    ?.asDouble

                if (sellPrice != null) {
                    gemPrice = sellPrice
                } else {
                    ChatUtils.messageToChat("Sell Price not found")
                }
            }.onFailure { error ->
                ChatUtils.messageToChat("Error fetching item: ${error.message}")
            }
        }
        coinHour = calculateMCoinsPerHourFormatted(flawedAmmount,gemPrice,starttime,System.currentTimeMillis())
    }


    var starttime = 0L
    private fun startTime(){
        starttime = System.currentTimeMillis()
    }

    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent) {
        //if (event.type.toInt() == 2 || !KazzUtils.config.gemstoneProfit) return

        val msg = ChatUtils.noColorCodes(event.message.formattedText)
        val gemPattern = Regex("""PRISTINE! You found . Flawed (\w+) Gemstone x(\d+)!""")
        val matchResult = gemPattern.find(msg)

        if (matchResult != null) {
            val (gemType, amountStr) = matchResult.destructured
            pristineProc++
            flawedAmmount += amountStr.toInt()
            trackedGem = gemType
            if (starttime == 0L) {
                startTime()
            }
        }
    }

    class GemstoneProfitDisplay: GuiElement("Gemstone Profit",x=400,y=80){
        override fun render() {
            if(!KazzUtils.config.gemstoneProfit) return
            var displayText = """
                §4 Pristine Procs: ${pristineProc}
                §4 Flawed Gems: ${flawedAmmount}
                §4 Coins/hr: ${coinHour}
                §4 Tracked Time: ${formatElapsedTime(starttime, System.currentTimeMillis())}
                """.trimIndent()
            val lines = displayText.split('\n')
            RenderUtils.drawAllInList(this, lines)
        }

        override fun demoRender() {
            if(!KazzUtils.config.gemstoneProfit) return

            var displayText = """
                §4 Pristine Procs: 123
                §4 Flawed Gems: 123613
                §4 Coins/hr: 69.1M/hr
                §4 Tracked Time: 15
                """.trimIndent()
            val lines = displayText.split('\n')
            RenderUtils.drawAllInList(this, lines)
        }

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT * 7
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth("§4 Tools: X/4")

        override val toggled: Boolean
            get() = KazzUtils.config.downtime

        init {
            KazzUtils.guiManager.registerElement(this)
        }

    }
}