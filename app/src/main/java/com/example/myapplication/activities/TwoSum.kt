package com.example.myapplication.activities

fun main(){
    //create a hashmap and store values in, if a value ran across its target sum, print the value

    var array = arrayOf(1,3,2,1,4,2,3)
    var target = 5
    var pairList = ArrayList<Pair<Int, Int>>()

    var hash: HashSet<Int> = HashSet()

    for(i in array){
        var temp = target - i
        if(hash.contains(temp)){
//            println("$i and $temp adds up to $target" )
            if(!pairList.contains(Pair(i,temp)))
                pairList.add(Pair(i, temp))
        }
        println(pairList)
        hash.add(i)
    }

}