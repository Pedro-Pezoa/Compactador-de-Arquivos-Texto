package Classes;

import java.io.*;
import java.util.BitSet;

import Tipos.Arvore;
import Tipos.Elemento;
import Tipos.ListaDupla;

public class ManipulaByte 
{
	protected Arvore<CharOcorrencia> arvoreDeOcorrencias;
	protected ListaDupla<CharOcorrencia> listaDeOcorrencias;
	protected String nomeDoArquivo;
	protected BitSet bit;
	
	@SuppressWarnings("resource")
	public ManipulaByte() throws Exception {}
	
	public String getNomeDoArquivo()
	{
		return this.nomeDoArquivo;
	}
	
	public void setNomeDoArquivo(String _novoNome) throws Exception
	{
		if (_novoNome == null || _novoNome.equals("")) throw new Exception("Nome do Arquivo Inv�lido");
		if (!new File(_novoNome).exists()) throw new Exception("Arquivo n�o Existente");
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
        
        // Fecha todas as conexoes IO com o usu�rio
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
        
        // Manipula os bytes para que a posi��o do vetor seja o caracter e o conte�do o n�mero de ocorr�ncias
        for (char c : new String(vetByte).toCharArray())
        	contador[c]++;
 
        // Inst�ncia uma String que ira receber todo o conte�do do arquivo para transformar em bits e um BufferedReader 
        // e ler novamente o texto do arquivo
        String txtCompilado = "";
        BufferedReader ent = new BufferedReader(new InputStreamReader(new FileInputStream(this.nomeDoArquivo)));
        
        // Lista o conte�do do arquivo por ordem de ocorr�ncia e depois tranforma esta lista em �rvore
        this.organizaLista(contador);
        arvoreDeOcorrencias = new Arvore<CharOcorrencia>(this.transformaArvore());
        
        // Releitura do conte�do do arquivo para transformar o texto em bits com a �rvore de ocorr�ncia
        while (ent.ready()) txtCompilado += this.compilaTexto(ent.readLine());
        
        // Cria o BitSet para poder alterar todas as posi��es do BitSet para true comparado com o texto em bits
        bit = new BitSet(txtCompilado.length());
        for (int i = 0; i < txtCompilado.length(); i++) 
        	if (txtCompilado.charAt(i) == '1') bit.set(i);
        
        // Cria um novo arquivo que possui o mesmo PATH, NOME e EXTENS�O e escreve nele o texto compilado
        File file = new File(this.nomeDoArquivo);
        file.createNewFile();
        
        // Cria um ObjectOutputStream para poder colocar no arquivo o BitSet do texto com a �rvore de ocorr�ncias
        ObjectOutputStream objs = new ObjectOutputStream(new FileOutputStream(this.nomeDoArquivo)); 
        
        // Printa no arquivo a classe BitSet e a �rvore de ocorr�ncia
        objs.writeObject(bit);
        objs.flush();
        objs.writeChars("///////");
        objs.flush();
        objs.writeObject(arvoreDeOcorrencias);
        objs.flush();
        
        // Fecha todas as classes de entrada e saida de dados
        objs.close();
        ent.close();
        data.close();
        arquivoFisico.close();
	}
	
	private String compilaTexto(String _txt) 
	{
		this.listaDeOcorrencias.iniciaPercurssoSequencial(false);
		int i = 0;
		while(this.listaDeOcorrencias.podePercorrer() && i < _txt.length())
		{
			if (this.listaDeOcorrencias.getAtual().getInfo().getQualCaracter().equals(_letra)) 
				return this.arvoreDeOcorrencias.getQtosEsqDir(this.listaDeOcorrencias.getAtual().getInfo()).replace('D', '1').replace('E', '0');
			this.listaDeOcorrencias.setAtual();
		}
		return null;
		
//		String result = "";
//		for (int i = 0; i < _txt.length(); i++) 
//			result += auxCompilaTexto(_txt.charAt(i)+"");
//		return result;
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

			Elemento<CharOcorrencia> elem3 = new Elemento<CharOcorrencia>(new CharOcorrencia(
					                 elem1.getInfo().getOcorrencia() + elem2.getInfo().getOcorrencia(),
					                 elem1.getInfo().getQualCaracter() + elem2.getInfo().getQualCaracter()));
			if (elem1.getInfo().getOcorrencia() > elem2.getInfo().getOcorrencia())
			{
				elem3.setDir(elem2);
				elem3.setEsq(elem1);
			}
			else
			{
				elem3.setDir(elem1);
				elem3.setEsq(elem2);
			}
			listaAux.incluirOrdenado(elem3);
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
        	listaDeOcorrencias.incluirNoFim(new Elemento<CharOcorrencia>(new CharOcorrencia(vetConteudo[k], vetPos[k]+"")));
	}
	
	public String toString()
	{
		return "Nome do Arquivo: " + this.nomeDoArquivo + " --- �rvore de Compila��o: " + this.arvoreDeOcorrencias.toString()
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