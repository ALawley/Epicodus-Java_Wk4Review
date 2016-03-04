import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Band {
  private int id;
  private String name;

  public Band (String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object otherBand){
    if (!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getName().equals(newBand.getName()) &&
        this.getId() == newBand.getId();
    }
  }

  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO bands(name) VALUES (:name)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("name", name)
      .executeUpdate()
      .getKey();
    }
  }

  //READ
  public static List<Band> all() {
    String sql = "SELECT * FROM bands";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  public static Band find(int id) {
    String sql = "SELECT * FROM bands WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Band.class);
    }
  }

  //UPDATE
  public void update(String newName) {
    String sql = "UPDATE bands SET name = :name WHERE id = :id";
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
      String sql = "DELETE FROM bands WHERE id = :id";
        con.createQuery(sql)
          .addParameter("id", id)
          .executeUpdate();
    }
  }
}
