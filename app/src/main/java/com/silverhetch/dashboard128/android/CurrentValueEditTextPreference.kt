package com.silverhetch.dashboard128.android

import android.content.Context
import android.util.AttributeSet
import androidx.preference.EditTextPreference

class CurrentValueEditTextPreference : EditTextPreference {
  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context?) : super(context)

  override fun setText(text: String?) {
    super.setText(text)
    summary = text
  }
}