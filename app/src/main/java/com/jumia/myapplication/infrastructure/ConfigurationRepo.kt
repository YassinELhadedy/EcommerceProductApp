package com.jumia.myapplication.infrastructure

import com.jumia.myapplication.domain.GetSuspended
import com.jumia.myapplication.infrastructure.dto.AppConfigurationResponse
import com.jumia.myapplication.infrastructure.dto.JumConfiguration
import com.jumia.myapplication.infrastructure.dto.JumProduct
import javax.inject.Inject

class ConfigurationRepo @Inject constructor(private val service: JumiaWebService) :
    GetSuspended<JumConfiguration> {
    override suspend fun get(id: Int): JumConfiguration = SafeApiCaller.jsonToPojo {
        service.getConfiguration()
    }
}