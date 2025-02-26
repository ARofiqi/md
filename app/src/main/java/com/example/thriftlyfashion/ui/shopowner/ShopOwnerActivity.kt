package com.example.thriftlyfashion.ui.shopowner

import ManageProductAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thriftlyfashion.R
import com.example.thriftlyfashion.remote.model.ManageProduct
import com.example.thriftlyfashion.ui.AddProductOwner.AddProductActivity
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class ShopOwnerActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var btnAddProduct: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ManageProductAdapter
    private var productList = mutableListOf<ManageProduct>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_owner)

        btnBack = findViewById(R.id.id_btnBack)
        btnAddProduct = findViewById(R.id.imageView20)
        recyclerView = findViewById(R.id.manage_product)

        val productLayoutManager = FlexboxLayoutManager(this)
        productLayoutManager.flexWrap = FlexWrap.WRAP
        productLayoutManager.flexDirection = FlexDirection.ROW
        recyclerView.layoutManager = productLayoutManager

        productList = getSampleProducts().toMutableList()

        productAdapter = ManageProductAdapter(this, productList, object : ManageProductAdapter.OnProductClickListener {
            override fun onDeleteClick(product: ManageProduct) {
                val position = productList.indexOf(product)
                if (position != -1) {
                    productList.removeAt(position)
                    productAdapter.notifyItemRemoved(position)
                    Toast.makeText(this@ShopOwnerActivity, "Hapus produk: ${product.name}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onEditClick(product: ManageProduct) {
//                val intent = Intent(this@ShopOwnerActivity, EditProductActivity::class.java)
//                intent.putExtra("PRODUCT_DATA", product) // Passing product as Serializable object
//                startActivity(intent)
            }
        }
        )
        recyclerView.adapter = productAdapter

        btnBack.setOnClickListener {
            finish()
        }

        btnAddProduct.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getSampleProducts(): List<ManageProduct> {
        return listOf(
            ManageProduct(
                id = "1",
                name = "Kemeja Batik",
                category = "Pakaian",
                size = "L",
                color = "Coklat",
                quantity = 10,
                price = 120000.0,
                images = listOf("https://example.com/image1.jpg"),
                status = "Tersedia"
            ),
            ManageProduct(
                id = "2",
                name = "Sepatu Sneakers",
                category = "Sepatu",
                size = "42",
                color = "Putih",
                quantity = 5,
                price = 350000.0,
                images = listOf("https://example.com/image2.jpg"),
                status = "Habis"
            ),
            ManageProduct(
                id = "3",
                name = "Tas Kulit",
                category = "Aksesoris",
                size = "Medium",
                color = "Hitam",
                quantity = 3,
                price = 450000.0,
                images = listOf("https://example.com/image3.jpg"),
                status = "Pre-Order"
            ),
            ManageProduct(
                id = "4",
                name = "Jam Tangan Pria",
                category = "Aksesoris",
                size = "Adjustable",
                color = "Perak",
                quantity = 8,
                price = 800000.0,
                images = listOf("https://example.com/image4.jpg"),
                status = "Tersedia"
            ),
            ManageProduct(
                id = "5",
                name = "Celana Jeans",
                category = "Pakaian",
                size = "32",
                color = "Biru",
                quantity = 15,
                price = 200000.0,
                images = listOf("https://example.com/image5.jpg"),
                status = "Diskon"
            ),
            ManageProduct(
                id = "6",
                name = "Topi Baseball",
                category = "Aksesoris",
                size = "Free Size",
                color = "Merah",
                quantity = 0,
                price = 80000.0,
                images = listOf("https://example.com/image6.jpg"),
                status = "Habis"
            ),
            ManageProduct(
                id = "7",
                name = "Smartphone Case",
                category = "Gadget",
                size = "Universal",
                color = "Transparan",
                quantity = 50,
                price = 50000.0,
                images = listOf("https://example.com/image7.jpg"),
                status = "Tersedia"
            ),
            ManageProduct(
                id = "8",
                name = "Laptop Backpack",
                category = "Aksesoris",
                size = "Large",
                color = "Abu-abu",
                quantity = 7,
                price = 350000.0,
                images = listOf("https://example.com/image8.jpg"),
                status = "Tersedia"
            ),
            ManageProduct(
                id = "9",
                name = "Sweater Hoodie",
                category = "Pakaian",
                size = "XL",
                color = "Hitam",
                quantity = 12,
                price = 250000.0,
                images = listOf("https://example.com/image9.jpg"),
                status = "Tersedia"
            ),
            ManageProduct(
                id = "10",
                name = "Sarung Tangan Motor",
                category = "Aksesoris",
                size = "M",
                color = "Hitam",
                quantity = 20,
                price = 90000.0,
                images = listOf("https://example.com/image10.jpg"),
                status = "Diskon"
            )
        )
    }
}
