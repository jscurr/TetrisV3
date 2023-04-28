

public class GameThread extends Thread{
    private GamePanel panel;

    public GameThread(GamePanel panel){
        this.panel = panel;
    }

    @Override
    public void run(){
        while(true){
            panel.spawnTetromino(panel.getRandomShape(), panel.getRandomColor());
            do{
                try{
                    Thread.sleep(panel.getDelay());
                }catch(InterruptedException ex){
                    System.out.println("the thread was interupted");
                }
            }
            while(panel.isDropping());
        }
    }

  
}