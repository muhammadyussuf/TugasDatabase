package id.ac.unsyiah.elektro.tesmobile.tugasdatabase;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi listview yang di layout
        ListView listBarang = (ListView) findViewById(R.id.listBarang);

        SQLiteOpenHelper barangDB = new BarangDB(this);
        db = barangDB.getReadableDatabase();

        // Query-nya:
        //   SELECT _id, sku, nama,gambar
        //   FROM TABEL_BARANG
        //   ORDER BY nama
        cursor = db.query(BarangDB.TABEL_BARANG,               // FROM
                new String[]{BarangDB.BARANG_ID, BarangDB.BARANG_SKU, BarangDB.BARANG_NAMA, BarangDB.BARANG_GAMBAR},//SELECT
                null,
                null,
                null,
                null,
                BarangDB.BARANG_NAMA);               // ORDER BY

        // Buat adapter, tampung nilai cursor yang udah dibuat diatas
        BarangCursorAdapter barangCursorAdapter = new BarangCursorAdapter(this, cursor);

        // list di set dengan Adapter yang kita buat sendiri
        listBarang.setAdapter(barangCursorAdapter);


        //Kalo diklik list itemnya
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> listView,View view,int nomorBerapa, long id) {
                        clickItemLstDaftarBarang(listView, view, nomorBerapa, id);
                    }
                };
        // Tetapkan listerner jika click pada salah satu item di list
        listBarang.setOnItemClickListener(itemClickListener);
    }

    private void clickItemLstDaftarBarang(AdapterView<?> listView, View view, int nomorBerapa, long id) {
        Intent pesan = new Intent(getApplicationContext(), UbahActivity.class);
        pesan.putExtra("id", id);     // Kirim id mahasiswa yang di-click
        startActivity(pesan);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
    public void clickBtnTambah(View view) {
        // Kirim ke TambahActivity
        Intent pesan = new Intent(getApplicationContext(), TambahActivity.class);
        startActivity(pesan);
    }
}