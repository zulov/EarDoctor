
import org.jfree.data.xy.XYSeries;

/**
 * @author Tomek
 */
public class Exam {

    XYSeries leftEar;
    XYSeries rightEar;
    XYSeries upLimitRange;
    XYSeries downLimitRange;
    String name;
    boolean comleted = false;
    boolean leftEarComleted = false;
    boolean rightEarComleted = false;

    int age;
    int curentEar = -1;
    double currentFreq = 8;
    float curentVolumeLevel = 0;

    public Exam(String _name, int _age) {
        age = _age;
        setUpLimitRange();
        setDownLimitRange();

        leftEar = new XYSeries("Lewe ucho");
        rightEar = new XYSeries("Prawe ucho");

        name = _name;

    }

    private float hAScale(float v) {
        float coef;
        float xMid=4500;
        if (age >75){
            coef=75;
        }else{
            coef=age;
        }
        if (v>4500){
            float max=8000+14000*(75-coef)/75;
            return (max*(v-4500))/15500+4500;
        }else{
            float min=84*(1+(coef-75)/75);
            return ((4500-min)*(v-4500))/4500+4500;
        }
        
    }

    private float vAScale(float v) {
        float yMid = (0 + 150) / 2;
        return (v - yMid) * (100 - age / (float)5) / 100 + yMid;
    }

    public void setUpLimitRange() {
        if (upLimitRange == null) {
            upLimitRange = new XYSeries("Górna granica");
        } else {
            upLimitRange.clear();
        }

        
        upLimitRange.add(hAScale(16), vAScale(115));
        upLimitRange.add(hAScale(20), vAScale(140));
        upLimitRange.add(hAScale(25), vAScale(147));
        upLimitRange.add(hAScale(50), vAScale(130));
        upLimitRange.add(hAScale(100), vAScale(125));
        upLimitRange.add(hAScale(200), vAScale(120));
        upLimitRange.add(hAScale(500), vAScale(117));
        upLimitRange.add(hAScale(1000), vAScale(115));
        upLimitRange.add(hAScale(2000), vAScale(110));
        upLimitRange.add(hAScale(3000), vAScale(108));
        upLimitRange.add(hAScale(4500), vAScale(100));
        upLimitRange.add(hAScale(5000), vAScale(110));
        upLimitRange.add(hAScale(7000), vAScale(120));
        upLimitRange.add(hAScale(9000), vAScale(125));
        upLimitRange.add(hAScale(10000), vAScale(130));
        upLimitRange.add(hAScale(12000), vAScale(128));
        upLimitRange.add(hAScale(16000), vAScale(120));
        upLimitRange.add(hAScale(20000), vAScale(80));
    }

    public void setDownLimitRange() {
        if (downLimitRange == null) {
            downLimitRange = new XYSeries("Granica bólu");
        } else {
            downLimitRange.clear();
        }
        downLimitRange.add(hAScale(16), vAScale(115));
        downLimitRange.add(hAScale(20), vAScale(75));
        downLimitRange.add(hAScale(25), vAScale(65));
        downLimitRange.add(hAScale(50), vAScale(40));
        downLimitRange.add(hAScale(100), vAScale(25));
        downLimitRange.add(hAScale(200), vAScale(18));
        downLimitRange.add(hAScale(500), vAScale(10));
        downLimitRange.add(hAScale(1000), vAScale(8));
        downLimitRange.add(hAScale(2000), vAScale(5));
        downLimitRange.add(hAScale(3000), vAScale(3));
        downLimitRange.add(hAScale(4500), vAScale(2));
        downLimitRange.add(hAScale(5000), vAScale(5));
        downLimitRange.add(hAScale(7000), vAScale(18));
        downLimitRange.add(hAScale(9000), vAScale(20));
        downLimitRange.add(hAScale(10000), vAScale(18));
        downLimitRange.add(hAScale(12000), vAScale(17));
        downLimitRange.add(hAScale(16000), vAScale(30));
        downLimitRange.add(hAScale(20000), vAScale(80));
    }

    public XYSeries getLeftEar() {
        return leftEar;
    }

    public void setLeftEar(XYSeries leftEar) {
        this.leftEar = leftEar;
    }

    public XYSeries getRightEar() {
        return rightEar;
    }

    public void setRightEar(XYSeries rightEar) {
        this.rightEar = rightEar;
    }

    public XYSeries getUpLimitRange() {
        return upLimitRange;
    }

    public void setUpLimitRange(XYSeries upLimitRange) {
        this.upLimitRange = upLimitRange;
    }

    public XYSeries getDownLimitRange() {
        return downLimitRange;
    }

    public void setDownLimitRange(XYSeries downLimitRange) {
        this.downLimitRange = downLimitRange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getCurrentFreq() {
        return currentFreq;
    }

    public void setCurrentFreq(double currentFreq) {
        this.currentFreq = currentFreq;
    }

    public float getCurentVolumeLevel() {
        return curentVolumeLevel;
    }

    public void setCurentVolumeLevel(float curentVolumeLevel) {
        this.curentVolumeLevel = curentVolumeLevel;
    }

    public void addLeftEarValue(double x, float y) {
        leftEar.add(x, y);
    }

    public void addRightEarValue(double x, float y) {
        rightEar.add(x, y);
    }

    public void clearRightEarValue() {
        rightEar = new XYSeries("Prawe ucho");
    }

    public void clearLeftEarValue() {
        leftEar = new XYSeries("Lewe ucho");
    }

    public void incFrequency() {
        this.currentFreq = this.currentFreq *2;
        if (currentFreq > 16000) {
            if (this.curentEar == -1) {
                this.curentEar = 1;
                this.leftEarComleted = true;
            } else if (this.curentEar == 1) {
                this.rightEarComleted = true;
                this.comleted = true;
            }
            currentFreq = 8;
        }
    }

    public void incVolume() {
        this.curentVolumeLevel = this.curentVolumeLevel + 10;
        if (curentVolumeLevel > 100) {
            curentVolumeLevel = 0;
            incFrequency();
        }
    }

    public boolean isComleted() {
        return comleted;
    }

    public void setComleted(boolean comleted) {
        this.comleted = comleted;
    }

    public int getCurentEar() {
        return curentEar;
    }

    public void setCurentEar(int curentEar) {
        this.curentEar = curentEar;
    }

    public void changeEar() {
        this.curentEar = this.curentEar * -1;
    }

    public boolean isLeftEarComleted() {
        return leftEarComleted;
    }

    public void setLeftEarComleted(boolean leftEarComleted) {
        this.leftEarComleted = leftEarComleted;
    }

    public boolean isRightEarComleted() {
        return rightEarComleted;
    }

    public void setRightEarComleted(boolean rightEarComleted) {
        this.rightEarComleted = rightEarComleted;
    }

}
