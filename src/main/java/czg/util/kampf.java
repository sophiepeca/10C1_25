import java.util.random.*;
import java.util.Scanner;


public class kampf {
  Scanner scanner = new Scanner(System.in);
  private String name;
  private int lehrer_leben, schueler_leben, multiplier, grundleben;
  List<Integer> items = new ArrayList<>();

  multiplier = 2;
  grundleben = 5;
  
  //Spieler und Lehrer hinzufügen

  // Implementierung der Züge
    // Angriff
    // Verteidigung
    // Berechnung des Schadens
    // Wiederholung

  private Lehrer addLehrer(string Name) {
    lehrer_object = new Lehrer(Name);
    int leben = lehrer_object.getLevel() * multiplier + grundleben;
    items = lehrer_object.getItems();
  }

  private Schueler addSchueler(string Name){
    schueler_object = new Schueler(Name);
    int leben = schueler_object.getLeben();

  } 
  // Frage? Wer ist dran?
  // -> Lehrer Zufall zieht Item??Random package???
  // -> Schüler kann Item aussuchen
  
  private void lehrer_zug() {
    Random rand = new Random();
    gewaehlt = rand.nextInt(4);

    int schaden  = attack_lehrer(gewaehlt)
    schueler_object.setLeben(schueler_object.getLeben() - schaden);
    system.outprintln("Du hast " + schaden + " bekommen.");
  }

  private int attack_lehrer(int welches_item) {
    int ID = 0
    
    // item eingegeben
    // TO DO:
    // Schaden berechnen und zurückgeben
    int benutztes_item = items.get(welches_item);

    // visuell was nettes

    int neuer_schaden = schueler_verteidigung(benutztes_item);
    return neuer_schaden;
  }

  private int schueler_verteidigung(int lehrer_item) {
    // Eingabe: Item vom Lehrer
    // Zwischendurch:   Schueler wählt item aus
    //                  schaden wird abgeändert

    int schueler_item = scanner.nextInt("Welches Item willst du nehmen?");
    kleiner_schaden = item.get(schueler_item).get(level);
    int grosserschaden = item.get(schueler_item).get(level);

    // Rückgabe: abgeänderter schaden 
    if kleiner_schaden >= grosser_schaden {
      return 0;
    }
    else {
      return grosser_schaden - kleiner_schaden;
    }
  }

  
}
