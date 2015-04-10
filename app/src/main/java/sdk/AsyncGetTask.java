package sdk;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by xxottosl on 2015-04-09.
 */
public class AsyncGetTask extends AsyncTask<String, String, String> {

    protected boolean error = false;
    protected WasherError washerError;
    @Override
    protected String doInBackground(String... uri) {
        if(!WasherService.isInitialized()){
            error = true;
            washerError = WasherError.NOT_INIT;
            return "";
        }

        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {

            //Log.d("GET URL", uri[0]);
            response = client.execute(new HttpGet(uri[0].replace(" ", "%20")));

            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                error = true;
                washerError = WasherError.BAD_CONNECTION;
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            error = true;
            washerError = WasherError.BAD_CONNECTION;
        } catch (IOException e) {
            error = true;
            washerError = WasherError.BAD_CONNECTION;
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

    }
}
