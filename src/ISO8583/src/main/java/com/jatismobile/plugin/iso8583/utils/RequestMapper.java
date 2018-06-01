package com.jatismobile.plugin.iso8583.utils;

import java.util.HashMap;
import java.util.Map;

import org.jpos.iso.ISOMsg;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RequestMapper {
	/*{"1":"0200",
	 * "2:"134003",
	 * "3":"380000",
	 * "7:"040795942",
	 * "11":"776312902",
	 * "12:"095942",
	 * "13":"0407",
	 * "15":"0408",
	 * "18":"6012",
	 * "32":"008",
	 * "37":"702601664041",
	 * "41":"DEVJATIS",
	 * "42":"200900100800000",
	 * "48":"00062155293",
	 * "49":"360",
	 * }
		
		TRX_ID
		PRODUCT_CODE
		SUBSCRIBER_NUMBER
		JATIS_BILLER_ID
		VENDOR_BILLER_ID
	 * 
	 * */
	
	private void setReqV2(ISOMsg isoMsg, HashMap<String, Object> generalRequest, String checksumhash) {
		
	}
	
	private void setReqV2(NodeList nlTemplate, HashMap<String, Object> ircMap, String checksumhash) {
		// TODO Auto-generated method stub
		for (int count = 0; count < nlTemplate.getLength(); count++) {
			Node template = nlTemplate.item(count);
			// make sure it's element node.
			if (template.getNodeType() == Node.ELEMENT_NODE) {
				// get node name and value
				
				String var = template.getTextContent();
				if (template.hasChildNodes() && !ircMap.keySet().contains(var)) {
//					System.out.println("Checksum : " + var);
					if (!var.equals("CHECKSUMHASH")) {
						setReqV2(template.getChildNodes(), ircMap, checksumhash);
					}
					else {
//						ircMap.put(var, checksumhash);
						template.setTextContent(checksumhash);
					}
				}
				else if (template.hasChildNodes() && ircMap.keySet().contains(var)) {
//						System.out.println("Nih = " + template.getTextContent() + " [CLOSE]");
//						ircMap.put(var, template.getTextContent());
						template.setTextContent(ircMap.get(var).toString());
				}
			}
			else {
//					System.out.println("dalem else : " + tempNode.getNodeName());
			}
		}
	}
	
	private void mapResponse(NodeList nlResponse, NodeList nlTemplate, Map<String, Object> props) {
		// TODO Auto-generated method stub
		for (int count = 0; count < nlResponse.getLength(); count++) {
			Node tempNode = nlResponse.item(count);
			Node template = nlTemplate.item(count);
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				// get node name and value
				
				String var = template.getTextContent();
				if (tempNode.hasChildNodes() && !props.keySet().contains(var)) {
					// loop again if has child nodes
//					System.out.println("response : " + var);
					mapResponse(tempNode.getChildNodes(), template.getChildNodes(), props);
				}
				else if (tempNode.hasChildNodes() && props.keySet().contains(var)) {
//						System.out.println("Nih Response = " + tempNode.getTextContent() + " [CLOSE]");
						props.put(var, tempNode.getTextContent());
				}
			}
			else {
//					System.out.println("dalem else : " + tempNode.getNodeName());
			}
		}
	}
}
