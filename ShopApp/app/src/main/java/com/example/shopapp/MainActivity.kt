package com.example.shopapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var rvProduct: RecyclerView
    private var list: ArrayList<Product> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvProduct = findViewById(R.id.rvProducts)
        rvProduct.setHasFixedSize(true)

        list.addAll(ProductData.listData)
        showRecyclerList()

        val btnDetailProduct : androidx.cardview.widget.CardView = findViewById(R.id.list_item)
        btnDetailProduct.setOnClickListener(this)
    }

    private fun showRecyclerList() {
        rvProduct.layoutManager = LinearLayoutManager(this)
        val productListAdapter = ProductListAdapter(list)
        rvProduct.adapter = productListAdapter
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.list_item -> {
                val moveIntent = Intent(this@MainActivity, DetailProduct::class.java)
                startActivity(moveIntent)
            }
        }
    }
}