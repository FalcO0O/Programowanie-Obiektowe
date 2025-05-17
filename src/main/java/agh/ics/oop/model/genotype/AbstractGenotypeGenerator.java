package agh.ics.oop.model.genotype;

import java.util.List;
import java.util.Random;

public abstract class AbstractGenotypeGenerator implements GenotypeGenerator {

    protected final List<Integer> leftGenotype;
    protected final List<Integer> rightGenotype;
    protected final int divideIndex;
    protected final Random rand = new Random();
    protected final int length;
    protected static final int GENE_LIMIT = 8;
    protected final int minMutationsNumber;
    protected final int maxMutationsNumber;

    public AbstractGenotypeGenerator(List<Integer> genotype1, int energy1,
                                     List<Integer> genotype2, int energy2,
                                     int minMutationsNumber, int maxMutationsNumber) {
        length = genotype1.size();
        int leftEnergy, rightEnergy;
        if (rand.nextBoolean()) {
            leftGenotype = genotype1;
            leftEnergy = energy1;
            rightGenotype = genotype2;
            rightEnergy = energy2;
        } else {
            leftGenotype = genotype2;
            leftEnergy = energy2;
            rightGenotype = genotype1;
            rightEnergy = energy1;
        }
        float energyRatio = (float) leftEnergy / (leftEnergy + rightEnergy);
        divideIndex = (int) (energyRatio * length);
        this.minMutationsNumber = minMutationsNumber;
        this.maxMutationsNumber = maxMutationsNumber;
    }

}
