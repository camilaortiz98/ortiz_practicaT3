package com.camila.ortiz.vid20212;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.lista_anime);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://camila.free.beeceptor.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Servicio servicio = retrofit.create(Servicio.class);
        Call<List<animeClass>> listGetAnime = servicio.listaAnimes();
        listGetAnime.enqueue(new Callback<List<animeClass>>() {
            @Override
            public void onResponse(Call<List<animeClass>> call, Response<List<animeClass>> response) {
                Log.e("lista ", "ok");
                Log.e("ERROR ", String.valueOf(response.code()));
                List<animeClass> classList = response.body();
                Log.e("Tamanio lista ", String.valueOf(classList.size()));
                adptador adptador = new adptador(classList);
                recyclerView.setAdapter(adptador);
            }

            @Override
            public void onFailure(Call<List<animeClass>> call, Throwable t) {
                Log.e("lista ", "NO");
                Log.e("lista ", t.getMessage().toString());
            }
        });
    }
}