def ler(arquivo):
	ordem = -1
	matriz = []

	arq = open(arquivo)

	texto = arq.readlines()

	ordem = int( texto[0] )

	for i in range( 0, len( texto ) ) :
		if i != 0:
			numeros = texto[i].split(" ")
			tamanho = len( numeros )
			matriz.append( [] )
			for j in range( 0, tamanho ):
				matriz[i-1].append( int( numeros[j] ) )

	arq.close()
	
	return matriz

def calcular( matriz ):
	n = len( matriz ) - 1
	soma = 0
	aux = 0

import sys

matriz = ler( sys.argv[1] )
#print( matriz )

calcular( matriz )


