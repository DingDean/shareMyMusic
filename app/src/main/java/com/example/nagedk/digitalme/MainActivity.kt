package com.example.nagedk.digitalme

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Intent.ACTION_SEND == intent.action) {
            println("ACTION SEND is found")
            if ("text/plain" == intent.type) {
                println("text plain is found")
                val content = intent.getStringExtra(Intent.EXTRA_TEXT)
                println("content is ")
                println(content)
                val textView = findViewById<TextView>(R.id.welcome_text).apply({
                    text = content
                })
            }
        }
    }

    fun sendMessage(view: View) {
        val textView = findViewById<TextView>(R.id.my_thought)
        val thought = textView.text ?: "这个人很懒，什么感想都没有"
        val queue = Volley.newRequestQueue(this)
        val url = "https://dingkewz.com/sw/musicShare"

        val mainText = findViewById<TextView>(R.id.welcome_text)
        val stringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener<String> { response ->
                    // response
                    mainText.apply {
                        text = getString(R.string.success)
                    }
                },
                Response.ErrorListener {
                    // error
                    mainText.apply {
                        text = getString(R.string.error)
                    }
                }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["thought"] = thought.toString()
                params["url"] = mainText.text.toString()
                params["pwd"] = ""

                return params
            }
        }

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}
