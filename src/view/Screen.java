package view;

import util.StringStream;

/**
 * Classe responsável controlar os elementos visuais que surgem na tela.
 * @author Everton Bruno Silva dos Santos
 */
public class Screen {
    
    /**
     * Construtor responsável por inicializar a classe.
     */
    public Screen(){
        
    }
    
    /**
     * Método responsável limpar a tela.
     */
    public void clear(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
    
    /**
     * Método responsável por exibir o menú principal na tela.
     */
    public void showMenu(){
        clear();
        System.out.println("|-----------------------------------------------------|");
        System.out.println("|                   Menu Principal                    |");
        System.out.println("|-----------------------------------------------------|");
        System.out.println("|   1)  Cadastrar novo livro                          |");
        System.out.println("|   2)  Carregar base de dados                        |");
        System.out.println("|   3)  Gravar arquivo                                |");
        System.out.println("|   4)  Listar autores e quantidade de obras          |");
        System.out.println("|   5)  Listar livros dado determinado nome de autor  |");
        System.out.println("|   6)  Listar todos os livros                        |");
        System.out.println("|   7)  Buscar livro dado determinado número de ebook |");
        System.out.println("|   8)  Buscar livros dado determinado ano            |");
        System.out.println("|   9)  Excluir livro dado determinado número de ebook|");
        System.out.println("|   10) Sair                                          |");
        System.out.println("|-----------------------------------------------------|");
        System.out.println("");        
    }
    
    /**
     * Método responsável por exibir mensagens na tela.
     * @param msg Refere-se a mensagem a ser exibida.
     */
    public void showMessage(final String msg) {
        final Keyboard keyboard = new Keyboard();
        keyboard.inputStr(msg + "\nPressione [ENTER] para continuar...");
    }

    /**
     * Método responsável por exibir mensagens na tela com o auxílio de uma
     * estrutura de desvio coordenada através de um valor boleano.
     * @param msgTrue  Refere-se a mensagem que será exibida caso o valor seja
     *                 verdadeiro.
     * @param msgFalse Refere-se a mensagem que será exibida caso o valor seja
     *                 falso.
     * @param result   Refere-se ao valor boleano.
     */
    public void showBooleanMessage(final String msgTrue, final String msgFalse, final boolean result) {
        if(result){
            showMessage(msgTrue);            
        } else {
            showMenu();
            showMessage(msgFalse);
        }
    }
    
    /**
     * Método responsável por exibir todos os elemetos da ArrayList.
     */
    public void ShowList(StringStream stringStream) {
        clear();
        stringStream.forEach((line) -> {
            System.out.println(line);
        });
        showMessage("");
    }
    
}