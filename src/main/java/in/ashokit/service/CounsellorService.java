package in.ashokit.service;


import in.ashokit.binding.DashBoardResponse;
import in.ashokit.entity.Counsellor;


public interface CounsellorService {
	
	public String saveCounsellor(Counsellor c);
	
	public Counsellor loginCheck(String email, String pwd);
	
	public boolean recoverPwd(String email);
	
	public DashBoardResponse getDashBoardInfo(Integer cid);
	
}
