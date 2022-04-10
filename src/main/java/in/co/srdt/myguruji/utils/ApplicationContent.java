package in.co.srdt.myguruji.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContent
{
	@Value("${in.co.srdt.myguruji.contentstorage}")
	private String baseLocation;
	
	@Value("${in.co.srdt.myguruji.assignmentcontent}")
	private String assignmentContentLocation;
	
	public String getBaseLocation()
	{
		return baseLocation;
	}
	public void setBaseLocation(String baseLocation)
	{
		this.baseLocation = baseLocation;
	}
	public String getAssignmentContentLocation() {
		return assignmentContentLocation;
	}
	public void setAssignmentContentLocation(String assignmentContentLocation) {
		this.assignmentContentLocation = assignmentContentLocation;
	}
}