package uz.appme.ussd.io

import io.reactivex.Single
import retrofit2.http.GET
import uz.appme.ussd.data.DataResponse
import uz.appme.ussd.data.UpdateResponse

interface UssdApi {

    @GET("data")
    fun getData(): Single<DataResponse>

    @GET("update")
    fun getUpdate(): Single<UpdateResponse>

}