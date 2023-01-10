package ZooDystopia.Utils;

public class Meter {
    private volatile float value;
    private float max;
    private float threshold;
    //private boolean condition;
    private String name;

    private float updateValue;

    public Meter(String name,float initialValue,float max,float threshold,float updateValue){
        this.setName(name);
        //condition = false;
        setMax(max);
        setThreshold(threshold);
        setUpdateValue(updateValue);
        setValue(initialValue);
    }

    public boolean ifIs(){
        return getValue()<= getThreshold();
    }
    public boolean isEmpty(){
        return getValue() == 0;
    }
    public boolean isFull() { return getValue() == getMax();}
    public synchronized void update(float value){
        setValue(getValue()+value);
    }
    public void update(){
        update(getUpdateValue());
    }

    public synchronized float getValue() {
        return value;
    }

    public synchronized void setValue(float value) {
        this.value = Math.min(Math.max(value,0), getMax());
    }

    @Override
    public String toString(){
        return getName() +": "+ getValue() + "/" + getMax();
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getUpdateValue() {
        return updateValue;
    }

    public void setUpdateValue(float updateValue) {
        this.updateValue = updateValue;
    }

    public void setToMax(){
        setValue(getMax());
    }
}
