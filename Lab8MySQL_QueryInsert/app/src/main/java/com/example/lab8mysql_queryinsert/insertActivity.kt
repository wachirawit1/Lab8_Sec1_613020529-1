package com.example.lab8mysql_queryinsert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_insert.*
import layout.Student
import layout.StudentAPI
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class insertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
    }
    fun addStudent (v: View){
        val serv: StudentAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StudentAPI::class.java)

        serv.insertStd(
            edt_id.text.toString(),
            edt_name.text.toString(),
            edt_age.text.toString().toInt()).enqueue(object : Callback<Student>{
            override fun onResponse(call: Call<Student>,  response: Response<Student>) {
                if (response.isSuccessful()){
                    Toast.makeText(applicationContext, "Sucessfully Inserted",Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(applicationContext, "ERROR",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Student>, t: Throwable) {
                Toast.makeText(applicationContext, " Error onFailuer " + t.message, Toast.LENGTH_LONG).show()
            }

        }

        )
    }
    fun  resetStudent(v:View){
        edt_id.text.clear()
        edt_name.text.clear()
        edt_age.text.clear()
    }

}