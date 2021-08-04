package com.jumia.myapplication.infrastructure

import com.jumia.myapplication.domain.GetSuspended
import com.jumia.myapplication.infrastructure.dto.AppConfigurationResponse
import javax.inject.Inject

class ConfigurationRepo @Inject constructor(private val service: JumiaWebService) :
    GetSuspended<AppConfigurationResponse> {
    override suspend fun get(id: Int): AppConfigurationResponse = SafeApiCaller.safeApiCall { service.getConfiguration()}
}