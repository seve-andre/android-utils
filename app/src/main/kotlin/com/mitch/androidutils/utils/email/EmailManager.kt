package com.mitch.androidutils.utils.email

import android.content.Context
import android.content.Intent
import android.net.Uri

data class Email(
    val to: List<EmailAddress>,
    val cc: List<EmailAddress> = emptyList(),
    val bcc: List<EmailAddress> = emptyList(),
    val subject: String? = null,
    val body: String? = null,
    val attachments: List<Uri> = emptyList()
)

@JvmInline
value class EmailAddress(val value: String)

fun Context.sendEmail(email: Email) {
    val actionSendType = when {
        email.attachments.isEmpty() -> Intent.ACTION_SENDTO
        email.attachments.size == 1 -> Intent.ACTION_SEND
        else -> Intent.ACTION_SEND_MULTIPLE
    }

    val intent = Intent(actionSendType).apply {
        data = Uri.parse("mailto:") // Only email apps handle this.

        // to
        putExtra(Intent.EXTRA_EMAIL, email.to.map { it.value }.toTypedArray())

        // cc
        if (email.cc.isNotEmpty()) {
            putExtra(Intent.EXTRA_CC, email.cc.map { it.value }.toTypedArray())
        }

        // bcc
        if (email.bcc.isNotEmpty()) {
            putExtra(Intent.EXTRA_BCC, email.cc.map { it.value }.toTypedArray())
        }

        // subject
        email.subject?.let {
            putExtra(Intent.EXTRA_SUBJECT, it)
        }

        // body
        email.body?.let {
            putExtra(Intent.EXTRA_TEXT, it)
        }

        // attachments
        if (email.attachments.isNotEmpty()) {
            putExtra(Intent.EXTRA_STREAM, email.attachments.toTypedArray())
        }
    }
    if (intent.resolveActivity(this.packageManager) != null) {
        startActivity(intent)
    }
}
