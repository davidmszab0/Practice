#!/bin/bash

#https://www.youtube.com/watch?v=eaI9jy4rGAk&list=PLYqs36RaYSmx9fJuOztGrmkL8E620XSEc&index=74

arr=("apple" "banana" "cherry")

	for i in ${arr[@]}
	do
		echo $i
	done

#display each item in the folder
for i in $(ls)
	do
		echo $i
	done