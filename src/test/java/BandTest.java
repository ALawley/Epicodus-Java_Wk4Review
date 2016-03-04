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
  public void find_findsInstanceOfBandById() {
    Band testBand = new Band("Agalloch");
    testBand.save();
    assertEquals(Band.find(testBand.getId()), testBand);
  }
}
