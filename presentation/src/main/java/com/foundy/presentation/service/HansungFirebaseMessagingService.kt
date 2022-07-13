package com.foundy.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.foundy.domain.model.Notice
import com.foundy.presentation.R
import com.foundy.presentation.view.webview.WebViewActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.messaging

class HansungFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d(TAG, "New token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "From: ${message.from}")

        if (message.data.isNotEmpty()) {
            val keyword = message.data["keyword"].toString()

            checkDatabaseHasKeyword(
                keyword,
                onHave = { sendNotification(message) },
                onNothing = { unsubscribe(keyword) }
            )
            Log.d(TAG, "Message data payload: ${message.data}")
        }

        message.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
    }

    // 하나의 기기에서 키워드를 삭제한 후 다른 기기에서는 해당 키워드를 삭제할 수 없다. 따라서 메시지를 받은 후 DB에 키워드가
    // 있는지 확인해야한다.
    // https://github.com/jja08111/HansungNotification/issues/24#issuecomment-1182850572
    private fun checkDatabaseHasKeyword(
        keyword: String,
        onHave: () -> Unit,
        onNothing: () -> Unit
    ) {
        val uid = Firebase.auth.uid ?: return
        Firebase.database.goOnline()
        val userKeywordsReference = Firebase.database.reference
            .child("users")
            .child(uid)
            .child("keywords")

        userKeywordsReference.child(keyword).get().addOnCompleteListener {
            if (it.isSuccessful && it.result.value != null) {
                onHave()
            } else {
                onNothing()
            }
        }
    }

    private fun sendNotification(message: RemoteMessage) {
        val title = message.data["title"].toString()
        val url = message.data["url"].toString()
        val notice = Notice(
            isHeader = false,
            isNew = false,
            title = title,
            date = "",
            writer = "",
            url = url
        )
        // Id를 고유값으로 지정하여 알림이 개별 표시되도록 함
        val id = (System.currentTimeMillis() / 7).toInt()
        val intent = WebViewActivity.getIntent(this, notice).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            id,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val channelId = getString(R.string.firebase_keyword_noti_channel_id)
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(getString(R.string.keyword_notification))
            .setContentText(title)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                getString(R.string.keyword_notification),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id, notificationBuilder.build())
    }

    private fun unsubscribe(keyword: String) {
        Firebase.messaging.unsubscribeFromTopic(keyword)
        Log.d(TAG, "Unsubscribe keyword: $keyword")
    }

    companion object {
        private const val TAG = "HansungFirebaseMessagingService"
    }
}