package in.ashokit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.Util.EmailUtils;
import in.ashokit.binding.DashBoardResponse;
import in.ashokit.entity.Counsellor;
import in.ashokit.entity.StudentEnq;
import in.ashokit.repo.CounsellorRepo;
import in.ashokit.repo.StudentEnqRepo;

@Service
public class CounsellorServiceImpl implements CounsellorService {
	
	@Autowired
	private CounsellorRepo crepo;
	
	@Autowired
	private StudentEnqRepo srepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Override
	public String saveCounsellor(Counsellor c) {
		
		Counsellor obj = crepo.findByEmail(c.getEmail());
		
		if(obj!=null) {
			return "Duplicate Email";
		}
		
		Counsellor savedObj = crepo.save(c);
		if(savedObj.getCid()!=null) {
			return "Registration Successful";
		}
		return "Registration Failed";
	}

	@Override
	public Counsellor loginCheck(String email, String pwd) {
		 return crepo.findByEmailAndPwd(email, pwd);
		
	}

	@Override
	public boolean recoverPwd(String email) {
		Counsellor c = crepo.findByEmail(email);
		if (c==null) {
			return false;
		}
		String subject = "Recover Password - VINAY IT";
		String body = "<h1>Your Password: "+c.getPwd()+"<h1>";
		return emailUtils.sendEmail(subject, body, email);
		
	}

	@Override
	public DashBoardResponse getDashBoardInfo(Integer cid) {
		List<StudentEnq> allEnq = srepo.findByCid(cid);
		int enrolledEnq = allEnq.stream()
								.filter(e->e.getEnqStatus().equals("enrolled"))
								.collect(Collectors.toList()).size();
		DashBoardResponse res = new DashBoardResponse();
		res.setTotalEnq(allEnq.size());
		res.setEnrolledEnq(enrolledEnq);
		res.setLostEnq(allEnq.size()-enrolledEnq);
		return res;
	}

}
