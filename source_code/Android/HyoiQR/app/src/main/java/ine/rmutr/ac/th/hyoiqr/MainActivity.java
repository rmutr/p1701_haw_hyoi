package ine.rmutr.ac.th.hyoiqr;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.physicaloid.lib.Physicaloid;
//import com.physicaloid.lib.usb.driver.uart.ReadLisener;
import com.physicaloid.lib.usb.driver.uart.ReadLisener;
import com.physicaloid.lib.usb.driver.uart.UartConfig;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    int system_state;
    String rx_buf_str, rx_str;
    int cdt_rx_delay;
    Physicaloid commMobile;
    Button btnOpen, btnTypeA, btnTypeB, btnTypeC;
    TextView txtResult, txtSysInfo;
    CountDownTimer cdtUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.system_state = 0;
        this.rx_buf_str = new String();
        this.rx_str = new String();
        this.cdt_rx_delay = 0;

        this.commMobile = new Physicaloid(this);

        btnOpen    = (Button)findViewById(R.id.btnOpen );
        btnTypeA   = (Button)findViewById(R.id.btnTypeA);
        btnTypeB   = (Button)findViewById(R.id.btnTypeB);
        btnTypeC   = (Button)findViewById(R.id.btnTypeC);
        txtResult  = (TextView)findViewById(R.id.txtResult);
        txtSysInfo = (TextView)findViewById(R.id.txtSysInfo);

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
        this.txtSysInfo.setText("Process runnning on state " + String.valueOf(system_state));
        switch (system_state) {
            default:
            case 0:
                this.btnOpen.setEnabled(true);
                this.btnTypeA.setEnabled(false);
                this.btnTypeB.setEnabled(false);
                this.btnTypeC.setEnabled(false);

                if (this.commMobile.isOpened() == true) {
                    this.commMobile.close();
                }
                this.commMobile.clearReadListener();
                break;

            case 1:
                if (this.commMobile.isOpened() == false) {

                    UartConfig uartConfig = new UartConfig(9600, UartConfig.DATA_BITS7
                            , UartConfig.STOP_BITS1, UartConfig.PARITY_EVEN, false, false);
                    commMobile.setConfig(uartConfig);

                    this.commMobile.open();

                    this.commMobile.addReadListener(new ReadLisener() {

                        @Override
                        public void onRead(int i) {

                            byte[] brx_buf = new byte[255];

                            int brx_bufs = commMobile.read(brx_buf);

                            if (brx_bufs > 0) {
                                rx_buf_str = new String(brx_buf);
                            }
                        }
                    });

                } else {
                    this.btnOpen.setEnabled(false);
                    this.btnTypeA.setEnabled(true);
                    this.btnTypeB.setEnabled(true);
                    this.btnTypeC.setEnabled(true);

                    this.system_state++;
                }
                break;

            case 2:
                if (this.commMobile.isOpened() == false) {
                    this.system_state = 0;
                } else {

                    if (this.rx_buf_str.length() > 0) {
                        this.rx_str = this.rx_buf_str;
                        this.system_state++;
                    }

                }
                break;

            case 3:
                if (this.commMobile.isOpened() == false) {
                    this.system_state = 0;
                } else {

                    if (this.rx_buf_str.length() > 0) {
                        this.rx_str = this.rx_buf_str;
                    }

                    this.rx_buf_str = "";
                    this.txtResult.setText(rx_str);
                    this.cdt_rx_delay = 20;
                    this.system_state++;
                }
                break;

            case 4:
                if (this.cdt_rx_delay > 0) {
                    this.cdt_rx_delay--;
                } else {
                    this.txtResult.setText("");
                    this.system_state = 2;
                }
                break;
        }
    }

    public void btnOpen_onClick(View view) {
        if (this.system_state == 0) {
            this.system_state++;
        }
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
