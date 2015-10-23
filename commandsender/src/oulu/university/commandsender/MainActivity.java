package oulu.university.commandsender;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Aryan on 10/22/2015.
 */
public class MainActivity extends Activity implements View.OnClickListener, ICallback {


    Button SendBtn;
    Button LinphoneBtn;
    Button LinphoneOnlineStatusBtn;
    Button LinphoneOnlineStatusBtnForGlasses;
    TextView LinphoneOnlineStatueTextViewForGlasses;
    Button LeftBtn;
    Button RightBtn;
    Button ForwordBtn;
    Button StopBtn;
    EditText LinphoneEditText;
    ICallBackSendMessage iCallBackSendMessage;

    LinphoneMiniManager linphoneMiniManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        getActionBar().setTitle("Send message to the Glasses");

        SendBtn = (Button) findViewById(R.id.dialog_confirm);
        SendBtn.setOnClickListener(this);
        LinphoneBtn = (Button) findViewById(R.id.LinphoneBtn);
        LinphoneBtn.setOnClickListener(this);
        LinphoneOnlineStatusBtn = (Button) findViewById(R.id.OnlineStatueBtn);
        LinphoneOnlineStatusBtn.getBackground().setColorFilter(Color.argb(255, 200, 0, 0), PorterDuff.Mode.DARKEN);
        LinphoneOnlineStatusBtnForGlasses = (Button) findViewById(R.id.OnlineStatueBtnForTheGlasses);
        LinphoneOnlineStatusBtnForGlasses.getBackground().setColorFilter(Color.argb(255, 200, 0, 0), PorterDuff.Mode.DARKEN);
        LinphoneOnlineStatueTextViewForGlasses = (TextView) findViewById(R.id.OnlineStatueTextViewForTheGlasses);
        LinphoneOnlineStatueTextViewForGlasses.setText("Connect to check Glasses status");
        LeftBtn = (Button) findViewById(R.id.command_left_btn);
        LeftBtn.setOnClickListener(this);
        RightBtn = (Button) findViewById(R.id.command_right_btn);
        RightBtn.setOnClickListener(this);
        StopBtn = (Button) findViewById(R.id.command_stop_btn);
        StopBtn.setOnClickListener(this);
        ForwordBtn = (Button) findViewById(R.id.command_forward_btn);
        ForwordBtn.setOnClickListener(this);
        LinphoneEditText = (EditText) findViewById(R.id.LinphoneTextView);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void callBackLinphoneMessageReceived(String linphoneMessageValue) {

    }

    @Override
    public void callBackLinphoneStatusChanged(String linphoneOnlineStatueValue) {
        linphoneOnlineStatueValue = linphoneOnlineStatueValue.toUpperCase();
        switch (linphoneOnlineStatueValue) {
            case "REGISTRATION SUCCESSFUL":
                LinphoneOnlineStatusBtn.getBackground().setColorFilter(Color.argb(255, 0, 200, 0), PorterDuff.Mode.DARKEN);
                LinphoneBtn.setText("Disconnect");
                LinphoneBtn.setTag("Disconnect");
                break;
            default:
                LinphoneOnlineStatusBtn.getBackground().setColorFilter(Color.argb(255, 200, 0, 0), PorterDuff.Mode.DARKEN);
                LinphoneBtn.setText("Connect");
                LinphoneBtn.setTag("Connect");
        }
    }

    @Override
    public void callBackLinphoneFriendStatusChanged(String LinphoneOnlineStatusFriendValue) {
        LinphoneOnlineStatusFriendValue = LinphoneOnlineStatusFriendValue.toUpperCase();
        if (iCallBackSendMessage != null){
        switch (LinphoneOnlineStatusFriendValue) {
            case "ONLINE":
                LinphoneOnlineStatusBtnForGlasses.getBackground().setColorFilter(Color.argb(255, 0, 200, 0), PorterDuff.Mode.DARKEN);
                LinphoneOnlineStatueTextViewForGlasses.setText("The Glasses is ONLINE");
                break;
            default:
                LinphoneOnlineStatusBtnForGlasses.getBackground().setColorFilter(Color.argb(255, 200, 0, 0), PorterDuff.Mode.DARKEN);
                LinphoneOnlineStatueTextViewForGlasses.setText("The Glasses is OFFLINE");
        }
        }
        else{
            LinphoneOnlineStatusBtnForGlasses.getBackground().setColorFilter(Color.argb(255, 200, 0, 0), PorterDuff.Mode.DARKEN);
            LinphoneOnlineStatueTextViewForGlasses.setText("Connect to check Glasses status");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getTag().toString()) {
            case "left":
                SendMessage("L");
                break;
            case "right":
                SendMessage("R");
                break;
            case "forward":
                SendMessage("F");
                break;
            case "stop":
                SendMessage("S");
                break;
            case "send":
                SendMessage(LinphoneEditText.getText().toString());
                break;
            case "Connect":
                linphoneMiniManager = new LinphoneMiniManager(this, "linphone", this);
                iCallBackSendMessage = linphoneMiniManager;
                break;
            case "Disconnect":
                linphoneMiniManager.destroy();
                iCallBackSendMessage = null;
                callBackLinphoneFriendStatusChanged("OFFLINE");
                break;
            default:
        }
    }

    private void SendMessage(String MessageValue) {
        if (iCallBackSendMessage != null) {
            iCallBackSendMessage.callBackLinphoneSendMessage(MessageValue);
        }
    }
}
