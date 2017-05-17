#!/bin/bash

a=()
b=("apple" "banana" "cherry")
echo ${b[2]}
#parsing an element to a position
b[5]="kiwi"
echo ${b[5]}

#adding an element to the end of the array
b+=("mango")
#print the whole array
echo ${b[@]}

#print the last element
echo ${b[@]: -1}

#associative arrays with key and value
declare -A myarray
myarray[color]=blue
myarray["office building"]="HQ West"

echo ${myarray["office building"]} is ${myarray["color"]}