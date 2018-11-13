
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/*
*   O argumento do método calcular é a matriz que representa o sistema linear à ser resolvido.
*   Este algoritmo esta pronto, incluive com a troca de linha em caso de elemento da diagonal principal
*   igual à zero,  
*   Possue ainda a checagem para ver se o sistema é:
*   Impossível: após a escalonagem a ultima linha deve possuir apenas elementos iguais a zero com 
*   excessão da ultima posição. ex.: 0 0 0 6.
*   Indeterminado: Após a escalonagem a ultima linha possui apenas elementos iguais à zero.
*   Nestes dois casos é retornado null, ainda não trabalhei em como diferenciar os dois casos.
*   Caso o sistema seja Determinado é realizado o cálculo das respostas e é retornado o vetor com as respostas.
*/

public class GaussJava {
    
    public static double[][] ler(String arquivo) throws FileNotFoundException, IOException{
        int ordem;
        double[][] matriz;
        String[] valores;
        
        BufferedReader br;
        
        br = new BufferedReader( new FileReader(arquivo));
        
        ordem = Integer.parseInt( br.readLine() );
        
        matriz = new double[ordem][ordem+1];
        
        for (int i = 0; i < ordem; i++) {
            valores = br.readLine().split(" ");
            for (int j = 0; j < ordem+1; j++) {
                matriz[i][j] = Double.parseDouble( valores[j] );
            }
        }
        br.close();
        
        return matriz;
    }
    
    public static double[] calcular(double[][] sistema) {
        int n = sistema.length - 1;
        double m, aux;

        //Inicia o escalonamento da matriz
        for (int i = 0; i < n; i++) {

            //Se algum elemento da diagonal principal é igual a zero é iniciado a troca de linhas.
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
            // Finaliza a troca de linhas.
            
            //Aonde inicia o escalonamento em si.
            for (int j = i + 1; j <= n; j++) {
                m = - (sistema[j][i] / sistema[i][i]);
                for (int k = i; k <= n + 1; k++) {
                    sistema[j][k] += m * sistema[i][k];
                }
            }
            ////Aonde finaliza o escalonamento em si.
        }
        //Finaliza o escalonamento da matriz.

        //inicia a checagem para ver se o sistema é impossível, indeterminado ou determinado.
        if (sistema[n][n] == 0 && sistema[n][n + 1] == 0) {
            return null;
        } else if (sistema[n][n] == 0 && sistema[n][n + 1] != 0) {
            return null;
        } else {
            //Inicia o calculo das respostas.
            double[] respostas = new double[n + 1];
            respostas[n] = sistema[n][n + 1] / sistema[n][n];
            double soma;
            for (int i = n - 1; i >= 0; i--) {
                soma = 0;
                for (int j = i + 1; j <= n; j++) {
                    soma += sistema[i][j] * respostas[j];
                    respostas[i] = (sistema[i][j + 1] - soma) / sistema[i][i];
                }
            }
            //Finaliza o cálculo das respostas.

            return respostas;
        }
        //Finaliza a checagem para ver se o sistema é impossível, indeterminado ou determinado.

    }
    
    public static void main(String[] args) {
        long tempoInicialLeitura, tempoFinalLeitura;
        long tempoInicialCalculo, tempoFinalCalculo;
        double resultado[];
        double[][] matriz;
        
        try {
            tempoInicialLeitura = System.currentTimeMillis();
            matriz = ler( args[0] );
            tempoFinalLeitura = System.currentTimeMillis();
            
            tempoInicialCalculo = System.currentTimeMillis();
            resultado = calcular( matriz );
            tempoFinalCalculo = System.currentTimeMillis();
            
            for (int i = 0; i < resultado.length; i++) {
                System.out.print( String.format( "%.2f", resultado[i] ) + " ");
            }
            System.out.println();
            System.out.println("Leitura: " + (tempoFinalLeitura - tempoInicialLeitura) );
            System.out.println("Cálculo: " + (tempoFinalCalculo - tempoInicialCalculo) );
            System.out.println("\n\n");
            
            System.out.println(tempoInicialLeitura + "-" + tempoFinalLeitura);
            System.out.println(tempoInicialCalculo + "-" + tempoFinalCalculo);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}