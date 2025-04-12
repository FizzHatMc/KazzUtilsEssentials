package de.kazzutils.utils.randomutils

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object DelayedExecutor {
    private val scheduler = Executors.newScheduledThreadPool(1)

    /**
     * Executes the given task after the specified delay in milliseconds.
     * @param delayMs The delay in milliseconds.
     * @param task The method to execute after the delay.
     */
    fun runDelayed(delayMs: Long, task: () -> Unit) {
        scheduler.schedule({
            task.invoke()
        }, delayMs, TimeUnit.MILLISECONDS)
    }
}
