package ine.rmutr.ac.th.hyoiqr;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.physicaloid.lib.Physicaloid;
//import com.physicaloid.lib.usb.driver.uart.ReadLisener;
import com.physicaloid.lib.usb.driver.uart.UartConfig;

public class MainActivity extends AppCompatActivity {
    int system_state;
    Physicaloid commMobile;
    Button btnOpen;
    CountDownTimer cdtUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.system_state = 0;

        this.commMobile = new Physicaloid(this);

        btnOpen = (Button)findViewById(R.id.btnOpen);

        cdtUpdate = new CountDownTimer(100, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                ProcessRun();
            }

            @Override
            public void onFinish() {
                cdtUpdate.start();
            }
        }.start();

    }

    public void ProcessRun() {
        this.btnOpen.setText(String.valueOf(this.system_state));

        switch (system_state) {
            default:
            case 0:
                if (this.commMobile.isOpened() == true) {
                    this.commMobile.close();
                }
                this.system_state++;
                break;

            case 1:
                if (this.commMobile.isOpened() == false) {
                    UartConfig uartConfig = new UartConfig(9600, UartConfig.DATA_BITS7
                            , UartConfig.STOP_BITS1, UartConfig.PARITY_EVEN, false, false);
                    commMobile.setConfig(uartConfig);

                    this.commMobile.open();
                } else {
                    this.system_state++;
                }
                break;

            case 2:

                break;
        }
    }

    public void btnOpen_onClick(View view) {

    }

    public void btnTypeA_onClick(View view) {
        if (commMobile.isOpened() == true) {
            String btx_str = "Mobile_Type_A";
            byte[] btx_buf = btx_str.getBytes();
            commMobile.write(btx_buf, btx_buf.length);
        }
    }

    public void btnTypeB_onClick(View view) {
        if (commMobile.isOpened() == true) {
            String btx_str = "Mobile_Type_B";
            byte[] btx_buf = btx_str.getBytes();
            commMobile.write(btx_buf, btx_buf.length);
        }
    }

    public void btnTypeC_onClick(View view) {
        if (commMobile.isOpened() == true) {
            String btx_str = "Mobile_Type_C";
            byte[] btx_buf = btx_str.getBytes();
            commMobile.write(btx_buf, btx_buf.length);
        }
    }
}
