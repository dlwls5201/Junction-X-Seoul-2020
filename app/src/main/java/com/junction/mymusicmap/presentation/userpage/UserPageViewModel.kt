package com.junction.mymusicmap.presentation.userpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tistory.blackjinbase.util.Dlog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UserPageViewModel(private val model: ZepetoModel) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private val _zepetoResponseLiveData = MutableLiveData<ZepetoResponse>()

    val zepetoResponseLiveData: LiveData<ZepetoResponse>
        get() = _zepetoResponseLiveData

    fun getUrl() {
        addDisposable(
            model.getUrl(
                ZepetoResponse(
                    type = "booth",
                    target = Target(listOf("NZIOG0")),
                    url = "https://render-cdn.zepeto.io/20201010/13/39mqExsd1fQ8srSg6A"
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.run {
                        _zepetoResponseLiveData.postValue(this)
                        Dlog.v("UserPageViewModel: success " + it.url)
                    }
                }, {
                    Dlog.v("${it.message}")
                })
        )
    }

}