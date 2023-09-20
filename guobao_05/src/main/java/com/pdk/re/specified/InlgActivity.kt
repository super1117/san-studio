package com.pdk.re.specified

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.common.util.Base64Utils
import com.just.agentweb.AgentWeb
import com.pdk.re.specified.utils.Base64Util
import com.pdk.re.specified.utils.RSAUtil
import org.json.JSONObject
import java.nio.charset.Charset


class InlgActivity : AppCompatActivity() {

    private lateinit var agentWeb: AgentWeb
    private val defColor by lazy {
        val enStr = getString(R.string.app_secret)
        if (enStr == Base64.encodeToString(getUrl().toByteArray(), Base64.DEFAULT)) {
            Color.BLACK
        } else {
            Color.parseColor("#194C38")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppsFlyerLib.getInstance().start(this, getString(R.string.app_key))
        window.statusBarColor = defColor
        setContentView(R.layout.activity_inlg)
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(findViewById(R.id.app_container), ViewGroup.LayoutParams(-1, -1))
            .closeIndicator()
            .setMainFrameErrorView(TextView(this))
            .addJavascriptInterface(getString(R.string.app_interface_name), this)
            .createAgentWeb()
            .go(getUrl())
            .apply {
                webCreator.webView.setBackgroundColor(defColor)
                webCreator.webView.settings.useWideViewPort = true
                webCreator.webView.settings.loadWithOverviewMode = true
                webCreator.webView.settings.userAgentString = getString(R.string.app_def_ua)
            }
        WebView.setWebContentsDebuggingEnabled(true)

    }

    private fun getUrl() = this.intent.getStringExtra("link2")?:""

    @JavascriptInterface
    fun postMessage(name: String, data: String) {
        if (name == "openWindow") {
        } else {
            AppsFlyerLib.getInstance().logEvent(this, name, hashMapOf<String, Any?>().apply {
                try {
                    val jsonData = JSONObject(data)
                    jsonData.keys().forEach {
                        val key = when (it) {
                            "currency"  -> AFInAppEventParameterName.CURRENCY
                            "amount" -> AFInAppEventParameterName.REVENUE
                            else -> it
                        }
                        this[key] = jsonData.opt(it)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
        }
    }

    override fun setRequestedOrientation(requestedOrientation: Int) {
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        agentWeb.webCreator.webView.evaluateJavascript("javascript:window.closeGame()") {}
    }

    override fun onBackPressed() {
        if (!agentWeb.back()) {
            super.onBackPressed()
        }
    }
}