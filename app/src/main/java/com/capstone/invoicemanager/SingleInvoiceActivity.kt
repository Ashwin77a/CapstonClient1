package com.capstone.invoicemanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.capstone.invoicemanager.connection.CrudApp
import com.capstone.invoicemanager.connection.RetrofitClient
import com.capstone.invoicemanager.datas.Invoice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.text.SimpleDateFormat


class SingleInvoiceActivity : AppCompatActivity() {
    val mainScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private lateinit var retrofit: Retrofit
    private lateinit var crudApp: CrudApp

    fun injectCrudApp(crudApp: CrudApp) {
        this.crudApp = crudApp
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_invoice)
        //setting the toolbar
        val toolbar = findViewById<Toolbar>(R.id.custom_toolbar)
        val titleTextView: TextView = findViewById<TextView>(R.id.titlebar)
        toolbar.title = ""
        titleTextView.text = "Details"

        val cName = findViewById<TextView>(R.id.tv_clientName)
        val amount = findViewById<TextView>(R.id.tvSingle_amount)
        val iDate = findViewById<TextView>(R.id.tvSingle_date)
        val description = findViewById<TextView>(R.id.tv_scrollable_text)


        //for connection
        retrofit = RetrofitClient.create()
        val crud = retrofit.create(CrudApp::class.java)
        crudApp = retrofit.create(CrudApp::class.java)



        val invoiceId = getSharedPreferences("invoice_data", MODE_PRIVATE).getInt("invoice_id", -1)

        mainScope.launch {
            val response = crudApp.getSingleInvoice(invoiceId)
            if (response.isSuccessful) {
                val invoice: Invoice = response.body() ?: Invoice()
                Log.i("@SingleInvoice","list is :$invoice")
                Log.d("@descheck",invoice.description)
                cName.text = invoice.clientName
                amount.text = invoice.amount.toString()
                val formattedDate = invoice.invoiceDate
                iDate.text = formattedDate
                description.text = invoice.description
                Log.d("@descheck",invoice.description)


            }
            else {
                Log.e("InvoiceListActivity", "Error fetching invoice: ${response.code()}")
            }



        }


        //delete button click
        findViewById<Button>(R.id.deleteButton).setOnClickListener {
            showDeleteConfirmationDialog()
        }

    }



    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Invoice")
        builder.setMessage("Do you want to delete this invoice?")
        val invoiceId = getSharedPreferences("invoice_data", MODE_PRIVATE).getInt("invoice_id", -1)

        builder.setPositiveButton("Yes") { dialog, which ->
            // Handle positive button click
            //for connection
            mainScope.launch {
                retrofit = RetrofitClient.create()
                val crud = retrofit.create(CrudApp::class.java)
                crudApp = retrofit.create(CrudApp::class.java)

                val response = crudApp.deleteInvoice(invoiceId)
                if(response.isSuccessful) {
                    Toast.makeText(this@SingleInvoiceActivity, "Deleted", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SingleInvoiceActivity, InvoiceListActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this@SingleInvoiceActivity, "Deletion Error!", Toast.LENGTH_SHORT).show()

                }
            }


        }

        builder.setNegativeButton("No") { dialog, which ->
            // Handle negative button click (dialog dismissed)
        }

        builder.create().show()
    }




}

