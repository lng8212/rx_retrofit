package com.example.reactivexretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.reactivexretrofit.databinding.ActivityMainBinding
import com.example.reactivexretrofit.model.Person
import com.example.reactivexretrofit.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        btnSubmit.setOnClickListener{
            if(edtName.text.toString() !="") {
                loadAPIData(edtName.text.toString())
                progressBar.visibility = View.VISIBLE
                bg.visibility = View.VISIBLE
            }
        }
    }

    private fun loadAPIData(name : String){
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getPersonObserver().observe(this, Observer <Person>{
            if (it != null){
                binding.person = it
                bg.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
            else {
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall(name)
    }
}