import java.util.ArrayList;

public class GraphMatrixSkeleton
{
    // Attribute der Klasse GraphMatrix deklarieren


    // Deklaration des Konstruktors der Klasse GraphMatrix
    

    //Deklaration der Methode knotenEinfuegen
    

    //Deklaration der Methode kanteEinfuegen
    

    //Deklaration der Methode knotenNummerGeben

    
    

    /**
     * Hilfsmethode
     * Gibt die Bezeichner der Knoten in der übergebenen Liste auf der Konsole aus
     * 
     * @param warteliste die Liste der Knoten, deren Bezeichner ausgegeben werden sollen
     */
    private void wartelisteAusgeben(ArrayList<KnotenSkeleton> warteliste)
    {
        for (KnotenSkeleton knoten : warteliste)
        {
            System.out.println("  " + knoten.bezeichnerGeben());
        }
    }

    /**
     * Hilfsmethode
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
     * Hilfsmethode
     * Methode, um eine bestimmte Anzahl von Leerzeichen zu generieren.
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