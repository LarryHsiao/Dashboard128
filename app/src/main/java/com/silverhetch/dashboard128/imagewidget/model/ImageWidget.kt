package com.silverhetch.dashboard128.imagewidget.model

import java.io.File
import java.net.URL

interface ImageWidget {
  fun id(): Int
  fun name(): String
  fun url(): URL
  fun file(): File
  fun validate()
}