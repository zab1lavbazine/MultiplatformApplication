package com.example.multiplatformapp.model

enum class ServiceStatus {
    UNKNOWN,
    UP,
    DOWN,
}


data class MonitoredService (
    val id: Long,
    val name: String,
    val url: String,
    val timeoutMills: Long,
    val intervalMills: Long,
    val status: ServiceStatus,
    val responseTimeMillis: Long? = null,
    val lastCheckedAt: Long? = null
)


data class CheckResult (
    val status: ServiceStatus,
    val responseTimeMillis: Long?,
)