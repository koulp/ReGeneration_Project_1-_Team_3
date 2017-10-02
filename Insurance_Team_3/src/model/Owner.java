package model;

public class Owner {
    private String afm;
    private String plates;
    private String exp_date;

    public Owner(){}

    public Owner(String plates, String exp_date){
        this.plates=plates;
        this.exp_date=exp_date;
    }

    public String getAfm(){ return this.afm; }

    public String getPlates(){ return this.plates; }

    public String getDate(){ return this.exp_date; }

    public void setAfm(String afm){ this.afm=afm; }

    public void setPlate(String plate){ this.plates=plate; }

    public void setDate(String exp_date){ this.exp_date=exp_date; }

}
