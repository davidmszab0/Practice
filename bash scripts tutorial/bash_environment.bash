#!/bin/bash

#etc/profile is read by all shells

# run in debug mode: bash -x test.bash

#printenv #print global variables

#set #prints the local variables and functions

MYVAR1="2" #integer

first_name="Franky" #lowercase is a convention for local variables

echo first_name

#export VARNAME="value" #sets a variable global
#In order to pass variables to a subshell you need to export it

#bash #command creates a subshell


#positional parameters -> bash_environment.bash ONE TWO
#similar to arguments in Java

# This script reads 3 positional parameters and prints them out.
POSPAR1="$1"
POSPAR2="$2"
POSPAR3="$3"

echo "$1 is the first positional parameter, \$1."
echo "$2 is the second positional parameter, \$2."
echo "$3 is the third positional parameter, \$3."
echo
# $# shows the number of elements, e.g. $#VARIABLE ->how many elements are in VARIABLE parameter
echo "The total number of positional parameters is $#."

# ------------------ brace expansion ------------------
echo sp{el,il,al}l

# ------------------ tilde expansion ------------------
#~/foo       #$HOME/foo



#PARAMETERS - VERY GOOD
#http://wiki.bash-hackers.org/syntax/pe



# ------------------ parameter expansion ------------------
#https://www.gnu.org/software/bash/manual/html_node/Shell-Parameter-Expansion.html
VALUE="this is Some Text"
echo "print VALUE: $VALUE"
echo "no of elements in VALUE: ${#VALUE}" #no of elements in VALUE

echo ${VALUE^} #UPPER case the first letter

# ------------------ indirect expansion ------------------
NAME="VARIABLE"
VARIABLE=42
echo "direct expansion is: ${NAME}"
echo "indirect expansion is: ${!NAME}"

word="This is Word"
unset VALUE
#If parameter is unset or null, the expansion of word is substituted. Otherwise, the value of parameter is substituted.
echo ${USER:-word}
echo $USER
#If parameter is unset or null, the expansion of word is assigned to parameter. The value of parameter is then substituted.
echo ${USER:=word} #grace is unset
echo $USER


# ------------------ ARRAY EXPANSION ------------------
array=(This is an Array Text)
echo "${array[@]^^}" #@ refers to all elements in the array, [1] refers to 'This'

# ------------------ COMMAND substitution ------------------
echo `date`
echo $(date) # is preferred

echo $[365*24]

#set -o #display options

# ------------------ Arithmetic Expansion -------------------------
VAR1=1
echo "Var = $VAR1"
echo $(( 4*5 )) && (( VAR1 += 1 ))
(( VAR1++ ))
echo "Var = $VAR1"

hour=5
echo "hour is $((hour-1))"















































