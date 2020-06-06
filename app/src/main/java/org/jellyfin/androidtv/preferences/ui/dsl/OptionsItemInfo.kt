package org.jellyfin.androidtv.preferences.ui.dsl

import android.content.Context
import androidx.annotation.StringRes
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceCategory
import java.util.*

class OptionsItemInfo(
	private val context: Context
) : OptionsItem() {
	var title: String? = null
	var content: String? = null

	fun setTitle(@StringRes resId: Int) {
		title = context.getString(resId)
	}

	fun setContent(@StringRes resId: Int) {
		content = context.getString(resId)
	}

	override fun build(category: PreferenceCategory) {
		EditTextPreference(context).also {
			category.addPreference(it)

			it.isPersistent = false
			it.key = UUID.randomUUID().toString()
			it.isEnabled = false
			it.title = title
			it.summary = content
		}
	}
}

@OptionsDSL
fun OptionsCategory.info(init: OptionsItemInfo.() -> Unit) {
	this += OptionsItemInfo(context).apply { init() }
}
