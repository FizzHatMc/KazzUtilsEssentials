package de.kazzutils.event

import de.kazzutils.data.enumClass.EventPriority
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

suspend fun <T : Event> post(event: T) =
    EventPriority.Highest.post(event)

fun <T : Event> postSync(event: T) =
    runBlocking {
        post(event)
    }

suspend fun <T : CancellableEvent> postCancellable(event: T) =
    coroutineScope {
        EventPriority.Highest.post(event)
        return@coroutineScope event.cancelled
    }

fun <T : CancellableEvent> postCancellableSync(event: T) =
    runBlocking {
        postCancellable(event)
    }

suspend inline fun <reified T : Event> on(priority: EventPriority = EventPriority.Normal, noinline block: suspend (T) -> Unit) =
    priority.subscribe<T>(block)

suspend inline fun <reified T : Event> await(priority: EventPriority = EventPriority.Normal) =
    priority.flow.filterIsInstance<T>().first()

suspend inline fun <reified T : Event> await(repetitions: Int, priority: EventPriority = EventPriority.Normal) = run {
    assert(repetitions >= 1) { "Expected repetitions to be at least 1 but received $repetitions" }
    var counter = 0
    priority.flow.filterIsInstance<T>().first { counter++ == repetitions }
}