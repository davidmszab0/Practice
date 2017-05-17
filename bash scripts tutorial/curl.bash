#!/bin/bash

# https://quickleft.com/blog/command-line-tutorials-curl/

#curl http://www.google.com

# to indent the html code
#curl http://www.google.com | tidy -i

# confirmation graph of curl's download
#curl http://www.google.com | pbcopy

curl http://www.google.com | grep "<title>"