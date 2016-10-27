package Tipos;

public class ListaDupla<Tipo extends Comparable<Tipo>>  
{
    protected Elemento<Tipo> inicio, fim, atual;
    protected boolean ehReverso, estaPercorrendo;
    
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
            	if (estaPercorrendo && this.atual.equals(Aux)) this.atual = Aux.getProx(); 
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
        if (this.inicio == this.fim) 
        {
        	this.fim = this.inicio = null;
        	finalizaPercurssoSequencial();
        }
        
        if (this.inicio != null) 
        {
        	if (this.estaPercorrendo && this.atual.equals(this.inicio) && !this.ehReverso) this.atual = this.inicio.getProx();
        	else if (this.estaPercorrendo && this.atual.equals(this.inicio) && this.ehReverso) this.atual = null;
        	this.inicio = this.inicio.getProx();
        	this.inicio.setAnt(null);
        }
    }
    
    public void finalizaPercurssoSequencial() 
    {
		this.atual = null;
		this.estaPercorrendo = false;
	}

	public void incluirNoFim(Tipo N)
    {
    	Elemento<Tipo> Novo = new Elemento<Tipo>(N);
    	if (inicio == null) inicio = fim = Novo;
    	else
    	{
    		this.fim.setProx(Novo);
    		Novo.setAnt(this.fim);
    		this.fim = Novo;
    	}
    }
    
    public void excluirDoFim()
    {
        if (this.inicio != null)
        {
        	if (this.estaPercorrendo && this.atual.equals(this.fim) && this.ehReverso) this.atual = this.fim.getAnt();
        	else if (this.estaPercorrendo && this.atual.equals(this.fim) && !this.ehReverso) this.atual = null;
        	
        	Elemento<Tipo> Aux = this.inicio;
        	
            while (Aux != null && Aux.getProx() != fim)
               Aux = Aux.getProx();
            
            if (Aux != null) Aux.setProx(null);
            
            if (this.inicio == this.fim)
            {
                this.inicio = this.fim = null;
                finalizaPercurssoSequencial();
            }
            
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
    
    public void iniciaPercurssoSequencial(boolean _ehReverso)
    {
    	if (_ehReverso) this.atual = this.fim;
    	else this.atual = this.inicio;
    	this.ehReverso = _ehReverso;
    	this.estaPercorrendo = true;
    }
    
    public boolean podePercorrer()
    {
    	return this.atual != null;
    }
    
    public Elemento<Tipo> getAtual()
    {
    	return this.atual;
    }
    
    public void setAtual()
    {
    	if (this.ehReverso) this.atual = this.atual.getAnt();
    	else this.atual = this.atual.getProx();
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
