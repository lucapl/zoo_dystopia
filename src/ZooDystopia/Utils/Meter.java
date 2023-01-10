package ZooDystopia.Utils;

public class Meter {
    private float value;
    private float max;
    private float threshold;
    //private boolean condition;
    private String name;

    private float updateValue;

    public Meter(String name,float initialValue,float max,float threshold,float updateValue){
        this.name = name;
        //condition = false;
        value = initialValue;
        this.max = max;
        this.threshold = threshold;
        this.updateValue = updateValue;
    }

    public boolean ifIs(){
        return getValue()<=threshold;
    }
    public boolean isEmpty(){
        return getValue() == 0;
    }
    public synchronized void update(float value){
        setValue(getValue()+value);
    }
    public void update(){
        update(updateValue);
    }

    public synchronized float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = Math.min(Math.max(value,0),max);
    }

    @Override
    public String toString(){
        return name+": "+ value + "/" + max;
    }
}
