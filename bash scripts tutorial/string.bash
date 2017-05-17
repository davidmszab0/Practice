#!/bin/bash

a="hello"
b="world"
c=$a$b
echo $c
#length of string a
echo ${#a}
echo ${#c}

#get the characters after the 3rd char
d=${c:3}
echo $d

#from 3rd char 4 chars
e=${c:3:4}
echo $e

#negative number counts from the end
echo ${c: -4}
echo ${c: -4:3}

fruit="apple banana banana cherry"
#replace 1 banana 
echo ${fruit/banana/durian}
#replace all bananas
echo ${fruit//banana/durian}

#replaces the very beginning of the string if it is apple
echo ${fruit/#apple/durian}
#this won't match
echo ${fruit/#banana/durian}
#replaces the end of the string
echo ${fruit/%cherry/durian}
echo "matching"
echo ${fruit/c*/durian}

#https://devmanual.gentoo.org/tools-reference/bash/index.html#multiple-selection
if [[ -z $fruit ]]; then 
	echo "string is null" 
else
	echo "string is not null"
fi
#-z "string"	String has zero length
#-n "string"	String has non-zero length

#To check whether a variable is set and not blank, use -n "${BLAH}"
