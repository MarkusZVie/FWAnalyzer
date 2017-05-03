package at.ac.univie.FirewallLogAnayzer.Data;

import java.util.ArrayList;

public class CiscoAsaCodeExceptions {
	private ArrayList<Integer> asaDescriptionCodeExceptions;
	private static CiscoAsaCodeExceptions instance =null;
	private CiscoAsaCodeExceptions(){
		asaDescriptionCodeExceptions = new ArrayList<>();
		asaDescriptionCodeExceptions.add(713049);
	}
	public static CiscoAsaCodeExceptions getInstance(){
		if(instance == null){
			instance = new CiscoAsaCodeExceptions();
		}
		return instance;
	}
	public boolean isAsaCodeAnException(int AsaCode) {
		if(asaDescriptionCodeExceptions.contains(AsaCode)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public ArrayList<String>  getExceptionalDescriptionText(int asaCode,ArrayList<String> desc){
		
		switch (asaCode) {
		case 713049:
			String errorDesc = desc.get(0);
			String s1 = errorDesc.substring(0, errorDesc.indexOf("=  SPI")+2);
			String s2 = errorDesc.substring(errorDesc.indexOf("=  SPI")+"=  SPI".length(), errorDesc.indexOf(("=  SPI"), errorDesc.indexOf("=  SPI")+1)+2);
			desc.set(0, s1 + "Inbound-SPI" + s2 + "Outbound-SPI");
			return desc;
		default:
			return desc;
		}
		
		
	}
	
	
}

