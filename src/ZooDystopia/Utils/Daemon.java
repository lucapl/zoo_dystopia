package ZooDystopia.Utils;

public class Daemon extends Thread{
    public Daemon(Runnable runnable){
        super(runnable);
        setDaemon(true);
    }
}
