package com.example.myapplication.activities

fun main(){
    //return 20
    var array = arrayOf(10,20,10,30,20,30,20,20)
    var maxCount = 1
    var currentCount = 1
    var result = array[0]
    var size = array.size
    //we'll first sort the array then traverse linearly throughout to count the most frequent
    array.sort()

    for(i in 1 until array.size) {
        if (array[i] == array[i - 1]) {
            currentCount++
        }else{
            if (currentCount > maxCount){
                maxCount = currentCount
                result = array[i-1]
            }
            currentCount = 1
        }
    }
    if(currentCount > maxCount){
        maxCount = currentCount
        result = array[size-1]
    }
    println(result)


}