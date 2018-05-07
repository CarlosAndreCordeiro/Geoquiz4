package com.example.tiagotiti.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {
    private ImageButton mPrevButton;
    private ImageButton mNextButton;
    private int pontuacao = 0;
    private TextView mPontuacao;


    private Button mBotaoVerdadeiro;
    private Button mBotaoFalso;
    private TextView mPergunta;
    private int indiceQuestoes = 0;

    private static final int REQUEST_CODE_CHEAT = 0;
    private static final int REQUEST_CHEAT_CODE = 0;
    private boolean mCheater;
    private Button mBtnCheat;

    private Questoes[] questoes =   new Questoes[]{
            new Questoes(R.string.pergunta1, false),
            new Questoes(R.string.pergunta2, true),
            new Questoes(R.string.pergunta3, false),
            new Questoes(R.string.pergunta4, true),
            new Questoes(R.string.pergunta5, true),
            new Questoes(R.string.pergunta6, true),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Collections.shuffle(Arrays.asList(questoes));

        mPontuacao= (TextView) findViewById(R.id.pontuacao);

        mPergunta = (TextView) findViewById(R.id.textoPergunta);
        mPergunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questoes.length!=0){
                    indiceQuestoes = (indiceQuestoes+1)%questoes.length;
                    atualizaQuestao();
                }

            }
        });
        mBotaoVerdadeiro = (Button) findViewById(R.id.botaoVerdadeiro);
        mBotaoVerdadeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checaResposta(true);
            }
        });

        mBotaoFalso = (Button) findViewById(R.id.botaoFalso);
        mBotaoFalso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checaResposta(false);

            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                indiceQuestoes = (indiceQuestoes+1)%questoes.length;
                mCheater = false;

                atualizaQuestao();
                }

        });


        mPrevButton = (ImageButton) findViewById(R.id.prev);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(indiceQuestoes ==0){
                    indiceQuestoes = questoes.length-1;
                } else{
                    indiceQuestoes--;

                }
                mCheater = false;

                atualizaQuestao();

            }
        });

        mBtnCheat = (Button) findViewById(R.id.roubar);
        mBtnCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean resposta = questoes[indiceQuestoes].isResposta();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, resposta);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });


    }

    public void atualizaQuestao(){
        int questao = questoes[indiceQuestoes].getIndicePergunta();
        mPergunta.setText(questao);
    }

    private void checaResposta(boolean respostaUsuario){
        boolean resposta = questoes[indiceQuestoes].isResposta();
        int idPergunta;

        if(mCheater) {
            idPergunta = R.string.licao_moral;
            pontuacao++;
        } else{


        if(resposta== respostaUsuario){
         idPergunta = R.string.correto;

         pontuacao++;
         mPontuacao.setText(String.valueOf(pontuacao));

        } else {
            idPergunta = R.string.incorreto;
        }
        }
        Toast.makeText(this, idPergunta, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dados) {
        if(resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == REQUEST_CHEAT_CODE) {
            if(dados == null) {
                return;
            }
            mCheater = CheatActivity.eRespostaMostrada(dados);
        }
    }

}
