package com.example.shopapp

object ProductData {
    private val productTitle = arrayOf(
            "MacBook Pro",
            "MacBook Air",
            "Dell XPS 13",
            "Surface Pro X",
            "Surface Pro 7",
            "HP Envy 13",
            "PixelBook Go",
            "Thinkpad X1",
            "Acer Swift 5",
            "ROG Zephyrus"
    )
    private val productVersion = arrayOf(
            "v2020",
            "v2019",
            "v7390",
            "Business",
            "Platinum",
            "x360",
            "vGa00536",
            "Gen 6",
            "v2018",
            "g14"
    )
    private val pricing = arrayOf(
            "$3,999",
            "$1,899",
            "$1,620",
            "$1,699",
            "$3,149",
            "$1,299",
            "$1,899",
            "$1,899",
            "$1,999",
            "$1,799"
    )
    private val imgItemPhoto = intArrayOf(
            R.drawable.product_1,
            R.drawable.product_2,
            R.drawable.product_3,
            R.drawable.product_4,
            R.drawable.product_5,
            R.drawable.product_6,
            R.drawable.product_7,
            R.drawable.product_8,
            R.drawable.product_9,
            R.drawable.product_10
    )

    val listData: ArrayList<Product>
        get() {
            val list = arrayListOf<Product>()
            for (position in productTitle.indices) {
                val product = Product()
                product.item = productTitle[position]
                product.version = productVersion[position]
                product.price = pricing[position]
                product.photo = imgItemPhoto[position]
                list.add(product)
            }
            return list
        }
}