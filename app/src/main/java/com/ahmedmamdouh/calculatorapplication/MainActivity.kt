package com.ahmedmamdouh.calculatorapplication

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.cachedValue
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.firstNumber
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.operation
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.operationCode
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.resultsTextView
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.secondNumber
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.setResultsText
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // A global textview object which controls the results that display on the screen
        resultsTextView = resultText

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

    }

    // A class which has utility methods to control the results displayed
    object ResultsController {

        lateinit var resultsTextView: TextView
        var firstNumber: String? = null
        var secondNumber: String? = null
        var operation: Boolean = false
        var cachedValue: Int = 0

        // This variable identifies the operation used. 1 for +, 2 for -, 3 for times and 4 for divided by.
        var operationCode: Int = -1

        fun setResultsText(results: String) {

        }

        fun appendToResultsText(char: String) {

        }
    }


    // Handling the event that occurs when a digit button is clicked
    object NumberClickedImplementer : View.OnClickListener {

        /**
         * This method is responsible for responding to click events by digit buttons and the decimal point.
         * It makes sure the numbers are displayed correctly.
         * @param v : this is the button view which was clicked.
         *
         */
        override fun onClick(v: View?) {
            val buttonView = v as Button
            when (buttonView.text) {
                "0" -> if (!(resultsTextView.text.equals("0"))) resultsTextView.text =
                    "${resultsTextView.text}0"
                "." -> if (!(resultsTextView.text.contains("."))) resultsTextView.text =
                    "${resultsTextView.text}."
                else -> if (!(resultsTextView.text.equals("0")) and !operation)
                    resultsTextView.text = "${resultsTextView.text}${buttonView.text}"
                else {

                    resultsTextView.text = "${buttonView.text}"
                    operation = false
                }

            }

        }

    }


    // Handling the event that occurs when an operator button is clicked
    object OperatorClickedImplementer : View.OnClickListener {

        /**
         * This method is responsible for responding to click events by operator buttons
         * It makes sure the operations are calculated and displayed correctly.
         * @param v : this is the button view which was clicked.
         *
         */
        override fun onClick(v: View?) {
            val buttonView = v as Button
            when (buttonView.text) {
                "+" -> {
                    if (firstNumber == null) {
                        firstNumber = resultsTextView.text.toString()
                        operation = true

                    } else if (!operation) {
                        updateResults()
                        operation = true
                    }
                    operationCode = 1

                }

                "-" -> {
                    if (firstNumber == null) {
                        firstNumber = resultsTextView.text.toString()
                        operation = true
                    } else if (!operation) {
                        updateResults()
                        operation = true
                    }
                    operationCode = 2
                }

                "x" -> {
                    if (firstNumber == null) {
                        firstNumber = resultsTextView.text.toString()
                        operation = true
                    } else if (!operation) {
                        updateResults()
                        operation = true
                    }
                    operationCode = 3
                }

                "/" -> {
                    if (firstNumber == null) {
                        firstNumber = resultsTextView.text.toString()
                        operation = true
                    } else if (!operation) {
                        updateResults()
                        operation = true
                    }
                    operationCode = 4
                }

                "=" -> {
                    if (firstNumber == null) {

                    } else {
                        updateResults()
                        firstNumber = null
                    }
                }
            }


        }

        /**
         * This method is responsible for updating the results when different operations occur
         */
        private fun updateResults() {
            secondNumber = resultsTextView.text.toString()
            cachedValue = secondNumber!!.toInt()
            when (operationCode) {
                1 -> cachedValue = firstNumber!!.toInt() + secondNumber!!.toInt()
                2 -> cachedValue = firstNumber!!.toInt() - secondNumber!!.toInt()
                3 -> cachedValue = firstNumber!!.toInt() * secondNumber!!.toInt()
                4 -> cachedValue = firstNumber!!.toInt() / secondNumber!!.toInt()
            }
            resultsTextView.text = "${cachedValue}"
            firstNumber = "${cachedValue}"
            secondNumber = null
        }
    }


    // Handling the event that occurs when a clear button is clicked
    object ClearClickedImplementer : View.OnClickListener {

        /**
         * This method is responsible for responding to click events by clear buttons
         * It makes sure that the screen gets cleared appropriately.
         * @param v : this is the button view which was clicked.
         *
         */
        override fun onClick(v: View?) {
            when (v!!.id) {
                R.id.removeBtn ->
                    if ((resultsTextView.text.toString().length == 1) and !operation)
                        resultsTextView.text = "0"
                    else if (!(resultsTextView.text.equals("0")) and !operation) {
                        resultsTextView.text = resultsTextView.text.toString()
                            .substring(0, resultsTextView.text.toString().length - 1)
                    }

                R.id.cBtn -> {
                    operation = false
                    firstNumber = null
                    secondNumber = null
                    cachedValue = 0
                    operationCode = -1
                    resultsTextView.text = "0"
                }
                
                R.id.ceBtn -> {
                    resultsTextView.text = "0"
                    if (operation) operation = false
                }
            }

        }

    }

}
