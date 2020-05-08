package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Classe responsável por ler e gravar dados na memória de armazenamento, bem 
 * como, comportar-se como uma ArrayList.
 * @author Everton Bruno Silva dos Santos
 */
public class StringStream extends ArrayList<String> {

    /**
     * Construtor responsável por inicializar a classe.
     */
    public StringStream() {
    }
        
    /**
     * Método responsável por ler arquivos de texto contidos na memória de armazenamento.
     * @param fileName Refere-se ao nome do arquivo de texto a ser lido.
     * @return Retorna resultado da operação.
     */
    public boolean loadFromFile(final String fileName) {
        super.clear();
        try (BufferedReader fileStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            fileStream.lines().forEach((line) -> {
                super.add(line);
            });
        } catch (final FileNotFoundException exception) {
            return false;
        } catch (final IOException exception) {
            return false;
        }
        return true;
    }

    /**
     * Método responsável por gravar arquivos de texto na memória de armazenamento.
     * @param fileName Refere-se ao nome do arquivo de texto a ser gravado.
     * @return Retorna resultado da operação.
     */
    public boolean saveFromFile(final String fileName) {
        try (PrintWriter fileStream = new PrintWriter(new FileOutputStream(fileName, false))) {
            super.forEach((line) -> {
                fileStream.println(line);
            });
        } catch (final FileNotFoundException exception) {
            return false;
        }
        return true;
    }

    /**
     * Método responsável por realizar buscas na ArrayList.
     * @param key Refere-se a chave de busca.
     * @return Retorna resultado boleano correspondente a busca.
     */
    public boolean search(final String key) {
        for(int i = 0; i < super.size(); i++){
            if(super.get(i).equals(key)){
                return true;
            }
        }
        return false;
    }

    /**
     * Método responsável por exibir todos os elemetos da ArrayList.
     */
    public void displayLink() {
        super.forEach((line) -> {
            System.out.println(line);
        });
    }
}
