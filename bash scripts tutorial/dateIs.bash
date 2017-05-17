#!/bin/bash

now=$(date +"%Y-%m-%dT%H:%M:%S")
year=$(date +"%Y")
month=$(date +"%m")
day=$(date +"%d")
day=$day-10
hour=$(date +"%H")
hour=$hour-5
minute=$(date +"%M")
minute=1
second=$(date +"%S")
second=1
#time1="$year-$month-${day}T$hour:$minute:$second"
time1="$year-$month-${day}T"$(( hour+=1 ))":$minute:$second"
printf "\n date is: $(date) \n"
printf "\n time is: $time1 \n"

#if(dd1<10) { /*if only one character add a 0 -> 01, 02*/
#    dd1='0'+dd1
#} 
(( day+=1 ))
if [ $day -lt 9 ]; then
	day="0"$day
fi
printf "\n day is: $day \n"

(( hour+=1 ))
if [ $hour -lt 9 ]; then
	hour="0"$hour
fi
printf "\n hour is: $hour \n"

(( minute+=1 ))
if [ $minute -lt 9 ]; then
	minute="0"$minute
fi
printf "\n minute is: $minute \n"

(( second+=1 ))
if [ $second -lt 9 ]; then
	second="0"$second
fi
printf "\n second is: $second \n"


time2="$year-$month-${day}T$hour:$minute:$second"
time3="2017-04-07T08:04:05.028Z"
time4="2017-04-07T12:04:05.028Z"
printf "\n time2 is: $time3 \n"

#hour01="$(echo $time2" '+%H')"
#printf "\n hour01 is: $hour01 \n"
if [[ "$time3" =~ T([0-9]+) ]]; then 
 	hour02="$(echo ${BASH_REMATCH[1]})"
fi

if [[ "$time3" =~ ([0-9]+)-([0-9]+)-([0-9]+) ]]; then
	year02="$(echo ${BASH_REMATCH[0]})"
fi
echo $year02

if [[ "$time4" =~ T([0-9]+) ]]; then 
 	hour04="$(echo ${BASH_REMATCH[1]})"
fi

if [[ "$time4" =~ ([0-9]+)-([0-9]+)-([0-9]+) ]]; then
	year04="$(echo ${BASH_REMATCH[0]})"
fi
echo $year04

if [[ $year02 == $year04 ]]; then
	if [[ $hour02 != $hour04 ]]; then
		echo "match!"
	fi 
fi

