/**
 * 
 */
package root.assign2.alarmClock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		String state = intent.getExtras().getString("extra");
		Log.e("MyActivity", "In the receiver with " + state);

		String richard_id = intent.getExtras().getString("quote id");
		Log.e("Richard quote is" , richard_id);

		Intent serviceIntent = new Intent(context,RingtonePlayingService.class);
		serviceIntent.putExtra("extra", state);
		serviceIntent.putExtra("quote id", richard_id);

		context.startService(serviceIntent);
	}
}



