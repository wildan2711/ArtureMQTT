package model;

/**
 * Created by wildan on 7/30/2016.
 */
public class Plant {

    private int mId;
    private String mName;
    private Config mPH;
    private Config mPPM;

    public static class Config{
        private double thresholdLow;
        private double thresholdHigh;

        public Config(){}

        public Config(double TL, double TH) {
            this.thresholdLow = TL;
            this.thresholdHigh = TH;
        }

        public double getTL(){ return thresholdLow; }

        public double getTH(){ return thresholdHigh; }

        @Override
        public String toString(){
            return this.thresholdLow + " - " + this.thresholdHigh;
        }
    }

    public Plant(){

    }

    public Plant(String name, Config ph, Config ppm){
        this.mName = name;
        this.mPH = ph;
        this.mPPM = ppm;
    }

    public double getId() { return mId; }

    public void setId(int id) { this.mId = id; }

    public String getName() { return mName; }

    public void setName(String name) { this.mName = name; }

    public Config getPH() { return mPH; }

    public void setPH(Config ph) { this.mPH = ph; }

    public Config getPPM() { return mPPM; }

    public void setPPM(Config ppm) { this.mPPM = ppm; }

    @Override
    public String toString(){
        return this.mName;
    }

}
