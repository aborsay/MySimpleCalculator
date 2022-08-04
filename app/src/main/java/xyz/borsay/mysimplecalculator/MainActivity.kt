package xyz.borsay.mysimplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btn0 : Button
    lateinit var btn1 : Button
    lateinit var btn2 : Button
    lateinit var btn3 : Button
    lateinit var btn4 : Button
    lateinit var btn5 : Button
    lateinit var btn6 : Button
    lateinit var btn7 : Button
    lateinit var btn8 : Button
    lateinit var btn9 : Button
    lateinit var btnAdd : Button
    lateinit var btnSubtract : Button
    lateinit var btnMultiply : Button
    lateinit var btnDivide : Button
    lateinit var btnEqual : Button
    lateinit var btnBKSP : Button
    lateinit var btnCLR: Button
    private  var  mathArrayList: ArrayList<String> = arrayListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn0 = findViewById(R.id.btn0)
        btn0.setOnClickListener(this)
        btn1 = findViewById(R.id.btn1)
        btn1.setOnClickListener(this)
        btn2 = findViewById(R.id.btn2)
        btn2.setOnClickListener(this)
        btn3 = findViewById(R.id.btn3)
        btn3.setOnClickListener(this)
        btn4 = findViewById(R.id.btn4)
        btn4.setOnClickListener(this)
        btn5 = findViewById(R.id.btn5)
        btn5.setOnClickListener(this)
        btn6 = findViewById(R.id.btn6)
        btn6.setOnClickListener(this)
        btn7 = findViewById(R.id.btn7)
        btn7.setOnClickListener(this)
        btn8 = findViewById(R.id.btn8)
        btn8.setOnClickListener(this)
        btn9 = findViewById(R.id.btn9)
        btn9.setOnClickListener(this)
        btnAdd = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener (this)
        btnAdd.isEnabled = false
        btnMultiply = findViewById(R.id.btnMultiply)
        btnMultiply.setOnClickListener (this)
        btnMultiply.isEnabled = false
        btnDivide = findViewById(R.id.btnDivide)
        btnDivide.setOnClickListener (this)
        btnDivide.isEnabled = false
        btnSubtract = findViewById(R.id.btnSubtract)
        btnSubtract.setOnClickListener (this)
        btnSubtract.isEnabled = false
        btnEqual = findViewById(R.id.btnEqual)
        btnEqual.setOnClickListener (this)
        btnEqual.isEnabled = false
        btnCLR = findViewById(R.id.btnCLR)
        btnCLR.setOnClickListener (this)
        btnCLR.isEnabled = false
        btnBKSP = findViewById(R.id.btnBKSP)
        btnBKSP.setOnClickListener (this)
        btnBKSP.isEnabled = false
    }

    private fun enableButtons(type: Int, enabled: Boolean = true){
        when(type){
            2 -> {
                btnCLR.isEnabled = enabled
                btnBKSP.isEnabled = enabled
            }
            1 ->{
                btnEqual.isEnabled = enabled
            }
            0 ->{
                btnAdd.isEnabled = enabled
                btnSubtract.isEnabled = enabled
                btnMultiply.isEnabled = enabled
                btnDivide.isEnabled = enabled
            }
            else ->{
                btnAdd.isEnabled = enabled
                btnSubtract.isEnabled = enabled
                btnMultiply.isEnabled = enabled
                btnDivide.isEnabled = enabled
                btnEqual.isEnabled = enabled
                btnCLR.isEnabled = enabled
                btnBKSP.isEnabled = enabled
            }
        }


    }

    private fun addDigit(digit: String){
        if(mathArrayList.size > 0 && isNumber(mathArrayList.last())){
            //Adds number to the current number for the calculation, thus
            //you can have more then one digit number in the math
            val lastIndex = mathArrayList.lastIndex
            mathArrayList[lastIndex] = mathArrayList.last()+digit
            enableButtons(0)
            enableButtons(2)
        }else if(mathArrayList.size == 0 || !isNumber(mathArrayList.last())){
            //add number is nothing for equation has been created or
            //adds new number ot equation if last part was not a number
            mathArrayList.add(digit)
            enableButtons(0)
            enableButtons(2)
        }
        println("Array size: ${mathArrayList.size }")
        println(mathArrayList.toString())
        if(mathArrayList.size > 2 && isNumber(mathArrayList.last()))
            enableButtons(1)
    }


    /**
     * This function could be used again later. It return true is the string
     * is a number a counting number, not double or float (decimal)
     * @param s is the string passwed
     * @return True is string is a Int, False otherwise
     */
    private fun isNumber(s: String?): Boolean {
        return if (s.isNullOrEmpty()) false else s.all { Character.isDigit(it) }
    }

    private fun printEquationOnScreen(){
        val tvTheEquation: TextView = findViewById(R.id.tvTheEquation)
        var strTheEquation = ""

        for(value in mathArrayList){
            strTheEquation += "$value "
        }
        tvTheEquation.text = strTheEquation
    }

    private fun solveEquation(){
        if(mathArrayList.size > 3  && isNumber(mathArrayList.last())){
            mathArrayList.add("=")
            val newMathArray: ArrayList<String> = mathArrayList.toMutableList() as ArrayList<String>


            printEquationOnScreen()

            newMathArray.forEachIndexed { index, element ->
                if(element == "*"){
                    val tempResult = newMathArray[index-1].toInt().times( newMathArray[index+1].toInt())
                    newMathArray[index-1]=""
                    newMathArray[index]=""
                    newMathArray[index+1]= tempResult.toString()
                    println("temp array ${newMathArray.toString()}")
                }else if(element == "/"){
                    val divideBy =  if(newMathArray[index+1].toInt() == 0) 1 else newMathArray[index+1].toInt()
                    val tempResult = newMathArray[index-1].toInt().div( divideBy)
                    newMathArray[index-1]=""
                    newMathArray[index]=""
                    newMathArray[index+1]= tempResult.toString()
                    println("temp array ${newMathArray.toString()}")
                }
            }

            newMathArray.removeAll(listOf("", null))
            println("temp array ${newMathArray.toString()}")
            newMathArray.forEachIndexed { index, element ->
                if(element=="+"){
                    val tempResult = newMathArray[index-1].toInt().plus( newMathArray[index+1].toInt())
                    newMathArray[index-1]=""
                    newMathArray[index]=""
                    newMathArray[index+1]= tempResult.toString()
                }else if(element == "-"){
                    val tempResult = newMathArray[index-1].toInt().minus( newMathArray[index+1].toInt())
                    newMathArray[index-1]=""
                    newMathArray[index]=""
                    newMathArray[index+1]= tempResult.toString()
                }else if(element =="="){
                    mathArrayList.add(newMathArray[index-1])
                }
            }
        }

    }

    private fun addMethodToEquation(method: String){
        println("size: ${mathArrayList.size} is even: ${mathArrayList.size %2}")
        if(mathArrayList.size % 2 !=  0){
            mathArrayList.add(method)
            enableButtons(0,false)
            println(mathArrayList.toString())
        }
    }

    private fun clearEquation(){
        if(mathArrayList.size > 0){
            mathArrayList.clear()
        }
        println(mathArrayList.toString())
    }

    private  fun backspaceInEquation(){
        if(mathArrayList.size > 0){
            val lastIndex = mathArrayList.lastIndex
            mathArrayList.removeAt(lastIndex)
        }
        println(mathArrayList.toString())
    }

    override fun onClick(p0: View?) {
        if(p0 is Button){
            val btnClickedButton: Button = p0
            when(val btnValue = btnClickedButton.text.toString()){
                "/","*","-","+" -> addMethodToEquation(btnValue)
                "CLR" ->clearEquation()
                "BKSP" -> backspaceInEquation()
                "=" -> solveEquation()
                else ->{
                    if(isNumber(btnValue) && btnValue.toInt() in 0..10)
                        addDigit(btnValue)
                }
            }
            printEquationOnScreen()
        }

    }


}