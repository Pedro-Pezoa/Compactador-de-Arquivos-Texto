package Classes;

import java.io.Serializable;

/**
 * Classe p�blica que armazena um caracter com o n�mero de ocorr�ncia desse caracter no texto lido, caso tenha mais de um caracter,
 * significa que a classe foi misturada com outras do mesmo tipo, representando a soma de todas as ocorr�ncias das letras juntas.
 * 
 * @author Pedro Luiz Pezoa u15190
 * @since 2016
 */
@SuppressWarnings("serial")
public class CharOcorrencia implements Comparable<CharOcorrencia>, Serializable
{
	/**
	 * Inteiro que armazena o n�mero de ocorr�ncias do caracter
	 */
	protected int ocorrencia;
	
	/**
	 * String que armazena um ou v�rios caracteres 
	 */
	protected String qualCaracter;
	
    //---------------------------------------------------------------------------------------------------------------------------//
    //----------------------------------------------------Construtor e Getters---------------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------------------//
	
	/**
	 * Construtor que inst�ncia os atributos da classe com os valores dos par�metros passados
	 * @param _novaOcorrencia Inteiro que armazena a nova ocorr�ncia
	 * @param _novoChar String que armazena a nova sequ�ncia de caracteres
	 * @throws Ser� lan�ado uma exce��o caso o inteiro for menor ou igual a 0 ou a String for nula ou vazia 
	 */
	public CharOcorrencia(int _novaOcorrencia, String _novoChar) throws Exception
	{
		this.setOcorrencia(_novaOcorrencia);
		this.setQualCaracter(_novoChar);
	}
	
	/**
	 * M�todo que retorna um inteiro que representa a ocorr�ncia dos caracteres
	 * @return O valor do atributo de ocorr�ncia da classe CharOcorrencia
	 */
	public int getOcorrencia()
    {
    	return this.ocorrencia;
    }

	/**
	 * M�todo que retorna uma String que representa a sequ�ncia de caracteres
	 * @return O valor do atributo de qualCaracter da classe CharOcorrencia
	 */
    public String getQualCaracter()
    {
    	return this.qualCaracter;
    }
	
    /**
	 * M�todo que altera o atributo ocorrencia da classe CharOcorrencia por um inteiro que representa a nova ocorr�ncia dos caracteres
	 * @param _novaOcorrencia Inteiro que armazena a nova ocorr�ncia
	 */
	public void setOcorrencia(int _novaOcorrencia) throws Exception
    {
		if (_novaOcorrencia <= 0) throw new Exception("Par�metros Inv�lidos-setOcorrencia");
    	this.ocorrencia = _novaOcorrencia;
    }

	/**
	 * M�todo que altera o atributo da classe qualCaracter por uma String que representa a nova sequ�ncia de caracteres
	 * @param _novoChar String que armazena a nova sequ�ncia de caracteres
	 */
    public void setQualCaracter(String _novoCaracter) throws Exception
    {
    	if (_novoCaracter == null || _novoCaracter.equals("")) throw new Exception("Par�metros Inv�lidos-setQualCaracter");
    	this.qualCaracter = _novoCaracter;
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
		if (this.qualCaracter.equals(" ") || this.qualCaracter.equals(",")) return "'" + this.qualCaracter + "'" + "->" + ocorrencia;
		return this.qualCaracter + "->" + this.ocorrencia;
	}
	
	/**
	 * M�todo que calcula os valores dos endere�os de mem�ria de todos os atributos da classe
	 * @return O valor dos endere�os de mem�ria
	 */
	public int hashCode()
	{
		int ret = super.hashCode();
		ret *= 7 + new Integer(this.ocorrencia).hashCode();
		ret *= 7 + this.qualCaracter.hashCode();
		return ret;
	}
	
	/**
	 * M�todo que compara a classe em s� com um outro objeto passado como par�metro
	 * @param _obj Objeto que ser� comparado com a classe em s�
	 * @return Retorna verdadeiro se o objeto for igual de alguma maneira com a classe em s� ou falso caso contr�rio
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
	 * M�todo que compara um dos atributos da classe em s� com o mesmo atributo da classe do mesmo tipo
	 * @param _obj Objeto que ser� comparado com a classe em s�
	 * @return Retorna -1 caso o atributo da classe this � maior que o passado como par�metro,
	 *         retorna 1 caso o atributo da classe this � menor que o passado como par�metro, 
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
