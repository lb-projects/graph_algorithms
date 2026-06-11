import java.util.ArrayList;

public class GraphMatrix
{
    /** Die aktuelle Anzahl der Knoten im Graphen. */
    private int anzahlKnoten;
    /** Ein Array zur Speicherung der Knoten. */
    private Knoten[] knoten;
    /** Die Adjazenzmatrix des Graphen. */
    private int[][] matrix;

    /**
     * Erstellt einen neuen Graphen mit der angegebenen maximalen Anzahl an Knoten.
     * 
     * @param maximumKnoten die maximale Anzahl an Knoten, die der Graph aufnehmen kann
     */
    public GraphMatrix(int maximumKnoten)
    {
        anzahlKnoten = 0;
        knoten = new Knoten[maximumKnoten];
        matrix = new int[maximumKnoten][maximumKnoten];

        //belege alle Eintraege der Adjazenzmatrix mit dem Wert Integer.MIN_VALUE
        //dieser Wert steht im Kontext für "keine Kante vorhanden"
        for (int i = 0; i < maximumKnoten; i++)
        {
            for (int j = 0; j < maximumKnoten; j++)
            {
                matrix[i][j] = Integer.MIN_VALUE;
            }
        }
    }

    /**
     * Fügt einen neuen Knoten zum Graphen hinzu.
     * 
     * @param bezeichner der Bezeichner des neuen Knotens
     */
    public void knotenEinfuegen(String bezeichner)
    {
        int index = knotenNummerGeben(bezeichner);

        //falls noch Platz zum Verwalten neuer Knoten ist
        if (anzahlKnoten < knoten.length)
        {
            //und falls noch kein Knoten mit dem selben Bezeichner existiert
            if (index == -1)
            {
                //füge den Knoten zum Graphen hinzu
                Knoten neuerKnoten = new Knoten(bezeichner);
                knoten[anzahlKnoten] = neuerKnoten;
                anzahlKnoten = anzahlKnoten + 1;
            }
            else
            {
                System.out.println("Knoten kann nicht eingefügt werden: Knoten mit identischem Bezeichner bereits vorhanden.");
            }
        }
        else
        {
            System.out.println("Knoten kann nicht eingefügt werden: Maximale Anzahl an Knoten erreicht.");
        }
    }

    /**
     * Fügt eine neue Kante zum Graphen hinzu.
     * 
     * @param start  der Bezeichner des Startknotens
     * @param ziel   der Bezeichner des Zielsknotens
     * @param gewicht das Gewicht der Kante
     */
    public void kanteEinfuegen(String start, String ziel, int gewicht)
    {
        int knotennummerStart = knotenNummerGeben(start);
        int knotennummerZiel = knotenNummerGeben(ziel);

        //falls beide Knoten existieren, fuege die Kante zur Adjazenzmatrix hinzu
        if ((knotennummerStart != -1) && (knotennummerZiel != -1))
        {
            matrix[knotennummerStart][knotennummerZiel] = gewicht;
        }
    }

    /**
     * Gibt die Nummer eines Knotens anhand seines Bezeichners zurück.
     * @param bezeichner der Bezeichner des Knotens
     * @return die Nummer des Knotens, oder -1, falls der Knoten nicht gefunden wurde
     */
    private int knotenNummerGeben(String bezeichner)
    {
        int index = -1;
        //vergleiche den übergebenen Bezeichner mit den Bezeichnern aller Knoten im Graphen
        for (int i = 0; i < anzahlKnoten; i++)
        {
            String vergleichswert = knoten[i].bezeichnerGeben();
            //falls die Bezeichner übereinstimmen, merke die Nummer des Knotens
            if (vergleichswert.equals(bezeichner))
            {
                index = i;
            }
        }
        return index;
    }

    /**
     * Führt eine Breitensuche auf dem Graphen durch, beginnend bei dem Knoten mit
     * dem angegebenen Bezeichner. Die Methode gibt die besuchten Knoten sowie 
     * während der Suche die aktuelle Warteliste auf der Konsole aus.
     * 
     * @param bezeichnerStartknoten der Bezeichner des Startknotens für die Breitensuche
     */
    public void breitensuche(String bezeichnerStartknoten)
    {
        //Datenstrukturen für die Arbeit des Algorithmus erzeugen
        ArrayList<Knoten> warteliste = new ArrayList<Knoten>();
        boolean[] besucht = new boolean[anzahlKnoten];
        
        //Startknoten auf Existenz prüfen
        int startIndex = knotenNummerGeben(bezeichnerStartknoten);
        if (startIndex == -1)
        {
            System.out.println("Startknoten nicht gefunden: " + bezeichnerStartknoten);
            return;
        }

        //Startknoten zur Warteliste hinzufuegen und als besucht markieren
        Knoten startknoten = knoten[startIndex];
        warteliste.add(startknoten);
        besucht[startIndex] = true;

        //Solange die Warteliste nicht leer ist, den nächsten Knoten bearbeiten
        while (!warteliste.isEmpty())
        {
            //nächsten Knoten aus der Warteliste entfernen und als aktuellen Knoten setzen
            Knoten aktuellerKnoten = warteliste.remove(0);
            String aktuellerBezeichner = aktuellerKnoten.bezeichnerGeben();
            int aktuellerIndex = knotenNummerGeben(aktuellerBezeichner);

            //aktuellen Knoten ausgeben
            System.out.println("Aktueller Knoten: " + aktuellerBezeichner);
            System.out.println("Warteliste: ");
            wartelisteAusgeben(warteliste);

            //prüfe alle Nachbarknoten und füge diese zur Warteliste hinzu,
            //wenn sie noch nicht besucht worden sind
            for (int i = 0; i < anzahlKnoten; i++)
            {
                if ((matrix[aktuellerIndex][i] > Integer.MIN_VALUE) 
                    && (!besucht[i]))          
                {
                    warteliste.add(knoten[i]);
                    besucht[i] = true;
                    System.out.println("Knoten hinzugefügt zu Warteliste: " + knoten[i].bezeichnerGeben());
                }
            }
            System.out.println("-----------------------------");
        }
    }

    /**
     * Gibt die Bezeichner der Knoten in der übergebenen Liste auf der Konsole aus
     * 
     * @param warteliste die Liste der Knoten, deren Bezeichner ausgegeben werden sollen
     */
    private void wartelisteAusgeben(ArrayList<Knoten> warteliste)
    {
        for (Knoten knoten : warteliste)
        {
            System.out.println("  " + knoten.bezeichnerGeben());
        }
    }


    /**
     * Bestimmt den kürzesten Weg zwischen zwei Knoten im Graphen und gibt diesen
     * auf der Konsole aus. Die Methode verwendet den Dijkstra-Algorithmus.
     * @param startknoten Bezeichner des Startknotens
     * @param zielknoten Bezeichner des Zielknotens
     */
    public void kuerzesterWeg(String startknoten, String zielknoten)
    {
        //Datenstrukturen für die Arbeit des Algorithmus erzeugen
        boolean[] besucht = new boolean[anzahlKnoten];
        int[] distanzen = new int[anzahlKnoten];
        int[] vorgaenger = new int[anzahlKnoten];

        //überprüfe, ob Start- und Zielknoten existieren
        int startIndex = knotenNummerGeben(startknoten);
        int zielIndex = knotenNummerGeben(zielknoten);
        if (startIndex == -1 || zielIndex == -1)
        {
            System.out.println("Start- oder Zielknoten nicht gefunden.");
            return;
        }
        //initialisiere die Distanz zum Startknoten für alle Knoten mit Integer.MAX_VALUE für unendlich
        //und die Vorgänger aller Knoten mit -1
        //und alle Knoten als unbesucht
        for (int i = 0; i < anzahlKnoten; i++)
        {
            distanzen[i] = Integer.MAX_VALUE;
            vorgaenger[i] = -1;
            besucht[i] = false;
        }

        //die Distanz vom Startknoten zu sich selbst ist 0
        distanzen[startIndex] = 0;

        //wiederhole, bis alle Knoten abgearbeitet sind
        for (int i = 0; i < anzahlKnoten; i++)
        {
            //bestimme den Knoten mit der kürzesten Distanz zum Startknoten, der noch nicht besucht wurde
            //und markiere diesen Knoten als besucht
            int aktuellerIndex = bestimmeKnotenMitKuerzesterDistanz(besucht, distanzen);
            besucht[aktuellerIndex] = true;

            //untersuche alle Nachbarknoten des aktuellen Knotens
            for(int j = 0; j < anzahlKnoten; j++)
            {
                //falls eine Kante zum Knoten mit Index j existiert und der Knoten noch nicht besucht wurde
                if ((matrix[aktuellerIndex][j] > Integer.MIN_VALUE) && (!besucht[j]))
                {
                    //berechne die Distanz zum Startknoten über den aktuellen Knoten
                    //und aktualisiere bei Bedarf die Distanz und den Vorgänger des Knotens mit Index j
                    int neueDistanz = distanzen[aktuellerIndex] + matrix[aktuellerIndex][j];
                    if (neueDistanz < distanzen[j])
                    {
                        distanzen[j] = neueDistanz;
                        vorgaenger[j] = aktuellerIndex;
                    }
                }
            }   
        }

        //gib den kürzesten Weg vom Startknoten zum Zielknoten aus:
        printKuerzesterWeg(startknoten, zielknoten, vorgaenger, distanzen);
    }
    
    /**
     * Gibt den kürzesten Weg vom Startknoten zum Zielknoten auf der Konsole aus.
     * @param startknoten Bezeichner des Startknotens
     * @param zielknoten Bezeichner des Zielknotens
     * @param vorgaenger Array der Vorgänger für jeden Knoten
     * @param distanzen Array der Distanzen zum Startknoten für jeden Knoten
     */
    private void printKuerzesterWeg(String startknoten, String zielknoten, int[] vorgaenger, int[] distanzen)
    {
        int zielIndex = knotenNummerGeben(zielknoten);
        if (distanzen[zielIndex] == Integer.MAX_VALUE)
        {
            System.out.println("Kein Weg vom Startknoten zum Zielknoten gefunden.");
            return;
        }
        
        System.out.println("Die kürzeste Distanz von " + startknoten + " zu " + zielknoten + " beträgt: " +
            distanzen[knotenNummerGeben(zielknoten)]);
        System.out.println("Der kürzeste Pfad lautet: ");

        StringBuilder pfad = new StringBuilder();
        String pfeil = " -> ";
        int naechsterIndex = zielIndex;
        while (vorgaenger[naechsterIndex] != -1)
        {
            pfad.insert(0, knoten[naechsterIndex].bezeichnerGeben());
            if(vorgaenger[naechsterIndex] != -1)
            {
                pfad.insert(0, pfeil);
                naechsterIndex = vorgaenger[naechsterIndex];
            }
        }

        pfad.insert(0, startknoten);
        System.out.println(pfad.toString());
    }

    /**
     * Bestimmt den Index des Knotens mit der kürzesten Distanz zum Startknoten, der noch nicht 
     * besucht wurde.
     * @param besucht das Array, das angibt, welche Knoten bereits besucht wurden
     * @param distanz das Array, das angibt, welche Distanz zum Startknoten jeder Knoten hat
     * @return den Index des Knotens mit der kürzesten Distanz zum Startknoten, der noch nicht besucht wurde
     */
    private int bestimmeKnotenMitKuerzesterDistanz(boolean[] besucht, int[] distanz)
    {
        //Index des bisher besten Knoten
        int minIndex = 0;
        //Distanz zum Startknoten für den bisher besten Knoten
        int minDistanz = Integer.MAX_VALUE;

        for(int i = 0; i < anzahlKnoten; i++)
        {
            //suche einen nicht-besuchten Knoten mit minimaler Distanz
            //zum Startknoten
            if(!besucht[i] && minDistanz > distanz[i])
            {
                minDistanz = distanz[i];
                minIndex = i;
            }
        }
        //Gib den Index des Knoten mit der kürzesten Distanz 
        //zum Startknoten zurück
        return minIndex;
    }

    /**
     * Gibt den Graphen in einer tabellarischen Darstellung aus.
     */
    public void printGraph()
    {
        // Formatierungskonstanten für die tabellarische Darstellung
        final int laenge = 3;           // Breite jeder Spalte in Zeichen
        final String abstand = "   ";   // Abstand zwischen den Spalten

        // Prüfung: Beende die Methode, wenn der Graph keine Knoten enthält
        if (anzahlKnoten == 0)
        {
            System.out.println("Graph ist leer.");
            return;
        }

        // Konstruiere die Kopfzeile der Tabelle mit den Knotennamen als Spaltentitel
        StringBuilder header = new StringBuilder();
        header.append(spaces(laenge));  // Platzhalter für die erste Spalte (Zeilenlabel)

        // Füge jeden Knotennamen als Spaltenüberschrift ein
        for (int spalte = 0; spalte < anzahlKnoten; spalte++)
        {
            String bezeichner = knoten[spalte].bezeichnerFormatiertGeben(laenge);
            header.append(abstand).append(bezeichner).append(spaces(laenge - bezeichner.length()));
        }

        // Gebe die Kopfzeile aus
        System.out.println(header.toString());

        // Durchlaufe alle Zeilen der Adjazenzmatrix
        for (int zeile = 0; zeile < anzahlKnoten; zeile++)
        {
            StringBuilder row = new StringBuilder();
            
            // Füge den Knotennamen als Zeilenlabel am Anfang hinzu
            String zeilenBezeichner = knoten[zeile].bezeichnerFormatiertGeben(laenge);
            row.append(zeilenBezeichner).append(spaces(laenge - zeilenBezeichner.length()));

            // Durchlaufe alle Spalten dieser Zeile (Kantengewichte zu allen anderen Knoten)
            for (int spalte = 0; spalte < anzahlKnoten; spalte++)
            {
                row.append(abstand);
                int gewicht = matrix[zeile][spalte];
                
                // Prüfe, ob eine Kante zwischen den beiden Knoten existiert
                if (gewicht != Integer.MIN_VALUE)
                {
                    // Kante existiert: Gebe das Gewicht aus, rechtsbündig formatiert
                    String gewichtString = String.valueOf(gewicht);
                    if (gewichtString.length() >= laenge)
                    {
                        // Gewicht passt direkt oder ist länger als die Feldbreite
                        row.append(gewichtString);
                    }
                    else
                    {
                        // Gewicht braucht Padding mit Leerzeichen für rechtsbündige Ausrichtung
                        row.append(spaces(laenge - gewichtString.length())).append(gewichtString);
                    }
                }
                else
                {
                    // Keine Kante vorhanden: Gebe ein leeres Feld aus
                    row.append(spaces(laenge));
                }
            }

            // Gebe die komplette Zeile aus
            System.out.println(row.toString());
        }
    }

    /**
     * Hilfsmethode, um eine bestimmte Anzahl von Leerzeichen zu generieren.
     * @param count die Anzahl der Leerzeichen, die generiert werden sollen
     * @return eine Zeichenkette, die aus der angegebenen Anzahl von Leerzeichen besteht
     */
    private String spaces(int count)
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++)
        {
            builder.append(' ');
        }
        return builder.toString();
    }
}