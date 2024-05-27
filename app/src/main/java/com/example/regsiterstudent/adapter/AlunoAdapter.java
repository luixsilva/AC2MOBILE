package com.example.regsiterstudent.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regsiterstudent.R;
import com.example.regsiterstudent.activity.AlunoActivity;
import com.example.regsiterstudent.api.ApiClient;
import com.example.regsiterstudent.api.UsuarioService;
import com.example.regsiterstudent.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoHolder> {
    private final List<Usuario> usuarios;
    Context context;

    public AlunoAdapter(List<Usuario> usuarios, Context context) {
        this.usuarios = usuarios;
        this.context = context;
    }

    @NonNull
    @Override
    public AlunoAdapter.AlunoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlunoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_aluno, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoAdapter.AlunoHolder holder, int position) {
        holder.ra.setText(usuarios.get(position).getRa() + " - " + usuarios.get(position).getRa());
        holder.nome.setText(usuarios.get(position).getNome());
        holder.btnexcluir.setOnClickListener(view -> removerItem(position));
        holder.btnEditar.setOnClickListener(view -> editarItem(position));
    }

    @Override
    public int getItemCount() {
        return usuarios != null ? usuarios.size() : 0;
    }

    private void editarItem(int position) {
        int id = usuarios.get(position).getId();
        Intent i = new Intent(context, AlunoActivity.class);
        i.putExtra("id", id);
        context.startActivity(i);
    }

    private void removerItem(int position) {
        int id = usuarios.get(position).getId();
        UsuarioService apiService = ApiClient.getUsuarioService();
        Call<Void> call = apiService.deleteUsuario(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    usuarios.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, usuarios.size());
                    Toast.makeText(context, "Excluido com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Exclusao", "Erro ao excluir");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Exclusao", "Erro ao excluir");
            }
        });
    }

    public class AlunoHolder extends RecyclerView.ViewHolder {
        public TextView ra;
        public TextView nome;
        public ImageView btnexcluir;
        public ImageView btnEditar;

        public AlunoHolder(@NonNull View itemView) {
            super(itemView);
            ra = itemView.findViewById(R.id.txtRa);
            nome = itemView.findViewById(R.id.txtNome);
            btnexcluir = itemView.findViewById(R.id.btnExcluir);
            btnEditar = itemView.findViewById(R.id.btnEditar);
        }
    }
}
