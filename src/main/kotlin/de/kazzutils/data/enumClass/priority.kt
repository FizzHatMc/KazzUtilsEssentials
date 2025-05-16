package de.kazzutils.data.enumClass


import de.kazzutils.event.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


enum class EventPriority {
    Lowest {
        override val next: EventPriority = this

        override suspend fun <T : Event> post(event: T) {
            flow.emit(event)
        }
    },
    Low {
        override val next: EventPriority = Lowest
    },
    Normal {
        override val next: EventPriority = Low
    },
    High {
        override val next: EventPriority = Normal
    },
    Highest {
        override val next: EventPriority = High
    };

    @PublishedApi
    internal val flow: MutableSharedFlow<Event> = MutableSharedFlow()
    internal abstract val next: EventPriority

    @PublishedApi
    internal suspend inline fun <reified T : Event> subscribe(noinline block: suspend (T) -> Unit) =
        flow.filterIsInstance<T>().onEach(block).launchIn(CoroutineScope(currentCoroutineContext()))

    internal open suspend fun <T : Event> post(event: T) {
        flow.emit(event)
        if (!event.continuePropagation()) return
        next.post(event)
    }
}