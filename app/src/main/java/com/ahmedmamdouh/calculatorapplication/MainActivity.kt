package com.ahmedmamdouh.calculatorapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.ahmedmamdouh.calculatorapplication.ResultsController.MAX_NUMBER_SIZE
import com.ahmedmamdouh.calculatorapplication.ResultsController.MAX_RUNNING_OPERATIONS
import com.ahmedmamdouh.calculatorapplication.ResultsController.adjustTextView
import com.ahmedmamdouh.calculatorapplication.ResultsController.cachedValue
import com.ahmedmamdouh.calculatorapplication.ResultsController.context
import com.ahmedmamdouh.calculatorapplication.ResultsController.divisionByZeroFlag
import com.ahmedmamdouh.calculatorapplication.ResultsController.firstNumber
import com.ahmedmamdouh.calculatorapplication.ResultsController.formatBigNumbers
import com.ahmedmamdouh.calculatorapplication.ResultsController.historyTextView
import com.ahmedmamdouh.calculatorapplication.ResultsController.operation
import com.ahmedmamdouh.calculatorapplication.ResultsController.operationCode
import com.ahmedmamdouh.calculatorapplication.ResultsController.resultsTextView
import com.ahmedmamdouh.calculatorapplication.ResultsController.scrollView
import com.ahmedmamdouh.calculatorapplication.ResultsController.secondNumber
import com.ahmedmamdouh.calculatorapplication.ResultsController.setResultsText
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
import java.math.RoundingMode
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // A global TextView object which controls the results that display on the screen
        resultsTextView = resultText

        // A global TextView object which controls the history of the calculations
        historyTextView = historyText

        // The ScrollView which controls the scrolling of the history TextView
        scrollView = historyScrollView

        // Passing the context to the ResultsController class
        context = this

        // Loading cached data if available
        val historyTextCached = intent.getStringExtra("history text")
        val resultsTextCached = intent.getStringExtra("result text")
        val operationCached = intent.getBooleanExtra("operation", false)
        val operationCodeCached = intent.getIntExtra("operation code", -1)
        val divisionByZeroCached = intent.getBooleanExtra("division by zero", false)
        val firstNumberCached = intent.getStringExtra("first number")
        if((historyTextCached != null) and (resultsTextCached != null) and
            (operationCached != null) and (operationCodeCached != null) and (divisionByZeroCached != null)){
            resultsTextView.text = resultsTextCached
            historyTextView.text = historyTextCached
            operation = operationCached
            operationCode = operationCodeCached
            divisionByZeroFlag = divisionByZeroCached
            firstNumber = firstNumberCached
        }


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
            if (divisionByZeroFlag) {
                resultsTextView.text = "0"
                resultsTextView.textSize = 42f
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
                else if(operation){
                    resultsTextView.text = "0."
                    operation = false
                }
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
                    if (divisionByZeroFlag)
                        return
                    if (firstNumber == null) {
                        firstNumber = resultsTextView.text.toString()
                        historyTextView.text = "$firstNumber + "
                        operation = true

                    } else if (!operation) {
                        historyTextView.text = "${historyTextView.text}${resultsTextView.text} + "
                        adjustTextView()
                        updateResults()
                        operation = true
                    }
                    operationCode = 1

                }

                "-" -> {
                    if (divisionByZeroFlag)
                        return

                    if (firstNumber == null) {
                        firstNumber = resultsTextView.text.toString()
                        historyTextView.text = "$firstNumber - "
                        operation = true
                    } else if (!operation) {
                        historyTextView.text = "${historyTextView.text}${resultsTextView.text} - "
                        adjustTextView()
                        updateResults()
                        operation = true
                    }
                    operationCode = 2
                }

                "x" -> {
                    if (divisionByZeroFlag)
                        return

                    if (firstNumber == null) {
                        firstNumber = resultsTextView.text.toString()
                        historyTextView.text = "$firstNumber x "
                        operation = true
                    } else if (!operation) {
                        historyTextView.text = "${historyTextView.text}${resultsTextView.text} x "
                        adjustTextView()
                        updateResults()
                        operation = true
                    }
                    operationCode = 3
                }

                "/" -> {
                    if (divisionByZeroFlag)
                        return

                    if (firstNumber == null) {
                        firstNumber = resultsTextView.text.toString()
                        historyTextView.text = "$firstNumber / "
                        operation = true
                    } else if (!operation) {
                        historyTextView.text = "${historyTextView.text}${resultsTextView.text} / "
                        adjustTextView()
                        updateResults()
                        operation = true
                    }
                    operationCode = 4
                }

                "=" -> {
                    if (divisionByZeroFlag) {
                        operation = false
                        firstNumber = null
                        secondNumber = null
                        cachedValue = 0.0
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
                        adjustTextView()
                        updateResults()
                        firstNumber = null
                    }
                    operation = true
                }

                "+/-" -> {
                    if (divisionByZeroFlag)
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
            cachedValue = secondNumber!!.toDouble()
            when (operationCode) {
                1 -> cachedValue = firstNumber!!.toDouble() + secondNumber!!.toDouble()
                2 -> cachedValue = firstNumber!!.toDouble() - secondNumber!!.toDouble()
                3 -> cachedValue = firstNumber!!.toDouble() * secondNumber!!.toDouble()
                4 -> {
                    if (secondNumber!!.toDouble() == 0.0) {
                        firstNumber = null
                        secondNumber = null
                        resultsTextView.text = context.getString(R.string.division_by_zero_error)
                        resultsTextView.textSize = 30f
                        divisionByZeroFlag = true
                        return
                    }

                    cachedValue = firstNumber!!.toDouble() / secondNumber!!.toDouble()
                }
            }
            val df = DecimalFormat("#.####")
            df.roundingMode = RoundingMode.CEILING

            if(df.format(cachedValue).length >= 14)
                resultsTextView.text = "${formatBigNumbers(df.format(cachedValue).toDouble().toString())}"
            else
                resultsTextView.text = df.format(cachedValue)

            firstNumber = "$cachedValue"
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
                    cachedValue = 0.0
                    operationCode = -1
                    resultsTextView.text = "0"
                    historyTextView.text = ""
                    if (divisionByZeroFlag) resultsTextView.textSize = 42f
                }

                R.id.ceBtn -> {
                    resultsTextView.text = "0"
                    if (operation) operation = false
                    if (divisionByZeroFlag) resultsTextView.textSize = 42f

                }
            }

        }

    }

    /**
     * This function is responsible for switching the layout from day mode to night mode
     * @param view : Button view which switches the layout to night mode once clicked
     */
    fun goToNightMode(view: View) {
        var darkActivityIntent = Intent(context, MainActivityDark::class.java)
        darkActivityIntent.putExtra("history text", historyTextView.text.toString())
        darkActivityIntent.putExtra("result text", resultsTextView.text.toString())
        darkActivityIntent.putExtra("operation", operation)
        darkActivityIntent.putExtra("operation code", operationCode)
        darkActivityIntent.putExtra("division by zero", divisionByZeroFlag)
        darkActivityIntent.putExtra("first number", firstNumber)
        startActivity(darkActivityIntent)
        finish()
    }


}
