package com.capstone.invoicemanager

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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
import java.util.Locale

class EditInvoiceActivity : AppCompatActivity() {
    private lateinit var retrofit: Retrofit
    val mainScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private lateinit var tvSingleDate: TextView
    private var selectedDate: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_invoice)
        retrofit = RetrofitClient.create()
        val crud = retrofit.create(CrudApp::class.java)

        val cName = findViewById<EditText>(R.id.editTextText20)
        val amount = findViewById<EditText>(R.id.editTextText23)
        val etDescription = findViewById<EditText>(R.id.editTextText3)

        val invoiceId = getSharedPreferences("invoice_data", MODE_PRIVATE).getInt("invoice_id", -1)
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)
        val tvSingleDate = findViewById<TextView>(R.id.tv_pick_date)
        this.tvSingleDate = tvSingleDate


        //setting the toolbar
        val toolbar = findViewById<Toolbar>(R.id.custom_toolbar)
        val titleTextView: TextView = findViewById(R.id.titlebar)
        toolbar.title = ""
        titleTextView.text = "Edit Invoice"

        mainScope.launch {
            val response = crud.getSingleInvoice(invoiceId)
            if (response.isSuccessful) {
                val invoice: Invoice = response.body() ?: Invoice()
                Log.i("@SingleInvoice","list is :$invoice")
                Log.d("@descheck",invoice.description)
                cName.setText(invoice.clientName)
                amount.setText(invoice.amount.toString())
                val cdate = invoice.invoiceDate.substring(0, 10)
                tvSingleDate.setText(cdate)
                etDescription.setText(invoice.description)
                Log.d("@descheck",invoice.description)


            }
            else {
                Log.e("InvoiceListActivity", "Error fetching invoice: ${response.code()}")
            }



        }

        val clickListener = View.OnClickListener {
            showDatePickerDialog()
        }
        linearLayout.setOnClickListener(clickListener)

        //getting data to send
        findViewById<Button>(R.id.editbutton).setOnClickListener{
            mainScope.launch {
                val cName = findViewById<EditText>(R.id.editTextText20).text.toString()
                val amount = findViewById<EditText>(R.id.editTextText23).text.toString()
                val etDescription = findViewById<EditText>(R.id.editTextText3).text.toString()
                val invoiceDate = findViewById<TextView>(R.id.tv_pick_date).text.toString()

                if (cName.isEmpty() || amount.isEmpty()|| invoiceDate.equals("Select")) {
                    Toast.makeText(this@EditInvoiceActivity, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
                }
                else if (cName.length < 5) {
                    Toast.makeText(this@EditInvoiceActivity, "Client name must be at least 5 characters!", Toast.LENGTH_SHORT).show()
                }
                else if(amount.toDouble()<3000) {
                    Toast.makeText(this@EditInvoiceActivity, "Amount must be greater than 3000", Toast.LENGTH_SHORT).show()

                }
                else {


                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())  // Format for date only
                    val parsedDate = dateFormat.parse(invoiceDate)
                    val formattedDate = dateFormat.format(parsedDate)

                    val invoice = Invoice (
                        userId =  getSharedPreferences("user_data", MODE_PRIVATE).getInt("user_id",-1),
                        clientName = cName,
                        amount = amount.toDouble(),
                        invoiceDate = formattedDate,
                        description = etDescription
                    )
                    Log.i("@create list:","$invoice")

                    //connection
                    val response = crud.editInvoice(invoiceId,invoice)

                    if (response.isSuccessful) {


                        Toast.makeText(this@EditInvoiceActivity, "Invoice Edited", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this@EditInvoiceActivity, InvoiceListActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this@EditInvoiceActivity, "Invoice edit error", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            }

    }


}

    private fun showDatePickerDialog() {

        val maxDate = Calendar.getInstance().timeInMillis  // Get current time in milliseconds


        val datePickerDialog = DatePickerDialog(this,
            { view, year, monthOfYear, dayOfMonth ->
                val selectedDate = "$year-${monthOfYear + 1}-$dayOfMonth"
                updateDateView(selectedDate) // Update text and (optional) store the date
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = maxDate
        datePickerDialog.show()
    }

    fun updateDateView(selectedDate: String) {
        tvSingleDate.text = selectedDate
        this.selectedDate = selectedDate
    }
}