package com.example.calcnumber

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var edtWidth: EditText
    private lateinit var edtHeight: EditText
    private lateinit var edtLength: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView

    companion object {
        private const val STATE_RESULT = "state_result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menampilkan tampilan dari activity_main.xml
        setContentView(R.layout.activity_main)

        edtWidth = findViewById(R.id.edt_width)
        edtHeight = findViewById(R.id.edt_height)
        edtLength = findViewById(R.id.edt_length)
        btnCalculate = findViewById(R.id.btn_calculate)
        tvResult = findViewById(R.id.tv_result)

        btnCalculate.setOnClickListener(this)

        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(STATE_RESULT) as String
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_calculate) {
            // .trim() used for ignoring space between characters
            val inputLength = edtLength.text.toString().trim()
            val inputWidth = edtWidth.text.toString().trim()
            val inputHeight = edtHeight.text.toString().trim()

            var isEmptyFields = false
            var isInvalidDouble = false

            when {
                inputLength.isEmpty() -> {
                    isEmptyFields = true
                    edtLength.error = "Can't Empty!"
                }
                inputWidth.isEmpty() -> {
                    isEmptyFields = true
                    edtWidth.error = "Can't Empty!"
                }
                inputHeight.isEmpty() -> {
                    isEmptyFields = true
                    edtHeight.error = "Can't Empty!"
                }
            }

            val width = toDouble(inputWidth)
            val length = toDouble(inputLength)
            val height = toDouble(inputHeight)

            when {
                width == null -> {
                    isInvalidDouble = true
                    edtWidth.error = "Invalid Value!"
                }
                length == null -> {
                    isInvalidDouble = true
                    edtLength.error = "Invalid Value!"
                }
                height == null -> {
                    isInvalidDouble = true
                    edtHeight.error = "Invalid Value!"
                }
            }

            if (!isEmptyFields && !isInvalidDouble) {
                val volume = length as Double * width as Double * height as Double
                tvResult.text = volume.toString()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, tvResult.text.toString())
    }

    private fun toDouble(str: String): Double? {
        return try {
            str.toDouble()
        } catch (e: NumberFormatException) {
            null
        }
    }
}