package de.kazzutils.core

import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.event.TickEvent
import de.kazzutils.event.await
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.Executor


val mcDispatcher = (mc as Executor).asCoroutineDispatcher()
val mcScope = CoroutineScope(mcDispatcher) + SupervisorJob() + CoroutineName("KazzUtils MC")

val Dispatchers.MC
    get() = mcDispatcher

private object Tick : CoroutineScope {
    @OptIn(DelicateCoroutinesApi::class)
    val dispatcher = newFixedThreadPoolContext(5, "KazzUtils Tick")
    override val coroutineContext = dispatcher + SupervisorJob()
}

fun tickTimer(ticks: Int, repeats: Boolean = false, register: Boolean = true, task: () -> Unit) =
    Tick.launch(start = if (register) CoroutineStart.DEFAULT else CoroutineStart.LAZY) {
        tickTask(ticks, repeats, task).collect()
    }

fun <T> tickTask(ticks: Int, repeats: Boolean = false, task: () -> T) =
    flow {
        do {
            await<TickEvent>(ticks)
            emit(withContext(Dispatchers.MC) {
                task()
            })
        } while (repeats)
    }.catch {e ->
        if (e is RuntimeException) {
            e.printStackTrace()
        } else throw e
    }.flowOn(Tick.dispatcher)





