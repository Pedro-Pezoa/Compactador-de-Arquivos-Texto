package Classes;

import java.io.*;

import Tipos.Arvore;
import Tipos.Elemento;
import Tipos.ListaDupla;

public class ManipulaByte 
{
	protected Arvore<CharOcorrencia> arvoreDeOcorrencias;
	protected String nomeDoArquivo;
	protected ListaDupla<CharOcorrencia> listaDeOcorrencias;
	
	@SuppressWarnings("resource")
	public ManipulaByte() throws Exception {}
	
	public String getNomeDoArquivo()
	{
		return this.nomeDoArquivo;
	}
	
	public void setNomeDoArquivo(String _novoNome) throws Exception
	{
		if (_novoNome == null || _novoNome.equals("")) throw new Exception("Nome do Arquivo Inválido");
		if (!new File(_novoNome).exists()) throw new Exception("Arquivo não Existente");
		this.nomeDoArquivo = _novoNome;
	}
	
	public void descompilaArquivo() throws Exception
	{
		BufferedReader ent = new BufferedReader(new InputStreamReader(new FileInputStream(this.nomeDoArquivo)));
		String txtDescomp = "";
		
		while (ent.ready()) txtDescomp += this.descompilaTexto(ent.readLine().replace('1', 'D').replace('0', 'E'));
		
		File file = new File(this.nomeDoArquivo);
        file.createNewFile();
        
        PrintWriter sai = new PrintWriter(file);
        sai.println(txtDescomp);
        sai.flush();
        
        // Fecha todas as conexoes IO com o usuário
        sai.close();
        ent.close();
	}
	
	private String descompilaTexto(String _txt) 
	{
		String aux = _txt.charAt(0) + "";
		for (int i = 0, j = 0; i < _txt.length(); i++) 
		{
			CharOcorrencia chare = this.arvoreDeOcorrencias.existeEsqDir(aux);
			if (chare != null)
			{
				_txt = 
				aux = _txt.charAt(i) + "";
			}
			else aux += _txt.charAt(i);
		}
		return _txt;
	}

	public void compilaArquivo() throws Exception
	{
		// Leitura dos bytes do Arquivo 
		FileInputStream arquivoFisico = new FileInputStream(this.nomeDoArquivo);
		DataInputStream data = new DataInputStream(new BufferedInputStream(arquivoFisico));
        
		// Insere esses dados em um vetor de bytes
        byte vetByte[] = new byte[arquivoFisico.available()];
        int[] contador = new int[256];
        data.read(vetByte);
        
        // Manipula os bytes para que a posição do vetor seja o caracter e o conteúdo o número de ocorrências
        for (char c : new String(vetByte).toCharArray())
        	contador[c]++;
 
        String txtCompilado = "";
        BufferedReader ent = new BufferedReader(new InputStreamReader(new FileInputStream(this.nomeDoArquivo)));
        
        // Lista o conteúdo do arquivo por ordem de ocorrência e depois tranforma esta lista em árvore
        this.organizaLista(contador);
        arvoreDeOcorrencias = new Arvore<CharOcorrencia>(this.transformaArvore());
        
        // Releitura do conteúdo do arquivo para transformar o texto em bytes com a árvore em bytes
        while (ent.ready()) txtCompilado += this.compilaTexto(ent.readLine());
        
        // Cria um novo arquivo que possui o mesmo PATH, NOME e EXTENSÃO e escreve nele o texto compilado em bytes
        File file = new File(this.nomeDoArquivo);
        file.createNewFile();
        
        PrintWriter sai = new PrintWriter(file);
        sai.println(txtCompilado);
        sai.flush();
        
        // Fecha todas as conexoes IO com o usuário
        sai.close();
        ent.close();
        data.close();
        arquivoFisico.close();
	}
	
	private String compilaTexto(String _txt) 
	{
		String result = "";
		for (int i = 0; i < _txt.length(); i++) 
			result += auxCompilaTexto(_txt.charAt(i)+"");
		return result;
	}

	private String auxCompilaTexto(String _letra) 
	{
		this.listaDeOcorrencias.iniciaPercurssoSequencial(false);
		while(this.listaDeOcorrencias.podePercorrer())
		{
			if (this.listaDeOcorrencias.getAtual().getInfo().getQualCaracter().equals(_letra)) 
				return this.arvoreDeOcorrencias.getQtosEsqDir(this.listaDeOcorrencias.getAtual().getInfo()).replace('D', '1').replace('E', '0');
			this.listaDeOcorrencias.setAtual();
		}
		return null;
	}

	private Elemento<CharOcorrencia> transformaArvore() throws Exception 
	{
		ListaDupla<CharOcorrencia> listaAux = this.listaDeOcorrencias.clonaLista();
		listaAux.iniciaPercurssoSequencial(true); // Percorre reverso
		while (listaAux.podePercorrer())
		{
			Elemento<CharOcorrencia> elem1 = listaAux.getAtual();
			listaAux.excluirDoFim();
			Elemento<CharOcorrencia> elem2 = listaAux.getAtual();
			listaAux.excluirDoFim();

			Arvore<CharOcorrencia> arv = new Arvore<CharOcorrencia>(new Elemento<CharOcorrencia>(
					                                               new CharOcorrencia(elem1.getInfo().getOcorrencia() + 
                                            		               elem2.getInfo().getOcorrencia(),
                                                                   elem1.getInfo().getQualCaracter() + 
                                                                   elem2.getInfo().getQualCaracter())));
			if (elem1.getInfo().getOcorrencia() > elem2.getInfo().getOcorrencia())
			{
				arv.incluirNaDireita(elem2);
				arv.incluirNaEsquerda(elem1);
			}
			else if (elem1.getInfo().getOcorrencia() < elem2.getInfo().getOcorrencia())
			{
				arv.incluirNaDireita(elem1);
				arv.incluirNaEsquerda(elem2);
			}
			listaAux.incluirOrdenado(arv.getRaiz());
		}
		return listaAux.getInicio();
	}

	private void organizaLista(int[] _contador)
	{
		this.listaDeOcorrencias = new ListaDupla<CharOcorrencia>();
		int[] vetConteudo = new int[256];
		char[] vetPos = new char[256];
		
		for (int i = 32, j = 0; i < _contador.length; i++) 
        {
        	if (_contador[i] != 0) 
        	{
        		vetConteudo[j] = _contador[i];
        		vetPos[j++] = (char)(i);
        	}
        }	
        
        int i = 0, j = 1, auxInt = 0;
        char auxChar = ' ';
        while (vetConteudo[i] > 0)
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
        	if (vetConteudo[j] == 0) j = ++i + 1;
        	else j++;
        }
        
        for (int k = 0; vetConteudo[k] > 0; k++) 
        	listaDeOcorrencias.incluirNoFim(new Elemento<CharOcorrencia>(
        			                        new CharOcorrencia(vetConteudo[k], vetPos[k]+"")));
	}
	
	public String toString()
	{
		return "Nome do Arquivo: " + this.nomeDoArquivo + " --- Árvore de Compilação: " + this.arvoreDeOcorrencias.toString()
	 	       + " --- Lista de Ocorrencia: " + this.listaDeOcorrencias.toString();
	}
	
	public int hashCode()
	{
		int ret = super.hashCode();
		ret *= 7 + this.nomeDoArquivo.hashCode();
		ret *= 7 + this.arvoreDeOcorrencias.hashCode();
		ret *= 7 + this.listaDeOcorrencias.hashCode();
		return ret;
	}
}