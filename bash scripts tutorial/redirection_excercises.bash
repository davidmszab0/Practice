#!/bin/bash

# http://guide.bash.academy/02.commands.html#

# Excercises

# REDIR.1. Run a command that produces a message on standard output

# echo "hello"

# REDIR.2. Run a command that produces a message on standard error
#ls /bob/bash

# REDIR.3. Run a command that produces messages on both standard output and standard error.
# ls /bin/bash /bob/bash

# REDIR.4. Send only the last command's standard error messages to a file called 
# errors.log. Then show the contents of errors.log on the terminal.

#ls /Users/grace/Desktop/scripts /bob/bash 2>errors.log

#echo "cat message is"; cat errors.log 

# REDIR.5. Append the last command's standard output and error messages to the 
# file called errors.log. Then show the contents of errors.log on the 
# terminal again.

#ls /bin/bash /bob/bash >>errors.log 2>&1

#echo "cat message is"; cat errors.log 

# REDIR.6. Use a here-string to show the string Hello world! on the terminal.

echo <<< 'Hello world!'

#REDIR.7. Fix this command so that the message is properly saved into the 
# log file and such that FD 3 is properly closed afterwards: 
# exec 3>&2 2>log; echo 'Hello!'; exec 2>&3

# exec 3>&2 >log; echo 'Hello!'; exec 1>&3 3>&-