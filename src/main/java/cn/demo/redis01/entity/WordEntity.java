package cn.demo.redis01.entity;

public class WordEntity {
    private String word;

    public WordEntity() {
    }

    public WordEntity(String word) {
        this.word=word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
