package in.ashokit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.ashokit.binding.SearchCriteria;
import in.ashokit.entity.StudentEnq;
import in.ashokit.repo.StudentEnqRepo;

@Service
public class EnqServiceImpl implements EnquiryService {
	
	@Autowired
	private StudentEnqRepo srepo;
	
	
	
	@Override
	public boolean addEnq(StudentEnq se) {
		StudentEnq saveEnq = srepo.save(se);
		return saveEnq.getEnqId()!=null;
	}

	@Override
	public List<StudentEnq> getEnquiries(Integer cid, SearchCriteria sc) {
		
		StudentEnq enq = new StudentEnq();
		enq.setCid(cid);
		if (sc.getClassMode()!=null && !sc.getClassMode().equals("")) {
			enq.setClassMode(sc.getClassMode());
		}
		if (sc.getCourseName()!=null && !sc.getCourseName().equals("")) {
			enq.setCourseName(sc.getCourseName());
		}
		if (sc.getEnqStatus()!=null && !sc.getEnqStatus().equals("")) {
			enq.setEnqStatus(sc.getEnqStatus());
		}
		
		Example<StudentEnq> of = Example.of(enq);
		
		List<StudentEnq> enquiries = srepo.findAll(of);
		return enquiries;
	}

}
