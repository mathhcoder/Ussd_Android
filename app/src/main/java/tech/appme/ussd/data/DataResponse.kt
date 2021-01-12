package tech.appme.ussd.data

import java.lang.Package

data class DataResponse (
    val proveders : List<Provider> = emptyList() ,
    val tariffs: List<Tariff> = emptyList(),
    val categories: List<Category> = emptyList(),
    val packages: List<tech.appme.ussd.data.Package> = emptyList(),
    val services: List<Service> = emptyList(),
    val contacts: Contact? = null
)