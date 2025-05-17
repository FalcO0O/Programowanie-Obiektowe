package agh.ics.oop.model.genotype;

public record GenotypeRankingEntry(String genotype, int frequency) implements Comparable<GenotypeRankingEntry> {

    @Override
    public int compareTo(GenotypeRankingEntry other) {
        if (this.frequency != other.frequency) {
            return this.frequency - other.frequency;
        }
        return this.genotype.compareTo(other.genotype);
    }

    @Override
    public String toString() {
        return "(genotype: %s, frequency: %d)".formatted(genotype, frequency);
    }
}
