package im.fuad.rit.concpar.p1;

class WordOccurrence {
    private String text;
    private String filename;

    public WordOccurrence(String text, String filename) {
        this.text = text.toLowerCase();
        this.filename = filename;
    }

    public String getText() { return text; }
    public String getFilename() { return filename; }

    public String toString() {
        return getText() + " " + getFilename();
    }

    public Boolean equals(WordOccurrence other) {
        return getText() == other.getText() &&
            getFilename() == other.getFilename();
    }
}
