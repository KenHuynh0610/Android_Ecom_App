package com.example.myapplication.activities

fun main(){

    //return 2 using XOR, for every elements that are duplicate will be 0, add them up in result,
    // space require O(1), time complexity O(n)
    var array = arrayOf(2, 3, 5, 4, 5, 3, 4)

    var result = array[0]

    for(i in 1 until array.size){
        result = result xor array[i]
    }

    println(result)
}