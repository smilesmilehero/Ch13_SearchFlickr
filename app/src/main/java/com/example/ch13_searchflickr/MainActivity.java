package com.example.ch13_searchflickr;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    WebView wv;
    ProgressBar pb;
    EditText keyText;
    String keyword;
    String baseUrl="https://m.flickr.com/search/?text=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wv=findViewById(R.id.webView);
        pb=findViewById(R.id.progressBar);
        keyText=findViewById(R.id.editText);

        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());

        wv.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view,int progress){
                pb.setProgress(progress);
                pb.setVisibility(progress<100 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public  void  onBackPressed(){
        if(wv.canGoBack()){
            wv.goBack();
            return;
        }
        super.onBackPressed();
    }

    public  void search(View v){
        keyword=keyText.getText().toString().replaceAll("\\s%20","%20");
        wv.loadUrl(baseUrl+keyword);
    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor editor=getPreferences(MODE_PRIVATE).edit();
        editor.putString("關鍵字",keyword);
        editor.commit();
    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences myPref=getPreferences(MODE_PRIVATE);
        keyword=myPref.getString("關鍵字","Taipei+101");

        if(wv.getUrl()==null)
            wv.loadUrl(baseUrl+keyword);
    }
}