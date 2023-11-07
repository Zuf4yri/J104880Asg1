package com.example.j104880asg1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.j104880asg1.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityMainBinding
    val chooseCategoryResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result -> if (result.resultCode == Activity.RESULT_OK) {}

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener(){
            handleRetrieveWithApi()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_action) {

            chooseCategoryResult.launch(Intent(this, ChooseCategoryActivity::class.java))

        }

        return super.onOptionsItemSelected(item)

    }

    private fun parseJson(jsonData: String?): String {
        try {
            val jsonArray = JSONArray(jsonData)
            if (jsonArray.length() > 0) {
                // Assuming you want to parse the first object in the array
                val firstObject = jsonArray.getJSONObject(0)
                val romanName = firstObject.getString("roman_name")
                return romanName
            } else {
                return "JSON response is empty."
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            return "JSON parsing error: ${e.message}"
        }
    /*try {
            val jsonObject = JSONObject(jsonData)
            val quote = jsonObject.getString("value")
            return quote
        } catch (e: JSONException) {
            e.printStackTrace()
            return "JSON parsing error: ${e.message}"
        }*/
    /*val jsonObject = JSONObject(jsonData)
        val quote = jsonObject.getString("value")
        return quote*/
    }
    // http://api.chucknorris.io/jokes/random
    //https://api.api-onepiece.com/fruits
    // https://stand-by-me.herokuapp.com/api/v1/stands
    private fun handleRetrieveWithApi(){
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET, "https://api.api-onepiece.com/fruits", null, {

                    response -> binding.textView.text = parseJson(response.toString()) },
            { binding.textView.text = "That didn't work!" + ": $it" }
        )
        queue.add(jsonObjectRequest)
    }
}