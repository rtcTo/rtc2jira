package to.rtc.rtc2jira.exporter.jira.entities;

public class StatusCategory extends NamedEntity {

  private String colorName;

  public static StatusCategory createDone() {
    StatusCategory statusCategory = new StatusCategory();
    statusCategory.setId("3");
    statusCategory.setName("Done");
    statusCategory.setColorName("green");
    return statusCategory;
  }

  public static StatusCategory createInProgress() {
    StatusCategory statusCategory = new StatusCategory();
    statusCategory.setId("4");
    statusCategory.setName("In Progress");
    statusCategory.setColorName("yellow");
    return statusCategory;
  }

  public static StatusCategory createToDo() {
    StatusCategory statusCategory = new StatusCategory();
    statusCategory.setId("2");
    statusCategory.setName("To Do");
    statusCategory.setColorName("blue-gray");
    return statusCategory;
  }



  @Override
  public String getPath() {
    return "statuscategory";
  }

  public String getColorName() {
    return colorName;
  }

  public void setColorName(String colorName) {
    this.colorName = colorName;
  }

}
