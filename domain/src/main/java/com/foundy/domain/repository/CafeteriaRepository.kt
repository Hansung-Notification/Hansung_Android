package com.foundy.domain.repository

import com.foundy.domain.model.cafeteria.CafeteriaData

interface CafeteriaRepository {
    suspend fun getWeekly(): Result<CafeteriaData>
}