#!/bin/bash

dirInstancia="/home/tiago/Repositorios/Github/SistemasLineares/Instancias/"
dirResultados="/home/tiago/Repositorios/Github/SistemasLineares/Respostas/"

for i in 3 4 5 10 15 100 110 120 130 140 150 160 170 180 190 200 210 220 230 240 250 260 270 280 290 300
do
	java Gerador $i $dirInstancia $dirResultados
done
