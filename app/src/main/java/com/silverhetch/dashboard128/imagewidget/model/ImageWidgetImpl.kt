package com.silverhetch.dashboard128.imagewidget.model

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels

class ImageWidgetImpl(private val context: Context,
                      private val widgetIndex: Int
) : ImageWidget {
  companion object {
    private const val SETUP_REQUIRED_URL = "https://setup_required"
  }
  private val imageStorageHome = File(context.filesDir, "image_widget")
  private val sharedPreferences = context.getSharedPreferences(
      name(),
      Context.MODE_PRIVATE
  )

  override fun name(): String {
    return "IMAGE_WIDGET_$widgetIndex"
  }

  override fun id(): Int {
    return widgetIndex
  }

  override fun url(): URL {
    return URL(
        sharedPreferences.getString(
            "url",
            SETUP_REQUIRED_URL
        )
    )
  }

  override fun file(): File {
    return File(imageStorageHome, name())
  }

  override fun validate() {
    try {
      if (url().sameFile(URL(SETUP_REQUIRED_URL))){
        assetImage("Setup-required-red.svg")
        return
      }
      FileOutputStream(file().apply {
        parentFile.mkdirs()
        if (exists()) {
          delete()
        }
      }).channel.transferFrom(
          Channels.newChannel(url().openStream()),
          0,
          Long.MAX_VALUE
      )
    } catch (e: Exception) {
      e.printStackTrace()
      assetImage("Loading-Failed-red.svg")
    }
  }

  private fun assetImage(fileName:String) {
    FileOutputStream(file().apply {
      parentFile.mkdirs()
      if (exists()) {
        delete()
      }
    }).channel.transferFrom(
        Channels.newChannel(context.assets.open(fileName)),
        0,
        Long.MAX_VALUE
    )
  }
}