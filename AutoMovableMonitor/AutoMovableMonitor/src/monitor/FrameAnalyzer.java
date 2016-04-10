package monitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class FrameAnalyzer {
	private File submitFile;
	private static final String api_id="c54857d5d14346509a68becb934bb63c";
	private static final String api_key="682abb6989ec4092bba75f421ae22866";
	public static String answer;
	FrameAnalyzer(File file){
		this.submitFile=file;
	};
	public String faceDetect() throws UnsupportedEncodingException{
		HttpClient client=new DefaultHttpClient();
		String url="https://v1-api.visioncloudapi.com/face/detection";
		HttpPost post=new HttpPost(url);
		FileBody file=new FileBody(submitFile);
		MultipartEntity entity=new MultipartEntity();
		entity.addPart("file",file);
		entity.addPart("api_id",new StringBody(api_id));
		entity.addPart("api_secret",new StringBody(api_key));
		post.setEntity(entity);
		try{
			HttpResponse response=client.execute(post);
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity httpentity=response.getEntity();
				BufferedReader br= new BufferedReader( new InputStreamReader(httpentity.getContent()));
				return br.readLine();
			}
			else{
				HttpEntity r_entity = response.getEntity();
				String responseString = EntityUtils.toString(r_entity);
				System.out.println("错误码是："+response.getStatusLine().getStatusCode()+" "+response.getStatusLine().getReasonPhrase()); System.out.println("出错原因是："+responseString);
				return null;
			}
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public int decidestep(){
		int step =1,width=0,left=0,right=0;
		String patternwidth="(\"width\":)(\\d+)(,)";
		Pattern pw = Pattern.compile(patternwidth);
		Matcher result1 =pw.matcher(answer);
		if (result1.find( )) {
		//System.out.println("width value: " + result1.group(0) );
		//System.out.println("width value: " + result1.group(1) );
		//System.out.println("width value: " + result1.group(2) );
			width=Integer.valueOf(result1.group(2)).intValue();
		//	System.out.println("width value: " + width );
		}
		String patternleft="(\"left\":)(\\d+)(,)";//left
		Pattern pl = Pattern.compile(patternleft);
		Matcher result2 =pl.matcher(answer);
		if (result2.find( )) {
		//System.out.println("left value: " + result2.group(2) );
			left=Integer.valueOf(result2.group(2)).intValue();
		//	System.out.println("left value: " + left );
		}
		String patternright="(\"right\":)(\\d+)(,)";
		Pattern pr = Pattern.compile(patternright);
		Matcher result3 =pr.matcher(answer);
		if (result3.find( )) {
		//System.out.println("right value: " + result3.group(2) );
			right=Integer.valueOf(result3.group(2)).intValue();
		//	System.out.println("right value: " + right );
		}
		//if((right+left)>width){step=-((right+left)-width);}
		//else{
			step=((right+left)-width);//}
		System.out.println("step value: " + step );
		return step;
	}

}
