package uz.appme.ussd.io

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*
import uz.appme.ussd.data.DataResponse
import uz.appme.ussd.data.Device
import uz.appme.ussd.data.Token

interface UssdApi {

    @POST("device")
    fun auth(@Body device: Device): Single<Response<Token>>

    @PUT("device")
    fun updateVersion(@Body device: Device): Single<Response<Any>>

    @GET("data")
    fun getData(): Single<DataResponse>

}