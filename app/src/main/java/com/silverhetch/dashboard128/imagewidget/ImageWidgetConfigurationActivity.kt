package com.silverhetch.dashboard128.imagewidget

import android.app.Activity
import android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID
import android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat

/**
 * Configuration activity for badge widget.
 */
class ImageWidgetConfigurationActivity : AppCompatActivity() {
  private val widgetId:Int by lazy {
    intent?.extras?.getInt(
        EXTRA_APPWIDGET_ID,
        INVALID_APPWIDGET_ID
    ) ?: INVALID_APPWIDGET_ID
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val root = FrameLayout(this).apply {
      layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
      id = ViewCompat.generateViewId()
    }
    setContentView(root)

    supportFragmentManager.beginTransaction()
        .replace(root.id, ImageWidgetConfigurationFragment.instance(widgetId))
        .commit()
  }

  override fun onBackPressed() {
    doneConfig()
  }

  private fun doneConfig() {
    WidgetUpdateService.enqueue(
        this,
        widgetId
    )

    setResult(
        Activity.RESULT_OK,
        Intent().apply {
          putExtra(
              EXTRA_APPWIDGET_ID,
              widgetId
          )
        })
    finish()
  }
}