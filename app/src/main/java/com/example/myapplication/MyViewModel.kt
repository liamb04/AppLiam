package com.example.myapplication

import androidx.lifecycle.ViewModel
class MyViewModel : ViewModel() {

    fun bmiInfo(bmi: Double): String {
        return when {
            bmi < 18.5 -> "You are underweight."
            bmi < 25 -> "You have a normal weight."
            bmi < 30 -> "You are overweight."
            else -> "You are obese."
        }
    }
    fun calculate(weight: Double, height: Double): Double{
        return weight / (height * height)
    }
}