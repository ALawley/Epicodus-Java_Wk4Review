import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/band_venues_test", null, null);

   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteBandQuery = "DELETE FROM bands *;";
      String deleteVenueQuery = "DELETE FROM venues *;";
      String deleteConcertQuery = "DELETE FROM concerts *;";
      con.createQuery(deleteBandQuery).executeUpdate();
      con.createQuery(deleteVenueQuery).executeUpdate();
      con.createQuery(deleteConcertQuery).executeUpdate();
    }
  }
}
