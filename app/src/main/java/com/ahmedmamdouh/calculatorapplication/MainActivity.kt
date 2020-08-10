package com.ahmedmamdouh.calculatorapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.MAX_NUMBER_SIZE
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.MAX_RUNNING_OPERATIONS
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.cachedValue
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.context
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.divisionByZeroFlag
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.firstNumber
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.historyTextView
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.operation
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.operationCode
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.resultsTextView
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.secondNumber
import com.ahmedmamdouh.calculatorapplication.MainActivity.ResultsController.setResultsText
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.cBtn
import kotlinx.android.synthetic.main.activity_main.ceBtn
import kotlinx.android.synthetic.main.activity_main.decimalBtn
import kotlinx.android.synthetic.main.activity_main.divisonBtn
import kotlinx.android.synthetic.main.activity_main.eightBtn
import kotlinx.android.synthetic.main.activity_main.equalsBtn
import kotlinx.android.synthetic.main.activity_main.fiveBtn
import kotlinx.android.synthetic.main.activity_main.fourBtn
import kotlinx.android.synthetic.main.activity_main.historyText
import kotlinx.android.synthetic.main.activity_main.minusBtn
import kotlinx.android.synthetic.main.activity_main.nineBtn
import kotlinx.android.synthetic.main.activity_main.oneBtn
import kotlinx.android.synthetic.main.activity_main.plusBtn
import kotlinx.android.synthetic.main.activity_main.posNegBtn
import kotlinx.android.synthetic.main.activity_main.removeBtn
import kotlinx.android.synthetic.main.activity_main.resultText
import kotlinx.android.synthetic.main.activity_main.sevenBtn
import kotlinx.android.synthetic.main.activity_main.sixBtn
import kotlinx.android.synthetic.main.activity_main.threeBtn
import kotlinx.android.synthetic.main.activity_main.timesBtn
import kotlinx.android.synthetic.main.activity_main.twoBtn
import kotlinx.android.synthetic.main.activity_main.zeroBtn


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // A global TextView object which controls the results that display on the screen
        resultsTextView = resultText

        // A global TextView object which controls the history of the calculations
        historyTextView = historyText

        // Passing the context to the ResultsController class
        context = this


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
        lateinit var historyTextView: TextView
        lateinit var context: Context
        var firstNumber: String? = null
        var secondNumber: String? = null
        var operation: Boolean = false
        var cachedValue: Int = 0
        var divisionByZeroFlag = false

        // Important constants
        const val MAX_RUNNING_OPERATIONS = 40
        const val MAX_NUMBER_SIZE = 10

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

            // Checking if a division by zero has occurred
            if(divisionByZeroFlag){
                resultsTextView.text = "0"
                resultsTextView.textSize = 60f
                historyTextView.text = ""
                divisionByZeroFlag = false
                operation = true
            }

            // Checking if maximum no. of possible running operations has been achieved
            if (historyTextView.text.toString().length >= MAX_RUNNING_OPERATIONS) {
                Toast.makeText(context, R.string.maximum_error_1, Toast.LENGTH_LONG).show()
                return
            }

            // Checking if maximum no. of digits in a number has been achieved
            if ((resultsTextView.text.toString().length >= MAX_NUMBER_SIZE) and !operation) {
                Toast.makeText(context, R.string.maximum_error_2, Toast.LENGTH_LONG).show()
                return
            }


            val buttonView = v as Button
            when (buttonView.text) {
                "0" -> if (!(resultsTextView.text.equals("0")) and !operation) resultsTextView.text =
                    "${resultsTextView.text}0"
                else {
                    resultsTextView.text = "${buttonView.text}"
                    operation = false
                }
                "." -> if (!(resultsTextView.text.contains(".")) and !operation) resultsTextView.text =
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
                    if(divisionByZeroFlag)
                        return
                    if (firstNumber == null) {
                        firstNumber = resultsTextView.text.toString()
                        historyTextView.text = "$firstNumber + "
                        operation = true

                    } else if (!operation) {
                        historyTextView.text = "${historyTextView.text}${resultsTextView.text} + "
                        updateResults()
                        operation = true
                    }
                    operationCode = 1

                }

                "-" -> {
                    if(divisionByZeroFlag)
                        return

                    if (firstNumber == null) {
                        firstNumber = resultsTextView.text.toString()
                        historyTextView.text = "$firstNumber - "
                        operation = true
                    } else if (!operation) {
                        historyTextView.text = "${historyTextView.text}${resultsTextView.text} - "
                        updateResults()
                        operation = true
                    }
                    operationCode = 2
                }

                "x" -> {
                    if(divisionByZeroFlag)
                        return

                    if (firstNumber == null) {
                        firstNumber = resultsTextView.text.toString()
                        historyTextView.text = "$firstNumber x "
                        operation = true
                    } else if (!operation) {
                        historyTextView.text = "${historyTextView.text}${resultsTextView.text} x "
                        updateResults()
                        operation = true
                    }
                    operationCode = 3
                }

                "/" -> {
                    if(divisionByZeroFlag)
                        return

                    if (firstNumber == null) {
                        firstNumber = resultsTextView.text.toString()
                        historyTextView.text = "$firstNumber / "
                        operation = true
                    } else if (!operation) {
                        historyTextView.text = "${historyTextView.text}${resultsTextView.text} / "
                        updateResults()
                        operation = true
                    }
                    operationCode = 4
                }

                "=" -> {
                    if(divisionByZeroFlag) {
                        operation = false
                        firstNumber = null
                        secondNumber = null
                        cachedValue = 0
                        operationCode = -1
                        resultsTextView.text = "0"
                        historyTextView.text = ""
                        resultsTextView.textSize = 60f
                        return
                    }

                    if (firstNumber == null) {
                        historyTextView.text = "${resultsTextView.text} = "
                    } else {
                        historyTextView.text = "${historyTextView.text}${resultsTextView.text} = "
                        updateResults()
                        firstNumber = null
                    }
                }

                "+/-" -> {
                    if(divisionByZeroFlag)
                        return

                    if (resultsTextView.text.toString() == "0")
                        return
                    if (resultsTextView.text.toString()[0] == '-')
                        resultsTextView.text = resultsTextView.text.toString()
                            .substring(1, resultsTextView.text.toString().length)
                    else
                        resultsTextView.text = "-" + resultsTextView.text.toString()
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
                4 -> {
                    if(secondNumber!!.toInt() == 0) {
                        firstNumber = null
                        secondNumber = null
                        resultsTextView.text = context.getString(R.string.division_by_zero_error)
                        resultsTextView.textSize = 30f
                        divisionByZeroFlag = true
                        return
                    }

                    cachedValue = firstNumber!!.toInt() / secondNumber!!.toInt()}
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
                R.id.removeBtn -> {
                    if (divisionByZeroFlag) return

                    if ((resultsTextView.text.toString().length == 1) and !operation)
                        resultsTextView.text = "0"
                    else if (!(resultsTextView.text.equals("0")) and !operation) {
                        resultsTextView.text = resultsTextView.text.toString()
                            .substring(0, resultsTextView.text.toString().length - 1)
                    }
                }

                R.id.cBtn -> {
                    operation = false
                    firstNumber = null
                    secondNumber = null
                    cachedValue = 0
                    operationCode = -1
                    resultsTextView.text = "0"
                    historyTextView.text = ""
                    if(divisionByZeroFlag) resultsTextView.textSize = 60f
                }

                R.id.ceBtn -> {
                    resultsTextView.text = "0"
                    if (operation) operation = false
                    if(divisionByZeroFlag) resultsTextView.textSize = 60f

                }
            }

        }

    }


}
