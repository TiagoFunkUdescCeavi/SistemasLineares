
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeitorLog {
    
    private String texto;
    private List<Dupla> listaDuplas;
    private List<Tupla> listaTuplas;
    private Map<String, List<Tupla> > hashMapTuplas = new HashMap<>();
    
    private int numeroRepiticoes;

    private String arquivo;
    private String arqFinal = "tabela%.tex";

    public LeitorLog(String arquivo, int numeroRepiticoes, String arqFinal) {
        super();
        this.arquivo = arquivo;
        this.numeroRepiticoes = numeroRepiticoes;
        this.arqFinal = arqFinal;
    }

    public void ler() {
        try {
            String linha;
            String[] valores;
            listaTuplas = new ArrayList<>();
            
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            while ((linha = br.readLine()) != null) {
                valores = linha.split(";");
                valores[0] = 
                    valores[0].substring( 
                        valores[0].lastIndexOf("/")+1,
                        valores[0].lastIndexOf(".")
                    );
                if( valores[0].equals("3") ||
                    valores[0].equals("4") ||
                    valores[0].equals("5") ||
                    valores[0].equals("10") ||
                    valores[0].equals("15") ){
                    // Nada a ser feito.
                }else{
                    listaTuplas.add( 
                        new Tupla(
                            valores[0],
                            Double.parseDouble(valores[1]),
                            Double.parseDouble(valores[2])
                        )
                    );
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void separar(){
        hashMapTuplas = new HashMap<>();
        for( Tupla t: listaTuplas ){
            if( !hashMapTuplas.containsKey( t.getInstancia() ) ){
                List<Tupla> l = new ArrayList<>();
                l.add( t );
                hashMapTuplas.put(t.getInstancia(), l );
            }else{
                List<Tupla> l = hashMapTuplas.get( t.getInstancia() );
                l.add( t );
                hashMapTuplas.put( t.getInstancia(), l );
            }
        }
    }
    
    public void processar(){
        final String cabecalho =
            "\\begin{landscape}\n" +
            "\t\\begin{table}[ht]\n" +
            "\t\\centering\n" +
            "\t\\begin{tabular}{| c | r | r | r | r | r | r | r | r | }\n" +
            "\\hline\n";
        
        final String rodape =
            "\\hline\n" +
            "\t\\end{tabular}\n" +
            "\t\\caption{Legenda da tabela}\n" +
            "\t\\label{seu_label}\n" +
            "\t\\end{table}\n" +
            "\\end{landscape}\n";
        
        texto = "Ordem&"
                + "Média Leitura&"
                + "Des pad Leitura&"
                + "Maior Leitura&"
                + "Menor Leitura&"
                + "Média Cálculo&"
                + "Des Pad Cálculo&"
                + "Maior Cálculo&"
                + "Menor Cálculo"
                + "\\\\ \\hline \n";
        
        double mediaLeitura, desvioPadraoLeitura, maiorLeitura, menorLeitura,
                mediaCalculo, desvioPadraoCalculo, maiorCalculo, menorCalculo;
        double aux;
        String linha;
        listaDuplas = new ArrayList<>();
        
        for( String chave: hashMapTuplas.keySet() ){
            mediaLeitura = 0;
            desvioPadraoLeitura = 0;
            maiorLeitura = 0;
            menorLeitura = Double.MAX_VALUE;
            mediaCalculo = 0;
            desvioPadraoCalculo = 0;
            maiorCalculo = 0;
            menorCalculo = Double.MAX_VALUE;
            linha = "";
            
            for( Tupla t: hashMapTuplas.get( chave ) ){
                aux = t.getTempoLeitura();
                if( aux > maiorLeitura ){
                    maiorLeitura = aux;
                }
                if( aux < menorLeitura ){
                    menorLeitura = aux;
                }
                mediaLeitura += aux;
                
                aux = t.getTempoCalculo();
                if( aux > maiorCalculo ){
                    maiorCalculo = aux;
                }
                if( aux < menorCalculo){
                    menorCalculo = aux;
                }
                mediaCalculo += aux;
            }
            
            mediaLeitura /= numeroRepiticoes;
            mediaCalculo /= numeroRepiticoes;
            
            for( Tupla t: hashMapTuplas.get( chave ) ){
                desvioPadraoLeitura += 
                    Math.pow(t.getTempoLeitura() - mediaLeitura, 2);
                
                desvioPadraoCalculo += 
                    Math.pow(t.getTempoCalculo() - mediaCalculo, 2);
            }
            
            desvioPadraoLeitura /= numeroRepiticoes;
            desvioPadraoCalculo /= numeroRepiticoes;
            
            desvioPadraoLeitura = Math.sqrt( desvioPadraoLeitura );
            desvioPadraoCalculo = Math.sqrt( desvioPadraoCalculo );
            
            String f = "%.2f";
                linha = 
                    chave + "&" +
                    String.format(f, mediaLeitura ) + "&" +
                    String.format(f, desvioPadraoLeitura ) + "&" + 
                    String.format(f, maiorLeitura ) +"&" + 
                    String.format(f, menorLeitura ) + "&" +
                    String.format(f, mediaCalculo ) + "&" + 
                    String.format(f, desvioPadraoCalculo ) + "&" +
                    String.format(f, maiorCalculo ) + "&" +
                    String.format(f, menorCalculo );
            linha = "\t\t" + linha + "\\\\\n";
            listaDuplas.add( new Dupla( Integer.parseInt( chave ), linha) );
        }

        for (int i = 0; i < listaDuplas.size(); i++) {
            for (int j = i+1; j < listaDuplas.size(); j++) {
                if( listaDuplas.get(i).getOrdem() > listaDuplas.get(j).getOrdem() ){
                    Dupla d = listaDuplas.get(i);
                    listaDuplas.set( i, listaDuplas.get(j) );
                    listaDuplas.set(j, d);
                }
            }
        }

        texto = cabecalho + texto;
        for (int i = 0; i < listaDuplas.size(); i++) {
            texto += listaDuplas.get(i).getLinha();
        }
        texto += rodape;

    }
    
    public void escrever(){
        FileWriter writer;
        try {
            writer = new FileWriter( arqFinal, false);
            writer.write(texto);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        LeitorLog ll = new LeitorLog( args[0], Integer.parseInt(args[1]), args[2] );
        ll.ler();
        ll.separar();
        ll.processar();
        ll.escrever();
    }
    
    private class Dupla{
        
        private int ordem;
        private String linha;

        public Dupla() {
        }

        public Dupla(int ordem, String linha) {
            this.ordem = ordem;
            this.linha = linha;
        }

        public int getOrdem() {
            return ordem;
        }

        public void setOrdem(int ordem) {
            this.ordem = ordem;
        }

        public String getLinha() {
            return linha;
        }

        public void setLinha(String linha) {
            this.linha = linha;
        }
    }

    private class Tupla {

        private String instancia;
        private double tempoLeitura;
        private double tempoCalculo;

        public Tupla() {
            super();
        }

        public Tupla(String instancia, double tempoLeitura, double tempoCalculo) {
            super();
            this.instancia = instancia;
            this.tempoLeitura = tempoLeitura;
            this.tempoCalculo = tempoCalculo;
        }

        public String getInstancia() {
            return instancia;
        }

        public void setInstancia(String instancia) {
            this.instancia = instancia;
        }

        public double getTempoLeitura() {
            return tempoLeitura;
        }

        public void setTempoLeitura(double tempo) {
            this.tempoLeitura = tempo;
        }

        public double getTempoCalculo() {
            return tempoCalculo;
        }

        public void setTempoCalculo(double solucao) {
            this.tempoCalculo = solucao;
        }

        @Override
        public String toString() {
            return "Tupla{" + "instancia=" + instancia + ", tempoLeitura=" + tempoLeitura + ", tempoCalculo=" + tempoCalculo + '}';
        }

    }

}
