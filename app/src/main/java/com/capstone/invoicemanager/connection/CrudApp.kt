package com.capstone.invoicemanager.connection

import com.capstone.invoicemanager.datas.Invoice
import com.capstone.invoicemanager.datas.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CrudApp {
    @GET("api/users")
    suspend fun getUser(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<Int>

    @POST("api/users")
    suspend fun createUser(@Body user: User): Response<Int>

    @GET("api/invoices/user/{userId}")
    suspend fun getInvoice(@Path("userId") userId: Int): Response<List<Invoice>>

    @GET("api/invoices/{invoiceId}")
    suspend fun getSingleInvoice(@Path("invoiceId") invoiceId: Int): Response<Invoice>

    @DELETE("api/invoices/{id}")
    suspend fun deleteInvoice(@Path("id") id: Int): Response<Int>

    @POST("api/invoices")
    suspend fun createInvoice(@Body invoice: Invoice): Response<Void>
    suspend fun editInvoice(invoiceId: Int, @Body invoice: Invoice): Response<Void>

}