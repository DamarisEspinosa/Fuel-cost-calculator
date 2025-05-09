package com.example.iti_271311_u1_espinosa_castro_damaris_alexia;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText editTextdistance, editTextFuelEfficiency, editTextFuelAmount, editTextFuelPrice, editTextTripCost;
    Spinner spinnerDistance, spinnerFuelEfficiency, spinnerFuelAmount, spinnerFuelPrice;
    String[] listDistances = {"kilometers(km)", "miles(mi)"};
    String[] listFuelEff = {"liters per kilometers (L/100 km)", "miles per gallon (US) (US mpg)", "miles per gallon (UK) (UK mpg)", "kilometers per liter (km/L)"};
    String[] listFuelAmount = {"centiliters (cl)", "liters (l)", "gallons (US) (US gal)", "gallons (UK) (UK gal)"};
    String previousUnitDistance, previousUnitFuelEff, previousUnitFuelAmount, previousFuelPrice;
    Button btnBorrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBorrar = findViewById(R.id.botonBorrar);
        // Spinner
        spinnerDistance = findViewById(R.id.spinnerDistance);
        spinnerFuelEfficiency = findViewById(R.id.spinnerFuelEfficiency);
        spinnerFuelAmount = findViewById(R.id.spinnerFuelAmount);
        spinnerFuelPrice = findViewById(R.id.spinnerFuelPrice);
        // EditText
        editTextdistance = findViewById(R.id.editTextDistance);
        editTextFuelEfficiency = findViewById(R.id.editTextFuelEfficiency);
        editTextFuelAmount = findViewById(R.id.editTextFuelAmount);
        editTextFuelPrice = findViewById(R.id.editTextFuelPrice);
        editTextTripCost = findViewById(R.id.editTextTripCost);
        // Agregar watcher a los edit text para que llame a la función cuando el valor ingresado cambie
        editTextdistance.addTextChangedListener(textWatcherFuelAmount);
        editTextFuelEfficiency.addTextChangedListener(textWatcherFuelAmount);
        editTextFuelAmount.addTextChangedListener(textWatcherTripCost);
        editTextFuelPrice.addTextChangedListener(textWatcherTripCost);
        ArrayAdapter<String> spinnerAdapterDistance = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listDistances);
        spinnerAdapterDistance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistance.setAdapter(spinnerAdapterDistance);
        ArrayAdapter<String> spinnerAdapterFuelEff = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listFuelEff);
        spinnerAdapterFuelEff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFuelEfficiency.setAdapter(spinnerAdapterFuelEff);
        ArrayAdapter<String> spinnerAdapterFuelAmount = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listFuelAmount);
        spinnerAdapterFuelAmount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFuelAmount.setAdapter(spinnerAdapterFuelAmount);
        spinnerFuelPrice.setAdapter(spinnerAdapterFuelAmount);

        // obtener la unidad seleccionada desde el principio
        previousUnitDistance = spinnerDistance.getSelectedItem().toString();
        previousUnitFuelEff = spinnerFuelEfficiency.getSelectedItem().toString();
        previousUnitFuelAmount = spinnerFuelAmount.getSelectedItem().toString();
        previousFuelPrice = spinnerFuelPrice.getSelectedItem().toString();

        spinnerDistance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedUnit = spinnerDistance.getSelectedItem().toString();
                String distance = editTextdistance.getText().toString();
                if (!distance.isEmpty()) {
                    double currentDistance = Double.parseDouble(distance);
                    double convertedDistance = convertDistance(currentDistance, previousUnitDistance, selectedUnit);
                    editTextdistance.setText(String.format(Locale.getDefault(), "%.3f", convertedDistance));
                }
                previousUnitDistance = selectedUnit;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        spinnerFuelEfficiency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedUnit = spinnerFuelEfficiency.getSelectedItem().toString();
                String fuelEfficiency = editTextFuelEfficiency.getText().toString();
                if(!fuelEfficiency.isEmpty()) {
                    double currentFuelEff = Double.parseDouble(fuelEfficiency);
                    double convertedFuelEff = convertFuelEfficiency(currentFuelEff, previousUnitFuelEff, selectedUnit);
                    editTextFuelEfficiency.setText(String.format(Locale.getDefault(),"%.3f", convertedFuelEff));
                }
                previousUnitFuelEff = selectedUnit;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spinnerFuelAmount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedUnit = spinnerFuelAmount.getSelectedItem().toString();
                String fuelAmount = editTextFuelAmount.getText().toString();
                if(!fuelAmount.isEmpty()) {
                    double currentFuelAmount = Double.parseDouble(fuelAmount);
                    double convertedFuelAmount = convertFuelAmount(currentFuelAmount, previousUnitFuelAmount, selectedUnit);
                    editTextFuelAmount.setText(String.format(Locale.getDefault(), "%.3f", convertedFuelAmount));
                }
                previousUnitFuelAmount = selectedUnit;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spinnerFuelPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedUnit = spinnerFuelPrice.getSelectedItem().toString();
                String fuelPrice = editTextFuelPrice.getText().toString();
                if(!fuelPrice.isEmpty()) {
                    double currentFuelPrice = Double.parseDouble(fuelPrice);
                    double convertedFuelPrice = convertFuelPrice(currentFuelPrice, previousFuelPrice, selectedUnit);
                    editTextFuelPrice.setText(String.format(Locale.getDefault(), "%.3f", convertedFuelPrice));
                }
                previousFuelPrice = selectedUnit;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextdistance.setText("");
                editTextFuelEfficiency.setText("");
                editTextFuelAmount.setText("");
                editTextFuelPrice.setText("");
                editTextTripCost.setText("");
            }
        });
    }
    // TextWatcher para fuel amount
    private TextWatcher textWatcherFuelAmount = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Llamar al método para actualizar el fuel amount cuando cambie el texto
            updateFuelAmount();
        }
        @Override
        public void afterTextChanged(Editable s) {}
    };
    // TextWatcher para trip cost
    private TextWatcher textWatcherTripCost = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // llamar al método para actualizar el fuel amount cuando cambie el texto
            updateFuelPrice();
        }
        @Override
        public void afterTextChanged(Editable editable) {}
    };
    // Función para el textWatcher fuelamount
    private void updateFuelAmount () {
        String distanceStr =   editTextdistance.getText().toString();
        String fuelEff = editTextFuelEfficiency.getText().toString();

        if(!distanceStr.isEmpty() && !fuelEff.isEmpty()) {
            double distance = Double.parseDouble(distanceStr);
            double fuel = Double.parseDouble(fuelEff);
            String unitDistance = spinnerDistance.getSelectedItem().toString();
            String unitFuel = spinnerFuelEfficiency.getSelectedItem().toString();

            double convDistance, convFuel;
            convDistance = convertDistance(distance, unitDistance, "kilometers(km)");
            convFuel = convertFuelEfficiency(fuel, unitFuel, "liters per kilometers (L/100 km)");

            double fuelAmount = convFuel * convDistance; // resultado en centilitros
            // convertir si está en otra unidad
            String unitAmount = spinnerFuelAmount.getSelectedItem().toString();
            double finalFuelAmount = convertFuelAmount(fuelAmount, "centiliters (cl)", unitAmount);
            editTextFuelAmount.setText(String.format(Locale.getDefault(), "%.3f", finalFuelAmount));
        } else {
            editTextFuelAmount.setText("");
        }
    }
    private void updateFuelPrice() {
        String fuelAmountStr = editTextFuelAmount.getText().toString();
        String fuelPriceStr = editTextFuelPrice.getText().toString();
        if(!fuelAmountStr.isEmpty() && !fuelPriceStr.isEmpty()) {
            double fuelAmount = Double.parseDouble(fuelAmountStr);
            double fuelPrice = Double.parseDouble(fuelPriceStr);
            String unitAmount = spinnerFuelAmount.getSelectedItem().toString();
            String unitPrice = spinnerFuelPrice.getSelectedItem().toString();

            double convAmount, convPrice;
            convAmount = convertFuelAmount(fuelAmount, unitAmount, "liters (l)");
            convPrice = convertFuelPrice(fuelPrice, unitPrice, "liters (l)");

            double tripCost = convAmount * convPrice;
            editTextTripCost.setText(String.format(Locale.getDefault(), "%.3f", tripCost));
        } else {
            editTextTripCost.setText("");
        }
    }
    // Función para convertir la distancia
    private double convertDistance(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals("kilometers(km)") && toUnit.equals("miles(mi)")) {
            return value * 0.621371;
        } else if (fromUnit.equals("miles(mi)") && toUnit.equals("kilometers(km)")) {
            return value / 0.621371;
        }
        return value;
    }
    // Función para convertir eficiencia de combustible
    private double convertFuelEfficiency(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals("liters per kilometers (L/100 km)") && toUnit.equals("miles per gallon (US) (US mpg)")) {
            return 235.21 / value;
        } else if (fromUnit.equals("liters per kilometers (L/100 km)") && toUnit.equals("miles per gallon (UK) (UK mpg)")) {
            return 282.48 /value;
        } else if (fromUnit.equals("liters per kilometers (L/100 km)") && toUnit.equals("kilometers per liter (km/L)")) {
            return 100 / value;
        } else if (fromUnit.equals("miles per gallon (US) (US mpg)") && toUnit.equals("liters per kilometers (L/100 km)")) {
            return 235.21 / value;
        } else if (fromUnit.equals("miles per gallon (US) (US mpg)") && toUnit.equals("miles per gallon (UK) (UK mpg)")) {
            return value * 1.20095;
        } else if (fromUnit.equals("miles per gallon (US) (US mpg)") && toUnit.equals("kilometers per liter (km/L)")) {
            return value * 0.425144;
        } else if (fromUnit.equals("miles per gallon (UK) (UK mpg)") && toUnit.equals("liters per kilometers (L/100 km)")) {
            return 282.48 / value;
        } else if (fromUnit.equals("miles per gallon (UK) (UK mpg)") && toUnit.equals("miles per gallon (US) (US mpg)")) {
            return value / 1.20095;
        } else if (fromUnit.equals("miles per gallon (UK) (UK mpg)") && toUnit.equals("kilometers per liter (km/L)")) {
            return value * 0.354006;
        } else if (fromUnit.equals("kilometers per liter (km/L)") && toUnit.equals("liters per kilometers (L/100 km)")) {
            return 100 / value;
        } else if (fromUnit.equals("kilometers per liter (km/L)") && toUnit.equals("miles per gallon (US) (US mpg)")) {
            return value / 0.425144;
        } else if (fromUnit.equals("kilometers per liter (km/L)") && toUnit.equals("miles per gallon (UK) (UK mpg)")) {
            return value / 0.354006;
        }
        return value;
    }
    // Función para convertir fuel amount
    private double convertFuelAmount(double fuelAmount, String fromUnit, String toUnit) {
        if(fromUnit.equals("centiliters (cl)") && toUnit.equals("liters (l)")) {
            return fuelAmount / 100;
        } else if (fromUnit.equals("centiliters (cl)") && toUnit.equals("gallons (US) (US gal)")) {
            return fuelAmount / 378.541;
        } else if (fromUnit.equals("centiliters (cl)") && toUnit.equals("gallons (UK) (UK gal)")) {
            return fuelAmount / 454.609;
        } else if (fromUnit.equals("liters (l)") && toUnit.equals("centiliters (cl)")) {
            return fuelAmount * 100;
        } else if (fromUnit.equals("liters (l)") && toUnit.equals("gallons (US) (US gal)")) {
            return fuelAmount / 3.78541;
        } else if (fromUnit.equals("liters (l)") && toUnit.equals("gallons (UK) (UK gal)")) {
            return fuelAmount / 4.54609;
        } else if (fromUnit.equals("gallons (US) (US gal)") && toUnit.equals("centiliters (cl)")) {
            return fuelAmount * 378.541;
        } else if (fromUnit.equals("gallons (US) (US gal)") && toUnit.equals("liters (l)")) {
            return fuelAmount * 3.78541;
        } else if (fromUnit.equals("gallons (US) (US gal)") && toUnit.equals("gallons (UK) (UK gal)")) {
            return fuelAmount * 0.832674;
        } else if (fromUnit.equals("gallons (UK) (UK gal)") && toUnit.equals("centiliters (cl)")) {
            return fuelAmount * 454.609;
        } else if (fromUnit.equals("gallons (UK) (UK gal)") && toUnit.equals("liters (l)")) {
            return fuelAmount * 4.54609;
        } else if (fromUnit.equals("gallons (UK) (UK gal)") && toUnit.equals("gallons (US) (US gal)")) {
            return fuelAmount * 0.832674;
        }
        return fuelAmount;
    }
    private double convertFuelPrice(double fuelPrice, String fromUnit, String toUnit) {
        if(fromUnit.equals("centiliters (cl)") && toUnit.equals("liters (l)")) {
            return fuelPrice * 100;
        } else if (fromUnit.equals("centiliters (cl)") && toUnit.equals("gallons (US) (US gal)")) {
            return (fuelPrice * 378.541);
        } else if (fromUnit.equals("centiliters (cl)") && toUnit.equals("gallons (UK) (UK gal)")) {
            return fuelPrice * 454.609;
        } else if (fromUnit.equals("liters (l)") && toUnit.equals("centiliters (cl)")) {
            return fuelPrice / 100;
        } else if (fromUnit.equals("liters (l)") && toUnit.equals("gallons (US) (US gal)")) {
            return fuelPrice * 3.78541;
        } else if (fromUnit.equals("liters (l)") && toUnit.equals("gallons (UK) (UK gal)")) {
            return fuelPrice * 4.54609;
        } else if (fromUnit.equals("gallons (US) (US gal)") && toUnit.equals("centiliters (cl)")) {
            return fuelPrice / 378.541;
        } else if (fromUnit.equals("gallons (US) (US gal)") && toUnit.equals("liters (l)")) {
            return fuelPrice / 3.78541;
        } else if (fromUnit.equals("gallons (US) (US gal)") && toUnit.equals("gallons (UK) (UK gal)")) {
            return fuelPrice / 0.832674;
        } else if (fromUnit.equals("gallons (UK) (UK gal)") && toUnit.equals("centiliters (cl)")) {
            return fuelPrice / 454.609;
        } else if (fromUnit.equals("gallons (UK) (UK gal)") && toUnit.equals("liters (l)")) {
            return fuelPrice / 4.54609;
        } else if (fromUnit.equals("gallons (UK) (UK gal)") && toUnit.equals("gallons (US) (US gal)")) {
            return fuelPrice / 1.20095;
        }
        return fuelPrice;
    }
}
