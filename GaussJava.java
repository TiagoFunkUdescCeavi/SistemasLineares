
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class GaussJava {
    
    public static double[][] ler(String arquivo) throws FileNotFoundException, IOException{
        int ordem;
        double[][] matriz;
        String[] valores;
        
        BufferedReader br;
        
        br = new BufferedReader( new FileReader(arquivo));
        
        ordem = Integer.parseInt( br.readLine().trim() );
        
        matriz = new double[ordem][ordem+1];
        
        for (int i = 0; i < ordem; i++) {
            valores = br.readLine().trim().split(" ");
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
        double soma;

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

        double[] respostas = new double[n + 1];
        respostas[n] = sistema[n][n + 1] / sistema[n][n];
        for (int i = n - 1; i >= 0; i--) {
            soma = 0;
            for (int j = i + 1; j <= n; j++) {
                soma += sistema[i][j] * respostas[j];
                respostas[i] = (sistema[i][j + 1] - soma) / sistema[i][i];
            }
        }

        return respostas;

    }
	
    public static void escrever(String arquivo, String s)throws FileNotFoundException, IOException{
    	BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo,true));
    	bw.write(s);
    	bw.newLine();
    	bw.close();
    }
    
    public static void main(String[] args) {
        long tempoInicialLeitura, tempoFinalLeitura;
        long tempoInicialCalculo, tempoFinalCalculo;
        double resultado[];
        double[][] matriz;
        String arquivo = args[0];
        String arquivoLog = args[1];
        
        try {
            tempoInicialLeitura = System.currentTimeMillis();
            matriz = ler( arquivo );
            tempoFinalLeitura = System.currentTimeMillis();
            
            tempoInicialCalculo = System.currentTimeMillis();
            resultado = calcular( matriz );
            tempoFinalCalculo = System.currentTimeMillis();

            escrever(
            	arquivoLog, arquivo + ";" +
            	String.valueOf( tempoFinalLeitura - tempoInicialLeitura ) + ";" +
            	String.valueOf( tempoFinalCalculo - tempoInicialCalculo ) 
            );
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
