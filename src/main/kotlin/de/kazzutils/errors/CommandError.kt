package de.kazzutils.errors

class CommandError(message: String, cause: Throwable) : Error(message, cause)