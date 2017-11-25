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