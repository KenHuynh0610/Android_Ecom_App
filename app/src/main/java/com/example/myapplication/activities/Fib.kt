package com.example.myapplication.activities

fun main(){
    //display first 10 terms of fibonacci sequence

    var n = 10
    var t = 0
    var j = 1

    println("first $n terms:")

    for (i in 1..n){
        println("$t +")
        val sum = t + j
        t = j
        j = sum
    }
}