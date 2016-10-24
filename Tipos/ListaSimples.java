package Tipos;

public class ListaSimples<Tipo extends Comparable<Tipo>> 
{
    @SuppressWarnings("hiding")
    private class Elemento <Tipo extends Comparable<Tipo>> 
    {
        protected Tipo info;
        protected Elemento<Tipo> prox;
        
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
        		
        		if (this.info == proxN.info && this.prox == proxN.prox) return true;
        	}
        	
        	if (_obj instanceof String)
        	{
        		String proxN = (String)_obj;
        		
        		if (this.toString().equals(proxN.toString())) return true;
        	} return false;
        }
    }
    
    protected Elemento<Tipo> inicio;
    protected Elemento<Tipo> fim;
    
    //---------------------------------------------------------------------------------------------------------------------------//
    //---------------------------------------------------------Construtor--------------------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------------------//
    
    public ListaSimples()
    {
        this.inicio = this.fim = null;
    }
    
    //---------------------------------------------------------------------------------------------------------------------------//
    //-----------------------------------------------------Métodos Principais----------------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------------------//
    
    public void incluirOrdenado(Tipo N)
    {
    	Elemento<Tipo> Aux = this.inicio, Ant = null;
        
        while (Aux!=null && Aux.getInfo().compareTo(N) < 0)
        {
            Ant = Aux;
            Aux = Aux.getProx();
        }       
        
        if (Ant == null) incluirNoInicio(N);
        else if (Aux == null) incluirNoFim(N);
        else 
        {
        	Elemento<Tipo> Novo = new Elemento<Tipo>(N);
            Novo.setProx(Aux);
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
            else Ant.setProx(Aux.getProx());
        }
    }
    
    public void incluirNoInicio(Tipo N)
    {
    	Elemento<Tipo> Novo = new Elemento<Tipo>(N);
        Novo.setProx(inicio);
        
        if (inicio == null) fim = Novo;
        inicio = Novo;
    }
    
    public void excluirDoInicio()
    {
        if (this.inicio == this.fim) this.fim = null;
        
        if (this.inicio != null) this.inicio = this.inicio.getProx();
        
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

                    if (Ant != null) Ant.setProx(Aux.getProx());
                    else this.inicio = this.inicio.getProx();
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
    
    public ListaSimples<Tipo> clonaLista() throws Exception 
	{
    	ListaSimples<Tipo> listaNova = new ListaSimples<Tipo>();
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
    
    public void juntarLista(ListaSimples<Tipo> _lista) throws Exception
	{
    	Elemento<Tipo> Aux = _lista.inicio;
    	
		while (Aux != null)
		{
			this.incluirNoFim(Aux.getInfo());
			Aux = Aux.getProx();
		}
	}
    
    //---------------------------------------------------------------------------------------------------------------------------//
    //---------------------------------------------------Métodos Adicionais------------------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------------------//
    
    public String toString()
    {
        String ret = "[" + this.inicio;
        Elemento<Tipo> aux = this.inicio.prox;
        
        while (aux != null)
        {
            ret += ("," + aux);
            aux = aux.getProx();
        }
        return ret + "]";        
    }
    
    public int hashCode()
    {
    	int ret = super.hashCode();
    	ret *= 7 + this.inicio.hashCode();
        ret *= 7 + this.fim.hashCode();
    	return ret;
    }
}
