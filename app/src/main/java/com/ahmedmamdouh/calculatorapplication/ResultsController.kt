package com.ahmedmamdouh.calculatorapplication

import android.content.Context
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import android.widget.TextView

// A class which has utility methods to control the results displayed
object ResultsController {

    // Variables which represent the views in the application
    lateinit var resultsTextView: TextView
    lateinit var historyTextView: TextView
    lateinit var context: Context
    lateinit var scrollView: HorizontalScrollView

    // Variables which will be used to store the right operand and left operand in an operation
    var firstNumber: String? = null
    var secondNumber: String? = null

    // This boolean is a flag which if true indicates that there's a math operation occurring
    var operation: Boolean = false

    // This is a variable which acts as an accumulator in running operations
    var cachedValue: Double = 0.0

    // This boolean is a flag which if true indicates that a division by zero error has happened
    var divisionByZeroFlag = false

    // Important constants
    const val MAX_RUNNING_OPERATIONS = 100
    const val MAX_NUMBER_SIZE = 12

    // This variable identifies the operation used. 1 for +, 2 for -, 3 for x and 4 for /
    var operationCode: Int = -1

    /**
     * This method adjusts the scrolling of the historyTextView whenever characters are appended to it
     **/
    fun adjustTextView() {
        scrollView.post { scrollView.fullScroll(ScrollView.FOCUS_RIGHT) }
    }

    /**
     * This method formats numbers that are too big to display
     * @param number : The string which will be formatted
     * @return formattedNumber : The string after being formatted to fit on the screen
     **/
    fun formatBigNumbers(number : String) : String {
        var formattedString = ""
        var count = 0
        var eFlag = false
        var dotFound = false
        for(digit in number){
            if((digit != '.') and !dotFound){
                formattedString += digit
                dotFound = true
            }
            else if((count < 10) and !eFlag){
                if(digit == 'E') {
                    eFlag = true
                    formattedString += digit
                    count = 11
                }
                else {
                    formattedString += digit
                    count += 1
                }

            }
            else if(count == 10){
                if(digit == 'E'){
                    formattedString += digit
                    count += 1
                    eFlag= true
                }
            }
            else if(eFlag and (count > 10))
                formattedString += digit
        }
        return formattedString
    }

    fun setResultsText(results: String) {

    }

    fun appendToResultsText(char: String) {

    }
}