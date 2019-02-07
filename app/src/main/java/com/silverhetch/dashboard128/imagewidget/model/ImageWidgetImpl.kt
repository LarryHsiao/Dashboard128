package com.silverhetch.dashboard128.imagewidget.model

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels

class ImageWidgetImpl(private val context: Context,
                      private val widgetIndex: Int
) : ImageWidget {
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
    return URL(sharedPreferences.getString("url", "https://img.shields.io/badge/Setup-required-red.svg"))
  }

  override fun file(): File {
    return File(imageStorageHome, name())
  }

  override fun validate() {
    try {
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
      errorImage()
    }
  }

  private fun errorImage() {
    FileOutputStream(file().apply {
      parentFile.mkdirs()
      if (exists()) {
        delete()
      }
    }).channel.transferFrom(
        Channels.newChannel(context.assets.open("Loading-Failed-red.svg")),
        0,
        Long.MAX_VALUE
    )
  }
}