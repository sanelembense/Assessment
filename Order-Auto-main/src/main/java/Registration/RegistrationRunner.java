package Registration;

import Registration.Helpers.BeforeSuite;
import Registration.Helpers.CustomRunner;
import Registration.Helpers.FeatureOverright;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.runner.RunWith;

import java.io.IOException;

@CucumberOptions(plugin = {"pretty","junit:target/JUnitReport.xml","html:target/HtmlReport.html","json:target/JsonReport.json"},
        features = {"src/main/resources/Features/Registration.feature" },
        glue = {"src/main/java/Registration" },
        monochrome = true, snippets = SnippetType.CAMELCASE)

@RunWith(CustomRunner.class)
public class RegistrationRunner {
    @BeforeSuite
    public static void test() throws InvalidFormatException, IOException {
        FeatureOverright.overrideFeatureFiles(System.getProperty("user.dir")+"/src/main/resources/Features/Registration.feature");
    }
}

