package com.silverhetch.dashboard128.imagewidget

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import com.silverhetch.dashboard128.R
import com.silverhetch.dashboard128.imagewidget.model.ImageWidgetImpl
import java.net.URL

class ImageWidgetConfigurationFragment : PreferenceFragmentCompat() {
  companion object {
    private const val ARG_WIDGET_IDX = "ARG_WIDGET_IDX"

    fun instance(index: Int): Fragment {
      return ImageWidgetConfigurationFragment().apply {
        arguments = Bundle().apply {
          putInt(ARG_WIDGET_IDX, index)
        }
      }
    }
  }


  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    preferenceManager.sharedPreferencesName = ImageWidgetImpl(context!!, arguments!!.getInt(ARG_WIDGET_IDX)).name()
    preferenceManager.sharedPreferencesMode = Context.MODE_PRIVATE
    setPreferencesFromResource(
        R.xml.preference_image_widget,
        null
    )
    findPreference("url").apply {
      setOnPreferenceChangeListener { preference, newValue ->
        try {
          URL(newValue.toString())
          true
        } catch (ignore: Exception) {
          false
        }
      }
    }
  }
}