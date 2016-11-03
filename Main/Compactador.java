package Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.plaf.FileChooserUI;

import Classes.ManipulaByte;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class Compactador {

	private JFrame frmCompactador;
	private ManipulaByte bit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Compactador window = new Compactador();
					window.frmCompactador.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Compactador() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCompactador = new JFrame();
		frmCompactador.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				try 
				{
					bit = new ManipulaByte();
				} catch (Exception e) {System.err.println(e.getMessage());}
			}
		});
		frmCompactador.setTitle("Ger\u00EAnciador de Compacta\u00E7\u00E3o de Arquivos Textos");
		frmCompactador.setBounds(100, 100, 625, 193);
		frmCompactador.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCompactador.getContentPane().setLayout(null);
		
		JLabel lblNomeArquivo = new JLabel("");
		lblNomeArquivo.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblNomeArquivo.setBounds(237, 92, 346, 14);
		frmCompactador.getContentPane().add(lblNomeArquivo);
		
		JLabel lblBemVindoAo = new JLabel("Bem Vindo ao Compactador de Arquivos Texto");
		lblBemVindoAo.setFont(new Font("Consolas", Font.PLAIN, 26));
		lblBemVindoAo.setBounds(10, 11, 597, 24);
		frmCompactador.getContentPane().add(lblBemVindoAo);
		
		JLabel lblEscolhaUmaDas = new JLabel("Escolha uma das Op\u00E7\u00F5es Abaixo");
		lblEscolhaUmaDas.setFont(new Font("Consolas", Font.PLAIN, 22));
		lblEscolhaUmaDas.setBounds(126, 46, 356, 24);
		frmCompactador.getContentPane().add(lblEscolhaUmaDas);
		
		JLabel label = new JLabel("Nome e Path do Arquivo:");
		label.setFont(new Font("Consolas", Font.PLAIN, 16));
		label.setBounds(20, 92, 207, 14);
		frmCompactador.getContentPane().add(label);
		
		JButton btnCompactar = new JButton("Compactar Arquivo");
		btnCompactar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try 
				{
					bit.setNomeDoArquivo(lblNomeArquivo.getText());
					bit.compilaArquivo();
					JOptionPane.showMessageDialog(null, "Compilado com Sucesso");
				} catch (Exception e) {JOptionPane.showMessageDialog(null, e.getMessage());}
			}
		});
		btnCompactar.setFont(new Font("Consolas", Font.PLAIN, 14));
		btnCompactar.setBounds(199, 117, 175, 23);
		frmCompactador.getContentPane().add(btnCompactar);
		
		JButton btnEscolher = new JButton("Escolher Arquivo");
		btnEscolher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fl = new JFileChooser();
				fl.showOpenDialog(null);
				lblNomeArquivo.setText(fl.getSelectedFile().toString());
			}
		});
		btnEscolher.setFont(new Font("Consolas", Font.PLAIN, 14));
		btnEscolher.setBounds(20, 117, 169, 23);
		frmCompactador.getContentPane().add(btnEscolher);
		
		JButton btnDescompactar = new JButton("Descompactar Arquivo");
		btnDescompactar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try 
				{
					bit.setNomeDoArquivo(lblNomeArquivo.getText());
					bit.descompilaArquivo();
					JOptionPane.showMessageDialog(null, "Descompilado com Sucesso");
				} catch (Exception e) {System.err.println(e.toString());}
			}
		});
		btnDescompactar.setFont(new Font("Consolas", Font.PLAIN, 14));
		btnDescompactar.setBounds(384, 117, 199, 23);
		frmCompactador.getContentPane().add(btnDescompactar);
	}
}