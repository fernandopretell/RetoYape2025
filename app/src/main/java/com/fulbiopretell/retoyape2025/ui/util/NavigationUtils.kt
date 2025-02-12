package com.fulbiopretell.retoyape2025.ui.util

import android.net.Uri
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> T.toUriEncoded(): String {
    return Uri.encode(Json.encodeToString(this)).toString()
}
