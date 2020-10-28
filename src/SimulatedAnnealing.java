import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        long begin = System.currentTimeMillis();
        System.out.println("BEGIN");
        // 初始化序列对
        SequencePair sequencePair = new SequencePair();
        System.out.println("初始序列对信息：");
        System.out.println("正序列：" + sequencePair.getPositive());
        System.out.println("负序列：" + sequencePair.getNegative());
        // 计算面积
        double area = getArea(sequencePair);
        System.out.println("模块总面积：" + getModuleArea(sequencePair));
        System.out.println("当前布局面积：" + area);
        System.out.println("面积利用率：" + getModuleArea(sequencePair) / area);
        // 模拟退火
        // 初始化温度
        System.out.println("程序运行中。。。");
        int temp = 100000;
        while (temp > 1) {
            System.out.println("当前温度："  + temp);
            for (int i = 0; i < 3000; i++) {
                SequencePair nowSequencePair = cloneSP(sequencePair);
                // 产生扰动
                exchange(nowSequencePair);
                // 评估
                // 计算新面积
                double newArea = getArea(nowSequencePair);
                // 比较面积
                if (newArea < area) {
                    // 接受
                    sequencePair = nowSequencePair;
                    area = newArea;
                } else {
                    // 以一定概率接受
                    if (Math.random() <= Math.exp(-(newArea - area) / temp)) {
                        // 接受
                        sequencePair = nowSequencePair;
                        area = newArea;
                    }
                }
            }
            // 降温
            temp = (int) (temp * 0.95);
        }
        System.out.println("FINISHED");
        System.out.println("当前序列对信息：");
        System.out.println("正序列：" + sequencePair.getPositive());
        System.out.println("负序列：" + sequencePair.getNegative());
        System.out.println("当前布局面积：" + getArea(sequencePair));
        System.out.println("面积利用率：" + getModuleArea(sequencePair) / getArea(sequencePair));
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin) / 1000 + " 秒");
    }

    /**
     * 随机产生扰动：交换正序列模块位置；交换负序列模块位置；同时交换正、负序列模块位置；旋转模块。
     * @param nowSequencePair 当前序列对
     */
    private static void exchange(SequencePair nowSequencePair) {
        /*
           随机产生变换
         */
        for (int i = 0; i < 100; i++) {
            Random random = new Random();
            int anInt = random.nextInt(3);
            if (anInt == 0) {
                // 交换正序列模块位置
                int index1;
                int index2;
                do {
                    index1 = random.nextInt(nowSequencePair.getPositive().size());
                    index2 = random.nextInt(nowSequencePair.getPositive().size());
                } while (index1 == index2);
                Module tempModule = nowSequencePair.getPositive().get(index1);
                nowSequencePair.getPositive().set(index1, nowSequencePair.getPositive().get(index2));
                nowSequencePair.getPositive().set(index2, tempModule);
            } else if (anInt == 1) {
                // 交换负序列模块位置
                int index1;
                int index2;
                do {
                    index1 = random.nextInt(nowSequencePair.getNegative().size());
                    index2 = random.nextInt(nowSequencePair.getNegative().size());
                } while (index1 == index2);
                Module tempModule = nowSequencePair.getNegative().get(index1);
                nowSequencePair.getNegative().set(index1, nowSequencePair.getNegative().get(index2));
                nowSequencePair.getNegative().set(index2, tempModule);
            } else {
                // 旋转模块
                int index = random.nextInt(nowSequencePair.getPositive().size());
                int width = nowSequencePair.getPositive().get(index).getWidth();
                int height = nowSequencePair.getPositive().get(index).getHeight();
                nowSequencePair.getPositive().get(index).setWidth(height);
                nowSequencePair.getPositive().get(index).setHeight(width);
            }
        }
    }

    /**
     * 计算面积
     * @param sequencePair 序列对
     * @return 面积
     */
    private static double getArea(SequencePair sequencePair) {
        List<Module> positive = sequencePair.getPositive();
        List<Module> negative = sequencePair.getNegative();
        int length1 = positive.size();
        int length2 = negative.size();
        double[][] dp1 = new double[length1+1][length2+1];
        double[][] dp2 = new double[length1+1][length2+1];
        for (int i = 1; i < length1 + 1; i++) {
            for (int j = 1; j < length2 + 1; j++) {
                if (positive.get(i - 1) == negative.get(j - 1)){
                    // 这边找到一个 lcs 的元素，继续往前找
                    dp1[i][j] = positive.get(i - 1).getWidth() + dp1[i-1][j-1];
                    dp2[i][j] = positive.get(i - 1).getHeight() + dp2[i-1][j-1];
                }else {
                    // 谁能让 lcs 最长，就听谁的
                    dp1[i][j] = Math.max(dp1[i-1][j],dp1[i][j-1]);
                    dp2[i][j] = Math.max(dp2[i-1][j],dp2[i][j-1]);
                }
            }
        }
        return dp1[length1][length2] * dp2[length1][length2];
    }

    /**
     * 克隆SP对象
     * @param sequencePair 待克隆SP对象
     * @return 新SP对象
     */
    private static SequencePair cloneSP(SequencePair sequencePair) {
        SequencePair sequencePair1 = new SequencePair();
        List<Module> positive = sequencePair1.getPositive();
        List<Module> negative = sequencePair1.getNegative();
        for (int i = 0; i < positive.size(); i++) {
            Module module = new Module();
            module.setId(sequencePair.getPositive().get(i).getId());
            module.setWidth(sequencePair.getPositive().get(i).getWidth());
            module.setHeight(sequencePair.getPositive().get(i).getHeight());
            positive.set(i, module);
        }
        for (int i = 0; i < negative.size(); i++) {
            Module module = sequencePair.getNegative().get(i);
            int id = module.getId();
            for (Module value : positive) {
                if (value.getId() == id) {
                    negative.set(i, value);
                }
            }
        }
        return sequencePair1;
    }

    private static double getModuleArea(SequencePair sequencePair) {
        double area = 0;
        List<Module> positive = sequencePair.getPositive();
        for (Module module : positive) {
            area += module.getHeight() * module.getWidth();
        }
        return area;
    }

}
