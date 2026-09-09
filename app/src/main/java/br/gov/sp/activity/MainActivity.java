package br.gov.sp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView imgPadrao;
    private ImageView imgPedra;
    private  ImageView imgTesoura;
    private ImageView imgPapel;
    private TextView txtResultado;
    private TextView txtPontosPC;
    private TextView txtPontosUsuario;
    private Button btnLimpar;
    private Button btnMelhorde3;
    private int valorPC = 0;
    private int valorUsuario = 0;
    private boolean jogoEmAndamento = true;
    private boolean modoMelhorDe3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Instanciando os componentes da interface
        imgPadrao = findViewById(R.id.imgPadrao);
        imgPapel = findViewById(R.id.imgPapel);
        imgPedra = findViewById(R.id.imgPedra);
        imgTesoura = findViewById(R.id.imgTesoura);
        txtResultado = findViewById(R.id.txtResultado);
        txtPontosPC = findViewById(R.id.txtPontosPC);
        txtPontosUsuario = findViewById(R.id.txtPontosUsuario);
        btnLimpar = findViewById(R.id.btnLimpar);
        btnMelhorde3 = findViewById(R.id.btnMelhorDe3);

        // Clique - Tesoura
        imgTesoura.setOnClickListener(view -> {
            opcSelecionada("tesoura");
        });

        imgPapel.setOnClickListener(view -> {
            opcSelecionada("papel");
        });

        imgPedra.setOnClickListener(view -> {
            opcSelecionada("pedra");
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Limpar os campos
                valorPC = 0;
                valorUsuario = 0;
                txtPontosPC.setText(String.valueOf(valorPC));
                txtPontosUsuario.setText(String.valueOf(valorUsuario));
                modoMelhorDe3 = false;
                jogoEmAndamento = true;

                // Limpar o Resultado (retorna padrão)
                txtResultado.setText("Clique na opção desejada");

            }
        });

        btnMelhorde3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ativa o modo Melhor de 3
                modoMelhorDe3 = true;

                // Reinicia o jogo
                jogoEmAndamento = true;
                valorPC = 0;
                valorUsuario = 0;
                txtPontosPC.setText("0");
                txtPontosUsuario.setText("0");

                // Volta imagem para padrão
                imgPadrao.setImageResource(R.drawable.padrao);
                
                txtResultado.setText("Partida iniciada! Escolha uma opção:");


            }
        });
    }

    public void opcSelecionada(String opcaoSelecionada){
        if (modoMelhorDe3 && !jogoEmAndamento) {
            return;
        }
        // Escolha aleatoria do PC
        int numero = new Random().nextInt(3);
        String[] opcoes = {"pedra", "papel", "tesoura"};
        String opcPC = opcoes[numero];

        // Apresentando a imagem correspondente do PC
        switch (opcPC){
            case "pedra":
                imgPadrao.setImageResource(R.drawable.pedra);
                break;
            case "papel":
                imgPadrao.setImageResource(R.drawable.papel);
                break;
            case "tesoura":
                imgPadrao.setImageResource(R.drawable.tesoura);
                break;
        }


        // Verifica o vencedor
        if (
                // PC Ganhando
                (opcPC.equals("tesoura") && opcaoSelecionada.equals("papel")) ||
                (opcPC.equals("papel") && opcaoSelecionada.equals("pedra")) ||
                (opcPC.equals("pedra") && opcaoSelecionada.equals("tesoura"))
        ){
            txtResultado.setText("Perdeu Mané..");
            valorPC+=1;
            txtPontosPC.setText(String.valueOf(valorPC));
        } else if (

                (opcaoSelecionada.equals("tesoura") && opcPC.equals("tesoura")) ||
                (opcaoSelecionada.equals("papel") && opcPC.equals("papel")) ||
                (opcaoSelecionada.equals("pedra") && opcPC.equals("pedra"))
        ) {
            txtResultado.setText("Empatou.");
        }else if (
                // Usuario ganhando
                (opcaoSelecionada.equals("tesoura") && opcPC.equals("papel")) ||
                (opcaoSelecionada.equals("papel") && opcPC.equals("pedra")) ||
                (opcaoSelecionada.equals("pedra") && opcPC.equals("tesoura"))
        ) {
            txtResultado.setText("GANHOU!!");
            valorUsuario+=1;
            txtPontosUsuario.setText(String.valueOf(valorUsuario));
        }
        if (modoMelhorDe3) {

            // Usuário fez 2 pontos
            if (valorUsuario >= 2) {

                txtResultado.setText("FIM DE JOGO! VOCÊ VENCEU!" + "\n" + "Melhor de 3 novamente ou clique em limpar");
                jogoEmAndamento = false;

            }

            // PC fez 2 pontos
            else if (valorPC >= 2) {

                txtResultado.setText("FIM DE JOGO! O PC VENCEU!" + "\n" + "Melhor de 3 novamente ou clique em limpar");
                jogoEmAndamento = false;
            }
        }
    }
}