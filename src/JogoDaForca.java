import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class JogoDaForca {
	private ArrayList<String> palavras = new ArrayList<>(); // lista de palavras lidas do arquivo
	private ArrayList<String> dicas = new ArrayList<>(); // lista de dicas lidas do arquivo
	private String dica = ""; // dica da palavra sorteada
	private String[] letras; // letras da palavra sorteada
	private int acertos; // contador de acertos
	private int penalidade; // penalidade atual

	public JogoDaForca(String nomearquivo) throws Exception {	
		try {
			Scanner arquivo = new Scanner(new FileReader(nomearquivo));
			
			String entrada;
			String [] partes;
			while(arquivo.hasNextLine()) {
				entrada = arquivo.nextLine();
				partes = entrada.split(";");
				palavras.add(partes[0]);
				dicas.add(partes[1]);
			}
			
			arquivo.close();
		} catch (FileNotFoundException e) {
			throw new Exception("Arquivo inexistente.");
		}	
	}

	 public void iniciar() {
		 Random sorteio = new Random();
		 
		 int indice_sorteado = sorteio.nextInt(palavras.size());
		 String palavra_sorteada = palavras.get(indice_sorteado);
		 
		 letras = new String[palavra_sorteada.length()];
		 
		 for (int i=0; i < palavra_sorteada.length(); i++) {
			 letras[i] = Character.toString(palavra_sorteada.charAt(i));
		 }
		 
		 dica = dicas.get(indice_sorteado);
	 }
	
	 public String getDica() {
		 return dica;
	 }
	 
	 public int getTamanho() {
		 return letras.length;
	 }
	 
	 public ArrayList<Integer> getPosicoes(String letra) throws Exception {
		 if(letra.length() > 1) {
			 throw new Exception("Digite apenas um caractere.");
		 } else if (letra.matches("[0-9]")){
			 throw new Exception("Digite um caractere não numérico.");
		 } else if (!letra.matches("[a-zA-Z]")){
			 throw new Exception("Digite um caractere alfabético sem acento.");
		 }
		 
		 ArrayList<Integer> posicoes = new ArrayList<>();
		 
		 for (int i=0; i<letras.length; i++) {
			 if (letras[i].equalsIgnoreCase(letra)) {
				 letras[i] = "*";
				 acertos = acertos + 1;
				 posicoes.add(i);
			 }
		 }
		 
		 if(posicoes.size() < 1) {
			 penalidade = penalidade + 1;
		 }

		 return posicoes;
	 }
	 
	 public boolean terminou() {
		 if (acertos == letras.length || penalidade == 6) {
			 return true;
		 } else {
			 return false;
		 }
	 }
	 
	 public int getAcertos() {
		 return acertos;
	 }
	 
	 public int getPenalidade() {
		 return penalidade;
	 }
	 
	 public String getResultado() {
		 if(acertos == letras.length) {
			 return "Você venceu.";
		 } else if(penalidade == 6) {
			 return "Você foi enforcado.";
		 } else {
			 return "Jogo em andamento.";
		 }
	 }
}