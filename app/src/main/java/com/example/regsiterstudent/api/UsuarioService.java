package com.example.regsiterstudent.api;

import com.example.regsiterstudent.model.Usuario;

import java.util.List;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioService {
    @GET("aluno")
    Call<List<Usuario>> getUsuarios();
    @POST("aluno")
    Call<Usuario> postUsuario(@Body Usuario usuario);
    @DELETE("aluno/{id}")
    Call<Void> deleteUsuario(@Path("id") int idUsuario);
    @GET("aluno/{id}")
    Call<Usuario> getUsuarioPorId(@Path("id") int idUsuario);
    @PUT("aluno/{id}")
    Call<Usuario> putUsuarioCall(@Path("id") int idUsuario, @Body Usuario usuario);
}


