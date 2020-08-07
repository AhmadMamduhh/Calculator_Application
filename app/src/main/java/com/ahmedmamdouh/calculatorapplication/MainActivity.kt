package com.ahmedmamdouh.calculatorapplication

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var resultTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resultTextView = resultText

        // Setting click listeners for digit buttons
        oneBtn.setOnClickListener(NumberClickedImplementer)
        twoBtn.setOnClickListener(NumberClickedImplementer)
        threeBtn.setOnClickListener(NumberClickedImplementer)
        fourBtn.setOnClickListener(NumberClickedImplementer)
        fiveBtn.setOnClickListener(NumberClickedImplementer)
        sixBtn.setOnClickListener(NumberClickedImplementer)
        sevenBtn.setOnClickListener(NumberClickedImplementer)
        eightBtn.setOnClickListener(NumberClickedImplementer)
        nineBtn.setOnClickListener(NumberClickedImplementer)
        zeroBtn.setOnClickListener(NumberClickedImplementer)
        decimalBtn.setOnClickListener(NumberClickedImplementer)

        // Setting click listeners for operator buttons
        plusBtn.setOnClickListener(OperatorClickedImplementer)
        minusBtn.setOnClickListener(OperatorClickedImplementer)
        timesBtn.setOnClickListener(OperatorClickedImplementer)
        divisonBtn.setOnClickListener(OperatorClickedImplementer)
        posNegBtn.setOnClickListener(OperatorClickedImplementer)
        equalsBtn.setOnClickListener(OperatorClickedImplementer)

        // Setting click listeners for clear buttons
        cBtn.setOnClickListener(ClearClickedImplementer)
        ceBtn.setOnClickListener(ClearClickedImplementer)
        removeBtn.setOnClickListener(ClearClickedImplementer)

        // Sending the results textview to the classes that handle the actions
        NumberClickedImplementer.outputTextView = resultTextView
        OperatorClickedImplementer.outputTextView = resultTextView
        ClearClickedImplementer.outputTextView = resultTextView



    }

    // Handling the event that occurs when a digit button is clicked
    object NumberClickedImplementer : View.OnClickListener{
        lateinit var outputTextView: TextView

        fun setTextView(textView: TextView){
            outputTextView = textView
        }

        /**
         * This method is responsible for responding to click events by digit buttons and the decimal point.
         * It makes sure the numbers are displayed correctly.
         * @param v : this the button view which was clicked.
         *
         */
        override fun onClick(v: View?) {
            val buttonView = v as Button
            when(buttonView.text){
                "0"-> if (!(outputTextView.text.equals("0")))  outputTextView.text = "${outputTextView.text}0"
                "." -> if (!(outputTextView.text.contains(".")))  outputTextView.text = "${outputTextView.text}."
                else -> if (!(outputTextView.text.equals("0"))) outputTextView.text =
                    "${outputTextView.text}${buttonView.text}"
                    else
                    outputTextView.text = "${buttonView.text}"

            }

        }

    }


    // Handling the event that occurs when an operator button is clicked
    object OperatorClickedImplementer : View.OnClickListener{
        lateinit var outputTextView: TextView

        fun setTextView(textView: TextView){
            outputTextView = textView
        }

        override fun onClick(v: View?) {

        }

    }

    // Handling the event that occurs when a clear button is clicked
    object ClearClickedImplementer : View.OnClickListener{
        lateinit var outputTextView: TextView

        fun setTextView(textView: TextView){
            outputTextView = textView
        }

        override fun onClick(v: View?) {

        }

    }
}