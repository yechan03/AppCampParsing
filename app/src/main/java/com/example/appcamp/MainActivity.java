package com.example.appcamp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {


    String key = "5551556b716b65793631496f4d5456";
    String data;

    TextView TV;
    Button BT;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TV = findViewById(R.id.tv);
        BT = findViewById(R.id.button);

        BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch( view.getId() ){

                    case R.id.button:

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                data= getXmlData();
                                // 아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        TV.setText(data); // TextView에 문자열  data 출력
                                    }
                                });
                            }
                        }).start();
                        break;
                }
            }
        });
    }

    String getXmlData() {
        StringBuffer buffer = new StringBuffer();

        String queryUrl = "http://openapi.seoul.go.kr:8088/" + key + "/xml/ListAirQualityByDistrictService/1/25/";

        try {

            URL url = new URL(queryUrl); //문자열로 된 요청 url을 URL 객체로 생성

            InputStream is = url.openStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            XmlPullParser xpp = factory.newPullParser();

            // inputstream 으로부터 xml 입력받기

            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tag;

                tag = xpp.getName();


                buffer.append("미세먼지 :");

                xpp.next();

                buffer.append(xpp.getText());

                buffer.append("\n");


            }


        } catch (Exception e) {

        }
        return buffer.toString();

    }
}
