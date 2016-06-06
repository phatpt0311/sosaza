package sosaza.commons;

import com.sun.deploy.net.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * Created by phphan on 6/3/2016.
 */
public class SSZHttpClient {
    CloseableHttpClient httpclient = HttpClients.createDefault();
    HttpGet httpget = new HttpGet("http://localhost/json");

    ResponseHandler<MyJsonObject> rh = new ResponseHandler<MyJsonObject>() {

        @Override
        public JsonObject handleResponse(
                final HttpResponse response) throws IOException {
            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(
                        statusLine.getStatusCode(),
                        statusLine.getReasonPhrase());
            }
            if (entity == null) {
                throw new ClientProtocolException("Response contains no content");
            }
            Gson gson = new GsonBuilder().create();
            ContentType contentType = ContentType.getOrDefault(entity);
            Charset charset = contentType.getCharset();
            Reader reader = new InputStreamReader(entity.getContent(), charset);
            return gson.fromJson(reader, MyJsonObject.class);
        }
    };
    MyJsonObject myjson = client.execute(httpget, rh);
}
