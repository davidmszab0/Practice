#!/bin/bash

#control operator

#starting a new line, which is equivalent to ; and tells bash to just run the 
#command and wait for it to end before advancing to the next command in the list.
cd music; mplayer *.mp3

#If the command before it didn't fail, the || operator will make bash skip the command after it. 
rm hello.txt || echo "Couldn't delete hello.txt." >&2

#everything inside the braces { ... } is considered a single command - { list ; }
rm hello.txt || { echo "Couldn't delete hello.txt." >&2; exit 1; }

# run a command asynchronously 
# coproc [ name ] command [ redirection ... ]

# ctrl + r # search in the last used functions

#If you're ever curious about exactly where bash finds the program to run for a 
#command name, you can use the type built-in to find out

type ping
type -a echo

#You should use "double quotes" for any argument that contains expansions 
#(such as $variable or $(command) expansions) and 
#'single quotes' for any other arguments. 

echo "Good morning, $USER."
echo 'You have won SECOND PRIZE in a beauty contest.'

# [[]] double brackets is for condition testing
if [[ 14 == 14 ]]; then
	echo "true"
else
	echo "false"
fi


# ------------------- REDIRECTION ------------------

# http://guide.bash.academy/02.commands.html#toc12

# [x]>file, [x]<file - write or read
# Make FD x write to / read from file.

read line <file

# standard input (FD 0), standard output (FD 1) and standard error (FD 2)

# redirect the shell ls command to a file instead
ls -l a b >myfiles.ls
# Redirecting standard output is done using the > operator

cat example.html

# We redirect FD 1 to the file "myfiles.ls" and FD 2 to the file "/dev/null"
ls -l a b >myfiles.ls 2>/dev/null
# the null device is always empty and anything you write to it is lost

# to send the output and error to the same file, it will be a garbage
ls -l a b >myfiles.ls 2>myfiles.ls
# to solve this problem, you need to send both your output and error bytes on 
# the same stream. And to do that, you're going to need to know how 
#to duplicate file descriptors:
ls -l a b >myfiles.ls 2>&1
# 2>&1 as Make FD 2 write(>) to where FD(&) 1 is currently writing.

# this will not work, because FD1 is directed to the terminal as standard, 
# FD1 hasnt been redirected
ls -l a b 2>&1 >myfiles.ls

# -------------------------------- FILE DESCRIPTOR COPYING
# [x]>&y, [x]<&y
# Make FD x write to / read from FD y's stream.

ping 127.0.0.1 >results 2>&1

# - Appending file redirection
#    [x]>>file
echo World >>~/world
# Make FD x append to the end of file.
# In append mode (>>), the file's existing contents is left and 
#your stream's bytes are added to the end of it.


# - Redirecting standard output and standard error
#    &>file

ping 127.0.0.1 &>results
# Make both FD 1 (standard output) and FD 2 (standard error) write to file.

#This is a convenience operator which does the same thing as >file 2>&1 
#but is more concise. Again, you can append rather than truncate 
#by doubling the arrow: &>>file

# -------------------------------- Here Documents
#    <<[-]delimiter
 #       here-document
#    delimiter

cat <<.
Hello world.
.
# Make FD 0 (standard input) read from the string between the delimiters.
# if expansion is not desired, you need to put quotes around your 'delimiter''s initial declaration

entity1=$(cat << EOM
  {
    "username" : "erik_${uuid1}",
    "email" : "$email1"
  }
EOM
)
# EOF == end of document
$ cat <<EOF
> hi
> there
EOF
#will output 
hi
there

# -------------------------------- Here Strings
#    <<<string
cat <<< "Hello world."
# Make FD 0 (standard input) read from the string.




