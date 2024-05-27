package com.example.regsiterstudent.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.regsiterstudent.R;
import com.example.regsiterstudent.api.ApiClient;
import com.example.regsiterstudent.api.UsuarioService;
import com.example.regsiterstudent.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoActivity extends AppCompatActivity {
    Button btnSalvar;
    UsuarioService apiService;
    TextView txtRa, txtNome, txtCep, txtLogradouro, txtComplemento, txtBairro, txtCidade, txtUf;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);

        btnSalvar = findViewById(R.id.btnSalvar);
        apiService = ApiClient.getUsuarioService();
        txtRa = findViewById(R.id.txtRaAluno);
        txtNome = findViewById(R.id.txtNomeAluno);
        txtCep = findViewById(R.id.txtCepAluno);
        txtLogradouro = findViewById(R.id.txtLogradouroAluno);
        txtComplemento = findViewById(R.id.txtComplemetoAluno);
        txtBairro = findViewById(R.id.txtBairroAluno);
        txtCidade = findViewById(R.id.txtCidadeAluno);
        txtUf = findViewById(R.id.txtUf);

        id = getIntent().getIntExtra("id", 0);
        if (id > 0) {
            apiService.getUsuarioPorId(id).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()) {
                        Usuario usuario = response.body();
                        if (usuario != null) {
                            txtRa.setText(usuario.getRa());
                            txtNome.setText(usuario.getNome());
                            txtCep.setText(usuario.getCep());
                            txtLogradouro.setText(usuario.getLogradouro());
                            txtComplemento.setText(usuario.getComplemento());
                            txtBairro.setText(usuario.getBairro());
                            txtCidade.setText(usuario.getCidade());
                            txtUf.setText(usuario.getUf());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Log.e("Obter usuario", "Erro ao obter usuario: " + t.getMessage());
                }
            });
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario usuario = new Usuario();
                usuario.setRa(Integer.parseInt(txtRa.getText().toString()));
                usuario.setNome(txtNome.getText().toString());
                usuario.setCep(txtCep.getText().toString());
                usuario.setLogradouro(txtLogradouro.getText().toString());
                usuario.setComplemento(txtComplemento.getText().toString());
                usuario.setBairro(txtBairro.getText().toString());
                usuario.setCidade(txtCidade.getText().toString());
                usuario.setUf(txtUf.getText().toString());

                if (id == 0) {
                    inserirUsuario(usuario);
                } else {
                    usuario.setId(id);
                    editarUsuario(usuario);
                }
            }
        });
    }

    public void inserirUsuario(Usuario usuario) {
        Call<Usuario> call = apiService.postUsuario(usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AlunoActivity.this, "Inserido com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e("Inserir", "Erro ao criar: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Inserir", "Erro ao criar: " + t.getMessage());
            }
        });
    }

    private void editarUsuario(Usuario usuario) {
        Call<Usuario> call = apiService.putUsuarioCall(id, usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AlunoActivity.this, "Editado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e("Editar", "Erro ao editar: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Editar", "Erro ao editar: " + t.getMessage());
            }
        });
    }
}
