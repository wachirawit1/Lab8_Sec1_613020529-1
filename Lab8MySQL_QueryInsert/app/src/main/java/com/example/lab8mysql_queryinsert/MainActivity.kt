package com.example.lab8mysql_queryinsert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import layout.Student
import layout.StudentAPI
import layout.StudentsAdapter
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import javax.security.auth.callback.CallbackHandler

class MainActivity : AppCompatActivity() {
    var studentList = arrayListOf<Student>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.adapter = StudentsAdapter(this.studentList,applicationContext)
        recycler_view.layoutManager= LinearLayoutManager(applicationContext)
    }

    override fun  onResume(){
        super.onResume()
        callStudentdata()
    }
    fun callStudentdata(){
        studentList.clear()
        val serv: StudentAPI= Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StudentAPI::class.java)

        serv.retrieveStudent()
            .enqueue(object : Callback<List<Student>>{
                override fun onResponse(
                    call: Call<List<Student>>,
                    response: Response<List<Student>>
                ){
                    response.body()?.forEach{
                        studentList.add(Student(it.std_id,it.std_name,it.std_age))
                    }
                    recycler_view.adapter=StudentsAdapter(studentList,applicationContext)
                    text1.text = "Student List : " + studentList.size.toString() + " students"
                }
                override fun onFailure(call: Call<List<Student>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })

}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.item1 -> {
                val intent = Intent(this@MainActivity,insertActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}