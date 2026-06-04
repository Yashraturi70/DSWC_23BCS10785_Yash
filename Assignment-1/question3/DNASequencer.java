public class DNASequencer {
    private StringBuilder sequenceBuilder;

    public DNASequencer() {
        sequenceBuilder = new StringBuilder(100000);
    }

    public void ingestSequence(char[] sensorData) {
        sequenceBuilder.append(sensorData);
    }

    public void mutateDNA(String target, String replacement) {
        int index = sequenceBuilder.indexOf(target);
        if (index != -1) {
            sequenceBuilder.replace(index, index + target.length(), replacement);
        }
    }
}
