package com.junction.mymusicmap.presentation.login

import android.os.Bundle
import android.widget.Toast
import com.junction.mymusicmap.R
import com.junction.mymusicmap.databinding.ActivityLoginBinding
import com.tistory.blackjinbase.base.BaseActivity
import com.tistory.blackjinbase.ext.alert
import com.tistory.blackjinbase.ext.toast
import com.tistory.blackjinbase.util.Dlog
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override var logTag = "LoginActivity"

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.model = viewModel
        Dlog.d("MyTag","This is log sample")

        toast("toast example")

        alert(title = "제목", message = "message") {
            positiveButton("확인") {

            }

            negativeButton("취소") {

            }
        }.show()
    }
}