package com.example.memeshareapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {

   private val  url = "https://meme-api.com/gimme"
    private lateinit var ivMeme:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ivMeme = findViewById(R.id.ivMeme)
        loadMeme()
    }

    private fun loadMeme(){
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val progressBar = findViewById<ProgressBar>(R.id.progreeBar)
        progressBar.visibility = View.VISIBLE


// Request a string response from the provided URL.
        val JsonObjectRequest = JsonObjectRequest(Request.Method.GET, this.url,null,
            { response ->
                Log.d("Result",response.toString())
//                Toast.makeText(this,"Looks Good",Toast.LENGTH_LONG).show()
                val url = response.getString("url")
                Glide.with(this).load(url).listener(object: RequestListener<Drawable>{

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                }).into(ivMeme)
            },
            Response.ErrorListener {
                Toast.makeText(this,"SomeThing went wrong",Toast.LENGTH_LONG).show()
            })

// Add the request to the RequestQueue.
        queue.add(JsonObjectRequest)
    }


    fun ShareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, Checkout the meme which I found $url")
        val chooser = Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)
    }
    fun NextMeme(view: View) {
        loadMeme()
    }
}

