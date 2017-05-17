#!/bin/bash
#[ EXPR1 -a EXPR2 ]	True if both EXPR1 and EXPR2 are true
#[ EXPR1 -o EXPR2 ]	True if either EXPR1 or EXPR2 is true

#~/ is the HOME dir
#~/Desktop/scripts/ #brings you to the Desktop/scripts folder

#https://www.youtube.com/watch?v=hgxSCmlvyCY&index=70&list=PLYqs36RaYSmx9fJuOztGrmkL8E620XSEc
a=5
if [a -gt 4	]; then
	echo $a is greater than 4!
else
	echo $a is not greater than 4!
fi

gender="male"
if [[ "$gender" == "f*" ]]; then
	echo "Pleasure to meet you, Madame."
else 
	echo "How come the MAN hasn't got a drink yet?"
fi

#if [[ regex ]]

a="This is my #1 string!"
#=~ is to allow regex in an if statement, '+' is one or more of these
if [[ $a =~ [0-9]+ ]]; then
	echo "There are numbers in the string: $a"
else
	echo "There are NO numbers in the string: $a"
fi

a="dog"
case $a in 
	cat) echo "Feline";; #;; terminate the case
	dog|puppy) echo "Canine";;
	*) echo "No match!";;
esac
	
if [[ 14 == 14 ]]; then
	echo "true"
else
	echo "false"
fi

identifier="something"
# this matches some, .? means that it matches any character or if there is none
if [[ $identifier =~ some?ing ]]
then
  printf "\nIt's there!\n"
else
  printf "\nIt's NOT there\n"
fi