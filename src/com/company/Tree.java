package com.company;

import java.util.Stack;

public class Tree {
    private TreeNode rootTreeNode; // корневой узел

    public Tree() { // Пустое дерево
        rootTreeNode = null;
    }

    public TreeNode getRootTreeNode() {
        return rootTreeNode;
    }

    public TreeNode findTreeNodeByValue(int value) { // поиск узла по значению
        TreeNode currentTreeNode = rootTreeNode; // начинаем поиск с корневого узла
        while (currentTreeNode.getVal() != value) { // поиск покуда не будет найден элемент или не будут перебраны все
            if (value < currentTreeNode.getVal()) { // движение влево?
                currentTreeNode = currentTreeNode.getLeft();
            } else { //движение вправо
                currentTreeNode = currentTreeNode.getRight();
            }
            if (currentTreeNode == null) { // если потомка нет,
                return null; // возвращаем null
            }
        }
        return currentTreeNode; // возвращаем найденный элемент
    }

    public void insertTreeNode(int value) { // метод вставки нового элемента
        TreeNode newTreeNode = new TreeNode(value); // создание нового узла
        if (rootTreeNode == null) { // если корневой узел не существует
            rootTreeNode = newTreeNode;// то новый элемент и есть корневой узел
        }
        else { // корневой узел занят
            TreeNode currentTreeNode = rootTreeNode; // начинаем с корневого узла
            TreeNode parentTreeNode;
            while (true) // мы имеем внутренний выход из цикла
            {
                parentTreeNode = currentTreeNode;
                if(value == currentTreeNode.getVal()) {   // если такой элемент в дереве уже есть, не сохраняем его
                    return;    // просто выходим из метода
                }
                else  if (value < currentTreeNode.getVal()) {   // движение влево?
                    currentTreeNode = currentTreeNode.getLeft();
                    if (currentTreeNode == null){ // если был достигнут конец цепочки,
                        parentTreeNode.setLeft(newTreeNode); //  то вставить слева и выйти из методы
                        return;
                    }
                }
                else { // Или направо?
                    currentTreeNode = currentTreeNode.getRight();
                    if (currentTreeNode == null) { // если был достигнут конец цепочки,
                        parentTreeNode.setRight(newTreeNode);  //то вставить справа
                        return; // и выйти
                    }
                }
            }
        }

    }

    public boolean deleteTreeNode(int value) // Удаление узла с заданным ключом
    {
        TreeNode currentTreeNode = rootTreeNode;
        TreeNode parentTreeNode = rootTreeNode;
        boolean isLeftChild = true;
        while (currentTreeNode.getVal() != value) { // начинаем поиск узла
            parentTreeNode = currentTreeNode;
            if (value < currentTreeNode.getVal()) { // Определяем, нужно ли движение влево?
                isLeftChild = true;
                currentTreeNode = currentTreeNode.getLeft();
            }
            else { // или движение вправо?
                isLeftChild = false;
                currentTreeNode = currentTreeNode.getRight();
            }
            if (currentTreeNode == null)
                return false; // yзел не найден
        }

        if (currentTreeNode.getLeft() == null && currentTreeNode.getRight() == null) { // узел просто удаляется, если не имеет потомков
            if (currentTreeNode == rootTreeNode) // если узел - корень, то дерево очищается
                rootTreeNode = null;
            else if (isLeftChild)
                parentTreeNode.setLeft(null); // если нет - узел отсоединяется, от родителя
            else
                parentTreeNode.setRight(null);
        }
        else if (currentTreeNode.getRight() == null) { // узел заменяется левым поддеревом, если правого потомка нет
            if (currentTreeNode == rootTreeNode)
                rootTreeNode = currentTreeNode.getLeft();
            else if (isLeftChild)
                parentTreeNode.setLeft(currentTreeNode.getLeft());
            else
                parentTreeNode.setRight(currentTreeNode.getLeft());
        }
        else if (currentTreeNode.getLeft() == null) { // узел заменяется правым поддеревом, если левого потомка нет
            if (currentTreeNode == rootTreeNode)
                rootTreeNode = currentTreeNode.getRight();
            else if (isLeftChild)
                parentTreeNode.setLeft(currentTreeNode.getRight());
            else
                parentTreeNode.setRight(currentTreeNode.getRight());
        }
        else { // если есть два потомка, узел заменяется преемником
            TreeNode heir = receiveHeir(currentTreeNode);// поиск преемника для удаляемого узла
            if (currentTreeNode == rootTreeNode)
                rootTreeNode = heir;
            else if (isLeftChild)
                parentTreeNode.setLeft(heir);
            else
                parentTreeNode.setRight(heir);
        }
        return true; // элемент успешно удалён
    }

    // метод возвращает узел со следующим значением после передаваемого аргументом.
    // для этого он сначала переходим к правому потомку, а затем
    // отслеживаем цепочку левых потомков этого узла.
    private TreeNode receiveHeir(TreeNode TreeNode) {
        TreeNode parentTreeNode = TreeNode;
        TreeNode heirTreeNode = TreeNode;
        TreeNode currentTreeNode = TreeNode.getRight(); // Переход к правому потомку
        while (currentTreeNode != null) // Пока остаются левые потомки
        {
            parentTreeNode = heirTreeNode;// потомка задаём как текущий узел
            heirTreeNode = currentTreeNode;
            currentTreeNode = currentTreeNode.getLeft(); // переход к левому потомку
        }
        // Если преемник не является
        if (heirTreeNode != TreeNode.getRight()) // правым потомком,
        { // создать связи между узлами
            parentTreeNode.setLeft(heirTreeNode.getRight());
            heirTreeNode.setRight(TreeNode.getRight());
        }
        return heirTreeNode;// возвращаем приемника
    }

    public void printTree() { // метод для вывода дерева в консоль
        Stack globalStack = new Stack(); // общий стек для значений дерева
        globalStack.push(rootTreeNode);
        int gaps = 48; // начальное значение расстояния между элементами
        boolean isRowEmpty = false;
        while (isRowEmpty == false) {
            Stack localStack = new Stack(); // локальный стек для задания потомков элемента
            isRowEmpty = true;

            for (int j = 0; j < gaps; j++)
                System.out.print(' ');
            while (globalStack.isEmpty() == false) { // покуда в общем стеке есть элементы
                TreeNode temp = (TreeNode) globalStack.pop(); // берем следующий, при этом удаляя его из стека
                if (temp != null) {
                    System.out.print('|'); // выводим его значение в консоли
                    System.out.print(temp.getVal()); // выводим его значение в консоли
                    System.out.print('|'); // выводим его значение в консоли
                    localStack.push(temp.getLeft()); // соохраняем в локальный стек, наследники текущего элемента
                    localStack.push(temp.getRight());
                    if (temp.getLeft() != null ||
                            temp.getRight() != null)
                        isRowEmpty = false;
                }
                else {
                    System.out.print("__");// - если элемент пустой
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < gaps * 2 - 2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            gaps /= 2;// при переходе на следующий уровень расстояние между элементами каждый раз уменьшается
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop()); // перемещаем все элементы из локального стека в глобальный
        }
    }

    public void printTreeLight() { // метод для вывода дерева в консоль
        Stack globalStack = new Stack(); // общий стек для значений дерева
        globalStack.push(rootTreeNode);
        boolean isRowEmpty = false;
        while (isRowEmpty == false) {
            Stack localStack = new Stack(); // локальный стек для задания потомков элемента
            isRowEmpty = true;
            while (globalStack.isEmpty() == false) { // покуда в общем стеке есть элементы
                TreeNode temp = (TreeNode) globalStack.pop(); // берем следующий, при этом удаляя его из стека
                if (temp != null) {
                    System.out.print('|'); // выводим его значение в консоли
                    System.out.print(temp.getVal()); // выводим его значение в консоли
                    System.out.print('|'); // выводим его значение в консоли
                    localStack.push(temp.getLeft()); // соохраняем в локальный стек, наследники текущего элемента
                    localStack.push(temp.getRight());
                    if (temp.getLeft() != null ||
                            temp.getRight() != null)
                        isRowEmpty = false;
                } else {
                    localStack.push(null);
                    localStack.push(null);
                }

            }
            System.out.println();
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop()); // перемещаем все элементы из локального стека в глобальный
        }
    }
}
