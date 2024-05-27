package com.sdk.ipassplussdk.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import androidx.core.os.LocaleListCompat
import java.util.Locale

class ContextUtils(base: Context?) : ContextWrapper(base) {
    companion object {
        fun updateLocale(context: Context, localeToSwitchTo: String?): ContextWrapper {
            var context: Context = context
            val resources: Resources = context.getResources()
            val configuration: Configuration = resources.getConfiguration()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val appLocale: LocaleList = LocaleList.forLanguageTags(localeToSwitchTo)
                val localeManager = context.getSystemService(Context.LOCALE_SERVICE) as android.app.LocaleManager
                localeManager.applicationLocales = appLocale
            } else {
                // Fallback for older versions
                val locale = Locale(localeToSwitchTo)
                Locale.setDefault(locale)
                val config = context.resources.configuration
                config.setLocale(locale)
                context.resources.updateConfiguration(config, context.resources.displayMetrics)
            }

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                val localeList = LocaleList(localeToSwitchTo)
//                LocaleList.setDefault(localeList)
//                configuration.setLocales(localeList)
//            }

            return ContextUtils(context)
        }
    }
}