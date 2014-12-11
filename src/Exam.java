
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
        float xMid = (20 + 16000) / 2;
        return (v - xMid) * (100 - age / 10) / 100 + xMid;
    }

    private float vAScale(float v) {
        float yMid = (0 + 150) / 2;
        return (v - yMid) * (100 - age / 10) / 100 + yMid;
    }

    public void setUpLimitRange() {
        if (upLimitRange == null) {
            upLimitRange = new XYSeries("Górna granica");
        } else {
            upLimitRange.clear();
        }
        upLimitRange.add(hAScale(20), vAScale(120));
        upLimitRange.add(hAScale(30), vAScale(140));
        upLimitRange.add(hAScale(50), vAScale(130));
        upLimitRange.add(hAScale(100), vAScale(125));
        upLimitRange.add(hAScale(200), vAScale(120));
        upLimitRange.add(hAScale(500), vAScale(116));
        upLimitRange.add(hAScale(1000), vAScale(115));
        upLimitRange.add(hAScale(2000), vAScale(116));
        upLimitRange.add(hAScale(5000), vAScale(105));
        upLimitRange.add(hAScale(10000), vAScale(150));
        upLimitRange.add(hAScale(16000), vAScale(80));
    }

    public void setDownLimitRange() {
        if (downLimitRange == null) {
            downLimitRange = new XYSeries("Dolna granica");
        } else {
            downLimitRange.clear();
        }
        downLimitRange.add(hAScale(20), vAScale(120));
        downLimitRange.add(hAScale(50), vAScale(40));
        downLimitRange.add(hAScale(100), vAScale(25));
        downLimitRange.add(hAScale(200), vAScale(15));
        downLimitRange.add(hAScale(500), vAScale(10));
        downLimitRange.add(hAScale(1000), vAScale(7));
        downLimitRange.add(hAScale(2000), vAScale(6));
        downLimitRange.add(hAScale(5000), vAScale(2));
        downLimitRange.add(hAScale(10000), vAScale(20));
        downLimitRange.add(hAScale(16000), vAScale(80));
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
