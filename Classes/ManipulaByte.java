package Classes;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import Tipos.Arvore;
import Tipos.Elemento;
import Tipos.Fila;
import Tipos.ListaDupla;

public class ManipulaByte 
{
	protected Arvore<CharOcorrencia> arvoreDeOcorrencias;
	
	@SuppressWarnings("resource")
	public ManipulaByte(String _nomeArq) throws Exception 
	{
		FileInputStream arquivoFisico = new FileInputStream(_nomeArq);
		DataInputStream data = new DataInputStream(new BufferedInputStream(arquivoFisico));
        
        byte vetByte[] = new byte[arquivoFisico.available()];
        int[] contador = new int[256];
        data.read(vetByte);
        
        for (char c : new String(vetByte).toCharArray())
        	contador[c]++;

        this.arvoreDeOcorrencias = new Arvore<CharOcorrencia>(this.transformaArvore(this.organizaLista(contador)));
	}
	
	private Elemento<CharOcorrencia> transformaArvore(ListaDupla<CharOcorrencia> _listaDeOcorrencias) throws Exception 
	{
		_listaDeOcorrencias.iniciaPercurssoSequencial(true);
		while (_listaDeOcorrencias.podePercorrer())
		{
			Elemento<CharOcorrencia> elem1 = _listaDeOcorrencias.getAtual();
			_listaDeOcorrencias.excluirDoFim();
			Elemento<CharOcorrencia> elem2 = _listaDeOcorrencias.getAtual();
			_listaDeOcorrencias.excluirDoFim();

			Arvore<CharOcorrencia> arv = new Arvore<CharOcorrencia>(new Elemento<CharOcorrencia>(new CharOcorrencia(elem1.getInfo().getOcorrencia() + elem2.getInfo().getOcorrencia(),
					                                                elem1.getInfo().getQualCaracter() + elem2.getInfo().getQualCaracter())));
			arv.incluirNaEsquerda(elem1);
			arv.incluirNaDireita(elem2);
			_listaDeOcorrencias.incluirOrdenado(arv.getRaiz());
		}
		return _listaDeOcorrencias.getInicio();
	}

	private ListaDupla<CharOcorrencia> organizaLista(int[] _contador)
	{
		ListaDupla<CharOcorrencia> listaDeOcorrencias = new ListaDupla<>();
		int[] vetConteudo = new int[256];
		char[] vetPos = new char[256];
		
		for (int i = 0, j = 0; i < _contador.length; i++) 
        {
        	if (_contador[i] != 0) 
        	{
        		vetConteudo[j] = _contador[i];
        		vetPos[j++] = (char)i;
        	}
        }	
        
        int i = 0, j = 1, auxInt = 0;
        char auxChar = ' ';
        while (i < vetConteudo.length-1)
        {
        	if (vetConteudo[i] < vetConteudo[j])
        	{
        		auxInt = vetConteudo[i];
        		vetConteudo[i] = vetConteudo[j];
        		vetConteudo[j] = auxInt;
        		
        		auxChar = vetPos[i];
        		vetPos[i] = vetPos[j];
        		vetPos[j] = auxChar;
        	}
        	if (j >= vetConteudo.length-1) j = ++i + 1;
        	else j++;
        }
        
        for (int k = 0; k < vetPos.length; k++) 
        	if (vetConteudo[k] != 0) listaDeOcorrencias.incluirNoFim(new Elemento<CharOcorrencia>(new CharOcorrencia(vetConteudo[k], vetPos[k]+"")));
        
        return listaDeOcorrencias;
	}
	
	public Arvore<CharOcorrencia> getArvoreOcorrencia()
	{
		return this.arvoreDeOcorrencias;
	}
	
	public String toString()
	{
		return this.arvoreDeOcorrencias.toString();
	}
	
	public int hashCode()
	{
		return this.arvoreDeOcorrencias.hashCode();
	}
}