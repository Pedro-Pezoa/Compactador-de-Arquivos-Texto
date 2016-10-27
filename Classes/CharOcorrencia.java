package Classes;

public class CharOcorrencia implements Comparable<CharOcorrencia>
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
		if (qualCaracter.equals(" ") || qualCaracter.equals(",")) return "'" + qualCaracter + "'" + "->" + ocorrencia;
		return qualCaracter + "->" + ocorrencia;
	}

	@Override
	public int compareTo(CharOcorrencia _obj) 
	{
		if (this.ocorrencia > _obj.ocorrencia) return 1;
		if (this.ocorrencia < _obj.ocorrencia) return -1;
		return 0;
	}
}
