import org.fluentlenium.adapter.FluentTest;
import org.junit.*;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("I want to view");
  }

  @Test
  public void bandAddTest() {
    goTo("http://localhost:4567/bands");
    fill("#name").with("Agalloch");
    submit("#addband");
    assertThat(pageSource()).contains("Agalloch");
  }

  @Test
  public void venueAddTest() {
    goTo("http://localhost:4567/venues");
    fill("#name").with("Hawthorne Theater");
    submit("#addvenue");
    assertThat(pageSource()).contains("Hawthorne Theater");
  }

  @Test
  public void bandUpdateTest() {
    Band testBand = new Band("Agalloch");
    testBand.save();
    String bandpage = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(bandpage);
    fill("#name").with("Primordial");
    submit("#updateband");
    assertThat(pageSource()).contains("Primordial");
  }

  @Test
  public void venueUpdateTest() {
    Venue testVenue = new Venue("Hawthorne Theater");
    testVenue.save();
    String venuepage = String.format("http://localhost:4567/venues/%d", testVenue.getId());
    goTo(venuepage);
    fill("#name").with("The Casbah");
    submit("#updatevenue");
    assertThat(pageSource()).contains("The Casbah");
  }

  @Test
  public void bandDeleteTest() {
    Band testBand = new Band("Agalloch");
    testBand.save();
    String bandpage = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(bandpage);
    submit("#deleteband");
    assertThat(pageSource()).doesNotContain("Agalloch");
  }

  @Test
  public void venueDeleteTest() {
    Venue testVenue = new Venue("Hawthorne Theater");
    testVenue.save();
    String venuepage = String.format("http://localhost:4567/venues/%d", testVenue.getId());
    goTo(venuepage);
    submit("#deletevenue");
    assertThat(pageSource()).doesNotContain("Hawthorne Theater");
  }

  @Test
  public void venueAssignTest() {
    Band testBand = new Band("Agalloch");
    testBand.save();
    Venue testVenue = new Venue("Hawthorne Theater");
    testVenue.save();
    String bandpage = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(bandpage);
    String venuebutton = String.format("#addvenue%d", testVenue.getId());
    submit(venuebutton);
    String venuelink = String.format("/venues/%d", testVenue.getId());
    assertThat(pageSource()).contains(venuelink);
  }
}
