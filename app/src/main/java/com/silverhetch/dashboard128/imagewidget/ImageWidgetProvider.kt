package com.silverhetch.dashboard128.imagewidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.silverhetch.dashboard128.services.ServiceJob

class ImageWidgetProvider : AppWidgetProvider() {
  override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
    super.onUpdate(context, appWidgetManager, appWidgetIds)
    appWidgetIds?.let { widgetIds ->
      context?.let {
        widgetIds.forEach { widgetId ->
          WidgetUpdateService.enqueue(context, widgetId)
        }
      }
    }
  }
}