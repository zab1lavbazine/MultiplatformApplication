package com.example.multiplatformapp.repository

import com.example.multiplatformapp.model.MonitoredService

interface ServiceRepository {

    suspend fun findAll(): List<MonitoredService>

    suspend fun insert(service: MonitoredService)

    suspend fun deleteById(id: Long)

    suspend fun update(service: MonitoredService)
}