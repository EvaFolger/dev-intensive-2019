package ru.skillbranch.devintensive.extensions

import android.content.Context
import android.inputmethodservice.Keyboard
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import android.app.Activity
import android.graphics.Rect



 fun Activity.hideKeyboard() {
  val view = currentFocus
  if( view != null) {
   val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
   imm.hideSoftInputFromWindow(view.windowToken, 0)
  }
 }
fun Activity.isKeyboardOpen():Boolean{
val rect=Rect()
 window.decorView.getWindowVisibleDisplayFrame(rect)
 val screenHeight = window.decorView.rootView.height
 val difference = screenHeight - (rect.bottom - rect.top)
 return difference > 200
}
fun Activity.isKeyboardClosed(): Boolean {
 return !isKeyboardOpen()
}