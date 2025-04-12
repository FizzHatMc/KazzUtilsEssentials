package de.kazzutils.utils.randomutils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object JsonUtils {

    fun saveMapToFile(filePath: String, key: String, newMap: Map<String, Int>) {
        val file = File(filePath)
        val json = Json { prettyPrint = true }

        // Read existing data
        val existingData: MutableMap<String, Map<String, Int>> = if (file.exists()) {
            val jsonString = file.readText()
            json.decodeFromString<MutableMap<String, Map<String, Int>>>(jsonString)
        } else {
            mutableMapOf()
        }

        // Add or update the key with the new map
        existingData[key] = newMap

        // Write the updated data back to the file
        val jsonString = json.encodeToString(existingData)
        file.writeText(jsonString)
    }

    fun loadMapFromFile(filePath: String, key: String): MutableMap<String, Int> {
        val file = File(filePath)
        if (!file.exists()) return mutableMapOf()

        val json = Json { prettyPrint = true }
        val existingData: Map<String, Map<String, Int>> = json.decodeFromString(file.readText())

        // Return the map corresponding to the given key as a MutableMap or an empty MutableMap if it doesn't exist
        return existingData[key]?.toMutableMap() ?: mutableMapOf()
    }

}