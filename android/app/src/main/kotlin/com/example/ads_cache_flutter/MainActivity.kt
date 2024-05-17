package com.example.interstitial_example

import android.os.Bundle
import com.google.android.gms.ads.*
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.embedding.android.FlutterActivity
import com.google.ads.android.adscache.AdsCache
import android.content.Context
import android.util.Log

const val AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"
class MainActivity: FlutterActivity() {


    private lateinit var adsCache: AdsCache
    private lateinit var channel: MethodChannel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adsCache = AdsCache(this)

        MobileAds.initialize(this) { initializationStatus ->
            adsCache.initialize()
        }

        channel.setMethodCallHandler{ call, result ->
            when (call.method){
                "showAd" -> {
                    adsCache.showAd(AD_UNIT_ID, this@MainActivity)
                    Log.d("AdsCache","Ad successfully displayed")
                }
            }
        }

    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        flutterEngine?.let { engine ->
            channel = MethodChannel(engine.dartExecutor.binaryMessenger, "android")
        }
    }

}
