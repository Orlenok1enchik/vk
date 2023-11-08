package com.example.myfriends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.VKTokenExpiredHandler
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.utils.VKUtils
import com.vk.dto.common.id.UserId
import com.vk.sdk.api.friends.FriendsService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        VK.initialize(this)
//        val tokenTracker = object: VKTokenExpiredHandler {
//            override fun onTokenExpired() {
//                // token expired
//            }
//        }

//        VK.addTokenExpiredHandler(tokenTracker)
        val fingerprients = VKUtils.getCertificateFingerprint(this,this.packageName)
//
        val certificate = fingerprients?.joinToString()
//
        Log.d("TAG", "fingerprients: $certificate")

        VK.login(this){
            when(it){
                is VKAuthenticationResult.Success -> {}
                is VKAuthenticationResult.Failed -> {}
            }
        }.launch(listOf(VKScope.FRIENDS))

        VK.execute(FriendsService().friendsGetAppUsers(), object: VKApiCallback<List<UserId>>{
            override fun success(result: List<UserId>) {
                Log.d("TAG", "success: ${result.joinToString()}")
            }
            override fun fail(error: Exception){
                Log.e("TAG", error.toString())
            }
        })
    }

}


