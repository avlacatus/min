package ro.infoiasi.min;

/**
 * Created by alexandra.lacatus on 3/19/14.
 */
public abstract class AbstractExecution {


    public static void log(Object obj) {
        if (obj != null) {
            if (obj instanceof Individual[]) {
                log(Individual.toString((Individual[]) obj));
            } else if (obj instanceof Individual[][]) {
                log(Individual.toString((Individual[][]) obj));
            } else {
                System.out.println(obj.toString());
            }
        } else {
            System.out.println("null");
        }
    }

    public static void loge(Object obj) {
        if (obj != null) {
            if (obj instanceof Individual[]) {
                loge(Individual.toString((Individual[]) obj));
            } else if (obj instanceof Individual[][]) {
                loge(Individual.toString((Individual[][]) obj));
            } else {
                System.err.println(obj.toString());
            }
        } else {
            System.err.println("null");
        }
    }

    public abstract int getFitnessEvaluationCount();

}
