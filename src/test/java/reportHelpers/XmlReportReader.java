package reportHelpers;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XmlReportReader {

    static public TestResults getTotalTestsResults() throws Exception {
        File directory = new File("./target/surefire-reports");

        File[] files = directory.listFiles((dir, name) -> name.endsWith(".xml"));
        assert files != null;
        if (files.length == 0) {
            throw new IOException("No XML reports found");
        }

        int total = 0;
        int passed = 0;
        int failed = 0;
        int skipped = 0;

        for (File file : files) {
            TestResults result = getTestResults(file);

            total += result.getTotal();
            passed += result.getPassed();
            failed += result.getFailed();
            skipped += result.getSkipped();
        }

        return new TestResults(total, passed, failed, skipped);
    }

    public static TestResults getTestResults(File file) throws ParserConfigurationException, IOException, SAXException {
        int failed;
        int passed;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        Element root = document.getDocumentElement();

        int errors = Integer.parseInt(root.getAttribute("errors"));
        int failures = Integer.parseInt(root.getAttribute("failures"));
        int total = Integer.parseInt(root.getAttribute("tests"));
        int skipped = Integer.parseInt(root.getAttribute("skipped"));

        failed = failures + errors;
        passed = total - failed - skipped;

        return new TestResults(total, passed, failed, skipped);
    }
}
