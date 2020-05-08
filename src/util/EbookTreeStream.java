package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import model.Ebook;

/**
 * Classe responsável por ler e gravar dados na memória de armazenamento, bem 
 * como comportar-se como uma árvore AVL.
 * @author Everton Bruno Silva dos Santos
 */
public class EbookTreeStream extends Tree<Ebook> {

    /**
     * Construtor responsável por inicializar a classe.
     */
    public EbookTreeStream() {
        super();
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
                super.insert(Integer.parseInt(line.split(";")[0]), toEbook(line));
            });
        } catch (final FileNotFoundException exception) {
            return false;
        } catch (final IOException exception) {
            return false;
        }
        return true;
    }

    /**
     * Método responsável por gerenciar a gravação de arquivos de texto na memória de armazenamento.
     * @param fileName Refere-se ao nome do arquivo de texto a ser gravado.
     * @return Retorna resultado da operação.
     */
    public boolean saveFromFile(final String fileName) {
        try (PrintWriter fileStream = new PrintWriter(new FileOutputStream(fileName, false))) {
            saveFromFile(fileStream, super.getRoot());
        } catch (final FileNotFoundException exception) {
            return false;
        }
        return true;
    }

    /**
     * Método responsável por efetuar a gravação de arquivos de texto na memória de armazenamento.
     * @param fileStream Refere-se ao objeto responsável por efetuar a gravação dos dados.
     * @param node Refere-se a árvore que fará a gravação dos dados.
     */
    private void saveFromFile(final PrintWriter fileStream, final Node node) {
        if (node != null) {
            saveFromFile(fileStream, node.getSonOnTheLeft());
            fileStream.println(toString((Ebook)node.getElement()));
            saveFromFile(fileStream, node.getSonOnTheRight());
        }
    }

    /**
     * Método responsável por efetuar a conversão de um ebook em string.
     * @param ebook Refere-se ao ebook que será convertido.
     * @return Retorna string com dados do livro.
     */
    private String toString(final Ebook ebook) {
        return ebook.getNumber() + ";" + ebook.getTitle() + ";" + ebook.getAuthor() + ";" + ebook.getMonth() + ";"
                + ebook.getYear() + ";" + ebook.getUrl();
    }

    /**
     * Método responsável por efetuar a conversão de uma string em ebook.
     * @param string Refere-se a string que será convertida.
     * @return Retorna ebook criado por meio da string.
     */
    private Ebook toEbook(final String string) {
        final String[] data = string.split(";");
        return new Ebook(Integer.parseInt(data[0]), data[1], data[2], data[3],
        Integer.parseInt(data[4]), data[5]);
    }
    
    
}
