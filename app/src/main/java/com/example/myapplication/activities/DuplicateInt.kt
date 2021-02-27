package com.example.myapplication.activities

fun main(){

    //print 10, 1, and 2 by looping through and counting the frequency of the numbers using HashMap
    var array = arrayOf(1,10,2,5,6,4,10,2,1)

    var map:HashMap<Int, Int> = HashMap()
    var count:Int = 0
    var flag = false

    for(i in array){
        if(map.containsKey(i)){
            count = map[i]!!
            map[i] = count + 1

        }
        else{
            map[i] = 1
        }
    }

    for((key,value) in map)
    {
        if (value>1){
            flag = true
            println("$key")
        }
    }

    if(!flag){
        println(-1)
    }
}