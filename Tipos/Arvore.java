package Tipos;

public class Arvore<Tipo extends Comparable<Tipo>> 
{
    protected Elemento<Tipo> raizPrincipal;
    
    public Arvore() 
    {
		this(null);
	}
    
    public Arvore(Tipo _tipo) 
    {
		this.raizPrincipal = new Elemento<Tipo>(_tipo);
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
	        if (Raiz.getInfo().compareTo(N) > 0) // incluir na Esquerda
	        {
	            if (Raiz.getEsq() == null) 
	                Raiz.setEsq(new Elemento<Tipo>(N));
	            else
	                incluir(Raiz.getEsq(), N);
	        }
	        else // <= N , incluir na Direita
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
    
    public Elemento<Tipo> getRaiz()
    {
    	return this.raizPrincipal;
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
