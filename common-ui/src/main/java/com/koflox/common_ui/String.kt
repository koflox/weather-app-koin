package com.koflox.common_ui

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import com.koflox.common_jvm_util.notNull

/**
 * Converts [CharSequence] to a [SpannableString].
 * Allows to specify a new font family and text size.
 *
 * @param fontResourceId new font resource id for [newFontStart]..[newFontEnd] range
 * @param sizeProportion text scale value for [newSizeStart]..[newSizeEnd] range
 * @param color text color value for [newColorStart]..[newColorEnd] range
 * @param style text style value for [newStyleStart]..[newStyleEnd] range
 * @param useUniversalRange if true, the start [universalStart] and end [universalEnd] values
 *                            for all modification will be used,
 *                            otherwise modification related values applied
 */
fun CharSequence.toSpannableString(
    @FontRes fontResourceId: Int? = null, context: Context? = null, newFontStart: Int = 0, newFontEnd: Int = 0,
    sizeProportion: Float? = null, newSizeStart: Int = 0, newSizeEnd: Int = 0,
    color: Int? = null, newColorStart: Int = 0, newColorEnd: Int = 0,
    style: Int? = null, newStyleStart: Int = 0, newStyleEnd: Int = 0,
    useUniversalRange: Boolean = false, universalStart: Int = 0, universalEnd: Int = 0
): SpannableString {
    return SpannableString(this).apply {
        notNull(fontResourceId, context) { resourceId, ctx ->
            val font = ResourcesCompat.getFont(ctx, resourceId)
            val typeface = Typeface.create(font, Typeface.DEFAULT.style)
            val typefaceSpan = getTypefaceSpan(typeface)

            StyleSpan(Typeface.BOLD)
            val fontStart = if (useUniversalRange) universalStart else newFontStart
            val fontEnd = if (useUniversalRange) universalEnd else newFontEnd

            setSpan(typefaceSpan, fontStart, fontEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        sizeProportion?.run {
            val sizeStart = if (useUniversalRange) universalStart else newSizeStart
            val sizeEnd = if (useUniversalRange) universalEnd else newSizeEnd

            setSpan(RelativeSizeSpan(this), sizeStart, sizeEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        color?.run {
            val sizeStart = if (useUniversalRange) universalStart else newColorStart
            val sizeEnd = if (useUniversalRange) universalEnd else newColorEnd

            setSpan(ForegroundColorSpan(this), sizeStart, sizeEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        style?.run {
            val sizeStart = if (useUniversalRange) universalStart else newStyleStart
            val sizeEnd = if (useUniversalRange) universalEnd else newStyleEnd

            setSpan(StyleSpan(this), sizeStart, sizeEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
}
