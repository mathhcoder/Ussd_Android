package uz.appme.ussd.model.data

data class Device(
    val os: String,
    val appVersion: String,
    val osVersion: String,
    val model : String,
    val dealer : String,
)