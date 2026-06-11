public class GraphMatrixTester {

    private static GraphMatrix graph;
    private static String[] bezeichner = {"abcde", "bcdef", "cdefg", "defgh", "efghi"};

    public static void main(String[] args) {
        graph = new GraphMatrix(5);

        erstelleGraph();
        testPrinting();
        breitensuche("abcde");
        kuerzesterWeg("abcde", "efghi");
    }

    private static void testPrinting()
    {
        graph.printGraph();
    }

    private static void breitensuche(String bezeichnerStartknoten)
    {
        System.out.println("Breitensuche ab Knoten: " + bezeichnerStartknoten);
        graph.breitensuche(bezeichnerStartknoten);
    }

    private static void kuerzesterWeg(String start, String ziel)
    {
        graph.kuerzesterWeg(start, ziel);
    }

    private static void erstelleGraph()
    {
        for (String label : bezeichner) {
            graph.knotenEinfuegen(label);
        }

        for (int i = 0; i < bezeichner.length; i++) {
            String start = bezeichner[i];
            String ziel = bezeichner[(i + 1) % bezeichner.length];
            graph.kanteEinfuegen(start, ziel, i+1);
        }
        graph.kanteEinfuegen("abcde", "cdefg", 5);
        graph.kanteEinfuegen("defgh", "cdefg", 5);
        graph.kanteEinfuegen("efghi", "bcdef", 5);
    }
}
