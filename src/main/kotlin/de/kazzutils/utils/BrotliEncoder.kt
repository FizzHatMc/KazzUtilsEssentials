package de.kazzutils.utils

import io.ktor.client.plugins.compression.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.CoroutineScope
import org.brotli.dec.BrotliInputStream

object BrotliEncoder: ContentEncoder {
    override val name: String = "br"

    override fun CoroutineScope.decode(source: ByteReadChannel): ByteReadChannel {
        val bombChecker = DecompressionBombChecker(100)
        val wrapped = bombChecker.wrapInput(source.toInputStream())

        return bombChecker.wrapOutput(
            BrotliInputStream(wrapped)
        ).toByteReadChannel()
    }

    override fun CoroutineScope.encode(source: ByteReadChannel): ByteReadChannel = throw UnsupportedOperationException("Cannot encode Brotli")
}