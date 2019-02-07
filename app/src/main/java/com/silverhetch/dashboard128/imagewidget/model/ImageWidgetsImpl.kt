package com.silverhetch.dashboard128.imagewidget.model

import android.content.Context

class ImageWidgetsImpl(
    private val context: Context
) : ImageWidgets {
  override fun widget(index: Int): ImageWidget {
    return ImageWidgetImpl(
        context,
        index
    )
  }
}