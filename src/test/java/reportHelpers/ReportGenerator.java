package reportHelpers;

import static reportHelpers.XmlReportReader.getTotalTestsResults;

public class ReportGenerator {
    public static void main(String[] args) throws Exception {

        TestResults res2 = getTotalTestsResults();
        System.out.println("Total: " + res2.getTotal());
        System.out.println("Passed: " + res2.getPassed());
        System.out.println("Failed: " + res2.getFailed());
        System.out.println("Skipped: " + res2.getSkipped());

        String s = ReadmeUpdater.updateReadme(ReadmeUpdater.readReadme(), res2);
        System.out.println(s);

        ReadmeUpdater.writeReadme(s);
    }
}
