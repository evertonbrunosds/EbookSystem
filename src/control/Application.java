package control;

import model.Ebook;
import util.EbookTreeStream;
import util.StringStream;
import util.Node;
import java.io.File;
import view.Screen;

/**
 * Classe responsável por controlar toda a aplicação.
 * @author Everton Bruno Silva dos Santos
 */
public class Application {
    private final EbookTreeStream ebookTreeStream;
    private final String searchResults;

    /**
     * Construtor responsável por inicializar a classe, bem como, inicializar a estrutura capaz de comportar-se como uma árvore AVL.
     * Ademais, este construtor atua criando o diretório onde serão destinados os resultados de busca.
     */
    public Application() {
        ebookTreeStream = new EbookTreeStream();                    //ESTRUTURA CAPAZ DE COMPORTAR-SE COMO UMA ÁRVORE AVL.
        final String osName = System.getProperty("os.name");        //CAPTURA O NOME DO SISTEMA OPERACIONAL
        if (osName.equals("Linux") || osName.equals("Mac OS X")) {  //VERIFICA SE É LINUX OU MAC
            searchResults = "SearchResults/";                       //USA ESTRUTURA DE DIRETÓRIOS DO LINUX E MAC
        } else {                                                    //NÃO SENDO LINUX OU MAC, SUPÕEM-SE QUE SEJA WINDOWS
            searchResults = "SearchResults\\";                      //USA ESTRUTURA DE DIRETÓRIOS DO WINDOWS
        }
        final File file = new File(searchResults);
        if (!file.exists()) {                                       //VERIFICA SE O DIRETÓRIO QUE ARMAZENA AS BUSCAS NÃO EXISTE
            if (!file.mkdir()) {                                    //VERIFICA SE É POSSÍVEL CRIAR DIRETÓRIO DE ARMAZENAMENTO DE BUSCAS
                final Screen screen = new Screen();
                screen.showMessage("Falha! O sistema encontra-se incapaz de criar o diretório de registro de busca."
                        + "Aviso! O sistema pode apresentar falhas durante a execussão.");
            }
        }
    }


    /**
     * Método responsável por retornar valor boleano que indica se a árvore está vazia.
     * @return Retorna valor boleano.
     */
    public boolean treeIsEmpty() {
        return ebookTreeStream.isEmpty();
    }

    /**
     * Método responsável por registrar um novo livro no sistema.
     * @param title     Refere-se ao nome do livro.
     * @param author    Refere-se ao autor do livro.
     * @param month     Refere-se ao mês de publicação do livro.
     * @param year      Refere-se ao ano de publicação do livro.
     * @param url       Refere-se ao URL onde encontra-se disponível o livro.
     */
    public void registerBook(final String title, final String author, final String month, final int year,
            final String url) {
        final int newKey = ebookTreeStream.highestKey() + 1;
        ebookTreeStream.insert(newKey, new Ebook(newKey, title, author, month, year, url));
    }

    /**
     * Método responsável por carregar arquivos na estrutura principal do sistema (uma árvore AVL).
     * @param fileName  Refere-se ao nome do arquivo.
     * @return          Retorna valor boleano que indica se a operação foi bem sucedida ou não.
     */
    public boolean loadFromFile(final String fileName) {
        System.out.println("Carregando arquivo, aguarde...");
        return ebookTreeStream.loadFromFile(fileName);
    }

    /**
     * Método responsável por salvar em arquivos os dados contidos na estrutura principal do sistema (uma árvore AVL).
     * @param fileName  Refere-se ao nome do arquivo.
     * @return          Retorna valor boleano que indica se a operação foi bem sucedida ou não.
     */
    public boolean saveFromFile(final String fileName) {
        System.out.println("Gravando arquivo, aguarde...");
        return ebookTreeStream.saveFromFile(fileName);
    }

    /**
     * Método responsável por listar todos os autores juntamente com a quantidade de obras catalogadas.
     * @return  Retorna lista contendo os dados da listagem feita na árvore AVL.
     */
    public StringStream catalogedWorksAndListAuthors() {
        System.out.println("Carregando, aguarde...");
        final StringStream stringStream = new StringStream();
        stringStream.add("----------------------------LISTA DE AUTORES---------------------------");
        listAuthors(ebookTreeStream.getRoot(), stringStream);   //EFETUA LISTAGEM DE AUTORES EM UM MÉTODO AUXILIAR RECURSIVO.
        stringStream.add("----------------------------LISTA DE AUTORES---------------------------");
        stringStream.add("QUANTIDADE DE OBRAS CATALOGADAS: " + ebookTreeStream.size());
        if (stringStream.size() == 3) {                         //VERIFICA SE NA LISTA TEM ALGUM AUTOR
            stringStream.clear();                               //NÃO HAVENDO, LIMPA A LISTA
            stringStream.add("Listagem Indisponível! Não há dados no sistema.");    //PÕEM DADOS QUE INDICAM QUE NÃO HÁ NADA NA LISTA
        }
        return stringStream;
    }

    /**
     * Método responsável por listar os nomes de todos os autores sem repetições.
     * @param node          Refere-se ao nó raiz da árvore AVL.
     * @param stringStream  Refere-se a lista que armazenará os dados a listagem.
     */
    private void listAuthors(final Node node, final StringStream stringStream) {
        if (node != null) {
            listAuthors(node.getSonOnTheLeft(), stringStream);                      //INICIA NOVA RECURSÃO À ESQUERDA
            if (!stringStream.search(((Ebook) node.getElement()).getAuthor())) {    //VERIFICA SE O AUTOR DO NÓ ATUAL JÁ ESTÁ NA LISTA
                stringStream.add(((Ebook) node.getElement()).getAuthor());          //NÃO ESTANDO, INSERE O AUTOR DO NÓ ATUAL
            }
            listAuthors(node.getSonOnTheRight(), stringStream);                     //INICIA NOVA RECURSÃO À DIREITA
        }
    }

    /**
     * Método responsável por listar todas as obras de um dado autor.
     * @param author Refere-se ao nome do autor.
     * @return       Retorna ao resultado do método contendo o resultado da listagem, bem como, da funcionalidade de salvar a listagem em arquivo.
     */
    public Object[] searchEbookByAuthor(final String author) {
        System.out.println("Carregando, aguarde...");
        final Object[] result = new Object[2];
        final StringStream stringStream = new StringStream();
        stringStream.add("----------------LIVROS DO AUTOR: [" + author + "]----------------");
        searchEbookByAuthor(ebookTreeStream.getRoot(), author, stringStream);       //EFETUA LISTAGEM DE OBRAS DE UM DADO AUTOR EM MÉTODO AUXILIAR RECURSIVO
        stringStream.add("----------------LIVROS DO AUTOR: [" + author + "]----------------");
        if (stringStream.size() == 2) {     //VERIFICA SE NA LISTA TEM ALGUMA OBRA
            stringStream.clear();           //NÃO HAVENDO, LIMPA A LISTA
            stringStream.add("Listagem Indisponível! O autor " + author + " não tem obras catalogadas.");   //PÕEM DADOS QUE INDICAM QUE NÃO HÁ NADA NA LISTA
        }
        result[0] = stringStream.saveFromFile(searchResults + author + ".txt");     //SALVA OS DADOS A LISTA EM ARQUIVO AO MESMO TEMPO QUE ARMAZENA O RESULTADO BOLEANO DA OPERAÇÃO
        result[1] = stringStream;                                                   //ARMAZENA A LISTA
        return result;
    }

    /**
     * Método responsável por listar todos os livros catalogados pertencentes a um dado autor.
     * @param node          Refere-se ao nó raiz da árvore AVL.
     * @param author        Refere-se ao nome do autor.
     * @param stringStream  Refere-se a lista onde serão armazenados os dados.
     */
    private void searchEbookByAuthor(final Node node, final String author, final StringStream stringStream) {
        if (node != null) {
            searchEbookByAuthor(node.getSonOnTheLeft(), author, stringStream);  //INICIA NOVA RECURÇÃO A ESQUERDA
            if (((Ebook) node.getElement()).getAuthor().equals(author)) {       //VERIFICA SE O AUTOR DO NÓ ATUAL É O PROCURADO
                stringStream.add(((Ebook) node.getElement()).getTitle());       //SENDO, INSERE O LIVRO DO AUTOR NA LISTA
            }
            searchEbookByAuthor(node.getSonOnTheRight(), author, stringStream); //INICIA NOVA RECURSÃO A DIREITA
        }
    }

    /**
     * Método responsável por listar todos os livros catalogados.
     * @return  Retorna lista contendo todos os livros catalogados.
     */
    public StringStream listEbook() {
        final StringStream stringStream = new StringStream();
        stringStream.add("----------------LISTA DE LIVROS CATALOGADOS----------------");
        listEbook(ebookTreeStream.getRoot(), stringStream); //EFETUA LISTAGEM DE TODOS OS LIVROS EM MÉTODO AUXILIAR RECURSIVO
        stringStream.add("----------------LISTA DE LIVROS CATALOGADOS----------------");
        if (stringStream.size() == 2) { //VERIFICA SE NA LISTA TEM ALGUM LIVRO
            stringStream.clear();       //NÃO HAVENDO, LIMPA A LISTA
            stringStream.add("Listagem Indisponível! Não há obras catalogadas no sistema.");    //NÃO HAVENDO, PÕEM DADOS QUE INDICAM QUE NA LISTA NÃO HÁ NADA
        }
        return stringStream;
    }

    /**
     * Método responsável por listar todos os livros catalogados.
     * @param node          Refere-se ao nó raiz da árvore AVL.
     * @param stringStream  Refere-se a lista onde serão armazenados todos os livros.
     */
    private void listEbook(final Node node, final StringStream stringStream) {
        if (node != null) {
            listEbook(node.getSonOnTheLeft(), stringStream);
            stringStream.add(((Ebook) node.getElement()).getTitle());
            listEbook(node.getSonOnTheRight(), stringStream);
        }
    }

    /**
     * Método responsável por buscar por um determinado livro por meio de seu número de ebook afim de exibir seu URL.
     * @param number    Refere-se ao número de identificação do livro.
     * @return          Retorna ao resultado do método contendo o resultado da busca, bem como, da funcionalidade de salvar a busca em arquivo.
     */
    public Object[] searchEbookByNumber(final int number) {
        final Object[] result = new Object[2];
        final Ebook ebook = (Ebook) ebookTreeStream.search(number);  //BUSCA POR NÓ ONDE POSSÍVELMENTE SE ENCONTRA O LIVRO
        final StringStream stringStream = new StringStream();
        if (ebook != null) {                                        //VERIFICA SE O LIVRO FOI ENCONTRADO
            stringStream.add(ebook.getUrl());                       //CASO TENHA SIDO, INSERE SEU URL EM UMA LISTA
        } else {                                                    //VERIFICA SE O LIVRO NÃO FOI ENCONTRADO
            stringStream.add("Link Indisponível! O livro cujo número é " + number + " não está contido no sistema.");   //CASO NÃO TENHA SIDO, PÕEM DADO QUE INDICA QUE O LIVRO NÃO FOI ACHADO
        }
        result[0] = stringStream.saveFromFile(searchResults + number + ".txt");     //SALVA OS DADOS A LISTA EM ARQUIVO AO MESMO TEMPO QUE ARMAZENA O RESULTADO BOLEANO DA OPERAÇÃO
        result[1] = stringStream;                                                   //ARMAZENA A LISTA
        return result;
    }

    /**
     * Método responsável por buscar por livros que foram publicados em determinado ano exibindo todas as suas informações.
     * @param year  Refere-se ao ano que será usado como chave de busca.
     * @return      Retorna ao resultado do método contendo o resultado da busca, bem como, da funcionalidade de salvar a busca em arquivo. 
     */
    public Object[] searchEbookByYear(final int year) {
        System.out.println("Carregando, aguarde...");
        final Object[] result = new Object[2];
        final StringStream stringStream = new StringStream();
        stringStream.add("----------------LIVROS DO ANO: [" + year + "]----------------");
        searchEbookByYear(ebookTreeStream.getRoot(), year, stringStream);    //EFATUA LISTAGEM DE LIVROS PUBLICADOS EM DETERMINADO ANO EM MÉTODO AUXILIAR   
        stringStream.add("-----------------------------------------------------");
        stringStream.add("----------------LIVROS DO ANO: [" + year + "]----------------");
        if(stringStream.size() == 3){   //VERIFICA SE NA LISTA TEM ALGUM LIVRO
            stringStream.clear();       //SE NÃO HOUVER, LIMPA A LISTA DE LIVROS
            stringStream.add("Listagem Indisponível! Não foram encontradas obras" +
                    " cujo ano de publicação é " + year + " no sistema.");  //SE NÃO HOUVER, PÕEM DADO QUE INDICA QUE NÃO HÁ LIVROS CORRESPONDENTES DO ANO INFORMADO
        }
        result[0] = stringStream.saveFromFile(searchResults + year + ".txt"); //SALVA OS DADOS A LISTA EM ARQUIVO AO MESMO TEMPO QUE ARMAZENA O RESULTADO BOLEANO DA OPERAÇÃO
        result[1] = stringStream;                                             //ARMAZENA A LISTA
        return result;
    }

    /**
     * Método responsável por listar todos os livros de determinado ano.
     * @param node              Refere-se ao nó raiz da árvore AVL.
     * @param year              Refere-se ao ano de publicação dos livros a serem listados.
     * @param stringStream      Refere-se a lista onde os dados serão armazenados.
     */
    private void searchEbookByYear(final Node node, final int year, final StringStream stringStream) {
        if (node != null) {
            searchEbookByYear(node.getSonOnTheLeft(), year, stringStream);  //INICIA NOVA RECURSÃO A ESQUERDA
            if (((Ebook) node.getElement()).getYear() == year) {            //VERIFICA SE O LIVRO DO NÓ ATUAL FOI PUBLICADO NO ANO EM QUE SE BUSCA PUBLICAÇÕES
                stringStream.add("-----------------------------------------------------");
                stringStream.add("NÚMERO: \t" + ((Ebook) node.getElement()).getNumber());
                stringStream.add("TÍTULO: \t" + ((Ebook) node.getElement()).getTitle());
                stringStream.add("AUTOR: \t\t" + ((Ebook) node.getElement()).getAuthor());
                stringStream.add("MÊS: \t\t" + ((Ebook) node.getElement()).getMonth());
                stringStream.add("ANO: \t\t" + ((Ebook) node.getElement()).getYear());
                stringStream.add("LINK: \t\t" + ((Ebook) node.getElement()).getUrl());
            }
            searchEbookByYear(node.getSonOnTheRight(), year, stringStream);//INICIA NOVA RECURSÃO A DIREITA
        }
    }

    /**
     * Método responsável por remover livros da árvore por meio de seus números de identificação.
     * @param number    Refere-se ao número de identificação do livro.
     * @return          Retorna valor boleano afim de indicar se a operação foi bem sucedida ou não.
     */
    public boolean removeEbook(final int number) {
        if(ebookTreeStream.search(number) != null){     //BUSCA LIVRO AFIM DE VERIFICAR SE O MESMO EXISTE
            ebookTreeStream.remove(number);             //SE EXISTIR FAZ COM QUE SEJA REMOVIDO
            return true;
        } else {                                        //VERIFICA SE O LIVRO NÃO EXISTE AFIM DE INDICAR QUE A OPERAÇÃO FALHOU
            return false;
        }
    }

    /**
     * Método responsável por auxiliar os testes. Para tal, ele retorna a estrutura
     * derivada de uma árvore AVL.
     * @return Retorna estrutura derivada de uma árvore AVL.
     */
    public EbookTreeStream getEbookTreeStream() {
        return ebookTreeStream;
    }

    /**
     * Método responsável por auxiliar os testes. Para tal, ele retorna o diretório
     * onde encontram-se os arquivos de busca.
     * @return Retorna diretório onde se encontra o diretório de busca.
     */
    public String getSearchResults() {
        return searchResults;
    }
    
}

