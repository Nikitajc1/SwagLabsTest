package reportHelpers;

public class TestResults {
    private final int total;
    private final int passed;
    private final int failed;
    private final int skipped;


    public TestResults(int total, int passed, int failed, int skipped) {
        this.total = total;
        this.passed = passed;
        this.failed = failed;
        this.skipped = skipped;
    }

    public int getTotal() {
        return total;
    }

    public int getPassed() {
        return passed;
    }

    public int getFailed() {
        return failed;
    }

    public int getSkipped() {
        return skipped;
    }
}
