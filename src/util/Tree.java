package util;

/**
 * Classe responsável por comportar-se como uma árvore AVL.
 * @author Everton Bruno Silva dos Santos
 * @param <E> Refere-se ao tipo de elemento que a árvore irá armazenar.
 */
public class Tree<E> {
    private Node<E> root;

    /**
     * Construtor responsável por inicializar a árvore, bem como seu nó raiz.
     */
    public Tree() {
        this.root = null;
    }
    
    /**
     * Método responsável por retornar a raiz da árvore.
     * @return Retorna a raiz da árvore.
     */
    public Node getRoot(){
        return root;
    }

    /**
     * Método responsável por retornar dado boleano que indica se a árvore está vazia.
     * @return Retorna dado boleano.
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * Método responsável por esvaziar a árvore.
     */
    public void clear() {
        this.root = null;
    }

    /**
     * Método responsável por inserir novos elementos na árvore.
     * @param key       Refere-se a chave do elemento.
     * @param element   Refere-se ao elemento.
     */
    public void insert(final int key, final E element) {
        if (!this.isContains(key)) {
            this.root = this.insert(key, element, root);
        }
    }

    /**
     * Método auxiliar recursivo responsável por inserir novos elementos na árvore.
     * @param key           Refere-se a chave do elemento.
     * @param element       Refere-se ao elemento.
     * @param currentNode   Refere-se ao nó raiz que irá deter o novo nó com o elemento.
     * @return              Retorna árvore reconstruída com novo elemento.
     */
    private Node<E> insert(final int key, final E element, final Node<E> currentNode) {
        if (currentNode == null) {
            return new Node<E>(key, element);
        } else if (currentNode.getKey() > key) {
            currentNode.setSonOnTheLeft(this.insert(key, element, currentNode.getSonOnTheLeft()));
        } else {
            currentNode.setSonOnTheRight(this.insert(key, element, currentNode.getSonOnTheRight()));
        }
        return this.adjustHeigth(currentNode);
    }

    /**
     * Método responsável por efetuar buscas na árvore.
     * @param key   Refere-se a chave do elemento.
     * @return      Retorna elemento.
     */
    public E search(final int key) {
        return this.search(key, root);
    }

    /**
     * Método auxiliar recursivo responsável por efetuar buscas na árvore.
     * @param key           Refere-se a chave do elemento.
     * @param currentNode   Refere-se ao nó que possívelmente contém o elemento buscado.
     * @return              Retorna elemento.
     */
    private E search(final int key, final Node<E> currentNode) {
        if (currentNode == null) {
            return null;
        } else if (currentNode.getKey() == key) {
            return currentNode.getElement();
        } else if (currentNode.getKey() > key) {
            return this.search(key, currentNode.getSonOnTheLeft());
        } else {
            return this.search(key, currentNode.getSonOnTheRight());
        }
    }

    /**
     * Método responsável por retornar dado boleano que indica se a árvore detém determinado elemento
     * @param key   Refere-se a chave do elemento.
     * @return      Retorna dado boleano.
     */
    public boolean isContains(final int key) {
        return this.search(key, root) != null;
    }

    /**
     * Método responsável por remover elementos da árvore.
     * @param key   Refere-se a achave do elemento.
     * @return      Retorna elemento removido.
     */
    public E remove(final int key) {
        final Container container = new Container();
        this.remove(key, container, root);
        return container.getElement();
    }

    /**
     * Método auxiliar recursivo responsável por remover elementos da árvore.
     * @param key           Refere-se a chave do elemento.
     * @param container     Refere-se a estrutura que irá armazenar o elemento removido.
     * @param currentNode   Refere-se ao nó que possívelmente contém o elemento que será removido.
     * @return              Retorna árvore reconstruída sem o elemento.
     */
    private Node<E> remove(final int key, final Container container, final Node<E> currentNode) {
        if (currentNode == null) {
            return null;
        } else if (currentNode.getKey() == key) {
            if (currentNode.isLeaf()) {
                container.setElement(currentNode.getElement());
                return null;
            } else if (currentNode.hasSonJustLeft()) {
                return currentNode.getSonOnTheLeft();
            } else if (currentNode.hasSonJustRight()) {
                return currentNode.getSonOnTheRight();
            } else {
                Node<E> tmpNode;
                for (tmpNode = currentNode.getSonOnTheLeft(); tmpNode.getSonOnTheRight() != null; tmpNode = tmpNode.getSonOnTheRight());
                final int tmpKey = tmpNode.getKey();
                final E tmpElement = tmpNode.getElement();
                tmpNode.setKey(currentNode.getKey());
                tmpNode.setElement(currentNode.getElement());
                currentNode.setKey(tmpKey);
                currentNode.setElement(tmpElement);
                currentNode.setSonOnTheLeft(this.remove(key, container, currentNode.getSonOnTheLeft()));
            }
        } else if (currentNode.getKey() > key) {
            currentNode.setSonOnTheLeft(this.remove(key, container, currentNode.getSonOnTheLeft()));
        } else {
            currentNode.setSonOnTheRight(this.remove(key, container, currentNode.getSonOnTheRight()));
        }
        return this.adjustHeigth(currentNode);
    }

    /**
     * Método responsável por retornar a altura de determinado nó da árvore.
     * @param currentNode   Refere-se ao nó o cuja altura será retornada.
     * @return              Retorna altura de determinado nó de árvore.
     */
    private int getHeight(final Node<E> currentNode) {
        if (currentNode == null) {
            return 0;
        } else {
            return Math.max(1 + this.getHeight(currentNode.getSonOnTheLeft()), 1 + this.getHeight(currentNode.getSonOnTheRight()));
        }
    }

    /**
     * Método responsável por atualizar o valor de balanceamento de determinado nó da árvore.
     * @param currentNode   Refere-se a determinado nó da árvore.
     */
    private void updateBalancing(final Node<E> currentNode) {
        currentNode.setBalancing(-this.getHeight(currentNode.getSonOnTheLeft()) + this.getHeight(currentNode.getSonOnTheRight()));
    }

    /**
     * Método responsável por retornar árvore reconstruída de forma balanceada.
     * @param currentNode   Refere-se ao nó que será balanceado.
     * @return              Retorna árvore reconstruída de forma balanceada.
     */
    private Node<E> adjustHeigth(final Node<E> currentNode) {
        this.updateBalancing(currentNode);
        if (currentNode.getBalancing() <= -2) {
            if (currentNode.getBalancing() * currentNode.getSonOnTheLeft().getBalancing() > 0) {
                return this.simpleRotationLeft(currentNode.getSonOnTheLeft(), currentNode);
            } else {
                return this.doubleRotationLeft(currentNode.getSonOnTheLeft(), currentNode);
            }
        } else if (currentNode.getBalancing() >= 2) {
            if (currentNode.getBalancing() * currentNode.getSonOnTheRight().getBalancing() > 0) {
                return this.simpleRotationRigth(currentNode.getSonOnTheRight(), currentNode);
            } else {
                return this.doubleRotationRight(currentNode.getSonOnTheRight(), currentNode);
            }
        }
        return currentNode;
    }

    /**
     * Método responsável por efetuar rotações simples a esquerda.
     * @param newRoot   Refere-se ao nó que se tornará raiz.
     * @param oldRoot   Refere-se ao nó que deixará de ser raiz.
     * @return          Retorna árvore rotacionada.
     */
    private Node<E> simpleRotationLeft(final Node<E> newRoot, final Node<E> oldRoot) {
        oldRoot.setSonOnTheLeft(newRoot.getSonOnTheRight());
        newRoot.setSonOnTheRight(oldRoot);
        return newRoot;
    }

    /**
     * Método responsável por efetuar rotações simples a direita.
     * @param newRoot   Refere-se ao nó que se tornará raiz.
     * @param oldRoot   Refere-se ao nó que deixará de ser raiz.
     * @return          Retorna árvore rotacionada.
     */
    private Node<E> simpleRotationRigth(final Node<E> newRoot, final Node<E> oldRoot) {
        oldRoot.setSonOnTheRight(newRoot.getSonOnTheLeft());
        newRoot.setSonOnTheLeft(oldRoot);
        return newRoot;
    }

    /**
     * Método responsável por efetuar rotações duplas a esquerda.
     * @param sonOnTheLeft  Refere-se ao nó filho a esquerda do nó que deirará de ser raiz.
     * @param oldRoot       Refere-se ao nó que deixará de ser raiz.
     * @return              Retorna árvore rotacionada.
     */
    private Node<E> doubleRotationLeft(final Node<E> sonOnTheLeft, final Node<E> oldRoot) {
        oldRoot.setSonOnTheLeft(sonOnTheLeft.getSonOnTheRight());
        sonOnTheLeft.setSonOnTheRight(oldRoot.getSonOnTheLeft().getSonOnTheLeft());
        oldRoot.getSonOnTheLeft().setSonOnTheLeft(sonOnTheLeft);
        return simpleRotationLeft(oldRoot.getSonOnTheLeft(), oldRoot);
    }

    /**
     * Método responsável por efetuar rotações duplas a direita.
     * @param sonOnTheRight Refere-se ao nó filho a direita do nó que deirará de ser raiz.
     * @param oldRoot       Refere-se ao nó que deixará de ser raiz.
     * @return              Retorna árvore rotacionada.
     */
    private Node<E> doubleRotationRight(final Node<E> sonOnTheRight, final Node<E> oldRoot) {
        oldRoot.setSonOnTheRight(sonOnTheRight.getSonOnTheLeft());
        sonOnTheRight.setSonOnTheLeft(oldRoot.getSonOnTheRight().getSonOnTheRight());
        oldRoot.getSonOnTheRight().setSonOnTheRight(sonOnTheRight);
        return simpleRotationRigth(oldRoot.getSonOnTheRight(), oldRoot);
    }
    
    /**
     * Método responsável por retornar a quantidade de nós contidos na árvore.
     * @return Retorna quantidade de nós.
     */
    public int size() {
        return size(root);
    }

    /**
     * Método auxiliar recursivo responsável por auxiliar o processo
     * @param node Refere-se a raiz da árvore.
     * @return Retorna quantidade de nós.
     */
    private int size(final Node node) {
        if(node == null){
            return 0;
        } else {
            return 1+size(node.getSonOnTheLeft())+size(node.getSonOnTheRight());
        }
    }
    
    /**
     * Método responsável por retornar a maior chave da árvore.
     * @return Retorna o maior valor de chave.
     */
    public int highestKey() {
        if (isEmpty()) {
            return -1;
        } else {
            return highestKey(root);
        }
    }

    /**
     * Método auxiliar recursivo responsável por auxiliar o processo de retornar a maior chave da
     * árvore recursivamente.
     * @param node Refere-se a raiz da árvore.
     * @return Retorna maior chave.
     */
    private int highestKey(final Node node) {
        if (node.getSonOnTheRight() == null) {
            return node.getKey();
        } else {
            return highestKey(node.getSonOnTheRight());
        }
    }

    /**
     * Classe responsável por comportar-se como recipiente afim de armazenar elementos.
     * @author Everton Bruno Silva dos Santos.
     */
    private class Container {
        private E element;

        /**
         * Construtor responsável por inicializar o recipiente.
         */
        public Container() {
            this.element = null;
        }

        /**
         * Método responsável por retornar o elemento contido no recipiente.
         * @return  Retorna elemento.
         */
        public E getElement() {
            return this.element;
        }

        /**
         * Método responsável por alterar o elemento contido no recipiente.
         * @param element   Refere-se ao novo elemento.
         */
        public void setElement(final E element) {
            this.element = element;
        }

    }

    
}