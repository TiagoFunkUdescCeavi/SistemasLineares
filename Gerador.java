import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Gerador{

	public static void main(String[] args){
		int ordem = Integer.parseInt( args[0] );
		double[] resultado = new double[ ordem ];
		double[][] sistema = new double[ ordem ][ ordem+1 ];
		Random sorteador = new Random();
		
		// Cria a resposta do sistema.
		for(int i = 0; i < ordem; i++){
			resultado[i] = sorteador.nextDouble()*10;
		}
		
		//imprimirVetor( resultado );
		
		// Cria o sistema.
		for(int i = 0; i < ordem; i++){
			for(int j = 0; j < ordem; j++){
				sistema[i][j] = sorteador.nextDouble()*10;
				if( sorteador.nextBoolean() ){
					sistema[i][j] *= -1.0;
				}
				
				// Vai cálculando o resultado final.
				sistema[i][ ordem ] += sistema[i][j]*resultado[j];
			}
		}
		
		//imprimirMatriz( sistema );
		try{
			escrever( ordem, resultado, sistema );
		} catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println( ordem );
	}
	
	private static final String CAMINHO_SISTEMA = "/home/tiago/Repositorios/Github/SistemasLineares/Instancias/";
	private static final String CAMINHO_RESULTADO = "/home/tiago/Repositorios/Github/SistemasLineares/Respostas/";
	
	private static void escrever(int ordem, double[] resultado, double[][] sistema) throws FileNotFoundException, IOException{
		// System.out.println( CAMINHO_SISTEMA + ordem + ".txt" );
    	BufferedWriter bw = new BufferedWriter( new FileWriter( CAMINHO_SISTEMA + ordem + ".txt" ) );
    	for(int i = 0; i < ordem; i++){
    		for(int j = 0; j < ordem+1; j++){
    			bw.write( String.format( "%.2f", sistema[i][j] ) + " ");
    		}
    		bw.newLine();
    	}
    	bw.close();
    	
    	// System.out.println( CAMINHO_RESULTADO + ordem + ".txt" );
    	bw = new BufferedWriter( new FileWriter( CAMINHO_RESULTADO + ordem + ".txt" ) );
    	for(int i = 0; i < ordem; i++){
   			bw.write( String.format( "%.2f", resultado[i] ) + " ");
    	}
    	bw.close();
	}
	
	private static void imprimirVetor(double[] vetor){
		for(int i = 0; i < vetor.length; i++){
			System.out.print( String.format("%.2f", vetor[i] ) + " " );
		}
		System.out.println();
	}
	
	private static void imprimirMatriz(double[][] matriz){
		for(int i = 0; i < matriz.length; i++ ){
			for(int j = 0; j < matriz[i].length; j++ ){
				System.out.print( String.format("%.2f", matriz[i][j] ) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
