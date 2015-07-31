package to.rtc.rtc2jira.storage;

/**
 * Small object to create a new field in a existing record in the db
 * 
 * @author Manuel
 */
public final class Field {

  private final String name;
  private Object value;

  private Field(String name, Object value) {
    this.name = name;
    this.value = value;
  }

  public static Field of(String name, Object value) {
    return new Field(name, value);
  }

  public String getName() {
    return name;
  }

  public Object getValue() {
    return value;
  }
}
