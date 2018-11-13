#include <iostream>
using std::cout;
using std::cerr;
using std::endl;
using std::ios;

#include <string>
using std::string;

#include <fstream>
using std::ifstream;

#include <cstdlib>
using std::exit;

int lerOrdem(string arquivo){
    int ordem;
    
    ifstream arq(arquivo.c_str(), ios::in);
    if( !arq ){
        cerr << "Arquivo não pode ser aberto." << endl;
        exit( 1 );
    }
    
    arq >> ordem;
    
    arq.close();
    
    return ordem;
}

double** lerMatriz(string arquivo){
    int ordem;
    double** matriz;
    
    ifstream arq(arquivo.c_str(),ios::in);
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

int main(int argc, char** argv) {
    int ordem;
    double** m;
    const string arquivo = "/home/tiago/Repositorios/Github/SistemasLineares/Instancias/b.txt";
    
    ordem = lerOrdem(arquivo);
    m = lerMatriz(arquivo);
    
    for (int i = 0; i < ordem; i++) {
        for (int j = 0; j < ordem+1; j++) {
            cout << m[i][j] << " ";
        }
        cout << endl;
    }
    
    return 0;
}

