package com.example.caijiapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.caijiapp.ble.BleClientActivity;
import com.example.caijiapp.bt.BTActivity;
import com.example.caijiapp.bt.BtClientActivity;
import com.example.caijiapp.bt.BtServerActivity;
import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BluetoothAdapter mBluetoothAdapter;
    private  APP app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        //bluetoothAdapterfun();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        APP.newtoast(this);

        //判断是否有访问位置的权限，没有权限，直接申请位置权限
        isPermission();
        //APP.toast("本机没有找到蓝牙硬件或驱动！", 0);
        // 检查蓝牙开关
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            APP.toast("本机没有找到蓝牙硬件或驱动！", 0);
            finish();
            return;
        } else {
            if (!adapter.isEnabled()) {
                //直接开启蓝牙
                //adapter.enable();
                //跳转到设置界面
                startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 112);
                //Intent enableBtIntent;
                //enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //(enableBtIntent, REQUEST_ENABLE_BT);
                Log.e("MainActivity","cfw--adapter.isDiscovering()="+adapter.isDiscovering());
                APP.toast("请打开蓝牙！", 0);
            }
        }

        // 检查是否支持BLE蓝牙
        /*if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            APP.toast("本机不支持低功耗蓝牙！", 0);
            finish();
            return;
        }*/


        Log.e("cfw","cfw-1s-setDiscoverableTimeout");
        setDiscoverableTimeout(adapter);

        findViewById(R.id.button_login).setOnClickListener(this);

        /*Button button=findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"按钮被点击了", Toast.LENGTH_SHORT).show();
            }
        });*/
        //初始化蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        getPairedDevices();
    }

    //动态获取位置权限
    //, Manifest.permission.READ_EXTERNAL_STORAGE
    //, Manifest.permission.WRITE_EXTERNAL_STORAGE
    @SuppressLint("NewApi")
    private void isPermission(){
        if ((checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                || (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                || (checkSelfPermission(android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED)
                || (checkSelfPermission(android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
                || (checkSelfPermission(android.Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED)
                || (checkSelfPermission(android.Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED)
                ) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION
                    , android.Manifest.permission.ACCESS_FINE_LOCATION
                    , android.Manifest.permission.BLUETOOTH_SCAN
                    ,android.Manifest.permission.BLUETOOTH_CONNECT
                    , android.Manifest.permission.BLUETOOTH
                    ,android.Manifest.permission.BLUETOOTH_ADMIN}, 200);
        }
    }

    @SuppressLint("MissingPermission")
    private void getPairedDevices() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();
                Log.d("MainActivity","设备名："+deviceName+'\n'+"地址："+deviceHardwareAddress);
            }
        }
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.button_login){
            //这里写上按钮点击后需要执行的代码
            //Toast.makeText(MainActivity.this,"按钮被点击了", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this, BTActivity.class));
            //startActivity(new Intent(this, BtClientActivity.class));
            //startActivity(new Intent(this, BtServerActivity.class));
            //startActivity(new Intent(this, BleClientActivity.class));
            //startActivity(new Intent(this, ConfigActivity.class));
        }
    }

    public void bluetoothAdapterfun() {
        /*bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(MainActivity.this, "此设备不支持蓝牙！", Toast.LENGTH_SHORT).show();
        }*/

        Button myButton = findViewById(R.id.button_login);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) myButton.getLayoutParams();
        params.topMargin = 16;
        params.bottomMargin = 16;
        myButton.setLayoutParams(params);
    }
    /*
     * 可见
     */
    public  void setDiscoverableTimeout(BluetoothAdapter adapter) {
//	    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        try {
            Method setDiscoverableTimeout = BluetoothAdapter.class.getMethod("setDiscoverableTimeout", int.class);
            setDiscoverableTimeout.setAccessible(true);
            Method setScanMode = BluetoothAdapter.class.getMethod("setScanMode", int.class, int.class);
            setScanMode.setAccessible(true);
            setDiscoverableTimeout.invoke(adapter, 1);
            setScanMode.invoke(adapter, BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE, 1);
            Log.e("cfw","cfw--setDiscoverableTimeout");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Bluetooth", "setDiscoverableTimeout failure:" + e.getMessage());
        }
    }

}