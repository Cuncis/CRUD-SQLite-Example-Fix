package com.cuncisboss.daftarsiswa;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cuncisboss.daftarsiswa.adapter.StudentAdapter;
import com.cuncisboss.daftarsiswa.db.DatabaseHelper;
import com.cuncisboss.daftarsiswa.model.Student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements StudentAdapter.ClickMenuListener {

    DatabaseHelper dbHelper;
    RecyclerView recyclerView;
    StudentAdapter adapter;

    List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        students = new ArrayList<>();
        students = dbHelper.getAllStudent();
        adapter = new StudentAdapter(students, MainActivity.this, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudent();
            }
        });
    }

    private void addStudent() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialog_add_student, null);
        builder.setView(view);

        final EditText etName = view.findViewById(R.id.et_name);
        final EditText etCity = view.findViewById(R.id.et_city);

        final AlertDialog dialog = builder.create();

        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String city = etCity.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(MainActivity.this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(city)) {
                    Toast.makeText(MainActivity.this, "Kota tidak bolah kosong", Toast.LENGTH_SHORT).show();
                } else {
                    showData(name, city);
                    dialog.dismiss();
                }

            }
        });

        dialog.show();
    }

    private void showData(String name, String city) {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        dbHelper.addStudent(new Student(name, city, date));
        students = dbHelper.getAllStudent();
        adapter = new StudentAdapter(students, MainActivity.this, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, "Berhasil menambahkan Data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_all) {
            dbHelper.deleteAllStudent();
            refresh();
            Toast.makeText(this, "Data berhasil dihapus semua", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickMenu(ImageButton btnSelection, final int position) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, btnSelection);
        popupMenu.inflate(R.menu.menu_selection);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.update:
                        updateData(position);
                        break;
                    case R.id.delete:
                        deleteData(position);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void deleteData(int position) {
        Student student = new Student();
        student.setId(students.get(position).getId());
        dbHelper.deleteStudent(student.getId());
        refresh();
        Toast.makeText(MainActivity.this, "Data berhasil di Hapus", Toast.LENGTH_SHORT).show();
    }

    private void updateData(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialog_add_student, null);
        builder.setView(view);

        final EditText etName = view.findViewById(R.id.et_name);
        final EditText etCity = view.findViewById(R.id.et_city);

        TextView tvTitle = view.findViewById(R.id.tv_title_dialog);
        Button btnUpdate = view.findViewById(R.id.btn_save);
        tvTitle.setText("Update Data");
        btnUpdate.setText("Update");

        etName.setText(students.get(position).getName());
        etCity.setText(students.get(position).getCity());

        final AlertDialog dialog = builder.create();

        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String city = etCity.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(MainActivity.this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(city)) {
                    Toast.makeText(MainActivity.this, "Kota tidak bolah kosong", Toast.LENGTH_SHORT).show();
                } else {
                    // ada positiion
                    showUpdate(name, city, position);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void showUpdate(String name, String city, int position) {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Student student = new Student(name, city, date);

        student.setId(students.get(position).getId());
        dbHelper.updateStudent(student);
        refresh();
        Toast.makeText(MainActivity.this, "Data berhasil di Update ", Toast.LENGTH_SHORT).show();
    }

    public void refresh() {
        students = dbHelper.getAllStudent();
        adapter = new StudentAdapter(students, MainActivity.this, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
