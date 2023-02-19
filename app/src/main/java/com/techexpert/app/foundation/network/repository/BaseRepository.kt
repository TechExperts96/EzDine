package com.techexpert.app.foundation.network.repository

import com.techexpert.app.foundation.network.interfaces.RBService
import javax.inject.Inject

/**
 * This repository class will be used for all Instant Transfer API calls.
 */
class BaseRepository @Inject constructor(private val rbService: RBService) {
    suspend fun getCarousels(all: String) = rbService.getCarousels(all)
}
