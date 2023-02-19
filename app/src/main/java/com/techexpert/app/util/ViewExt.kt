package com.techexpert.app.util

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

fun FragmentActivity?.showDialogFragment(dialogFragment: DialogFragment?) {
    if (this != null) else return
    val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
    if (!isFinishing && dialogFragment != null) {
        if (dialogFragment.isAdded) {
            dialogFragment.dismissAllowingStateLoss()
        }
        ft.add(dialogFragment, null)
        ft.commitAllowingStateLoss()
    }
}

fun ImageView.setTint(@ColorRes color: Int?) {
    if (color == null) {
        ImageViewCompat.setImageTintList(this, null)
    } else {
        ImageViewCompat.setImageTintList(
            this,
            ColorStateList.valueOf(ContextCompat.getColor(context, color))
        )
    }
}

fun TextView.onRightDrawableClicked(onClicked: (view: View) -> Unit) {
    this.setOnTouchListener { v, event ->
        var hasConsumed = false
        if (v is TextView) {
            if (event.x >= v.width - v.totalPaddingRight) {
                if (event.action == MotionEvent.ACTION_UP) {
                    onClicked(this)
                }
                hasConsumed = true
            }
        }
        hasConsumed
    }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun TextInputLayout.setHintStyle(id: Int) {
    this.apply {
        setHintTextAppearance(id)
    }
}

fun View.disable() {
    isEnabled = false
}

fun View.enable() {
    isEnabled = true
}

fun Context.showShortToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun View.showShortSanckbar(message: CharSequence) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun View.showLongSanckbar(message: CharSequence) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun View.showIndefiniteSanckbar(message: CharSequence) {
    Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE).show()
}
