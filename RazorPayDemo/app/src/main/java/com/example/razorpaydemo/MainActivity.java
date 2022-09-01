package com.example.razorpaydemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.razorpaydemo.databinding.ActivityMainBinding;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    ActivityMainBinding binding;
    Button buttonPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        buttonPay = binding.btnPay;
        String sAmount = "100";
        int amount = Math.round(Float.parseFloat(sAmount)*100);


        buttonPay.setOnClickListener(view -> {
            Checkout checkout = new Checkout();
            checkout.setKeyID("rzp_test_qNCSal9AgQMlbG");
            checkout.setImage(com.razorpay.R.drawable.rzp_logo);
            JSONObject object  = new JSONObject();
            try {
                object.put("name","Android coding");
                object.put("description","Test Payment");
                object.put("theme.color","#0093DD");
                object.put("currency","INR");
                object.put("amount",amount);
                object.put("prefill.contact","34255666");
                object.put("prefill.email","something@gmail.com");
                checkout.open(MainActivity.this,object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment ID");
        builder.setMessage(s);
        builder.show();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

    }
}