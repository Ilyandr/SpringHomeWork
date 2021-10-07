package Entity;

public class LearnerInfo
{
    private String firstName;
    private String lastName;
    private short age;

    public void setInfo(String firstName, String lastName, short age)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public short getAge() { return age; }
}
