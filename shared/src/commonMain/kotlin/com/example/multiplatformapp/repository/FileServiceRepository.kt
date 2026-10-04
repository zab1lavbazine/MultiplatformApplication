package com.example.multiplatformapp.repository

import com.example.multiplatformapp.model.MonitoredService
import java.io.File

class FileServiceRepository(
    private val file: File
) : ServiceRepository {
    override suspend fun findAll(): List<MonitoredService> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(service: MonitoredService) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun update(service: MonitoredService) {
        TODO("Not yet implemented")
    }
}