import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 序列对
 */
public class SequencePair {

    private List<Module> positive = new ArrayList<>();
    private List<Module> negative = new ArrayList<>();

    public SequencePair() {
        for (int i = 0; i < 50; i++) {
            Module module = new Module();
            module.setId(i);
            this.positive.add(module);
            this.negative.add(module);
        }
        Collections.shuffle(this.negative);
        Collections.shuffle(this.positive);
    }

    public List<Module> getPositive() {
        return positive;
    }

    public void setPositive(List<Module> positive) {
        this.positive = positive;
    }

    public List<Module> getNegative() {
        return negative;
    }

    public void setNegative(List<Module> negative) {
        this.negative = negative;
    }

    @Override
    public String toString() {
        return "SequencePair{" +
                "positive=" + positive +
                ", negative=" + negative +
                '}';
    }
}
