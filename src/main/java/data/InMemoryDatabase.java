package data;


public class InMemoryDatabase implements Database {

    private String count;

    public InMemoryDatabase(String product) {
        count = product.split("\\s")[0];
    }

    public static InMemoryDatabase with(String product) {
        return new InMemoryDatabase(product);
    }

    @Override
    public String findProduct(String criteria) {
        if ("pacemaker".equalsIgnoreCase(criteria)) {
            return "A-1-1:" + count + " " +
                    "B-2-2:0" + " " +
                    "C-3-3:2" + " " +
                    "D-4-4:5" + " " +
                    "E-5-5:" + count
                    ;
        }
        return "A-1-1:0" + " " +
                "B-2-2:0" + " " +
                "C-3-3:0" + " " +
                "D-4-4:0" + " " +
                "E-5-5:0"
                ;
    }
}
