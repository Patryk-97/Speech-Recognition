package com.sriyanksiddhartha.speechtotext;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.RecognitionListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.media.AudioManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private TextView txvResult;
	private Commands commands;
	private SpeechRecognizer speechRecognizer;
	private SpeechRecognitionListener speechRecognitionListener;
	private Intent recognizerIntent;
	private AudioManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
		speechRecognitionListener = new SpeechRecognitionListener();
		manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		speechRecognizer.setRecognitionListener(speechRecognitionListener);
		this.txvResult = (TextView) findViewById(R.id.txvResult);
		this.commands = new Commands();

		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.GERMAN);

//		while(true)
//		{
//			speechRecognizer.startListening(intent);
//			new CountDownTimer(5000, 5000) {
//
//				public void onTick(long millisUntilFinished) {
//					//do nothing, just let it tick
//				}
//
//				public void onFinish() {
//					speechRecognizer.stopListening();
//				}
//			}.start();
//			speechRecognizer.stopListening();
//			speechRecognizer.startListening(intent);
//		}
	}

	public void getSpeechInput(View view) {

		/*Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.GERMAN);

		intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 10000);
		intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 10000);

		//sr.startListening(intent);

		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(intent, 10);
		} else {
			Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
		}

		sr.startListening(intent);*/
		startListening();
	}

	//@Override
	/*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
			case 10:
				if (resultCode == RESULT_OK && data != null) {
					ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
					String[] words = result.get(0).split(" ");
					for(int i = 0; i < words.length; i++)
					{
						commands.addWord(words[i]);
						if(commands.isValidExpression())
						{
							if(commands.isValidSong() == true)
							{
								this.txvResult.setText("Recognized command: " + commands.getActualCommand() + "\n");
								this.txvResult.append("Found song: " + commands.getSong());
							}
							else
							{
								this.txvResult.setText("Recognized command " + commands.getActualCommand());
							}
						}
						else if(commands.unrecognizedCommand())
						{
							if (commands.isValidCommand() == true)
							{
								this.txvResult.setText("Recognized command:\n");
								this.txvResult.append("Not found song: " + commands.getSong());
							}
							else
							{
								this.txvResult.setText("Unrecognized command ");
							}
						}
					}
					commands.resetActualCommand();
				}
				break;
		}
	}*/

	private boolean sentenceService(String sentence)
	{
		// locals
		boolean rV = false;
		String[] words = sentence.split(" ");

		for(int i = 0; i < words.length; i++)
		{
			commands.addWord(words[i]);
			if(commands.isValidExpression())
			{
				if(commands.isValidSong() == true)
				{
					this.txvResult.append("Recognized command: " + commands.getActualCommand() + "\n");
					this.txvResult.append("Found song: " + commands.getSong() + "\n");
				}
				else
				{
					this.txvResult.append("Recognized command " + commands.getActualCommand() + "\n");
				}
				rV = true;
			}
			else if(commands.unrecognizedCommand())
			{
				if (commands.isValidCommand() == true)
				{
					this.txvResult.append("Recognized command:\n");
					this.txvResult.append("Not found song: " + commands.getSong() + "\n");
				}
				else
				{
					this.txvResult.append("Unrecognized command\n");
				}
			}
		}
		commands.resetActualCommand();
		return rV;
	}

	private void startListening() {
		speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
		speechRecognizer.setRecognitionListener(speechRecognitionListener);
		recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 10000);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 10000);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

		//if setting.SpeechEnable
//		manager.setStreamMute(AudioManager.STREAM_MUSIC, true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
			manager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
		} else {
			manager.setStreamMute(AudioManager.STREAM_MUSIC, true);
		}
		speechRecognizer.startListening(recognizerIntent);
	}

	public void restart()
	{
		speechRecognizer.destroy();
		speechRecognizer = null;
		startListening();
	}

	class SpeechRecognitionListener implements RecognitionListener
	{
		public void onReadyForSpeech(Bundle params)
		{
			//txvResult.append("On ready");
		}
		public void onBeginningOfSpeech()
		{
			//txvResult.append("On beggining");
		}
		public void onRmsChanged(float rmsdB)
		{

		}
		public void onBufferReceived(byte[] buffer)
		{

		}
		public void onEndOfSpeech()
		{

			//txvResult.append("onEndOfSpeech");
		}
		public void onError(int error)
		{
			//txvResult.append("error " + error);
			restart();
		}
		public void onResults(Bundle results)
		{
			ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
			for (int i = 0; i < data.size(); i++)
			{
				txvResult.append("results: " + data.get(i) + "\n");
				if(sentenceService(data.get(i)) == true)
				{
					break;
				}
			}
			restart();
		}
		public void onPartialResults(Bundle partialResults)
		{

		}
		public void onEvent(int eventType, Bundle params)
		{

		}
	}
}
