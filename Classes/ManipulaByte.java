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
	protected ListaDupla<CharOcorrencia> listaDeOcorrencias;
	
	@SuppressWarnings("resource")
	public ManipulaByte(String _nomeArq) throws Exception 
	{
		FileInputStream arquivoFisico = new FileInputStream(_nomeArq);
		DataInputStream data = new DataInputStream(new BufferedInputStream(arquivoFisico));
		this.listaDeOcorrencias = new ListaDupla<CharOcorrencia>();
        
        byte vetByte[] = new byte[arquivoFisico.available()];
        int[] contador = new int[256];
        data.read(vetByte);
        
        for (char c : new String(vetByte).toCharArray())
        	contador[c]++;

        this.organizaVet(contador);
        this.transformaArvore();
	}
	
	private void transformaArvore() throws Exception 
	{
		this.listaDeOcorrencias.iniciaPercurssoSequencial(true);
		CharOcorrencia chare = this.listaDeOcorrencias.getInicio().getInfo();
		while (this.listaDeOcorrencias.podePercorrer())
		{
			Elemento<CharOcorrencia> elem1 = this.listaDeOcorrencias.getAtual();
			this.listaDeOcorrencias.excluirDoFim();
			Elemento<CharOcorrencia> elem2 = this.listaDeOcorrencias.getAtual();
			this.listaDeOcorrencias.excluirDoFim();

			Arvore<CharOcorrencia> arv = new Arvore<CharOcorrencia>(new CharOcorrencia(elem1.getInfo().getOcorrencia()+elem2.getInfo().getOcorrencia(), ""));
			arv.incluir(elem1.getInfo());
			arv.incluir(elem2.getInfo());
			this.listaDeOcorrencias.incluirOrdenado(arv.getRaiz().getInfo());
			chare = this.listaDeOcorrencias.getInicio().getInfo();
		}
		System.out.println(chare);
	}

	private void organizaVet(int[] _contador)
	{
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
        	if (vetConteudo[k] != 0) this.listaDeOcorrencias.incluirNoFim(new CharOcorrencia(vetConteudo[k], vetPos[k]+""));
	}
	
	public ListaDupla<CharOcorrencia> getListaOcorrencia()
	{
		return this.listaDeOcorrencias;
	}
	
	public String toString()
	{
		return this.listaDeOcorrencias.toString();
	}
	
	public int hashCode()
	{
		return this.listaDeOcorrencias.hashCode();
	}
}