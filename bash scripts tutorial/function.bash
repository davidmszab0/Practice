#!/bin/bash

#https://www.youtube.com/watch?v=eaI9jy4rGAk&list=PLYqs36RaYSmx9fJuOztGrmkL8E620XSEc&index=74
function greet {
	echo "Hi, $1! What a nice $2!"
}

echo "Greeting message now"

greet David Evening

function numberthings {
	i=1
	for f in $@; do
		echo $i: @f #next argument in the dollarsign array of arguments
		((i+=1))
	done
}
#$(ls) all the folders and files in the current dir
#numberthings $(ls)

numberthings pine birch maple spruce

#https://www.youtube.com/watch?v=Uf4vod4NQ1Q&list=PLYqs36RaYSmx9fJuOztGrmkL8E620XSEc&index=72
arr=("apple" "banana" "cherry")

function count {
	for i in ${arr[@]}
	do
		echo $i
	done
}
count 

# Usage of the $ like $(echo foo) means run whatever is inside the parentheses 
#in a subshell and return that as the value. In my example, you would get 
#foo since echo will write foo to standard out
$(echo foo)