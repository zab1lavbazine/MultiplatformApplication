package com.example.multiplatformapp.viewmodel

import com.example.multiplatformapp.model.MonitoredService
import com.example.multiplatformapp.model.ServiceStatus
import com.example.multiplatformapp.repository.ServiceRepository
import com.example.multiplatformapp.service.ServiceChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class MonitorViewModel(
    private val checker: ServiceChecker,
) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _services = MutableStateFlow<List<MonitoredService>>(emptyList())

    private val monitoringJobs = mutableMapOf<Long, Job>()

    val services: StateFlow<List<MonitoredService>>
        get() = _services

    fun addService(
        name: String,
        url: String,
        timeoutMillis : Long,
        intervalMillis: Long,
    ) {
        val service = MonitoredService(
            id = System.currentTimeMillis(),
            name = name,
            url = url,
            status = ServiceStatus.UNKNOWN,
            timeoutMills = timeoutMillis,
            intervalMills = intervalMillis,
        )

        _services.update {
            it + service
        }

        startMonitoring (service.id)
    }

    fun deleteServiceById( serviceId: Long ) {
        scope.launch {
            stopMonitoring(serviceId)
            // removing from _services
            _services.update { services ->
                services.filterNot { it.id == serviceId }
            }
        }
    }

    private suspend fun stopMonitoring(serviceId: Long) {
        // joining, cancelling and removing service to monitor
        monitoringJobs.remove(serviceId)?.cancelAndJoin()
    }

    fun checkService(serviceId: Long) {
        scope.launch {
            performCheck(serviceId)
        }
    }


    private fun startMonitoring(serviceId: Long) {
        monitoringJobs[serviceId]?.cancel()

        monitoringJobs[serviceId] = scope.launch {
            while (isActive) {
                performCheck(serviceId)

                val service = _services.value.firstOrNull {
                    it.id == serviceId
                } ?: break

                delay(service.intervalMills.milliseconds)
            }
        }
    }


    private suspend fun performCheck(serviceId: Long) {
        val service = _services.value.firstOrNull {
            it.id == serviceId
        } ?: return

        val result = checker.check(service)

        if (_services.value.none { it.id == serviceId }) {
            return
        }

        _services.update { services ->
            services.map {
                if (it.id == serviceId) {
                    it.copy(
                        status = result.status,
                        responseTimeMillis = result.responseTimeMillis,
                        lastCheckedAt = System.currentTimeMillis()
                    )
                } else {
                    it
                }
            }
        }
    }
}