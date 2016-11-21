package Classes;

import java.io.Serializable;

/**
 * Classe pública que armazena um caracter com o número de ocorrência desse caracter no texto lido, caso tenha mais de um caracter,
 * significa que a classe foi misturada com outras do mesmo tipo, representando a soma de todas as ocorrências das letras juntas.
 * 
 * @author Pedro Luiz Pezoa u15190
 * @since 2016
 */
@SuppressWarnings("serial")
public class CharOcorrencia implements Comparable<CharOcorrencia>, Serializable
{
	/**
	 * Inteiro que armazena o número de ocorrências do caracter
	 */
	protected int ocorrencia;
	
	/**
	 * String que armazena um ou vários caracteres 
	 */
	protected String qualCaracter;
	
    //---------------------------------------------------------------------------------------------------------------------------//
    //----------------------------------------------------Construtor e Getters---------------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------------------//
	
	/**
	 * Construtor que instância os atributos da classe com os valores dos parâmetros passados
	 * @param _novaOcorrencia Inteiro que armazena a nova ocorrência
	 * @param _novoChar String que armazena a nova sequência de caracteres
	 * @throws Será lançado uma exceção caso o inteiro for menor ou igual a 0 ou a String for nula ou vazia 
	 */
	public CharOcorrencia(int _novaOcorrencia, String _novoChar) throws Exception
	{
		this.setOcorrencia(_novaOcorrencia);
		this.setQualCaracter(_novoChar);
	}
	
	/**
	 * Método que retorna um inteiro que representa a ocorrência dos caracteres
	 * @return O valor do atributo de ocorrência da classe CharOcorrencia
	 */
	public int getOcorrencia()
    {
    	return this.ocorrencia;
    }

	/**
	 * Método que retorna uma String que representa a sequência de caracteres
	 * @return O valor do atributo de qualCaracter da classe CharOcorrencia
	 */
    public String getQualCaracter()
    {
    	return this.qualCaracter;
    }
	
    /**
	 * Método que altera o atributo ocorrencia da classe CharOcorrencia por um inteiro que representa a nova ocorrência dos caracteres
	 * @param _novaOcorrencia Inteiro que armazena a nova ocorrência
	 */
	public void setOcorrencia(int _novaOcorrencia) throws Exception
    {
		if (_novaOcorrencia <= 0) throw new Exception("Parâmetros Inválidos-setOcorrencia");
    	this.ocorrencia = _novaOcorrencia;
    }

	/**
	 * Método que altera o atributo da classe qualCaracter por uma String que representa a nova sequência de caracteres
	 * @param _novoChar String que armazena a nova sequência de caracteres
	 */
    public void setQualCaracter(String _novoCaracter) throws Exception
    {
    	if (_novoCaracter == null || _novoCaracter.equals("")) throw new Exception("Parâmetros Inválidos-setQualCaracter");
    	this.qualCaracter = _novoCaracter;
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
		if (this.qualCaracter.equals(" ") || this.qualCaracter.equals(",")) return "'" + this.qualCaracter + "'" + "->" + ocorrencia;
		return this.qualCaracter + "->" + this.ocorrencia;
	}
	
	/**
	 * Método que calcula os valores dos endereços de memória de todos os atributos da classe
	 * @return O valor dos endereços de memória
	 */
	public int hashCode()
	{
		int ret = super.hashCode();
		ret *= 7 + new Integer(this.ocorrencia).hashCode();
		ret *= 7 + this.qualCaracter.hashCode();
		return ret;
	}
	
	/**
	 * Método que compara a classe em sí com um outro objeto passado como parâmetro
	 * @param _obj Objeto que será comparado com a classe em sí
	 * @return Retorna verdadeiro se o objeto for igual de alguma maneira com a classe em sí ou falso caso contrário
	 */
	public boolean equals(Object _obj)
	{
		if (_obj == null) return false;
		
		if (this == _obj) return true;
		
		if (this.getClass() == _obj.getClass())
		{
			CharOcorrencia chare = (CharOcorrencia)_obj;
			if (this.ocorrencia == chare.ocorrencia && this.qualCaracter.equals(chare.qualCaracter)) return true;
		}
		
		if (_obj instanceof String)
		{
			String chare = (String)_obj;
			if (this.toString().equals(chare.toString())) return true;
		}
		return false;
	}

	/**
	 * Método que compara um dos atributos da classe em sí com o mesmo atributo da classe do mesmo tipo
	 * @param _obj Objeto que será comparado com a classe em sí
	 * @return Retorna -1 caso o atributo da classe this é maior que o passado como parâmetro,
	 *         retorna 1 caso o atributo da classe this é menor que o passado como parâmetro, 
	 *         retorna 0 caso os atributos sejam iguais  
	 */
	@Override
	public int compareTo(CharOcorrencia _obj) 
	{
		if (this.ocorrencia > _obj.ocorrencia) return -1;
		if (this.ocorrencia < _obj.ocorrencia) return 1;
		return 0;
	}
}
