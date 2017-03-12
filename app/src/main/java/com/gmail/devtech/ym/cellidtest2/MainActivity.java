package com.gmail.devtech.ym.cellidtest2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.TextView;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;

import java.util.List;

import static android.support.v7.appcompat.R.id.info;

public class MainActivity extends AppCompatActivity {

    private TextView TV;
    private TelephonyManager TM;
    private List<NeighboringCellInfo> neighboringCellInfoList;

    private String str;
    private Button getCellsInfoBtn;
    private Button getCellsInfoBtn2;
    private List<CellInfo> cellInfoList;
    private PhoneStateListener PSL;
    private int event;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TV = (TextView)findViewById(R.id.iv);
        TM = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        PSL = new PhoneStateListener();
        event = PSL.LISTEN_CELL_INFO | PSL.LISTEN_CELL_LOCATION;


        //getAllCellInfo
        getCellsInfoBtn = (Button)findViewById(R.id.getCellsInfoBtn);
        getCellsInfoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TM.listen(PSL, event);
                cellInfoList = TM.getAllCellInfo();     //nullしかとれず。。。
                Integer callState = TM.getDataState();
                String deviceID = TM.getDeviceId();     //IMEI取得確認
                String networkOperator = TM.getNetworkOperator();   //44020(ソフトバンク)を取得
                String simSerialNumber = TM.getSimSerialNumber();     //SimNumber取得確認8981200-24

                GsmCellLocation gsmCellLocation = (GsmCellLocation)TM.getCellLocation();
                int cid = gsmCellLocation.getCid() & 0xffff;  // GSM cell id
                int lac = gsmCellLocation.getLac() & 0xffff;  // GSM Location Area Code


                if(cellInfoList == null)
                    TV.setText("cellInfoList = null");
                else{
                    TV.setText("There are " + cellInfoList.size() + " Cells\n");
                    List<CellInfo> cellInfoList = TM.getAllCellInfo();
                    for (CellInfo cellInfo : cellInfoList)
                    {
                        if (cellInfo instanceof CellInfoLte)
                        {

                            CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                            if (cellInfoLte != null) {
                                int cellId = Integer.MAX_VALUE;
                                cellId = cellInfoLte.getCellIdentity().getCi();
                                TV.setText("Cell ID = " + cellId);
                            }
                        }
                    }
                }
            }
        });


        //getNeighboringCellInfo
        getCellsInfoBtn2 = (Button)findViewById(R.id.getCellsInfoBtn2);
        getCellsInfoBtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                neighboringCellInfoList = TM.getNeighboringCellInfo();


                if(neighboringCellInfoList == null)
                    TV.setText("neighboringCellInfoList == null\n");
                else
                    TV.setText("There are " + neighboringCellInfoList.size() + " Cells\n");

            }
        });

    }
}

