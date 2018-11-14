#include <iostream>
using std::cout;
using std::cerr;
using std::endl;
using std::ios;

#include <fstream>
using std::ifstream;

#include <cstdlib>
using std::exit;

#include <time.h>

int lerOrdem(char* arquivo){
    int ordem;
    
    ifstream arq( arquivo, ios::in );
    if( !arq ){
        cerr << "Arquivo não pode ser aberto." << endl;
        exit( 1 );
    }
    
    arq >> ordem;
    
    arq.close();
    
    return ordem;
}

double** lerMatriz(char* arquivo){
    int ordem;
    double** matriz;
    
    ifstream arq( arquivo, ios::in );
    if( !arq ){
        cerr << "Arquivo não pode ser aberto." << endl;
        exit( 1 );
    }
    
    arq >> ordem;
    matriz = (double**) malloc( (ordem) * sizeof( double * ) );
    for (int i = 0; i < ordem; i++) {
        matriz[i] = (double *) malloc( (ordem+1)*sizeof( double ) );
    }

    for (int i = 0; i < ordem; i++) {
        for (int j = 0; j < ordem+1; j++) {
            arq >> matriz[i][j];
        }
    }
    
    arq.close();
    
    return matriz;
}

double* calcular(int ordem, double** sistema){
    int n = ordem - 1;
    double m, aux, soma;
    double* respostas;
    
    for (int i = 0; i < n; i++) {
        if (sistema[i][i] == 0 && i != n) {
            for (int j = i; j <= n; j++) {
                if (sistema[j][i] != 0 && j != i) {
                    for (int k = 0; k <= n + 1; k++) {
                        aux = sistema[i][k];
                        sistema[i][k] = sistema[j][k];
                        sistema[j][k] = aux;
                    }
                    break;
                }
            }
        }

        for (int j = i + 1; j <= n; j++) {
            m = -1 * (sistema[j][i] / sistema[i][i]);
            for (int k = 0; k <= n + 1; k++) {
                sistema[j][k] += m * sistema[i][k];
            }
        }
    }
    
    respostas = (double*) malloc( (n + 1) * sizeof( double ) );
    respostas[n] = sistema[n][n+1] / sistema[n][n];
    for (int i = n - 1; i >= 0; i--) {
        soma = 0;
        for (int j = i + 1; j <= n; j++) {
            soma += sistema[i][j] * respostas[j];
            respostas[i] = (sistema[i][j + 1] - soma) / sistema[i][i];
        }
    }
    
    return respostas;
}

int main(int argc, char** argv) {
    int ordem;
    double* respostas;
    double** matriz;
    clock_t tempoInicialLeitura, tempoFinalLeitura;
    clock_t tempoInicialCalculo, tempoFinalCalculo;
    
    ordem = lerOrdem( argv[1] );
    
    tempoInicialLeitura = clock();
    matriz = lerMatriz( argv[1] );
    tempoFinalLeitura = clock();
    
    tempoInicialCalculo = clock();
    respostas = calcular(ordem, matriz);
    tempoFinalCalculo = clock();
    
    for (int i = 0; i < ordem; i++) {
        cout << respostas[i] << " ";
    }
    cout << endl;
    
    cout.precision(17);
    double div = (double) CLOCKS_PER_SEC / 1000;
    
    cout << "Leitura: " << (tempoFinalLeitura - tempoInicialLeitura) << endl;
    cout << "Leitura: " << (tempoFinalLeitura - tempoInicialLeitura) / div << endl;
    
    cout << "Leitura: " << (tempoFinalCalculo - tempoInicialCalculo) << endl;
    cout << "Leitura: " << (tempoFinalCalculo - tempoInicialCalculo) / div << endl;
    
    return 0;
}

