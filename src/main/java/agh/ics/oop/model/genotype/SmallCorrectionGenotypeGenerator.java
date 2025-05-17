package agh.ics.oop.model.genotype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmallCorrectionGenotypeGenerator extends AbstractGenotypeGenerator {

    public SmallCorrectionGenotypeGenerator(List<Integer> genotype1, int energy1,
                                            List<Integer> genotype2, int energy2,
                                            int minMutationsNumber, int maxMutationsNumber) {
        super(genotype1, energy1, genotype2, energy2, minMutationsNumber, maxMutationsNumber);
    }

    @Override
    public List<Integer> generate() {
        List<Integer> genome = new ArrayList<>();
        genome.addAll(leftGenotype.subList(0, divideIndex));
        genome.addAll(rightGenotype.subList(divideIndex, length));

        // mutacje
        if(rand.nextInt(100) < 5) // szansa na mutację to 5%
        {
            int numberOfReplaces = rand.nextInt(minMutationsNumber, maxMutationsNumber + 1);
            ArrayList<Integer> changedIndexes = new ArrayList<>();
            for (int i = 0; i < length; i++) changedIndexes.add(i);
            Collections.shuffle(changedIndexes);
            List<Integer> indexesToChange = changedIndexes.subList(0, numberOfReplaces);
            for (var index : indexesToChange)
            {
                genome.set(index, GENE_LIMIT);
            }
        }
        return genome;
    }

}
