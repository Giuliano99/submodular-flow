package SubmodularFlow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ScanSubmodularFunction {

    protected HashMap<Integer, Double> readFile(String datName) {

        HashMap<Integer, Double> submodFunktion = new HashMap();

        File file = new File(datName);

        if (!file.canRead() || !file.isFile())
            System.exit(0);

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(datName));
            String zeile = null;
            while ((zeile = in.readLine()) != null) {
                if (zeile.length() > 0) {
                    submodFunktion.put(convertLength(zeile), convertResult(zeile));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
        return submodFunktion;
    }

    private static double convertResult(String string) {
        double out = 0;

        if (string.length() == 15) {
            int buchstabe = string.charAt(14);
            out = buchstabe - 48;
        } else {
            int buchstabe = string.charAt(15);
            out = (buchstabe - 48) * -1;
        }
        return out;
    }

    private static int convertLength(String string) {
        int count;
        int vordereZahl = 0;
        int multiplikator;
        for (int i = 0; i < string.length(); i++) {
            char buchstabe = string.charAt(i);

            if ((int) buchstabe == 58) break;
            if ((int) buchstabe == 49) multiplikator = 1;
            else multiplikator = 0;
            switch (i) {
                case 0:
                    vordereZahl = vordereZahl + 32 * multiplikator;
                    break;

                case 2:
                    vordereZahl = vordereZahl + 16 * multiplikator;
                    break;
                case 4:
                    vordereZahl = vordereZahl + 8 * multiplikator;
                    break;
                case 6:
                    vordereZahl = vordereZahl + 4 * multiplikator;
                    break;
                case 8:
                    vordereZahl = vordereZahl + 2 * multiplikator;
                    break;
                case 10:
                    vordereZahl = vordereZahl + 1 * multiplikator;
                    break;
            }
        }
        return vordereZahl;
    }
}
