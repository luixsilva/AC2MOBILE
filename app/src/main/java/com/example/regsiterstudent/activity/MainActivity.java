package com.example.regsiterstudent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regsiterstudent.R;
import com.example.regsiterstudent.adapter.AlunoAdapter;
import com.example.regsiterstudent.api.ApiClient;
import com.example.regsiterstudent.api.UsuarioService;
import com.example.regsiterstudent.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerUsuario;
    AlunoAdapter AlunoAdapter;
    UsuarioService apiService;
    List<Usuario> listaUsuarios;
    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerUsuario = (RecyclerView) findViewById(R.id.recyclerUsuario);
        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        apiService = ApiClient.getUsuarioService();
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,AlunoActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        obterUsuarios();
    }

    private void configurarRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerUsuario.setLayoutManager(layoutManager);
        AlunoAdapter = new AlunoAdapter(listaUsuarios, this);
        recyclerUsuario.setAdapter(AlunoAdapter);
        recyclerUsuario.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void obterUsuarios() {
        retrofit2.Call<List<Usuario>> call = apiService.getUsuarios();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>>
                    response) {
                listaUsuarios = response.body();
                configurarRecycler();
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("TESTE", "Erro ao obter os contatos: " + t.getMessage());
            }
        });

    }
}