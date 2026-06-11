public class Knoten
{
    //Attribute der Klasse Knoten deklarieren


    //Deklaration des Konstruktors der Klasse Knoten
    

    //Deklaration der Methode bezeichnerGeben


    /**
     * Hilfsmethode
     * Gibt den Bezeichner des Knotens mit einer bestimmten Länge zurück.
     * 
     * @param laenge die gewünschte Länge des Bezeichners
     * @return der Bezeichner des Knotens mit der angegebenen Länge
     */
    public String bezeichnerFormatiertGeben(int laenge)
    {
        if (bezeichner == null)
        {
            return null;
        }
        else if (laenge < 0)
        {
            throw new IllegalArgumentException("laenge muss >= 0 sein");
        }
        else if (bezeichner.length() <= laenge)
        {
            return bezeichner;
        } 
        else
        {
            return bezeichner.substring(0, laenge);
        }
    }

}