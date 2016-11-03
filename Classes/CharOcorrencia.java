package Classes;

import java.io.Serializable;

public class CharOcorrencia implements Comparable<CharOcorrencia>, Serializable
{
	protected int ocorrencia;
	protected String qualCaracter;
	
    //---------------------------------------------------------------------------------------------------------------------------//
    //----------------------------------------------- ----Construtor e Getters---------------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------------------//
	
	public CharOcorrencia(int _novaOcorrencia, String _novoChar) 
	{
		this.ocorrencia = _novaOcorrencia;
		this.qualCaracter = _novoChar;
	}
	
	public int getOcorrencia()
    {
    	return this.ocorrencia;
    }
    
    public String getQualCaracter()
    {
    	return this.qualCaracter;
    }
	
    //---------------------------------------------------------------------------------------------------------------------------//
    //---------------------------------------------------Métodos Apocalipticos---------------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------------------//
    
	public String toString()
	{
		if (this.qualCaracter.equals(" ") || this.qualCaracter.equals(",")) return "'" + this.qualCaracter + "'" + "->" + ocorrencia;
		return this.qualCaracter + "->" + this.ocorrencia;
	}
	
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

	@Override
	public int compareTo(CharOcorrencia _obj) 
	{
		if (this.ocorrencia > _obj.ocorrencia) return -1;
		if (this.ocorrencia < _obj.ocorrencia) return 1;
		return 0;
	}
}
