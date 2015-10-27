package com.callsattribution.umpt;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class MobileInfoService {
	/**
	 * ��õ绰�����������Ϣ
	 * 
	 * @param inStream
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public static String getMobileAddress(InputStream inStream, String mobile)
			throws Exception {
		// ����������
		String soap = readSoapFile(inStream, mobile);
		byte[] data = soap.getBytes();
		//ʹ��URL��������;
		URL url = new URL(
				"http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5 * 1000);
		// ���ͨ��post�ύ���ݣ�����������������������
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type",
				"application/soap+xml; charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		outStream.close();
		if (conn.getResponseCode() == 200) {
			return parseResponseXML(conn.getInputStream());
		}
		return null;
	}

	/**
	 * ��ȡSoap�ļ�
	 * 
	 * @param inStream
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	private static String readSoapFile(InputStream inStream, String mobile)
			throws Exception {
		// ��ȡ������
		byte[] data = StreamTool.readInputStream(inStream);
		String soapxml = new String(data);
		Map<String, String> params = new HashMap<String, String>();
		params.put("mobile", mobile);
		return replace(soapxml, params);
	}

	/**
	 * �滻ռλ������
	 * 
	 * @param xml
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String replace(String xml, Map<String, String> params)
			throws Exception {
		String result = xml;
		if (params != null && !params.isEmpty()) {
			// ѭ���滻������ռλ��
			for (Map.Entry<String, String> entry : params.entrySet()) {
				// ��Ҫ��$����ת��
				String name = "\\$" + entry.getKey();
				// ʹ��������ʽ�滻
				Pattern pattern = Pattern.compile(name);
				Matcher matcher = pattern.matcher(result);
				if (matcher.find()) {
					result = matcher.replaceAll(entry.getValue());
				}
			}
		}
		return result;
	}

	/**
	 * �������ص�XML�ַ�������
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	private static String parseResponseXML(InputStream inStream)
			throws Exception {
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inStream, "UTF-8");
		// ������һ���¼�
		int eventType = parser.getEventType();
		// ֻҪ�����ĵ������¼�
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_TAG:
				// ��ȡ��������ǰָ���Ԫ�ص�����
				String name = parser.getName();
				if ("getMobileCodeInfoResult".equals(name)) {
					return parser.nextText();
				}
				break;
			}
			eventType = parser.next();
		}
		return null;
	}
}