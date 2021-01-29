package uz.appme.ussd.model

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*
import uz.appme.ussd.model.data.DataResponse
import uz.appme.ussd.model.data.Device
import uz.appme.ussd.model.data.Token

interface UssdApi {

    @POST("device")
    fun auth(@Body device: Device): Single<Response<Token>>

    @PUT("device")
    fun updateVersion(@Body device: Device): Single<Response<Any>>

    @GET("data")
    fun getData(): Single<DataResponse>

}