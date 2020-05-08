package control;

import util.StringStream;
import view.*;

/**
 * Classe responsável por proporcionar a interação entre o utilizador e a aplicação.
 * @author Everton Bruno Silva dos Santos
 */
public class ConsoleInterface {
    private final Screen screen = new Screen();
    private final Keyboard keyboard = new Keyboard();
    private final Application application = new Application();
    
    /**
     * Construtor responsável por inicializar a classe.
     */
    public ConsoleInterface(){
        
    }
    
    /**
     * Método responsável por inicializar a interface da aplicação.
     */
    public void start(){
        /*
        O objeto "result" tem o propósito de receber os resultados dos métodos contidos no objeto Application
            O índice 0 (zero) tem a finalidade de receber dados sobre o resultado das operações de salvar e carregar arquivos
            O índice 1 (um) tem a finalidade de receber listas contendo os resultados das listagens efetuadas
        */
        Object result[] = new Object[2];
        while(true){
            screen.showMenu();
            switch (keyboard.inputStr("Digite uma opção válida: ")){
                case "1":
                    application.registerBook(
                        keyboard.inputStr("Informe o título da obra: "),
                        keyboard.inputStr("Informe o autor da obra: "),
                        keyboard.inputStr("Informe o mês da obra: "),
                        keyboard.inputInt("Informe o ano da obra: "),
                        keyboard.inputStr("Informe o URL do ebook: "));
                        screen.showMessage("Sucesso! Livro inserido no sistema.");
                    break;
                case "2":
                    if (!application.treeIsEmpty()){
                        if(!keyboard.inputBoolean("Aviso! Os dados que estão no sistema serão sobrescritos, deseja prosseguir[S/N]? ")){
                            break;
                        }
                    }
                    result[0] = application.loadFromFile(keyboard.inputStr("Informe o nome do arquivo: "));
                    screen.showBooleanMessage("Sucesso! Arquivo carregado no sistema.",
                        "Falha! Não foi possível ler o arquivo, possíveis razões:"+
                        "\n1- O arquivo informado não existe!"+
                        "\n2- Você não tem permissão para ler no arquivo informado!\n", (boolean)result[0]);
                    break;
                case "3":
                    result[0] = application.saveFromFile(keyboard.inputStr("Informe o nome do arquivo: "));
                    screen.showBooleanMessage("Sucesso! Arquivo gravado pelo sistema.",
                        "Falha! Não foi possível gravar o arquivo, possíveis razões:"+
                        "\n1- O diretório informado não existe!"+
                        "\n2- Você não tem permissão para gravar no diretório informado!\n", (boolean)result[0]);
                    break;
                case "4":
                    screen.ShowList(application.catalogedWorksAndListAuthors());
                    break;
                case "5":
                    result = application.searchEbookByAuthor(keyboard.inputStr("Informe o nome do autor: "));
                    screen.ShowList((StringStream)result[1]);
                    if(!(boolean)result[0]){
                        screen.showMessage("\nFalha! Não foi possível gravar o resultado da busca em arquivo.\n");
                    }
                    break;
                case "6":
                    screen.ShowList(application.listEbook());
                    break;
                case "7":
                    result = application.searchEbookByNumber(keyboard.inputInt("Informe o número do livro: "));
                    screen.ShowList((StringStream)result[1]);
                    if(!(boolean)result[0]){
                        screen.showMessage("\nFalha! Não foi possível gravar o resultado da busca em arquivo.\n");
                    }
                    break;
                case "8":
                    result = application.searchEbookByYear(keyboard.inputInt("Informe o ano: "));
                    screen.ShowList((StringStream)result[1]);
                    if(!(boolean)result[0]){
                        screen.showMessage("\nFalha! Não foi possível gravar o resultado da busca em arquivo.\n");
                    }
                    break;
                case "9":
                    if(application.treeIsEmpty()){
                        screen.showMessage("Falha! Não há dados no sistema.");
                        break;
                    }
                    result[0] = application.removeEbook(keyboard.inputInt("Informe o número do livro: "));
                    screen.showBooleanMessage("Sucesso! Livro removido do sistema.",
                    "Falha! Este livro não está contido no sistema.", (boolean)result[0]);
                    break;
                case "10":
                    screen.clear();
                    System.out.println("Aplicação finalizada pelo usuário.");
                    System.exit(0);
                default:
                    break;                    
            }            
        }
    }
}
