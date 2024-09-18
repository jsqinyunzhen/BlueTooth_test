package com.example.caijiapp.bt;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.caijiapp.R;

public class BtDevAdapter extends RecyclerView.Adapter<BtDevAdapter.VH> {
    private static final String TAG = BtDevAdapter.class.getSimpleName();
    private final List<BluetoothDevice> mDevices = new ArrayList<>();
    private final Listener mListener;
    ViewGroup parent1;
    BtDevAdapter(Listener listener) {
        mListener = listener;
        addBound();
    }

    private void addBound() {

        Set<BluetoothDevice> bondedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();//BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        Log.d(TAG, "addBound" + bondedDevices);
        if (bondedDevices != null)
            mDevices.addAll(bondedDevices);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parent1 =parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dev, parent, false);
        //view.setContentView(R.layout.activity_btactivity);
        TextView name;
        name = view.findViewById(R.id.username);
        Log.d(TAG, "onCreateViewHolder---->" + view);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, int position) {
        if(mDevices.size() > 0)
        {
            Log.d(TAG, "onBindViewHolder---->" + mDevices.size()+"position="+position);

            BluetoothDevice dev = mDevices.get(position);
            String name = dev.getName();
            String address = dev.getAddress();
            int bondState = dev.getBondState();

            Log.d(TAG, "onBindViewHolder2---->" +name);
            if(holder.name == null)
            {
                holder.name = holder.itemView.findViewById(R.id.BTname);
            }
            if(holder.address == null)
            {
                holder.address = holder.itemView.findViewById(R.id.BTaddress);
            }
            holder.name.setText(name == null ? "" : name);
            holder.address.setText(String.format("%s (%s)", address, bondState == 10 ? "未配对" : "配对"));
        }
    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }

    public void add(BluetoothDevice dev) {
        if (mDevices.contains(dev))
            return;
        mDevices.add(dev);
        notifyDataSetChanged();
    }

    public void reScan() {
        mDevices.clear();
        addBound();
        BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();

        if (!bt.isDiscovering())
        {
            if(bt.startDiscovery())
            {
                Log.d(TAG, "bluedevice startDiscovery success");
            }
            else
            {
                //Toast.makeText(MainActivity.this, "此设备不支持蓝牙！", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "bluedevice startDiscovery failed");
            }
        }

        notifyDataSetChanged();
    }

    class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView address;

        VH(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.username);
            address = itemView.findViewById(R.id.editTextTextPassword);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Log.d(TAG, "onClick, getAdapterPosition=" + pos);
            if (pos >= 0 && pos < mDevices.size())
                mListener.onItemClick(mDevices.get(pos));
        }
    }

    public interface Listener {
        void onItemClick(BluetoothDevice dev);
    }
}
