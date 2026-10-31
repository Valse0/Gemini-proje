package com.luma.translate.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

/** Support / feedback destinations. Both open in the device's own browser (no WebView, no backend). */
object SupportLinks {
    const val SUPPORT_DEVELOPER = "https://ko-fi.com/hepyek"
    const val FEEDBACK_FORM = "https://tally.so/r/aO9QkW"
}

/**
 * Opens [url] in the user's external browser. Returns false (instead of
 * crashing) when no app can handle the link.
 */
fun openExternalLink(context: Context, url: String): Boolean {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).addCategory(Intent.CATEGORY_BROWSABLE)
    if (context !is Activity) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    return try {
        context.startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        false
    } catch (e: SecurityException) {
        false
    }
}
