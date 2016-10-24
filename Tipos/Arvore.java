package Tipos;

public class Arvore<Tipo extends Comparable<Tipo>> 
{
    @SuppressWarnings("hiding")
	private class Elemento<Tipo extends Comparable<Tipo>> 
    {
        protected Tipo info;
        protected Elemento<Tipo> dir, esq;
        
        //----------------------------------------------------Construtor-------------------------------------------------//
        
        public Elemento(Tipo N)
        {
            this.info = N;
            this.dir = this.esq = null;
        }
        
        //--------------------------------------------------Getters e Setters----------------------------------------------------//
        
        public void setDir(Elemento<Tipo> E)
        {
            this.dir = E;
        }
        
        public void setEsq(Elemento<Tipo> E)
        {
            this.esq = E;
        }
        
        public Elemento<Tipo> getDir()
        {
            return this.dir;
        }
        
        public Elemento<Tipo> getEsq()
        {
            return this.esq;
        }
        
        public Tipo getInfo()
        { 
        	return this.info;
        }
        
        @SuppressWarnings("unused")
		public void setInfo(Tipo I)
        {
        	this.info = I;
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
            ret *= 7 + this.esq.hashCode();
            ret *= 7 + this.dir.hashCode();
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
        		
        		if (this.info == proxN.info && this.esq == proxN.esq && this.dir == proxN.dir) return true;
        	}
        	
        	if (_obj instanceof String)
        	{
        		String proxN = (String)_obj;
        		
        		if (this.toString().equals(proxN.toString())) return true;
        	} return false;
        }
    }    

    private Elemento<Tipo> raizPrincipal;
    
    public Arvore() 
    {
		this.raizPrincipal = null;
	}
    
    public void incluir(Tipo N)
    {
        incluir(raizPrincipal, N);
    }
    
    private void incluir(Elemento<Tipo> Raiz, Tipo N)
    {
        if (Raiz == null) // arvore vazia
            raizPrincipal = new Elemento<Tipo>(N);
        else
        {
	        if (Raiz.getInfo().compareTo(N) < 0) // incluir na esquerda
	        {
	            if (Raiz.getEsq() == null) 
	                Raiz.setEsq(new Elemento<Tipo>(N));
	            else
	                incluir(Raiz.getEsq(), N);
	        }
	        else // <= N , incluir na direita
	        {
	            if (Raiz.getDir() == null)
	                Raiz.setDir(new Elemento<Tipo>(N));
	            else          
	                incluir(Raiz.getDir(), N);
	        }
        }
    }
    
    public int qtosNos()
    {
        return qtosNos(raizPrincipal);
    }
    
    private int qtosNos(Elemento<Tipo> Raiz)
    {
        if (Raiz == null) return 0;
        return (qtosNos(Raiz.getDir()) + qtosNos(Raiz.getEsq()) + 1);
    }
    
    private String visita(Elemento<Tipo> Raiz)
    {
        if (Raiz == null) return "";
        return visita(Raiz.getEsq()) + " " + Raiz.getInfo()+ " " + visita (Raiz.getDir());
    }
    
    public String toString()
    {
        return "[ "+ visita(raizPrincipal) + " ]";
    } 
}
