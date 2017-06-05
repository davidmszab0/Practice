Nothing special to setup and run.
Needs MySQL java connector.
Import included SQL script into MySQL server to create database.
Add user with read and write privileges to the database.
user: input_user 
password: input_user


Compile and run Start for both clients.

To connect to mysql database on docker -> 
$ docker run --name friendfinder -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -v /Users/grace/git/github/Practice/android tutorials/Project/mysql_db_persistent:/var/lib/mysql -d mysql

Then in mysql WorkBench connect to 
127.0.0.1:3306
user name: “root” 
password: “”
