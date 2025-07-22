package com.pxl.pixelstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.pxl.pixelstore.domain.encryption.KeysManager
import com.pxl.pixelstore.ui.RootScreen
import com.pxl.pixelstore.ui.theme.PixelStoreTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    @Inject
//    lateinit var sessionManager: SessionManager
    @Inject
    lateinit var newKeysManager: KeysManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSessionData()

        enableEdgeToEdge()

        setContent {
            PixelStoreTheme {
                RootScreen()
            }
        }
    }


    private fun initSessionData() {

        lifecycleScope.launch(Dispatchers.IO) {
//            sessionManager.loadSessionData()
//            newKeysManager.initKey(
//                sessionManager.getPassword(),
//                sessionManager.getSalt()
//            )
        }


        lifecycleScope.launch(Dispatchers.Main) {
//            sessionManager.userLoggedInFlow.collect { userLoggedIn ->
//            }
        }
    }
}