#!/bin/bash
# Two regular expressions may be joined by the infix operator "|"
# the resulting regular expression matches any string matching 
# either subexpression

# GREP 
# check out the manual page -> 'man grep' - exit with 'q'

# http://www.tldp.org/LDP/Bash-Beginners-Guide/html/sect_04_02.html

#displays the lines from /etc/passwd containing the string root.
grep 'root' /etc/passwd

#displays the line numbers containing this search string
grep -n 'root' /etc/passwd

#With the third command she checks which users are not using bash, 
#but accounts with the nologin shell are not displayed.
#grep -v bash /etc/passwd | grep -v nologin

#Then she counts the number of accounts that have /bin/false as the shell
grep -c 'false' /etc/passwd

#excusively display lines starting with root
grep '^root' /etc/passwdq

# bash_profile is at /grace
# to see hidden files: 
#defaults write com.apple.finder AppleShowAllFiles YES
#killall Finder

grep '\<c...h\>' /usr/share/dict/words
grep '\<c.*h\>' /usr/share/dict/words
ls -ld [[:digit:]]*
ls -ld [[:upper:]]*

#grep -v is grep not
#grep -E 'pattern1|pattern2' filename

# --------------------- EXCERCISES -------------------------

#Display a list of all the users on your system who log in with the Bash shell as a default
cat /etc/passwd|grep 'bash'

#From the /etc/group directory, display all lines starting with the string "daemon".
grep 'daemon' /etc/group

#Print all the lines from the same file that don't contain the string
grep -v 'daemon' /etc/group

#it will cut (split) on colons -> https://linux.die.net/man/1/cut
w | cut -d:

#prints the first chunk of the cutted string
w | cut -d' ' -f1

#Display localhost information from the /etc/hosts file, display the line number(s) matching the search string and count the number of occurrences of the string
grep -n localhost /etc/hosts | cut -d: -f1   
grep -c localhost /etc/hosts 

#Display a list of /usr/share/doc subdirectories containing information about shells
# "*" would match all files and subdirectories; the "/" restricts it to directories
ls -d /usr/share/doc/*/ | grep bash

#How many README files do these subdirectories contain? Don't count anything in the form of "README.a_string".
ls -d /usr/share/doc/*/ | grep 'README' | grep -v 'README.a_string'