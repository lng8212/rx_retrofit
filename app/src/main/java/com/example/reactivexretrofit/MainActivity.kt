package com.example.reactivexretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.reactivexretrofit.databinding.ActivityMainBinding
import com.example.reactivexretrofit.model.Person
import com.example.reactivexretrofit.retrofit.ServiceBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private var buildServices= ServiceBuilder.buildService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        btnSubmit.setOnClickListener{
            if(edtName.text.toString() !="") {
                val yourName= edtName.text.toString()
                val compositeDisposable = CompositeDisposable()
                compositeDisposable.add(
                    buildServices.getPerson(yourName)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({response -> onResponse(response)}, {t -> onFailure(t) }))
                progressBar.visibility = View.VISIBLE
                bg.visibility = View.VISIBLE
            }
        }
    }

    private fun onFailure(t: Throwable?) {
        Toast.makeText(this,t!!.message, Toast.LENGTH_SHORT).show()
    }

    private fun onResponse(response: Person?) {
        binding.person = response
        bg.visibility = View.GONE
        progressBar.visibility = View.GONE
    }
}