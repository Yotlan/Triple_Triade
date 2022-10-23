package fr.playcards;

import fr.playcards.cardgame.*;
import fr.playcards.room.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class StartServer {
    public static void main(String[] argv) {
        try {
            LocateRegistry.createRegistry(1099);
            while(true) {
                Naming.rebind(
                        "play-cards/1099/createroom",
                        new SRoom(new FF8TripleTriade()));
            }
        }
        catch(Exception e) { System.out.println("erreur" + e);}
    }
}
