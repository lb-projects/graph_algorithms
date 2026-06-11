public class Knoten
{
    /** Der Bezeichner des Knotens. */
    private String bezeichner;

    /**
     * Erstellt einen neuen Knoten mit dem angegebenen Bezeichner.
     * 
     * @param bezeichner der Bezeichner des neuen Knotens
     */
    public Knoten(String bezeichner)
    {
        this.bezeichner = bezeichner;
    }

    /**
     * Gibt den Bezeichner des Knotens zurück.
     * 
     * @return der Bezeichner des Knotens
     */
    public String bezeichnerGeben()
    {
        return bezeichner;
    }

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