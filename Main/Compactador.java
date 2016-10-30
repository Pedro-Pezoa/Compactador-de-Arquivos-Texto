package Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import Classes.ManipulaByte;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Compactador {

	private JFrame frmCompactador;
	private JTextField txtNomeArq, txtPath;
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
		frmCompactador.setBounds(100, 100, 625, 227);
		frmCompactador.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCompactador.getContentPane().setLayout(null);
		
		JLabel lblBemVindoAo = new JLabel("Bem Vindo ao Compactador de Arquivos Texto");
		lblBemVindoAo.setFont(new Font("Consolas", Font.PLAIN, 26));
		lblBemVindoAo.setBounds(10, 11, 597, 24);
		frmCompactador.getContentPane().add(lblBemVindoAo);
		
		JLabel lblEscolhaUmaDas = new JLabel("Escolha uma das Op\u00E7\u00F5es Abaixo");
		lblEscolhaUmaDas.setFont(new Font("Consolas", Font.PLAIN, 22));
		lblEscolhaUmaDas.setBounds(126, 46, 356, 24);
		frmCompactador.getContentPane().add(lblEscolhaUmaDas);
		
		JLabel lblNewLabel = new JLabel("Nome do Arquivo:");
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblNewLabel.setBounds(36, 95, 144, 14);
		frmCompactador.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("PATH do Arquivo:");
		lblNewLabel_1.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(36, 120, 144, 14);
		frmCompactador.getContentPane().add(lblNewLabel_1);
		
		txtNomeArq = new JTextField();
		txtNomeArq.setFont(new Font("Consolas", Font.PLAIN, 16));
		txtNomeArq.setBounds(179, 92, 396, 20);
		frmCompactador.getContentPane().add(txtNomeArq);
		txtNomeArq.setColumns(10);
		
		txtPath = new JTextField();
		txtPath.setFont(new Font("Consolas", Font.PLAIN, 16));
		txtPath.setBounds(179, 117, 396, 20);
		frmCompactador.getContentPane().add(txtPath);
		txtPath.setColumns(10);
		
		JButton btnCompactar = new JButton("Compactar Arquivo");
		btnCompactar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try 
				{
					bit.setNomeDoArquivo(txtPath.getText() + txtNomeArq.getText());
					bit.compilaArquivo();
					JOptionPane.showMessageDialog(null, "Compilado com Sucesso");
				} catch (Exception e) {JOptionPane.showMessageDialog(null, e.getMessage());}
			}
		});
		btnCompactar.setFont(new Font("Consolas", Font.PLAIN, 14));
		btnCompactar.setBounds(36, 145, 175, 23);
		frmCompactador.getContentPane().add(btnCompactar);
		
		JButton btnLimpar = new JButton("Limpar Campos");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtNomeArq.setText("");
				txtPath.setText("");
			}
		});
		btnLimpar.setFont(new Font("Consolas", Font.PLAIN, 14));
		btnLimpar.setBounds(430, 145, 145, 23);
		frmCompactador.getContentPane().add(btnLimpar);
		
		JButton btnDescompactar = new JButton("Descompactar Arquivo");
		btnDescompactar.setFont(new Font("Consolas", Font.PLAIN, 14));
		btnDescompactar.setBounds(221, 145, 199, 23);
		frmCompactador.getContentPane().add(btnDescompactar);
	}
}
