package com.example.remotecontrol;

import android.app.Activity;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {
    //https://irdb.globalcache.com/Home/Database
    //Brand name:TCL/TV/Code Group 1/Power toggle
    //tested on TV Thomson 40FC32*1
    private final static String CMD_TV_POWER =
            "0000 006C 0000 001A 0098 0098 0013 004C 0013 004C 0013 004C 0013 004C 0013 0026 0013 0026 0013 004C 0013 0026 0013 004C 0013 0026 0013 004C 0013 0026 0013 0026 0013 0026 0013 0026 0013 0026 0013 004C 0013 004C 0013 0026 0013 004C 0013 0026 0013 004C 0013 0026 0013 004C 0013 0156";
    private final static String CMD_TV_OK =
            //ok button on remote
            "0000 006C 0000 001A 0098 0098 0013 004C 0013 004C 0013 004C 0013 004C 0013 004C 0013 004C 0013 004C 0013 004C 0013 0026 0013 004C 0013 0026 0013 0026 0013 0026 0013 0026 0013 0026 0013 0026 0013 0026 0013 0026 0013 0026 0013 0026 0013 004C 0013 0026 0013 004C 0013 004C 0013 0152";

    //IR hex codes tested on Amplifier Technics SA-EX310.
    //http://www.remotecentral.com/cgi-bin/codes/technics/saax530/page-3/
    private final static String CMD_AMP_POWER =
            "0000 0070 0000 0064 0080 0040 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0030 0010 0030 0010 0030 0010 0030 0010 0010 0010 0010 0010 0030 0010 0010 0010 0030 0010 0030 0010 0030 0010 0010 0010 0010 0010 0030 0010 0ab4 0080 0040 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0030 0010 0030 0010 0030 0010 0030 0010 0010 0010 0010 0010 0030 0010 0010 0010 0030 0010 0030 0010 0030 0010 0010 0010 0010 0010 0030 0010 09ec";
    private final static String CMD_AMP_VOLUP =
            "0000 0070 0000 0068 0080 0040 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0ab3 0080 0040 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0ab3 0080 0040 0010 0010 0010 0030 0010 000a";
    private final static String CMD_AMP_VOLDOWN =
            "0000 0070 0000 0065 0080 0040 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0ab4 0080 0040 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0010 0010 0010 0010 0030 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0030 0010 0ab3 0080 0039";

    private ConsumerIrManager irManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        irManager = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);

//        buttonPwrTv = findViewById(R.id.btnPwrTv);
//        buttonPwrAmp= findViewById(R.id.btnPwrAmp);
//        buttonVolDown= findViewById(R.id.btnVolDown);
//        buttonVolUp = findViewById(R.id.btnVolUp);
//        buttonPwrAmp.findViewById(R.id.btnPwrAmp);
//        buttonPwrAmp.setOnClickListener(view -> {
//            int[] pattern5 = {3456, 1728, 432, 432, 432, 1296, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 1296, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 1296, 432, 432, 432, 1296, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 1296, 432, 432, 432, 1296, 432, 1296, 432, 1296, 432, 1296, 432, 432, 432, 432, 432, 1296, 432, 432, 432, 1296, 432, 1296, 432, 1296, 432, 432, 432, 432, 432, 1296, 432, 73980, 3456, 1728, 432, 432, 432, 1296, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 1296, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 1296, 432, 432, 432, 1296, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 432, 1296, 432, 432, 432, 1296, 432, 1296, 432, 1296, 432, 1296, 432, 432, 432, 432, 432, 1296, 432, 432, 432, 1296, 432, 1296, 432, 1296, 432, 432, 432, 432, 432, 1296, 432, 68580};
//           irManager.transmit(37010, pattern5);
//        });

        findViewById(R.id.btnPwrTv).setOnClickListener(new ClickListener(hex2ir(CMD_TV_POWER)));
        findViewById(R.id.btnPwrAmp).setOnClickListener(new ClickListener(hex2ir(CMD_AMP_POWER)));
        findViewById(R.id.btnVolDown).setOnClickListener(new ClickListener(hex2ir(CMD_AMP_VOLDOWN)));
        findViewById(R.id.btnVolUp).setOnClickListener(new ClickListener(hex2ir(CMD_AMP_VOLUP)));
        findViewById(R.id.btnOkTv).setOnClickListener(new ClickListener(hex2ir(CMD_TV_OK)));
    }

    private IRCommand hex2ir(final String irData) {
        List<String> list = new ArrayList<>(Arrays.asList(irData.split(" ")));
        list.remove(0); // dummy
        int frequency = Integer.parseInt(list.remove(0), 16); // frequency
        list.remove(0); // seq1
        list.remove(0); // seq2
        frequency = (int) (1000000 / (frequency * 0.241246));
        int pulses = 1000000 / frequency;
        int count;
        int[] pattern = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            count = Integer.parseInt(list.get(i), 16);
            pattern[i] = count * pulses;
        }
        return new IRCommand(frequency, pattern);
    }

    private class ClickListener implements View.OnClickListener {
        private final IRCommand cmd;

        public ClickListener(final IRCommand cmd) {
            this.cmd = cmd;
        }

        @Override
        public void onClick(final View view) {
            try {
                android.util.Log.d("Remote", "frequency: " + cmd.freq);
                android.util.Log.d("Remote", "pattern: " + Arrays.toString(cmd.pattern));
                irManager.transmit(cmd.freq, cmd.pattern);
            } catch (Exception e) {
                String TAG = "MainActivity";
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private static class IRCommand {
        private final int freq;
        private final int[] pattern;

        private IRCommand(int freq, int[] pattern) {
            this.freq = freq;
            this.pattern = pattern;
        }
    }
}
