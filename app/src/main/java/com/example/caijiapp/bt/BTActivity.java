package com.example.caijiapp.bt;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caijiapp.R;
import com.example.caijiapp.util.BtReceiver;


public class BTActivity extends AppCompatActivity implements BtBase.Listener, BtReceiver.Listener, BtDevAdapter.Listener{
    private TextView mTips;
    private EditText mInputMsg;
    private EditText mInputFile;
    private TextView mLogs;
    private BtReceiver mBtReceiver;
    private BtDevAdapter mBtDevAdapter;
    private final BtClient mClient = new BtClient(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_btactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RecyclerView rv = findViewById(R.id.RecyclerView_BTClient);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mBtDevAdapter = new BtDevAdapter(this);
        rv.setAdapter(mBtDevAdapter);
        //mTips = findViewById(R.id.tv_tips);
        //mInputMsg = findViewById(R.id.input_msg);
        //mInputFile = findViewById(R.id.input_file);
        //mLogs = findViewById(R.id.tv_log);
        mBtReceiver = new BtReceiver(this, this);//注册蓝牙广播

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        BluetoothAdapter.getDefaultAdapter().startDiscovery();

    }

    @Override
    public void socketNotify(int state, Object obj) {

    }

    @Override
    public void onItemClick(BluetoothDevice dev) {

    }

    @Override
    public void foundDev(BluetoothDevice dev) {

    }
    // 重新扫描
    public void BluetoothScan(View view) {
        //mBtDevAdapter.reScan();
    }
}