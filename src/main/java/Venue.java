import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Venue {
  private int id;
  private String name;

  public Venue (String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object otherVenue){
    if (!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.getName().equals(newVenue.getName()) &&
        this.getId() == newVenue.getId();
    }
  }

  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO venues(name) VALUES (:name)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("name", name)
      .executeUpdate()
      .getKey();
    }
  }

  //READ
  public static List<Venue> all() {
    String sql = "SELECT * FROM venues";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  public static Venue find(int id) {
    String sql = "SELECT * FROM venues WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Venue.class);
    }
  }

  public List<Band> getBands() {
    String sql = "SELECT bands.* FROM bands JOIN concerts ON (bands.id = concerts.band_id) WHERE concerts.venue_id = :id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Band.class);
    }
  }

  //UPDATE
  public void update(String newName) {
    String sql = "UPDATE venues SET name = :name WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("name", newName)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
//
  //DELETE
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM venues WHERE id = :id";
        con.createQuery(sql)
          .addParameter("id", id)
          .executeUpdate();
          String sql2 = "DELETE FROM concerts WHERE venue_id = :id";
            con.createQuery(sql2)
              .addParameter("id", id)
              .executeUpdate();
    }
  }

  public static void clearAll() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM venues *";
        con.createQuery(sql).executeUpdate();
      String sql2 = "DELETE FROM concerts *";
        con.createQuery(sql2).executeUpdate();
    }
  }
}
