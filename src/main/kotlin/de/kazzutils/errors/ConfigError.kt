package de.kazzutils.errors

class ConfigError(message: String, cause: Throwable) : Error(message, cause)