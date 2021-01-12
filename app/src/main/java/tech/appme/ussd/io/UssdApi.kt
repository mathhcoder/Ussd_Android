package tech.appme.ussd.io

import io.reactivex.Single
import retrofit2.http.GET
import tech.appme.ussd.data.DataResponse
import tech.appme.ussd.data.UpdateResponse
interface UssdApi {

    @GET("data")
    fun getData(): Single<DataResponse>

    @GET("update")
    fun getUpdate(): Single<UpdateResponse>

}