import java.util.Random;

/**
 * 模块类
 *  id: 模块id
 *  width: 宽
 *  height: 高
 */
public class Module {
    private int id;
    private int width;
    private int height;

    public Module(){
        Random random = new Random();
        //this.width = 100;
        this.width = random.nextInt(100) + 50;
        //this.height = 100;
        this.height = random.nextInt(100) + 50;

    }


    public Module(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "{" + id + '}';
    }
}