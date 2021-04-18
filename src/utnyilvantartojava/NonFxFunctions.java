package utnyilvantartojava;

public class NonFxFunctions {

// a logika ahhoz, hogy a + - gomb 12 hónapnak megfelelően működjön.
    public static String workDateDecOrInc(Settings settings, String workDate, String value) {

        Integer year = Integer.parseInt(workDate.substring(0, 4));
        Integer month = Integer.parseInt(workDate.substring(5, 7));
        if (value == "+") {
            if (month < 12) {
                month = month + 1;
            } else {
                year = year + 1;
                month = 1;
            }
        }

        if (value == "-") {
            if (month > 1) {
                month--;
            } else {
                year--;
                month = 12;
            }
        }
        String mnt;
        if (month < 10) {
            mnt = "0" + month.toString();
        } else {
            mnt = month.toString();
        }
        //tempSettings=settings;
        settings.setAktualis_honap(year.toString() + "-" + mnt);
        settings.setId(settings.getRendszam() + year.toString() + "-" + mnt);

        return year.toString() + "-" + mnt;

    }


    public String checkFueling(String fuel) {
        System.out.println(fuel);
        if (fuel.contains(",")) {
            fuel = fuel.replace(",", ".");
        }
        return fuel;
    }
    // összerakja a várost és a címet egy stringbe
    public String getClientFullAddress(Client client) {
        System.out.println(client.getCity() + " " + client.getAddress());
        return client.getCity() + " " + client.getAddress();

    }

    //Kicseréli a nemkivánatos karaktereket szóközre

    public String cleanString(String str) {
        if (str.contains("/")) {
            str = str.replaceAll("/", " ");
        }
        if (str.contains("'")) {
            str = str.replaceAll("'", " ");
        }
        if (str.contains("\\")) {
            str = str.replaceAll("\\", " ");
        }
        if (str.contains("\"")) {
            str = str.replaceAll("\"", " ");
        }

        return str;
    }

    public String getClientNumberFromRoute(String clientNumber) {
        if (clientNumber.contains("/")) {
            int index = clientNumber.indexOf('/');
            return clientNumber.substring(0, index);
        } else {
            return clientNumber;
        }
    }
    public Boolean convertBool(String value) {
        if (value.contentEquals("ok")) {
            return true;
        } else {
            return false;
        }
    }
    public int convertBool(Boolean value) {
        int convertedValue;
        if (value) {
            return 1;
        } else {
            return 0;
        }
    }

    public Boolean convertBool(int value) {
        if (value == 1) {
            return true;
        } else {
            return false;
        }
    }


}