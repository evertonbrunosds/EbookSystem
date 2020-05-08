package view;

import java.util.Scanner;

/**
 * Classe responsável por manipular dados inseridos pelo teclado.
 * @author Everton Bruno Silva dos Santos
 */
public class Keyboard {
    
    /**
     * Construtor responsável por inicializar a classe.
     */
    public Keyboard(){
        
    }
    
    /**
     * Método responsável por capturar dados via teclado.
     * @param msg Refere-se a mensagem que será exibida ao usuário.
     * @return Retorna o dado digitado pelo usuário.
     */
    public String inputStr(final String msg) {
        final Scanner data = new Scanner(System.in);
        System.out.print(msg);
        return data.nextLine().replace(";", "");
    }

    /**
     * Método responsável por capturar dados via teclado.
     * @param msg Refere-se a mensagem que será exibida ao usuário.
     * @return Retorna o dado digitado pelo usuário.
     */
    public int inputInt(final String msg) {
        int number;
        while (true) {
            try {
                number = Integer.parseInt(inputStr(msg));
                return number;
            } catch (final NumberFormatException exception) {
                System.out.println("Falha! Apenas números são aceitos...");
            }
        }
    }

    /**
     * Método responsável por capturar dados via teclado.
     * @param msg Refere-se a mensagem que será exibida ao usuário.
     * @return Retorna valor boleano correspondente ao dado válido digitado pelo
     *         usuário.
     */
    public boolean inputBoolean(final String msg) {
        while(true){
            switch (inputStr(msg).toLowerCase()) {
                case "s":
                    return true;                    
                case "n":
                    return false;
                default:
                    System.out.println("Falha! Opção inválida, tente [S/N]...");
                    break;
            }
        }
    }
    
}