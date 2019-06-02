package swt6.spring.basics.ioc.domain;

public class Employee {
  private Long     id;
  private String   firstName;
  private String   lastName;
  
  public Employee() {  
  }

  public Employee(String firstName, String lastName) {
    this(null, firstName, lastName);
  }

  public Employee(Long id, String firstName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Long getId() {
    return id;
  }
  
  @SuppressWarnings("unused")
  private void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public String getLastName() {
    return lastName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String toString() {
    StringBuffer sb = new StringBuffer();
    if (id != null)
      sb.append(id + ": ");
    sb.append(lastName + ", " + firstName);
    
    return sb.toString();
  }
}
