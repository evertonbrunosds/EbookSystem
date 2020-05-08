package util;

/**
 * Classe responsável por comportar-se como nó de árvore AVL.
 * @author Everton Bruno Silva dos Santos.
 * @param <E> Refere-se ao tipo de elemento que o nó irá armazenar.
 */
public class Node<E> {
    private int key;
    private int balancing;
    private E element;
    private Node<E> sonOnTheLeft;
    private Node<E> sonOnTheRight;

    /**
     * Construtor responsável por inicializar o nó, bem como suas propriedades.
     * @param key       Refere-se ao chave do elemento.
     * @param element   Refere-se ao elemento.
     */
    public Node(final int key, final E element) {
        this.key = key;
        this.balancing = 0;
        this.element = element;
        this.sonOnTheLeft = null;
        this.sonOnTheRight = null;
    }

    /**
     * Método responsável por alterar a chave do nó.
     * @param key Refere-se a nova chave do nó.
     */
    public void setKey(final int key) {
        this.key = key;
    }

    /**
     * Método responsável por alterar o valor de balanceamento do nó.
     * @param balancing Refere-se ao novo valor de balanceamento do nó.
     */
    public void setBalancing(final int balancing) {
        this.balancing = balancing;
    }

    /**
     * Método responsável por alterar o elemento do nó.
     * @param element   Refere-se ao novo elemento do nó.
     */
    public void setElement(final E element) {
        this.element = element;
    }

    /**
     * Método responsável por alterar a referência do nó filho à esquerda.
     * @param sonOnTheLeft  Refere-se a referência do novo nó filho a esquera.
     */
    public void setSonOnTheLeft(final Node<E> sonOnTheLeft) {
        this.sonOnTheLeft = sonOnTheLeft;
    }

    /**
     * Método responsável por alterar a referência do nó filho à direita.
     * @param sonOnTheLeft  Refere-se a referência do novo nó filho a direita.
     */
    public void setSonOnTheRight(final Node<E> sonOnTheRight) {
        this.sonOnTheRight = sonOnTheRight;
    }

    /**
     * Método responsável por retornar a chave do nó.
     * @return  Retorna chave do nó.
     */
    public int getKey() {
        return this.key;
    }

    /**
     * Método responsável por retornar o valor de balanceamento do nó.
     * @return  Retorna valor de balanceamento do nó.
     */
    public int getBalancing() {
        return this.balancing;
    }

    /**
     * Método responsável por retornar o elemento do nó.
     * @return  Retorna elemento do nó.
     */
    public E getElement() {
        return this.element;
    }

    /**
     * Método responsável por retornar a referência do nó filho à esquerda.
     * @return  Retorna referência do novo nó filho a esquera.
     */
    public Node<E> getSonOnTheLeft() {
        return this.sonOnTheLeft;
    }

    /**
     * Método responsável por retornar a referência do nó filho à direita.
     * @return  Retorna referência do novo nó filho a direita.
     */
    public Node<E> getSonOnTheRight() {
        return this.sonOnTheRight;
    }

    /**
     * Método responsável por retornar dado boleano que indica se o nó é uma folha.
     * @return  Retorna dado boleano.
     */
    public boolean isLeaf() {
        return this.sonOnTheLeft == null && this.sonOnTheRight == null;
    }

    /**
     * Método responsável por retornar dado boleano que indica se o nó possue apenas filhos a esquerda.
     * @return  Retorna dado boleano.
     */
    public boolean hasSonJustLeft() {
        return this.sonOnTheLeft != null && this.sonOnTheRight == null;
    }

    /**
     * Método responsável por retornar dado boleano que indica se o nó possue apenas filhos a direita.
     * @return  Retorna dado boleano.
     */
    public boolean hasSonJustRight() {
        return this.sonOnTheLeft == null && this.sonOnTheRight != null;
    }
    
        
}