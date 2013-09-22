package com.cayden.auto.activity;


import android.app.Activity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import com.cayden.auto.R;
import com.cayden.auto.util.PhoneUtils;

public class MainActivity extends Activity {
	  /** Called when the activity is first created. */  
    RadioGroup rg;//来电操作单选框  
    ToggleButton tbtnRadioSwitch;//Radio开关  
    ToggleButton tbtnDataConn;//数据连接的开关  
    TelephonyManager telMgr;  
    CallStateListener stateListner;  
    int checkedId=0;  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);  
          
        telMgr= (TelephonyManager)getSystemService(TELEPHONY_SERVICE);  
        telMgr.listen(new CallStateListener(), CallStateListener.LISTEN_CALL_STATE);  
          
        PhoneUtils.printAllInform(TelephonyManager.class);  
          
        rg = (RadioGroup)findViewById(R.id.rGrpSelect);  
        rg.setOnCheckedChangeListener(new CheckEvent());  
        tbtnRadioSwitch=(ToggleButton)this.findViewById(R.id.tbtnRadioSwitch);  
        
        
        tbtnRadioSwitch.setOnClickListener(new ClickEvent());  
//        try {  
//            tbtnRadioSwitch.setChecked(PhoneUtils.getITelephony(telMgr).isRadioOn());  
//        }  catch (Exception e) {  
//            Log.e("error",e.getMessage());  
//        }  
//        tbtnDataConn=(ToggleButton)this.findViewById(R.id.tbtnDataConn);  
//        tbtnDataConn.setOnClickListener(new ClickEvent());  
//        try {  
//            tbtnDataConn.setChecked(PhoneUtils.getITelephony(telMgr).isDataConnectivityPossible());  
//        }  catch (Exception e) {  
//            Log.e("error",e.getMessage());  
//        }  
    }  
      
    /** 
     * 来电时的操作 
     * @author GV 
     * 
     */  
    public class CheckEvent implements RadioGroup.OnCheckedChangeListener{  
  
        @Override  
        public void onCheckedChanged(RadioGroup group, int checkedId) {  
            MainActivity.this.checkedId=checkedId;  
        }  
    }  
      
    /** 
     * Radio和数据连接的开关 
     * @author GV 
     * 
     */  
    public class ClickEvent implements View.OnClickListener{  
  
        @Override  
        public void onClick(View v) {  
//            if (v == tbtnRadioSwitch) {  
//                try {  
//                    PhoneUtils.getITelephony(telMgr).setRadio(tbtnRadioSwitch.isChecked());  
//                } catch (Exception e) {  
//                    Log.e("error", e.getMessage());  
//                }  
//            }  
//            else if(v==tbtnDataConn){  
//                try {  
//                    if(tbtnDataConn.isChecked())  
//                        PhoneUtils.getITelephony(telMgr).enableDataConnectivity();  
//                    else if(!tbtnDataConn.isChecked())  
//                        PhoneUtils.getITelephony(telMgr).disableDataConnectivity();  
//                } catch (Exception e) {  
//                    Log.e("error", e.getMessage());  
//                }     
//            }  
        }  
    }  
      
    /** 
     * 监视电话状态 
     * @author GV 
     * 
     */  
    public class CallStateListener extends PhoneStateListener {  
        @Override  
        public void onCallStateChanged(int state, String incomingNumber) {  
            if(state==TelephonyManager.CALL_STATE_IDLE)//挂断  
            {  
                Log.e("IDLE",incomingNumber);  
            }  
            else if(state==TelephonyManager.CALL_STATE_OFFHOOK)//接听  
            {  
                Log.e("OFFHOOK",incomingNumber);  
            }  
            else if(state==TelephonyManager.CALL_STATE_RINGING)//来电  
            {  
                if(MainActivity.this.checkedId==R.id.rbtnAutoAccept)  
                {  
                    try {  
                        //需要<uses-permission android:name="android.permission.MODIFY_PHONE_STATE" />   
//                        PhoneUtils.getITelephony(telMgr).silenceRinger();//静铃  
                        PhoneUtils.getITelephony(telMgr).answerRingingCall();//自动接听  
                          
                    } catch (Exception e) {  
                        Log.e("error",e.getMessage());  
                    }     
                }  
                else if(MainActivity.this.checkedId==R.id.rbtnAutoReject)  
                {  
                    try {  
                        PhoneUtils.getITelephony(telMgr).endCall();//挂断  
//                        PhoneUtils.getITelephony(telMgr).cancelMissedCallsNotification();//取消未接显示  
                    } catch (Exception e) {  
                        Log.e("error",e.getMessage());    
                    }  
                }  
            }  
            super.onCallStateChanged(state, incomingNumber);  
        }  
    }  
}