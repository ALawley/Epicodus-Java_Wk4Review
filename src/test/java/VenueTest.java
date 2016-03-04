import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Venue.all().size());
  }

  @Test
  public void save_addsInstanceOfVenueToList() {
    Venue testVenue = new Venue("Hawthorne Theater");
    Venue testVenue1 = new Venue("The Casbah");
    testVenue.save();
    testVenue1.save();
    assertEquals(2, Venue.all().size());
  }

  @Test
  public void find_findsInstanceOfVenueById() {
    Venue testVenue = new Venue("Hawthorne Theater");
    testVenue.save();
    assertEquals(Venue.find(testVenue.getId()), testVenue);
  }
}
