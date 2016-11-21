package Classes;

import java.io.*;
import java.util.BitSet;

import Tipos.Arvore;
import Tipos.Elemento;
import Tipos.ListaDupla;

/**
 * Classe p�blica que armazena uma arvore e lista de ocorr�ncias, um BitSet do texto original e o PATH/Nome do arquivo lido
 * para poder compilar e descompilar arquivos texto
 * 
 * @author Pedro Luiz Pezoa u15190
 * @since 2016
 */
public class ManipulaByte 
{
	/**
	 * Arvore de CharOcorrencia que armazena os Objetos de CharOcorrencia
	 */
	protected Arvore<CharOcorrencia> arvoreDeOcorrencias;
	
	/**
	 * Lista de CharOcorrencia que armazena os Objetos de CharOcorrencia
	 */
	protected ListaDupla<CharOcorrencia> listaDeOcorrencias;
	
	/**
	 * String que armazena o PATH/Nome do arquivo escolhido
	 */
	protected String nomeDoArquivo;
	
    //------------------------------------------------------------------------------------------------------------------------------------//
    //----------------------------------------------------Construtor, Getters e Setter---------------------------------------------------//
    //------------------------------------------------------------------------------------------------------------------------------------//
	
	/**
	 * Construtor da classe sem nenhum par�metro
	 */
	public ManipulaByte() throws Exception {}
	
	/**
	 * M�todo que retorna uma String que representa o PATH/Nome do arquivo
	 * @return O valor do atributo de nomeDoArquivo da classe ManipulaByte
	 */
	public String getNomeDoArquivo()
	{
		return this.nomeDoArquivo;
	}
	
	/**
	 * M�todo que retorna uma arvore de CharOcorrencia que representa a arvore de ocorr�ncia do arquivo
	 * @return O valor do atributo de arvoreDeOcorrencias da classe ManipulaByte
	 */
	public Arvore<CharOcorrencia> getArvore()
	{
		return this.arvoreDeOcorrencias;
	}
	
	/**
	 * M�todo que retorna uma lista de CharOcorrencia que representa a lista de ocorr�ncia do arquivo
	 * @return O valor do atributo de listaDeOcorrencias da classe ManipulaByte
	 */
	public ListaDupla<CharOcorrencia> getLista()
	{
		return this.listaDeOcorrencias;
	}
	
	/**
	 * M�todo que altera o atributo nomeDoArquivo da classe ManipulaByte por uma String que representa o novo PATH/Nome do arquivo escolhido
	 * @param _novoNome String que armazena o PATH/Nome do arquivo escolhido
	 * @throws Ser� lan�ado uma exce��o se o par�metro for nulo ou vazio ou se o arquivo que representa o nome n�o existir 
	 */
	public void setNomeDoArquivo(String _novoNome) throws Exception
	{
		if (_novoNome == null || _novoNome.equals("")) throw new Exception("Nome do arquivo inv�lido, por favor escolha um arquivo");
		if (!new File(_novoNome).exists()) throw new Exception("Arquivo n�o existente, por favor escolha um arquivo que exista");
		this.nomeDoArquivo = _novoNome;
	}
	
    //------------------------------------------------------------------------------------------------------------------------------------//
    //----------------------------------------------------------M�todos Principais--------------------------------------------------------//
    //------------------------------------------------------------------------------------------------------------------------------------//
	
	/**
	 * M�todo que descompila o arquivo texto para um formato legivel para o usu�rio entender e com mais espa�o em bytes
	 * @throws Ser� lan�ado uma exce��o se os ObjetosIO n�o serem inst�nciados direito ou se o arquivo n�o existir
	 */
	@SuppressWarnings("unchecked")
	public void descompilaArquivo() throws Exception
	{
		// Leitura das classes BitSet e arvore de ocorr�ncia
		ObjectInputStream obji = new ObjectInputStream(new FileInputStream(this.nomeDoArquivo));
		BitSet bit = (BitSet)obji.readObject();
		this.arvoreDeOcorrencias = (Arvore<CharOcorrencia>)obji.readObject();
		
		// For para percorrer o BitSet e selecionar uma parcela dos bits para poder procurar na arvore de ocorr�ncia e assim transformar os bits em texto
		String seq = "", result = "";
		for (int i = 0; i < bit.length(); i++) 
		{
			// Verifica se � true ou false
			if (bit.get(i)) seq += "D";
			else seq += "E";
			
			// Percorre a arvore para verificar se existe uma ocorr�ncia dessa sequ�ncia de 
			// caracteres e se houver ira pegar o caracter e concatenar na String result
			CharOcorrencia resp = this.arvoreDeOcorrencias.existeEsqDir(seq);
			if (resp != null)
			{
				result += resp.getQualCaracter();
				seq = "";
			}
		}
		
		// Cria um novo arquivo para printar nele o texto descompilado
		File file = new File(this.nomeDoArquivo);
        file.createNewFile();
        
        // Printa o texto descompilado
        PrintWriter sai = new PrintWriter(new FileOutputStream(this.nomeDoArquivo));
        sai.println(result);
        sai.flush();
        
        // Fecha todas as classes de entrada e saida de dados
        sai.close();
        obji.close();
	}

	/**
	 * M�todo que compila o arquivo texto para um formato ilegivel para o usu�rio entender e com menos espa�o em bytes
	 * @throws Ser� lan�ado uma exce��o se os ObjetosIO n�o serem inst�nciados direito ou se o arquivo n�o existir ou se 
	 *         os outros m�todos auxiliares que ela utiliza lan�arem exce��o
	 */
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
        BitSet bit = new BitSet(txtCompilado.length());
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
        objs.writeObject(arvoreDeOcorrencias);
        objs.flush();
        
        // Fecha todas as classes de entrada e saida de dados
        objs.close();
        ent.close();
        data.close();
        arquivoFisico.close();
	}
	
    //------------------------------------------------------------------------------------------------------------------------------------//
    //----------------------------------------------------------M�todos Auxiliares--------------------------------------------------------//
    //------------------------------------------------------------------------------------------------------------------------------------//
	
	/**
	 * M�todo que auxilia o compilaArquivo transformando a String do arquivo em bits para o BitSet
	 * @param _txt String que armazena o texto do arquivo lido
	 * @return Retorna uma String com 0 e 1 para o BitSet
	 */
	private String compilaTexto(String _txt) 
	{
		// Come�a a percorrer a lista e inst�ncia uma String result
		this.listaDeOcorrencias.iniciaPercurssoSequencial(false);
		String result = "";
		
		// Percorre a lista para poder achar os caracteres do par�metro _txt com o da lista
		for (int i = 0; i < _txt.length();)
		{
			if (this.listaDeOcorrencias.getAtual().getInfo().getQualCaracter().equals(_txt.charAt(i) + "")) 
			{
				// Caso o caracter da lista seja o desejado do _txt, ser� feito o m�todo da arvore que retorna quantas vezes ele foi para a esquerda e 
				// direita quando este percorreu a arvore
				result += this.arvoreDeOcorrencias.getQtosEsqDir(this.listaDeOcorrencias.getAtual().getInfo()).replace('D', '1').replace('E', '0');
				this.listaDeOcorrencias.iniciaPercurssoSequencial(false);
				i++;
			}
			else this.listaDeOcorrencias.setPosAtual();
		}
		return result;
	}

	/**
	 * M�todo que transforma uma lista de ocorr�ncia em uma arvore de ocorr�ncia percorrendo a lista, somando seus caracteres e suas ocorr�ncias e transformando
	 * em uma raiz de uma arvore, adicionando os valores somados ou na esquerda ou na direita e adicionar a raiz da �rvore na lista
	 * @throws Ser� lan�ado uma exce��o caso a lista n�o clone ela mesma direito ou se a classe CharOcorrencia n�o seja inst�nciado direito
	 * @return Retorna um Elemento que ser� a raiz principal da arvore de ocorr�ncia
	 */
	private Elemento<CharOcorrencia> transformaArvore() throws Exception
	{
		// Clona a lista de ocorr�ncia e come�a a percorrer a lista clonada de ocorr�ncia
		ListaDupla<CharOcorrencia> listaAux = this.listaDeOcorrencias.clonaLista();
		listaAux.iniciaPercurssoSequencial(true);
		
		// Percorre a lista
		while (listaAux.podePercorrer())
		{
			// Tira da lista os dois �ltimos e atribui eles em vari�veis separadas
			Elemento<CharOcorrencia> elem1 = listaAux.getAtual();
			listaAux.excluirDoFim();
			Elemento<CharOcorrencia> elem2 = listaAux.getAtual();
			listaAux.excluirDoFim();

			// Cria um elemento que suas ocorr�ncias e caracteres ser�o a soma dos/das outros/outras caracteres e ocorr�ncias
			Elemento<CharOcorrencia> elem3 = new Elemento<CharOcorrencia>(new CharOcorrencia(
					                 elem1.getInfo().getOcorrencia() + elem2.getInfo().getOcorrencia(),
					                 elem1.getInfo().getQualCaracter() + elem2.getInfo().getQualCaracter()));
			
			// Compara qual dos elementos � o maior e o menor e dependendo de qual seja ser� armazenado na esquerda ou na direita do terceito elemento 
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
			
			// Inclui na lista o terceiro elemento ordenadamente
			listaAux.incluirOrdenado(elem3);
		}
		// Retorna o primeiro elemento da lista que ser� a raiz principal da arvore de ocorr�ncia
		return listaAux.getInicio();
	}

	/**
	 * M�todo que organiza um vetor  de ocorr�ncias e caracteres em uma lista de ocorr�ncias colocando os caracteres e as ocorr�ncias numa classe CharOcorrencia
	 * @throws Ser� lan�ado uma exce��o caso a classe CharOcorrencia n�o seja inst�nciado direito
	 * @return Retorna uma lista de ocorr�ncias organizada
	 */
	private void organizaLista(int[] _contador) throws Exception
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
	
    //---------------------------------------------------------------------------------------------------------------------------------------//
    //----------------------------------------------------------M�todos Apocalipticos--------------------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------------------------------//
	
	/**
	 * M�todo que cria uma String que representa os atributos da classe em s�
	 * @return Uma String que possui os valores dos atributos da classe em s�
	 */
	public String toString()
	{
		return "Nome do Arquivo: " + this.nomeDoArquivo + " --- �rvore de Compila��o: " + this.arvoreDeOcorrencias.toString()
	 	       + " --- Lista de Ocorrencia: " + this.listaDeOcorrencias.toString();
	}
	
	/**
	 * M�todo que calcula os valores dos endere�os de mem�ria de todos os atributos da classe
	 * @return O valor dos endere�os de mem�ria
	 */
	public int hashCode()
	{
		int ret = super.hashCode();
		ret *= 7 + this.nomeDoArquivo.hashCode();
		ret *= 7 + this.arvoreDeOcorrencias.hashCode();
		ret *= 7 + this.listaDeOcorrencias.hashCode();
		return ret;
	}
}