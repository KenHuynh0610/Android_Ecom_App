package com.example.myapplication.activities

import java.util.*

fun main(){
    var count = 0
    var array = arrayOf(1,9,0,0,2,4,0,2,0,1,5)
    for(i in 0 until array.size){
        if(array[i] != 0){
            array[count++] = array[i]
        }
    }
    while(count<array.size){
        array[count++] = 0
    }
    println(array.contentToString())
}