package oulu.university.smartglasses;

/**
 * Created by afirouzi on 20.10.2015.
 */
public interface ICallback {
        void callBackLinphoneMessageReceived(String linphoneMessageValue);
        void callBackLinphoneStatusChanged(String linphoneOnlineStatueValue);
}
