package Main;

import Tipos.Elemento;
import Tipos.Fila;
import Tipos.Pilha;

/**
 * Classe Calculos que realiza a organiza��o da equa��o e os calculos desta mesma
 * 
 * @author Pedro Luiz Pezoa
 * @since 2016
 */
/**
 * 
 */
public class Calculos 
{
	/**
	 * Fila que recebe os n�meros da equa��o
	 */
	private Fila<Double> filaNumb;
	
	/**
	 * Pilha que recebe os operadores da equa��o
	 */
	private Pilha<String> pilhaOp;
	
	/**
	 * Strings que ir�o conter a equa��o organizada e os n�meros que devem ser postos na fila, respectivamente
	 */
	private String result, aux;

    //---------------------------------------------------------------------------------------------------------------------------//
    //-------------------------------------------------------Construtor----------------------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------------------//
	
	/**
	 * Construtor que inst�ncia a classe Calculos inst�nciando a pilha, fila e as duas Strings result e aux
	 */
	public Calculos() throws Exception 
	{
		this.filaNumb = new Fila<Double>();
		this.pilhaOp = new Pilha<String>();
		this.result = this.aux = "";
	}

    //---------------------------------------------------------------------------------------------------------------------------//
    //-----------------------------------------------------M�todo Principal------------------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------------------//
	
	/**
	 * M�todo que calcula a equa��o organizada passada como par�metro do usu�rio e devolve o resultado desta mesma
	 * @param _txt equa��o desorganizada que o usu�rio fez na calculadora
	 * @return retorna o resultado da equa��o
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String calcula(String _txt) throws Exception 
	{
		if (_txt.equals("0")) return "0";
		
		String equacao = this.organizaEquacao(_txt);
		Pilha<Double> pilha = new Pilha<Double>();
		
		double valor = 0;
		int i = -1;
		
		while (i < equacao.length()-1)
		{
			switch (equacao.charAt(++i)+"") 
			{
				case "+":
					valor = pilha.desempilhar().getInfo();
					pilha.empilhar(new Elemento(pilha.desempilhar().getInfo() + valor));
					break;
		
				case "-":
					valor = pilha.desempilhar().getInfo();
					pilha.empilhar(new Elemento(pilha.desempilhar().getInfo() - valor));
					break;
					
				case "*":
					valor = pilha.desempilhar().getInfo();
					pilha.empilhar(new Elemento(pilha.desempilhar().getInfo() * valor));
					break;
					
				case "/":
					valor = pilha.desempilhar().getInfo();
					pilha.empilhar(new Elemento(pilha.desempilhar().getInfo() / valor));
					break;
					
				case "^":
					valor = pilha.desempilhar().getInfo();
					pilha.empilhar(new Elemento(Math.pow(pilha.desempilhar().getInfo(), valor)));
					break;
					
				default:
					pilha.empilhar(new Elemento((double)this.filaNumb.desefileirar().getInfo()));
					break;
			}
		}
		
		for (int j = 0; j < (pilha.consultaTopo().getInfo()+"").length()-1; j++) 
			if ((pilha.consultaTopo().getInfo()+"").charAt(j) == '.' && (pilha.consultaTopo().getInfo()+"").charAt(j+1) == '0')
				return (pilha.consultaTopo().getInfo()+"").substring(0, j);
		
		return pilha.consultaTopo().getInfo()+"";
	}
	
	/**
	 * M�todo que organiza a equa��o em um sistema Polon�s passada como par�metro pelo m�todo de calculo
	 * @param _equacao equa��o desorganizada que deve ser organizada
	 * @return retorna a equa��o organizada pelo sistema Polon�s
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String organizaEquacao(String _equacao) throws Exception
	{
		this.filaNumb.limparFila();
		this.pilhaOp.limparPilha();
		this.result = this.aux = "";
		
		int i = -1, j = -1;
		while (i < _equacao.length()-1) 
		{
			switch (_equacao.charAt(++i)) 
			{
				case '+': case '-': j = this.auxOrganizacao(j, _equacao.charAt(i)+"", "*", "/");
				break;
		
				case '*': case '/': j = this.auxOrganizacao(j, _equacao.charAt(i)+"", "^", null);
				break;
				
				case '^': j = this.auxOrganizacao(j, _equacao.charAt(i)+"", null, null);
				break;
				
				case '(': this.pilhaOp.empilhar(new Elemento("("));
				break;
					
				case ')':
					if (!aux.equals(""))
					{
						this.filaNumb.enfileirar(new Elemento(Double.parseDouble(aux.trim())));
						this.result += ++j;
					}
					
					while(!pilhaOp.consultaTopo().getInfo().equals("("))
						this.result += this.pilhaOp.desempilhar().getInfo();
					pilhaOp.desempilhar();
					aux = "";
				break;
				
				default: aux += _equacao.charAt(i);
			}
		}
		
		if (!aux.equals(""))
		{
			this.filaNumb.enfileirar(new Elemento(Double.parseDouble(aux.trim())));
			this.result += ++j;
		}
		while (!this.pilhaOp.isEmpty())
			this.result += this.pilhaOp.desempilhar();
		
		return this.result;
	}
	
	/**
	 * M�todo que auxilia a organiza��o da equa��o em um sistema Polon�s
	 * @param _j inteiro que representa a numera��o dos n�meros que est�o na fila
	 * @param _operador String que representa o operador atual
	 * @param _op1 String que representa o primeiro operador para a verifica��o
	 * @param _op2 String que representa o segundo operador para a verifica��o
	 * @return retorna a equa��o organizada pelo sistema Polon�s
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int auxOrganizacao(int _j, String _operador, String _op1, String _op2)
	{
		try
		{
			if (!this.aux.trim().equals(""))
			{
				this.filaNumb.enfileirar(new Elemento(Double.parseDouble(this.aux.trim())));
				this.result += ++_j;
				this.aux = "";
			}
			
			if ((!this.pilhaOp.isEmpty()) && (this.pilhaOp.consultaTopo().getInfo().equals(_op1) || this.pilhaOp.consultaTopo().getInfo().equals(_op2)))
				while (!this.pilhaOp.isEmpty())
					this.result += this.pilhaOp.desempilhar();
					
			this.pilhaOp.empilhar(new Elemento(_operador));
			
		}catch(Exception e){System.err.println(e.toString());}
		
		return _j;
	}
	
    //---------------------------------------------------------------------------------------------------------------------------//
	//---------------------------------------------------M�todos Apocalipticos---------------------------------------------------//
	//---------------------------------------------------------------------------------------------------------------------------//
	
	/**
	 * M�todo que cria uma String que representa os atributos da classe em s�
	 * @return Uma String que possui os valores dos atributos da classe em s�
	 */
	public String toString()
	{
		return "Fila de n�meros: " + this.filaNumb.toString() + 
			   " Pilha de operadores: " + this.pilhaOp.toString() + 
			   " String Resultante: " + this.result + 
			   " String Auxiliar: " + this.aux;
	}
	
	/**
	 * M�todo que calcula os valores dos endere�os de mem�ria de todos os atributos da classe
	 * @return O valor dos endere�os de mem�ria
	 */
	public int hashCode()
	{
		int ret = super.hashCode();
		
		ret *= 7 + this.filaNumb.hashCode();
		ret *= 7 + this.pilhaOp.hashCode();
		ret *= 7 + this.result.hashCode();
		ret *= 7 + this.aux.hashCode();
		
		return ret;
	}
	
	/**
	 * M�todo que compara a classe em s� com um outro objeto passado como par�metro
	 * @param _obj Objeto que ser� comparado com a classe em s�
	 * @return Retorna verdadeiro se o objeto for igual de alguma maneira com a classe em s� ou falso caso contr�rio
	 */
	public boolean equals(Object _obj)
	{
		if (_obj == null)
			return false;
		
		if (this == _obj)
			return true;
		
		if (this.getClass() == _obj.getClass())
		{
			Calculos cal = (Calculos)_obj;
			
			if (this.filaNumb.equals(cal.filaNumb) && this.pilhaOp.equals(cal.pilhaOp) && 
				this.result.equals(cal.result) && this.aux.equals(cal.aux))
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