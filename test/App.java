import org.apache.commons.beanutils.BeanUtils;
import util.LCSUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class App {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        SequencePair sequencePair = new SequencePair();
        SequencePair nowSequencePair = (SequencePair) BeanUtils.cloneBean(sequencePair);
        sequencePair.getPositive().get(1).setHeight(100);
        System.out.println(sequencePair);
        System.out.println(nowSequencePair);
    }
}
