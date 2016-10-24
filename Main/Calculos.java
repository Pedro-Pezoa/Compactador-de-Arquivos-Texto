package Main;

import Tipos.Elemento;
import Tipos.Fila;
import Tipos.Pilha;

/**
 * Classe Calculos que realiza a organização da equação e os calculos desta mesma
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
	 * Fila que recebe os números da equação
	 */
	private Fila<Double> filaNumb;
	
	/**
	 * Pilha que recebe os operadores da equação
	 */
	private Pilha<String> pilhaOp;
	
	/**
	 * Strings que irão conter a equação organizada e os números que devem ser postos na fila, respectivamente
	 */
	private String result, aux;

    //---------------------------------------------------------------------------------------------------------------------------//
    //-------------------------------------------------------Construtor----------------------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------------------//
	
	/**
	 * Construtor que instância a classe Calculos instânciando a pilha, fila e as duas Strings result e aux
	 */
	public Calculos() throws Exception 
	{
		this.filaNumb = new Fila<Double>();
		this.pilhaOp = new Pilha<String>();
		this.result = this.aux = "";
	}

    //---------------------------------------------------------------------------------------------------------------------------//
    //-----------------------------------------------------Método Principal------------------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------------------//
	
	/**
	 * Método que calcula a equação organizada passada como parâmetro do usuário e devolve o resultado desta mesma
	 * @param _txt equação desorganizada que o usuário fez na calculadora
	 * @return retorna o resultado da equação
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
	 * Método que organiza a equação em um sistema Polonês passada como parâmetro pelo método de calculo
	 * @param _equacao equação desorganizada que deve ser organizada
	 * @return retorna a equação organizada pelo sistema Polonês
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
	 * Método que auxilia a organização da equação em um sistema Polonês
	 * @param _j inteiro que representa a numeração dos números que estão na fila
	 * @param _operador String que representa o operador atual
	 * @param _op1 String que representa o primeiro operador para a verificação
	 * @param _op2 String que representa o segundo operador para a verificação
	 * @return retorna a equação organizada pelo sistema Polonês
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
	//---------------------------------------------------Métodos Apocalipticos---------------------------------------------------//
	//---------------------------------------------------------------------------------------------------------------------------//
	
	/**
	 * Método que cria uma String que representa os atributos da classe em sí
	 * @return Uma String que possui os valores dos atributos da classe em sí
	 */
	public String toString()
	{
		return "Fila de números: " + this.filaNumb.toString() + 
			   " Pilha de operadores: " + this.pilhaOp.toString() + 
			   " String Resultante: " + this.result + 
			   " String Auxiliar: " + this.aux;
	}
	
	/**
	 * Método que calcula os valores dos endereços de memória de todos os atributos da classe
	 * @return O valor dos endereços de memória
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