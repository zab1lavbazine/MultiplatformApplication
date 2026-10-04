package com.example.multiplatformapp.service


import com.example.multiplatformapp.model.CheckResult
import com.example.multiplatformapp.model.MonitoredService
import com.example.multiplatformapp.model.ServiceStatus
import io.ktor.client.HttpClient
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get



class ServiceChecker(
    private val client: HttpClient
) {

    suspend fun check (
        service: MonitoredService
    ): CheckResult {
        val startedAt = System.currentTimeMillis()

        return try {
            val response = client.get(service.url) {
                timeout {
                    requestTimeoutMillis = service.timeoutMills
                }
            }

            val elapsed = System.currentTimeMillis() - startedAt

            CheckResult(
                status = if (response.status.value in 200..299) {
                    ServiceStatus.UP
                } else {
                    ServiceStatus.DOWN
                },
                responseTimeMillis = elapsed
            )
        } catch (e : Exception) {
            CheckResult (
                status = ServiceStatus.DOWN,
                responseTimeMillis = null,
            )
        }
    }

    companion object {
        fun createServiceChecker () : ServiceChecker {
            return ServiceChecker(HttpClient())
        }
    }
}