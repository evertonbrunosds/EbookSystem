package view;

import control.ConsoleInterface;

/**
 * Classe responsável por inicializar a aplicação.
 * @author Everton Bruno Silva dos Santos
 */
public class Main {

    /**
     * Construtor responsável por inicializar a classe.
     * @param args Refere-se ao parâmetro do método principal.
     */
    public static void main(final String[] args) {
        final ConsoleInterface consoleInterface = new ConsoleInterface();
        consoleInterface.start();        
    }
    
}
