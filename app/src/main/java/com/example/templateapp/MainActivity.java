package com.example.tabung;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity - Aplikasi Kalkulator Tabung
 * 
 * Aplikasi ini menghitung luas permukaan dan volume tabung
 * berdasarkan input jari-jari dan tinggi dari pengguna.
 */
public class MainActivity extends AppCompatActivity {
    // Input fields untuk jari-jari dan tinggi
    private EditText radiusInput;
    private EditText heightInput;
    
    // TextView untuk menampilkan hasil perhitungan
    private TextView areaResult;
    private TextView volumeResult;
    
    // Container untuk hasil perhitungan (awalnya tersembunyi)
    private LinearLayout resultsContainer;
    
    // View untuk visualisasi tabung
    private CylinderView cylinderView;

    /**
     * Inisialisasi aplikasi
     * Mengatur layout dan menghubungkan event listeners
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radiusInput = findViewById(R.id.radiusInput);
        heightInput = findViewById(R.id.heightInput);
        areaResult = findViewById(R.id.areaResult);
        volumeResult = findViewById(R.id.volumeResult);
        resultsContainer = findViewById(R.id.resultsContainer);
        cylinderView = findViewById(R.id.cylinderView);
        Button calculateButton = findViewById(R.id.calculateButton);
        Button resetButton = findViewById(R.id.resetButton);

        /**
         * Event listener untuk tombol "Hitung"
         * Menghitung luas permukaan dan volume tabung berdasarkan input jari-jari dan tinggi
         */
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String radiusStr = radiusInput.getText().toString();
                String heightStr = heightInput.getText().toString();
                
                // Cek apakah kedua input field telah diisi
                if (!radiusStr.isEmpty() && !heightStr.isEmpty()) {
                    try {
                        // Konversi input string ke double
                        double radius = Double.parseDouble(radiusStr);
                        double height = Double.parseDouble(heightStr);
                        
                        // Hitung luas permukaan: 2πr² + 2πrh dengan presisi tinggi
                        double areaBase = 2 * Math.PI * Math.pow(radius, 2);
                        double areaLateral = 2 * Math.PI * radius * height;
                        double surfaceArea = areaBase + areaLateral;
                        
                        // Hitung volume: πr²h dengan presisi tinggi
                        double volume = Math.PI * Math.pow(radius, 2) * height;
                        
                        // Format hasil perhitungan luas permukaan dengan langkah-langkah
                        String areaSteps = String.format(
                            "Luas Permukaan = 2πr² + 2πrh\n" +
                            "= 2π(%.4f)² + 2π(%.4f)(%.4f)\n" +
                            "= %.4f + %.4f\n" +
                            "= %.4f",
                            radius, radius, height,
                            areaBase,
                            areaLateral,
                            surfaceArea
                        );

                        // Format hasil perhitungan volume dengan langkah-langkah
                        String volumeSteps = String.format(
                            "Volume = πr²h\n" +
                            "= π(%.4f)²(%.4f)\n" +
                            "= %.4f",
                            radius, height,
                            volume
                        );

                        areaResult.setText(areaSteps);
                        volumeResult.setText(volumeSteps);
                        resultsContainer.setVisibility(View.VISIBLE);
                        
                        // Update visualisasi tabung
                        cylinderView.setDimensions((float)radius, (float)height);
                    } catch (NumberFormatException e) {
                        areaResult.setText("Masukkan angka yang valid");
                        volumeResult.setText("");
                        resultsContainer.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areaResult.setText("");
                volumeResult.setText("");
                radiusInput.setText("");
                heightInput.setText("");
                resultsContainer.setVisibility(View.GONE);
            }
        });
    }
}
