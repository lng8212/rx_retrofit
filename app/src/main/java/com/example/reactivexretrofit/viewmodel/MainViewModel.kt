package com.example.reactivexretrofit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reactivexretrofit.model.Person
import com.example.reactivexretrofit.retrofit.RetrofitInstance
import com.example.reactivexretrofit.retrofit.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    lateinit var person: MutableLiveData<Person>
    init {
        person = MutableLiveData()
    }

    fun getPersonObserver(): MutableLiveData<Person>{
        return person
    }

    fun makeApiCall (name: String){
        val retroInstance = RetrofitInstance.getRetroInstance().create(RetrofitService::class.java)
        retroInstance.getPerson(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getPersonObserverRx())
    }

    fun getPersonObserverRx(): Observer<Person>{
        return object : Observer<Person>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Person) {
                person.postValue(t)
            }

            override fun onError(e: Throwable) {
                person.postValue(null)
            }

            override fun onComplete() {
            }

        }
    }
}