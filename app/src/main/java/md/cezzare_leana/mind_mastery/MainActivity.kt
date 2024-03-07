package md.cezzare_leana.mind_mastery

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import md.cezzare_leana.mind_mastery.R
import java.security.SecureRandom

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayout: LinearLayout
    private lateinit var goBtn: Button
    private lateinit var btn0: Button
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btnPlayAgn: Button
    private lateinit var timer: TextView
    private lateinit var sum: TextView
    private lateinit var score: TextView
    private lateinit var result: TextView

    private val rand = SecureRandom()
    private val answers = ArrayList<Int>()
    private var locationOfCorrectAnswer = 0
    private var scoreGot = 0
    private var numOfQuestions = 0
    private var countDownTimer: CountDownTimer? = null

    fun playAgn(view: View?) {
        btn0.isEnabled = true
        btn1.isEnabled = true
        btn2.isEnabled = true
        btn3.isEnabled = true
        scoreGot = 0
        numOfQuestions = 0
        timer.text = "30s"
        result.text = ""
        newQuestion()
        btnPlayAgn.visibility = View.INVISIBLE

        score.text = "$scoreGot/$numOfQuestions"

        countDownTimer = object : CountDownTimer(30100, 3000) {
            override fun onTick(millisUntilFinished: Long) {
                newQuestion()
                timer.text = (millisUntilFinished / 1000).toString() + "s"
            }

            override fun onFinish() {
                timer.text = "0s"
                result.text = "Done! ;)"
                btn0.isEnabled = false
                btn1.isEnabled = false
                btn2.isEnabled = false
                btn3.isEnabled = false
                btnPlayAgn.visibility = View.VISIBLE
            }
        }.start()
    }

    fun chooseAnswer(view: View) {
        Log.i("Tag number: ", view.tag.toString())

        if (locationOfCorrectAnswer.toString() == view.tag.toString()) {
            result.text = "Correct! :)"
            scoreGot++
            Log.i("Correct: ", view.tag.toString())
        } else {
            result.text = "Wrong! :("
            Log.i("Wrong: ", view.tag.toString())
        }
        numOfQuestions++
        score.text = "$scoreGot/$numOfQuestions"
        newQuestion()
    }

    fun startTrainer(view: View?) {
        goBtn.visibility = View.INVISIBLE
        playAgn(findViewById<View>(R.id.txtTimer) as TextView)
        linearLayout.visibility = View.VISIBLE
    }

    fun newQuestion() {
        val num1 = rand.nextInt(21)
        val num2 = rand.nextInt(21)
        sum.text = "$num1 + $num2"

        locationOfCorrectAnswer = rand.nextInt(4)
        answers.clear()
        for (i in 0..3) {
            if (i == locationOfCorrectAnswer) {
                answers.add(num1 + num2)
            } else {
                var wrongAnswer = rand.nextInt(41)
                while (wrongAnswer == num1 + num2) {
                    wrongAnswer = rand.nextInt(41)
                }
                answers.add(wrongAnswer)
            }
        }

        btn0.text = answers[0].toString()
        btn1.text = answers[1].toString()
        btn2.text = answers[2].toString()
        btn3.text = answers[3].toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        linearLayout = findViewById<View>(R.id.gameLayout) as LinearLayout
        goBtn = findViewById<View>(R.id.btnGo) as Button
        btn0 = findViewById<View>(R.id.btn0) as Button
        btn1 = findViewById<View>(R.id.btn1) as Button
        btn2 = findViewById<View>(R.id.btn2) as Button
        btn3 = findViewById<View>(R.id.btn3) as Button
        btnPlayAgn = findViewById<View>(R.id.btnPlayAgain) as Button
        timer = findViewById<View>(R.id.txtTimer) as TextView
        sum = findViewById<View>(R.id.txtSum) as TextView
        score = findViewById<View>(R.id.txtScore) as TextView
        result = findViewById<View>(R.id.txtResult) as TextView

        newQuestion()
        goBtn.visibility = View.VISIBLE
        linearLayout.visibility = View.INVISIBLE
    }
}
