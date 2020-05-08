package model;

/**
 * Classe responsável por comportar-se como ebook.
 * @author Everton Bruno Silva dos Santos
 */
public class Ebook {
    private final int number;
    private final String title;
    private final String author;
    private final String month;
    private final int year;
    private final String url;

    /**
     * Construtor responsável por inicializar a classe, bem como, atribuir valores a 
     * propriedades contendo: número de ebook; título, autor, mês
     *  e ano de publicação da obra.
     * @param number Refere-se ao número do ebook.
     * @param title Refere-se ao título da obra.
     * @param author Refere-se ao nome do autor da obra.
     * @param month Refere-se ao mês de publicação da obra.
     * @param year Refere-se ao ano de publicação da obra.
     * @param url Refere-se ao endereço eletrônico da obra.
     */
    public Ebook(final int number, final String title, final String author, final String month, final int year,
            final String url) {
        this.number = number;
        this.title = title;
        this.author = author;
        this.month = month;
        this.year = year;
        this.url = url;
    }
    
    /**
     * Método responsável por retornar o número de ebook.
     * @return Retorna número do ebook.
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Método responsável por retornar o título da obra.
     * @return Retorna título da obra.
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Método responsável por retornar o nome do autor da obra.
     * @return Retorna nome do autor da obra.
     */
    public String getAuthor() {
        return author;
    }
    
    /**
     * Método responsável por retornar o mês de publicação da obra.
     * @return Retorna mês de publicação da obra.
     */
    public String getMonth() {
        return month;
    }
    
    /**
     * Método responsável por retornar o ano de publicação da obra.
     * @return Retorna ano de publicação da obra.
     */
    public int getYear() {
        return year;
    }

    /**
     * Método responsável por retornar o endereço eletrônico do ebook.
     * @return Retorna endereço eletrônico do ebook.
     */
    public String getUrl() {
        return url;
    }
    
}
