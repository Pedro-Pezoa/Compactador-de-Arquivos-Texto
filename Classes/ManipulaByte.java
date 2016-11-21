package Classes;

import java.io.*;
import java.util.BitSet;

import Tipos.Arvore;
import Tipos.Elemento;
import Tipos.ListaDupla;

/**
 * Classe pública que armazena uma arvore e lista de ocorrências, um BitSet do texto original e o PATH/Nome do arquivo lido
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
	 * Construtor da classe sem nenhum parâmetro
	 */
	public ManipulaByte() throws Exception {}
	
	/**
	 * Método que retorna uma String que representa o PATH/Nome do arquivo
	 * @return O valor do atributo de nomeDoArquivo da classe ManipulaByte
	 */
	public String getNomeDoArquivo()
	{
		return this.nomeDoArquivo;
	}
	
	/**
	 * Método que retorna uma arvore de CharOcorrencia que representa a arvore de ocorrência do arquivo
	 * @return O valor do atributo de arvoreDeOcorrencias da classe ManipulaByte
	 */
	public Arvore<CharOcorrencia> getArvore()
	{
		return this.arvoreDeOcorrencias;
	}
	
	/**
	 * Método que retorna uma lista de CharOcorrencia que representa a lista de ocorrência do arquivo
	 * @return O valor do atributo de listaDeOcorrencias da classe ManipulaByte
	 */
	public ListaDupla<CharOcorrencia> getLista()
	{
		return this.listaDeOcorrencias;
	}
	
	/**
	 * Método que altera o atributo nomeDoArquivo da classe ManipulaByte por uma String que representa o novo PATH/Nome do arquivo escolhido
	 * @param _novoNome String que armazena o PATH/Nome do arquivo escolhido
	 * @throws Será lançado uma exceção se o parâmetro for nulo ou vazio ou se o arquivo que representa o nome não existir 
	 */
	public void setNomeDoArquivo(String _novoNome) throws Exception
	{
		if (_novoNome == null || _novoNome.equals("")) throw new Exception("Nome do arquivo inválido, por favor escolha um arquivo");
		if (!new File(_novoNome).exists()) throw new Exception("Arquivo não existente, por favor escolha um arquivo que exista");
		this.nomeDoArquivo = _novoNome;
	}
	
    //------------------------------------------------------------------------------------------------------------------------------------//
    //----------------------------------------------------------Métodos Principais--------------------------------------------------------//
    //------------------------------------------------------------------------------------------------------------------------------------//
	
	/**
	 * Método que descompila o arquivo texto para um formato legivel para o usuário entender e com mais espaço em bytes
	 * @throws Será lançado uma exceção se os ObjetosIO não serem instânciados direito ou se o arquivo não existir
	 */
	@SuppressWarnings("unchecked")
	public void descompilaArquivo() throws Exception
	{
		// Leitura das classes BitSet e arvore de ocorrência
		ObjectInputStream obji = new ObjectInputStream(new FileInputStream(this.nomeDoArquivo));
		BitSet bit = (BitSet)obji.readObject();
		this.arvoreDeOcorrencias = (Arvore<CharOcorrencia>)obji.readObject();
		
		// For para percorrer o BitSet e selecionar uma parcela dos bits para poder procurar na arvore de ocorrência e assim transformar os bits em texto
		String seq = "", result = "";
		for (int i = 0; i < bit.length(); i++) 
		{
			// Verifica se é true ou false
			if (bit.get(i)) seq += "D";
			else seq += "E";
			
			// Percorre a arvore para verificar se existe uma ocorrência dessa sequência de 
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
	 * Método que compila o arquivo texto para um formato ilegivel para o usuário entender e com menos espaço em bytes
	 * @throws Será lançado uma exceção se os ObjetosIO não serem instânciados direito ou se o arquivo não existir ou se 
	 *         os outros métodos auxiliares que ela utiliza lançarem exceção
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
        
        // Manipula os bytes para que a posição do vetor seja o caracter e o conteúdo o número de ocorrências
        for (char c : new String(vetByte).toCharArray())
        	contador[c]++;
 
        // Instância uma String que ira receber todo o conteúdo do arquivo para transformar em bits e um BufferedReader 
        // e ler novamente o texto do arquivo
        String txtCompilado = "";
        BufferedReader ent = new BufferedReader(new InputStreamReader(new FileInputStream(this.nomeDoArquivo)));
        
        // Lista o conteúdo do arquivo por ordem de ocorrência e depois tranforma esta lista em árvore
        this.organizaLista(contador);
        arvoreDeOcorrencias = new Arvore<CharOcorrencia>(this.transformaArvore());
        
        // Releitura do conteúdo do arquivo para transformar o texto em bits com a árvore de ocorrência
        while (ent.ready()) txtCompilado += this.compilaTexto(ent.readLine());
        
        // Cria o BitSet para poder alterar todas as posições do BitSet para true comparado com o texto em bits
        BitSet bit = new BitSet(txtCompilado.length());
        for (int i = 0; i < txtCompilado.length(); i++) 
        	if (txtCompilado.charAt(i) == '1') bit.set(i);
        
        // Cria um novo arquivo que possui o mesmo PATH, NOME e EXTENSÃO e escreve nele o texto compilado
        File file = new File(this.nomeDoArquivo);
        file.createNewFile();
        
        // Cria um ObjectOutputStream para poder colocar no arquivo o BitSet do texto com a árvore de ocorrências
        ObjectOutputStream objs = new ObjectOutputStream(new FileOutputStream(this.nomeDoArquivo)); 
        
        // Printa no arquivo a classe BitSet e a árvore de ocorrência
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
    //----------------------------------------------------------Métodos Auxiliares--------------------------------------------------------//
    //------------------------------------------------------------------------------------------------------------------------------------//
	
	/**
	 * Método que auxilia o compilaArquivo transformando a String do arquivo em bits para o BitSet
	 * @param _txt String que armazena o texto do arquivo lido
	 * @return Retorna uma String com 0 e 1 para o BitSet
	 */
	private String compilaTexto(String _txt) 
	{
		// Começa a percorrer a lista e instância uma String result
		this.listaDeOcorrencias.iniciaPercurssoSequencial(false);
		String result = "";
		
		// Percorre a lista para poder achar os caracteres do parâmetro _txt com o da lista
		for (int i = 0; i < _txt.length();)
		{
			if (this.listaDeOcorrencias.getAtual().getInfo().getQualCaracter().equals(_txt.charAt(i) + "")) 
			{
				// Caso o caracter da lista seja o desejado do _txt, será feito o método da arvore que retorna quantas vezes ele foi para a esquerda e 
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
	 * Método que transforma uma lista de ocorrência em uma arvore de ocorrência percorrendo a lista, somando seus caracteres e suas ocorrências e transformando
	 * em uma raiz de uma arvore, adicionando os valores somados ou na esquerda ou na direita e adicionar a raiz da árvore na lista
	 * @throws Será lançado uma exceção caso a lista não clone ela mesma direito ou se a classe CharOcorrencia não seja instânciado direito
	 * @return Retorna um Elemento que será a raiz principal da arvore de ocorrência
	 */
	private Elemento<CharOcorrencia> transformaArvore() throws Exception
	{
		// Clona a lista de ocorrência e começa a percorrer a lista clonada de ocorrência
		ListaDupla<CharOcorrencia> listaAux = this.listaDeOcorrencias.clonaLista();
		listaAux.iniciaPercurssoSequencial(true);
		
		// Percorre a lista
		while (listaAux.podePercorrer())
		{
			// Tira da lista os dois últimos e atribui eles em variáveis separadas
			Elemento<CharOcorrencia> elem1 = listaAux.getAtual();
			listaAux.excluirDoFim();
			Elemento<CharOcorrencia> elem2 = listaAux.getAtual();
			listaAux.excluirDoFim();

			// Cria um elemento que suas ocorrências e caracteres serão a soma dos/das outros/outras caracteres e ocorrências
			Elemento<CharOcorrencia> elem3 = new Elemento<CharOcorrencia>(new CharOcorrencia(
					                 elem1.getInfo().getOcorrencia() + elem2.getInfo().getOcorrencia(),
					                 elem1.getInfo().getQualCaracter() + elem2.getInfo().getQualCaracter()));
			
			// Compara qual dos elementos é o maior e o menor e dependendo de qual seja será armazenado na esquerda ou na direita do terceito elemento 
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
		// Retorna o primeiro elemento da lista que será a raiz principal da arvore de ocorrência
		return listaAux.getInicio();
	}

	/**
	 * Método que organiza um vetor  de ocorrências e caracteres em uma lista de ocorrências colocando os caracteres e as ocorrências numa classe CharOcorrencia
	 * @throws Será lançado uma exceção caso a classe CharOcorrencia não seja instânciado direito
	 * @return Retorna uma lista de ocorrências organizada
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
    //----------------------------------------------------------Métodos Apocalipticos--------------------------------------------------------//
    //---------------------------------------------------------------------------------------------------------------------------------------//
	
	/**
	 * Método que cria uma String que representa os atributos da classe em sí
	 * @return Uma String que possui os valores dos atributos da classe em sí
	 */
	public String toString()
	{
		return "Nome do Arquivo: " + this.nomeDoArquivo + " --- Árvore de Compilação: " + this.arvoreDeOcorrencias.toString()
	 	       + " --- Lista de Ocorrencia: " + this.listaDeOcorrencias.toString();
	}
	
	/**
	 * Método que calcula os valores dos endereços de memória de todos os atributos da classe
	 * @return O valor dos endereços de memória
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