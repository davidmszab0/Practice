package grace.assignment3.CallHistory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * BroadcastReceiver for receiving incoming calls
 * Source: Julia Bergmayr, https://github.com/bergmali/android/tree/master/ass3
 * This class is not my, David Szabo's work!
 */

public class CallBroadcastReceiver extends BroadcastReceiver {

	private DbOperations dbOperations;
	private String date;

	/**
	 * When an incoming call is detected, it gets saved into the database and Listview gets updated
	 * manually
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("onReceive - IncomingCalls"); // just for debugging

		CallInfo callInfo = null;
		Bundle bundle = intent.getExtras();
		String state = bundle.getString(TelephonyManager.EXTRA_STATE);

		if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) {
			String number = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
			dbOperations = new DbOperations(context);
			dbOperations.open();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.date = sdf.format(date);
			List<CallInfo> allCalls = dbOperations.getAllCalls();
			// if number already exists only update date of callInfo
			// no duplicate entries in case of one number calling twice
			for (CallInfo callInfo1 : allCalls) {
				if (callInfo1.getNumber().equalsIgnoreCase(number)) {
					dbOperations.deleteCall(callInfo1);
				}
			}
			dbOperations.addCall(this.date, number);
			// update listview with new callInfo
			CallLog.updateListView();
		}
	}
}
