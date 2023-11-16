import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VisitanteForm());

        Animal macaco = new Macaco("Beto Richa", 3, "Babuino", "Preto", 4);
        Animal macaco2 = new Macaco("Luiz Inacio Lula da Silva", 3, "Macaco Prego", "Preto", 4);
        Mamifero mamifero = new Mamifero("afonso",140,"marssupial","liso",2);
        Mamifero mamiferoLoboSolo = new Mamifero("Laura",18,"Lobo Guara","liso",4);
        Mamifero m = new Macaco("Cezar",10,"Gorila","liso",2);
        Mamifero ornitorrinco = new Ornitorrinco("Zé",4,"Australiano","liso",4);

        ornitorrinco.comer("Banana");
        ornitorrinco.comer("camarao");

        Jaula jaula1 = new Jaula("Jaula 1", 500.00);

        jaula1.adicionarAnimal(macaco);
        jaula1.adicionarAnimal(macaco2);

        for(int i = 0; i < jaula1.size(); i++){
            System.out.println(jaula1.animais.get(i));
        }

    }
}
