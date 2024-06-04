package com.capstone.invoicemanager.datas

import java.util.Date


data class Invoice (
    var invoiceId: Int = 0,
    var userId: Int = 0,
    var clientName: String = "",
    var amount: Double = 0.0,
    var invoiceDate:String = "" , // Initialize with epoch time (Jan 1, 1970)
    var description: String = "",
)

