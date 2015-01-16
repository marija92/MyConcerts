package mk.ukim.finki.myconcerts.services;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import mk.ukim.finki.myconcerts.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FindByArtist extends Activity{

	/*public void onCreate(Bundle savedInstanceState) {	      
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment2);  
         
        final Button searchButton = (Button) findViewById(R.id.btnSearch);
        
        
        searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String serverURL =getString(R.string.lastFmUrl);
                
                // Use AsyncTask execute Method To Prevent ANR Problem
                new LongOperation().execute(serverURL);

				
			}
		});
 
          
    }
      
      
    // Class with extends AsyncTask class
     
    private class LongOperation  extends AsyncTask<String, Void, Void> {
          
        // Required initialization
         
        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(FindByArtist.this);
        String data =""; 
        TextView uiUpdate = (TextView) findViewById(R.id.output);
        TextView jsonParsed = (TextView) findViewById(R.id.jsonParsed);
        int sizeData = 0;  
        EditText serverText = (EditText) findViewById(R.id.serverText);
         
         
        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
              
            //Start Progress Dialog (Message)
            
            Dialog.setMessage("Please wait..");
            Dialog.show();
             
            try{
                // Set Request parameter
                data +="&" + URLEncoder.encode("data", "UTF-8") + "="+serverText.getText();
                     
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
             
        }
  
        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
             
            // Make Post Call To Web Server 
            BufferedReader reader=null;
    
                 // Send data 
                try
                { 
                   
                   // Defined URL  where to send data
                   URL url = new URL(urls[0]);
                      
                  // Send POST data request
        
                  URLConnection conn = url.openConnection(); 
                  conn.setDoOutput(true); 
                  OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
                  wr.write( data ); 
                  wr.flush(); 
               
                  // Get the server response 
                    
                  reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                  StringBuilder sb = new StringBuilder();
                  String line = null;
                 
                    // Read Server Response
                    while((line = reader.readLine()) != null)
                        {
                               // Append server response in string
                               sb.append(line + " ");
                        }
                     
                    // Append Server Response To Content String 
                   Content = sb.toString();
                }
                catch(Exception ex)
                {
                    Error = ex.getMessage();
                }
                finally
                {
                    try
                    {
          
                        reader.close();
                    }
        
                    catch(Exception ex) {}
                }
             
          
            return null;
        }
          
        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.
              
            // Close progress dialog
            Dialog.dismiss();
              
            if (Error != null) {                  
                uiUpdate.setText("Output : "+Error);
                  
            } else {               
                // Show Response Json On Screen (activity)
                uiUpdate.setText( Content );
                 
             //Start Parse Response JSON Data 
                   
             }
        }
        
        ///cisto za proba
        public void parseJson(String s) throws ParseException {

    		JSONParser jsonParser = new JSONParser();
    		JSONObject jsonObject = (JSONObject) jsonParser.parse(s);
    		// get a String from the JSON object
    		String author = (String) jsonObject.get("artist");
    		System.out.println("Artist: \t" + author);
    		String title = (String) jsonObject.get("title");
    		System.out.println("Title: \t" + title);

    	}
      
        
        
        
    }
	*/
	
	
}
