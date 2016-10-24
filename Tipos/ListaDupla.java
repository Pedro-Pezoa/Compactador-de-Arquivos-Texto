package Tipos;

public class ListaDupla<Tipo extends Comparable<Tipo>>  
{
	@SuppressWarnings("hiding")
    private class Elemento <Tipo extends Comparable<Tipo>> 
    {
        protected Tipo info;
        protected Elemento<Tipo> prox;
        protected Elemento<Tipo> ant;
        
        //----------------------------------------------------Construtor-------------------------------------------------//
        
        public Elemento(Tipo _conteudo)
        {
            this.info = _conteudo;
        }
        
        //--------------------------------------------------Getters e Setters----------------------------------------------------//
        
        @SuppressWarnings("unused")
		public void setInfo(Tipo _c)
        {
            this.info = _c;
        }
        
        public Tipo getInfo()
        {
           return this.info;
        }

        public void setProx(Elemento<Tipo> _c)
        {
            this.prox = _c;
        }
            
        public Elemento<Tipo> getProx()
        {
            return this.prox;
        }
        
        public void setAnt(Elemento<Tipo> _c)
        {
            this.ant = _c;
        }
            
        public Elemento<Tipo> getAnt()
        {
            return this.ant;
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
            ret *= 7 + this.prox.hashCode();
            ret *= 7 + this.ant.hashCode();
        	return ret;
        }
        
        @SuppressWarnings("unchecked")
    	@Override
        public boolean equals(Object _obj) 
        {
        	if (_obj == null) return false;
        	
        	if (this == _obj) return true;
        	
        	if (this.getClass() == _obj.getClass())
        	{
        		Elemento<Tipo> proxN = (Elemento<Tipo>)_obj;
        		
        		if (this.info == proxN.info && this.prox == proxN.prox && this.ant == proxN.ant) return true;
        	}
        	
        	if (_obj instanceof String)
        	{
        		String proxN = (String)_obj;
        		
        		if (this.toString().equals(proxN.toString())) return true;
        	} return false;
        }
    }
    
    protected Elemento<Tipo> inicio, fim;
    
    //---------------------------------------------------------------------------------------------------------------------------//
    //----------------------------------------------- ----Construtor e Getters---------------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------------------//
    
    public ListaDupla()
    {
        this.inicio = this.fim = null;
    }
    
    public Elemento<Tipo> getInicio()
    {
    	return this.inicio;
    }
    
    public Elemento<Tipo> getFim()
    {
    	return this.fim;
    }
    
    //---------------------------------------------------------------------------------------------------------------------------//
    //-----------------------------------------------------Métodos Principais----------------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------------------//
    
    public void incluirOrdenado(Tipo N)
    {
    	Elemento<Tipo> Aux = this.inicio, Ant = null;
        
        while (Aux != null && Aux.getInfo().compareTo(N) < 0)
        {
            Ant = Aux;
            Aux = Aux.getProx();
        }       
        
        if (Ant == null) incluirNoInicio(N);
        else if (Aux == null) incluirNoFim(N);
        else 
        {
        	Elemento<Tipo> Novo = new Elemento<Tipo>(N);
        	Aux.setAnt(Novo);
            Novo.setProx(Aux);
            Novo.setAnt(Ant);
            Ant.setProx(Novo);
        }
    }
    
    public void excluirOrdenado(Tipo N)
    {
    	Elemento<Tipo> Aux = this.inicio, Ant = null;
        
        while (Aux != null && Aux.getInfo().compareTo(N) < 0)
        {
            Ant = Aux;
            Aux = Aux.getProx();
        }
        
        if (Aux != null && Aux.getInfo().equals(N))
        { 
            if (Ant == null) excluirDoInicio();
            else if (Aux == fim) excluirDoFim();
            else 
            {
            	Ant.setProx(Aux.getProx());
            	Aux.getProx().setAnt(Ant);
            }
        }
    }
    
    public void incluirNoInicio(Tipo N)
    {
    	Elemento<Tipo> Novo = new Elemento<Tipo>(N);
        Novo.setProx(inicio);
        Novo.setAnt(null);
        
        if (inicio == null) fim = Novo;
        inicio = Novo;
    }
    
    public void excluirDoInicio()
    {
        if (this.inicio == this.fim) this.fim = null;
        
        if (this.inicio != null) 
        {
        	this.inicio = this.inicio.getProx();
        	this.inicio.setAnt(null);
        }
    }
    
    public void incluirNoFim(Tipo N)
    {
    	Elemento<Tipo> Novo = new Elemento<Tipo>(N);
    	
        if (this.fim != null) this.fim.setProx(Novo);
        this.fim = Novo;
        if (inicio == null) inicio = Novo;
    }
    
    public void excluirDoFim()
    {
        if (this.inicio != null)
        {
        	Elemento<Tipo> Aux = this.inicio;
        	
            while (Aux != null && Aux.getProx() != fim)
               Aux = Aux.getProx();
            
            if (Aux != null) Aux.setProx(null);
            
            if (this.inicio == this.fim)
                this.inicio = this.fim = null;
            
            else this.fim = Aux;
        }
    }
    
    public void excluir(Tipo N)
    {  
    	Elemento<Tipo> Ant = null, Aux = this.inicio;

        while (Aux != null)
        { 
            if (Aux.getInfo().equals(N))
            {
                if (this.inicio == this.fim) this.fim = this.inicio = null;
                
                else
                {  
                    if (Aux == this.fim) this.fim = Ant; 

                    if (Ant != null) 
                    {
                    	Ant.setProx(Aux.getProx());
                    	Aux.getProx().setAnt(Ant);
                    }
                    else 
                    {
                    	this.inicio = this.inicio.getProx();
                    	this.inicio.setAnt(null);
                    }
               }
            } 
            else Ant = Aux;
            
            Aux = Aux.getProx();
        }
    }
    
    //---------------------------------------------------------------------------------------------------------------------------//
    //------------------------------------------------------Método Auxiliar------------------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------------------//
    
    public Elemento<Tipo> ondeEsta(Tipo N)
    {
        Elemento<Tipo> Aux = this.inicio; 
        
        while (Aux != null && Aux.getInfo().compareTo(N) < 0)
            Aux = Aux.getProx();
          
        if (Aux != null && Aux.getInfo() == N) return Aux;
        return null;
    }
    
    public ListaDupla<Tipo> clonaLista() throws Exception 
	{
    	ListaDupla<Tipo> listaNova = new ListaDupla<Tipo>();
    	Elemento<Tipo> Aux = this.inicio;
    	
		while (Aux != null)
		{
			listaNova.incluirNoFim(Aux.getInfo());
			Aux = Aux.getProx();
		}
		return listaNova;
	}
    
    public void limparLista()
	{
		while (this.inicio != null) this.excluirDoFim();
	}
    
    public void juntarLista(ListaDupla<Tipo> _lista) throws Exception
	{
    	Elemento<Tipo> Aux = _lista.inicio;
    	
		while (Aux != null)
		{
			this.incluirNoFim(Aux.getInfo());
			Aux = Aux.getProx();
		}
	}
    
    //---------------------------------------------------------------------------------------------------------------------------//
    //---------------------------------------------------Métodos Apocalipticos---------------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------------------//
    
    public String toString()
    {
        String ret="[ " + this.inicio;
        Elemento<Tipo> aux = this.inicio.prox;
        
        while (aux != null)
        {
            ret += (", " + aux);
            aux = aux.getProx();
        }
        return ret + " ]";        
    }
}
