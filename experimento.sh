#!/bin/bash

dirLogs="/home/tiago/Repositorios/Github/SistemasLineares/Logs/"

logJava=$dirLogs"LogJava.csv"
logCpp=$dirLogs"LogCpp.csv"
logPython=$dirLogs"LogPython.csv"

echo "Logs:"
echo $logJava
echo $logCpp
echo $logPython

javac GaussJava.java
g++ GaussCpp.cpp -o GaussCpp.run

#for i in 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
for i in 1 2
do
	for arquivo in /home/tiago/Repositorios/Github/SistemasLineares/Instancias/*
	do
		echo "$i:$arquivo" 
		java GaussJava $arquivo $logJava
		./GaussCpp.run $arquivo $logCpp
		python3 GaussPython.py $arquivo $logPython
	done
done
