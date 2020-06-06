package org.jellyfin.androidtv.preferences.ui.dsl

import android.content.Context
import androidx.annotation.StringRes
import androidx.preference.PreferenceCategory
import org.jellyfin.androidtv.preferences.ui.preference.ButtonRemapPreference
import java.util.*

class OptionsItemShortcut(
	private val context: Context
) : OptionsItemMutable<Int>() {
	fun setTitle(@StringRes resId: Int) {
		title = context.getString(resId)
	}

	override fun build(category: PreferenceCategory) {
		ButtonRemapPreference(context).also {
			category.addPreference(it)

			it.isPersistent = false
			it.key = UUID.randomUUID().toString()
			it.isEnabled = dependencyCheckFun() && enabled
			it.isVisible = visible
			it.title = title
			it.summaryProvider = ButtonRemapPreference.ButtonRemapSummaryProvider.instance
			it.keyCode = binder.get()
			it.defaultKeyCode = binder.default()
			it.setOnPreferenceChangeListener { _, newValue ->
				binder.set(newValue as Int)

				// Always return false because we save it
				false
			}
		}
	}
}

@OptionsDSL
fun OptionsCategory.shortcut(init: OptionsItemShortcut.() -> Unit) {
	this += OptionsItemShortcut(context).apply { init() }
}
