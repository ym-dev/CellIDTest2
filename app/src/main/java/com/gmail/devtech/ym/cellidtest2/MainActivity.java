package com.gmail.devtech.ym.cellidtest2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.PhoneStateListener;
import android.widget.TextView;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;

import java.util.List;

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
                cellInfoList = TM.getAllCellInfo();

                if(cellInfoList != null)
                    TV.setText("cellInfoList = null");
                else{
                    TV.setText("There are " + cellInfoList.size() + " Cells\n");
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

