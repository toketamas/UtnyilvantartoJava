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


}