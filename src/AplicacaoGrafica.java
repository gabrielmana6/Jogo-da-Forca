import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class AplicacaoGrafica {

	private JogoDaForca jogo;
	
	private String letras;
	private String[] letrasAdivinhadas; 	//letras adivinhadas
	private ArrayList<Integer> posicoes;	//posicoes adivinhadas
	private String[] arquivos = {"0.png", "1.png", "2.png", "3.png", "4.png", "5.png", "6.png",};
	
	private JFrame frame;
	private JButton button_iniciar;
	private JLabel label_tentativas;
	private JLabel label_digitaLetra;
	private JTextField textField_letra;
	private JButton button_ok;
	private JLabel label_erroLetra;
	private JLabel label_imagem;
	private JLabel label_letrasAdivinhadas;
	private JLabel label_resultado;
	private JLabel label_dica;
	private JLabel label_erroIniciar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AplicacaoGrafica window = new AplicacaoGrafica();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AplicacaoGrafica() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Jogo da Forca");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		//Imagem do aplicativo
		ImageIcon icon = new ImageIcon(AplicacaoGrafica.class.getResource("/imagens/0.png"));
		frame.setIconImage(icon.getImage());
		
		//Botão Iniciar
		button_iniciar = new JButton("Iniciar");
		button_iniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					label_erroIniciar.setText("");
					
					jogo = new JogoDaForca("palavras.csv");
					jogo.iniciar();
					
					posicoes = new ArrayList<Integer>();
					letrasAdivinhadas = new String[jogo.getTamanho()];
					Arrays.fill(letrasAdivinhadas, "_");
					
					button_ok.setEnabled(true);
					
					textField_letra.setEnabled(true);
					letras = "";
					for (String letra : letrasAdivinhadas) {
						letras = letras+ " " + letra;
					}
					label_letrasAdivinhadas.setText(letras);
					label_digitaLetra.setText("Digite uma letra:");
					label_tentativas.setText("Tentativas: 6");
					label_dica.setText("Dica: " + jogo.getDica());
					textField_letra.setText("");
					
					ImageIcon icon =new ImageIcon(AplicacaoGrafica.class.getResource("/imagens/"+ arquivos[0]));
					icon.setImage(icon.getImage().getScaledInstance(label_imagem.getWidth(), label_imagem.getHeight(), 1) );
					label_imagem.setIcon(icon);
				}
				catch(Exception ex) {
					label_erroIniciar.setText(ex.getMessage());
				}
			}
		});
		button_iniciar.setBounds(12, 223, 97, 25);
		frame.getContentPane().add(button_iniciar);
		
		label_tentativas = new JLabel("");
		label_tentativas.setBounds(12, 91, 222, 16);
		frame.getContentPane().add(label_tentativas);
		
		label_digitaLetra = new JLabel("");
		label_digitaLetra.setBounds(12, 33, 97, 16);
		frame.getContentPane().add(label_digitaLetra);
		
		textField_letra = new JTextField();
		textField_letra.setEnabled(false);
		textField_letra.setBounds(121, 30, 46, 22);
		frame.getContentPane().add(textField_letra);
		textField_letra.setColumns(10);
		
		//Botão OK
		button_ok = new JButton("OK");
		button_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					label_erroLetra.setText("");
					
					posicoes = jogo.getPosicoes(textField_letra.getText());
					
					if (posicoes.size() > 0) {
						for (int i : posicoes) {
							letrasAdivinhadas[i] = textField_letra.getText();
							
							letras = "";
							for (String letra : letrasAdivinhadas) {
								letras = letras+ " " + letra;
							}
							label_letrasAdivinhadas.setText(letras);
						}
					} else {
						label_tentativas.setText("Tentativas: " + Math.abs(jogo.getPenalidade() - 6));
						
						ImageIcon icon =new ImageIcon(AplicacaoGrafica.class.getResource("/imagens/"+ arquivos[jogo.getPenalidade()]));
						icon.setImage(icon.getImage().getScaledInstance(label_imagem.getWidth(), label_imagem.getHeight(), 1) );
						label_imagem.setIcon(icon);

					}
					
				} catch (Exception ex) {
					label_erroLetra.setText(ex.getMessage());
				} finally {					
					textField_letra.setText("");
					
					if(jogo.terminou()) {					
						button_ok.setEnabled(false);
						label_resultado.setText("Resultado: " + jogo.getResultado());
					}
				}
				
			}
		});
		button_ok.setEnabled(false);
		button_ok.setBounds(179, 29, 55, 25);
		frame.getContentPane().add(button_ok);
		
		label_erroLetra = new JLabel("");
		label_erroLetra.setForeground(Color.RED);
		label_erroLetra.setBounds(12, 62, 303, 16);
		frame.getContentPane().add(label_erroLetra);
		
		label_imagem = new JLabel("");
		label_imagem.setHorizontalAlignment(SwingConstants.CENTER);
		label_imagem.setBounds(325, 13, 97, 101);
		frame.getContentPane().add(label_imagem);
		
		label_letrasAdivinhadas = new JLabel("");
		label_letrasAdivinhadas.setHorizontalAlignment(SwingConstants.CENTER);
		label_letrasAdivinhadas.setBounds(287, 118, 135, 16);
		frame.getContentPane().add(label_letrasAdivinhadas);
		
		label_resultado = new JLabel("");
		label_resultado.setBounds(178, 227, 244, 16);
		frame.getContentPane().add(label_resultado);
		
		label_dica = new JLabel("");
		label_dica.setBounds(121, 147, 301, 16);
		frame.getContentPane().add(label_dica);
		
		label_erroIniciar = new JLabel("");
		label_erroIniciar.setForeground(Color.RED);
		label_erroIniciar.setBounds(12, 198, 410, 14);
		frame.getContentPane().add(label_erroIniciar);
	}
}
