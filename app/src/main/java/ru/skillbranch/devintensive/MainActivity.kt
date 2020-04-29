package ru.skillbranch.devintensive
import android.accessibilityservice.AccessibilityService
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Rect
import android.inputmethodservice.Keyboard
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.method.KeyListener
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.extensions.isKeyboardOpen
import ru.skillbranch.devintensive.models.Bender
import java.security.Key

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var batary: ImageView
    lateinit var quest:TextView
    lateinit var answ:EditText
    lateinit var sd:ImageButton
    lateinit var benderObg: Bender
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        batary=findViewById(R.id.i_batary)
        quest=findViewById(R.id.i_question)
        answ=findViewById(R.id.i_answer)
        sd=findViewById(R.id.i_send)
        val status = savedInstanceState?.getString("STATUS")?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION")?:Bender.Question.NAME.name
        benderObg= Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))
        val (r,g,b)=benderObg.status.color
        batary.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)
        quest.text=benderObg.askQuestion()
        sd.setOnClickListener(this)

        answ.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE){
                sendAnswer()
                hideKeyboard()
                true
            } else false
        }

    }
    private fun sendAnswer(){
        val (phrase, color) = benderObg.listenAnswer(answ.text.toString())
        answ.text.clear()
        val (r, g, b) = color
        batary.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
        quest.text = phrase
    }

    override fun onClick(v: View?) {
        if(v?.id==R.id.i_send){
           val(phrase, color)= benderObg.listenAnswer(answ.text.toString())
            answ.setText("")
            val (r,g,b)=color
            batary.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)
            quest.text=phrase
            hideKeyboard()

        }}
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("STATUS", benderObg.status.name)
        outState.putString("QUESTION", benderObg.question.name)

    }


    }






