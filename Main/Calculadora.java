package Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingConstants;

/**
 * Classe Principal que ao ser executada, mostrará um formulário que possui uma calculadora básica
 * 
 * @author Pedro Luiz Pezoa
 * @since 2016
 */
public class Calculadora 
{
	private JFrame frmCalculadora;
	private JTextField txtResult;
	private JButton btnDividir, btnMulti, btnSub, btnAdicionar, btnIgual, btnPot, btnPonto, btnZero;
	private Calculos calculadora;
	private boolean podePonto = true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calculadora window = new Calculadora();
					window.frmCalculadora.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Calculadora() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frmCalculadora = new JFrame();
		frmCalculadora.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				setNumb("0");
				try {
					calculadora = new Calculos();
				} catch (Exception e) {System.err.println(e.getMessage());}
			}
		});
		frmCalculadora.setTitle("Calculadora");
		frmCalculadora.getContentPane().setFont(new Font("Consolas", Font.PLAIN, 22));
		frmCalculadora.setBounds(600, 200, 306, 374);
		frmCalculadora.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCalculadora.getContentPane().setLayout(null);
		
		txtResult = new JTextField();
		txtResult.setHorizontalAlignment(SwingConstants.RIGHT);
		txtResult.setEditable(false);
		txtResult.setFont(new Font("Consolas", Font.PLAIN, 26));
		txtResult.setBounds(10, 11, 273, 47);
		frmCalculadora.getContentPane().add(txtResult);
		txtResult.setColumns(10);
		
		btnDividir = new JButton("/");
		btnDividir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOperador("/");
			}
		});
		btnDividir.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnDividir.setBounds(10, 71, 60, 33);
		frmCalculadora.getContentPane().add(btnDividir);
		
		btnMulti = new JButton("*");
		btnMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOperador("*");
			}
		});
		btnMulti.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnMulti.setBounds(83, 71, 60, 33);
		frmCalculadora.getContentPane().add(btnMulti);
		
		btnSub = new JButton("-");
		btnSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOperador("-");
			}
		});
		btnSub.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnSub.setBounds(153, 70, 60, 33);
		frmCalculadora.getContentPane().add(btnSub);
		
		btnAdicionar = new JButton("+");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOperador("+");
			}
		});
		btnAdicionar.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnAdicionar.setBounds(223, 69, 60, 33);
		frmCalculadora.getContentPane().add(btnAdicionar);
		
		JButton btnSete = new JButton("7");
		btnSete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNumb("7");
			}
		});
		btnSete.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnSete.setBounds(10, 161, 60, 35);
		frmCalculadora.getContentPane().add(btnSete);
		
		JButton btnOito = new JButton("8");
		btnOito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNumb("8");
			}
		});
		btnOito.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnOito.setBounds(80, 161, 60, 35);
		frmCalculadora.getContentPane().add(btnOito);
		
		JButton btnNove = new JButton("9");
		btnNove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNumb("9");
			}
		});
		btnNove.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnNove.setBounds(153, 161, 60, 35);
		frmCalculadora.getContentPane().add(btnNove);
		
		JButton btnApagaTudo = new JButton("C");
		btnApagaTudo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNumb("");
			}
		});
		btnApagaTudo.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnApagaTudo.setBounds(10, 115, 60, 35);
		frmCalculadora.getContentPane().add(btnApagaTudo);
		
		JButton btnQuatro = new JButton("4");
		btnQuatro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNumb("4");
			}
		});
		btnQuatro.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnQuatro.setBounds(10, 207, 60, 35);
		frmCalculadora.getContentPane().add(btnQuatro);
		
		JButton btnCinco = new JButton("5");
		btnCinco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNumb("5");
			}
		});
		btnCinco.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnCinco.setBounds(80, 207, 60, 35);
		frmCalculadora.getContentPane().add(btnCinco);
		
		JButton btnSeis = new JButton("6");
		btnSeis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNumb("6");
			}
		});
		btnSeis.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnSeis.setBounds(153, 207, 60, 35);
		frmCalculadora.getContentPane().add(btnSeis);
		
		btnPot = new JButton("Pot");
		btnPot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOperador("^");
			}
		});
		btnPot.setFont(new Font("Consolas", Font.PLAIN, 14));
		btnPot.setBounds(223, 162, 60, 34);
		frmCalculadora.getContentPane().add(btnPot);
		
		JButton btnUm = new JButton("1");
		btnUm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setNumb("1");
			}
		});
		btnUm.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnUm.setBounds(10, 253, 60, 35);
		frmCalculadora.getContentPane().add(btnUm);
		
		JButton btnDois = new JButton("2");
		btnDois.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNumb("2");
			}
		});
		btnDois.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnDois.setBounds(80, 253, 60, 35);
		frmCalculadora.getContentPane().add(btnDois);
		
		JButton btnTres = new JButton("3");
		btnTres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNumb("3");
			}
		});
		btnTres.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnTres.setBounds(153, 253, 60, 35); 
		frmCalculadora.getContentPane().add(btnTres);
		
		btnIgual = new JButton("=");
		btnIgual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try 
				{
					if (verificaParenteses(txtResult.getText()))
					{
						String result = calculadora.calcula(txtResult.getText());
						
						if (result.length() > 14) txtResult.setText(result.substring(0,14));
						else txtResult.setText(result);
					}
					else JOptionPane.showMessageDialog(null, "Quantidade de parênteses inválida");
				} catch (Exception e) {System.err.println(e.toString());}
			}
		});
		btnIgual.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnIgual.setBounds(223, 207, 60, 81);
		frmCalculadora.getContentPane().add(btnIgual);
		
		btnZero = new JButton("0");
		btnZero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNumb("0");
			}
		});
		btnZero.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnZero.setBounds(10, 293, 203, 35);
		frmCalculadora.getContentPane().add(btnZero);
		
		btnPonto = new JButton(".");
		btnPonto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNumb(".");
				estadoBotoes(false);
				btnPonto.setEnabled(false);
				podePonto = false;
			}
		});
		btnPonto.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnPonto.setBounds(223, 293, 60, 35);
		frmCalculadora.getContentPane().add(btnPonto);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 65, 273, 11);
		frmCalculadora.getContentPane().add(separator);
		
		JButton btnParam1 = new JButton("(");
		btnParam1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setNumb("(");
				estadoBotoes(false);
			}
		});
		btnParam1.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnParam1.setBounds(80, 115, 60, 35);
		frmCalculadora.getContentPane().add(btnParam1);
		
		JButton btnParam2 = new JButton(")");
		btnParam2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setNumb(")");
				estadoBotoes(true);
				podePonto = false;
			}
		});
		btnParam2.setFont(new Font("Consolas", Font.PLAIN, 21));
		btnParam2.setBounds(153, 115, 60, 35);
		frmCalculadora.getContentPane().add(btnParam2);
		
		JButton btnApagar = new JButton("Del");
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtResult.getText().equals("0")) 
				{
					if(txtResult.getText().charAt(txtResult.getText().length()-1) != ' ')
					{
						if(txtResult.getText().charAt(txtResult.getText().length()-1) == '.') podePonto = true;
						txtResult.setText(txtResult.getText().substring(0, txtResult.getText().length()-1));
						if (txtResult.getText().equals("")) txtResult.setText("0");
					}	
					else txtResult.setText(txtResult.getText().substring(0, txtResult.getText().length()-3));
				}
			}
		});
		btnApagar.setFont(new Font("Consolas", Font.PLAIN, 14));
		btnApagar.setBounds(223, 116, 60, 33);
		frmCalculadora.getContentPane().add(btnApagar);
	}
	
	//----------------------------------------------------------------------------------------------------------------------------//
    //-----------------------------------------------------Método Auxiliares------------------------------------------------------//
    //----------------------------------------------------------------------------------------------------------------------------//
	
	/**
	 * Método que armazena o número digitado pelo usuário no TextField
	 * @param _numb String que representa o algarismo que deve ser armazenado
	 */
	private void setNumb(String _numb)
	{
		if (_numb.equals(""))
			txtResult.setText("0");
		
		else if ((txtResult.getText().equals("0") && (_numb.equals("."))))	
			txtResult.setText(txtResult.getText() + _numb);
		
		else if ((txtResult.getText().equals("0") && (!_numb.equals("0"))))	
			txtResult.setText(_numb);
		
		else if (!txtResult.getText().equals("0"))	
			txtResult.setText(txtResult.getText() + _numb);
		
		else if ((!txtResult.getText().equals("0") && _numb.equals("0")))
			txtResult.setText(txtResult.getText() + _numb);
		
		btnPonto.setEnabled(podePonto);
		estadoBotoes(true);
		btnZero.setEnabled(true);
	}
	
	/**
	 * Método que armazena o operador digitado pelo usuário no TextField
	 * @param _op String que representa o operador que deve ser armazenado
	 */
	private void setOperador(String _op)
	{
		if (!txtResult.getText().equals("0"))
		{	
			if (txtResult.getText().equals("Erro"))
			{
				JOptionPane.showMessageDialog(null, "Número de Calculo Inválido");
				txtResult.setText("0");
			}
			else
			{	
				txtResult.setText(txtResult.getText() + " " + _op + " ");
				estadoBotoes(false);
				podePonto = true;
			}
		}
		if (_op.equals("/"))
			btnZero.setEnabled(false);
	}

	/**
	 * Método que altera o estado de visibilidade de certos botões
	 * @param _op Um boolean que representa qual será o estado dos botões
	 */
	private void estadoBotoes(boolean _estado) 
	{
		btnAdicionar.setEnabled(_estado);
		btnSub.setEnabled(_estado);
		btnMulti.setEnabled(_estado);
		btnDividir.setEnabled(_estado);
		btnIgual.setEnabled(_estado);
		btnPot.setEnabled(_estado);
	}
	
	/**
	 * Método que verifica quantas aspas existem na equação como um todo
	 * @param _equacao String que representa a equação a ser verificada
	 */
	private boolean verificaParenteses(String _equacao)
	{
		int qtosParam1 = 0, qtosParam2 = 0;
		for (int k = 0; k < _equacao.length(); k++) 
		{
			if (_equacao.charAt(k) == '(') qtosParam1++;
			else if (_equacao.charAt(k) == ')') qtosParam2++;
		}
		return qtosParam1 == qtosParam2;
	}
	
	//---------------------------------------------------------------------------------------------------------------------------//
	//---------------------------------------------------Métodos Apocalipticos---------------------------------------------------//
	//---------------------------------------------------------------------------------------------------------------------------//
	
	/**
	 * Método que cria uma String que representa os atributos da classe em sí
	 * @return Uma String que possui os valores dos atributos da classe em sí
	 */
	public String toString()
	{
		return "JFrame: " + this.frmCalculadora.toString()+ 
			   " JTextField: " + this.txtResult.toString() + 
			   " JButton: " + this.btnAdicionar.toString() + " " + this.btnDividir.toString() + " " + this.btnIgual.toString() + " " + this.btnMulti.toString() + " " 
			                + this.btnPonto.toString() + " " + this.btnPot.toString() + " " + this.btnSub.toString() + " " + this.btnZero.toString() +
			   " Calculos: " + this.calculadora.toString() +
			   " boolean:" + new Boolean(this.podePonto).toString();
	}
	
	/**
	 * Método que calcula os valores dos endereços de memória de todos os atributos da classe
	 * @return O valor dos endereços de memória
	 */
	public int hashCode()
	{
		int ret = super.hashCode();
		
		ret *= 7 + this.frmCalculadora.hashCode();
		ret *= 7 + this.txtResult.hashCode();
		ret *= 7 + this.btnAdicionar.hashCode();
		ret *= 7 + this.btnDividir.hashCode();
		
		ret *= 7 + this.btnIgual.hashCode();
		ret *= 7 + this.btnMulti.hashCode();
		ret *= 7 + this.btnPonto.hashCode();
		ret *= 7 + this.btnPot.hashCode();
		
		ret *= 7 + this.btnSub.hashCode();
		ret *= 7 + this.btnZero.hashCode();
		ret *= 7 + this.calculadora.hashCode();
		ret *= 7 + new Boolean(this.podePonto).hashCode();
		
		return ret;
	}
	
	/**
	 * Método que compara a classe em sí com um outro objeto passado como parâmetro
	 * @param _obj Objeto que será comparado com a classe em sí
	 * @return Retorna verdadeiro se o objeto for igual de alguma maneira com a classe em sí ou falso caso contrário
	 */
	public boolean equals(Object _obj)
	{
		if (_obj == null)
			return false;
		
		if (this == _obj)
			return true;
		
		if (this.getClass() == _obj.getClass())
		{
			Calculadora cal = (Calculadora)_obj;
			
			if (this.frmCalculadora.equals(cal.frmCalculadora) && this.txtResult.equals(cal.txtResult) && this.btnAdicionar.equals(cal.btnAdicionar) && 
				this.btnDividir.equals(cal.btnDividir) && this.btnIgual.equals(cal.btnIgual) && this.btnMulti.equals(cal.btnMulti) && 
				this.btnPonto.equals(cal.btnPonto) && this.btnPot.equals(cal.btnPot) && this.btnSub.equals(cal.btnSub) && this.btnZero.equals(cal.btnZero) && 
				this.calculadora.equals(cal.calculadora) && new Boolean(this.podePonto).equals(new Boolean(cal.podePonto))) 
				return true;
		}
		
		if (_obj instanceof String)
		{
			String cal = (String)_obj;
			
			if (this.toString().equals(cal))
				return true;
		}
		return false;
	}
}
