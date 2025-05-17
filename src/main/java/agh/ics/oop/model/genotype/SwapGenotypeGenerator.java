package agh.ics.oop.model.genotype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SwapGenotypeGenerator extends AbstractGenotypeGenerator {

    public SwapGenotypeGenerator(List<Integer> genotype1, int energy1,
                                 List<Integer> genotype2, int energy2,
                                 int minMutationsNumber, int maxMutationsNumber){
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
            if(rand.nextInt(100) < 20) // w tym na podmiankę 20%
            {
                var firstGene = rand.nextInt(length);
                var secondGene = rand.nextInt(length);
                swap(genome, firstGene, secondGene);
            }
            else { // a na zwykla mutację 80%
                int numberOfReplaces = rand.nextInt(minMutationsNumber, maxMutationsNumber + 1);
                List<Integer> changedIndexes = new ArrayList<>();
                for (int i = 0; i < length; i++) changedIndexes.add(i);
                Collections.shuffle(changedIndexes);
                List<Integer> indexesToChange = changedIndexes.subList(0, numberOfReplaces);
                for (var index : indexesToChange)
                {
                    genome.set(index, rand.nextInt(length));
                }
            }
        }
        return genome;
    }

    private void swap(List<Integer> list, int index1, int index2)
    {
        var value = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, value);
    }

}
