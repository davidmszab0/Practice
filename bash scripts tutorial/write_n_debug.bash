#!/bin/bash

#chmod u+x script1.sh

#bash -x write_n_debug.bash 

set -x			# activate debugging from here
w				#print user info
set +x			# stop debugging from here

COLOUR="black"					# set a local shell variable
VALUE="9"					# set a local shell variable
echo "This is a string: $COLOUR"		# display content of variable 
echo "And this is a number: $VALUE"		# display content of variable

# run in debug mode: bash -x test.bash
echo "hello $USER"

#source write_n_debug.bash #??

#https://devmanual.gentoo.org/tools-reference/bash/index.html#multiple-selection

source ./function.bash
#The $(command) construct can be used to run a command and capture the output (stdout) as a string.
#Note: The `command` construct also does this, 
#but should be avoided in favour of $(command ) for clarity, ease of reading and nesting purposes

echo "test count function"
echo $(count)


