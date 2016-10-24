package Tipos;

public class Elemento <Tipo> 
{
    protected Tipo info;
    
    //-----------------------------------------------------Construtor-------------------------------------------------//
    
    public Elemento(Tipo _conteudo)
    {
        this.info = _conteudo;
    }
    
    //--------------------------------------------------Getters e Setters----------------------------------------------------//
    
    public void setInfo(Tipo _c)
    {
        this.info = _c;
    }
    
    public Tipo getInfo()
    {
       return this.info;
    }
    
    //------------------------------------------------Métodos Apocalipticos------------------------------------------------------//
    
    public String toString()
    {
    	return this.info + "";
    }
    
    public int hashCode()
    {
    	int ret = super.hashCode();
    	ret *= 7 + this.info.hashCode();
    	return ret;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public boolean equals(Object _obj) 
    {
    	if (_obj == null)
    		return false;
    	
    	if (this == _obj)
    		return true;
    	
    	if (this.getClass() == _obj.getClass())
    	{
    		Elemento<Tipo> proxN = (Elemento<Tipo>)_obj;
    		
    		if (this.info == proxN.info)
    			return true;
    	}
    	
    	if (_obj instanceof String)
    	{
    		String proxN = (String)_obj;
    		
    		if (this.toString().equals(proxN.toString()))
    			return true;
    	}
    	return false;
    }
}
