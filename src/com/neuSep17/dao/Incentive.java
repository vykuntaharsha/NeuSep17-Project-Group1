package com.neuSep17.dao;

public class Incentive {
    private String Id;
    private String Title;
    private int Discount;
    private String Description;
    private String Disclaimer;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle()
    {
        this.Title = Title;
    }

    public int getDiscount()
    {
        return Discount;
    }

    public void setDiscount()
    {
        this.Discount = Discount;
    }

    public String getDescription()
    {
        return Description;
    }
    public void setDescription()
    {
        this.Description = Description;
    }

    public String getDisclaimer()
    {
        return Disclaimer;
    }
    public void setDisclaimer()
    {
        this.Disclaimer = Disclaimer;
    }

    @Override
    public String toString()
    {
        return "Incentive Title : " + Title + ",ID : " + Id + ", Discount : " + Discount + ", Description : " + Description + ", Disclaimer :" + Disclaimer;
    }


}




/*
public class Employee
{
   private Integer id;
   private String firstName;
   private String lastName;

   public Employee(){

   }

   public Employee(Integer id, String firstName, String lastName, Date birthDate){
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
   }

   public Integer getId()
   {
      return id;
   }
   public void setId(Integer id)
   {
      this.id = id;
   }
   public String getFirstName()
   {
      return firstName;
   }
   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }
   public String getLastName()
   {
      return lastName;
   }
   public void setLastName(String lastName)
   {
      this.lastName = lastName;
   }

   @Override
   public String toString()
   {
      return "Employee [id=" + id + ", firstName=" + firstName + ", " +
            "lastName=" + lastName + "]";
   }
}

 */