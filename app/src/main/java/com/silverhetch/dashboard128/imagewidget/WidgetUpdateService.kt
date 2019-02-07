package com.silverhetch.dashboard128.imagewidget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import android.widget.RemoteViews
import androidx.core.app.JobIntentService
import com.caverock.androidsvg.RenderOptions
import com.caverock.androidsvg.SVG
import com.silverhetch.dashboard128.R
import com.silverhetch.dashboard128.imagewidget.model.ImageWidgetsImpl
import com.silverhetch.dashboard128.services.ServiceJob


/**
 * Update badge image from given source.
 */
class WidgetUpdateService : JobIntentService() {
  companion object {
    private const val ARG_WIDGET_IDX = "ARG_WIDGET_IDX"

    fun enqueue(context: Context, index: Int) {
      JobIntentService.enqueueWork(
          context,
          WidgetUpdateService::class.java,
          ServiceJob.ID_IMAGE_WIDGET_UPDATE_SERVICE,
          Intent().apply {
            putExtra(ARG_WIDGET_IDX, index)
          }
      )
    }
  }


  override fun onHandleWork(intent: Intent) {
    try {
      ImageWidgetsImpl(this).widget(
          intent.getIntExtra(ARG_WIDGET_IDX, -1)
      ).let { widget ->
        widget.validate()
        val image = SVG.getFromInputStream(widget.file().inputStream())
        if (image.documentWidth == -1f) {
          return
        }
        AppWidgetManager.getInstance(this).apply {
          updateAppWidget(
              widget.id(),
              RemoteViews(packageName, R.layout.widget_image).apply {

                setImageViewBitmap(
                    R.id.widgetImage_image,
                    Bitmap.createBitmap(
                        Math.ceil(image.documentWidth.toDouble()).toInt()*4,
                        Math.ceil(image.documentHeight.toDouble()).toInt()*4,
                        Bitmap.Config.ARGB_8888
                    ).apply {
                      image.setDocumentViewBox(
                          0f,
                          0f,
                          image.documentWidth,
                          image.documentHeight
                      )
                      image.setDocumentWidth("100%")
                      image.setDocumentHeight("100%")
                      image.renderToCanvas(Canvas(this))
                    }
                )
              }
          )
        }
      }
    } catch (ignore: Exception) {
      ignore.printStackTrace()
    }
  }
}