import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Band.all().size());
  }

  @Test
  public void save_addsInstanceOfBandToList() {
    Band testBand = new Band("Agalloch");
    Band testBand1 = new Band("Primordial");
    testBand.save();
    testBand1.save();
    assertEquals(2, Band.all().size());
  }

  @Test
  public void update_changesBandName() {
    Band testBand = new Band("Agalloch");
    testBand.save();
    testBand.update("Primordial");
    Band savedBand = Band.find(testBand.getId());
    assertEquals("Primordial", savedBand.getName());
  }

  @Test
  public void delete_removesBandFromDatabase() {
    Band testBand = new Band("Agalloch");
    testBand.save();
    testBand.delete();
    assertEquals(0, Band.all().size());
  }

  @Test
  public void clearAll_removesAllBandsFromDatabase() {
    Band testBand = new Band("Agalloch");
    Band testBand1 = new Band("Primordial");
    testBand.save();
    testBand1.save();
    Band.clearAll();
    assertEquals(0, Band.all().size());
  }

  @Test
  public void find_findsInstanceOfBandById() {
    Band testBand = new Band("Agalloch");
    testBand.save();
    assertEquals(Band.find(testBand.getId()), testBand);
  }

  @Test
  public void addVenue_assignsVenueToBand() {
    Band testBand = new Band("Agalloch");
    testBand.save();
    Venue testVenue = new Venue("Hawthorne Theater");
    testVenue.save();
    testBand.addVenue(testVenue.getId());
    assertEquals(1, testBand.getVenues().size());
  }

  @Test
  public void getVenues_getsAllVenuesBandHasPlayed() {
    Band testBand = new Band("Agalloch");
    testBand.save();
    Venue testVenue = new Venue("Hawthorne Theater");
    testVenue.save();
    testBand.addVenue(testVenue.getId());
    assertEquals(testVenue, testBand.getVenues().get(0));
  }
}
