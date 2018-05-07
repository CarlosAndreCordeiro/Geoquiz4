package com.example.tiagotiti.geoquiz;

public class Questoes {
    private int indicePergunta;
    private boolean resposta;

    public Questoes() {
    }

    public Questoes(int indicePergunta, boolean resposta) {
        this.indicePergunta = indicePergunta;
        this.resposta = resposta;
    }

    public int getIndicePergunta() {
        return indicePergunta;
    }

    public void setIndicePergunta(int indicePergunta) {
        this.indicePergunta = indicePergunta;
    }

    public boolean isResposta() {
        return resposta;
    }

    public void setResposta(boolean resposta) {
        this.resposta = resposta;
    }
}
